package com.mieicfeup.df.footpoly.model;

import java.io.Serializable;

/**
 * Created by fabio on 30/04/2016.
 */
public class Player implements Serializable
{
    private String name;
    private int balance;
    private int index;
    private Boolean isHuman;
    private Boolean bankrupt;

    /**
     * @return true if player is bankrupt and false otherwise
     */
    public Boolean isBankrupt() {
        return bankrupt;
    }

    /**
     * @param bankrupt sets player as bankrupt if true
     */
    public void setBankrupt(Boolean bankrupt) {
        this.bankrupt = bankrupt;
    }

    /**
     * Player Constructor
     * @param name player name
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

    public boolean decBalance(int amount)
    {
        if(amount > this.balance)
            return false;

        this.balance -= amount;
        return true;
    }

    public void incBalance(int amount)
    {
        this.balance += amount;
    }
}
