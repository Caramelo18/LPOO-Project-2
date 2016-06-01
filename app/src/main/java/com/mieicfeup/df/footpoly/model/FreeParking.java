package com.mieicfeup.df.footpoly.model;

/**
 * Created by fabio on 01/06/2016.
 */
public class FreeParking extends Place
{
    private int ammount;

    public FreeParking()
    {
        this.ammount = 0;
    }

    public boolean trigger(Player player)
    {
        player.incBalance(0);
        return true;
    }
}

