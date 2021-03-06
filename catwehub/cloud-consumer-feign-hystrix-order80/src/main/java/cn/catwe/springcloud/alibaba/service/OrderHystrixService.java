package cn.catwe.springcloud.alibaba.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value= "CLOUD-PROVIDER-HYSTRIX-PAYMENT")
public interface OrderHystrixService {
    @GetMapping("/payment/hystrix/ok/{id}")
    public String PaymentInfo_Ok(@PathVariable("id") Integer id);
    @GetMapping("/payment/hystrix/timeout/{id}")
    public String PaymentInfo_TimeOut(@PathVariable("id") Integer id);
}
