package org.example.gateway;

import org.example.auth.sdk.EnableAceAuthClient;
import org.example.gateway.utils.DBLog;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author ace
 * @create 2018/3/12.
 */
@SpringCloudApplication
@EnableAceAuthClient
//TODO 切换为webclient
@EnableFeignClients({"org.example.auth.sdk.feign"})
public class GatewayServerBootstrap {
    public static void main(String[] args) {
        DBLog.getInstance().start();
        SpringApplication.run(GatewayServerBootstrap.class, args);
    }
}
