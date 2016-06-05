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

    public Jail()
    {
        players = new ArrayList<Player>();
        remainingTurns = new ArrayList<Integer>();
    }

    public boolean atJail(Player player)
    {
        return players.contains(player);
    }

    public void addPlayer(Player player)
    {
        players.add(player);
        remainingTurns.add(2);
        Log.w("players size", String.valueOf(players.size()));
        Log.w("remaining size", String.valueOf(remainingTurns.size()));
    }

    public boolean trigger(Player player)
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
            return true;

        int remaining = remainingTurns.get(index);
        --remaining;

        if(remaining == 0)
        {
            players.remove(player);
            remainingTurns.remove(index);
            Log.w("after remove", player.getName());
            Log.w("rem players size", String.valueOf(players.size()));
            Log.w("rem remaining size", String.valueOf(remainingTurns.size()));
            return true;
        }
        remainingTurns.set(index, remaining);
        return true;

    }
}
