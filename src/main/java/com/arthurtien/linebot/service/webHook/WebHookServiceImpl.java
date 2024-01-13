package com.arthurtien.linebot.service.webHook;

import com.arthurtien.linebot.dto.ReplyMessage;
import com.arthurtien.linebot.dto.ReplyMessageResponse;
import com.arthurtien.linebot.dto.webHook.Events;
import com.arthurtien.linebot.dto.webHook.WebHookEventObjects;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class WebHookServiceImpl implements WebHookService {

    @Override
    public void process(WebHookEventObjects webHookEventObjects) {
        log.info(webHookEventObjects.toString());

        ReplyMessageResponse replyMessageResponse = getReplyMessageResponse(webHookEventObjects.getEvents().get(0));

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer kh/DB2NTWeH8tUmNOHi04XMPqaMTIOuGyR18CK4xHVjNqG0vOksVznNeuoL3bcTK9JKJLVG+rCDil6PMwfyjeoJ/WXf5hoNGxLNZaifQ//+0KOkAFrQ6yQw6PgvwWb81GR7dZSTpxRfvQZWivE+BuQdB04t89/1O/w1cDnyilFU=");
        HttpEntity<ReplyMessageResponse> httpEntity = new HttpEntity<>(replyMessageResponse, httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForEntity(
                "https://api.line.me/v2/bot/message/reply",
                httpEntity,
                JSONPObject.class);
    }

    private ReplyMessageResponse getReplyMessageResponse(Events events) {
        ReplyMessageResponse ReplyMessageResponse = new ReplyMessageResponse();
        ReplyMessageResponse.setReplyToken(events.getReplyToken());
        List<ReplyMessage> replyMessageList = new ArrayList<>();
        replyMessageList.add(new ReplyMessage("text", events.getMessage().getText()));
        ReplyMessageResponse.setMessages(replyMessageList);
        return ReplyMessageResponse;
    }

    @Override
    public String hmac(String algorithm, String httpRequestBody, String channelSecret) throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec key = new SecretKeySpec(channelSecret.getBytes(), algorithm);
        Mac mac = Mac.getInstance(algorithm);
        mac.init(key);
        byte[] source = httpRequestBody.getBytes(StandardCharsets.UTF_8);
        return Base64.encodeBase64String(mac.doFinal(source));
    }
}
