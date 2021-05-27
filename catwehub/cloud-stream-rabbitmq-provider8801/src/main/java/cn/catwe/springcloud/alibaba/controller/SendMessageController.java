package cn.catwe.springcloud.alibaba.controller;

import cn.catwe.springcloud.alibaba.service.IMessageProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class SendMessageController {
    @Resource
    private IMessageProvider iMessageProvider;
    @GetMapping(value = "/sendMessage")
    public String send(){
       return iMessageProvider.send();
    }
}
