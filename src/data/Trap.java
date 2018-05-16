package data;

import static helpers.Artist.*;
import static helpers.Clock.*;

import org.newdawn.slick.opengl.Texture;

import helpers.Clock;

/**
 * The Trap class blah blah
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
	 * The texture of Traps.
	 */
	private static Texture tex = quickLoad("trap");
	
	/**
	 * The sprite trapped in the Trap.
	 */
	private Sprite trappedSprite;
	
	/**
	 * The time since the trap was planted
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
		if (trappedSprite == null)
			return;
		draw();
		if (timeSinceStart >= DURATION)
		{
			trappedSprite.getPlayer().removeStatus(Status.STUN);
			trappedSprite = null;
			remove();
		}
	}
	
	/**
	 * @return whether the trap has been activated
	 */
	public boolean activated()
	{
		return trappedSprite != null;
	}
	
	/**
	 * Sets the sprite trapped in the trap.
	 * 
	 * @param s the sprite
	 */
	public void setTrappedSprite(Sprite s)
	{
		trappedSprite = s;
		timeSinceStart = 0;
		trappedSprite.getPlayer().addStatus(Status.STUN);
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
