package week4.home.study.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:spring/applicationContext.xml")
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
