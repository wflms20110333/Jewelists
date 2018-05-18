package data;

import static helpers.Artist.beginSession;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import helpers.Clock;
import helpers.StateManager;

/**
 * The Boot class is startup sequence that sets up the game when it is run.
 * 
 * @author Elizabeth Zou
 * @author An Nguyen
 * Dependencies: lwjgl(to manage keyboard input and screen display)
 */
public class Boot
{
	/**
	 * Constructs a Boot.
	 */
	public Boot()
	{
		beginSession();
		Keyboard.enableRepeatEvents(true);
		while (!Display.isCloseRequested())
		{
			glClear(GL_COLOR_BUFFER_BIT);
			Keyboard.enableRepeatEvents(true);
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
