package com.app.springcore.config;

import com.app.springcore.implementation.WholesalerSellerServiceImpl;
import com.app.springcore.service.SellerService;
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
