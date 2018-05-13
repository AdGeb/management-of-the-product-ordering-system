package com.app.service;

import com.app.dao.ErrorsDao;
import com.app.dao.ErrorsDaoImpl;
import com.app.dao.generic.DbTables;
import com.app.model.Errors;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class ErrorServiceImpl implements ErrorService{

    ErrorsDao errorsDao = new ErrorsDaoImpl();

    @Override
    public void addErrror(DbTables table, String message, LocalDateTime date) {
        errorsDao.add(new Errors(date, message));
    }

    public static DbTables findProperTable(String tableName){
        if(tableName != null){
            if(tableName.equals("com.app.model.Category")) return DbTables.Category;
            if(tableName.equals("com.app.model.Country")) return DbTables.Country;
            if(tableName.equals("com.app.model.Customer")) return DbTables.Customer;
            if(tableName.equals("com.app.model.Payment")) return DbTables.Customer_Order;
            if(tableName.equals("com.app.model.Producer")) return DbTables.Producer;
            if(tableName.equals("com.app.model.Product")) return DbTables.Product;
            if(tableName.equals("com.app.model.Shop")) return DbTables.Shop;
            if(tableName.equals("com.app.model.Stock")) return DbTables.Stock;
            if(tableName.equals("com.app.model.Trade")) return DbTables.Trade;
        }
        return null;
    }
}
