package asteroids.participants;

import asteroids.destroyers.AlienBulletDestroyer;
import asteroids.destroyers.AsteroidDestroyer;
import asteroids.destroyers.ShipDestroyer;
import asteroids.game.Participant;

public class AlienBullet extends Bullet implements ShipDestroyer, AsteroidDestroyer
{
    public AlienBullet (double x, double y, double direction)
    {
        super(x, y, direction);
    }

    public void collidedWith (Participant p)
    {
        if ((p instanceof AlienBulletDestroyer))
        {
            Participant.expire(this);
        }
    }
}