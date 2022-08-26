package ru.job4j.dream.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.service.CityService;
import ru.job4j.dream.service.PostService;
import ru.job4j.dream.util.GetUserView;

import javax.servlet.http.HttpSession;

@Controller @ThreadSafe
public class PostController {
    private final PostService store;
    private final CityService cityService;

    public PostController(PostService store, CityService cityService) {
        this.store = store;
        this.cityService = cityService;
    }

    @GetMapping("/posts")
    public String posts(Model model, HttpSession session) {
        GetUserView.getUserView(model, session);
        model.addAttribute("posts", store.findAll());
        return "posts";
    }

    @GetMapping("/formAddPost")
    public String addPost(Model model, HttpSession session) {
        GetUserView.getUserView(model, session);
        model.addAttribute("cities", cityService.getAllCities());
        return "addPost";
    }

    @PostMapping("/createPost")
    public String createPost(@ModelAttribute Post post) {
        store.add(post);
        return "redirect:/posts";
    }

    @GetMapping("/formUpdatePost/{postId}")
    public String formUpdatePost(Model model, @PathVariable("postId") int id, HttpSession session) {
        GetUserView.getUserView(model, session);
        model.addAttribute("post", store.findById(id));
        model.addAttribute("cities", cityService.getAllCities());
        return "updatePost";
    }

    @PostMapping("/updatePost")
    public String updatePost(@ModelAttribute Post post) {
        store.update(post);
        return "redirect:/posts";
    }
}
