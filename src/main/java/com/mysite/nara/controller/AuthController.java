package com.mysite.nara.controller;

import com.mysite.nara.dto.UserDTO;
import com.mysite.nara.entity.User;
import com.mysite.nara.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginpage(Principal principal) {
        if(principal == null) {
            return "login";
        }
        return "redirect:/";
    }

    @GetMapping("/register")
    public String showRegisterpage(Model model) {
        model.addAttribute("user", new UserDTO());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") UserDTO userDTO,
                           BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "register";
        }
        userService.save(userDTO);
        return "redirect:/login";
    }

    //이메일 중복체크
    @PostMapping("/checkemail")
    public String checkEmail(@RequestParam("email") String email, Model model) {
        boolean exists = userService.existsByEmail(email);
        return exists ? "redirect:/login" : "redirect:/register";
    }


}
