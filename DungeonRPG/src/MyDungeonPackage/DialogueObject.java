package MyDungeonPackage;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class DialogueObject extends DungeonObject{

	MyTextureClass DialogueTexture;
	DialogueData MyData;
	
	public DialogueObject(int X, int Y,int Z, float size, DialogueData TempData) {
		super("grass", "black", X, Y, Z, DisplayObjectType.TexturedObject, size);
		ModelHandler.AddTexture("dialogue");
		DialogueTexture = ModelHandler.FindTexture("dialogue");
		MyData = TempData;
		// TODO Auto-generated constructor stub
	}
	
	public void Render2DObject(){
		if(PowerInput > 0){
			//System.out.println("rawr");
			GL11.glLoadIdentity();
			int locET = GL20.glGetUniformLocation(ShaderHandler.TextShader.shaderProgram, "ColourMap");
			DialogueTexture.Draw(locET);
			drawTexture(0,GameData.Width + 30,GameData.Width, -GameData.Width);
			
			FontHandler.ElitePro.DisplayFont(220, 140, 0.8f, MyData.LineOne);//58
			FontHandler.ElitePro.DisplayFont(220, 110, 0.8f, MyData.LineTwo);//58
			FontHandler.ElitePro.DisplayFont(220, 80, 0.8f, MyData.LineThree);//58
			
		}
		
	}

	public void RenderObject(){
	}
	
	public void RenderObjectDepth(){
	}

	private void drawTexture(float x, float y, int width, int height){
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0f, 0f);
		GL11.glVertex2f(x, y);
	    
		GL11.glTexCoord2f(1f, 0f);
		GL11.glVertex2f(x + width, y);
	    
		GL11.glTexCoord2f(1f, 1f);
		GL11.glVertex2f(x + width, y + height);
	    
		GL11.glTexCoord2f(0f, 1f);
		GL11.glVertex2f(x, y + height);
		GL11.glEnd();
	 }

}
