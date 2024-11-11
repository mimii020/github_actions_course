package com.example.LunchMatch.repositories;

import com.example.LunchMatch.models.LunchMatch;
import com.example.LunchMatch.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LunchMatchRepository extends JpaRepository<LunchMatch, Integer> {
    List<LunchMatch> findBySenderId(Integer senderId);
    List<LunchMatch> findByReceiverId(Integer receiverId);

}
