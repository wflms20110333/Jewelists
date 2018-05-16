package data;

import static helpers.Artist.beginSession;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.Display;

import helpers.Clock;
import helpers.StateManager;

/**
 * The Boot class blah
 * 
 * @author Elizabeth Zou
 * @author An Nguyen
 */

public class Boot
{
	public Boot()
	{
		beginSession();

		while (!Display.isCloseRequested())
		{
			glClear(GL_COLOR_BUFFER_BIT);
			Clock.update();
			StateManager.update();

			Display.update();
			Display.sync(60);
		}

		Display.destroy();
	}

	public static void main(String[] args)
	{
		new Boot();
	}
}
