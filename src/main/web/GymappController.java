package fi.haagahelia.gymapp.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GymappController {
    
    @GetMapping("/")
    public String home() {
        return "home";
    }
}
