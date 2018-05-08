package data;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import static helpers.Artist.*;

public class Player
{
	private TileGrid grid;
	private TileType[] types;
	private int index;
	
	public Player(TileGrid grid)
	{
		this.grid = grid;
		this.types = new TileType[2];
		this.types[0] = TileType.Cave;
		this.types[1] = TileType.Wall;
		this.index = 0;
	}
	
	public void setTile()
	{
		grid.setTile((int) Math.floor(Mouse.getX() / TileGrid.SIZE), 
				(int) Math.floor((HEIGHT - Mouse.getY() - 1) / TileGrid.SIZE), types[index]);
	}
	
	public void update()
	{
		if (Mouse.isButtonDown(0)) {
			setTile();
		}
		while (Keyboard.next()) {
			if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT && Keyboard.getEventKeyState()) {
				moveIndex();
			}
		}
	}
	
	private void moveIndex()
	{
		index++;
		index %= types.length;
	}
}
