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

//    Custom config bean

    private SellerService wholesaleSellerService;
    @Autowired
    public void setWholesaleSellerService(@Qualifier("wholesaleSellerServiceImpl") SellerService wholesaleSellerService) {
        this.wholesaleSellerService = wholesaleSellerService;
    }

    @GetMapping("/seller-wholesale")
    public String wholesaleSeller() {
        return wholesaleSellerService.getSellerType();
    }

    private SellerService customWholesaleSellerService;
    @Autowired
    public void setCustomWholesaleSellerService(@Qualifier("customWholesaleSellerServiceImpl") SellerService customWholesaleSellerService) {
        this.customWholesaleSellerService = customWholesaleSellerService;
    }

    @GetMapping("/seller-custom-wholesale")
    public String customWholesaleSeller() {
        return customWholesaleSellerService.getSellerType();
    }
}
