package com.example.blogsystem.Service;

import com.example.blogsystem.API.ApiException;
import com.example.blogsystem.Model.Blog;
import com.example.blogsystem.Model.User;
import com.example.blogsystem.Repository.AuthRepository;
import com.example.blogsystem.Repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;
    private final AuthRepository authRepository;

    public List<Blog> getAdminBlogs(Integer adminId){
        User user = authRepository.findUserById(adminId);

        if (user == null)
            throw new ApiException("user not found");
        return blogRepository.findAll();
    }

    public List<Blog> getBlogs(Integer userId){
        User user = authRepository.findUserById(userId);
        if (user == null)
            throw new ApiException("user not found");
        return blogRepository.findAllByUser(user);

    }
    public Blog getBlogById(Integer userId, Integer blogId){
        User user = authRepository.findUserById(userId);
        if (user == null)
            throw new ApiException("user not found");

        Blog blog = blogRepository.findBlogById(blogId);
        if (blog == null)
            throw new ApiException("blog not found");
        if (!blog.getUser().getId().equals(userId))
            throw new ApiException("Cannot view");
        return blogRepository.findBlogById(blogId);
    }

    public Blog getBlogByTitle(Integer userId, String title){
        User user = authRepository.findUserById(userId);
        if (user == null)
            throw new ApiException("user not found");

        Blog blog = blogRepository.findBlogByUserAndTitle(user, title);
        if (blog == null)
            throw new ApiException("blog not found");

        return blog;
    }

    public void addBlog(Integer userId, Blog blog){
        User user = authRepository.findUserById(userId);
        if (user == null)
            throw new ApiException("user not found");
        blog.setUser(user);
        blogRepository.save(blog);
    }

    public void updateBlog(Integer userId, Integer blogId, Blog blog){
        User user = authRepository.findUserById(userId);
        Blog oldBlog = blogRepository.findBlogById(blogId);
        if(user == null) throw new ApiException("Not found user");
        if(oldBlog == null) throw new ApiException("Not found todo");
        if(!oldBlog.getUser().getId().equals(userId)) throw new ApiException("Cannot update");
        oldBlog.setBody(blog.getBody());

//        commented as title never changed
//        oldBlog.setTitle(oldBlog.getTitle());
        blogRepository.save(oldBlog);
    }

    public void deleteBlog(Integer userId, Integer blogId){
        User user = authRepository.findUserById(userId);
        Blog selecteBlog = blogRepository.findBlogById(blogId);
        if(user == null) throw new ApiException("Not found user");
        if(selecteBlog == null) throw new ApiException("Not found todo");
        if(!selecteBlog.getUser().getId().equals(userId)) throw new ApiException("Cannot delete");
        blogRepository.delete(selecteBlog);
    }
}
