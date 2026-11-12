package fi.haagahelia.gymapp.web;

import fi.haagahelia.gymapp.domain.AppUser;
import fi.haagahelia.gymapp.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import fi.haagahelia.gymapp.web.dto.RegistrationForm;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;

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
    public String showRegistrationForm(Model model) {
        model.addAttribute("registrationForm", new RegistrationForm());
        return "register";
    }

    /**
     * Validates the registration form, checks that the passwords match
     * and prevents duplicate usernames or email addresses.
     * The password is encoded before the user is saved.
     */
    @PostMapping("/register")
    public String registerUser(
            @Valid @ModelAttribute("registrationForm") RegistrationForm form,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "register";
        }

        if (!form.getPassword().equals(form.getConfirmPassword())) {
            bindingResult.rejectValue(
                    "confirmPassword",
                    "password.mismatch",
                    "Passwords do not match");
            return "register";
        }

        if (userRepository.existsByUsername(form.getUsername())
                || userRepository.existsByEmail(form.getEmail())) {
            bindingResult.reject(
                    "user.exists",
                    "Username or email already exists");
            return "register";
        }

        AppUser user = new AppUser();
        user.setUsername(form.getUsername());
        user.setEmail(form.getEmail());
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        user.setRole("USER");
        userRepository.save(user);

        return "redirect:/login?registered";
    }
}
