package top.imwonder.simpleftpserver;

import java.util.Date;
import java.util.Locale;

public class StringFomatTest {
    public static void main(String[] args) {
        Date time = new Date();
        System.out.println(String.format(new Locale("en")," %tD %te %th ", time,time,time));
    }
}