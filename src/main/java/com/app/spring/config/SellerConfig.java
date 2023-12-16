package com.app.spring.config;

import com.app.spring.implementation.WholesalerSellerServiceImpl;
import com.app.spring.service.SellerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SellerConfig {
//    Creates and configures a bean for the wholesaleSellerServiceImpl.
    @Bean
    public SellerService wholesaleSellerServiceImpl() {
        return new WholesalerSellerServiceImpl();
    }

//    Creates and configures a bean with a custom name "customWholesaleSellerServiceImpl".
    @Bean("customWholesaleSellerServiceImpl")
    public SellerService s() {
        return new WholesalerSellerServiceImpl();
    }
}
