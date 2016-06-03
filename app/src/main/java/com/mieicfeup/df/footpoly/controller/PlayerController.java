package com.mieicfeup.df.footpoly.controller;

import android.util.Log;

import com.mieicfeup.df.footpoly.model.Player;

/**
 * Created by Diogo on 01/06/2016.
 */
public class PlayerController {
    Player player;

    public PlayerController(Player player) {
        this.player = player;
    }
    /**
     * @return player
     */
    public Player getPlayer() {
        return this.player;
    }

    public void increaseIndex(int inc)
    {
        this.player.incrementIndex(inc);
        if(this.player.getIndex() > 19)
            this.player.incrementIndex(-20);

        String message = "increase: " + String.valueOf(inc) + " " + String.valueOf(this.player.getIndex());
        Log.w("Player Footpoly", message);
    }
}
