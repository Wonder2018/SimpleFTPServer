package top.imwonder.simpleftpserver;

import java.util.Date;
import java.util.Locale;

import org.junit.Test;

public class StringFomatTest {
    @Test
    public void testStringFomat() {
        Date time = new Date();
        System.out.println(String.format(new Locale("en")," %tD %te %th ", time,time,time));
    }
}