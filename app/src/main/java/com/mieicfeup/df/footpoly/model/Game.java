package com.mieicfeup.df.footpoly.model;

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
    public Game() {
        this.players = new ArrayList<Player>();
        this.table = new Table();
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
}
