package ru.job4j.dream.service;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.store.CandidateStore;

import java.util.Collection;

public class CandidateService {
    private final CandidateStore store = CandidateStore.instOf();
    private static final CandidateService CANDIDATE_SERVICE = new CandidateService();

    private CandidateService() {
    }

    public static CandidateService instOf() {
        return CANDIDATE_SERVICE;
    }

    public Collection<Candidate> findAll() {
        return store.findAll();
    }

    public void update(Candidate candidate) {
        store.update(candidate);
    }

    public void add(Candidate candidate) {
        store.add(candidate);
    }

    public Candidate findById(int id) {
        return store.findById(id);
    }
}
