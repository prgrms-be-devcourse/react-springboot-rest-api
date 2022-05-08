package com.example.myfirstfullstackproject.controller.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.springframework.lang.Nullable;

@Builder
@Data
public class Message {

    @NonNull
    private BusinessStatus status;

    @NonNull
    private String message;

    @Nullable
    private Object data;

    public Message(BusinessStatus status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static String getMessageFormatted(int statusCode, String description) {
        return String.format("상태코드: %d, 세부내용: %s", statusCode, description);
    }

}
