package UI;

import static helpers.Artist.*;

import java.awt.Rectangle;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.Color;

import data.Player;
import data.TileGrid;

/**
 * The InfoBar class blah blah
 * 
 * @author An Nguyen
 */
public class InfoBar extends UIItem
{
	public static final int SCALE = 5;
	public static final int PADDING = 10;
	
	private Player player;

	public InfoBar(Player player, Texture texture, int x, int y, int width, int height)
	{
		this(player, texture, new Rectangle(x, y, width, height));
	}

	public InfoBar(Player player, Texture texture, int x, int y)
	{
		this(player, texture, new Rectangle(x, y, texture.getImageWidth(), texture.getImageHeight()));
	}

	public InfoBar(Player player, Texture texture, Rectangle rect)
	{
		super(texture, rect);
		this.player = player;
	}

	public Player getPlayer()
	{
		return player;
	}

	public void setPlayer(Player player)
	{
		this.player = player;
	}

	@Override
	public void draw()
	{
		Rectangle rect = getRect();
		
		drawQuad(rect, Color.black);
		
		Texture texture = player.getSprite().getTexture();
		drawQuadTex(texture, rect.x + PADDING, rect.y + PADDING, TileGrid.SIZE, TileGrid.SIZE);
		drawString(rect.x + PADDING * 2 + TileGrid.SIZE, rect.y + PADDING, "" + player.getJewels() + " " + player.getAbility(), Color.white);
		

		// health bar
		drawQuad(rect.x, rect.y + (SCALE - 1) * rect.height / SCALE, rect.width, rect.height / SCALE, Color.gray);
		drawQuad(rect.x, rect.y + (SCALE - 1) * rect.height / SCALE, rect.width * player.getPercent(), rect.height / SCALE, Color.red);
	}

	@Override
	public void update()
	{
		draw();
	}
}
