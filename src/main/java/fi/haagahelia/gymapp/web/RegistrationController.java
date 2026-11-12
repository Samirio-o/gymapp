package fi.haagahelia.gymapp.web;

import fi.haagahelia.gymapp.domain.AppUser;
import fi.haagahelia.gymapp.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Shows the registration form.
     * The registration page is available before authentication.
     */
    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    /**
     * Creates a new user account after checking that the username and email
     * are not already registered. The password is encoded before saving.
     */
    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String email,
            @RequestParam String password) {
        if (userRepository.existsByUsername(username) || userRepository.existsByEmail(email)) {
            return "redirect:/register?error";
        }

        AppUser user = new AppUser();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("USER");
        userRepository.save(user);

        return "redirect:/register?success";
    }
}
