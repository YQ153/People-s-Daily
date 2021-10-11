package com.yanqi.utils;

import com.yanqi.myEnum.DateFormatEnum;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author YQQ
 * @program: People's Daily
 * @description: 对象处理工具
 * @date 2021-09-30 19:24:11
 */
public class DateUtils {
    private SimpleDateFormat simpleDateFormat;
    private DateFormatEnum dateFormatEnum = null;
    private Date date;

    public DateUtils() {
    }

    /**
     * 构造器，传出日期格式（枚举），生成实时日期的对象。
     * @param dateFormatEnum
     */
    public DateUtils(DateFormatEnum dateFormatEnum) {
        this.dateFormatEnum = dateFormatEnum;
        simpleDateFormat = new SimpleDateFormat(dateFormatEnum.getValue());
        date = new Date();
    }

    /**
     * 构造器，传入日期格式（枚举）和日期（object），生成指定日期的对象
     * @param dateFormatEnum
     * @param date
     */
    public DateUtils(DateFormatEnum dateFormatEnum, Date date) {
        this.dateFormatEnum = dateFormatEnum;
        simpleDateFormat = new SimpleDateFormat(dateFormatEnum.getValue());
        this.date = date;
    }

    public DateFormatEnum getDateFormatEnum() {
        return dateFormatEnum;
    }

    public void setDateFormatEnum(DateFormatEnum dateFormatEnum) {
        this.dateFormatEnum = dateFormatEnum;
        simpleDateFormat = new SimpleDateFormat(this.dateFormatEnum.getValue());
    }

    public String getDateWithString(){
        return simpleDateFormat.format(date);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAppointDateWithString(){
        return simpleDateFormat.format(this.date);
    }

    public String getAppointDateWithString(Date date){
        this.date = date;
        return simpleDateFormat.format(this.date);
    }
}
