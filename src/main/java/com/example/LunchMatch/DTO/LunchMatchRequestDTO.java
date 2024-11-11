package com.example.LunchMatch.DTO;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
public class LunchMatchRequestDTO {
    private Integer receiverId;
    private LocalDateTime requestDate;
    public Integer getReceiverId() {
        return receiverId;
    }

    public LocalDateTime getRequestDate() {
        return requestDate;
    }
}
