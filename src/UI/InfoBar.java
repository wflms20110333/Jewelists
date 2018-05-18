package UI;

import static helpers.Artist.*;

import java.awt.Rectangle;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.Color;

import data.Player;
import data.TileGrid;

/**
 * Display information about the user, including health, deadtimer, ability, and jewel count.
 * 
 * @author An Nguyen
 * Dependencies: slick(to manage textures and colors)
 */
public class InfoBar extends UIItem
{
	/**
	 * Scale used for health bar. The bigger the smaller the bar
	 */
	public static final int SCALE = 5;
	/**
	 * The padding for drawn text
	 */
	public static final int PADDING = 10;
	
	private Player player;

	/**
	 * Create a new information bar
	 * 
	 * @param player the player this belongs to
	 * @param texture the texture of the bar, preferably null
	 * @param x the x coordinate of the bar
	 * @param y the y coordinate of the bar
	 * @param width the width of the bar
	 * @param height the height of the bar
	 */
	public InfoBar(Player player, Texture texture, int x, int y, int width, int height)
	{
		this(player, texture, new Rectangle(x, y, width, height));
	}

	/**
	 * Create a new information bar
	 * 
	 * @param player the player this belongs to
	 * @param texture the texture of the bar, preferably null
	 * @param x the x coordinate of the bar
	 * @param y the y coordinate of the bar
	 */
	public InfoBar(Player player, Texture texture, int x, int y)
	{
		this(player, texture, new Rectangle(x, y, texture.getImageWidth(), texture.getImageHeight()));
	}

	/**
	 * Create a new information bar
	 * 
	 * @param player the player this belongs to
	 * @param texture the texture of the bar, preferably null
	 * @param rect the bounding rectangle of the bar
	 */
	public InfoBar(Player player, Texture texture, Rectangle rect)
	{
		super(texture, rect);
		this.player = player;
	}

	/**
	 * @return the player the bar is monitoring
	 */
	public Player getPlayer()
	{
		return player;
	}

	/**
	 * Set which player the bar is monitoring
	 * @param player the player the bar is monitoring
	 */
	public void setPlayer(Player player)
	{
		this.player = player;
	}

	/**
	 * Draw the bar
	 */
	@Override
	public void draw()
	{
		Rectangle rect = getRect();
		
		drawQuad(rect, Color.black);
		
		Color transparent = new Color(player.getColor().a, player.getColor().g, player.getColor().b, 0.3f);
		
		Texture texture = player.getSprite().getTexture();
		drawQuadTex(texture, rect.x + PADDING, rect.y + PADDING, TileGrid.SIZE, TileGrid.SIZE);
		drawString(rect.x + PADDING * 2 + TileGrid.SIZE, rect.y + PADDING, 
				"" + player.getJewels(), Color.white);
		
		float abilitySize = (float) ((SCALE - 1) * rect.getHeight() / SCALE);
		drawQuadTex(player.getAbility().getTexture(), (float) (rect.getX() + rect.getWidth() - abilitySize), 
				(float) rect.getY(), abilitySize, abilitySize);
		
		float cooldownPercent = player.getCooldownLeft() / player.getAbility().getCooldown();
		drawQuad((float) (rect.getX() + rect.getWidth() - abilitySize), 
				(float) rect.getY(), abilitySize, abilitySize * cooldownPercent, new Color(transparent));
		
		// health bar
		drawQuad(rect.x, rect.y + (SCALE - 1) * rect.height / SCALE, rect.width, rect.height / SCALE, transparent);
		drawQuad(rect.x, rect.y + (SCALE - 1) * rect.height / SCALE, rect.width * player.getPercent(),
				rect.height / SCALE, player.getColor());
	}

	/**
	 * Update the bar
	 */
	@Override
	public void update()
	{
		draw();
	}
}
