package data;

import static helpers.Clock.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * The StateManager class manages effects for players.
 * 
 * @author An Nguyen
 */
public class StatusManager
{
	/**
	 * The time left for each effect.
	 */
	private Map<Status, Float> timeLeft;
	
	/**
	 * The duration of each effect.
	 */
	private Map<Status, Float> durations;
	
	/**
	 * Constructs a StatusManager.
	 */
	public StatusManager()
	{
		timeLeft = new HashMap<Status, Float>();
		durations = new HashMap<Status, Float>();
	}
	
	/**
	 * Adds a given effect.
	 * 
	 * @param effect the given effect
	 * @param seconds the duration of the given effect
	 */
	public void addStatus(Status effect, float seconds)
	{
		if (timeLeft.containsKey(effect))
		{
			timeLeft.put(effect, Math.max(seconds, timeLeft.get(effect)));
			durations.put(effect, Math.max(seconds, durations.get(effect)));
		}
		else
		{
			timeLeft.put(effect, seconds);
			durations.put(effect, seconds);
		}
	}
	
	/**
	 * Returns whether a given effect is active.
	 * 
	 * @param effect the given effect
	 * @return whether the given effect is active
	 */
	public boolean statusActive(Status effect)
	{
		return timeLeft.containsKey(effect);
	}
	
	/**
	 * Updates the statuses of the effects currently managed by the status
	 * manager, removing them if their durations are up.
	 */
	public void update()
	{
		Iterator<Entry<Status, Float>> iterator = timeLeft.entrySet().iterator();
		while (iterator.hasNext())
		{
			Entry<Status, Float> entry = iterator.next();
			entry.setValue(entry.getValue() - getSeconds());
			if (entry.getValue() <= 0)
			{
				iterator.remove();
				durations.remove(entry.getKey());
			}
		}
	}
}
