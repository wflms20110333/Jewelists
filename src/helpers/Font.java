package helpers;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.opengl.Texture;

public class Font {
	
	private Map<Character, Glyph> glyphs;
	
	private final Texture texture;
	
	public Font(java.awt.Font font) {
		glyphs = new HashMap<>();
		texture = null;
	}
	
	private Texture createTexture() {
		return null;
	}
}
