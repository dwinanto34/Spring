package com.app.spring.implementation;

import com.app.spring.service.SellerService;

// Here, I don't have @Service or @Component
//
public class WholesalerSellerServiceImpl implements SellerService {
    @Override
    public String getSellerType() {
        return "This is Wholesale seller";
    }
}
