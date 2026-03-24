package org.example.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Cat extends Animal {
    private static final Logger logger = LoggerFactory.getLogger(Cat.class);

    private String breed;
    private static int catCount = 0;

    private static synchronized void incrementCount() {
        catCount++;
    }

    public Cat() {
        super();
        this.breed = "Неизвестно";
        incrementCount();
    }

    public Cat(String name, int age, String habitat, String breed) {
        super(name, age, habitat);
        this.breed = breed;
        incrementCount();
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    @Override
    public void makeSound() {
        logger.info("{} говорит: Мяу!", name);
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        logger.info("Порода: {}", breed);
    }

    public static int getCatCount() {
        return catCount;
    }
}