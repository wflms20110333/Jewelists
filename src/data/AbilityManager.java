package data;

import java.util.Random;

public class AbilityManager {
	
	Player player;
	Ability ability;
	
	long durationTick, cooldownTick;
	
	public AbilityManager(Player player) {
		this(player, Ability.random());
	}
	
	public AbilityManager(Player player, Ability ability) {
		this.player = player;
		this.ability = ability;
	}
	
	// MANAGES EACH ABILITY
	
	private void activateSpeed() { 
		
	}
	
	static enum Ability {
		
		// buff
		SPEED(true), DMG_BOOST(true), MAGNET(true),
		// activated instantly
		BLINK, HEAL, SLOW;
		
		private final long duration;
		private final long cooldown;
		private static final Ability[] abilities = values();
		
		public static final long BUFF_DURATION = 10;
		public static final long BUFF_COOLDOWN = 10;
		
		private Ability() {
			this(false);
		}
		
		private Ability(boolean buff) {
			if (buff) {
				this.duration = BUFF_DURATION;
				this.cooldown = BUFF_COOLDOWN;
			} else
				this.duration = this.cooldown = -1;
		}
		
		public long getDuration() {
			return duration;
		}
		
		public long getCooldown() {
			return cooldown;
		}
		
		public static Ability random() {
			return abilities[(int) (Math.random() * abilities.length)];
		}
	}
}
