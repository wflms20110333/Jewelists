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
	private static long lastFrame; // = getTime();

	/**
	 * The total time that the game has been running.
	 */
	private static long totalTime;

	/**
	 * The change in time since {@link #update} was last called.
	 */
	private static float d = 0;

	/**
	 * The speed at which the game is running.
	 */
	private static float multiplier = 1;

	/**
	 * Returns the current time in milliseconds as a long.
	 * 
	 * @return the current time in milliseconds as a long
	 */
	public static long getTime()
	{
		return Sys.getTime() * 1000 / Sys.getTimerResolution();
	}

	/**
	 * Returns the change in time since {@link #update} was last called.
	 * 
	 * @return the change in time since {@link #update} was last called
	 */
	public static float getDelta()
	{
		long currentTime = getTime();
		int delta = (int) (currentTime - lastFrame);
		lastFrame = getTime();
		// return Math.min(delta * 0.01f, 0.5f);
		return delta * 0.01f;
	}

	/**
	 * Updates the status of the clock.
	 */
	public static void update()
	{
		d = getDelta();
		totalTime += d;
	}

	/**
	 * Returns the change in time since {@link #update} was last called, 0 if
	 * the game is paused.
	 * 
	 * @return the change in time since {@link #update} was last called, 0 if
	 *         the game is paused
	 */
	public static float delta()
	{
		if (paused)
			return 0;
		else
			return d * multiplier;
	}
	
	/**
	 * Returns the total time that the game has been running.
	 * @return the total time that the game has been running
	 */
	public static float getTotalTime()
	{
		return totalTime;
	}
	
	/**
	 * Returns the speed at which the game is running.
	 * @return the speed at which the game is running
	 */
	public static float getMultiplier()
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
	
	/**
	 * Pauses the game if it is running, unpauses the game if it is paused.
	 */
	public static void toggle_pause()
	{
		paused = !paused;
	}
}
