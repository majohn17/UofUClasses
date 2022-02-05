package asteroids.participants;

import static asteroids.game.Constants.*;
import java.awt.Shape;
import java.awt.geom.*;
import asteroids.destroyers.*;
import asteroids.game.Controller;
import asteroids.game.Participant;

/**
 * Represents ships
 */
public class Ship extends Participant implements AsteroidDestroyer, AlienShipDestroyer, AlienBulletDestroyer
{
    /** The outline of the ship with no boost */
    private Shape normalShip;

    /** The outline of the ship with boost */
    private Shape fireShip;

    /** Game controller */
    private Controller controller;

    /** Used to determine if the ship is accelerating */
    private boolean isAccelerating;

    /** Used to determine if the fireShip needs to be shown */
    private boolean showShip;

    /**
     * Constructs a ship at the specified coordinates that is pointed in the given direction.
     */
    public Ship (int x, int y, double direction, Controller controller)
    {
        this.controller = controller;
        setPosition(x, y);
        setRotation(direction);
        this.isAccelerating = false;
        this.showShip = false;

        Path2D.Double poly = new Path2D.Double();
        poly.moveTo(21, 0);
        poly.lineTo(-21, 12);
        poly.lineTo(-14, 10);
        poly.lineTo(-14, -10);
        poly.lineTo(-21, -12);
        poly.closePath();
        normalShip = poly;

        poly = new Path2D.Double();
        poly.moveTo(21, 0);
        poly.lineTo(-21, 12);
        poly.lineTo(-14, 10);
        poly.lineTo(-14, -5);
        poly.lineTo(-25, 0);
        poly.lineTo(-14, 5);
        poly.lineTo(-14, -10);
        poly.lineTo(-21, -12);
        poly.closePath();
        fireShip = poly;
    }

    /**
     * Returns the x-coordinate of the point on the screen where the ship's nose is located.
     */
    public double getXNose ()
    {
        Point2D.Double point = new Point2D.Double(20, 0);
        transformPoint(point);
        return point.getX();
    }

    /**
     * Returns the x-coordinate of the point on the screen where the ship's nose is located.
     */
    public double getYNose ()
    {
        Point2D.Double point = new Point2D.Double(20, 0);
        transformPoint(point);
        return point.getY();
    }

    /**
     * Alternates between fireShip and normalShip outline if the ship is accelerating and returns normal ship for not
     * accelerating
     */
    @Override
    protected Shape getOutline ()
    {
        if (this.isAccelerating)
        {
            this.showShip = !this.showShip;

            if (this.showShip == true)
            {
                return this.fireShip;
            }
            return this.normalShip;
        }
        else
        {
            return this.normalShip;
        }
    }

    /**
     * Customizes the base move method by imposing friction
     */
    @Override
    public void move ()
    {
        applyFriction(SHIP_FRICTION);
        super.move();
    }

    /**
     * Turns right by Pi/16 radians
     */
    public void turnRight ()
    {
        rotate(Math.PI / 16);
    }

    /**
     * Turns left by Pi/16 radians
     */
    public void turnLeft ()
    {
        rotate(-Math.PI / 16);
    }

    /**
     * Accelerates by SHIP_ACCELERATION
     */
    public void accelerate ()
    {
        accelerate(SHIP_ACCELERATION);
        if (!this.isAccelerating)
        {
            this.controller.startLoop("thrust");
        }
        this.isAccelerating = true;
    }

    /**
     * Determines when the ship is not accelerating
     */
    public void stopAccelerate ()
    {
        if (this.isAccelerating)
        {
            this.controller.stopLoop("thrust");
        }
        this.isAccelerating = false;
    }

    /**
     * Fires a ShipBullet at BULLET_SPEED in the direction the ship is facing at the time
     */
    public void shoot ()
    {
        if (!this.controller.checkBulletLimit(BULLET_LIMIT))
        {
            Bullet b = new ShipBullet(getXNose(), getYNose(), getRotation());
            b.setVelocity(BULLET_SPEED, getRotation());
            this.controller.addParticipant(b);
            this.controller.playClip("fire");
        }
    }

    /**
     * When a Ship collides with a ShipDestroyer, it expires
     */
    @Override
    public void collidedWith (Participant p)
    {
        if (p instanceof ShipDestroyer)
        {
            // Expire the ship from the game
            Participant.expire(this);

            // Stop thrusting sound and play and explosion
            this.controller.playClip("bangShip");
            this.controller.stopLoop("thrust");

            // Add debris coming from when the ship explodes
            this.controller.addParticipant(new Debris(getX(), getY(), 20.0));
            this.controller.addParticipant(new Debris(getX(), getY(), 20.0));
            this.controller.addParticipant(new Debris(getX(), getY(), 5.0));

            // Tell the controller the ship was destroyed
            this.controller.shipDestroyed();
        }
    }
}
