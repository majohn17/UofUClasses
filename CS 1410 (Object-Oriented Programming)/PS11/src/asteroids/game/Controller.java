package asteroids.game;

import static asteroids.game.Constants.*;
import java.awt.event.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import asteroids.participants.AlienShip;
import asteroids.participants.Asteroid;
import asteroids.participants.Ship;

/**
 * Controls a game of Asteroids.
 */
public class Controller implements KeyListener, ActionListener
{
    /** The state of all the Participants */
    private ParticipantState pstate;

    /** Holds all the clips */
    private HashMap<String, Clip> sounds;

    /** The ship (if one is active) or null (otherwise) */
    private Ship ship;

    private AlienShip alienShip;

    /** When this timer goes off, it is time to refresh the animation */
    private Timer refreshTimer;

    /** When this timer goes off, the beat speeds up */
    private Timer beatTimer;

    /** Determines which beat is going to be played */
    private boolean beat1;

    /** Used to determine if the ship is turning left */
    private boolean leftTurn;

    /** Used to determine if the ship is turning left */
    private boolean rightTurn;

    /** Used to determine if the ship is accelerating */
    private boolean boost;

    /** Used to determine if the ship is shooting */
    private boolean shot;

    /**
     * The time at which a transition to a new stage of the game should be made. A transition is scheduled a few seconds
     * in the future to give the user time to see what has happened before doing something like going to a new level or
     * resetting the current level.
     */
    private long transitionTime;

    /** Number of lives left */
    private int lives;

    /** Used to count the score the user has */
    private int score;

    /** Used to determine what level the user is on */
    private int level;

    /** The game display */
    private Display display;

    /**
     * Constructs a controller to coordinate the game and screen
     */
    public Controller ()
    {
        // Initialize the ParticipantState
        this.pstate = new ParticipantState();

        // Gets sounds ready to use
        this.sounds = new SoundHolder().getClips();

        // Set up the refresh timer
        this.refreshTimer = new Timer(FRAME_INTERVAL, this);

        // Set up the beat timer
        this.beatTimer = new Timer(INITIAL_BEAT, this);

        // Clear the transitionTime
        this.transitionTime = Long.MAX_VALUE;

        // Record the display object
        this.display = new Display(this);

        // Bring up the splash screen and start the refresh timer
        splashScreen();
        this.display.setVisible(true);
        refreshTimer.start();
        this.beat1 = true;
    }

    /**
     * Returns the ship, or null if there isn't one
     */
    public Ship getShip ()
    {
        return ship;
    }

    /**
     * Configures the game screen to display the splash screen
     */
    private void splashScreen ()
    {
        // Clear the screen, reset the level, and display the legend
        clear();
        this.level = 1;
        this.display.setLegend("Asteroids");

        // Place four asteroids near the corners of the screen.
        placeAsteroids();
    }

    /**
     * The game is over. Displays a message to that effect.
     */
    private void finalScreen ()
    {
        display.setLegend(GAME_OVER);
        display.removeKeyListener(this);
    }

    /**
     * Place a new ship in the center of the screen. Remove any existing ship first.
     */
    private void placeShip ()
    {
        // Place a new ship
        Participant.expire(this.ship);
        this.ship = new Ship(SIZE / 2, SIZE / 2, -Math.PI / 2, this);
        addParticipant(this.ship);
        scheduleTransition(5000 + RANDOM.nextInt(5000));
    }

    /**
     * Place a new alien ship and remove any existing ship first.
     */
    private void placeAlienShip ()
    {
        // Place a new alien ship
        Participant.expire(this.alienShip);
        if (this.level > 1)
        {
            int alienSize = this.level == 2 ? 1 : 0;
            this.alienShip = new AlienShip(alienSize, this);
            this.alienShip.setPosition(0, RANDOM.nextDouble() * 750);
            this.alienShip.setVelocity(5 - alienSize, RANDOM.nextInt(2) * Math.PI);
            addParticipant(this.alienShip);
        }
    }

    /**
     * Places an asteroid near one corner of the screen. Gives it a random velocity and rotation.
     */
    private void placeAsteroids ()
    {
        addParticipant(new Asteroid(0, "TopLeft", this));
        addParticipant(new Asteroid(1, "TopRight", this));
        addParticipant(new Asteroid(2, "BottomLeft", this));
        addParticipant(new Asteroid(3, "BottomRight", this));
        for (int i = 1; i < this.level; i++)
        {
            switch (RANDOM.nextInt(4))
            {
                case 0:
                {
                    addParticipant(new Asteroid(RANDOM.nextInt(4), "TopLeft", this));
                    break;
                }
                case 1:
                {
                    addParticipant(new Asteroid(RANDOM.nextInt(4), "TopRight", this));
                    break;
                }
                case 2:
                {
                    addParticipant(new Asteroid(RANDOM.nextInt(4), "BottomLeft", this));
                    break;
                }
                case 3:
                {
                    addParticipant(new Asteroid(RANDOM.nextInt(4), "BottomRight", this));
                    break;
                }
            }
        }
    }

