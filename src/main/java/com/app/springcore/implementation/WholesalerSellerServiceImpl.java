package com.app.springcore.implementation;

import com.app.springcore.service.SellerService;

// Here, I don't have @Service or @Component
//
public class WholesalerSellerServiceImpl implements SellerService {
    @Override
    public String getSellerType() {
        return "This is Wholesale seller";
    }
}
