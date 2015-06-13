package MyDungeonPackage;

import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class MyTextureClass {
	
	Texture MyTexture;
	String TextureName;
	
	public MyTextureClass(String Name){
		
		TextureName = Name;
		
		try {
			MyTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("Res/Textures/" + Name +".png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}

	public String GetTextureName(){
		
		return TextureName;
		
	}
	
	public void Draw(int ShaderLoc){
		GL11.glEnable( GL11.GL_TEXTURE_2D); 
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
    	GL11.glBindTexture(GL11.GL_TEXTURE_2D, MyTexture.getTextureID());
		GL20.glUniform1i(ShaderLoc, 0);
	}
	
	public void Draw(){
		GL11.glEnable( GL11.GL_TEXTURE_2D); 
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
    	GL11.glBindTexture(GL11.GL_TEXTURE_2D, MyTexture.getTextureID());
	}
	
	public void DrawTwo(int ShaderLoc){
		GL11.glEnable( GL11.GL_TEXTURE_2D); 
		GL13.glActiveTexture(GL13.GL_TEXTURE2);
    	GL11.glBindTexture(GL11.GL_TEXTURE_2D, MyTexture.getTextureID());
		GL20.glUniform1i(ShaderLoc, 2);
	}
	
	public void DrawThree(int ShaderLoc){
		GL11.glEnable( GL11.GL_TEXTURE_2D); 
		GL13.glActiveTexture(GL13.GL_TEXTURE3);
    	GL11.glBindTexture(GL11.GL_TEXTURE_2D, MyTexture.getTextureID());
		GL20.glUniform1i(ShaderLoc, 3);
	}
	
}
