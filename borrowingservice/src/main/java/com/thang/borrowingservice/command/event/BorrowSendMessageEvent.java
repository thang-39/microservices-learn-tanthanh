package com.thang.borrowingservice.command.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BorrowSendMessageEvent {
    private String id;
    private String employeeId;
    private String message;
}
