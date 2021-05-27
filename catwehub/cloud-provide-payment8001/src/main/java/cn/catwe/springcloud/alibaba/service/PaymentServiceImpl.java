package cn.catwe.springcloud.alibaba.service;

import cn.catwe.springcloud.alibaba.dao.PaymentDao;
import cn.catwe.springcloud.alibaba.entities.Payment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PaymentServiceImpl implements PaymentService{
    @Resource
    private PaymentDao paymentDao;
    public int create(Payment payment){
        return paymentDao.create(payment);
    }
    public Payment getPaymentById(@Param("id") Long id){
        return paymentDao.getPaymentById(id);
    }
}
