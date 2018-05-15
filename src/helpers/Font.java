package helpers;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.opengl.Texture;

public class Font {
	
	private Map<Character, Glyph> glyphs;
	
	private final Texture texture;
	
	private int height;
	
	public Font(java.awt.Font font) {
		glyphs = new HashMap<>();
		texture = null;
	}
	
	private Texture createTexture(java.awt.Font Font) {
		int width = 0, height = 0;
		
		for (char c = 32; c < 256; c++) {
			if (c == 127)
				continue;
			
		}
		
		return null;
	}
	
	private BufferedImage createChar(java.awt.Font font, char c) {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	}
}
