package com.thang.borrowingservice.command.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.Date;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateBorrowCommand {

    @TargetAggregateIdentifier
    private String id;

    private String bookId;
    private String employeeId;

    private Date borrowingDate;
}
