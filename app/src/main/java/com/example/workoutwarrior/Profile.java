package com.example.workoutwarrior;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Profile {

    private static Profile INSTANCE = null;

    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static DatabaseReference dRef = database.getReference();

    private ProfileData data;

    //Get profile, create one if one has not already been created
    //Enforces only one instance
    public static Profile getProfile(){
        if(INSTANCE == null){
            INSTANCE = new Profile();
        }
        return INSTANCE;
    }

    //update the profile to a new profile
    public void loadProfileData(ProfileData data){
        this.data = data;
    }

    private Profile(){
        data = new ProfileData();
    }

    public String getName() {
        return data.name;
    }

    public int getExperience() {
        return data.experience;
    }

    public int getStrength() {
        return data.strength;
    }

    public int getConstitution() {
        return data.constitution;
    }

    public int getDexterity() {
        return data.dexterity;
    }

    public int getsQuest(){ return data.sQuest; }

    public int getdQuest(){ return data.dQuest; }

    public int getcQuest(){ return data.cQuest; }

    public void setData(ProfileData newData){
        data = newData;
    }
}
