package com.aixn.springboot;

import com.aixn.springboot.utils.JwtUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;



@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootApplicationTests {

    @Test
    public void JETTEST() throws Exception {
        System.out.println(JwtUtil.createToken("123"));
        String taken = JwtUtil.createToken("123");
        System.out.println(JwtUtil.verifyToken(taken));
    }



}
