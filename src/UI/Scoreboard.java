package UI;

import java.awt.Rectangle;

import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import static helpers.Artist.*;

import data.Game;
import data.Player;

/**
 * 
 * OR YOU CAN JUST CHANCE IT YOU KNOW. AS LONG AS YOU MAKE A GOOD GAME - Ishman,
 * 2018
 *
 */

public class Scoreboard extends UIItem
{

	public static final Color[] colors = new Color[]
	{ Color.red, Color.blue, Color.green, Color.yellow };
	Game game;

	public Scoreboard(Texture texture, Rectangle rect, Game game)
	{
	long time;
	
	public Scoreboard(Texture texture, Rectangle rect, Game game, long time) {
		super(texture, rect);
		this.game = game;
		this.time = time;
	}

	@Override
	public void draw()
	{
	public void draw() {
		Rectangle rect = getRect();
		
		drawQuad(rect, Color.black);

		drawString(rect.x, rect.y, "Time: " + time, Color.white);
		
		long sum = 0;
		for (Player player : game.getPlayers())
			sum += player.getScore();

		int x = rect.x;

		for (Player player : game.getPlayers())
		{
		
		int color_index = 0;
		for (Player player : game.getPlayers()) {
			int portion = (int) ((double) player.getScore() / sum * rect.width);
			drawQuad(x, rect.y + rect.height / 2, portion, rect.height / 2, colors[color_index++]);
			x += portion;
		}
	}

	@Override
	public void update(long miliseconds)
	{
		// prob make it more efficient
	public void update(long miliseconds) {
		time -= miliseconds;
		draw();
	}
}
