package cs1410;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents a variety of information about a geocache. A geocache has a title, an owner, a difficulty rating, a
 * terrain rating, a GC code, a latitude, and a longitude.
 */
public class Cache
{
    /** Holds the GC code of this Cache */
    private String gcCode;
    /** Holds the title of this Cache */
    private String title;
    /** Holds the owner of this Cache */
    private String owner;
    /** Holds the difficulty rating of this Cache */
    private double difficulty;
    /** Holds the terrain rating of this Cache */
    private double terrain;
    /** Holds the latitude coordinates of this Cache */
    private String latitude;
    /** Holds the longitude coordinates of this Cache */
    private String longitude;
    /** Holds the valid double values that the rating of terrain and difficulty can be */
    private static final ArrayList<Double> VALID_RATINGS = new ArrayList<>(
            Arrays.asList(1.0, 1.5, 2.0, 2.5, 3.0, 3.5, 4.0, 4.5, 5.0));

    /**
     * Creates a Cache from a string that consists of these seven cache attributes: the GC code, the title, the owner,
     * the difficulty rating, the terrain rating, the latitude, and the longitude, in that order, separated by single
     * TAB ('\t') characters.
     */
    public Cache (String attributes)
    {
        // Splits the string into usable sections for the info
        String[] info = attributes.split("\t");

        // Ensures that there is enough information or no extra information
        if (info.length != 7)
        {
            throw new IllegalArgumentException();
        }

        this.gcCode = info[0];
        // Ensures that the GC code matches the correct format
        if (isValidCode(this.gcCode) == false)
        {
            throw new IllegalArgumentException();
        }

        this.title = info[1];
        this.owner = info[2];

        // Ensures that difficulty and terrain are valid double values
        try
        {
            this.difficulty = Double.parseDouble(info[3]);
            if (!(VALID_RATINGS.contains(this.difficulty)))
            {
                throw new IllegalArgumentException();
            }
            this.terrain = Double.parseDouble(info[4]);
            if (!(VALID_RATINGS.contains(this.terrain)))
            {
                throw new IllegalArgumentException();
            }
        }
        catch (NumberFormatException e)
        {
            throw new IllegalArgumentException();
        }

        this.latitude = info[5];
        this.longitude = info[6];
        // Ensures that the title, owner, latitude, and longitude aren't blank
        if (this.title.trim().equals("") || this.owner.trim().equals("") || this.latitude.trim().equals("")
                || this.longitude.trim().equals(""))
        {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Checks to see if a GC code matches the valid GC code format
     */
    public static boolean isValidCode (String code)
    {
        boolean valid = false;
        if (code.indexOf("GC") == 0 && code.length() >= 3)
        {
            for (int i = 2; i < code.length(); i++)
            {
                if (Character.isDigit(code.charAt(i)) == true || Character.isUpperCase(code.charAt(i)) == true)
                {
                    valid = true;
                }
                else
                {
                    return false;
                }
            }
            return valid;
        }
        else
        {
            return false;
        }
    }

    /**
     * Converts this cache to a string
     */
    public String toString ()
    {
        return getTitle() + " by " + getOwner();
    }

    /**
     * Returns the GC code of this cache
     */
    public String getGcCode ()
    {
        return this.gcCode;
    }

    /**
     * Returns the title of this cache
     */
    public String getTitle ()
    {
        return this.title;
    }

    /**
     * Returns the owner of this cache
     */
    public String getOwner ()
    {
        return this.owner;
    }

    /**
     * Returns the difficulty rating of this cache
     */
    public double getDifficulty ()
    {
        return this.difficulty;
    }

    /**
     * Returns the terrain rating of this cache
     */
    public double getTerrain ()
    {
        return this.terrain;
    }

    /**
     * Returns the latitude of this cache
     */
    public String getLatitude ()
    {
        return this.latitude;
    }

    /**
     * Returns the longitude of this cache
     */
    public String getLongitude ()
    {
        return this.longitude;
    }
}
