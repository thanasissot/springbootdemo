package com.asot.springbootdemo1.example;

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
