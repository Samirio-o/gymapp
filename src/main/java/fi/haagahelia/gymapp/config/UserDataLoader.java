package fi.haagahelia.gymapp.config;

import fi.haagahelia.gymapp.domain.AppUser;
import fi.haagahelia.gymapp.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.core.annotation.Order;

@Configuration
public class UserDataLoader {

    /**
     * Creates a demo user when the database does not contain one.
     */
    @Bean
    @Order(2)
    CommandLineRunner loadDemoUser(
            UserRepository repository,
            PasswordEncoder passwordEncoder) {

        return args -> {
            if (repository.existsByUsername("demo")) {
                return;
            }

            AppUser user = new AppUser();
            user.setUsername("demo");
            user.setEmail("demo@gymapp.local");
            user.setPassword(passwordEncoder.encode("demo"));
            user.setRole("USER");

            repository.save(user);
        };
    }
}