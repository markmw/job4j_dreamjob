package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CandidateStore {
    private static final CandidateStore INST = new CandidateStore();
    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private CandidateStore() {
        candidates.put(1, new Candidate(
                1,
                "Addy Bay",
                "Learning Java in sometimes",
                "2022-08-11"));
        candidates.put(2, new Candidate(
                2,
                "Ivan Ivanov",
                "Learning C++ in sometimes",
                "2021-11-11"));
        candidates.put(3, new Candidate(
                3,
                "Sergey Popov",
                "Learning Swift in sometimes",
                "2022-12-12"));
    }

    public static CandidateStore instOf() {
        return INST;
    }

    public Collection<Candidate> findAll() {
        return candidates.values();
    }
}
