package UI;

import static helpers.Artist.*;

import java.awt.Rectangle;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.Color;

import data.Player;

public class InfoBar extends UIItem {
	
	private Player player;
	
	public InfoBar(Player player, Texture texture, int x, int y, int width, int height) { 
		this(player, texture, new Rectangle(x, y, width, height));
	}
	
	public InfoBar(Player player, Texture texture, int x, int y) {
		this(player, texture, new Rectangle(x, y, texture.getImageWidth(), texture.getImageHeight()));
	}
	
	public InfoBar(Player player, Texture texture, Rectangle rect) {
		super(texture, rect);
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	
	@Override
	public void draw() {
		Rectangle rect = getRect();
		
		drawQuad(rect, Color.pink);
		
		drawString(rect.x + 10, rect.y + 10, "Jewels: " + player.getTotalJewels(), Color.white);
		
		// health bar
		drawQuad(rect.x, rect.y + 2 * rect.height / 3, rect.width, rect.height, Color.gray);
		drawQuad(rect.x, rect.y + 2 * rect.height / 3, rect.width * player.getPercent(), rect.height, Color.gray);
	}
	
	@Override
	public void update(long seconds) {
		draw();
	}
}
