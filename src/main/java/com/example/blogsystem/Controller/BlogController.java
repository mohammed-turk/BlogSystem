package com.example.blogsystem.Controller;

import com.example.blogsystem.API.ApiResponse;
import com.example.blogsystem.Model.Blog;
import com.example.blogsystem.Model.User;
import com.example.blogsystem.Service.BlogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/blog")
@RequiredArgsConstructor
public class BlogController {
    private final BlogService blogService;

    @GetMapping("/admin/get-all")
    public ResponseEntity<?> getAdminBlogs(@AuthenticationPrincipal User admin){
        return ResponseEntity.status(200).body(blogService.getAdminBlogs(admin.getId()));
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getBlogs(@AuthenticationPrincipal User user){
        return ResponseEntity.status(200).body(blogService.getBlogs(user.getId()));
    }

    @GetMapping("/{blogId}")
    public ResponseEntity<?> getBlogById(@AuthenticationPrincipal User user, @PathVariable Integer blogId){
        return ResponseEntity.status(200).body(blogService.getBlogById(user.getId(), blogId));
    }

    @GetMapping("/admin/title/{title}")
    public ResponseEntity<?> getBlogByTitleAdmin(@AuthenticationPrincipal User admin, @PathVariable String title){
        return ResponseEntity.status(200).body(blogService.getBlogByTitle(admin.getId(), title));
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<?> getBlogByTitle(@AuthenticationPrincipal User user, @PathVariable String title){
        return ResponseEntity.status(200).body(blogService.getBlogByTitle(user.getId(), title));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addBlog(@AuthenticationPrincipal User user,@RequestBody @Valid Blog blog){
        blogService.addBlog(user.getId(), blog);
    return ResponseEntity.status(200).body(new ApiResponse("blog added successfully"));
    }

    @PutMapping("/update/{blogId}")
    public ResponseEntity<?> updateBlog(@AuthenticationPrincipal User user, @PathVariable Integer blogId, @RequestBody @Valid Blog blog){
        blogService.updateBlog(user.getId(), blogId,blog);
        return ResponseEntity.status(200).body(new ApiResponse("blog updated successfully"));
    }

    @DeleteMapping("/delete/{blogId}")
    public ResponseEntity<?> deleteBlog(@AuthenticationPrincipal User user, @PathVariable Integer blogId) {
        blogService.deleteBlog(user.getId(), blogId);
        return ResponseEntity.status(200).body(new ApiResponse("blog deleted successfully"));
    }

}
