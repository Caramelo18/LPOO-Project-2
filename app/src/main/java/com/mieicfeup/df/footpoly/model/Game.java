package com.mieicfeup.df.footpoly.model;

import android.util.SparseBooleanArray;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Diogo on 01/06/2016.
 */
public class Game implements Serializable {

    private static final long serialVersionUID = -9052951549344457625L;
    private int currentPlayerController;
    private final ArrayList<Player> players;
    private final Table table;
    private boolean gameEnded;
    private boolean newGame;

    /**
     * Game Constructor
     * @param playerNames ArrayList containing players names
     * @param checkedPlayers SparseBooleanArray containing whether its human or not
     */
    public Game(ArrayList<String> playerNames, SparseBooleanArray checkedPlayers) {
        this.players = new ArrayList<>();
        this.table = new Table();
        this.currentPlayerController = 0;
        this.newGame = true;

        for (int i = 0; i < playerNames.size(); i++) {
            players.add(new Player(playerNames.get(i), checkedPlayers.get(i)));
        }
        this.gameEnded = false;
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
     * Sets newGame as false
     */
    public void setNewGame() {
        newGame = false;
    }

    /**
     * @return newGame
     */
    public boolean isNewGame() {
        return newGame;
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

    /**
     * @return player controller index
     */
    public int getCurrentPlayerController() {
        return currentPlayerController;
    }

    /**
     * @param currentPlayerController player controller index
     */
    public void setCurrentPlayerController(int currentPlayerController) {
        this.currentPlayerController = currentPlayerController;
    }


    /**
     * Returns an Arraylist of stadiums owned by player
     * @param player player to check
     * @return an ArrayList of Stadiums owned by player
     */
    public ArrayList<Stadium> stadiumsOwnedBy(Player player)
    {
        ArrayList<Stadium> stadiums = new ArrayList<>();

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

    /**
     * Returns an Arraylist of stadiums owned by other players than player
     * @param player player to check
     * @return an ArrayList of Stadiums owned by other players than player
     */
    public ArrayList<Stadium> stadiumsNotOwnedBy(Player player)
    {
        ArrayList<Stadium> stadiums = new ArrayList<>();

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

    /**
     * Returns an Arraylist of stadiums owned by player and upgradable (stadiums whom
     * @param player player to check
     * @return an ArrayList of Stadiums owned by player
     */
    public ArrayList<Stadium> upgradableStadiums(Player player)
    {
        ArrayList<Stadium> ownedBy = stadiumsOwnedBy(player);
        ArrayList<Stadium> upgradable = new ArrayList<>();
        ArrayList<String> countriesChecked = new ArrayList<>();

        for(int i = 0; i < ownedBy.size(); i++)
        {
            ArrayList<Stadium> sameCountry = new ArrayList<>();
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

    /**
     * Getter for gameEnded
     * @return true if game ended, false otherwise
     */
    public boolean getGameEnded()
    {
        return this.gameEnded;
    }

    /**
     * Updates game status by setting gameEnded if there is only one player non bankrupt
     */
    public void updateGameStatus()
    {
        int numAlive = 0;
        for(int i = 0; i < players.size(); i++)
        {
            if(players.get(i).updateBankrupt())
                removePlayerStadiums(players.get(i));
            if(!players.get(i).isBankrupt())
                numAlive++;
        }

        if(numAlive <= 1)
            this.gameEnded = true;
    }

    /**
     * Removes the owner of the stadiums owned by a player
     * @param player player to remove stadiums
     */
    private void removePlayerStadiums(Player player)
    {
        for(int i = 0; i < table.getPlaceSize(); i++)
        {
            Place currPlace = table.getPlace(i);
            if(currPlace.getClass() == Stadium.class)
            {
                Stadium stadium = (Stadium) currPlace;
                if(stadium.getOwner() == player)
                    stadium.setOwner(null);
            }
        }
    }

    /**
     * Returns the winner player
     * @return the winner player
     */
    public Player getWinner()
    {
        for(int i = 0; i < players.size(); i++)
        {
            if(!players.get(i).isBankrupt())
                return players.get(i);
        }
        return null;
    }
}
