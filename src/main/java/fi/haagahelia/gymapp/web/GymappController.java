package fi.haagahelia.gymapp.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import fi.haagahelia.gymapp.repository.ExerciseRepository;
import fi.haagahelia.gymapp.domain.Exercise;

@Controller
public class GymappController {
    private final ExerciseRepository exerciseRepository;

    @GetMapping("/")
    public String home() {
        return "home";
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

    public GymappController(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
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
