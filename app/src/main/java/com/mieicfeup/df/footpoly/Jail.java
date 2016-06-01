package com.mieicfeup.df.footpoly;

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
    }

    public boolean trigger(Player player)
    {
        int index = 0;
        for(int i = 0; i < players.size(); ++i)
        {
            if(players.get(i) == player)
            {
                index = i;
                break;
            }
        }

        int remaining = remainingTurns.get(index);
        --remaining;

        if(remaining == 0)
        {
            players.remove(index);
            remainingTurns.remove(index);
            return true;
        }
        remainingTurns.set(index, remaining);
        return true;

    }
}
