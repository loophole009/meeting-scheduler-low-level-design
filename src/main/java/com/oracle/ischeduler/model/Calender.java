package com.oracle.ischeduler.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class Calender {
    private List<Meeting> meetings;
    public Calender(){
        this.meetings = new ArrayList<>();
    }
}
