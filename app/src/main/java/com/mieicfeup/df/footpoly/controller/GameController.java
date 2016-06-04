package com.mieicfeup.df.footpoly.controller;

import android.util.Log;

import com.mieicfeup.df.footpoly.model.Dice;
import com.mieicfeup.df.footpoly.model.Game;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Diogo on 01/06/2016.
 */
public class GameController {
    private int currentPlayer;
    private ArrayList<PlayerController> playerList;
    private Game game;
    private Dice dice;

    /**
     * Game controller constructor
     * @param game
     */
    public GameController(Game game) {
        this.game = game;
        this.dice = new Dice();
        this.currentPlayer = 0;
        this.playerList = new ArrayList<PlayerController>();

        for (int i = 0; i < game.getPlayers().size(); i++) {
            playerList.add(i, new PlayerController(game.getPlayers().get(i)));
        }

        Collections.shuffle(playerList);
    }

    /**
     * @return players list
     */
    public ArrayList<PlayerController> getPlayerList() {
        return playerList;
    }

    /**
     * @return current player index
     */
    public int getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Updates currentPlayer to the next player
     */
    public void endTurn() {
        if (currentPlayer == playerList.size() - 1)
            currentPlayer = 0;
        else
            currentPlayer++;

        int rollValue = dice.rollDice();

        Log.w("Roll Value", Integer.toString(rollValue));

        this.playerList.get(currentPlayer).movePlayer(rollValue);
    }
}
