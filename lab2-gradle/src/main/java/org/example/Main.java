package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Программа запущена");

        try {
            Properties props = new Properties();
            InputStream input = Main.class.getClassLoader()
                    .getResourceAsStream("build-passport.properties");
            if (input != null) {
                props.load(input);
                logger.info("Build user: {}", props.getProperty("user"));
                logger.info("OS: {}", props.getProperty("os"));
                logger.info("Java: {}", props.getProperty("java"));
                logger.info("Build time: {}", props.getProperty("build.time"));
                logger.info("Git commit hash: {}", props.getProperty("git.commit.hash"));
                logger.info("Build number: {}", props.getProperty("build.number"));
            }
        } catch (Exception e) {
            logger.error("Ошибка чтения build-passport", e);
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите строку: ");
        String input = scanner.nextLine();

        String reversedWithCommons = StringUtils.reverse(input);
        logger.info("Строка, перевёрнутая с помощью Apache Commons Lang3: {}", reversedWithCommons);

        String reversedWithOwn = StringProcessor.reverse(input);
        logger.info("Строка, перевёрнутая с помощью собственного модуля: {}", reversedWithOwn);

        String capitalized = StringUtils.capitalize(input);
        logger.info("Строка с заглавной первой буквой: {}", capitalized);

        logger.info("Программа завершена");
    }
}