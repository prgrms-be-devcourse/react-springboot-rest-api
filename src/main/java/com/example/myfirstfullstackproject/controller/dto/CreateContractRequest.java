package com.example.myfirstfullstackproject.controller.dto;

import com.example.myfirstfullstackproject.model.Option;
import lombok.Getter;

import java.util.List;

@Getter
public class CreateContractRequest {

    private String name;
    private String email;
    private long cloudId;
    private List<Option> optionList;

}
