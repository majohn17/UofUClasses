package cs1410;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * A CacheList is a collection of Cache objects together with these six constraints:
 * 
 * <ol>
 * <li>A title constraint</li>
 * <li>An owner constraint</li>
 * <li>A minimum difficulty constraint</li>
 * <li>A maximum difficulty constraint</li>
 * <li>A minimum terrain constraint</li>
 * <li>A maximum terrain constraint</li>
 * </ol>
 */
public class CacheList
{
    /** Holds the caches sorted in ascending order in this list */
    private ArrayList<Cache> allCaches;
    /** Holds the title constraint of this list */
    private String titleConstraint;
    /** Holds the owner constraint of this list */
    private String ownerConstraint;
    /** Holds the minimum difficulty constraint of this list */
    private double minDif;
    /** Holds the maximum difficulty constraint of this list */
    private double maxDif;
    /** Holds the minimum terrain constraint of this list */
    private double minTer;
    /** Holds the maximum terrain constraint of this list */
    private double maxTer;
    /** Holds the number of caches in this list */
    private int count;

    /**
     * Creates a CacheList from the specified Scanner. Each line of the Scanner should contain the description of a
     * cache in a format suitable for consumption by the Cache constructor. The resulting CacheList should contain one
     * Cache object corresponding to each line of the Scanner.
     */
    public CacheList (Scanner caches) throws IOException
    {
        allCaches = new ArrayList<Cache>();
        this.count = 1;
        try
        {
            while (caches.hasNextLine())
            {
                this.allCaches.add(new Cache(caches.nextLine()));
                this.count++;
            }
        }
        catch (IllegalArgumentException e)
        {
            throw new IllegalArgumentException(Integer.toString(this.count));
        }

        this.titleConstraint = "";
        this.ownerConstraint = "";
        this.minDif = 1.0;
        this.maxDif = 5.0;
        this.minTer = 1.0;
        this.maxTer = 5.0;

        // Sort the list of all caches
        Collections.sort(allCaches, (c1, c2) -> c1.getTitle().compareToIgnoreCase(c2.getTitle()));
    }

    /**
     * Sets the title constraint to the specified value.
     */
    public void setTitleConstraint (String title)
    {
        this.titleConstraint = title;
    }

    /**
     * Sets the owner constraint to the specified value.
     */
    public void setOwnerConstraint (String owner)
    {
        this.ownerConstraint = owner;
    }

    /**
     * Sets the minimum and maximum difficulty constraints to the specified values.
     */
    public void setDifficultyConstraints (double min, double max)
    {
        this.minDif = min;
        this.maxDif = max;
    }

    /**
     * Sets the minimum and maximum terrain constraints to the specified values.
     */
    public void setTerrainConstraints (double min, double max)
    {
        this.minTer = min;
        this.maxTer = max;
    }

    /**
     * Returns a list that contains each cache c from the CacheList so long as c's title contains the title constraint
     * as a substring, c's owner equals the owner constraint (unless the owner constraint is empty), c's difficulty
     * rating is between the minimum and maximum difficulties (inclusive), and c's terrain rating is between the minimum
     * and maximum terrains (inclusive). Both the title constraint and the owner constraint are case insensitive.
     * 
     * The returned list is arranged in ascending order by cache title.
     */
    public ArrayList<Cache> select ()
    {
        ArrayList<Cache> caches = new ArrayList<Cache>();

        for (Cache c : this.allCaches)
        {
            if (c.getTitle().contains(this.titleConstraint))
            {
                if (this.ownerConstraint.equals("") || c.getOwner().equals(this.ownerConstraint))
                {
                    if (c.getDifficulty() >= this.minDif && c.getDifficulty() <= this.maxDif)
                    {
                        if (c.getTerrain() >= this.minTer && c.getTerrain() <= this.maxTer)
                        {
                            caches.add(c);
                        }
                    }
                }
            }
        }

        // Sorts the list of selected caches
        Collections.sort(caches, (s1, s2) -> s1.getTitle().compareToIgnoreCase(s2.getTitle()));
        return caches;
    }

    /**
     * Returns a list containing all the owners of all of the Cache objects in this CacheList. There are no duplicates.
     * The list is arranged in ascending order.
     */
    public ArrayList<String> getOwners ()
    {
        ArrayList<String> owners = new ArrayList<String>();

        for (Cache c : allCaches)
        {
            if (!(owners.contains(c.getOwner())))
            {
                owners.add(c.getOwner());
            }
        }

        // Sort the list of owners
        Collections.sort(owners, (s1, s2) -> s1.compareToIgnoreCase(s2));
        return owners;
    }
}
