package cn.catwe.springcloud.alibaba.controller;

import cn.catwe.springcloud.alibaba.service.OrderHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@DefaultProperties(defaultFallback = "payment_Global_FallbackMethod")
public class OrderHystrixController {
    @Resource
    private OrderHystrixService orderHystrixService;

    @GetMapping("/consumer/payment/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id){
        String result = orderHystrixService.PaymentInfo_Ok(id);
        log.info("*******result:"+result);
        return result;
    }
    //@HystrixCommand(fallbackMethod ="paymentTimeOutFallbackMethod",commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "1500")})
    @HystrixCommand
    @GetMapping("/consumer/payment/hystrix/timeout/{id}")
    public String paymentInfo_TimeOut(@PathVariable("id") Integer id){
        int age = 10/0;
        String result = orderHystrixService.PaymentInfo_TimeOut(id);
        log.info("*******result:"+result);
        return result;
    }
    public String paymentTimeOutFallbackMethod(@PathVariable("id") Integer id){
        return "客户端提示:对方系统繁忙";

    }
    //下面是全局fallback
    public String payment_Global_FallbackMethod(){
        return "Global异常处理，请稍后再试！";
    }

}


