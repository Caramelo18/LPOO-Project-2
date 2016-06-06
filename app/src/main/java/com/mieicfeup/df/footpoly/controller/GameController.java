package com.mieicfeup.df.footpoly.controller;

import android.os.Bundle;

import com.mieicfeup.df.footpoly.model.Dice;
import com.mieicfeup.df.footpoly.model.Game;
import com.mieicfeup.df.footpoly.model.Place;
import com.mieicfeup.df.footpoly.model.Player;
import com.mieicfeup.df.footpoly.model.Stadium;
import com.mieicfeup.df.footpoly.view.BuyStadiumDialog;
import com.mieicfeup.df.footpoly.view.MortgageDialog;
import com.mieicfeup.df.footpoly.view.UpgradeStadiumDialog;

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

    /**
     * Starts the turn, deciding which function to call based on player being human or not
     * @return 0 if player is human and doesn't land on a new Stadium
     *         1 if player is human and lands on a new Stadium
     *         2 if player is bot
     */
    public int startTurn() {
        int rollValue = dice.rollDice();
        PlayerController player = this.playerList.get(currentPlayer);

        if (isCurrentPlayerHuman()) {
            return handleHumanTurn(rollValue, player);
        }
        else {
            handleBotTurn(rollValue, player);
            return 2;
        }
    }

    /**
     * Handles the human player turn
     * @param rollValue number of places to move
     * @param playerController current player controller
     * @return 1 if the player has landed on a new Stadium and 0 otherwise
     */
    private int handleHumanTurn(int rollValue, PlayerController playerController) {
        Player player = playerController.getPlayer();

        if(game.playerAtJail(player))
        {
            Place jail = game.getTable().getPlace(5);
            jail.trigger(player);
            return 0;
        }

        if(this.playerList.get(currentPlayer).movePlayer(rollValue))
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
     * Handles the bot player turn
     * @param rollValue number of places to move
     * @param playerController current player controller
     */
    private void handleBotTurn(int rollValue, PlayerController playerController) {

        Player player = playerController.getPlayer();

        if(game.playerAtJail(player))
        {
            Place jail = game.getTable().getPlace(5);
            jail.trigger(player);
            return;
        }

        if(this.playerList.get(currentPlayer).movePlayer(rollValue))
        {
            Place start = game.getTable().getPlace(0);
            start.trigger(player);
        }

        Place currPlace = game.getTable().getPlace(player.getIndex());
        if(!currPlace.trigger(player)) {
            playerController.buyStadium((Stadium) currPlace);
        }

        updateAllTexts();
        endTurn();
        return;
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
        Player player = playerList.get(currentPlayer).getPlayer();
        ArrayList<Stadium> a = game.stadiumsOwnedBy(player);
        args.putSerializable("stadiumList", a);
        dialog.setPlayer(playerList.get(currentPlayer));
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

    public UpgradeStadiumDialog showUpgradeStadiumDialog()
    {
        UpgradeStadiumDialog dialog = new UpgradeStadiumDialog();
        PlayerController player = playerList.get(currentPlayer);
        ArrayList<Stadium> stadiums = game.stadiumsOwnedBy(player.getPlayer());
        dialog.setData(stadiums, player);
        return dialog;
    }

    /**
     * @return true if current player is human and false otherwise
     */
    public boolean isCurrentPlayerHuman() {
        Player player = playerList.get(currentPlayer).getPlayer();
        return player.isHuman();
    }
}
