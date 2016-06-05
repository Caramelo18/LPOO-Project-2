package com.mieicfeup.df.footpoly.model;

import android.util.Log;

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
        Log.w("GotoJail", "trigger");
        jail.addPlayer(player);
        return true;
    }
}
