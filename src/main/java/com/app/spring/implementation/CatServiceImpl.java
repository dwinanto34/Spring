package com.app.spring.implementation;

import com.app.spring.service.AnimalService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class CatServiceImpl implements AnimalService {
    @Override
    public String sayHello() {
        return "Hello, this is cat!";
    }
}
