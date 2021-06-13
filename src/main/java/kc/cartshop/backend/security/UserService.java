package kc.cartshop.backend.security;

import kc.cartshop.data.input.UserInput;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public User registerNewUserAccount(UserInput userInput) {
        Role userRole = roleRepository.findByName("ROLE_USER");
        User user = new User();
        user.setFirstName(userInput.getFirstName());
        user.setLastName(userInput.getLastName());
        user.setPassword(passwordEncoder.encode(userInput.getPassword()));
        user.setEmail(userInput.getEmail());
        user.setRoles(Collections.singletonList(userRole));
        user.setEnabled(true);
        return userRepository.save(user);
    }

    public List<User> returnUsers(){
        return userRepository.findAll();
    }
}
