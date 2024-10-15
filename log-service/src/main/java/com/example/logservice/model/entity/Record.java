package com.example.logservice.model.entity;

import com.example.logservice.model.enums.Level;

import java.util.Date;

public class Record {

    private Date savedAt;
    private Date generatedAt;
    private Level level;
    private String message;
    private String loggerName;

}
