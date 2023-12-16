package com.app.springcore.rest;

import com.app.springcore.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnimalController {
//    For dependency injection there are 3 ways to do it:

//    First option, constructor injection (Recommended)
    private AnimalService animalService;
    @Autowired
    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

//    Second option, setter injection (Recommended)
//    private AnimalService animalService;
//    @Autowired
//    public void setAnimalService(AnimalService animalService) {
//        this.animalService = animalService;
//    }

//    Third option, field injection (Not recommended)
//    @Autowired
//    private AnimalService animalService;

    @GetMapping("/animal")
    public String animal() {
        return animalService.sayHello();
    }
}
