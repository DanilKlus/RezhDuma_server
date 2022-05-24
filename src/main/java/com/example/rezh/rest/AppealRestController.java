package com.example.rezh.rest;


import com.example.rezh.entities.Appeal;
import com.example.rezh.models.AppealModel;
import com.example.rezh.services.AppealService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/appeals")
@AllArgsConstructor
public class AppealRestController {

    private final AppealService appealService;

    //all
    @GetMapping("/popular")
    public ResponseEntity getFrequentAppeals(@RequestParam(required = false) Integer page,
                                             @RequestParam(required = false) Integer count,
                                             @RequestParam(required = false, defaultValue = "") String find,
                                             @RequestParam(required = false, defaultValue = "") String type,
                                             @RequestParam(required = false, defaultValue = "") String district,
                                             @RequestParam(required = false, defaultValue = "") String topic) {
        try {
            var appeals = appealService.getFrequents(find, type, district, topic);
            if (page != null && count != null)
                return ResponseEntity.ok(AppealModel.toModel(appealService.doPagination(appeals, page, count), false));
            return ResponseEntity.ok(AppealModel.toModel(appeals, false));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    //users
    @GetMapping(value = "user/{appealID}")
    public ResponseEntity getUserOneAppeal(@RequestHeader(name = "Authorization") String token,
                                           @PathVariable Long appealID) {
        try {
            return ResponseEntity.ok().body(AppealModel.toModel(appealService.getUserAppeal(token, appealID), false));
        } catch (Exception e ) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @GetMapping(value = "/user")
    public ResponseEntity getUserAppeals(@RequestHeader(name = "Authorization") String token,
                                         @RequestParam(required = false) Boolean answered,
                                         @RequestParam(required = false, defaultValue = "") String find,
                                         @RequestParam(required = false, defaultValue = "") String type,
                                         @RequestParam(required = false, defaultValue = "") String district,
                                         @RequestParam(required = false, defaultValue = "") String topic,
                                         @RequestParam(required = false) Integer page,
                                         @RequestParam(required = false) Integer count) {
        try {
            List<Appeal> appeals = appealService.getAppeals(token, answered, find,  type, district, topic);

            if (page != null && count != null)
                return ResponseEntity.ok(AppealModel.toModel(appealService.doPagination(appeals, page, count), false));

            return ResponseEntity.ok().body(AppealModel.toModel(appeals, false));
        } catch (Exception e ) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @DeleteMapping(value = "/user", params = {"appeal"})
    public ResponseEntity deleteUserAppeal(@RequestHeader(name = "Authorization") String token,
                                           @RequestParam Long appeal) {
        try {
            appealService.deleteAppeal(token, appeal);
            return ResponseEntity.ok("Обращение удалено");
        } catch (Exception e ) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @PostMapping(value = "/user")
    public ResponseEntity postAppeal(@RequestHeader(name = "Authorization") String token,
                                     @RequestParam(required = false) String type,
                                     @RequestParam(required = false) String district,
                                     @RequestParam(required = false) String topic,
                                     @RequestParam(required = false) String text,
                                     @RequestParam(required = false) ArrayList<MultipartFile> files) {

        try {
            appealService.postAppeal(token, type, district, topic, text, files);
            return ResponseEntity.ok("Обращение создано");
        } catch (Exception e ) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @PatchMapping(value = "/user", params = {"appeal"})
    public ResponseEntity editUserAppeal(@RequestHeader(name = "Authorization") String token,
                                         @RequestParam Long appeal,
                                         @RequestParam(required = false) String type,
                                         @RequestParam(required = false) String district,
                                         @RequestParam(required = false) String topic,
                                         @RequestParam(required = false) String text,
                                         @RequestParam(required = false) ArrayList<MultipartFile> files) {

        try {
            appealService.editAppeal(token, appeal, type, district, topic, text, files);
            return ResponseEntity.ok("Обращение изменено");
        } catch (Exception e ) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }



    //admins
    @GetMapping(value = "admin/{appealID}")
    public ResponseEntity getAnyOneAppeal(@PathVariable Long appealID) {
        try {
            return ResponseEntity.ok().body(AppealModel.toModel(appealService.getAppeal(appealID), true));
        } catch (Exception e ) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @GetMapping("/admin")
    public ResponseEntity getAllAppeals(@RequestParam(required = false) Boolean answered,
                                        @RequestParam(required = false, defaultValue = "") String find,
                                        @RequestParam(required = false, defaultValue = "") String type,
                                        @RequestParam(required = false, defaultValue = "") String district,
                                        @RequestParam(required = false, defaultValue = "") String topic,
                                        @RequestParam(required = false) Integer page,
                                        @RequestParam(required = false) Integer count) {
        try {
            List<Appeal>  appeals = appealService.getFiltredAllAppeals(answered, find, type, topic, district);

            if (page != null && count != null)
                return ResponseEntity.ok(AppealModel.toModel(appealService.doPagination(appeals, page, count), true));
            return ResponseEntity.ok(AppealModel.toModel(appeals, true));
        } catch (Exception e ) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @PatchMapping(value = "admin/{appealID}")
    public ResponseEntity setAnswerOnAppeal(@PathVariable Long appealID,
                                            @RequestParam(required = false) Long id,
                                            @RequestParam(required = false) String response,
                                            @RequestParam(required = false) Boolean frequent) {
        try {
            appealService.setAnswer(appealID, id, response, frequent);
            return ResponseEntity.ok("Ответ на обращение записан");
        } catch (Exception e ) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }
}