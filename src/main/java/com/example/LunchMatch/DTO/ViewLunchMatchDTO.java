package com.example.LunchMatch.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
public class ViewLunchMatchDTO {
    private Integer matchId;
    private Integer senderId;
    private Integer receiverId;
    private String senderUsername;
    private String receiverUsername;
    private String status;
}
