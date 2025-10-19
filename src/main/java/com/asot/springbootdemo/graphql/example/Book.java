package com.asot.springbootdemo.graphql.example;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Book {
    private Long id;
    private String title;
    private String author;

}
