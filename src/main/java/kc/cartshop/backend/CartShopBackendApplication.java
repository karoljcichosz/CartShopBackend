package kc.cartshop.backend;

import kc.cartshop.backend.config.WebSecurityConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({ WebSecurityConfiguration.class })
public class CartShopBackendApplication {
    //TODO na przytszlosc: 1. tworzenie koszyka przy utworzeniu user, 2. koszyka na sesjach i redis, 3. logowanie i sesja na froncie
    public static void main(String[] args) {
        SpringApplication.run(CartShopBackendApplication.class, args);
    }

}
