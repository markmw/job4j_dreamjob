package ru.job4j.dream.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.Main;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.City;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

class CandidateDbStoreTest {
    private static final BasicDataSource POOL = new Main().loadPool();
    private static final CandidateDbStore STORE = new CandidateDbStore(POOL);

    @BeforeEach
    public void reset() {
        STORE.reset();
    }

    @Test
    public void whenCreateCandidate() {
        byte[] photo = new byte[0];
        Candidate candidate = STORE.add(new Candidate(
                0,
                "Addy",
                "Someone description",
                LocalDateTime.now(),
                true,
                new City(1, "Moscow"),
                photo));
        Candidate candidateInDb = STORE.findById(candidate.getId());
        Assertions.assertThat(candidate).isEqualTo(candidateInDb);
    }

    @Test
    public void whenUpdateCandidate() {
        byte[] photo = new byte[0];
        Candidate candidate = STORE.add(new Candidate(
                0,
                "Addy",
                "Someone description",
                LocalDateTime.now(),
                true,
                new City(1, "Moscow"),
                photo));
        candidate.setName("Mark");
        candidate.setDescription("New Description");
        candidate.setCity(new City(2));
        STORE.update(candidate);
        Assertions.assertThat(STORE.findById(candidate.getId())).isEqualTo(candidate);
    }

    @Test
    public void whenFindCandidateById() {
        byte[] photo = new byte[0];
        Candidate candidate = STORE.add(new Candidate(
                0,
                "Addy",
                "Someone description",
                LocalDateTime.now(),
                true,
                new City(1, "Moscow"),
                photo));
        Candidate candidateInDb = STORE.findById(candidate.getId());
        Assertions.assertThat(candidateInDb.getId()).isEqualTo(candidate.getId());
    }

    @Test
    public void whenFindAllCandidate() {
        byte[] photo = new byte[0];
        Candidate candidate = STORE.add(new Candidate(
                0,
                "Addy",
                "Someone description",
                LocalDateTime.now(),
                true,
                new City(1, "Moscow"),
                photo));
        Candidate candidate1 = STORE.add(new Candidate(
                0,
                "Addy",
                "Someone description",
                LocalDateTime.now(),
                true,
                new City(1, "Moscow"),
                photo));
        Candidate candidate2 = STORE.add(new Candidate(
                0,
                "Addy",
                "Someone description",
                LocalDateTime.now(),
                true,
                new City(1, "Moscow"),
                photo));
        Collection<Candidate> candidateCollection = List.of(candidate, candidate1, candidate2);
        Assertions.assertThat(STORE.findAll()).isEqualTo(candidateCollection);
    }
}