package com.mieicfeup.df.footpoly.model;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by fabio on 01/06/2016.
 */
public class Jail extends Place
{
    private ArrayList<Player> players;
    private ArrayList<Integer> remainingTurns;

    /**
     * Default constructor
     */
    public Jail()
    {
        players = new ArrayList<>();
        remainingTurns = new ArrayList<>();
    }

    /**
     * Checks if a player is at jail
     * @param player player to check
     * @return true if player is at jail, false otherwise
     */
    public boolean atJail(Player player)
    {
        return players.contains(player);
    }

    /**
     * Adds a player to jail
     * @param player to add to jail
     */
    public void addPlayer(Player player)
    {
        players.add(player);
        remainingTurns.add(3);
    }

    /**
     * @param player player
     * @return Number of turns player still has to be in jail for
     */
    public Integer getRemainingTurns(Player player) {
        int playerIndex = players.indexOf(player);
        if (playerIndex == -1)
            return 0;
        else
            return remainingTurns.get(playerIndex);
    }

    /**
     * Decreases number of turns remaining of a player if it is in jail or releases the player from jail if there are no turns remaining
     * @param player player to check
     * @return no dialog enum
     */
    public dialogType trigger(Player player)
    {
        Log.w("Jail", "trigger");
        int index = -1;
        for(int i = 0; i < players.size(); ++i)
        {
            if(players.get(i) == player)
            {
                index = i;
                break;
            }
        }

        if(index == -1)
            return dialogType.NODIALOG;

        int remaining = remainingTurns.get(index);
        --remaining;

        if(remaining == 0)
        {
            players.remove(player);
            remainingTurns.remove(index);
            return dialogType.NODIALOG;
        }
        remainingTurns.set(index, remaining);
        return dialogType.NODIALOG;

    }
}
