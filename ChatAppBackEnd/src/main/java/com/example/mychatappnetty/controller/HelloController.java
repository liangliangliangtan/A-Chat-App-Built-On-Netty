package com.example.mychatappnetty.controller;

import com.example.mychatappnetty.util.UUIDUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class HelloController {

  @GetMapping(value = "/hello")
  public String hello() {
    return "hello,Remote SpringBoot Application!";
  }


  @GetMapping("/uidGenerator")
  public String UidGenerator() {
    return UUIDUtil.generateUUID();
  }
}
