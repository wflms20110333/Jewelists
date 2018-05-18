package helpers;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.InputStream;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import data.TileGrid;

import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

/**
 * The Artist class is a helper class that assists with the game's graphics.
 * 
 * @author Elizabeth Zou
 */

public class Artist
{
	private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	/**
	 * The width and height of the window that displays the game.
	 */
	public static final int WIDTH = (int) (screenSize.getWidth() / TileGrid.SIZE) * TileGrid.SIZE;
	public static final int HEIGHT = (int) (screenSize.getHeight() / TileGrid.SIZE - 2) * TileGrid.SIZE;
	
	public static final Color DEFAULT_COLOR = Color.black;
	public static final Color MASTER_COLOR = Color.white;
	
	private static TrueTypeFont font;
	
	/**
	 * Sets up the display and the settings for the graphics.
	 */
	public static void beginSession()
	{
		Display.setTitle("Jewelists");
		try
		{
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.create();
		} catch (LWJGLException e)
		{
			e.printStackTrace();
		}
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, WIDTH, HEIGHT, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		glEnable(GL_TEXTURE_2D);
		
		// makes sprite backgrounds not black, blending with background tiles
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		// load font
		setFont("font_orange_juice", 40);
	}
	
	public static TrueTypeFont getFont() {
		return font;
	}
	
	public static void setFont(String name, int sz) {
		try
		{
			InputStream input = ResourceLoader.getResourceAsStream("Assets/" + name + ".ttf");
			Font temp = Font.createFont(Font.TRUETYPE_FONT, input);
			temp = temp.deriveFont((float) sz);
			System.out.println(temp);
			font = new TrueTypeFont(temp, true);
		} catch (Exception e)
		{
			System.err.println("Cannot find font: " + name + ".ttf");
		}
	}
	
	/**
	 * Draws a white rectangular area onto the game display.
	 * 
	 * @param x the x coordinate of the top left corner of rectangular area
	 * @param y the y coordinate of the top left corner of the rectangular area
	 * @param width the width of the rectangular area
	 * @param height the height of the rectangular area
	 */
	public static void drawQuad(float x, float y, float width, float height)
	{
		drawQuad(new Rectangle((int) x, (int) y, (int) width, (int) height), DEFAULT_COLOR);
	}
	
	public static void drawQuad(float x, float y, float width, float height, Color color)
	{
		drawQuad(new Rectangle((int) x, (int) y, (int) width, (int) height), color);
	}
	
	public static void drawQuad(Rectangle rect, Color color) {
		
		glColor4f(
			color.getRed() / 255f, 
			color.getGreen() / 255f, 
			color.getBlue() / 255f, 
			color.getAlpha() / 255f
		);
		
		glDisable(GL_TEXTURE_2D);
		glBegin(GL_QUADS);
		glVertex2f(rect.x, rect.y); // Top left corner
		glVertex2f(rect.x + rect.width, rect.y); // Top right corner
		glVertex2f(rect.x + rect.width, rect.y + rect.height); // Bottom right corner
		glVertex2f(rect.x, rect.y + rect.height); // Bottom left corner
		glEnd();
		glColor4f(255,255,255,255);
		glEnable(GL_TEXTURE_2D);
	}
	
	
	/**
	 * Draws a rectangular area with a texture onto the game display.
	 * 
	 * @param tex the texture of the rectangular area
	 * @param x the x coordinate of the top left corner of rectangular area
	 * @param y the y coordinate of the top left corner of the rectangular area
	 * @param width the width of the rectangular area
	 * @param height the height of the rectangular area
	 */
	public static void drawQuadTex(Texture tex, float x, float y, float width, float height)
	{
		tex.bind();
		glTranslatef(x, y, 0);
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0);
		glVertex2f(0, 0);
		glTexCoord2f(1, 0);
		glVertex2f(width, 0);
		glTexCoord2f(1, 1);
		glVertex2f(width, height);
		glTexCoord2f(0, 1);
		glVertex2f(0, height);
		glEnd();
		glLoadIdentity();
	}
	
	/**
	 * Loads a texture from a local file.
	 * 
	 * @param path the path to the file
	 * @param fileType the type of the file (e.g. PNG)
	 * @return the texture loaded from the given file
	 */
	public static Texture loadTexture(String path, String fileType)
	{
		Texture tex = null;
		InputStream in = ResourceLoader.getResourceAsStream(path);
		try
		{
			tex = TextureLoader.getTexture(fileType, in);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return tex;
	}
	
	public static void drawString(int x, int y, String s, Color color) {
		font.drawString(x, y, s, color);
		glColor4f(255,255,255,255);
	}
	
	/**
	 * Loads a texture from a local file, without defining path or file type.
	 * 
	 * @param name the name of the file
	 * @return the texture loaded from the file
	 */
	public static Texture quickLoad(String name)
	{
		return loadTexture("Assets/" + name + ".png", "PNG");
	}
}
