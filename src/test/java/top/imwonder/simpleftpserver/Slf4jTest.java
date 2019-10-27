package top.imwonder.simpleftpserver;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Slf4jTest {

    @Test
    public void testLog() {
        String testInfo = "Free flying flowers are like dreams";
        log.info("The test info is :{}", testInfo);
    }
}