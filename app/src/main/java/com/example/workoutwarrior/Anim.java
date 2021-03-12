package com.example.workoutwarrior;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.Random;
public class Anim {


    private int deltaTime; // in milliSeconds

    private Rect duckRect;
    private int duckWidth;
    private int duckHeight;

    public Anim( Rect newDuckRect) {
        setDuckRect( newDuckRect );
    }

    public void setDeltaTime( int newDeltaTime ) {
        if( newDeltaTime > 0 )
            deltaTime = newDeltaTime;
    }

    public Rect getDuckRect( ) {
        return duckRect;
    }

    public void setDuckRect( Rect newDuckRect ) {
        if( newDuckRect != null ) {
            duckWidth = newDuckRect.right - newDuckRect.left;
            duckHeight = newDuckRect.bottom - newDuckRect.top;
            duckRect = newDuckRect;
        }
    }



}
