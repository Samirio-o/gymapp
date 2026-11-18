package fi.haagahelia.gymapp.web;

import java.time.LocalDate;
import fi.haagahelia.gymapp.domain.WorkoutLog;
import fi.haagahelia.gymapp.repository.WorkoutLogRepository;
import fi.haagahelia.gymapp.domain.AppUser;
import fi.haagahelia.gymapp.domain.Exercise;
import fi.haagahelia.gymapp.domain.WorkoutPlan;
import fi.haagahelia.gymapp.repository.ExerciseRepository;
import fi.haagahelia.gymapp.repository.UserRepository;
import fi.haagahelia.gymapp.repository.WorkoutPlanRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;

@Controller
public class WorkoutPlanController {

    private final WorkoutPlanRepository workoutPlanRepository;
    private final UserRepository userRepository;
    private final ExerciseRepository exerciseRepository;
    private final WorkoutLogRepository workoutLogRepository;

    public WorkoutPlanController(
            WorkoutPlanRepository workoutPlanRepository,
            UserRepository userRepository,
            ExerciseRepository exerciseRepository,
            WorkoutLogRepository workoutLogRepository) {
        this.workoutPlanRepository = workoutPlanRepository;
        this.userRepository = userRepository;
        this.exerciseRepository = exerciseRepository;
        this.workoutLogRepository = workoutLogRepository;
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
        Iterable<WorkoutLog> workoutLogs = workoutLogRepository.findByWorkoutPlanId(id);

        model.addAttribute("workoutLogs", workoutLogs);

        model.addAttribute("workoutPlan", workoutPlan);
        model.addAttribute("exercises", exercises);

        return "workout-plan-details";
    }

    /**
     * Shows the form for editing a workout plan.
     */
    @GetMapping("/workout-plans/{id}/edit")
    public String showEditForm(
            @PathVariable Long id,
            Authentication authentication,
            Model model) {

        WorkoutPlan workoutPlan = getUserWorkoutPlan(id, authentication);
        model.addAttribute("workoutPlan", workoutPlan);

        return "workout-plan-edit-form";
    }

    /**
     * Updates a workout plan belonging to the authenticated user.
     */
    @PostMapping("/workout-plans/{id}/edit")
    public String updateWorkoutPlan(
            @PathVariable Long id,
            @RequestParam String name,
            @RequestParam String muscleGroup,
            Authentication authentication) {

        WorkoutPlan workoutPlan = getUserWorkoutPlan(id, authentication);
        workoutPlan.setName(name);
        workoutPlan.setMuscleGroup(muscleGroup);

        workoutPlanRepository.save(workoutPlan);

        return "redirect:/workout-plans/" + id;
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

    /**
     * Deletes a workout plan and all of its related records.
     */
    @PostMapping("/workout-plans/{id}/delete")
    @Transactional
    public String deleteWorkoutPlan(
            @PathVariable Long id,
            Authentication authentication) {

        WorkoutPlan workoutPlan = getUserWorkoutPlan(id, authentication);

        workoutLogRepository.deleteByWorkoutPlanId(id);
        exerciseRepository.deleteByWorkoutPlanId(id);
        workoutPlanRepository.delete(workoutPlan);

        return "redirect:/";
    }

    /**
     * Loads a workout plan only if it belongs to the logged-in user.
     */
    private WorkoutPlan getUserWorkoutPlan(
            Long id,
            Authentication authentication) {

        WorkoutPlan workoutPlan = workoutPlanRepository.findById(id)
                .orElseThrow();

        if (!workoutPlan.getUser().getUsername()
                .equals(authentication.getName())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return workoutPlan;
    }

    /**
     * Records that the authenticated user completed a workout plan today.
     */
    @PostMapping("/workout-plans/{id}/logs")
    public String logWorkout(
            @PathVariable Long id,
            Authentication authentication) {

        WorkoutPlan workoutPlan = getUserWorkoutPlan(id, authentication);
        LocalDate today = LocalDate.now();

        if (!workoutLogRepository.existsByWorkoutPlanIdAndWorkoutDate(id, today)) {
            WorkoutLog workoutLog = new WorkoutLog();
            workoutLog.setWorkoutDate(today);
            workoutLog.setWorkoutPlan(workoutPlan);

            workoutLogRepository.save(workoutLog);
        }

        return "redirect:/workout-plans/" + id;
    }

}