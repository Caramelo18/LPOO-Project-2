package com.mieicfeup.df.footpoly.model;

import android.util.Log;

import java.util.Random;

/**
 * Created by fabio on 01/06/2016.
 */
public class Luck extends Place
{
    private FreeParking freeParking;
    private Random rand;

    public Luck(FreeParking freeParking)
    {
        this.freeParking = freeParking;
        rand = new Random();
    }

    public boolean trigger(Player player)
    {
        int card = rand.nextInt(10);
        int times = rand.nextInt(3) + 1;
        int ammount = 200 * times;
        if (card < 6) // player must play
        {
            Log.w("luck", "pay");
            return player.decBalance(ammount);
        }
        else // player will receive money
        {
            player.incBalance(ammount);
            Log.w("luck", "receive");
            return true;
        }
    }
}
