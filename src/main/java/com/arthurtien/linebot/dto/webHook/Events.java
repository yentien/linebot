package com.arthurtien.linebot.dto.webHook;

import lombok.Data;

@Data
public class Events {
    private String type;
    private WebHookMessage message;
    private String webhookEventId;
    private DeliveryContext deliveryContext;
    private long timestamp;
    private Source source;
    private String replyToken;
    private String mode;
}
