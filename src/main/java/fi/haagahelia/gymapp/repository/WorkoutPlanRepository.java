package fi.haagahelia.gymapp.repository;

import fi.haagahelia.gymapp.domain.WorkoutPlan;
import org.springframework.data.repository.CrudRepository;

public interface WorkoutPlanRepository
        extends CrudRepository<WorkoutPlan, Long> {

    /**
     * Finds all workout plans belonging to a specific user.
     */
    Iterable<WorkoutPlan> findByUserUsername(String username);

    /**
     * Finds workout plans for a selected muscle group.
     */
    Iterable<WorkoutPlan> findByMuscleGroup(String muscleGroup);
}