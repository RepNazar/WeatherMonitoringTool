package ua.Nazar.Rep.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.Nazar.Rep.domain.Role;
import ua.Nazar.Rep.domain.User;
import ua.Nazar.Rep.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAuthority('MAIN_ADMIN')")
    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userService.findAll());

        return "userList";
    }

    @PreAuthorize("hasAuthority('MAIN_ADMIN')")
    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());

        return "userEdit";
    }

    @PreAuthorize("hasAuthority('MAIN_ADMIN')")
    @PostMapping
    public String userSave(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("password2") String passwordConfirm,
            @RequestParam("email") String email,
            @RequestParam Map<String, String> form,
            @RequestParam("userId") User user,
            Model model
    ) {
        if (password.equals(passwordConfirm)) {
            userService.saveUser(user, username, password, email, form);

            model.addAttribute("user", null);
            model.addAttribute("passwordError",null);
            return "redirect:/user";
        } else {
            model.addAttribute("roles",Role.values());
            model.addAttribute("user", user);
            model.addAttribute("passwordError", "Passwords are different");
            return "userEdit";
        }
    }

    @GetMapping("profile")
    public String getProfile(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());

        return "profile";
    }

    @PostMapping("profile")
    public String updateProfile(
            @AuthenticationPrincipal User user,
            @RequestParam String password,
            @RequestParam String email,
            @RequestParam("password2") String passwordConfirm,
            Model model
            ) {
        if (password.equals(passwordConfirm)) {

            userService.updateProfile(user, password, email);
            model.addAttribute("user", null);
            model.addAttribute("passwordError", null);

            return "redirect:/user/profile";
        }else{
            model.addAttribute("username", user.getUsername());
            model.addAttribute("email", email);
            model.addAttribute("passwordError", "Passwords are different");
            return "profile";
        }
    }
}
