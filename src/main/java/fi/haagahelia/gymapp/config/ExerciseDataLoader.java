package fi.haagahelia.gymapp.config;

import fi.haagahelia.gymapp.domain.Exercise;
import fi.haagahelia.gymapp.repository.ExerciseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExerciseDataLoader {

    private final ExerciseRepository exerciseRepository;

    ExerciseDataLoader(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    /**
     * Creates sample exercises only when the repository is empty.
     * This prevents duplicate exercises when the application starts again.
     */
    @Bean
    CommandLineRunner loadExercises(ExerciseRepository repository) {
        return args -> {
            if (repository.count() > 0) {
                return;
            }
            repository.save(createExercise("Shoulder Press", "shoulders", 4, 10, 90));
            repository.save(createExercise("Lateral Raise", "shoulders", 3, 12, 60));

            repository.save(createExercise("Bench Press", "chest", 4, 8, 120));
            repository.save(createExercise("Chest Fly", "chest", 3, 12, 60));

            repository.save(createExercise("Barbell Curl", "biceps", 3, 10, 60));
            repository.save(createExercise("Hammer Curl", "biceps", 3, 12, 60));

            repository.save(createExercise("Triceps Pushdown", "triceps", 3, 12, 60));
            repository.save(createExercise("Skull Crusher", "triceps", 3, 10, 90));

            repository.save(createExercise("Lat Pulldown", "back", 4, 10, 90));
            repository.save(createExercise("Seated Row", "back", 3, 12, 90));

            repository.save(createExercise("Squat", "legs", 4, 8, 120));
            repository.save(createExercise("Leg Press", "legs", 3, 10, 90));
        };
    }

    /**
     * Creates an Exercise object with its training information.
     */
    private Exercise createExercise(
            String name,
            String muscleGroup,
            int sets,
            int repetitions,
            int restTime) {

        Exercise exercise = new Exercise();
        exercise.setName(name);
        exercise.setMuscleGroup(muscleGroup);
        exercise.setSets(sets);
        exercise.setRepetitions(repetitions);
        exercise.setRestTime(restTime);

        return exercise;
    }
}
