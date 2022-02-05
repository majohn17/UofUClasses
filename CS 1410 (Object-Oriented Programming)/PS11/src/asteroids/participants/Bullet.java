package asteroids.participants;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import asteroids.game.Participant;
import asteroids.game.ParticipantCountdownTimer;
import static asteroids.game.Constants.*;

public abstract class Bullet extends Participant
{
    private Shape outline;

    public Bullet (double x, double y, double direction)
    {
        this.setPosition(x, y);
        this.setVelocity(BULLET_SPEED, direction);
        this.outline = new Ellipse2D.Double(0.0, 0.0, 1.0, 1.0);
        new ParticipantCountdownTimer(this, BULLET_DURATION);
    }

    @Override
    protected Shape getOutline ()
    {
        return this.outline;
    }

    @Override
    public void countdownComplete (Object payload)
    {
        Participant.expire(this);
    }
}
