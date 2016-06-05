package com.mieicfeup.df.footpoly.controller;

import android.graphics.drawable.GradientDrawable;
import android.util.Log;

import com.mieicfeup.df.footpoly.model.Dice;
import com.mieicfeup.df.footpoly.model.Game;
import com.mieicfeup.df.footpoly.model.Place;
import com.mieicfeup.df.footpoly.model.Player;
import com.mieicfeup.df.footpoly.model.Stadium;

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


    }

    public void shufflePlayers()
    {
        Collections.shuffle(playerList);
        playerList.get(0).strokeText();
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

    public void rollDice()
    {
        int rollValue = dice.rollDice();
        this.playerList.get(currentPlayer).movePlayer(rollValue);

        Player player = this.playerList.get(currentPlayer).getPlayer();
        Place currPlace = game.getTable().getPlaces(player.getIndex());

        currPlace.trigger(player);
        updateAllTexts();
    }

    /**
     * Updates currentPlayer to the next player
     */
    public void endTurn() {
        playerList.get(currentPlayer).clearStroke();
        if (currentPlayer == playerList.size() - 1)
            currentPlayer = 0;
        else
            currentPlayer++;

        playerList.get(currentPlayer).strokeText();
    }

    public void updateAllTexts()
    {
        for(int i = 0; i < playerList.size(); i++)
            playerList.get(i).updateText();
    }
}
