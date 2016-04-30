package com.mieicfeup.df.footpoly;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by fabio on 30/04/2016.
 */
public class Token
{
    private ImageView pino;
    private int index;

    public Token(ImageView token)
    {
        this.pino = token;
        this.index = 0;
        this.pino.setX(0);
        this.pino.setY(40);
    }

    public void increaseIndex(int inc)
    {
        this.index += inc;
        if(this.index > 19)
            this.index -= 20;

        String message = "increase: " + String.valueOf(inc) + " " + String.valueOf(this.index);
        Log.w("Token Footpoly", message);
        updatePosition();
    }
    private void updatePosition()
    {
        if(this.index >= 0 && this.index <= 5)
            pino.setY(40);
        else if(this.index >= 10 && this.index <= 15)
            pino.setY(690);
        else if(this.index == 6 || this.index == 19)
            pino.setY(175);
        else if(this.index == 7 || this.index == 18)
            pino.setY(305);
        else if(this.index == 8 || this.index == 17)
            pino.setY(440);
        else if(this.index == 9 || this.index == 16)
            pino.setY(570);

        if(this.index == 0 || (this.index >= 15 && this.index <= 19))
            pino.setX(0);
        else if(this.index >= 5 && this.index <= 10)
            pino.setX(630);
        else if(this.index == 1 || this.index == 14)
            pino.setX(110);
        else if(this.index == 2 || this.index == 13)
            pino.setX(240);
        else if(this.index == 3 || this.index == 12)
            pino.setX(370);
        else if(this.index == 4 || this.index == 11)
            pino.setX(500);
    }
}
