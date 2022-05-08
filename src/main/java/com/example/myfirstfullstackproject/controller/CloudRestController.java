package com.example.myfirstfullstackproject.controller;

import com.example.myfirstfullstackproject.controller.message.Message;
import com.example.myfirstfullstackproject.model.Category;
import com.example.myfirstfullstackproject.service.CloudService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.example.myfirstfullstackproject.controller.message.BusinessStatus.*;
import static com.example.myfirstfullstackproject.controller.message.Message.getMessageFormatted;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class CloudRestController {

    private final CloudService cloudService;

    @GetMapping(value ="/clouds", produces = "application/json")
    public ResponseEntity<Message> showCloudList(@RequestParam Optional<Category> category) {
        if(category.isEmpty()) {
            return new ResponseEntity<>(Message.builder()
                    .status(ALL_CLOUDS_RETURNED)
                    .message(getMessageFormatted(ALL_CLOUDS_RETURNED.getStatusCode(), ALL_CLOUDS_RETURNED.getDescription()))
                    .data(cloudService.getAllClouds()).build(), HttpStatus.OK);
        }

        return new ResponseEntity<>(Message.builder()
                .status(CLOUDS_BY_CATEGORY_RETURNED)
                .message(getMessageFormatted(CLOUDS_BY_CATEGORY_RETURNED.getStatusCode(), CLOUDS_BY_CATEGORY_RETURNED.getDescription()))
                .data(cloudService.getCloudsByCategory(Category.valueOf(category.get().name()))).build(), HttpStatus.OK);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    private ResponseEntity<Message> handleInvalidCategoryRequestException() {
        return new ResponseEntity<>(Message.builder()
                .status(ILLEGAL_CATEGORY_REQUEST)
                .message(getMessageFormatted(ILLEGAL_CATEGORY_REQUEST.getStatusCode(), ILLEGAL_CATEGORY_REQUEST.getDescription()))
                .build(), HttpStatus.BAD_REQUEST);
    }

}