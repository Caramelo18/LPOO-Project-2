package com.mieicfeup.df.footpoly.controller;

import android.os.Bundle;

import com.mieicfeup.df.footpoly.model.Dice;
import com.mieicfeup.df.footpoly.model.Game;
import com.mieicfeup.df.footpoly.model.Place;
import com.mieicfeup.df.footpoly.model.Player;
import com.mieicfeup.df.footpoly.model.Stadium;
import com.mieicfeup.df.footpoly.view.BuyStadiumDialog;
import com.mieicfeup.df.footpoly.view.MortgageDialog;

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

    public int rollDice()
    {
        int rollValue = dice.rollDice();
        Player player = this.playerList.get(currentPlayer).getPlayer();

        if(game.playerAtJail(player))
        {
            Place jail = game.getTable().getPlace(5);
            jail.trigger(player);
            return 0;
        }

        if(this.playerList.get(currentPlayer).movePlayer(3))
        {
            Place start = game.getTable().getPlace(0);
            start.trigger(player);
        }

        Place currPlace = game.getTable().getPlace(player.getIndex());
        if(!currPlace.trigger(player))
            return 1;

        updateAllTexts();
        return 0;
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

    public MortgageDialog showMortgageDialog() {
        MortgageDialog dialog = new MortgageDialog();
        Bundle args = new Bundle();
        ArrayList<Stadium> a = new ArrayList<Stadium>();
        a.add((Stadium) game.getTable().getPlace(2));
        a.add((Stadium) game.getTable().getPlace(4));
        a.add((Stadium) game.getTable().getPlace(18));
        args.putSerializable("stadiumList", a);
        dialog.setArguments(args);
        return dialog;
    }

    public BuyStadiumDialog showBuyStadiumDialog()
    {
        BuyStadiumDialog dialog = new BuyStadiumDialog();
        Place currPlace = game.getTable().getPlace(playerList.get(currentPlayer).getPlayer().getIndex());
        Stadium stadium = (Stadium) currPlace;
        dialog.setData(stadium, playerList.get(currentPlayer));
        return dialog;
    }
}
