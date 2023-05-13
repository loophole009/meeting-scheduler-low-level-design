package com.oracle.ischeduler.model;

import java.util.UUID;

import lombok.Getter;

@Getter
public class Candidate {
    private final String id;
    private String name;
    private String email;
    // public void respondInvitation(Notification invite);

    public Candidate(String name, String email){
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
    }
    
}
