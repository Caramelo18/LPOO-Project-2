package com.mieicfeup.df.footpoly;

import java.io.Serializable;

/**
 * Created by fabio on 01/06/2016.
 */
public abstract class Place implements Serializable
{

    public abstract boolean trigger(Player player);
}
