package com.thang.borrowingservice.command.event;

import com.thang.borrowingservice.command.data.BorrowRepository;
import com.thang.borrowingservice.command.data.Borrowing;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BorrowEventHandler {

    @Autowired
    private BorrowRepository borrowRepository;

    @EventHandler
    public void on(BorrowCreatedEvent event) {
        Borrowing borrowing = new Borrowing();
        BeanUtils.copyProperties(event,borrowing);
        borrowRepository.save(borrowing);
    }

    @EventHandler
    public void on(BorrowDeletedEvent event) {
         borrowRepository.deleteById(event.getId());
    }
}
