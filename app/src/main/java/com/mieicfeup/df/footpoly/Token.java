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
    private int balance;

    public Token(ImageView token)
    {
        this.pino = token;
        this.index = 0;
        this.pino.setX(0);
        this.pino.setY(130);
        this.balance = 5000;
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

    public int getIndex()
    {
        return this.index;
    }
    private void updatePosition()
    {
        if(this.index >= 0 && this.index <= 5)
            pino.setY(130);
        else if(this.index >= 10 && this.index <= 15)
            pino.setY(780);
        else if(this.index == 6 || this.index == 19)
            pino.setY(265);
        else if(this.index == 7 || this.index == 18)
            pino.setY(395);
        else if(this.index == 8 || this.index == 17)
            pino.setY(530);
        else if(this.index == 9 || this.index == 16)
            pino.setY(660);

        if(this.index == 0 || (this.index >= 15 && this.index <= 19))
            pino.setX(0);
        else if(this.index >= 5 && this.index <= 10)
            pino.setX(650);
        else if(this.index == 1 || this.index == 14)
            pino.setX(130);
        else if(this.index == 2 || this.index == 13)
            pino.setX(260);
        else if(this.index == 3 || this.index == 12)
            pino.setX(390);
        else if(this.index == 4 || this.index == 11)
            pino.setX(520);
    }

    public int getBalance()
    {
        return balance;
    }

    public boolean decBalance(int ammount)
    {
        if(ammount > this.balance)
            return false;

        this.balance -= ammount;
        return true;
    }

    public void incBalance(int ammount)
    {
        this.balance += ammount;
    }

}
