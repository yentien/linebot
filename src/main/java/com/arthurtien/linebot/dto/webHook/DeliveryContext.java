package com.arthurtien.linebot.dto.webHook;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DeliveryContext {
    @JsonProperty("isRedelivery")
    private boolean redelivery;
}
