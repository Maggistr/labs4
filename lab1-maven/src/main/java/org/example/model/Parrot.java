package org.example.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Parrot extends Animal {
    private static final Logger logger = LoggerFactory.getLogger(Parrot.class);

    private volatile boolean canTalk;
    private static int parrotCount = 0;

    private static synchronized void incrementCount() {
        parrotCount++;
    }

    public Parrot() {
        super();
        this.canTalk = false;
        incrementCount();
    }

    public Parrot(String name, int age, String habitat, boolean canTalk) {
        super(name, age, habitat);
        this.canTalk = canTalk;
        incrementCount();
    }

    public boolean getCanTalk() {
        return canTalk;
    }

    public void setCanTalk(boolean canTalk) {
        this.canTalk = canTalk;
    }

    @Override
    public void makeSound() {
        if (canTalk) {
            logger.info("{} говорит: Привет!", name);
        } else {
            logger.info("{} говорит: Чирик-чирик!", name);
        }
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        logger.info("Умеет говорить: {}", canTalk ? "Да" : "Нет");
    }

    public static int getParrotCount() {
        return parrotCount;
    }
}