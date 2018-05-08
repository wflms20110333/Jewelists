package helpers;

import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;
import java.io.InputStream;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Artist
{
	public static final int WIDTH = 1280, HEIGHT = 736; //960
	
	public static void beginSession()
	{
		Display.setTitle("HCS3 Project");
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.create();
		} catch (LWJGLException e) {
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
	}
	
	public static void drawQuad(float x, float y, float width, float height)
	{
		glBegin(GL_QUADS);
		glVertex2f(x, y); // Top left corner
		glVertex2f(x + width, y); // Top right corner
		glVertex2f(x + width, y + height); // Bottom right corner
		glVertex2f(x, y + height); // Bottom left corner
		glEnd();
	}
	
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
	
	public static Texture loadTexture(String path, String fileType)
	{
		Texture tex = null;
		InputStream in = ResourceLoader.getResourceAsStream(path);
		try {
			tex = TextureLoader.getTexture(fileType, in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tex;
	}
	
	public static Texture quickLoad(String name)
	{
		Texture tex = null;
		tex = loadTexture("Assets/" + name + ".png", "PNG");
		return tex;
	}
}
