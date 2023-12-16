package com.app.spring.rest;

import com.app.spring.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SellerController {
//    If no qualifier is specified, the primary bean will be used.
    private SellerService primarySellerService;
    @Autowired
    public SellerController(SellerService primarySellerService) {
        this.primarySellerService = primarySellerService;
    }

    @GetMapping("/seller-primary")
    public String primarySeller() {
        return primarySellerService.getSellerType();
    }

//    Autowired setRetailSellerService injects the specific bean with the "retailSellerServiceImpl" qualifier
    private SellerService retailSellerService;
    @Autowired
    public void setRetailSellerService(@Qualifier("retailSellerServiceImpl") SellerService retailSellerService) {
        this.retailSellerService = retailSellerService;
    }

    @GetMapping("/seller-retail")
    public String retailSeller() {
        return retailSellerService.getSellerType();
    }
}
