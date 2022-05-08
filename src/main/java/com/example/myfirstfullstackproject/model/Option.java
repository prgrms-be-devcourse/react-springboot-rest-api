package com.example.myfirstfullstackproject.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Option {

    private long optionId;

    @NotNull(message = "cloudId should not be empty.")
    private long cloudId;

    @NotNull(message = "title should not be empty.")
    private String title;

    @NotNull(message = "detail should not be empty.")
    private String detail;

}
