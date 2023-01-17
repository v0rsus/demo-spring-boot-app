package org.musala.drones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "org.musala.drones")
public class DronesBootApplication
{
    public static void main(String[] args) {
        SpringApplication.run(DronesBootApplication.class, args);
    }
}
