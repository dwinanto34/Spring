package com.app.springcore.implementation;

import com.app.springcore.service.SellerService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class DistributorSellerServiceImpl implements SellerService {
    public DistributorSellerServiceImpl() {
        System.out.println("Constructor: " + getClass().getSimpleName());
    }

    @PostConstruct
    public void sendPostConstructNotification() {
        System.out.println("Hey, this is post construct bean lifecycle event");
    }

    @PreDestroy
    public void sendPreDestroyNotification() {
        System.out.println("Hey, this is pre destory bean lifecycle event");
    }

    @Override
    public String getSellerType() {
        return "This is Distributor seller";
    }
}
