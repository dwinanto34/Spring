package com.app.spring.implementation;

import com.app.spring.service.SellerService;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class DistributorSellerServiceImpl implements SellerService {
    public DistributorSellerServiceImpl() {
        System.out.println("Constructor: " + getClass().getSimpleName());
    }

    @Override
    public String getSellerType() {
        return "This is Distributor seller";
    }
}
