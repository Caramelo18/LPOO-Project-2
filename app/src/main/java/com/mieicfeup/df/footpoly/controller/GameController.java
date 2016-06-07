package com.mieicfeup.df.footpoly.controller;

import android.os.Bundle;
import android.util.Log;

import com.mieicfeup.df.footpoly.model.Dice;
import com.mieicfeup.df.footpoly.model.Game;
import com.mieicfeup.df.footpoly.model.Jail;
import com.mieicfeup.df.footpoly.model.Luck;
import com.mieicfeup.df.footpoly.model.Place;
import com.mieicfeup.df.footpoly.model.Player;
import com.mieicfeup.df.footpoly.model.Stadium;
import com.mieicfeup.df.footpoly.view.InfoDialog;
import com.mieicfeup.df.footpoly.view.BuyStadiumDialog;
import com.mieicfeup.df.footpoly.view.LuckDialog;
import com.mieicfeup.df.footpoly.view.MortgageDialog;
import com.mieicfeup.df.footpoly.view.UpgradeStadiumDialog;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Diogo on 01/06/2016.
 */
public class GameController {
    private int currentPlayer;
    private final ArrayList<PlayerController> playerList;
    private final Game game;
    private static final Dice dice = Dice.getDice();

    /**
     * Game controller constructor
     * @param game game object
     */
    public GameController(Game game) {
        this.game = game;
        this.currentPlayer = game.getCurrentPlayerController();
        this.playerList = new ArrayList<>();

        for (int i = 0; i < game.getPlayers().size(); i++) {
            playerList.add(i, new PlayerController(game.getPlayers().get(i)));
        }

    }

    /**
     * Shuffles players to randomize playing order
     */
    public void shufflePlayers()
    {
        if (game.isNewGame()) {
            Collections.shuffle(playerList);
            playerList.get(0).strokeText();
            game.setNewGame();
        }
        else {
            endTurn();
        }
    }

    /**
     * @return players list
     */
    public ArrayList<PlayerController> getPlayerList() {
        return playerList;
    }

    /**
     * Starts the turn, deciding which function to call based on player being human or not
     * @return 0 if player is human and doesn't land on a new Stadium
     *         1 if player is human and lands on a new Stadium
     *         2 if player is human and lands on Luck
     *         3 if player is bot
     *         4 if game has ended
     */
    public int startTurn() {
        if(!game.getGameEnded())
        {
            int rollValue = dice.rollDice();
            PlayerController player = this.playerList.get(currentPlayer);
            boolean bankrupt = player.getPlayer().isBankrupt();
            Log.w( player.getPlayer().getName(), String.valueOf(player.getPlayer().getBalance()));
            if(!bankrupt)
            {
                if (isCurrentPlayerHuman()) {
                    game.updateGameStatus();
                    return handleHumanTurn(rollValue, player);
                } else {
                    handleBotTurn(rollValue, player);
                    game.updateGameStatus();
                    return 3;
                }
            }
            else
                endTurn();
        }
        showWinner();
        return 4;
    }

    /**
     * Handles the human player turn
     * @param rollValue number of places to move
     * @param playerController current player controller
     * @return 1 if the player has landed on a new Stadium, 2 if landed on luck and 0 otherwise
     */
    private int handleHumanTurn(int rollValue, PlayerController playerController) {
        Player player = playerController.getPlayer();

        if(game.playerAtJail(player))
        {
            Place jail = game.getTable().getPlace(5);
            jail.trigger(player);
            Log.w("at jail", player.getName());
            showJailDialog(player, game.getTable().getJail());
            return 0;
        }

        PlayerController.moveTo target = this.playerList.get(currentPlayer).movePlayer(rollValue);
        if(target == PlayerController.moveTo.START)
        {
            Place start = game.getTable().getPlace(0);
            start.trigger(player);
        }
        else if(target == PlayerController.moveTo.GOTOJAIL)
        {
            Jail jail = game.getTable().getJail();
            jail.addPlayer(player);
        }

        if(player.getIndex() != 0)
        {
            Place currPlace = game.getTable().getPlace(player.getIndex());

            Place.dialogType dialogType = currPlace.trigger(player);
            if (dialogType.equals(Place.dialogType.BUYSTADIUM))
                return 1;
            else if (dialogType.equals(Place.dialogType.LUCK))
                return 2;
        }

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
            String message = playerList.get(currentPlayer).getPlayer().getName() + " at jail";
            showBotDialog(player, message);
            endTurn();
            return;
        }

