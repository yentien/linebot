package com.arthurtien.linebot.dto.webHook;

import lombok.Data;

@Data
public class Message {
    private String type;
    private String id;
    private String quoteToken;
    private String text;
}
