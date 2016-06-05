package com.mieicfeup.df.footpoly.model;

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
     * Returns the Place object the specified player is on
     * @param playerIndex index of the player
     * @return the Place
     */
    public Place getPlayerPlace(int playerIndex) {
        int playerCurrPlace = players.get(playerIndex).getIndex();
        return table.getPlaces(playerCurrPlace);
    }
}
