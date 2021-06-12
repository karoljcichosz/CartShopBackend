//package kc.cartshop.backend.keycloak;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/keycloak")
//class KeycloakController {
//    private KeycloakProperties config;
//    KeycloakController(KeycloakProperties config, @Value("${keycloakRequiredUserRole}") String requiredUserRole){
//        this.config=config;
//        this.config.requiredUserRole=requiredUserRole;
//    }
//
//    @GetMapping("/config")
//    KeycloakProperties config() {
//        return config;
//    }
//}