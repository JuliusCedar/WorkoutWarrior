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
    private Bitmap [ ] ducks;
    private int duckFrame;

    private Anim anim;

    public AnimView(Context context, int width, int height, int [] TARGET ) {
        super( context );

        TARGETS = TARGET;
        ducks = new Bitmap[TARGETS.length];
        for( int i = 0; i < ducks.length; i++ )
            ducks[i] = BitmapFactory.decodeResource( getResources( ), TARGETS[i] );
        float scale = ( ( float ) width / ( ducks[0].getWidth( ) * 5 ) );

        Rect duckRect = new Rect( 0, 0, width, (int) (height)/4);
        anim = new Anim( duckRect);

        anim.setDeltaTime( DELTA_TIME );

        paint = new Paint( );
        paint.setColor( 0xFF000000 );
        paint.setAntiAlias( true );

    }

    public void onDraw( Canvas canvas ) {
        super.onDraw( canvas );

        // draw animated duck
        duckFrame = ( duckFrame + 1 ) % ducks.length;
        if( false )
            canvas.drawBitmap( ducks[0], null,
                    anim.getDuckRect( ), paint );
        else
            canvas.drawBitmap( ducks[duckFrame], null,
                    anim.getDuckRect( ), paint );
    }

    public Anim getAnim( ) {
        return anim;
    }

}
