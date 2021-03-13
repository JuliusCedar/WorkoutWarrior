package com.example.workoutwarrior;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.Random;
public class Anim {


    private int deltaTime; // in milliSeconds

    private Rect animRect;
    private int animWidth;
    private int animHeight;

    public Anim( Rect newAnimRect) {
        setAnimRect( newAnimRect );
    }

    public void setDeltaTime( int newDeltaTime ) {
        if( newDeltaTime > 0 )
            deltaTime = newDeltaTime;
    }

    public Rect getAnimRect( ) {
        return animRect;
    }

    public void setAnimRect( Rect newAnimRect ) {
        if( newAnimRect != null ) {
            animWidth = newAnimRect.right - newAnimRect.left;
            animHeight = newAnimRect.bottom - newAnimRect.top;
            animRect = newAnimRect;
        }
    }



}
