package com.example.workoutwarrior;

public class ProfileData{

    public ProfileData(){
        this("None");
    }

    public ProfileData(String name){
        this.name = name;
        experience = 0;
        strength = 10;
        dexterity = 10;
        constitution = 10;
        sQuest = 0;
        dQuest = 0;
        cQuest = 0;
    }

    public String name;
    public int experience;
    public int strength;
    public int dexterity;
    public int constitution;

    public int sQuest;
    public int dQuest;
    public int cQuest;
}