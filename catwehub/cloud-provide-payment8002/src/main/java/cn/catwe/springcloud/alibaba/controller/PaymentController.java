package cn.catwe.springcloud.alibaba.controller;

import cn.catwe.springcloud.alibaba.entities.CommonResult;
import cn.catwe.springcloud.alibaba.entities.Payment;
import cn.catwe.springcloud.alibaba.service.PaymentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class PaymentController {
    @Resource
    private PaymentService paymentService;
    @Value("${server.port}")
    private String serverPort;
    @PostMapping("/payment/create")
    public CommonResult create(@RequestBody Payment payment){
        int result = paymentService.create(payment);
        if (result >0) {
            return new CommonResult(200,"插入数据库成功,serverPort: "+serverPort,null);
        }else {
            return new CommonResult(444,"插入数据库失败",null);
        }
    };
    @GetMapping("/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id){
        Payment payment = paymentService.getPaymentById(id);
        if (payment != null) {
            return new CommonResult(200,"查询成功,serverPort: "+serverPort,payment);
        }else {
            return new CommonResult(444,"无对应记录，查询的ID是："+id,null);
        }
    }

    @GetMapping(value = "/payment/lb")
    public String getPaymentlb(){
        return serverPort;
    }
}
