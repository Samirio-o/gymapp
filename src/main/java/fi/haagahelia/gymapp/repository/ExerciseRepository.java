package fi.haagahelia.gymapp.repository;

import fi.haagahelia.gymapp.domain.Exercise;
import org.springframework.data.repository.CrudRepository;

public interface ExerciseRepository extends CrudRepository<Exercise, Long> {
    /**
     * Finds all exercises that belong to the selected muscle group.
     * Spring Data JPA creates the database query from the method name.
     */
    Iterable<Exercise> findByMuscleGroup(String muscleGroup);
}
