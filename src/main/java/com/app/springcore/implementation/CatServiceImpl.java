package com.app.springcore.implementation;

import com.app.springcore.service.AnimalService;
import org.springframework.stereotype.Service;

@Service
public class CatServiceImpl implements AnimalService {
    @Override
    public String sayHello() {
        return "Hello, this is cat!";
    }
}
