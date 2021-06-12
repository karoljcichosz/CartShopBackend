package kc.cartshop.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableConfigurationProperties(KeycloakProperties.class)
public class CartShopBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(CartShopBackendApplication.class, args);
    }

}
