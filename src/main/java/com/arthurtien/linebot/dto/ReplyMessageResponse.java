package com.arthurtien.linebot.dto;

import lombok.Data;

import java.util.List;

@Data
public class ReplyMessageResponse {
    private String replyToken;
    private List<ReplyMessage> messages;
}
