package com.app.spring.implementation;

import com.app.spring.service.SellerService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Service
// This service is marked as @Lazy, indicating that it will be lazily initialized.
//@Lazy
// The default scope is singleton
// By specifying the PROTOTYPE scope
// It instructs the system to create a new instance each time the bean is requested
//@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RetailSellerServiceImpl implements SellerService {
    public RetailSellerServiceImpl() {
        System.out.println("Constructor: " + getClass().getSimpleName());
    }

    @Override
    public String getSellerType() {
        return "This is Retail seller";
    }
}
