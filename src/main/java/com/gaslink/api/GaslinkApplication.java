//package com.gaslink.api;
//
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.scheduling.annotation.EnableScheduling;
//
//@SpringBootApplication
//@EnableScheduling
//public class GaslinkApplication {
//    public static void main(String[] args) {
//        SpringApplication.run(GaslinkApplication.class, args);
//    }
//}


package com.gaslink.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.gaslink.api"})
public class GaslinkApplication {
    public static void main(String[] args) {
        SpringApplication.run(GaslinkApplication.class, args);
    }
}