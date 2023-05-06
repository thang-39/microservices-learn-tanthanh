package com.thang.borrowingservice.query.projection;

import com.thang.borrowingservice.command.data.BorrowRepository;
import com.thang.borrowingservice.command.data.Borrowing;
import com.thang.borrowingservice.query.model.BorrowingResponseModel;
import com.thang.borrowingservice.query.queries.GetAllBorrowingQuery;
import com.thang.borrowingservice.query.queries.GetListBorrowingByEmployeeQuery;
import com.thang.commonservice.model.BookResponseCommonModel;
import com.thang.commonservice.model.EmployeeResponseCommonModel;
import com.thang.commonservice.query.GetDetailsBookQuery;
import com.thang.commonservice.query.GetDetailsEmployeeQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BorrowingProjection {
    @Autowired
    private BorrowRepository borrowRepository;

    @Autowired
    private QueryGateway queryGateway;

    @QueryHandler
    public List<BorrowingResponseModel> handle(GetListBorrowingByEmployeeQuery query) {
        List<BorrowingResponseModel> modelList = new ArrayList<>();
        List<Borrowing> listEntity = borrowRepository.findByEmployeeIdAndReturnDateIsNull(query.getEmployeeId());
        listEntity.forEach( e -> {
            BorrowingResponseModel model = new BorrowingResponseModel();
            BeanUtils.copyProperties(e,model);
            model.setNameBook(queryGateway
                    .query(new GetDetailsBookQuery(model.getBookId()),
                            ResponseTypes.instanceOf(BookResponseCommonModel.class))
                    .join().getName());
            EmployeeResponseCommonModel employeeModel = queryGateway
                    .query(new GetDetailsEmployeeQuery(model.getEmployeeId()),
                            ResponseTypes.instanceOf(EmployeeResponseCommonModel.class))
                    .join();
            model.setNameEmployee(employeeModel.getFirstName()+ " " + employeeModel.getLastName());


            modelList.add(model);
        });
        return modelList;
    }

    @QueryHandler
    public List<BorrowingResponseModel> handle(GetAllBorrowingQuery query) {
        List<BorrowingResponseModel> modelList = new ArrayList<>();
        List<Borrowing> entityList = borrowRepository.findAll();
        entityList.forEach( e -> {
            BorrowingResponseModel model = new BorrowingResponseModel();
            BeanUtils.copyProperties(e, model);
            model.setNameBook(queryGateway
                    .query(new GetDetailsBookQuery(model.getBookId()),
                            ResponseTypes.instanceOf(BookResponseCommonModel.class))
                    .join().getName());
            EmployeeResponseCommonModel employeeModel = queryGateway
                    .query(new GetDetailsEmployeeQuery(model.getEmployeeId()),
                            ResponseTypes.instanceOf(EmployeeResponseCommonModel.class))
                    .join();
            model.setNameEmployee(employeeModel.getFirstName()+ " " + employeeModel.getLastName());
            modelList.add(model);
        });
        return modelList;
    }
}
