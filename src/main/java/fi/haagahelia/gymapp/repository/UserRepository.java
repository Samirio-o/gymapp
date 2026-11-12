package fi.haagahelia.gymapp.repository;

import fi.haagahelia.gymapp.domain.AppUser;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<AppUser, Long> {
    /**
     * Provides database operations for User entities.
     * Spring Data JPA generates the queries from the method names.
     */
    AppUser findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
