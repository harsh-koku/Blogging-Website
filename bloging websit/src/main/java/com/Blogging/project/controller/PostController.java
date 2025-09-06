package com.Blogging.project.controller;

import com.Blogging.project.model.Post;
import com.Blogging.project.repository.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PostController {
    @Autowired
    PostRepo repo;
    private int id;
    private Model model;

    @GetMapping("/")
    public String viewHomePage(Model model) {
        model.addAttribute("listPosts", repo.findAll());
        return "index";
    }

    @GetMapping("/new")
    public String newPost(Model model) {
        model.addAttribute("post", new Post());
        return "new_post";
    }

    @PostMapping("/save")
    public String savePost(@ModelAttribute("post") Post post) {
        repo.save(post);
        return "redirect:/";
    }

    @PostMapping("/update")
    public String updatePost(@ModelAttribute("post") Post post) {
        repo.save(post);
        return "redirect:/";
    }

    @GetMapping("delete/{id}")
    public String deletePost(@PathVariable int id, Model model) {
        repo.deleteById(id);
        return "redirect:/";
    }
    @GetMapping("/edit/{id}")
    public String editPost(@PathVariable int id, Model model) {
        return repo.findById(id).map(post -> {
            model.addAttribute("post", post);
            return "edit_post";
        }).orElseGet(() -> {
            // If post not found, show popup and go back to home
            model.addAttribute("errorMessage", "Post with ID " + id + " not found!");
            return "error_popup";
        });
    }
}