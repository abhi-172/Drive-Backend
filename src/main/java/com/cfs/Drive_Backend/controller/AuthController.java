package com.cfs.Drive_Backend.controller;

import com.cfs.Drive_Backend.dto.LoginRequest;
import com.cfs.Drive_Backend.dto.RegisterRequest;
import com.cfs.Drive_Backend.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService)
    {
        this.userService=userService;
    }
    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        return userService.register(request);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request,
                        HttpSession session) {
        return userService.login(request, session);
    }

    @GetMapping("/me")
    public Object getCurrentUser(HttpSession session) {
        return userService.getLoggedInUser(session);
    }
}
