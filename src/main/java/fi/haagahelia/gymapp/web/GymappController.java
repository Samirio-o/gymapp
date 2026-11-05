package fi.haagahelia.gymapp.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class GymappController {

    @GetMapping("/")
    public String home() {
        return "home";
    }

    /**
     * Shows the exercises for the selected muscle group.
     * The value in the URL is captured by the muscleGroup path variable.
     */
    @GetMapping("/workouts/{muscleGroup}")
    public String showExercises(@PathVariable String muscleGroup, Model model) {
        model.addAttribute("muscleGroup", muscleGroup);
        return "exercises";
    }

}
