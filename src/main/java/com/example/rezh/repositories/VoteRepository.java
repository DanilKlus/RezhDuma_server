package com.example.rezh.repositories;


import com.example.rezh.entities.votes.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    List<Vote> findAllBySurvey(boolean survey);

    @Query(" SELECT c FROM Vote c WHERE c.topic like :text ")
    List<Vote> findVotes(@Param("text") String text);

    @Query(" SELECT c FROM Vote c WHERE c.topic like :text AND c.survey = false ")
    List<Vote> findVotesWithWord(@Param("text") String text);

    @Query(" SELECT c FROM Vote c WHERE c.topic like :text AND c.survey = true ")
    List<Vote> findSurveysWithWord(@Param("text") String text);
}
