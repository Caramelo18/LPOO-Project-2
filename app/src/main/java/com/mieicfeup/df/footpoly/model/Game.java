package com.mieicfeup.df.footpoly.model;

import android.util.Log;
import android.util.SparseBooleanArray;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Diogo on 01/06/2016.
 */
public class Game implements Serializable {

    private ArrayList<Player> players;
    private Table table;

    /**
     * Game Constructor
     */
    public Game(ArrayList<String> playerNames, SparseBooleanArray checkedPlayers) {
        this.players = new ArrayList<Player>();
        this.table = new Table();

        for (int i = 0; i < playerNames.size(); i++) {
            players.add(new Player(playerNames.get(i), checkedPlayers.get(i)));
        }
    }

    /**
     * @return players
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * @return table
     */
    public Table getTable() {
        return table;
    }

    /**
     * Checks if the player is at the jail
     * @param player player to check
     * @return true if it is at jail, false otherwise
     */
    public boolean playerAtJail(Player player)
    {
        return table.playerAtJail(player);
    }

    public ArrayList<Stadium> stadiumsOwnedBy(Player player)
    {
        ArrayList<Stadium> stadiums = new ArrayList<Stadium>();

        for(int i = 0; i < table.getPlaceSize(); i++)
        {
            Place currPlace = table.getPlace(i);
            if(currPlace.getClass() == Stadium.class)
            {
                Stadium stadium = (Stadium) currPlace;
                if(stadium.getOwner() == player)
                    stadiums.add(stadium);
            }
        }
        return stadiums;
    }

    public ArrayList<Stadium> stadiumsNotOwnedBy(Player player)
    {
        ArrayList<Stadium> stadiums = new ArrayList<Stadium>();

        for(int i = 0; i < table.getPlaceSize(); i++)
        {
            Place currPlace = table.getPlace(i);
            if(currPlace.getClass() == Stadium.class)
            {
                Stadium stadium = (Stadium) currPlace;
                if(stadium.getOwner() != player && stadium.getOwner() != null)
                    stadiums.add(stadium);
            }
        }
        return stadiums;
    }

    public ArrayList<Stadium> upgradableStadiums(Player player)
    {
        ArrayList<Stadium> ownedBy = stadiumsOwnedBy(player);
        ArrayList<Stadium> upgradable = new ArrayList<Stadium>();
        ArrayList<String> countriesChecked = new ArrayList<String>();

        for(int i = 0; i < ownedBy.size(); i++)
        {
            ArrayList<Stadium> sameCountry = new ArrayList<Stadium>();
            if(!countriesChecked.contains(ownedBy.get(i).getCountry()))
            {
                sameCountry.add(ownedBy.get(i));
                countriesChecked.add(ownedBy.get(i).getCountry());
                for (int j = i + 1; j < ownedBy.size(); j++) {
                    if (ownedBy.get(i).getCountry().equals(ownedBy.get(j).getCountry())) {
                        sameCountry.add(ownedBy.get(j));
                    }
                }
                if (sameCountry.size() >= 2) {
                    upgradable.addAll(sameCountry);
                }
            }
        }

        return upgradable;
    }

}
