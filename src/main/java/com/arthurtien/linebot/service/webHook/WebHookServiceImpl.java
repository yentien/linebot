package com.arthurtien.linebot.service.webHook;

import com.arthurtien.linebot.dto.webHook.WebHookEventObjects;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Slf4j
@Service
public class WebHookServiceImpl implements WebHookService{

    @Override
    public void process(WebHookEventObjects webHookEventObjects) {
        log.info(webHookEventObjects.toString());
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
