package data;

import static helpers.Clock.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class StatusManager {
	
	private Map<Status, Float> timeLeft;
	private Map<Status, Float> durations;
	
	public StatusManager() {
		timeLeft = new HashMap<Status, Float>();
		durations = new HashMap<Status, Float>();
	}
	
	public void addStatus(Status effect, float duration) {
		if (timeLeft.containsKey(effect)) {
			timeLeft.put(effect, Math.max(duration, timeLeft.get(effect)));
			durations.put(effect, Math.max(duration, durations.get(effect)));
		} else {
			timeLeft.put(effect, duration);
			durations.put(effect, duration);
		}
	}
	
	// not recommended, do not use
	public void removeStatus(Status effect) {
		timeLeft.remove(effect);
		durations.remove(effect);
	}
	
	public boolean statusActive(Status effect) {
		return timeLeft.containsKey(effect);
	}
	
	public void update() {
		Iterator<Entry<Status, Float>> iterator = timeLeft.entrySet().iterator();
		while (iterator.hasNext()){
			Entry<Status, Float> entry = iterator.next();
			entry.setValue(entry.getValue() - getSeconds());
			if (entry.getValue() <= 0) {
				iterator.remove();
				durations.remove(entry.getKey());
			}
		}
	}
}
