package com.example.LunchMatch.controllers;

import com.example.LunchMatch.DTO.LunchMatchRequestDTO;
import com.example.LunchMatch.DTO.ViewLunchMatchDTO;
import com.example.LunchMatch.models.LunchMatch;
import com.example.LunchMatch.models.User;
import com.example.LunchMatch.services.LunchMatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("lunch-matches")
@RequiredArgsConstructor
public class LunchMatchController {
    @Autowired
    private LunchMatchService lunchMatchService;

    @PostMapping(value = "/send-request")
    public ResponseEntity<String> sendMatchRequest(
            @RequestBody LunchMatchRequestDTO lunchMatchDTO,
            Authentication authentication
            ) {

        lunchMatchService.sendMatchRequest(lunchMatchDTO, authentication);
        return ResponseEntity.ok("lunch match request sent successfully");

    }

    @PutMapping(value = "/sent-requests/{matchId}/accept")
    public ResponseEntity<String> acceptLunchMatch(
            @PathVariable("matchId") Integer matchId,
            Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        lunchMatchService.acceptRequest(matchId, user);
        return ResponseEntity.ok("match request accepted");
    }

    @PutMapping(value = "/received-requests/{matchId}/deny")
    public ResponseEntity<String> denyLunchMatch(
            @PathVariable Integer matchId,
            Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        lunchMatchService.denyRequest(matchId, user);
        return ResponseEntity.ok("match request denied");
    }


    //Optional Body: You can pass a body to ResponseEntity.ok(), which will be included in the response. If no body is provided, it will return an empty response body with a status of 200
    @GetMapping(value = "/sent-requests")
    public ResponseEntity<List<ViewLunchMatchDTO>> getSentRequests(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        List<ViewLunchMatchDTO> sentMatches = lunchMatchService.getSentRequests(user);
        return ResponseEntity.ok(sentMatches);
    }

    @GetMapping(value = "/received-requests")
    public ResponseEntity<List<ViewLunchMatchDTO>> getReceivedRequests(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        List<ViewLunchMatchDTO> receivedMatches = lunchMatchService.getReceivedRequests(user);
        return ResponseEntity.ok(receivedMatches);
    }

}
