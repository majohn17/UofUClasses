package asteroids.participants;

import static asteroids.game.Constants.*;
import asteroids.game.Participant;
import asteroids.game.ParticipantCountdownTimer;
import java.awt.Shape;
import java.awt.geom.Path2D;

public class Debris extends Participant
{
    private Shape outline;

    public Debris (double x, double y, double length)
    {
        double changeValue = RANDOM.nextDouble() * 8.0 - 4.0;
        double halfLength = length / 2.0;

        Path2D.Double line = new Path2D.Double();
        line.moveTo(0.0, -(halfLength));
        line.lineTo(0.0, halfLength);

        setRotation(2 * Math.PI * RANDOM.nextDouble());
        setPosition(x + changeValue, y + changeValue);
        setVelocity(RANDOM.nextDouble() + 0.5, (2.0 * Math.PI * RANDOM.nextDouble()));

        this.outline = line;

        new ParticipantCountdownTimer(this, this, 1500 + (int) (RANDOM.nextDouble() * 300.0));
    }

    protected Shape getOutline ()
    {
        return this.outline;
    }

    public void countdownComplete (Object payload)
    {
        Participant.expire(this);
    }

    public void collidedWith (Participant p)
    {
    }
}