package data;

/**
 * The TileType enum represents the different tile types that can form the
 * grid of the game.
 * 
 * @author Elizabeth Zou
 */
public enum TileType
{
	/**
	 * Cave tiles.
	 */
	Cave("cave"),
	
	/**
	 * Wall tiles.
	 */
	Wall1("wall"),
	
	Wall2("wall"),
	
	Wall3("wall"),
	
	/**
	 * Dirt tiles.
	 */
	Dirt("dirt"),
	
	/**
	 * Water tiles.
	 */
	Water("water");
	
	/**
	 * The name of the texture that represents the tile type.
	 */
	String textureName;
	
	/**
	 * Constructs a new TileType.
	 * 
	 * @param textureName the name of the texture that represents the tile type
	 */
	TileType(String textureName)
	{
		this.textureName = textureName;
	}
}
