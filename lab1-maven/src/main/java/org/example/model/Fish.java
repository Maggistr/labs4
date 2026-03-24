package org.example.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Fish extends Animal {
    private static final Logger logger = LoggerFactory.getLogger(Fish.class);

    private String waterType;
    private static int fishCount = 0;

    private static synchronized void incrementCount() {
        fishCount++;
    }

    public Fish() {
        super();
        this.waterType = "Пресная";
        incrementCount();
    }

    public Fish(String name, int age, String habitat, String waterType) {
        super(name, age, habitat);
        this.waterType = waterType;
        incrementCount();
    }

    public String getWaterType() {
        return waterType;
    }

    public void setWaterType(String waterType) {
        this.waterType = waterType;
    }

    @Override
    public void makeSound() {
        logger.info("{} говорит: (пускает пузыри)", name);
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        logger.info("Тип воды: {}", waterType);
    }

    public void swim() {
        logger.info("{} плавает в воде", name);
    }

    public static int getFishCount() {
        return fishCount;
    }
}