package UI;

import java.awt.Rectangle;

import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.w3c.dom.css.Rect;

import static helpers.Artist.*;

import data.Game;
import data.Player;

<<<<<<< HEAD
/**
 * 
 * OR YOU CAN JUST CHANCE IT YOU KNOW. AS LONG AS YOU MAKE A GOOD GAME
 * 											- Ishman, 2018
 *
 */

public class Scoreboard extends UIItem {
	
	public static final Color[] colors = new Color[] {Color.red, Color.blue, Color.green, Color.yellow};
	Game game;
	
	
	public Scoreboard(Texture texture, Rectangle rect, Game game) {
		super(texture, rect);
		this.game = game;
	}

	@Override
	public void draw() {
		
		Rectangle rect = getRect();
		drawQuad(rect, Color.black);
		
		long sum = 0;
		for (Player player : game.getPlayers())
			sum += player.getScore();
		
		int x = rect.x;
		
		for (Player player : game.getPlayers()) {
			drawQuad(rect, Color.);
		}
	}
	
	@Override
	public void update(long miliseconds) {
		// prob make it more efficient
		draw();
	}
=======
public class Scoreboard extends UIItem
{

	Player[] players;

	public Scoreboard(Texture texture, Rectangle rect)
	{
		super(texture, rect);

	}

	@Override
	public void draw()
	{

	}

>>>>>>> 2509da0a66e0a5983c54a821475dc34454994bf0
}
