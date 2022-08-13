package ru.job4j.dream.service;

import ru.job4j.dream.model.Post;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class PostService {
    private static final PostService INST = new PostService();
    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private final AtomicInteger num = new AtomicInteger();

    public PostService() {
    }

    public static PostService instOf() {
        return INST;
    }

    public Collection<Post> findAll() {
        return posts.values();
    }

    public void add(Post post) {
        num.incrementAndGet();
        post.setId(num.get());
        post.setCreated(LocalDateTime.now());
        posts.put(num.get(), post);
    }

    public Post findById(int id) {
        return posts.get(id);
    }

    public void update(Post post) {
        posts.replace(post.getId(), post);
    }
}
