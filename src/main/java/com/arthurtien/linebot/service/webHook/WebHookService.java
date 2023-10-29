package com.arthurtien.linebot.service.webHook;

import com.arthurtien.linebot.dto.webHook.WebHookEventObjects;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface WebHookService {
    void process(WebHookEventObjects webHookEventObjects);
    // hmac解碼
    String hmac(String algorithm, String httpRequestBody, String channelSecret) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException;
}
