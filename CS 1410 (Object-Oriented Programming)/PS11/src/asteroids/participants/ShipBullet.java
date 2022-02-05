package asteroids.participants;

import asteroids.destroyers.ShipBulletDestroyer;
import asteroids.destroyers.AsteroidDestroyer;
import asteroids.destroyers.AlienShipDestroyer;
import asteroids.game.Participant;

public class ShipBullet extends Bullet implements AsteroidDestroyer, AlienShipDestroyer
{
    public ShipBullet (double x, double y, double direction)
    {
        super(x, y, direction);
    }

    @Override
    public void collidedWith (Participant p)
    {
        if (p instanceof ShipBulletDestroyer)
        {
            Participant.expire(this);
        }
    }
}
