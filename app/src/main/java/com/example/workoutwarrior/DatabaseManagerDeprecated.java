package com.example.workoutwarrior;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.Dictionary;
import java.util.Hashtable;
import android.util.Log;

public class DatabaseManagerDeprecated extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "warriorDB";
    private static final int DATABASE_VERSION = 1;
    private static final String PROFILE_TABLE = "profileTable";
    private static final String PLAYER_STATS = "playerStats";

    public DatabaseManagerDeprecated(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /* Create two tables in the database. One for storing information relating to the user's profile
       and another for keeping track of the players stats.
     */
    public void onCreate(SQLiteDatabase db) {
        createProfileTable(db);
        createStatsTable(db);
    }

    private void createProfileTable(SQLiteDatabase db) {
        String createProfileTable = "create table " + PROFILE_TABLE + " ( ID integer primary key, ";
        createProfileTable += " name text, password text, class text)";
        db.execSQL(createProfileTable);
    }

    private void createStatsTable(SQLiteDatabase db) {
        String createStatsTable = "create table " + PLAYER_STATS + " (ID integer primary key, ";
        createStatsTable += "level integer, strength integer, dexterity integer, constitution integer)";
        db.execSQL(createStatsTable);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + PROFILE_TABLE);
        db.execSQL("drop table if exists " + PLAYER_STATS);
        onCreate(db);
    }

    /* Inserts a new row into the profile table.
     */
    public void insertIntoProfile(int id, String username, String password, String pClass) {
        SQLiteDatabase db = this.getWritableDatabase();
        String insert = "insert into " + PROFILE_TABLE;
        insert += " values('" + id + "', '" + username + "', '" + password;
        insert += "', '" + pClass +  "')";
        db.execSQL(insert);
        db.close();
    }

    /* Inserts a new row into the stats table.
     */
    public void insertIntoStats(int level, int strength, int dexterity, int constitution, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String insert = "insert into " + PLAYER_STATS;
        insert += " values('" + id + "', '" + level + "', '" + strength;
        insert +=  "', '" + dexterity + "', '" + constitution + "')";
        db.execSQL(insert);
        db.close();
    }

    /* Updates a player's stats table. Note that you have to give it every value
     * even if one or more of them are unchanged.
     */
    public void updateStatsById(int id, Profile profile) {
        SQLiteDatabase db = this.getWritableDatabase();
        String update = "update " + PLAYER_STATS;
        //update += " set level = '" + profile.getLevel() + "', ";
        update += "strength = '" + profile.getStrength() + "', ";
        update += "dexterity = '" + profile.getDexterity() + "', ";
        update += "constitution = '" + profile.getConstitution() + "'";
        update += " where ID = " + id;

        db.execSQL(update);
        db.close();
    }

    /* Returns a hashtable of values for a given profile based on the id.
     * Example return value:   {"name": "BigMuscleGuy",
     *                          "class": "Knight",
     *                          "level": "30",
     *                          "strength": "30",
     *                          "dexterity": "3",
     *                          "constitution": "7"}
     */
    public Dictionary selectById(int id) {
        String[] profileInfo = getNameAndClass(id);
        String getStats = " select * from " + PLAYER_STATS + " where id = " + id;
        Dictionary playerStats = new Hashtable();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(getStats, null);

        if (cursor.moveToFirst()) {
            playerStats.put("name", profileInfo[0]);
            playerStats.put("class", profileInfo[1]);
            playerStats.put("level", cursor.getInt(1));
            playerStats.put("strength", cursor.getInt(2));
            playerStats.put("dexterity", cursor.getInt(3));
            playerStats.put("constitution", cursor.getInt(4));
        }

        return playerStats;
    }

    public int getPlayerId(String uName, String password){
        String idQuery = "select * from " + PROFILE_TABLE + " where name = '" + uName + "' and password = '" + password + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(idQuery, null);

        int id = -1;

        if (cursor.moveToFirst()) {
            id = cursor.getInt(cursor.getColumnIndex("ID"));
        }

        return id;
    }

    /* Returns a players name and class in an array of Strings.
     * The first index contains the player's name and the second
     * index contains the player's class.
     */
    private String[] getNameAndClass(int id) {
        String getProfile = "select * from " + PROFILE_TABLE + " where id = " + id;
        String[] nameClass = new String[2];

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(getProfile, null);

        if (cursor.moveToFirst()) {
            nameClass[0] = cursor.getString(1);
            nameClass[1] = cursor.getString(3);
        }
        return nameClass;
    }
}
