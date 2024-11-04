package org.thuvien.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookAddDTO {
    private String title;
    private String author;
    private String publisher;
    private Date publishedDate;
    private String description;
    private int quantity;
    private String category;


}
