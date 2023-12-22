package com.qingmu.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Method;
import java.util.concurrent.ThreadPoolExecutor;

@SpringBootTest
class RiggerApplicationTests {

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    @Test
    void contextLoads() {
        int corePoolSize = threadPoolExecutor.getCorePoolSize();
        System.out.println(corePoolSize);
    }

    @Test
    void listAllImportJavaMethods(){
        String reference = "com.qingmu.rigger.RiggerApplication";
        try {
            Class<?> aClass = Class.forName(reference);
            Method[] methods = aClass.getMethods();
            Class<?>[] classes = aClass.getDeclaredClasses();
            for(Class cazz : classes){
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



}
