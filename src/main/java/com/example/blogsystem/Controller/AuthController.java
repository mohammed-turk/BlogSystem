package com.example.blogsystem.Controller;

import com.example.blogsystem.API.ApiResponse;
import com.example.blogsystem.Model.User;
import com.example.blogsystem.Service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid User user) {
        authService.registerUser(user);
        return ResponseEntity.status(200).body(new ApiResponse("user added successfully"));
    }

    @PostMapping("/register-admin")
    public ResponseEntity<?> registerAdmin(@RequestBody @Valid User user){
        authService.registerAdmin(user);
        return ResponseEntity.status(200).body(new ApiResponse("admin registered"));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@AuthenticationPrincipal User user, @RequestBody @Valid User newUser){
        authService.updateUser(user.getId(), newUser);
        return ResponseEntity.status(200).body(new ApiResponse("user updated"));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@AuthenticationPrincipal User user){
        authService.deleteUser(user.getId());
        return ResponseEntity.status(200).body(new ApiResponse("user deleted"));
    }

}
