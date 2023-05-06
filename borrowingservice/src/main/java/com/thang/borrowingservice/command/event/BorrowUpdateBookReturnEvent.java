package com.thang.borrowingservice.command.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BorrowUpdateBookReturnEvent {
    private String id;
    private String bookId;
    private String employee;
    private Date returnDate;

}
