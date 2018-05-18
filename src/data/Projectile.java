package data;

import static helpers.Clock.getSeconds;

import java.util.List;

import org.newdawn.slick.opengl.Texture;

public class Projectile extends Entity {
	
	public static final float speed = 200;
	public static List<Projectile> projectileList;
	
	Tile nextTile;
	int direction;
	
	public Projectile(Texture texture, Tile startTile, TileGrid grid) {
		this(texture, startTile, grid, 'U');
	}
	
	public Projectile(Texture texture, Tile startTile, TileGrid grid, char direction) {
		super(texture, startTile, grid);
		this.direction = 0;
		for (int k = 0; k < TileGrid.order.length; k++) {
			if (direction == TileGrid.order[k])
				this.direction = k;
		}
	}
	
	@Override
	public void update() {
		int nextX = nextTile.getX();
		int nextY = nextTile.getY();
		
		if (!getGrid().validIndex(getCurrentTile().getIndX(), getCurrentTile().getIndY())) {
			remove();
			return;
		} else {
			Entity moving = getGrid().getMovingEntity(getCurrentTile());
			if (moving != null && moving instanceof Sprite) {
				Player player = ((Sprite) moving).getPlayer();
				player.heal(-3);
				remove();
			}
		}
		
		if (getGrid().validIndex(nextTile.getIndX(), nextTile.getIndY())) {
			Entity moving = getGrid().getMovingEntity(nextTile);
			if (moving != null && moving instanceof Sprite) {
				Player player = ((Sprite) moving).getPlayer();
				player.heal(-3);
				remove();
			}
		}
		
		// compute position
		float x = getX() + getSeconds() * speed * TileGrid.changeX[direction];
		float y = getY() + getSeconds() * speed * TileGrid.changeY[direction];
		
				
		if (TileGrid.changeX[direction] * (nextX - x) < 0)
			x = nextX;
		if (TileGrid.changeY[direction] * (nextY - y) < 0)
			y = nextY;
				
		setX(x);
		setY(y);
				
		if (x == nextX && y == nextY)
		{
			setCurrentTile(nextTile);
			nextTile = new Tile((int) (x + TileGrid.SIZE), (int) (y + TileGrid.SIZE), 0, 0, TileType.Cave);
		}
	}
	
	@Override
	public void remove() {
		projectileList.remove(this);
	}
}
