package com.example.helloworldtest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DepartmentAppTests {

    @Test
    public void contextLoads() {
        System.out.println("Context load check.");
    }

    @Test
    public void testHelloEndpoint() {
        System.out.println("Testing Department app done");
    }
}