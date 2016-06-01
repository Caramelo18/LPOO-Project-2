package com.mieicfeup.df.footpoly;

/**
 * Created by fabio on 01/06/2016.
 */
public class GoToJail extends Place
{
    private Jail jail;

    public GoToJail(Jail jail)
    {
        this.jail = jail;
    }

    public boolean trigger(Player player)
    {
        jail.addPlayer(player);
        return true;
    }
}
