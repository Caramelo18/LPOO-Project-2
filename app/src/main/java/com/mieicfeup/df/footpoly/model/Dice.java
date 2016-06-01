package com.mieicfeup.df.footpoly.model;

import java.util.Random;

/**
 * Created by fabio on 30/04/2016.
 */
public class Dice
{
    private Random rand;
    public Dice()
    {
        rand = new Random();
    }

    public int rollDice()
    {
        int res = rand.nextInt(6) + 1;
        return res;
    }
}
