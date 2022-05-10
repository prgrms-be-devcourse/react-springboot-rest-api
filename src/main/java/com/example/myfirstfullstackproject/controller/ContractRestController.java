package com.example.myfirstfullstackproject.controller;

import com.example.myfirstfullstackproject.controller.dto.CreateContractRequest;
import com.example.myfirstfullstackproject.controller.message.Message;
import com.example.myfirstfullstackproject.service.ContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.myfirstfullstackproject.controller.message.BusinessStatus.CONTRACT_CREATED;
import static com.example.myfirstfullstackproject.controller.message.BusinessStatus.NOT_ENOUGH_CONTRACT_FIELDS;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ContractRestController {

    private final ContractService contractService;

    @PostMapping(value = "/contracts", consumes = "application/json")
    public ResponseEntity<Message> createContract(@RequestBody CreateContractRequest request) {
        if (request.getName().isBlank() || request.getEmail().isBlank()) {
            return new ResponseEntity<>(Message.builder()
                    .status(NOT_ENOUGH_CONTRACT_FIELDS)
                    .message(NOT_ENOUGH_CONTRACT_FIELDS.getDescription()).build(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(Message.builder()
                .status(CONTRACT_CREATED)
                .message(CONTRACT_CREATED.getDescription())
                .data(contractService.createContract(
                        request.getCloudId(),
                        request.getEmail(),
                        request.getName(),
                        request.getOptionList()
                )).build(), HttpStatus.OK);
    }

}
