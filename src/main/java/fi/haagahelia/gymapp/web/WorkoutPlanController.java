package fi.haagahelia.gymapp.web;

import fi.haagahelia.gymapp.domain.AppUser;
import fi.haagahelia.gymapp.domain.Exercise;
import fi.haagahelia.gymapp.domain.WorkoutPlan;
import fi.haagahelia.gymapp.repository.ExerciseRepository;
import fi.haagahelia.gymapp.repository.UserRepository;
import fi.haagahelia.gymapp.repository.WorkoutPlanRepository;
import fi.haagahelia.gymapp.domain.Exercise;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class WorkoutPlanController {

    private final WorkoutPlanRepository workoutPlanRepository;
    private final UserRepository userRepository;
    private final ExerciseRepository exerciseRepository;

    public WorkoutPlanController(
            WorkoutPlanRepository workoutPlanRepository,
            UserRepository userRepository,
            ExerciseRepository exerciseRepository) {
        this.workoutPlanRepository = workoutPlanRepository;
        this.userRepository = userRepository;
        this.exerciseRepository = exerciseRepository;
    }

    /**
     * Shows the form for creating a new workout plan.
     */
    @GetMapping("/workout-plans/new")
    public String showCreateForm() {
        return "workout-plan-form";
    }

    /**
     * Shows a workout plan and its exercises for the logged-in user.
     */
    @GetMapping("/workout-plans/{id}")
    public String showWorkoutPlan(
            @PathVariable Long id,
            Authentication authentication,
            Model model) {

        WorkoutPlan workoutPlan = workoutPlanRepository.findById(id)
                .orElseThrow();

        if (!workoutPlan.getUser().getUsername()
                .equals(authentication.getName())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        Iterable<Exercise> exercises = exerciseRepository.findByWorkoutPlanId(id);

        model.addAttribute("workoutPlan", workoutPlan);
        model.addAttribute("exercises", exercises);

        return "workout-plan-details";
    }

    /**
     * Creates a workout plan for the currently authenticated user.
     */
    @PostMapping("/workout-plans")
    public String createWorkoutPlan(
            @RequestParam String name,
            @RequestParam String muscleGroup,
            Authentication authentication) {

        AppUser user = userRepository
                .findByUsername(authentication.getName());

        WorkoutPlan workoutPlan = new WorkoutPlan();
        workoutPlan.setName(name);
        workoutPlan.setMuscleGroup(muscleGroup);
        workoutPlan.setUser(user);

        workoutPlanRepository.save(workoutPlan);

        return "redirect:/";
    }
}