package org.example;
import java.nio.charset.StandardCharsets;
import org.example.model.*;
import org.example.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8)) {

            Cat cat1 = new Cat();
            Parrot parrot1 = new Parrot();
            Fish fish1 = new Fish();

            logger.info("Ввод информации о животных");

            logger.info("\nВвод информации о кошке");
            System.out.print("Введите имя кошки: ");
            cat1.setName(scanner.nextLine());
            System.out.print("Введите возраст кошки: ");
            cat1.setAge(scanner.nextInt());
            scanner.nextLine();
            System.out.print("Введите среду обитания кошки: ");
            cat1.setHabitat(scanner.nextLine());
            System.out.print("Введите породу кошки: ");
            cat1.setBreed(scanner.nextLine());

            logger.info("\nВвод информации о попугае");
            System.out.print("Введите имя попугая: ");
            parrot1.setName(scanner.nextLine());
            System.out.print("Введите возраст попугая: ");
            parrot1.setAge(scanner.nextInt());
            scanner.nextLine();
            System.out.print("Введите среду обитания попугая: ");
            parrot1.setHabitat(scanner.nextLine());
            System.out.print("Попугай умеет говорить? (true/false): ");
            parrot1.setCanTalk(scanner.nextBoolean());
            scanner.nextLine();

            logger.info("\nВвод информации о рыбке");
            System.out.print("Введите имя рыбки: ");
            fish1.setName(scanner.nextLine());
            System.out.print("Введите возраст рыбки: ");
            fish1.setAge(scanner.nextInt());
            scanner.nextLine();
            System.out.print("Введите среду обитания рыбки: ");
            fish1.setHabitat(scanner.nextLine());
            System.out.print("Введите тип воды для рыбки: ");
            fish1.setWaterType(scanner.nextLine());

            Cat cat2 = new Cat("Барсик", 3, "Дом", "Сиамская");
            Parrot parrot2 = new Parrot("Кеша", 2, "Джунгли", true);
            Fish fish2 = new Fish("Немо", 1, "Аквариум", "Соленая");

            logger.info("\nИнформация о животных");

            Animal[] animals = {cat1, parrot1, fish1, cat2, parrot2, fish2};

            for (Animal animal : animals) {
                logger.info("\n{}", animal.getClass().getSimpleName());
                animal.displayInfo();
                animal.makeSound();

                if (animal instanceof Fish) {
                    ((Fish) animal).swim();
                }
            }

            logger.info("\nСтатистика созданных объектов");
            logger.info("Количество созданных кошек: {}", Cat.getCatCount());
            logger.info("Количество созданных попугаев: {}", Parrot.getParrotCount());
            logger.info("Количество созданных рыбок: {}", Fish.getFishCount());
            logger.info("Общее количество животных: {}", Cat.getCatCount() + Parrot.getParrotCount() + Fish.getFishCount());


            logger.info("\n--- Работа с JSON ---");
            String catJson = JsonUtils.toJson(cat2);
            logger.info("Сериализованный кот: {}", catJson);

            Cat deserializedCat = JsonUtils.fromJson(catJson, Cat.class);
            logger.info("Десериализованный кот: имя = {}, возраст = {}, порода = {}",
                    deserializedCat.getName(), deserializedCat.getAge(), deserializedCat.getBreed());


            logger.info("Транзитивные зависимости Jackson можно посмотреть командой: mvn dependency:tree");

        }
    }
}