package data;

import org.newdawn.slick.opengl.Texture;

import static helpers.Clock.*;

import java.awt.datatransfer.FlavorTable;
import java.util.ArrayList;

/**
 * The Monster class blah blah
 * 
 * @author Elizabeth Zou
 */
public class Monster extends Entity
{
	
	private static final float DEFAULT_HEALTH = 10;
	private static final int MONSTER_ATTACK_RANGE = 1;
	private static final int MONSTER_ATTACK_DURATION = 10;
	
	/**
	 * The speed of the Monster.
	 */
	private float speed;
	
	/**
	 * True if {@link #update} has never been called.
	 */
	private boolean first = true;
	
	/**
	 * The direction the Monster is currently moving.
	 */
	private char direction;
	
	/**
	 * The tile the Monster is currently moving into.
	 */
	private Tile nextTile;
	
	/**
	 * A list of all the possible permutations of "ULDR", the four directions.
	 */
	private ArrayList<String> permutations;
	
	private StatusManager statuses;
	
	private float health;
	private float maxHealth;
	
	/**
	 * Constructs a Monster.
	 * 
	 * @param texture the texture of the monster
	 * @param startTile the starting tile of the monster
	 * @param grid the grid in which the monster exists
	 * @param speed the speed of the monster
	 */
	public Monster(Texture texture, Tile startTile, TileGrid grid, float speed)
	{
		super(texture, startTile, grid);
		getGrid().setOccupied(startTile, this);
		this.statuses = new StatusManager();
		health = maxHealth = DEFAULT_HEALTH;
		this.speed = speed;
		permutations = new ArrayList<>();
		genPerms("", "ULDR");
		randSetNextTile();
	}
		
	
	/**
	 * Private helper method that generates all the possible permutations of
	 * "ULDR", the four directions.
	 * 
	 * @param now the generated permuted string
	 * @param left a concatenation of the characters left to append
	 */
	private void genPerms(String now, String left)
	{
		if (left.length() == 0)
		{
			permutations.add(now);
			return;
		}
		for (int i = 0; i < left.length(); i++)
			genPerms(now + left.charAt(i), left.substring(0, i) + left.substring(i + 1));
	}
	
	/**
	 * Updates the status of the monster. At any given moment, a monster
	 * continues in a straight line or heads toward a random direction, given
	 * that a path in this new direction is available.
	 */
	@Override
	public void update()
	{
		// first delta calculated is huge
		if (first)
			first = false;
		else
		{
			if (statusActive(Status.POISON))
				heal(-Status.POISON.getMultiplier() * getSeconds());
			
			for (Tile tile : getGrid().getTilesInRange(getCurrentTile(), MONSTER_ATTACK_RANGE)) {
				Entity entity = getGrid().getMovingEntity(tile);
				if (entity != null && entity instanceof Sprite) {
					System.out.println("POISON");
					((Sprite) entity).getPlayer().addStatus(Status.POISON, MONSTER_ATTACK_DURATION);
				}
			}
			
			if (nextTile == null)
				randSetNextTile();
			if (nextTile == null)
				return;
			int nextX = nextTile.getIndX() * TileGrid.SIZE;
			int nextY = nextTile.getIndY() * TileGrid.SIZE;
			
			float adjusted_speed = speed;
			if (statusActive(Status.SLOW))
				adjusted_speed *= Status.SLOW.getMultiplier();
			if (statusActive(Status.SPEED))
				adjusted_speed *= Status.SPEED.getMultiplier();
			
			for (int k = 0; k < TileGrid.order.length; k++)
			{
				if (direction == TileGrid.order[k])
				{
					// compute position
					float x = getX() + getSeconds() * adjusted_speed * TileGrid.changeX[k];
					float y = getY() + getSeconds() * adjusted_speed * TileGrid.changeY[k];
					
					// adjust for overshot
					if (TileGrid.changeX[k] * (nextX - x) < 0)
						x = nextX;
					if (TileGrid.changeY[k] * (nextY - y) < 0)
						y = nextY;
					
					setX(x);
					setY(y);
					
					if (x == nextX && y == nextY)
					{
						getGrid().setOccupied(getCurrentTile(), null);
						setCurrentTile(nextTile);
						nextTile = null;
						if (Math.random() < 0.5)
							setNextTile(direction);
					}
				}
			}
		}
	}
	
	/**
	 * Private helper method that sets the tile that the monster will move into
	 * with a random direction.
	 */
	private void randSetNextTile()
	{
		setNextTile(permutations.get((int) (Math.random() * permutations.size())));
	}
	
	/**
	 * Private helper method that sets the tile that the monster will move into
	 * with a given ordering of the four directions, i.e. checks the
	 * availability in the four directions in a given order.
	 * 
	 * @param order the give ordering of the four directions
	 */
	private void setNextTile(String order)
	{
		if (statusActive(Status.STUN))
			return;
		for (char c : order.toCharArray())
		{
			int i = (int) (getX() / TileGrid.SIZE);
			int j = (int) (getY() / TileGrid.SIZE);
			for (int k = 0; k < TileGrid.order.length; k++)
			{
				if (TileGrid.order[k] == c && getGrid().canEnter(i + TileGrid.changeX[k], j + TileGrid.changeY[k]))
				{
					setNextTile(c);
					return;
				}
			}
		}
	}
	
	/**
	 * Adds a status effect.
	 * 
	 * @param effect the given effect
	 * @param seconds the duration of the effect
	 */
	public void addStatus(Status effect, float seconds)
	{
		statuses.addStatus(effect, seconds);
	}
	
	/**
	 * Returns whether a given effect is active.
	 * 
	 * @param effect the given effect
	 * @return whether the given effect is active
	 */
	public boolean statusActive(Status effect)
	{
		return statuses.statusActive(effect);
	}
	
	public float getHealth()
	{
		return health;
	}
	
	public void heal(float heal)
	{
		this.health += heal;
		if (health > maxHealth)
			health = maxHealth;
		if (health < 0) 
			kill();
	}
	
	public void kill() {
		getGrid().setOccupied(getCurrentTile(), null);
		if (nextTile != null)
			getGrid().setOccupied(nextTile, null);
		nextTile = null;
		remove();
	}
	
 	public float getMaxHealth()
	{
		return maxHealth;
	}
	
	/**
	 * Private helper method that sets the tile that the monster will move into
	 * given a preferred direction. If movement in this direction is allowed,
	 * the monster will continue to move in this direction; else the monster
	 * will proceed in a random available direction.
	 * 
	 * @param dir the preferred direction
	 */
	private void setNextTile(char dir)
	{
		if (statusActive(Status.STUN))
			return;
		if (nextTile != null)
			return;
		int i = getCurrentTile().getIndX();
		int j = getCurrentTile().getIndY();
		for (int k = 0; k < TileGrid.order.length; k++)
		{
			if (TileGrid.order[k] == dir)
			{
				i += TileGrid.changeX[k];
				j += TileGrid.changeY[k];
			}
		}
		if (getGrid().canEnter(i, j))
		{
			nextTile = getGrid().getTile(i, j);
			getGrid().setOccupied(nextTile, this);
			direction = dir;
		}
		else
			nextTile = null;
	}
	
	/**
	 * Returns the speed of the monster.
	 * 
	 * @return the speed of the monster
	 */
	public float getSpeed()
	{
		return speed;
	}
	
	/**
	 * Sets the speed of the monster.
	 * 
	 * @param speed the new speed of the monster
	 */
	public void setSpeed(float speed)
	{
		this.speed = speed;
	}
}