    /**
     * Used to determine if the number of ShipBullets is at the bullet limit and returns false if less than bullet limit
     */
    public boolean checkBulletLimit (int bulletLimit)
    {
        return this.pstate.countShipBullets() >= bulletLimit;
    }

    /**
     * Clears the screen so that nothing is displayed
     */
    private void clear ()
    {
        this.pstate.clear();
        this.display.setLegend("");
        if (this.ship != null)
        {
            this.ship.stopAccelerate();
            this.ship = null;
        }
        if (this.alienShip != null)
        {
            Participant.expire(this.alienShip);
            this.alienShip.stopSound();
            this.alienShip = null;
        }
    }

    /**
     * Sets things up and begins a new game.
     */
    private void initialScreen ()
    {
        // Set the initial beat stuff
        this.beatTimer.stop();
        this.beatTimer.setDelay(INITIAL_BEAT);
        this.beat1 = true;
        this.beatTimer.start();

        // Clear the screen
        clear();

        // Sets the initial game values
        this.lives = 3;
        this.score = 0;
        this.level = 1;

        // Place asteroids
        placeAsteroids();

        // Place the ship
        placeShip();

        // Ensures the ship isn't moving when game is started
        this.leftTurn = false;
        this.rightTurn = false;
        this.boost = false;
        this.shot = false;

        // Updates each of the displays with correct initial information
        this.display.setLives(this.lives);
        this.display.setScore(this.score);
        this.display.setLevel(this.level);

        // Start listening to events (but don't listen twice)
        this.display.removeKeyListener(this);
        this.display.addKeyListener(this);

        // Give focus to the game screen
        this.display.requestFocusInWindow();
    }

    private void nextLevel ()
    {
        clear();
        this.level += 1;
        placeAsteroids();
        placeShip();
        this.display.setLevel(this.level);
        this.beatTimer.setDelay(900);
        this.beatTimer.restart();
    }

    /**
     * Adds a new Participant
     */
    public void addParticipant (Participant p)
    {
        pstate.addParticipant(p);
    }

    /**
     * The ship has been destroyed
     */
    public void shipDestroyed ()
    {
        // Null out the ship
        this.ship = null;

        // Decrement lives
        this.lives--;
        this.display.setLives(this.lives);

        // Stop timer when ship destroyed
        this.beatTimer.stop();

        // Since the ship was destroyed, schedule a transition
        scheduleTransition(END_DELAY);
    }

    /**
     * An asteroid has been destroyed
     */
    public void asteroidDestroyed (int size)
    {
        this.score += ASTEROID_SCORE[size];
        this.display.setScore(score);
        // If all the asteroids are gone, schedule a transition
        if (pstate.countAsteroids() == 0)
        {
            this.beatTimer.stop();
            scheduleTransition(END_DELAY);
        }
    }

    /**
     * An asteroid has been destroyed
     */
    public void alienShipDestroyed (int size)
    {
        this.score += ALIENSHIP_SCORE[size];
        this.display.setScore(score);

        this.alienShip = null;

        // If ship is still alive, a transition will be scheduled to spawn another alien
        if (this.ship != null)
        {
            scheduleTransition(5000 + RANDOM.nextInt(5000));
        }
    }

    /**
     * Schedules a transition m msecs in the future
     */
    private void scheduleTransition (int m)
    {
        transitionTime = System.currentTimeMillis() + m;
    }

    /**
     * Returns the hashmap that contains all the clips
     */
    public HashMap<String, Clip> getSounds ()
    {
        return this.sounds;
    }

    /**
     * This method will be invoked because of button presses and timer events.
     */
    @Override
    public void actionPerformed (ActionEvent e)
    {
        // The start button has been pressed. Stop whatever we're doing
        // and bring up the initial screen
        if (e.getSource() instanceof JButton)
        {
            initialScreen();
        }
        // Time decreases as beats switch
        else if (e.getSource() == beatTimer)
        {
            String beat = this.beat1 ? "beat1" : "beat2";
            this.beat1 = !(this.beat1);
            playClip(beat);
            this.beatTimer.setDelay(Math.max(FASTEST_BEAT, this.beatTimer.getDelay() - BEAT_DELTA));
        }
        // Time to refresh the screen and deal with keyboard input
        else if (e.getSource() == refreshTimer)
        {
            // It may be time to make a game transition
            performTransition();

            // Move the participants to their new locations
            pstate.moveParticipants();
            if (this.leftTurn && this.ship != null)
            {
                this.ship.turnLeft();
            }
            if (this.rightTurn && this.ship != null)
            {
                this.ship.turnRight();
            }
            if (this.boost && this.ship != null)
            {
                this.ship.accelerate();
            }
            else if (this.ship != null)
            {
                this.ship.stopAccelerate();
            }
            if (this.shot && this.ship != null)
            {
                this.ship.shoot();
            }

            // Refresh screen
            this.display.refresh();
        }
    }

