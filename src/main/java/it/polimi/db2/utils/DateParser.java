package it.polimi.db2.utils;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateParser {
    private final Date today;

    public DateParser() {
        this.today = new Date(java.util.Calendar.getInstance().getTime().getTime());
    }

    public Date parseDate(String date) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return new Date(format.parse(date).getTime());
    }

    public Date getToday() {
        return today;
    }
}
