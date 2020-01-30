package com.example.mychatappnetty.util;

import com.github.wujun234.uid.UidGenerator;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class UUIDUtil {

    private static UidGenerator generator;

    @Resource
    public void setGenerator(UidGenerator cachedUidGenerator){
        UUIDUtil.generator = cachedUidGenerator;
    }

    public static String generateUUID() {
        return String.valueOf(generator.getUID());
    }

    /*public static void main(String[] args) {
        System.out.println(UUIDUtil.generateUUID());
    }*/
}
