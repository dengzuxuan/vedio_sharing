package com.vediosharing.backend;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @ClassName log4jTest
 * @Description TODO
 * @Author Colin
 * @Date 2023/11/4 18:32
 * @Version 1.0
 */
@SpringBootTest
public class log4jTest {
    public static final Logger LOGGER = LoggerFactory.getLogger(Slf4j.class);
    @Test
    void testlog(){
        LOGGER.error("error");
        LOGGER.warn("warning");
        LOGGER.info("info");
        LOGGER.debug("debug");
        LOGGER.trace("trace");

        String name = "dengzx";
        LOGGER.info("用户：{}",name);
        try {
            int i=1/0;
        }catch (Exception e){
            LOGGER.error("出现异常",e);
        }

    }
}
