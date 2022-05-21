package com.example.rezh.services;


import com.example.rezh.entities.File;
import com.example.rezh.entities.History;
import com.example.rezh.exceptions.HistoryNotFoundException;
import com.example.rezh.repositories.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class HistoryService {

    private final HistoryRepository historyRepository;
    private final FileStore fileStore;

    @Autowired
    public HistoryService(HistoryRepository historyRepository, FileStore fileStore) {
        this.historyRepository = historyRepository;
        this.fileStore = fileStore;
    }

    public History getOneHistory(Long id) throws HistoryNotFoundException {
        if (historyRepository.findById(id).isEmpty())
            throw new HistoryNotFoundException("История не найдена");

        return historyRepository.getById(id);
    }

    public List<History> getHistoryPagination(Integer page, Integer count, String find) {
        List<History> history;
        if (find == null)
            history = getAllHistory();

        else
            history = historyRepository.findHistory( "%" + find + "%");

        if (page != null && count != null) {
            List<History> currentHistory = new ArrayList<>();
            for (int i = (page - 1) * count; i < page * count; i++) {
                if (i > history.size() - 1)
                    break;
                currentHistory.add(history.get(i));
            }
            return currentHistory;
        }
        return history;
    }

    public List<History> getAllHistory(){
        return historyRepository.findAll();
    }

    public void postHistory(String title, String text, ArrayList<MultipartFile> files) throws IOException {
        History history = new History();
        history.setTitle(title);
        history.setText(text);
        if (files != null)
            addFiles(files, history);
        historyRepository.save(history);
    }

    public void deleteHistory(Long id) throws HistoryNotFoundException {
        if (historyRepository.findById(id).isEmpty())
            throw new HistoryNotFoundException("История не найдена");

        historyRepository.deleteById(id);
    }

    public void editHistory(String title, String text, ArrayList<MultipartFile> files, Long id) throws HistoryNotFoundException, IOException {
        if (historyRepository.findById(id).isEmpty())
            throw new HistoryNotFoundException("История не найдена");

        History currentHistory = getOneHistory(id);
        if (title != null)
            currentHistory.setTitle(title);
        if (text != null)
            currentHistory.setText(text);
        if (files != null)
            addFiles(files, currentHistory);

        historyRepository.save(currentHistory);
    }

    private void addFiles(ArrayList<MultipartFile> files, History history) throws IOException {

        for (MultipartFile file : files) {
            if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
                String path = "rezh3545";
                String filename = "uploads/" + UUID.randomUUID() + "-" + file.getOriginalFilename();
                fileStore.save(path, filename, file);



                File historyFile = new File();
                historyFile.setFileName("https://storage.yandexcloud.net/rezh3545/" + filename);
                historyFile.setHistory(history);
                history.addFile(historyFile);
            }
        }
    }
}
