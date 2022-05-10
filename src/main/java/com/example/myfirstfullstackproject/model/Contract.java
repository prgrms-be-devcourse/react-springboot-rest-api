package com.example.myfirstfullstackproject.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Contract {

    private long contractId;

    @NotNull
    private long cloudId;

    @NotNull
    private String email;

    @NotNull
    private String userName;

    @NotNull
    private ContractStatus contractStatus;

    @NotNull
    private List<Option> options;

    @NotNull
    private LocalDateTime createdAt;

    @NotNull
    private LocalDateTime updatedAt;

}
