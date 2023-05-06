package com.thang.borrowingservice.command.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thang.borrowingservice.command.data.BorrowRepository;
import com.thang.borrowingservice.command.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@EnableBinding(Source.class)
public class BorrowService {
    @Autowired
    private BorrowRepository borrowRepository;

    @Autowired
    private MessageChannel output;

    public void sendMessage(Message message) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(message);
            output.send(MessageBuilder.withPayload(json).build());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public String findIdBorrowing(String employeeId, String bookId) {
        return borrowRepository.findByEmployeeIdAndBookIdAndReturnDateIsNull(employeeId,bookId).getId();
    }
}
