package fi.haagahelia.gymapp.web;

import fi.haagahelia.gymapp.domain.Exercise;
import fi.haagahelia.gymapp.domain.WorkoutPlan;
import fi.haagahelia.gymapp.repository.ExerciseRepository;
import fi.haagahelia.gymapp.repository.WorkoutPlanRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ExerciseController {

    private final ExerciseRepository exerciseRepository;
    private final WorkoutPlanRepository workoutPlanRepository;

    public ExerciseController(
            ExerciseRepository exerciseRepository,
            WorkoutPlanRepository workoutPlanRepository) {
        this.exerciseRepository = exerciseRepository;
        this.workoutPlanRepository = workoutPlanRepository;
    }

    /**
     * Shows the form for adding an exercise to a workout plan.
     */
    @GetMapping("/workout-plans/{planId}/exercises/new")
    public String showCreateForm(
            @PathVariable Long planId,
            Authentication authentication,
            Model model) {

        WorkoutPlan workoutPlan = getUserWorkoutPlan(planId, authentication);
        model.addAttribute("workoutPlan", workoutPlan);

        return "exercise-form";
    }

    /**
     * Saves a new exercise inside the selected workout plan.
     */
    @PostMapping("/workout-plans/{planId}/exercises")
    public String createExercise(
            @PathVariable Long planId,
            @RequestParam String name,
            @RequestParam int sets,
            @RequestParam int repetitions,
            @RequestParam int restTime,
            Authentication authentication) {

        WorkoutPlan workoutPlan = getUserWorkoutPlan(planId, authentication);

        Exercise exercise = new Exercise();
        exercise.setName(name);
        exercise.setMuscleGroup(workoutPlan.getMuscleGroup());
        exercise.setSets(sets);
        exercise.setRepetitions(repetitions);
        exercise.setRestTime(restTime);
        exercise.setWorkoutPlan(workoutPlan);

        exerciseRepository.save(exercise);

        return "redirect:/workout-plans/" + planId;
    }

    private WorkoutPlan getUserWorkoutPlan(
            Long planId,
            Authentication authentication) {

        WorkoutPlan workoutPlan = workoutPlanRepository.findById(planId)
                .orElseThrow();

        if (!workoutPlan.getUser().getUsername()
                .equals(authentication.getName())) {
            throw new IllegalStateException("Workout plan does not belong to user");
        }

        return workoutPlan;
    }
}