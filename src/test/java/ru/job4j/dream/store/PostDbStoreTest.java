package ru.job4j.dream.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.Main;
import ru.job4j.dream.model.City;
import ru.job4j.dream.model.Post;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

class PostDbStoreTest {
    private static final BasicDataSource POOL = new Main().loadPool();
    private static final PostDbStore STORE = new PostDbStore(POOL);

    @BeforeEach
    public void reset() {
        STORE.reset();
    }

    @Test
    public void whenCreatePost() {
        Post post = STORE.add(new Post(
                0,
                "Java Job",
                "Someone description",
                LocalDateTime.now(),
                true,
                new City(1, "Moscow")));
        Post postInDb = STORE.findById(post.getId());
        Assertions.assertThat(post).isEqualTo(postInDb);
    }

    @Test
    public void whenUpdatePost() {
        Post post = STORE.add(new Post(
                0,
                "Java Job",
                "Someone description",
                LocalDateTime.now(),
                true,
                new City(1, "Moscow")));
        post.setName("New name");
        post.setDescription("New Description");
        post.setCity(new City(2));
        STORE.update(post);
        Assertions.assertThat(STORE.findById(post.getId())).isEqualTo(post);
    }

    @Test
    public void whenFindPostById() {
        Post post = STORE.add(new Post(
                0,
                "Java Job",
                "Someone description",
                LocalDateTime.now(),
                true,
                new City(1, "Moscow")));
        Post postInDb = STORE.findById(post.getId());
        Assertions.assertThat(postInDb.getId()).isEqualTo(post.getId());
    }

    @Test
    public void whenFindAllPost() {
        Post post1 = STORE.add(new Post(
                0,
                "Java Job",
                "Someone description",
                LocalDateTime.now(),
                true,
                new City(1, "Moscow")));
        Post post2 = STORE.add(new Post(
                0,
                "Java Job",
                "Someone description",
                LocalDateTime.now(),
                true,
                new City(1, "Moscow")));
        Post post3 = STORE.add(new Post(
                0,
                "Java Job",
                "Someone description",
                LocalDateTime.now(),
                true,
                new City(1, "Moscow")));
        Collection<Post> postCollection = List.of(post1, post2, post3);
        Assertions.assertThat(STORE.findAll()).isEqualTo(postCollection);
    }
}