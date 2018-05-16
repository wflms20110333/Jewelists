package data;

public enum TileType
{
	Cave("cave_32", true),
	Wall("wall2_32", false),
	//Deposit1("deposit1", false),
	//Deposit2("deposit2", false),
	Grass("grass_32", true),
	Dirt("dirt_32", false),
	Water("water_32", false);
	
	String textureName;
	boolean buildable;
	
	TileType(String textureName, boolean buildable)
	{
		this.textureName = textureName;
		this.buildable = buildable;
	}
}
