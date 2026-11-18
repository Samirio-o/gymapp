package fi.haagahelia.gymapp.repository;

import fi.haagahelia.gymapp.domain.WorkoutLog;

import java.time.LocalDate;

import org.springframework.data.repository.CrudRepository;

public interface WorkoutLogRepository
        extends CrudRepository<WorkoutLog, Long> {

    /**
     * Finds workout logs belonging to a specific workout plan.
     */
    Iterable<WorkoutLog> findByWorkoutPlanId(Long workoutPlanId);

    boolean existsByWorkoutPlanIdAndWorkoutDate(
            Long workoutPlanId,
            LocalDate workoutDate);

    /**
     * Deletes every workout diary entry associated with a workout plan.
     */
    void deleteByWorkoutPlanId(Long workoutPlanId);
}