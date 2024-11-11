package com.example.LunchMatch.services;

import com.example.LunchMatch.DTO.LunchMatchRequestDTO;
import com.example.LunchMatch.DTO.ViewLunchMatchDTO;
import com.example.LunchMatch.models.LunchMatch;
import com.example.LunchMatch.models.MatchStatus;
import com.example.LunchMatch.repositories.LunchMatchRepository;
import com.example.LunchMatch.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import com.example.LunchMatch.models.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LunchMatchService {
    @Autowired
    private LunchMatchRepository lunchMatchRepository;
    @Autowired
    private UserRepository userRepository;
    //sendRequest
    public void sendMatchRequest(LunchMatchRequestDTO lunchMatchDTO, Authentication authentication) {
        User sender = (User) authentication.getPrincipal();
        Integer receiverId = lunchMatchDTO.getReceiverId();
        LocalDateTime requestDate = lunchMatchDTO.getRequestDate();
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new RuntimeException("sender not found"));


        if(sender != null && receiver != null) {
            LunchMatch matchRequest = LunchMatch.builder()
                    .date(requestDate)
                    .sender(sender)
                    .receiver(receiver)
                    .status(MatchStatus.PENDING)
                    .build();
            lunchMatchRepository.save(matchRequest);

        }

    }
    //AcceptRequest
    public void acceptRequest(Integer lunchMatchId, User user) {
        LunchMatch lunchMatch = lunchMatchRepository.findById(lunchMatchId)
                .orElseThrow(() -> new RuntimeException("match not found"));
        if(lunchMatch.getReceiver().equals(user)) {
            lunchMatch.setStatus(MatchStatus.ACCEPTED);
            lunchMatchRepository.save(lunchMatch);
        }

    }
    //DeclineRequest
    public void denyRequest(Integer lunchMatchId, User user) {
        LunchMatch lunchMatch = lunchMatchRepository.findById(lunchMatchId)
                .orElseThrow(() -> new RuntimeException("match not found"));
        if(lunchMatch.getReceiver().equals(user)) {
            lunchMatchRepository.delete(lunchMatch);
        }

    }
    //getSentRequests
    public List<ViewLunchMatchDTO> getSentRequests(User user) {
        List<LunchMatch> sentLunchMatches = lunchMatchRepository.findBySenderId(user.getId());
        return sentLunchMatches.stream().map(lunchMatch -> {
            ViewLunchMatchDTO lunchMatchDTO = ViewLunchMatchDTO.builder()
                    .matchId(lunchMatch.getId())
                    .receiverId(user.getId())
                    .receiverUsername(user.fullName())
                    .senderId(lunchMatch.getSender().getId())
                    .senderUsername(lunchMatch.getSender().fullName())
                    .status(String.valueOf(lunchMatch.getStatus()))
                    .build();
            return lunchMatchDTO;
        }).toList();

    }

    //getReceivedRequests
    public List<ViewLunchMatchDTO> getReceivedRequests(User user) {
        List<LunchMatch> receivedRequests = lunchMatchRepository.findByReceiverId(user.getId());
        return receivedRequests.stream().map(lunchMatch -> {
            ViewLunchMatchDTO lunchMatchDTO = ViewLunchMatchDTO.builder()
                    .matchId(lunchMatch.getId())
                    .receiverId(user.getId())
                    .receiverUsername(user.fullName())
                    .senderId(lunchMatch.getSender().getId())
                    .senderUsername(lunchMatch.getSender().fullName())
                    .status(String.valueOf(lunchMatch.getStatus()))
                    .build();
            return lunchMatchDTO;
        }).toList();

    }
}
