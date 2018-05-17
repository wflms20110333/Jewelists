package data;

import static helpers.Clock.*;

import java.awt.GridBagConstraints;


public class AbilityManager {
	
	Player player;
	Ability ability;
	
	double cooldownTick;
	
	public AbilityManager(Player player) {
		this(player, Ability.random());
	}
	
	public AbilityManager(Player player, Ability ability) {
		this.player = player;
		this.ability = ability;
	}
	
	public void update() {
		if (cooldownTick != -1)
			cooldownTick += getSeconds();
		if (cooldownTick >= ability.getDuration())
			cooldownTick = -1;
	}
	
	public void activate() {
		if (cooldownTick == -1) {
			
			
			cooldownTick = 0;
		}
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
				Tile nextTile = sprite.getGrid().getTile(
					thisTile.getIndX() + TileGrid.changeX[k] * (int) ability.getValue(),
					thisTile.getIndY() + TileGrid.changeY[k] * (int) ability.getValue()
				);
				if (grid.canEnter(nextTile.getIndX(), nextTile.getIndY())) {
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
			Tile theirTile = other.getSprite().getCurrentTile();
			if (Math.abs(thisTile.getIndX() - theirTile.getIndX()) <= radius &&
				Math.abs(thisTile.getIndY() - theirTile.getIndY()) <= radius)
					other.addStatus(Status.SLOW, ability.duration);
		}
		
		return true;
	}
	
	static enum Ability {
		
		// buff
		SPEED(true), DMG_BOOST(true), MAGNET(true),
		// activated instantly
		BLINK(2), HEAL(2), SLOW(true);
		
		private final long duration;
		private final long cooldown;
		private final int value;
		private static final Ability[] abilities = values();
		
		public static final long BUFF_DURATION = 10;
		public static final long BUFF_COOLDOWN = 10;
		
		private Ability() {
			this(false);
		}
		
		private Ability(boolean buff) {
			this(buff, -1);
		}
		
		private Ability(int value) {
			this(false, value);
		}
		
		private Ability(boolean buff, int value) {
			if (buff) {
				this.duration = BUFF_DURATION;
				this.cooldown = BUFF_COOLDOWN;
			} else
				this.duration = this.cooldown = -1;
			this.value = value;
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
	}
}
