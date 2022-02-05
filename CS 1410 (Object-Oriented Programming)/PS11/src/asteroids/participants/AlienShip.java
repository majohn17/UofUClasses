package asteroids.participants;

import static asteroids.game.Constants.*;
import java.awt.Shape;
import java.awt.geom.*;
import asteroids.destroyers.AlienShipDestroyer;
import asteroids.destroyers.AsteroidDestroyer;
import asteroids.destroyers.ShipBulletDestroyer;
import asteroids.destroyers.ShipDestroyer;
import asteroids.game.Controller;
import asteroids.game.Participant;
import asteroids.game.ParticipantCountdownTimer;

/**
 * Represents asteroids
 */
public class AlienShip extends Participant implements AsteroidDestroyer, ShipBulletDestroyer, ShipDestroyer
{
    /** The size of the asteroid (0 = small, 1 = big) */
    private int size;

    /** The outline of the asteroid */
    private Shape outline;

    /** The game controller */
    private Controller controller;

    /** Lets alien ship know if its direction needs to be changes */
    private boolean directionChange;

    /**
     * Throws an IllegalArgumentException if size or variety is out of range.
     * 
     * Creates an alien ship of a size (0 = small, 1 = big).
     */
    public AlienShip (int size, Controller controller)
    {
        if ((size < 0) || (size > 1))
        {
            throw new IllegalArgumentException("Invalid alien ship size: " + size);
        }
        this.controller = controller;
        this.size = size;

        // Created the shape and resizes the Alien Ship
        Path2D.Double poly = new Path2D.Double();
        poly.moveTo(0.0, 0.0);
        poly.lineTo(-11.0, 9.0);
        poly.lineTo(-29.0, 9.0);
        poly.lineTo(-40.0, 0.0);
        poly.lineTo(0.0, 0.0);
        poly.lineTo(-40.0, 0.0);
        poly.lineTo(-29.0, -9.0);
        poly.lineTo(-11.0, -9.0);
        poly.lineTo(-29.0, -9.0);
        poly.lineTo(-25.0, -17.0);
        poly.lineTo(-15.0, -17.0);
        poly.lineTo(-11.0, -9.0);
        poly.closePath();
        this.outline = poly;
        double scale = ALIENSHIP_SCALE[size];
        poly.transform(AffineTransform.getScaleInstance(scale, scale));

        // Ship initially doesn't change direction
        this.directionChange = false;

        // Start timers to change direction and shoot at specified times
        new ParticipantCountdownTimer(this, "shoot", 1500);
        new ParticipantCountdownTimer(this, "direction", 1000);

        // Starts the alien sound for each different size of ship
        if (this.getSize() == 1)
        {
            this.controller.startLoop("saucerBig");
        }
        else
        {
            this.controller.startLoop("saucerSmall");
        }
    }

    @Override
    protected Shape getOutline ()
    {
        return this.outline;
    }

    /**
     * Returns the size of the asteroid
     */
    public int getSize ()
    {
        return size;
    }

    /**
     * Fires an AlienBullet at BULLET_SPEED in the direction the ship if size is small or randomly
     */
    public void shoot ()
    {
        Bullet b = new AlienBullet(getX(), getY(), setBulletDirection());
        b.setSpeed(BULLET_SPEED);
        this.controller.addParticipant(b);
    }

    /**
     * Stops the alien sound depending on what size the alien ship is.
     */
    public void stopSound ()
    {
        this.controller.stopLoop("saucerBig");
        this.controller.stopLoop("saucerSmall");
    }

    /**
     * Determines a correct direction to fire a bullet depending on the ships size
     */
    public double setBulletDirection ()
    {
        if (this.size == 1)
        {
            return RANDOM.nextDouble() * (Math.PI * 2);
        }
        else
        {
            double shipX = this.controller.getShip().getX();
            double shipY = this.controller.getShip().getY();
            double distanceX = shipX - getX();
            double distanceY = shipY - getY();
            double distance = Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));
            double direction = Math.acos(distanceX / distance);
            direction = distanceY > 0 ? direction : -direction;
            return direction;
        }
    }

    /**
     * Used to make the ship move in a random direction (up, straight, down)
     */
    @Override
    public void move ()
    {
        super.move();
        if (this.directionChange)
        {
            this.directionChange = false;
            if (Math.cos(this.getDirection()) > 0)
            {
                this.setDirection(RANDOM.nextInt(3) - 1);
            }
            else
            {
                this.setDirection(Math.PI + RANDOM.nextInt(3) - 1);
            }

            new ParticipantCountdownTimer(this, "direction", 1000);
        }
    }

    /**
     * Calls a method or actions when the countdown timers are reached
     */
    @Override
    public void countdownComplete (Object payload)
    {
        if (payload.equals("shoot"))
        {
            Ship ship = this.controller.getShip();
            if (ship != null)
            {
                shoot();
                this.controller.playClip("fire");
                new ParticipantCountdownTimer(this, "shoot", 1500);
            }
        }
        else if (payload.equals("direction"))
        {
            this.directionChange = true;
        }
    }

    /**
     * When an alien ship collides with an AlienShipDestroyer, it expires.
     */
    @Override
    public void collidedWith (Participant p)
    {
        // Expires the alien ship, stops current sounds, and plays the alien ship explosion sound
        if (p instanceof AlienShipDestroyer)
        {
            Participant.expire(this);
            stopSound();
            this.controller.playClip("bangAlienShip");

            // Creates debris from the ship when it is destroyed
            int sizeValue = this.size % 2 == 0 ? 1 : 0;
            this.controller.addParticipant(new Debris(getX(), getY(), 15 - (sizeValue * 6)));
            this.controller.addParticipant(new Debris(getX(), getY(), 15 - (sizeValue * 6)));
            this.controller.addParticipant(new Debris(getX(), getY(), 15 - (sizeValue * 6)));
            this.controller.addParticipant(new Debris(getX(), getY(), 15 - (sizeValue * 6)));
            this.controller.addParticipant(new Debris(getX(), getY(), 10 - (sizeValue * 6)));
            this.controller.addParticipant(new Debris(getX(), getY(), 10 - (sizeValue * 6)));

            // Tells controller that the alien ship was destroyed
            this.controller.alienShipDestroyed(this.size);
        }

    }
}
