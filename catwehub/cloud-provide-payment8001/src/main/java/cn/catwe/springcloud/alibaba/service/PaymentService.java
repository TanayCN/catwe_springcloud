package cn.catwe.springcloud.alibaba.service;

import cn.catwe.springcloud.alibaba.entities.Payment;
import org.apache.ibatis.annotations.Param;

public interface PaymentService {
    public int create(Payment payment);
    public Payment getPaymentById(@Param("id") Long id);
}
