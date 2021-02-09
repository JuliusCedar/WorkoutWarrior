package com.example.workoutwarrior;

public class Profile {

    private static Profile INSTANCE = null;

    private String name;
    private int level;
    private String playerClass;
    private int Str;
    private int Dex;
    private int Con;

    //Get profile, create one if one has not already been created
    //Enforces only one instance
    public static Profile getProfile(){
        if(INSTANCE == null){
            INSTANCE = new Profile();
        }
        return INSTANCE;
    }

    //update the profile to a new profile
    public static void loadProfile(String name, int level, String pClass, int str, int dex, int con){
        INSTANCE.name = name;
        INSTANCE.level = level;
        INSTANCE.playerClass = pClass;
        INSTANCE.Str = str;
        INSTANCE.Dex = dex;
        INSTANCE.Con = con;
    }

    private Profile(){
        name = "NONE";
        level = -1;
        playerClass = "NONE";
        Str = -1;
        Dex = -1;
        Con = -1;
    }

    public int getStr() {
        return Str;
    }

    public int getCon() {
        return Con;
    }

    public int getDex() {
        return Dex;
    }

    public int getLevel() {
        return level;
    }

    public String getName() {
        return name;
    }

    public String getPlayerClass() {
        return playerClass;
    }
}
