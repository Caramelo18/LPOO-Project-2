package com.mieicfeup.df.footpoly.model;

import java.io.Serializable;

/**
 * Created by fabio on 30/04/2016.
 */
public class Player implements Serializable
{
    private static final long serialVersionUID = -3237874103546203476L;
    private final String name;
    private int balance;
    private int index;
    private final Boolean isHuman;
    private Boolean bankrupt;

    /**
     * Player Constructor
     * @param name player name
     * @param isHuman is player human
     */
    public Player(String name, Boolean isHuman)
    {
        this.name = name;
        this.balance = 5000;
        this.index = 0;
        this.isHuman = isHuman;
        this.bankrupt = false;
    }

    /**
     * @return true if player is bankrupt and false otherwise
     */
    public Boolean isBankrupt() {
        return bankrupt;
    }

    /**
     * Updates the player bankrupt state
     * @return returns true if player is now bankrupt
     */
    public boolean updateBankrupt()
    {
        if(this.balance < 0 && !this.bankrupt)
        {
            this.bankrupt = true;
            return true;
        }
        else if(this.balance >= 0)
        {
            this.bankrupt = false;
            return false;
        }
        return false;
    }


    /**
     * @return true if player is human and false otherwise
     */
    public Boolean isHuman() {
        return isHuman;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @return current index (position on the table)
     */
    public int getIndex()
    {
        return this.index;
    }

    /**
     * Increments the player index
     * @param inc value to increment
     */
    public void incrementIndex(int inc) {
        this.index += inc;
    }

    /**
     * @return player balance
     */
    public int getBalance()
    {
        return balance;
    }

    /**
     * Decreases amount in player balance if possible
     * @param amount amount to decrease
     * @return true if decreased successfully, false otherwise
     */
    public boolean decBalance(int amount)
    {
        if(amount > this.balance)
            return false;

        this.balance -= amount;
        return true;
    }

    /**
     * Increases player balance by parameter amount
     * @param amount amount to increase
     */
    public void incBalance(int amount)
    {
        this.balance += amount;
    }
}
