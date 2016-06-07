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

    /**
     * Normal constructor
     * @param name Stadium name
     * @param country Stadium country
     * @param cost Stadium cost
     * @param rent Stadium base rent
     */
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

    /**
     * @return Stadium name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @return Stadium country
     */
    public String getCountry()
    {
        return country;
    }

    /**
     * @return Stadium cost
     */
    public int getCost()
    {
        return cost;
    }

    /**
     * @return stadium rent on the current upgrade level, 0 if it's mortgaged
     */
    public int getRent()
    {
        if(this.mortgaged)
            return 0;

        return (this.rent + (this.rent * this.upgradeLevel));
    }

    /**
     * @param upgradeLevel upgradeLevel to check rent
     * @return Rent of the stadium for a given upgrade level
     */
    public int getRent(int upgradeLevel)
    {
        return (this.rent + (this.rent * upgradeLevel));
    }

    /**
     * @return Stadium owner
     */
    public Player getOwner()
    {
        return owner;
    }

    /**
     * @return true if mortgaged, false otherwise
     */
    public boolean getMortgaged()
    {
        return this.mortgaged;
    }

    /**
     * Sets stadium owner
     * @param owner new Stadium owner
     */
    public void setOwner(Player owner)
    {
        this.owner = owner;
    }

    /**
     * @return Returns Stadium upgrade level
     */
    public int getUpgradeLevel()
    {
        return this.upgradeLevel;
    }

    /**
     * @param level the desired upgrade level
     * @return the cost of a upgrade to a given level
     */
    public int getUpgradeCost(int level)
    {
        int delta = level - this.upgradeLevel;
        return delta * this.upgradeCost;
    }

    /**
     * Upgrades stadium to upgradeLevel if owner has enough money
     * @param upgradeLevel new upgradeLevel
     * @return true if owner has money for upgrade, false otherwise
     */
    public boolean upgradeStadium(int upgradeLevel)
    {
        if(owner.decBalance(this.getUpgradeCost(upgradeLevel))) {
            this.upgradeLevel = upgradeLevel;
            return true;
        }

        return false;
    }

    /**
     * Sets stadium mortgage and increases/decreases owner balance
     * @param mortgage new mortgage state
     */
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
            if(owner.decBalance(this.cost))
                this.mortgaged = false;
            return;
        }
    }

    /**
     * If the player at the stadium is not the owner, decreases player balance and increases owner balance
     * @param player player at the stadium
     * @return buy stadium dialog enum if stadium has no owner, no dialog enum otherwise
     */
    public dialogType trigger(Player player)
    {
        Log.w("Stadium", "trigger");
        if(this.owner == player)
            return dialogType.NODIALOG;

        if(this.owner == null)
            return dialogType.BUYSTADIUM;

        int amount = this.getRent();
        int balance = player.getBalance();
        if(!player.decBalance(amount))
        {
            player.incBalance(-balance - 1);
            owner.incBalance(balance);
            return dialogType.NODIALOG;
        }

        owner.incBalance(amount);
        return dialogType.NODIALOG;
    }
}
