package fi.haagahelia.gymapp.config;

import fi.haagahelia.gymapp.domain.AppUser;
import fi.haagahelia.gymapp.domain.WorkoutPlan;
import fi.haagahelia.gymapp.repository.UserRepository;
import fi.haagahelia.gymapp.repository.WorkoutPlanRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import fi.haagahelia.gymapp.domain.Exercise;
import fi.haagahelia.gymapp.repository.ExerciseRepository;

@Configuration
public class WorkoutPlanDataLoader {

    /**
     * Creates a demo workout plan for the demo user.
     */
    @Bean
    @Order(3)
    CommandLineRunner loadDemoWorkoutPlan(
            WorkoutPlanRepository workoutPlanRepository,
            UserRepository userRepository,
            ExerciseRepository exerciseRepository) {

        return args -> {
            if (workoutPlanRepository.count() > 0) {
                return;
            }

            AppUser demoUser = userRepository.findByUsername("demo");

            WorkoutPlan workoutPlan = new WorkoutPlan();
            workoutPlan.setName("Demo Chest Workout");
            workoutPlan.setMuscleGroup("chest");
            workoutPlan.setUser(demoUser);

            workoutPlanRepository.save(workoutPlan);

            Iterable<Exercise> chestExercises = exerciseRepository.findByMuscleGroup("chest");

            for (Exercise exercise : chestExercises) {
                exercise.setWorkoutPlan(workoutPlan);
                exerciseRepository.save(exercise);
            }
        };
    }
}