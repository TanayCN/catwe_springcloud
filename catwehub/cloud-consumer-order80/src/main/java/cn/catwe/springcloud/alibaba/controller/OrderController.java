package cn.catwe.springcloud.alibaba.controller;

import cn.catwe.springcloud.alibaba.entities.CommonResult;
import cn.catwe.springcloud.alibaba.entities.Payment;
import cn.catwe.springcloud.alibaba.lb.LoadBalancer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

@RestController
@Slf4j
public class OrderController {
//    public static  final String PAYMENT_URL="http://localhost:8001";
    public static  final String PAYMENT_URL="http://CLOUD-PAYMENT-SERVICE";
    @Resource
    private LoadBalancer loadBalancer;
    @Resource
    private DiscoveryClient discoveryClient;

    @Resource
    private RestTemplate restTemplate;
    @GetMapping("/consumer/payment/create")
    public CommonResult<Payment> create(Payment payment){
        //return restTemplate.postForObject(PAYMENT_URL+"/payment/create",payment,CommonResult.class);
        return restTemplate.postForEntity(PAYMENT_URL+"/payment/create",payment,CommonResult.class).getBody();
    }
    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id") Long id){
        return  restTemplate.getForObject(PAYMENT_URL+"/payment/get/"+id,CommonResult.class);
    }
    @GetMapping("/consumer/payment/getPayment/{id}")
    public CommonResult<Payment> getPaymentByRest(@PathVariable("id") Long id){
        ResponseEntity<CommonResult> entity = restTemplate.getForEntity(PAYMENT_URL+"/payment/get/"+id,CommonResult.class);
        if (entity.getStatusCode().is2xxSuccessful()) {
            return entity.getBody();
        }else {
            return new CommonResult<>(444,"????????????");
        }
    }
    @GetMapping(value = "/payment/lb")
    public  String getPaymentLB(){
        List<ServiceInstance> instances  = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        if(instances==null || instances.size()<=0){
            return null;
        }
        ServiceInstance serviceInstance = loadBalancer.instances(instances);
        URI uir = serviceInstance.getUri();
        return restTemplate.getForObject(uir+"/payment/lb",String.class);
    }
    // ====================> zipkin+sleuth
    @GetMapping("/consumer/payment/zipkin")
    public String paymentZipkin()
    {
        String result = restTemplate.getForObject("http://localhost:8001"+"/payment/zipkin/", String.class);
        return result;
    }


}
