package com.thang.borrowingservice.command.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DeleteBorrowCommand {
    @TargetAggregateIdentifier
    private String id;
}
