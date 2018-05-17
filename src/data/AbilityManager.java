package data;

import static helpers.Clock.*;

import java.awt.GridBagConstraints;

import org.w3c.dom.NamedNodeMap;


public class AbilityManager {
	
	Player player;
	Ability ability;
	
	float cooldownTick;
	
	public AbilityManager(Player player) {
		this(player, Ability.random());
	}
	
	public AbilityManager(Player player, Ability ability) {
		this.player = player;
		this.ability = ability;
		this.cooldownTick = -1;
	}
	
	public void update() {
		if (cooldownTick != -1)
			cooldownTick += getSeconds();
		if (cooldownTick >= ability.getCooldown())
			cooldownTick = -1;
	}
	
	public void activate() {
		if (cooldownTick == -1) {
			boolean applied = false;
			if (ability == Ability.SPEED)
				applied = applySpeed();
			if (ability == Ability.DMG_BOOST)
				applied = applyDmgBoost();
			if (ability == Ability.MAGNET)
				applied = applyMagnet();
			if (ability == Ability.SLOW)
				applied = applySlow();
			if (ability == Ability.BLINK)
				applied = blink();
			if (ability == Ability.HEAL)
				applied = heal();
			
			if (applied)
				cooldownTick = 0;
		}
	}
	
	public Ability getAbility() {
		return ability;
	}
	
	// MANAGES EACH ABILITY
	private boolean applySpeed() {
		player.addStatus(Status.SPEED, ability.getDuration());
		return true;
	}
	
	private boolean applyDmgBoost() {
		player.addStatus(Status.DMG_BOOST, ability.getDuration());
		return true;
	}
	
	private boolean applyMagnet() {
		player.addStatus(Status.MAGNET, ability.getDuration());
		return true;
	}
	
	private boolean blink() {
		Sprite sprite = player.getSprite();
		char direction = sprite.getFacingDirection();
		Tile thisTile = sprite.getCurrentTile();
		TileGrid grid = sprite.getGrid();
		
		for (int k = 0; k < TileGrid.order.length; k++) {
			if (direction == TileGrid.order[k]) {
				int nextX = thisTile.getIndX() + TileGrid.changeX[k] * (int) ability.getValue();
				int nextY = thisTile.getIndY() + TileGrid.changeY[k] * (int) ability.getValue();
				if (grid.canEnter(nextX, nextY)) {
					Tile nextTile = sprite.getGrid().getTile(nextX, nextY);
					player.getSprite().blink(nextTile);
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean heal() {
		player.setHealth(player.getHealth() + (int) ability.getValue());
		return true;
	}
	
	private boolean applySlow() {
		Sprite sprite = player.getSprite();
		Tile thisTile = sprite.getCurrentTile();
		
		int radius = (int) ability.getValue();
		// apply slow in a radius
		for (Player other : player.getGame().getPlayers()) {
			if (other == player)
				continue;
			Tile theirTile = other.getSprite().getCurrentTile();
			if (Math.abs(thisTile.getIndX() - theirTile.getIndX()) <= radius &&
				Math.abs(thisTile.getIndY() - theirTile.getIndY()) <= radius)
					other.addStatus(Status.SLOW, ability.getDuration());
		}
		
		return true;
	}
	
	static enum Ability {
		// buff
		SPEED("Speed", true), DMG_BOOST("Damage boost", true), MAGNET("Magnet", true),
		// activated instantly
		BLINK("Blink", 3), HEAL("Heal", 2), SLOW("Slow", true, 3);
		
		private static final Ability[] abilities = values();
		
		public static final long BUFF_DURATION = 10;
		public static final long BUFF_COOLDOWN = 10;
		
		private final long duration;
		private final long cooldown;
		private final int value;
		private final String name;
		
		private Ability(String name) {
			this(name, false);
		}
		
		private Ability(String name, boolean buff) {
			this(name, buff, -1);
		}
		
		private Ability(String name, int value) {
			this(name, false, value);
		}
		
		private Ability(String name, boolean buff, int value) {
			if (buff)
				this.duration = BUFF_DURATION;
			else
				this.duration = -1;
			this.cooldown = BUFF_COOLDOWN;
			this.value = value;
			this.name = name;
		}
		
		public long getDuration() {
			return duration;
		}
		
		public long getCooldown() {
			return cooldown;
		}
		
		public long getValue() {
			return value;
		}
		
		public static Ability random() {
			return abilities[(int) (Math.random() * abilities.length)];
		}
		
		@Override
		public String toString() {
			return name;
		}
	}
}
