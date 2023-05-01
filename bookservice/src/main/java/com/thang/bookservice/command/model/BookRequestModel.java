package com.thang.bookservice.command.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookRequestModel {

    private String bookId;
    private String name;
    private String author;
    private Boolean isReady;
}