package helpers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import data.Tile;
import data.TileGrid;

public class MapHelper
{
	public static void saveMap(String mapName, TileGrid grid)
	{
		String mapData = "";
		for (int i = 0; i < TileGrid.COLS; i++) {
			for (int j = 0; j < TileGrid.ROWS; j++) {
				mapData += getTileID(grid.getTile(i, j));
			}
		}
		
		try
		{
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(mapName)));
			bw.write(mapData);
			bw.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static String getTileID(Tile t)
	{
		switch (t.getType())
		{
		case Cave:
			return "0";
		case Wall:
			return "1";
		//case Deposit1:
			//return "2";
		//case Deposit2:
			//return "3";
		case Grass:
			return "4";
		case Dirt:
			return "5";
		case Water:
			return "6";
		}
		return "E";
	}
}
