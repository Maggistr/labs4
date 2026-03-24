package org.example.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Animal {
    private static final Logger logger = LoggerFactory.getLogger(Animal.class);

    protected String name;
    protected int age;
    protected String habitat;

    public Animal() {
        this.name = "Неизвестно";
        this.age = 0;
        this.habitat = "Неизвестно";
    }

    public Animal(String name, int age, String habitat) {
        this.name = name;
        this.age = age;
        this.habitat = habitat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getHabitat() {
        return habitat;
    }

    public void setHabitat(String habitat) {
        this.habitat = habitat;
    }

    public abstract void makeSound();

    public void displayInfo() {
        logger.info("Имя: {}", name);
        logger.info("Возраст: {} лет/год(а)", age);
        logger.info("Среда обитания: {}", habitat);
    }
}