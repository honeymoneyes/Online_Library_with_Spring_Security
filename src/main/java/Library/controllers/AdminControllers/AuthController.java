package Library.controllers.AdminControllers;

import Library.models.Person;
import Library.service.securityService.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final RegisterService registerService;

    @Autowired
    public AuthController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @GetMapping("/login")
    public String login() {
        return "views/auth/login";
    }
    @GetMapping("/register")
    public String registerPage(@ModelAttribute("person") Person person) {
        return "views/auth/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("person") Person person) {
        registerService.register(person);
        return "views/auth/login";
    }
}
