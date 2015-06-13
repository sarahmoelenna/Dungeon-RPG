package MyDungeonPackage;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;

public class CharacterSelectMenu {

	MyTextureClass MyBackgroundTexture;
	MyTextureClass MyATexture;
	
	int PauseState; // 0 - return, 1, main menu, 2 - exit
	float PauseTimer;
	boolean UnPause;
	boolean ReleasedOnce;
	
	public CharacterSelectMenu(){
		ModelHandler.AddTexture("background");
		MyBackgroundTexture = ModelHandler.FindTexture("background");
		
		ModelHandler.AddTexture("abutton");
		MyATexture = ModelHandler.FindTexture("abutton");
		
		PauseState = 0;
		PauseTimer = 1;
		UnPause = false;
		ReleasedOnce = false;
	}
	
	public void UpdateCharacterSelect(int delta){
		
		PauseTimer += 0.005f * delta;
		if(PauseTimer > 1){PauseTimer = 1;}
		
		if(PauseTimer == 1 && Input.GetA() == true){
			PauseState --;
			PauseTimer = 0;
		}
		
		if(PauseTimer == 1 && Input.GetD() == true){
			PauseState ++;
			PauseTimer = 0;
		}
		
		if(PauseState < 0){PauseState = 3;}
		
		if(PauseState > 3){PauseState = 0;}
		
		if((Input.GetF() != true && Input.GetEnter()!= true) && ReleasedOnce == false){
			ReleasedOnce = true;
		}
		
		if((Input.GetF() || Input.GetEnter()) && PauseTimer == 1 && ReleasedOnce == true){
			if(PauseState == 0){
				UnPause = true;
				PauseTimer = 0;
				GameData.playerType = PlayerType.Warrior;
			}
			if(PauseState == 1){
				UnPause = true;
				PauseTimer = 0;
				GameData.playerType = PlayerType.Paladin;
			}
			if(PauseState == 2){
				UnPause = true;
				PauseTimer = 0;
				GameData.playerType = PlayerType.Thief;
			}
			if(PauseState == 3){
				UnPause = true;
				PauseTimer = 0;
				GameData.playerType = PlayerType.Necromancer;
			}
		}
		
	}
	
	public void DrawCharacterSelect(){
		GL11.glLoadIdentity();
		ShaderHandler.TextShader.Activate();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
	    int locET = GL20.glGetUniformLocation(ShaderHandler.TextShader.shaderProgram, "ColourMap");
	    MyBackgroundTexture.Draw(locET);
	    drawTexture(0,0,1920,1080);
	    
	    MyATexture.Draw(locET);
	    drawTexture(210 + PauseState*480,120,64,64);
	    
	    ShaderHandler.TextShader.DeActivate();
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
