package com.thang.bookservice.query.projection;

import com.thang.bookservice.command.data.Book;
import com.thang.bookservice.command.data.BookRepository;
import com.thang.bookservice.query.model.BookResponseModel;
import com.thang.bookservice.query.queries.GetAllBooksQuery;
import com.thang.bookservice.query.queries.GetBookQuery;
import com.thang.commonservice.model.BookResponseCommonModel;
import com.thang.commonservice.query.GetDetailsBookQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookProjection {
    @Autowired
    private BookRepository bookRepository;

    @QueryHandler
    public BookResponseModel handle(GetBookQuery getBookQuery) {
        BookResponseModel model = new BookResponseModel();
        Book book = bookRepository.findById(getBookQuery.getBookId()).get();
        BeanUtils.copyProperties(book, model);
        return model;
    }

    @QueryHandler
    public BookResponseCommonModel handle(GetDetailsBookQuery getDetailsBookQuery) {
        BookResponseCommonModel model = new BookResponseCommonModel();
        Book book = bookRepository.findById(getDetailsBookQuery.getBookId()).get();
        BeanUtils.copyProperties(book,model);
        return model;
    }

    @QueryHandler
    public List<BookResponseModel> handle(GetAllBooksQuery getAllBooksQuery) {
        List<Book> listEntity = bookRepository.findAll();
        List<BookResponseModel> listBook = new ArrayList<>();
        listEntity.forEach(e -> {
            BookResponseModel model = new BookResponseModel();
            BeanUtils.copyProperties(e,model);
            listBook.add(model);
        });
        return listBook;

    }

}
