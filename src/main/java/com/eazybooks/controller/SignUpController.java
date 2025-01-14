package com.eazybooks.controller;
import com.eazybooks.Model.User;
import com.eazybooks.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignUpController {
    UserService userService;


    public SignUpController(UserService userService) {
        this.userService = userService;

    }

    @GetMapping
    public String userSignUp(){
        return "signup";

    }

    /**
     *
     * @param user
     * @param model
     * @return
     */
    @PostMapping()
    public String signupUser(@ModelAttribute User user, Model model) {
        String signupError = null;

        if (!userService.isUsernameAvailable(user.getUsername())) {
            signupError = "The username already exists.";
        }

        if (signupError == null) {
            int rowsAdded = userService.createUser(user);
            if (rowsAdded < 0) {
                signupError = "There was an error signing you up. Please try again.";
            }
        }

        if (signupError == null) {
            model.addAttribute("signupSuccess", true);
            return "login";

        } else {
            model.addAttribute("signupError", signupError);
            return "signup";
        }


    }
}
