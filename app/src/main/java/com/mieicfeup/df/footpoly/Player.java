package com.mieicfeup.df.footpoly;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by fabio on 30/04/2016.
 */
public class Player
{
    private ImageView token;
    private TextView text;
    private int index;
    private int balance;
    private int number;

    public Player(ImageView token, TextView text, int number)
    {
        this.token = token;
        this.text = text;
        this.number = number;
        this.index = 0;
        this.token.setX(0);
        this.token.setY(130);
        this.balance = 5000;
    }

    public void increaseIndex(int inc)
    {
        this.index += inc;
        if(this.index > 19)
            this.index -= 20;

        String message = "increase: " + String.valueOf(inc) + " " + String.valueOf(this.index);
        Log.w("Player Footpoly", message);
        updatePosition();
    }

    public int getIndex()
    {
        return this.index;
    }
    private void updatePosition()
    {
        Thread t = new Thread()
        {
            public void run() {
                if (index >= 0 && index <= 5)
                    token.setY(130);
                else if (index >= 10 && index <= 15)
                    token.setY(780);
                else if (index == 6 || index == 19)
                    token.setY(265);
                else if (index == 7 || index == 18)
                    token.setY(395);
                else if (index == 8 || index == 17)
                    token.setY(530);
                else if (index == 9 || index == 16)
                    token.setY(660);

                if (index == 0 || (index >= 15 && index <= 19))
                    token.setX(0);
                else if (index >= 5 && index <= 10)
                    token.setX(650);
                else if (index == 1 || index == 14)
                    token.setX(130);
                else if (index == 2 || index == 13)
                    token.setX(260);
                else if (index == 3 || index == 12)
                    token.setX(390);
                else if (index == 4 || index == 11)
                    token.setX(520);
            }
        };
        t.start();
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

    public void updateText()
    {
        String newText = "Player " + this.number + ": " + this.balance;
        text.setText(newText);
    }

    public int getNumber()
    {
        return number;
    }

}