        PlayerController.moveTo target = this.playerList.get(currentPlayer).movePlayer(rollValue);
        if(target == PlayerController.moveTo.START)
        {
            Place start = game.getTable().getPlace(0);
            start.trigger(player);
        }
        else if(target == PlayerController.moveTo.GOTOJAIL)
        {
            Jail jail = game.getTable().getJail();
            jail.addPlayer(player);
        }

        if(player.getIndex() != 0)
        {
            Place currPlace = game.getTable().getPlace(player.getIndex());
            Place.dialogType dialogType = currPlace.trigger(player);

            if (dialogType.equals(Place.dialogType.BUYSTADIUM)) {
                playerController.buyStadium((Stadium) currPlace);
                String message = "Bot bought " + ((Stadium) currPlace).getName();
                showBotDialog(player, message);
            } else if (dialogType.equals(Place.dialogType.LUCK)) {
                Log.w("dialog type", "luck");
                String message = "Bot in Luck place";
                showBotDialog(player, message);
            } else {
                if (currPlace.getClass() == Stadium.class) {
                    Stadium s = (Stadium) currPlace;
                    if (s.getOwner() != player) {
                        String message = "Bot paid to " + s.getOwner().getName() + " on " + s.getName();
                        showBotDialog(player, message);
                    }
                }
            }
        }
        updateAllTexts();
        endTurn();
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

        game.setCurrentPlayerController(currentPlayer);

        playerList.get(currentPlayer).strokeText();
    }

    /**
     * Updates all players textviews containing player name and balance
     */
    private void updateAllTexts()
    {
        for(int i = 0; i < playerList.size(); i++)
            playerList.get(i).updateText();
    }

    /**
     * Sets a new MortgageDialog
     * @return MortgageDialog generated
     */
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

    /**
     * Sets a new BuyStadiumDialog
     * @return BuyStadiumDialog generated
     */
    public BuyStadiumDialog showBuyStadiumDialog()
    {
        BuyStadiumDialog dialog = new BuyStadiumDialog();
        Place currPlace = game.getTable().getPlace(playerList.get(currentPlayer).getPlayer().getIndex());
        Stadium stadium = (Stadium) currPlace;
        dialog.setData(stadium, playerList.get(currentPlayer));
        return dialog;
    }

    /**
     * Sets a new UpgradeStadiumDialog
     * @return UpgradeStadiumDialog generated
     */
    public UpgradeStadiumDialog showUpgradeStadiumDialog()
    {
        UpgradeStadiumDialog dialog = new UpgradeStadiumDialog();
        PlayerController player = playerList.get(currentPlayer);
        ArrayList<Stadium> stadiums = game.upgradableStadiums(player.getPlayer());
        dialog.setData(stadiums, player);
        return dialog;
    }

    /**
     * Sets a new LuckDialog
     * @return LuckDialog generated
     */
    public LuckDialog showLuckDialog()
    {
        LuckDialog dialog = new LuckDialog();
        Luck luck = game.getTable().getLuck();
        int amount = luck.getAmount();
        PlayerController player = playerList.get(currentPlayer);
        dialog.setData(amount, player);
        return dialog;
    }

    /**
     * Shows a new BotDialog
     * @param player bot player
     * @param botMessage message to display
     */
    private void showBotDialog(Player player, String botMessage)
    {
        InfoDialog dialog = new InfoDialog();
        dialog.setData(player, botMessage);
        dialog.showDialog();
    }

    /** Indicates that the player is in jail and the remaining turns
     * @param player Player
     * @param jail Jail
     */
    private void showJailDialog(Player player, Jail jail)
    {
        InfoDialog dialog = new InfoDialog();
        dialog.setData(player, "You're in jail for the next " + jail.getRemainingTurns(player) + " turns.");
        dialog.showDialog();
    }

    /**
     * Shows a dialog with the winner name
     */
    private void showWinner()
    {
        Player winner = game.getWinner();
        String message;
        if(winner.isHuman())
            message = winner.getName() + " won the game. Congratulations!";
        else
            message = winner.getName() + " won the game.";
        showBotDialog(winner, message);
    }

    /**
     * @return true if current player is human, false otherwise
     */
    public boolean isCurrentPlayerHuman() {
        Player player = playerList.get(currentPlayer).getPlayer();
        return player.isHuman();
    }

    /**
     * @return game
     */
    public Game getGame() {
        return game;
    }
}
