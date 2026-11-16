package fi.haagahelia.gymapp.web;

import fi.haagahelia.gymapp.domain.AppUser;
import fi.haagahelia.gymapp.domain.WorkoutPlan;
import fi.haagahelia.gymapp.repository.UserRepository;
import fi.haagahelia.gymapp.repository.WorkoutPlanRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WorkoutPlanController {

    private final WorkoutPlanRepository workoutPlanRepository;
    private final UserRepository userRepository;

    public WorkoutPlanController(
            WorkoutPlanRepository workoutPlanRepository,
            UserRepository userRepository) {
        this.workoutPlanRepository = workoutPlanRepository;
        this.userRepository = userRepository;
    }

    /**
     * Shows the form for creating a new workout plan.
     */
    @GetMapping("/workout-plans/new")
    public String showCreateForm() {
        return "workout-plan-form";
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