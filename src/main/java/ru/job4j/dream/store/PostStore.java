package ru.job4j.dream.store;

import ru.job4j.dream.model.Post;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class PostStore {
    private static final PostStore INST = new PostStore();
    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private final AtomicInteger num = new AtomicInteger();

    private PostStore() {
        posts.put(num.incrementAndGet(), new Post(
                num.get(),
                "Junior Java Job",
                "It's Java Junior work",
                LocalDateTime.of(2022, 8, 12, 13, 30)));
        posts.put(num.incrementAndGet(), new Post(
                num.get(),
                "Middle Java Job",
                "It's Java Middle work",
                LocalDateTime.of(2022, 8, 12, 9, 0)));
        posts.put(num.incrementAndGet(), new Post(
                num.get(),
                "Senior Java Job",
                "It's Java Senior work",
                LocalDateTime.of(2022, 8, 12, 12, 0)));
    }

    public static PostStore instOf() {
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
}
