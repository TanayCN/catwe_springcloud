package cn.catwe.springcloud.alibaba.service;

import org.springframework.web.bind.annotation.PathVariable;

public interface PaymentService {
    public String PaymentInfo_Ok(Integer id);

    public String PaymentInfo_TimeOut(Integer id);

    public String paymentCircuitBreaker(@PathVariable("id") Integer id);
}
