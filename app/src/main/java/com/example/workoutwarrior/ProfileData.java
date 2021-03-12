package com.example.workoutwarrior;

import java.util.ArrayList;

public class ProfileData{

    public ProfileData(){
        this("None");
    }

    public ProfileData(String name){
        this.name = name;
        strength = 10;
        dexterity = 10;
        constitution = 10;
        sQuest = 0;
        dQuest = 0;
        cQuest = 0;
        achievements = new ArrayList<>();
    }

    public String name;
    public int strength;
    public int dexterity;
    public int constitution;

    public int sQuest;
    public int dQuest;
    public int cQuest;

    public ArrayList<String> achievements;
}