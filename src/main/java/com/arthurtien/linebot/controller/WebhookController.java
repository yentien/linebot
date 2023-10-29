package com.arthurtien.linebot.controller;

import com.arthurtien.linebot.dto.webHook.WebHookEventObjects;
import com.arthurtien.linebot.service.webHook.WebHookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Slf4j
@RestController
public class WebhookController {

    @Autowired
    public WebhookController(ObjectMapper objectMapper, WebHookService webHookService) {
        this.objectMapper = objectMapper;
        this.webHookService = webHookService;
    }

    private final ObjectMapper objectMapper;
    private final WebHookService webHookService;


    @PostMapping("/linebot/callback")
    public void LineBotWebHook(@RequestBody WebHookEventObjects webHookEventObjects,
                               @RequestHeader("x-line-signature") String xLineSignature) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, JsonProcessingException {

        String channelSecret = "c1cd0030c28bc68ddfc1c71afa60b14f";
        String jsonString = objectMapper.writeValueAsString(webHookEventObjects);
        String hexHash = webHookService.hmac("HmacSHA256", jsonString, channelSecret);
        if (!xLineSignature.equals(hexHash)) {
            log.info("Signature verification failed.");
            return;
        }

        webHookService.process(webHookEventObjects);
    }
}
