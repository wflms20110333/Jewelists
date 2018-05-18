package UI;

import java.awt.Rectangle;

import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import static helpers.Artist.*;

import data.Game;
import data.Player;
import helpers.Clock;

/**
 * Keeps the scoreboard for the game, as well as the timer for the game
 *
 * @author An Nguyen, Collin McMahon
 */
public class Scoreboard extends UIItem
{
	/**
	 * The size of the alert box
	 */
	public static final int ALERT_BOX_SPRITE_SIZE = 200;
	
	/**
	 * The x-coordinate of the alert box
	 */
	public static final int ALERT_BOX_SPRITE_X = (AlertBox.ALERT_BOX_WIDTH - ALERT_BOX_SPRITE_SIZE) / 2;
	
	private Game game;
	private double time;
	
	private AlertBox alertBox;

	/**
	 * Create a new scoreboard
	 * 
	 * @param texture the texture behind the scoreboard, should be set null
	 * @param rect the rectangle bounding the board
	 * @param game the game the board is keeping score for
	 * @param time the time until the game ends
	 */
	public Scoreboard(Texture texture, Rectangle rect, Game game, long time)
	{
		super(texture, rect);
		this.game = game;
		this.time = time;
	}

	/**
	 * Draw the scoreboard
	 */
	@Override
	public void draw()
	{
		Rectangle rect = getRect();
		drawQuad(rect, Color.black);
		drawString(rect.x, rect.y, "Time: " + (int) time, Color.white);

		float sum = 0;
		for (Player player : game.getPlayers())
			sum += player.getScore();
		
		int x = rect.x;
		for (Player player : game.getPlayers())
		{
			float portion = player.getScore() * rect.width / sum;
			drawQuad(x, rect.y + rect.height / 2, portion, rect.height / 2, player.getColor());
			x += portion;
		}
	}

	/**
	 * Update the scoreboard, then draw it
	 */
	@Override
	public void update()
	{
		time -= Clock.getSeconds();
		draw();
		if (time < 0)
		{
			if (alertBox == null)
			{
				alertBox = new AlertBox();
				alertBox.addButton("Okay", "button_okay");
				int winner = -1;
				Player[] players = game.getPlayers();
				if (players[0].getScore() > players[1].getScore())
					winner = 0;
				else if (players[0].getScore() < players[1].getScore())
					winner = 1;
				String message;
				if (winner == -1)
					message = "There is a tie O_O";
				else if (winner == 0)
					message = "Player 1 has won!!";
				else
					message = "Player 2 has won!!";
				alertBox.addString(new String[]{message});
				if (winner != -1)
					alertBox.addImage(players[winner].getSprite().getTexture(), ALERT_BOX_SPRITE_X,
							ALERT_BOX_SPRITE_SIZE, ALERT_BOX_SPRITE_SIZE);
			}
			alertBox.draw();
			if (alertBox.isButtonClicked("Okay"))
				game.end();
		}
	}
}
