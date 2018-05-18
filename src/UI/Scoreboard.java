package UI;

import java.awt.Rectangle;

import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import static helpers.Artist.*;

import data.Game;
import data.Player;
import helpers.Clock;

/**
 * 
 * OR YOU CAN JUST CHANCE IT YOU KNOW. AS LONG AS YOU MAKE A GOOD GAME - Ishman,
 * 2018
 *
 */

public class Scoreboard extends UIItem
{
	Game game;
	double time;
	
	AlertBox alertBox;
	
	public static final int ALERT_BOX_SPRITE_SIZE = 200;
	public static final int ALERT_BOX_SPRITE_X = (AlertBox.ALERT_BOX_WIDTH - ALERT_BOX_SPRITE_SIZE) / 2;

	public Scoreboard(Texture texture, Rectangle rect, Game game, long time)
	{
		super(texture, rect);
		this.game = game;
		this.time = time;
	}

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
