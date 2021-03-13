package com.example.workoutwarrior;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

public class AnimView extends View{

    public static int DELTA_TIME = 100;
    private int [ ] TARGETS;
    private Paint paint;
    private Bitmap [ ] frames;
    private int animFrame;

    private Anim anim;

    public AnimView(Context context, int width, int height, int [] TARGET ) {
        super( context );

        TARGETS = TARGET;
        frames = new Bitmap[TARGETS.length];
        for(int i = 0; i < frames.length; i++ )
            frames[i] = BitmapFactory.decodeResource( getResources( ), TARGETS[i] );
        float scale = ( ( float ) width / ( frames[0].getWidth( ) * 5 ) );

        Rect animRect = new Rect( 0, 0, width, (int) (height)/4);
        anim = new Anim( animRect);

        anim.setDeltaTime( DELTA_TIME );

        paint = new Paint( );
        paint.setColor( 0xFF000000 );
        paint.setAntiAlias( true );

    }

    public void onDraw( Canvas canvas ) {
        super.onDraw( canvas );

        animFrame = ( animFrame + 1 ) % frames.length;
        if( false )
            canvas.drawBitmap( frames[0], null,
                    anim.getAnimRect( ), paint );
        else
            canvas.drawBitmap( frames[animFrame], null,
                    anim.getAnimRect( ), paint );
    }

    public Anim getAnim( ) {
        return anim;
    }

}