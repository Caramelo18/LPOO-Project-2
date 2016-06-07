package com.mieicfeup.df.footpoly.model;

import android.util.Log;

/**
 * Created by fabio on 01/06/2016.
 */
public class GoToJail extends Place
{
    private Jail jail;

    /**
     * Normal constructor
     * @param jail jail associated
     */
    public GoToJail(Jail jail)
    {
        this.jail = jail;
    }

    /**
     * Adds the player to the jail
     * @param player player to add to jail
     * @return no dialog enum
     */
    public dialogType trigger(Player player)
    {
        Log.w("GotoJail", "trigger");
        jail.addPlayer(player);
        return dialogType.NODIALOG;
    }
}
