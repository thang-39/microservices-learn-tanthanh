package com.thang.bookservice.query.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookResponseModel {
    private String bookId;
    private String name;
    private String author;
    private Boolean isReady;
}
