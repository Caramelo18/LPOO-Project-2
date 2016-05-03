package com.mieicfeup.df.footpoly;

/**
 * Created by fabio on 01/05/2016.
 */
public class Stadium
{
    private Token owner;
    private String name;
    private String country;
    private int cost;
    private int rent;
    private int houseRent;
    private int hotelRent;
    private boolean house;
    private boolean hotel;

    public Stadium(String name, String country, int cost, int rent, int houseRent, int hotelRent)
    {
        this.name = name;
        this.country = country;
        this.cost = cost;
        this.rent = rent;
        this.houseRent = houseRent;
        this.hotelRent = hotelRent;
    }

    public String getName()
    {
        return name;
    }

    public Token getOwner()
    {
        return owner;
    }

    public void setOwner(Token owner)
    {
        this.owner = owner;
    }

    public int getCost()
    {
        return cost;
    }

    public int getRent()
    {
        if(!house && !hotel)
            return rent;

        else if(house)
            return houseRent;

        else
            return hotelRent;
    }

    public void upgradeStadium()
    {
        if(!house && !hotel)
            house = true;

        else if(house)
            hotel = true;
    }
}
