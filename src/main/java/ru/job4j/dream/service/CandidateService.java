package ru.job4j.dream.service;

import ru.job4j.dream.model.Candidate;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class CandidateService {
    private static final CandidateService INST = new CandidateService();
    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();
    private final AtomicInteger num = new AtomicInteger();

    public CandidateService() {
    }

    public static CandidateService instOf() {
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

    public Candidate findById(int id) {
        return candidates.get(id);
    }

    public void update(Candidate candidate) {
        candidates.replace(candidate.getId(), candidate);
    }
}
