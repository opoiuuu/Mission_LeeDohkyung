package com.example.boardproject.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity // db 테이블을 의미
@Data

public class Board {
    // board 스키마 안에 있는 Table안의 Board

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //
    private Integer id;
    private String title;
    private String content;
}