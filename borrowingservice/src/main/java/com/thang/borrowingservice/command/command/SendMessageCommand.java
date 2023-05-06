package com.thang.borrowingservice.command.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SendMessageCommand {
    @TargetAggregateIdentifier
    private String id;
    private String employeeId;
    private String message;
}
