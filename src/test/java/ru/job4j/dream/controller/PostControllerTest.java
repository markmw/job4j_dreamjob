package ru.job4j.dream.controller;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.Model;
import ru.job4j.dream.model.City;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.service.CityService;
import ru.job4j.dream.service.PostService;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class PostControllerTest {
    @Test
    public void whenPosts() {
        List<Post> posts = Arrays.asList(
                new Post(
                        1,
                        "New post",
                        "new desc",
                        LocalDateTime.now(),
                        true,
                        new City(1)),
                new Post(
                        2,
                        "New post",
                        "new desc",
                        LocalDateTime.now(),
                        false,
                        new City(2))
        );
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        PostService postService = mock(PostService.class);
        when(postService.findAll()).thenReturn(posts);
        CityService cityService = mock(CityService.class);
        PostController postControl = new PostController(
                postService,
                cityService
        );

        String page = postControl.posts(model, session);
        verify(model).addAttribute("posts", posts);
        assertThat(page).isEqualTo("posts");
    }

    @Test
    public void whenAddPost() {
        Post post = new Post(1,
                "Alex",
                "test",
                LocalDateTime.now(),
                true,
                new City(1));
        List<City> cities = Arrays.asList(
                new City(1, "Москва"),
                new City(2, "Санкт-Петербург")
        );
        Model model = mock(Model.class);
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        HttpSession httpSession = new MockHttpSession();
        model.addAttribute("user", post);
        when(cityService.getAllCities()).thenReturn(cities);
        PostController postController = new PostController(postService, cityService);
        String page = postController.addPost(model, httpSession);
        verify(model).addAttribute("user", post);
        verify(model).addAttribute("cities", cities);
        assertThat(page).isEqualTo("addPost");
    }

    @Test
    public void whenFormUpdatePost() {
        Post input = new Post(
                1,
                "post",
                "old",
                LocalDateTime.now(),
                true,
                new City(1));
        Model model = mock(Model.class);
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        PostController postController = new PostController(postService, cityService);
        postController.createPost(input);
        HttpSession session = new MockHttpSession();
        String formUpdate = postController.formUpdatePost(model, input.getId(), session);
        verify(model).addAttribute("post", postService.findById(input.getId()));
        verify(model).addAttribute("cities", cityService.getAllCities());
        assertThat(formUpdate).isEqualTo("updatePost");
    }

    @Test
    public void whenUpdatePost() {
        Post input = new Post(
                1,
                "post",
                "desc",
                LocalDateTime.now(),
                true,
                new City(1));
        Post newInput = new Post(
                1,
                "New post",
                "desc2",
                LocalDateTime.now(),
                true,
                new City(1));
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        PostController postController = new PostController(
                postService,
                cityService
        );
        postController.createPost(input);
        String update = postController.updatePost(newInput);
        verify(postService).update(input);
        assertThat(update).isEqualTo("redirect:/posts");
    }

    @Test
    public void whenCreatePost() {
        Post input = new Post(
                1,
                "post",
                "desc",
                LocalDateTime.now(),
                true,
                new City(1));
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        PostController postController = new PostController(
                postService,
                cityService
        );
        String page = postController.createPost(input);
        verify(postService).add(input);
        assertThat(page).isEqualTo("redirect:/posts");
    }
}