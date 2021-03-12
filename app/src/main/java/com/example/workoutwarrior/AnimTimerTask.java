package com.example.workoutwarrior;

import java.util.TimerTask;

public class AnimTimerTask extends TimerTask {

    private Anim anim;
    private AnimView animView;

    public AnimTimerTask( AnimView view ) {
        animView = view;
        anim = view.getAnim( );

    }

    public void run( ) {

        animView.postInvalidate( );
    }

}
