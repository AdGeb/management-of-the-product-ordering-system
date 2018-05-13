package com.app.service;

import com.app.dao.generic.DbTables;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface ErrorService {
    void addErrror(DbTables table, String message, LocalDateTime date);
}
