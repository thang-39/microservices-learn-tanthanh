package com.thang.borrowingservice.command.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Message {
    private String employeeId;
    private String message;
}
