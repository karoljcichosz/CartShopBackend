package kc.cartshop.backend.security;

import kc.cartshop.data.input.UserInput;
import kc.cartshop.data.output.UserOutput;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@ExposesResourceFor(User.class)
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<UserOutput> getUsers() {
        return userService.returnUsers().stream().map(
                u -> UserOutput.builder()
                        .email(u.getEmail())
                        .firstName(u.getFirstName())
                        .lastName(u.getLastName())
                        .tokenExpired(u.isTokenExpired())
                        .enabled(u.isEnabled())
                        .build()).collect(Collectors.toList());
    }

    @PostMapping("/users")
    void addUser(@RequestBody UserInput user) {
        userService.registerNewUserAccount(user);
    }
}
