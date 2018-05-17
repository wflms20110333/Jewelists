package data;

public enum TileType
{
	Cave("cave"),
	Wall("wall"),
	Dirt("dirt"),
	Water("water");
	
	String textureName;
	
	TileType(String textureName)
	{
		this.textureName = textureName;
	}
}
