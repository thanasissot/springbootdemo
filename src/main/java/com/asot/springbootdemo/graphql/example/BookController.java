package com.asot.springbootdemo.graphql.example;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {
    private final BookRepository repository;

    @QueryMapping
    public List<Book> books() {
        return repository.findAll();
    }

    @QueryMapping
    public Book bookById(@Argument Long id) {
        return repository.findById(id).orElse(null);
    }

    @MutationMapping
    public Book addBook(@Argument String title, @Argument String author) {
        Book book = new Book(null, title, author);
        return repository.save(book);
    }

    @MutationMapping
    public Boolean deleteBook(@Argument Long id) {
        return repository.deleteById(id);
    }
}
