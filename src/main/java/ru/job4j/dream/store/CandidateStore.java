package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class CandidateStore {
    private static final CandidateStore INST = new CandidateStore();
    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();
    private final AtomicInteger num = new AtomicInteger();

    private CandidateStore() {
        candidates.put(num.incrementAndGet(), new Candidate(
                num.get(),
                "Addy Bay",
                "Learning Java in sometimes",
                LocalDateTime.of(2022, 8, 12, 9, 30)));
        candidates.put(num.incrementAndGet(), new Candidate(
                num.get(),
                "Ivan Ivanov",
                "Learning C++ in sometimes",
                LocalDateTime.of(2022, 8, 12, 12, 0)));
        candidates.put(num.incrementAndGet(), new Candidate(
                num.get(),
                "Sergey Popov",
                "Learning Swift in sometimes",
                LocalDateTime.of(2022, 8, 12, 13, 30)));
    }

    public static CandidateStore instOf() {
        return INST;
    }

    public Collection<Candidate> findAll() {
        return candidates.values();
    }

    public void add(Candidate candidate) {
        num.incrementAndGet();
        candidate.setId(num.get());
        candidate.setCreated(LocalDateTime.now());
        candidates.put(num.get(), candidate);
    }
}
