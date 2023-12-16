package com.app.springcore.implementation;

import com.app.springcore.service.SellerService;
import org.springframework.stereotype.Service;

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
