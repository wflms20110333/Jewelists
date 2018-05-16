package helpers;

import org.lwjgl.Sys;

/**
 * The Clock class is a helper class that assists with tracking the changes in
 * time during the game.
 * 
 * @author Elizabeth Zou
 */

public class Clock
{
	/**
	 * Whether or not the game is paused.
	 */
	private static boolean paused = false;

	/**
	 * The time at which {@link #update} was last called.
	 */
	private static double lastFrame;
	private static double thisFrame;

	/**
	 * The speed at which the game is running.
	 */
	private static double multiplier = 1;
	
	
	public Clock() {
		lastFrame = getTime();
	}

	/**
	 * @return the current time in milliseconds as a double
	 */
	private static double getTime()
	{
		return 1000.0 * Sys.getTime() / Sys.getTimerResolution();
	}

	/**
	 * Updates the status of the clock.
	 */
	public static void update()
	{
		lastFrame = thisFrame;
		thisFrame = getTime();
	}
	
	/**
	 * @return the speed at which the game is running
	 */
	public static double getMultiplier()
	{
		return multiplier;
	}
	
	/**
	 * Changes the speed at which the game is running.
	 * @param change the difference in speed for the game to run
	 */
	public static void changeMultiplier(float change)
	{
		if (multiplier + change >= 0 && multiplier + change <= 7)
			multiplier += change;
	}
	
	public static void togglePause()
	{
		paused = !paused;
	}
	
	public static float getSeconds() {
		return (float) (getMiliseconds() / 1000.0);
	}
	
	public static float getMiliseconds() {
		return (float) (multiplier * (thisFrame - lastFrame));
	}
}
