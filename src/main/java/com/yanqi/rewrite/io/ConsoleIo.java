package com.yanqi.rewrite.io;

import java.util.Date;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * @author YQQ
 * @program: People's Daily
 * @description:
 * @date 2021-10-11 23:09:20
 */
public class ConsoleIo {
    public static Date getDateFromConsole(){
        String next = "";
        String reg_yyyyMMdd = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})(((0[13578]|1[02])(0[1-9]|[12][0-9]|3[01]))" +
                "|((0[469]|11)(0[1-9]|[12][0-9]|30))|(02(0[1-9]|[1][0-9]|2[0-8]))))" +
                "|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))0229)";
        Pattern compile = Pattern.compile(reg_yyyyMMdd);
        while (!compile.matcher(next).matches()){
            if (next != ""){
                System.out.println("输入的日期不正确，请重新输入");
            }
            Scanner scanner = new Scanner(System.in);
            System.out.println("请输入日期（格式为yyyymmdd）：");
            next = scanner.next();
        }
        StringBuffer date = new StringBuffer(next);
        date.insert(4,'-').insert(7,'-');
        System.out.println(date.toString());
        return new Date(String.valueOf(date));
    }
}
