package MyDungeonPackage;

import java.io.IOException;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;


class LoadScreen {
	
	static Texture textureA;
	
	static Texture textureB;
	
	static int LoadBar = 0;

	public static void SwitchToOrth(){
   	 GL11.glDisable(GL11.GL_DEPTH_TEST);
   	 GL11.glMatrixMode(GL11.GL_PROJECTION);
   	 GL11.glPushMatrix();
   	 GL11.glLoadIdentity();
   	 GL11.glOrtho(0, 1920, 0, 1080, -1, 15);
   	 GL11.glMatrixMode(GL11.GL_MODELVIEW);
   	 GL11.glLoadIdentity();
    }
    
	public static void SwitchToPerspective(){
   	 GL11.glEnable(GL11.GL_DEPTH_TEST);
   	 GL11.glMatrixMode(GL11.GL_PROJECTION);
   	 GL11.glPopMatrix();
   	 GL11.glMatrixMode(GL11.GL_MODELVIEW);
    }
	
	public static void LoadLoadTexture(String Name){
		
		try {
			textureA = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("Res/Textures/" + Name + ".png"));
		} catch (IOException e1) {
		}
		
		
		try {
			textureB = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("/Res/Textures/loading.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public static void LoadLoadTexture(){
		
		try {
			textureA = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("Res/Textures/black.png"));
			System.out.println("Texture Loaded: Black");
		} catch (IOException e1) {


		}
		
		
		try {
			textureB = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("Res/Textures/loading.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
    
    public static void ShowLoad(){
    	
    	LoadBar++;
    	
    	ShaderHandler.TextShader.Activate();
    	
    	GL11.glDisable(GL11.GL_CULL_FACE);
    	
    	SwitchToOrth();
    	/*GL11.glDisable(GL11.GL_LIGHTING);
    	GL11.glEnable(GL11.GL_TEXTURE_2D);
    	GL13.glActiveTexture(GL13.GL_TEXTURE0);
    	GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureB.getTextureID());
    	//System.out.println("I AM BEING MOTHERFUCKING CALLED BITCH");
    	GL11.glLoadIdentity();
    	
    	GL11.glBegin(GL11.GL_QUADS);
    	
    	GL11.glTexCoord2f(0, 1);
    	GL11.glVertex3f(0f, 0f, -1.01f);
    	
    	GL11.glTexCoord2f(0, 0);
    	GL11.glVertex3f(0f, 1f, -1.01f);
    	
    	GL11.glTexCoord2f(1, 0);
    	GL11.glVertex3f(1f, 1f, -1.01f);
    	
    	GL11.glTexCoord2f(1, 1);
    	GL11.glVertex3f(1f, 0f, -1.01f);
    	
    	GL11.glEnd();*/
    	
    	
    	//SwitchToPerspective();
    	if(LoadBar > 6){
    		LoadBar = 0;
    	}
    	
    	//MyFont.Scale(0.8f, 0.8f);
    	//MyFont.Print("Loading Level", -0.22f, -0.85f, 1f, 1f, 1f);
    	FontHandler.ElitePro.DisplayFont(748, 76, 1f, "Loading Level");
    	
    	if(LoadBar == 0){
    		//MyFont.Scale(1, 1);
        	//MyFont.Print(". ", -0.2f, -0.9f, 1f, 1f, 1f);
        	FontHandler.ElitePro.DisplayFont(768, 51, 1f, ". ");
    	}
    	if(LoadBar == 1){
    		//MyFont.Scale(1, 1);
        	//MyFont.Print(". . ", -0.2f, -0.9f, 1f, 1f, 1f);
        	FontHandler.ElitePro.DisplayFont(768, 51, 1f, ". . ");
    	}
    	if(LoadBar == 2){
    		//MyFont.Scale(1, 1);
        	//MyFont.Print(". . . ", -0.2f, -0.9f, 1f, 1f, 1f);
        	FontHandler.ElitePro.DisplayFont(768, 51, 1f, ". . . ");
    	}
    	if(LoadBar == 3){
    		//MyFont.Scale(1, 1);
        	//MyFont.Print(". . . . ", -0.2f, -0.9f, 1f, 1f, 1f);
        	FontHandler.ElitePro.DisplayFont(768, 51, 1f, ". . . . ");
    	}
    	if(LoadBar == 4){
    		//MyFont.Scale(1, 1);
        	//MyFont.Print(". . . . .", -0.2f, -0.9f, 1f, 1f, 1f);
        	FontHandler.ElitePro.DisplayFont(768, 51, 1f, ". . . . .");
    	}
    	if(LoadBar == 5){
    		//MyFont.Scale(1, 1);
        	//MyFont.Print(". . . . .", -0.2f, -0.9f, 1f, 1f, 1f);
        	FontHandler.ElitePro.DisplayFont(768, 51, 1f, ". . . . . .");
    	}
    	
    	ShaderHandler.TextShader.DeActivate();
    	Display.update();
    	SwitchToPerspective();
    }
	
}
