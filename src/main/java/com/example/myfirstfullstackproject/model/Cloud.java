package com.example.myfirstfullstackproject.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class Cloud {

    private long cloudId;

    @NotNull
    private String cloudName;

    @NotNull
    private Category category;

    @NotNull
    private long price;

    @NotNull
    private int users;

    @NotNull
    private int storage;

    private String img;

    @NotNull
    private LocalDateTime createdAt;

    @NotNull
    private LocalDateTime updatedAt;

    @JsonCreator
    public Cloud(@JsonProperty("cloudId") long cloudId,
                 @JsonProperty("cloudName") String cloudName,
                 @JsonProperty("category") Category category,
                 @JsonProperty("price") long price,
                 @JsonProperty("users") int users,
                 @JsonProperty("storage") int storage,
                 @JsonProperty("img") String img,
                 @JsonProperty("createdAt") LocalDateTime createdAt,
                 @JsonProperty("updatedAt") LocalDateTime updatedAt) {

        this.cloudId = cloudId;
        this.cloudName = cloudName;
        this.category = category;
        this.price = price;
        this.users = users;
        this.storage = storage;
        this.img = img;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Cloud(String cloudName, Category category, long price, int users, int storage, String img, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.cloudName = cloudName;
        this.category = category;
        this.price = price;
        this.users = users;
        this.storage = storage;
        this.img = img;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}
