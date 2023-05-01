package com.thang.bookservice.command.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class DeleteBookCommand {

    @TargetAggregateIdentifier
    private String bookId;

}
