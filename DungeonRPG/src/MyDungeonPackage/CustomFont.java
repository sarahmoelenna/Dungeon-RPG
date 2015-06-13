package MyDungeonPackage;
import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;


public class CustomFont {
	
	Texture FontTexture;
	
	public CustomFont(String FontName){
		
		try {
			FontTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("Res/Textures/" + FontName + ".png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
	}
	
	public void DisplayFont(float x, float y, float scale, String MyText){
		
		scale = scale * 32;
		
		for(int i = 0; i < MyText.length(); i++){
			Vector2f TopLeft = new Vector2f(0,0);
			TopLeft = CharTopLeft(MyText.charAt(i));
			//System.out.println(MyText.charAt(i) + " " + i + " " + Character.getNumericValue(MyText.charAt(i)));
			//System.out.println(TopLeft);
			
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
		    GL11.glBindTexture(GL11.GL_TEXTURE_2D, FontTexture.getTextureID());
		    int locET = GL20.glGetUniformLocation(ShaderHandler.TextShader.shaderProgram, "ColourMap");
			GL20.glUniform1i(locET, 0);

			GL11.glLoadIdentity();
						
			GL11.glBegin(GL11.GL_QUADS);
	    	GL11.glTexCoord2f(TopLeft.x, TopLeft.y + 0.0625f);
	    	GL11.glVertex2f(x + i*scale, y);
	        
	    	GL11.glTexCoord2f(TopLeft.x + 0.0625f, TopLeft.y + 0.0625f);
	    	GL11.glVertex2f(x + scale + i*scale, y);
	        
	    	GL11.glTexCoord2f(TopLeft.x + 0.0625f, TopLeft.y);
	    	GL11.glVertex2f(x + scale + i*scale, y + scale);
	        
	    	GL11.glTexCoord2f(TopLeft.x, TopLeft.y);
	    	GL11.glVertex2f(x + i*scale, y + scale);
	    	GL11.glEnd();
			
			
		}
	}
	
public void DisplayFont(float x, float y, float scale, String MyText, float alpha){
		
		scale = scale * 32;
		
		for(int i = 0; i < MyText.length(); i++){
			Vector2f TopLeft = new Vector2f(0,0);
			TopLeft = CharTopLeft(MyText.charAt(i));
			//System.out.println(MyText.charAt(i) + " " + i + " " + Character.getNumericValue(MyText.charAt(i)));
			//System.out.println(TopLeft);
			
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
		    GL11.glBindTexture(GL11.GL_TEXTURE_2D, FontTexture.getTextureID());
		    int locET = GL20.glGetUniformLocation(ShaderHandler.TextAlphaShader.shaderProgram, "ColourMap");
			GL20.glUniform1i(locET, 0);
			
			int locE = GL20.glGetUniformLocation(ShaderHandler.TextAlphaShader.shaderProgram, "Alpha");
			GL20.glUniform1f(locE, alpha);

			GL11.glLoadIdentity();
						
			GL11.glBegin(GL11.GL_QUADS);
	    	GL11.glTexCoord2f(TopLeft.x, TopLeft.y + 0.0625f);
	    	GL11.glVertex2f(x + i*scale, y);
	        
	    	GL11.glTexCoord2f(TopLeft.x + 0.0625f, TopLeft.y + 0.0625f);
	    	GL11.glVertex2f(x + scale + i*scale, y);
	        
	    	GL11.glTexCoord2f(TopLeft.x + 0.0625f, TopLeft.y);
	    	GL11.glVertex2f(x + scale + i*scale, y + scale);
	        
	    	GL11.glTexCoord2f(TopLeft.x, TopLeft.y);
	    	GL11.glVertex2f(x + i*scale, y + scale);
	    	GL11.glEnd();
			
			
		}
	}

	private Vector2f CharTopLeft(char MyChar){
		
		Vector2f Position = new Vector2f(0,0);
		if(MyChar == 'A') {
			return Position = new Vector2f(0,0);
		}
		else if (MyChar == 'B') {
			return Position = new Vector2f(0.0625f,0);
		}
		else if (MyChar == 'C') {
			return Position = new Vector2f(0.0625f * 2,0);
		}
		else if (MyChar == 'D') {
			return Position = new Vector2f(0.0625f * 3,0);
		}
		else if (MyChar == 'E') {
			return Position = new Vector2f(0.0625f * 4,0);
		}
		else if (MyChar == 'F') {
			return Position = new Vector2f(0.0625f * 5,0);
		}
		else if (MyChar == 'G') {
			return Position = new Vector2f(0.0625f * 6,0);
		}
		else if (MyChar == 'H') {
			return Position = new Vector2f(0.0625f * 7,0);
		}
		else if (MyChar == 'I') {
			return Position = new Vector2f(0.0625f * 8,0);
		}
		else if (MyChar == 'J') {
			return Position = new Vector2f(0.0625f * 9,0);
		}
		else if (MyChar == 'K') {
			return Position = new Vector2f(0.0625f * 10,0);
		}
		else if (MyChar == 'L') {
			return Position = new Vector2f(0.0625f * 11,0);
		}
		else if (MyChar == 'M') {
			return Position = new Vector2f(0.0625f * 12,0);
		}
		else if (MyChar == 'N') {
			return Position = new Vector2f(0.0625f * 13,0);
		}
		else if (MyChar == 'O') {
			return Position = new Vector2f(0.0625f * 14,0);
		}
		else if (MyChar == 'P') {
			return Position = new Vector2f(0.0625f * 15,0);
		}
		else if (MyChar == 'Q') {
			return Position = new Vector2f(0.0625f * 0,0.0625f);
		}
		else if (MyChar == 'R') {
			return Position = new Vector2f(0.0625f * 1,0.0625f);
		}
		else if (MyChar == 'S') {
			return Position = new Vector2f(0.0625f * 2,0.0625f);
		}
		else if (MyChar == 'T') {
			return Position = new Vector2f(0.0625f * 3,0.0625f);
		}
		else if (MyChar == 'U') {
			return Position = new Vector2f(0.0625f * 4,0.0625f);
		}
		else if (MyChar == 'V') {
			return Position = new Vector2f(0.0625f * 5,0.0625f);
		}
		else if (MyChar == 'W') {
			return Position = new Vector2f(0.0625f * 6,0.0625f);
		}
		else if (MyChar == 'X') {
			return Position = new Vector2f(0.0625f * 7,0.0625f);
		}
		else if (MyChar == 'Y') {
			return Position = new Vector2f(0.0625f * 8,0.0625f);
		}
		else if (MyChar == 'Z') {
			return Position = new Vector2f(0.0625f * 9,0.0625f);
		}
		if(MyChar == 'a') {
			return Position = new Vector2f(0,0.0625f * 2);
		}
		else if (MyChar == 'b') {
			return Position = new Vector2f(0.0625f,0.0625f * 2);
		}
		else if (MyChar == 'c') {
			return Position = new Vector2f(0.0625f * 2,0.0625f * 2);
		}
		else if (MyChar == 'd') {
			return Position = new Vector2f(0.0625f * 3,0.0625f * 2);
		}
		else if (MyChar == 'e') {
			return Position = new Vector2f(0.0625f * 4,0.0625f * 2);
		}
		else if (MyChar == 'f') {
			return Position = new Vector2f(0.0625f * 5,0.0625f * 2);
		}
		else if (MyChar == 'g') {
			return Position = new Vector2f(0.0625f * 6,0.0625f * 2);
		}
		else if (MyChar == 'h') {
			return Position = new Vector2f(0.0625f * 7,0.0625f * 2);
		}
		else if (MyChar == 'i') {
			return Position = new Vector2f(0.0625f * 8,0.0625f * 2);
		}
		else if (MyChar == 'j') {
			return Position = new Vector2f(0.0625f * 9,0.0625f * 2);
		}
		else if (MyChar == 'k') {
			return Position = new Vector2f(0.0625f * 10,0.0625f * 2);
		}
		else if (MyChar == 'l') {
			return Position = new Vector2f(0.0625f * 11,0.0625f * 2);
		}
		else if (MyChar == 'm') {
			return Position = new Vector2f(0.0625f * 12,0.0625f * 2);
		}
		else if (MyChar == 'n') {
			return Position = new Vector2f(0.0625f * 13,0.0625f * 2);
		}
		else if (MyChar == 'o') {
			return Position = new Vector2f(0.0625f * 14,0.0625f * 2);
		}
		else if (MyChar == 'p') {
			return Position = new Vector2f(0.0625f * 15,0.0625f * 2);
		}
		else if (MyChar == 'q') {
			return Position = new Vector2f(0.0625f * 0,0.0625f * 3);
		}
		else if (MyChar == 'r') {
			return Position = new Vector2f(0.0625f * 1,0.0625f * 3);
		}
		else if (MyChar == 's') {
			return Position = new Vector2f(0.0625f * 2,0.0625f * 3);
		}
		else if (MyChar == 't') {
			return Position = new Vector2f(0.0625f * 3,0.0625f * 3);
		}
		else if (MyChar == 'u') {
			return Position = new Vector2f(0.0625f * 4,0.0625f * 3);
		}
		else if (MyChar == 'v') {
			return Position = new Vector2f(0.0625f * 5,0.0625f * 3);
		}
		else if (MyChar == 'w') {
			return Position = new Vector2f(0.0625f * 6,0.0625f * 3);
		}
		else if (MyChar == 'x') {
			return Position = new Vector2f(0.0625f * 7,0.0625f * 3);
		}
		else if (MyChar == 'y') {
			return Position = new Vector2f(0.0625f * 8,0.0625f * 3);
		}
		else if (MyChar == 'z') {
			return Position = new Vector2f(0.0625f * 9,0.0625f * 3);
		}
		else if (MyChar == '0') {
			return Position = new Vector2f(0,0.0625f * 4);
		}
		else if (MyChar == '1') {
			return Position = new Vector2f(0.0625f * 1,0.0625f * 4);
		}
		else if (MyChar == '2') {
			return Position = new Vector2f(0.0625f * 2,0.0625f * 4);
		}
		else if (MyChar == '3') {
			return Position = new Vector2f(0.0625f * 3,0.0625f * 4);
		}
		else if (MyChar == '4') {
			return Position = new Vector2f(0.0625f * 4,0.0625f * 4);
		}
		else if (MyChar == '5') {
			return Position = new Vector2f(0.0625f * 5,0.0625f * 4);
		}
		else if (MyChar == '6') {
			return Position = new Vector2f(0.0625f * 6,0.0625f * 4);
		}
		else if (MyChar == '7') {
			return Position = new Vector2f(0.0625f * 7,0.0625f * 4);
		}
		else if (MyChar == '8') {
			return Position = new Vector2f(0.0625f * 8,0.0625f * 4);
		}
		else if (MyChar == '9') {
			return Position = new Vector2f(0.0625f * 9,0.0625f * 4);
		}
		else if (MyChar == ':') {
			return Position = new Vector2f(0,0.0625f * 5);
		}
		else if (MyChar == ';') {
			return Position = new Vector2f(0.0625f * 1,0.0625f * 5);
		}
		else if (MyChar == '$') {
			return Position = new Vector2f(0.0625f * 2,0.0625f * 5);
		}
		else if (MyChar == ' ') {
			return Position = new Vector2f(0.0625f * 3,0.0625f * 5);
		}
		else if (MyChar == 39) {
			return Position = new Vector2f(0.0625f * 4,0.0625f * 5);
		}
		else if (MyChar == '!') {
			return Position = new Vector2f(0.0625f * 5,0.0625f * 5);
		}
		else if (MyChar == '?') {
			return Position = new Vector2f(0.0625f * 6,0.0625f * 5);
		}
		else if (MyChar == '.') {
			return Position = new Vector2f(0.0625f * 7,0.0625f * 5);
		}
		else if (MyChar == ',') {
			return Position = new Vector2f(0.0625f * 8,0.0625f * 5);
		}
		else if (MyChar == 47) {
			return Position = new Vector2f(0.0625f * 9,0.0625f * 5);
		}
		else if (MyChar == '+') {
			return Position = new Vector2f(0.0625f * 10,0.0625f * 5);
		}
		else if (MyChar == '-') {
			return Position = new Vector2f(0.0625f * 11,0.0625f * 5);
		}
		else if (MyChar == '<') {
			return Position = new Vector2f(0.0625f * 12,0.0625f * 5);
		}
		else if (MyChar == '>') {
			return Position = new Vector2f(0.0625f * 13,0.0625f * 5);
		}
		else return Position = new Vector2f(0.0625f * 3,0.0625f * 5);
		
		
	}
	
}
