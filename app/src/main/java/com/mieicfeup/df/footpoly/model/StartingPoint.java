package com.mieicfeup.df.footpoly.model;

/**
 * Created by fabio on 01/06/2016.
 */
public class StartingPoint extends Place
{
    public StartingPoint(){}
    public boolean trigger(Player player)
    {
        player.incBalance(500);
        return true;
    }
}
