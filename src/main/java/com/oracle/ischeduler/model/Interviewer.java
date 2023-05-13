package com.oracle.ischeduler.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
import java.util.TreeMap;

@Getter
public class Interviewer {

    private final String id;
    private final String name;
    @Setter
    private boolean isAvailable;
    private TreeMap < Integer, Integer > bookedIntervals;

    public Interviewer(final String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.isAvailable = true;
        this.bookedIntervals = new TreeMap < > ();
    }
}