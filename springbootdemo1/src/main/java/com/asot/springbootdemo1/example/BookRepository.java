package com.asot.springbootdemo1.example;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class BookRepository {
    private final Map<Long, Book> books = new LinkedHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(0);

    @PostConstruct
    public void init() {
        // seed some data
        save(new Book(null, "Clean Code", "Robert C. Martin"));
        save(new Book(null, "Effective Java", "Joshua Bloch"));
    }

    public List<Book> findAll() {
        return new ArrayList<>(books.values());
    }

    public Optional<Book> findById(Long id) {
        return Optional.ofNullable(books.get(id));
    }

    public Book save(Book book) {
        if (book.getId() == null) {
            book.setId(idGenerator.incrementAndGet());
        }
        books.put(book.getId(), book);
        return book;
    }

    public boolean deleteById(Long id) {
        return books.remove(id) != null;
    }
}
