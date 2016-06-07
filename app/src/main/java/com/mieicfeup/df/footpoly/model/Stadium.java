package com.mieicfeup.df.footpoly.model;

import android.util.Log;

/**
 * Created by fabio on 01/05/2016.
 */
public class Stadium extends Place
{
    private Player owner;
    private String name;
    private String country;
    private int cost;
    private int rent;
    private int upgradeCost;
    private int upgradeLevel;
    private boolean mortgaged;

    public Stadium(String name, String country, int cost, int rent)
    {
        this.name = name;
        this.country = country;
        this.cost = cost;
        this.rent = rent;
        this.upgradeCost = (int) Math.round(cost * 0.8);
        this.upgradeLevel = 0;
        this.mortgaged = false;
    }

    public String getName()
    {
        return name;
    }

    public String getCountry()
    {
        return country;
    }

    public int getCost()
    {
        return cost;
    }

    public int getRent()
    {
        if(this.mortgaged)
            return 0;

        return (this.rent + (this.rent * this.upgradeLevel));
    }

    public int getRent(int upgradeLevel)
    {
        return (this.rent + (this.rent * upgradeLevel));
    }

    public Player getOwner()
    {
        return owner;
    }

    public boolean getMortgaged()
    {
        return this.mortgaged;
    }

    public void setOwner(Player owner)
    {
        this.owner = owner;
    }

    public int getUpgradeLevel()
    {
        return this.upgradeLevel;
    }

    public int getUpgradeCost(int level)
    {
        int delta = level - this.upgradeLevel;
        return delta * this.upgradeCost;
    }

    public boolean upgradeStadium(int upgradeLevel)
    {
        if(owner.decBalance(this.getUpgradeCost(upgradeLevel))) {
            this.upgradeLevel = upgradeLevel;
            return true;
        }

        return false;
    }

    public void setMortgaged(boolean mortgage)
    {
        if(mortgage && !this.mortgaged)
        {
            owner.incBalance(this.cost);
            this.mortgaged = true;
            return;
        }
        else if(!mortgage && this.mortgaged)
        {
            owner.decBalance(this.cost);
            this.mortgaged = false;
            return;
        }
    }

    /**
     * @param player player at the stadium
     * @return false if stadium has no owner, true otherwise
     */
    public dialogType trigger(Player player)
    {
        Log.w("Stadium", "trigger");
        if(this.owner == player)
            return dialogType.NODIALOG;

        if(this.owner == null)
            return dialogType.BUYSTADIUM;

        int amount = this.getRent();
        if(!player.decBalance(amount))
            return dialogType.NODIALOG;
        owner.incBalance(amount);
        return dialogType.NODIALOG;
    }
}
