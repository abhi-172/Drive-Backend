package com.cfs.Drive_Backend.services;

import com.cfs.Drive_Backend.dto.LoginRequest;
import com.cfs.Drive_Backend.dto.RegisterRequest;
import com.cfs.Drive_Backend.entity.User;
import com.cfs.Drive_Backend.repo.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.Serial;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String register(RegisterRequest request)
    {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);
        return"User registered Successfully";
    }

    public String login(LoginRequest request, HttpSession session){
        User user   = userRepository.findByEmail(request.getEmail())
                .orElse(null);

        if(user != null && passwordEncoder.matches(request.getPassword(), user.getPassword()))
        {
            session.setAttribute("user", user);
            return "login successful";
        }
        return "Invalid Credentials";
    }

    public User getLoggedInUser(HttpSession session) {
        return (User) session.getAttribute("user");
    }
}
