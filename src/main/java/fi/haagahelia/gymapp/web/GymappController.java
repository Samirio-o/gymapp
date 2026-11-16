package fi.haagahelia.gymapp.web;

import fi.haagahelia.gymapp.domain.WorkoutPlan;
import fi.haagahelia.gymapp.repository.WorkoutPlanRepository;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import fi.haagahelia.gymapp.repository.ExerciseRepository;
import fi.haagahelia.gymapp.domain.Exercise;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.Authentication;

@Controller
public class GymappController {
    private final ExerciseRepository exerciseRepository;
    private final WorkoutPlanRepository workoutPlanRepository;

    /**
     * Shows only the workout plans belonging to the logged-in user.
     */
    @GetMapping("/")
    public String home(Authentication authentication, Model model) {
        Iterable<WorkoutPlan> workoutPlans = workoutPlanRepository.findByUserUsername(authentication.getName());

        model.addAttribute("workoutPlans", workoutPlans);
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    public String getMethodName(@RequestParam String param) {
        return new String();
    }

    /**
     * Shows the exercises belonging to the selected muscle group.
     * The muscle group is read from the URL, and matching exercises
     * are loaded from the database through the repository.
     */
    @GetMapping("/workouts/{muscleGroup}")
    public String showExercises(@PathVariable String muscleGroup, Model model) {
        Iterable<Exercise> exercises = exerciseRepository.findByMuscleGroup(muscleGroup);
        model.addAttribute("muscleGroup", muscleGroup);
        model.addAttribute("exercises", exercises);

        return "exercises";
    }

    public GymappController(
            ExerciseRepository exerciseRepository,
            WorkoutPlanRepository workoutPlanRepository) {
        this.exerciseRepository = exerciseRepository;
        this.workoutPlanRepository = workoutPlanRepository;
    }

    /**
     * Shows the details of one exercise selected by its ID.
     */
    @GetMapping("/exercise/{id}")
    public String showExerciseDetails(@PathVariable Long id, Model model) {
        Exercise exercise = exerciseRepository.findById(id).orElseThrow();
        model.addAttribute("exercise", exercise);
        return "exercise-details";
    }

}