    /**
     * Returns an iterator over the active participants
     */
    public Iterator<Participant> getParticipants ()
    {
        return pstate.getParticipants();
    }

    /**
     * If the transition time has been reached, transition to a new state
     */
    private void performTransition ()
    {
        // Do something only if the time has been reached
        if (transitionTime <= System.currentTimeMillis())
        {
            // Clear the transition time
            transitionTime = Long.MAX_VALUE;

            // If there are no lives left, the game is over. Show the final screen.
            if (this.lives <= 0)
            {
                finalScreen();
            }
            // If there are no asteroids left, reset beat timer and start a new level.
            else if (this.pstate.countAsteroids() == 0)
            {
                this.beatTimer.setDelay(INITIAL_BEAT);
                nextLevel();
            }
            // If the ship is destroyed, restart the beat timer and place another ship.
            else if (this.ship == null)
            {
                this.beatTimer.restart();
                placeShip();
            }
            // Place a alien ship if there's not one there and level > 1
            else if (this.alienShip == null)
            {
                placeAlienShip();
            }
        }
    }

    /**
     * If a key of interest is pressed, record that it is down.
     */
    @Override
    public void keyPressed (KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A)
        {
            this.leftTurn = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D)
        {
            this.rightTurn = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W)
        {
            this.boost = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_SPACE
                || e.getKeyCode() == KeyEvent.VK_S)
        {
            this.shot = true;
        }
    }

    /**
     * Same turning controls happen when the key is released.
     */
    @Override
    public void keyReleased (KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A)
        {
            this.leftTurn = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D)
        {
            this.rightTurn = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W)
        {
            this.boost = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_SPACE
                || e.getKeyCode() == KeyEvent.VK_S)
        {
            this.shot = false;
        }
    }

    /**
     * These events are ignored.
     */
    @Override
    public void keyTyped (KeyEvent e)
    {
    }

    public class SoundHolder
    {
        private HashMap<String, Clip> clips;

        /**
         * Initialized all the clips into a sound holder object
         */
        public SoundHolder ()
        {
            this.clips = new HashMap<String, Clip>();
            this.clips.put("bangAlienShip", createClip("/sounds/bangAlienShip.wav"));
            this.clips.put("bangLarge", createClip("/sounds/bangLarge.wav"));
            this.clips.put("bangMedium", createClip("/sounds/bangMedium.wav"));
            this.clips.put("bangShip", createClip("/sounds/bangShip.wav"));
            this.clips.put("bangSmall", createClip("/sounds/bangShip.wav"));
            this.clips.put("beat1", createClip("/sounds/beat1.wav"));
            this.clips.put("beat2", createClip("/sounds/beat2.wav"));
            this.clips.put("fire", createClip("/sounds/fire.wav"));
            this.clips.put("saucerBig", createClip("/sounds/saucerBig.wav"));
            this.clips.put("saucerSmall", createClip("/sounds/saucerSmall.wav"));
            this.clips.put("thrust", createClip("/sounds/thrust.wav"));
        }

        /**
         * Creates an audio clip from a sound file.
         */
        public Clip createClip (String soundFile)
        {
            // Opening the sound file this way will work no matter how the
            // project is exported. The only restriction is that the
            // sound files must be stored in a package.
            try (BufferedInputStream sound = new BufferedInputStream(getClass().getResourceAsStream(soundFile)))
            {
                // Create and return a Clip that will play a sound file. There are
                // various reasons that the creation attempt could fail. If it
                // fails, return null.
                Clip clip = AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream(sound));
                return clip;
            }
            catch (LineUnavailableException e)
            {
                return null;
            }
            catch (IOException e)
            {
                return null;
            }
            catch (UnsupportedAudioFileException e)
            {
                return null;
            }
        }

        public HashMap<String, Clip> getClips ()
        {
            return this.clips;
        }
    }

    /**
     * Plays a clip when method is called
     */
    public void playClip (String clipName)
    {
        Clip clip = this.sounds.get(clipName);
        if (clip != null)
        {
            if (clip.isRunning())
            {
                clip.stop();
            }
            clip.setFramePosition(0);
            clip.start();
        }
    }

    /**
     * Starts a loop of a clip when called
     */
    public void startLoop (String clipName)
    {
        Clip clip = this.sounds.get(clipName);
        if (clip != null)
        {
            if (clip.isRunning())
            {
                clip.stop();
            }
            clip.setFramePosition(0);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    /**
     * Stops a loop of a clip when called
     */
    public void stopLoop (String clipName)
    {
        Clip clip = (Clip) this.sounds.get(clipName);
        if (clip != null)
        {
            if (clip.isRunning())
            {
                clip.stop();
            }
        }
    }
}
