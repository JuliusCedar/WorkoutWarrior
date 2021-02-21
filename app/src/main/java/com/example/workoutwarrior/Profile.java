package com.example.workoutwarrior;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Profile {

    private static Profile INSTANCE = null;

    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static DatabaseReference dRef = database.getReference();

    private String name;
    private int experience;
    private int strength;
    private int dexterity;
    private int constitution;

    private int sQuest;
    private int dQuest;
    private int cQuest;

    //Get profile, create one if one has not already been created
    //Enforces only one instance
    public static Profile getProfile(){
        if(INSTANCE == null){
            INSTANCE = new Profile();
        }
        return INSTANCE;
    }

    //update the profile to a new profile
    public static void loadProfile(Profile profile){
        INSTANCE = profile;
    }

    private Profile(){
        name = "NONE";
        experience = -1;
        strength = -1;
        dexterity = -1;
        constitution = -1;
    }

    public int getStrength() {
        return strength;
    }

    public int getConstitution() {
        return constitution;
    }

    public int getDexterity() {
        return dexterity;
    }

    private void setStrength(int strength){ this.strength = strength; }

    private void setDexterity(int strength){ this.strength = strength; }

    private void setConstitution(int strength){ this.strength = strength; }

    public int getExperience() {
        return experience;
    }

    public String getName() {
        return name;
    }

    public int getsQuest(){ return sQuest; }

    public int getdQuest(){ return dQuest; }

    public int getcQuest(){ return cQuest; }

    private void setsQuest(int sQuest){ this.sQuest = sQuest; }

    private void setdQuest(int dQuest){ this.dQuest = dQuest; }

    private void setcQuest(int cQuest){ this.cQuest = cQuest; }
}
