package com.example.blogsystem.Service;

import com.example.blogsystem.API.ApiException;
import com.example.blogsystem.Model.User;
import com.example.blogsystem.Repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthRepository authRepository;

    public void registerUser(User user){
        if (authRepository.findUserByUsername(user.getUsername()) != null )
            throw new ApiException("user already exist");
        user.setRole("USER");
        String hash = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(hash);
        authRepository.save(user);

    }

    public void registerAdmin(User user){
        if (authRepository.findUserByUsername(user.getUsername()) != null)
            throw new ApiException("admin already exist");
        user.setRole("ADMIN");
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        authRepository.save(user);
    }

    public void updateUser(Integer id, User newUser){
        User old = authRepository.findUserById(id);
        if (old == null)
            throw new ApiException("user not found");

        User sameName = authRepository.findUserByUsername(newUser.getUsername());
        if (sameName != null && !sameName.getId().equals(id))
            throw new ApiException("username already taken");

        old.setUsername(newUser.getUsername());
        old.setPassword(new BCryptPasswordEncoder().encode(newUser.getPassword()));
        authRepository.save(old);
    }

    public void deleteUser(Integer id){
        User user = authRepository.findUserById(id);
        if (user == null)
            throw new ApiException("user not found");
        authRepository.delete(user);
    }

}
