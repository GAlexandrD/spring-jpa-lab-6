package com.example.lab6.Task.utils;

import com.example.lab6.Task.errors.DateParseException;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DateParser {
    public Date parseDateStr(String dateStr){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = formatter.parse(dateStr);
        } catch (ParseException e) {
            throw new DateParseException(dateStr);
        }
        return date;
    }

    public String dateToStr(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }
}
