package com.example.blogsystem;

import com.example.blogsystem.Model.User;
import com.example.blogsystem.Repository.AuthRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class BlogSystemApplication {

    public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(BlogSystemApplication.class, args);
        AuthRepository authRepository = context.getBean(AuthRepository.class);

        if (authRepository.findUserByUsername("admin") == null){
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(new BCryptPasswordEncoder().encode("Admin@123"));
            admin.setRole("ADMIN");
            authRepository.save(admin);
        }
    }

}
