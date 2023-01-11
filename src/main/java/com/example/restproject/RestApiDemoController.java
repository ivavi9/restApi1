package com.example.restproject;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/coffees")
public class RestApiDemoController {
    //Begin Changes
    private final CoffeeRepository coffeeRepository;

    public RestApiDemoController(CoffeeRepository coffeeRepository) {
        this.coffeeRepository = coffeeRepository;
    }






    @GetMapping()
    Iterable<Coffee> getCoffees(){
        return coffeeRepository.findAll();
    }

    @GetMapping("/{id}")
    Optional<Coffee> getCoffeeById(@PathVariable String id){
       return coffeeRepository.findById(id);
    }
    @PostMapping()
    Coffee postCoffee(@RequestBody Coffee coffee){
        return coffeeRepository.save(coffee);
    }
//Adding Comments
    @PutMapping("/{id}")
    ResponseEntity<Coffee> putCoffee(@PathVariable String id,@RequestBody Coffee coffee){
        return (!coffeeRepository.existsById(id))?
                new ResponseEntity<>(coffeeRepository.save(coffee), HttpStatus.CREATED):
                new ResponseEntity<>(coffeeRepository.save(coffee),HttpStatus.OK

        );

    }

    @DeleteMapping("/{id}")
    void deleteCoffee(@PathVariable String id){
        coffeeRepository.deleteById(id);
    }




}
@RestController
@RequestMapping("/droid")
class DroidController{
    private final Droid droid;

    public DroidController(Droid droid) {
        this.droid = droid;
    }

    @GetMapping
    Droid getDroid(){
        return droid;
    }
}


@RestController
@RequestMapping("/greeting")
class GreetingController{

    private final Greeting greeting;

    public GreetingController(Greeting greeting) {
        this.greeting = greeting;
    }


    @GetMapping
    String getGreeting(){
        return greeting.getName();
    }
    @GetMapping("/coffee")
    String getCoffeeGreeting(){
        return greeting.getCoffee();
    }
}


@Component
class DataLoader {

    private final CoffeeRepository coffeeRepository;

    public DataLoader(CoffeeRepository coffeeRepository){
        this.coffeeRepository = coffeeRepository;

    }

    @PostConstruct
    private void loadData(){
        coffeeRepository.saveAll(List.of(
                new Coffee("Americano"),
                new Coffee("Cappacino"),
                new Coffee("Cortado"),
                new Coffee("Indian")
        ));
    }
}

@ConfigurationProperties(prefix = "greeting")
class Greeting{
    private String name;
    private String coffee;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoffee() {
        return coffee;
    }

    public void setCoffee(String coffee) {
        this.coffee = coffee;
    }
}

class Droid{
    private String id,description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

