package data;

import static helpers.Artist.*;

import org.newdawn.slick.opengl.Texture;

import helpers.Clock;

/**
 * The Trap class represents traps that players can build and step on. Trapped
 * players are unable to move for a period of time.
 * 
 * @author Elizabeth Zou
 */
public class Trap extends Entity
{
	/**
	 * The duration of the buffer period (in seconds).
	 */
	private static final long BUFFER = 2;
	
	/**
	 * The duration of the Trap (in seconds).
	 */
	private static final long DURATION = 3;
	
	/**
	 * Whether the Trap has been activated.
	 */
	private boolean activated;
	
	/**
	 * The texture of Traps.
	 */
	private static Texture tex = quickLoad("trap");
	
	/**
	 * The time since the Trap was planted.
	 */
	private double timeSinceStart;
	
	/**
	 * Constructs a Trap.
	 * 
	 * @param startTile the tile at which the trap is located
	 * @param grid the grid in which the trap exists
	 */
	public Trap(Tile startTile, TileGrid grid)
	{
		super(tex, startTile, grid);
		timeSinceStart = 0;
	}
	
	/**
	 * Updates the state of the trap. The trap is drawn only during the buffer
	 * period or when a sprite is trapped in it. After a trap is used, it is
	 * automatically removed.
	 */
	@Override
	public void update()
	{
		timeSinceStart += Clock.getSeconds();
		if (timeSinceStart < BUFFER)
			draw();
		if (!activated)
			return;
		draw();
		if (timeSinceStart >= DURATION)
			remove();
	}
	
	/**
	 * Returns whether the trap has been activated.
	 * 
	 * @return whether the trap has been activated
	 */
	public boolean activated()
	{
		return activated;
	}
	
	/**
	 * Sets the sprite trapped in the trap.
	 * 
	 * @param s the sprite
	 */
	public void activate(Sprite s)
	{
		s.getPlayer().addStatus(Status.STUN, DURATION);
		timeSinceStart = 0;
		activated = true;
	}
	
	/**
	 * Returns whether the buffer period has passed.
	 * 
	 * @return whether the buffer period has passed
	 */
	public boolean getBufferPassed()
	{
		return timeSinceStart >= BUFFER;
	}
}
