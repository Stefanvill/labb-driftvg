package se.iths.stefan.labbdrift.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/")
@Controller
public class HomeController {
    @GetMapping()
    public String home(Model model) {
        model.addAttribute("home", "Welcome to the homepage, please select any page below");
        return "home/home";
    }
}
