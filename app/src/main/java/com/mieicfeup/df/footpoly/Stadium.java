package com.mieicfeup.df.footpoly;

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

    public void upgradeStadium()
    {
        if(this.upgradeLevel < 2)
        {
            ++this.upgradeLevel;
            owner.decBalance(this.upgradeCost);
        }
    }

    public void setMortgaged()
    {
        if(this.mortgaged)
        {
            owner.decBalance(this.cost);
            this.mortgaged = false;
        }
        else
        {
            owner.incBalance(this.cost);
            this.mortgaged = true;
        }
    }

    public boolean trigger(Player player)
    {
        if(this.owner == player || this.owner == null)
            return true;

        int ammount = this.getRent();
        if(!player.decBalance(ammount))
            return false;
        owner.incBalance(ammount);
        return true;
    }
}
