package cn.catwe.springcloud.alibaba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class NacosOrderMain83 {
    public static void main(String[] args) {
        SpringApplication.run(NacosOrderMain83.class,args);
    }
}
