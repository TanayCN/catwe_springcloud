package cn.catwe.springcloud.alibaba.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;


@Service
public class PaymentServiceImpl implements PaymentService{

    @Override
    public String PaymentInfo_Ok(Integer id) {
        return "线程池："+Thread.currentThread().getName()+"    PaymentInfo_Ok,id:"+id;
    }

    @HystrixCommand(fallbackMethod = "PaymentInfo_TimeOutHandler",commandProperties ={@HystrixProperty(name ="execution.isolation.thread.timeoutInMilliseconds" ,value = "５000")})
    @Override
    public String PaymentInfo_TimeOut(Integer id) {

        int timeNumber =3;
        try {
            TimeUnit.SECONDS.sleep(timeNumber);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池："+Thread.currentThread().getName()+"    PaymentInfo_TimeOut,id:"+id;
    }
    public String PaymentInfo_TimeOutHandler(Integer id){

        return "线程池："+Thread.currentThread().getName()+"8001系统繁忙";
    }

    //服务熔断
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback",commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),  //是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),   //请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"),  //时间范围
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60"), //失败率达到多少后跳闸
    })
    public String paymentCircuitBreaker(@PathVariable("id") Integer id){
        if (id < 0){
            throw new RuntimeException("*****id 不能负数");
        }
        String serialNumber = IdUtil.simpleUUID();

        return Thread.currentThread().getName()+"\t"+"调用成功,流水号："+serialNumber;
    }
    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id){
        return "id 不能负数，请稍候再试,(┬＿┬)/~~     id: " +id;
    }


}
