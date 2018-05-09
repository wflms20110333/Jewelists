package data;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import java.util.LinkedList;
import java.util.Queue;

public class Player
{
	private static final int WALL_COST = 2;

	private TileGrid grid;
	private int[] keys = new int[9];
	private Sprite sprite;
	private int totalJewels;
	Queue<Deposit> deposits;
	
	private TileType otherPlayerDeposit;

	public Player(TileGrid grid, int[] keys, Texture texture, TileType other)
	{
		this.grid = grid;
		for (int i = 0; i < this.keys.length; i++)
			this.keys[i] = keys[i];
		Tile tile = grid.randEmptyTile();
		sprite = new Sprite(texture, tile, grid, 5);
		totalJewels = 0;
		deposits = new LinkedList<Deposit>();
		otherPlayerDeposit = other;
	}

	/*
	 * public void setTile() { grid.setTile((int) Math.floor(Mouse.getX() /
	 * TileGrid.SIZE), (int) Math.floor((HEIGHT - Mouse.getY() - 1) /
	 * TileGrid.SIZE), types[index]); }
	 */

	public void update()
	{
		/*
		 * if (Mouse.isButtonDown(0)) { //setTile(); }
		 */
		// while (Keyboard.next()) {
		Keyboard.next();
		
		// shift
		if (Keyboard.isKeyDown(keys[4]) && Keyboard.getEventKeyState())
		{
			if (Keyboard.isKeyDown(keys[0]) && Keyboard.getEventKeyState())
			{
				Tile tgt = grid.up(sprite.currTile());
				if (tgt != null)
					attack(tgt);
			}
			if (Keyboard.isKeyDown(keys[1]) && Keyboard.getEventKeyState())
			{
				Tile tgt = grid.left(sprite.currTile());
				if (tgt != null)
					attack(tgt);
			}
			if (Keyboard.isKeyDown(keys[2]) && Keyboard.getEventKeyState())
			{
				Tile tgt = grid.down(sprite.currTile());
				if (tgt != null)
					attack(tgt);
			}
			if (Keyboard.isKeyDown(keys[3]) && Keyboard.getEventKeyState())
			{
				Tile tgt = grid.right(sprite.currTile());
				if (tgt != null)
					attack(tgt);
			}
		}
		else
		{
			if (Keyboard.isKeyDown(keys[0]) && Keyboard.getEventKeyState())
			{
				sprite.updatePath('U');
			}
			if (Keyboard.isKeyDown(keys[1]) && Keyboard.getEventKeyState())
			{
				sprite.updatePath('L');
			}
			if (Keyboard.isKeyDown(keys[2]) && Keyboard.getEventKeyState())
			{
				sprite.updatePath('D');
			}
			if (Keyboard.isKeyDown(keys[3]) && Keyboard.getEventKeyState())
			{
				sprite.updatePath('R');
			}
		}

		if (Keyboard.isKeyDown(keys[5]) && Keyboard.getEventKeyState())
		{
			// new deposit
		}
		if (Keyboard.isKeyDown(keys[6]) && Keyboard.getEventKeyState())
		{
			Tile tile = sprite.currTile();
			if (tile.getType() == TileType.Cave) // && spendJewels(WALL_COST))
				grid.setTile(tile.getIndX(), tile.getIndY(), TileType.Wall);
		}
		if (Keyboard.isKeyDown(keys[7]) && Keyboard.getEventKeyState())
		{
			// bomb
		}
		if (Keyboard.isKeyDown(keys[8]) && Keyboard.getEventKeyState())
		{
			// trap
		}
		
		sprite.update();
		sprite.draw();
	}
	
	private void attack(Tile tile)
	{
		// graphics of attacking??
		if (tile.getType() == TileType.Wall) // && spendJewels(WALL_COST))
			grid.setTile(tile.getIndX(), tile.getIndY(), TileType.Wall);
		if (tile.getType() == otherPlayerDeposit) {
			// steal from deposit
		}
	}
	
	/**
	 * Expends a specified number of jewels from the player's deposits.
	 * @param count the number of jewels to expend
	 * @return whether or not the jewels have been successfully expended
	 */
	private boolean spendJewels(int count)
	{
		if (totalJewels < count)
			return false;
		totalJewels -= count;
		while (count > 0)
		{
			Deposit d = deposits.peek();
			if (d.getNumJewels() <= count)
			{
				count -= d.getNumJewels();
				deposits.poll();
			}
			else
			{
				d.remove(count);
				count = 0;
			}
		}
		return true;
	}
}
