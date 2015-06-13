package MyDungeonPackage;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Vector3f;

public class GameData {
	
	static Vector3f CameraPosition = new Vector3f(0,-15,0);
	static Vector3f CameraRotation = new Vector3f(0,0,0);
	
	static Vector3f DepthPosition = new Vector3f(80.0f, -200.0f, 80.0f);
	static Vector3f DepthRotation = new Vector3f(35,134,0);
	
	static Vector3f SkyClear = new Vector3f(0.52f, 0.81f, 0.92f);
	static Vector3f LightColour = new Vector3f(1.0f, 1.0f, 1.0f);
	
	static boolean DayTime = true;
	static boolean Midnight = false;
	static boolean Lights = true;
	
	static boolean ConsoleOpen = false;
	static int DepthTexture;
	static int GameScale = 10;
	
	static int Width = 1920;
	static int Height = 1080;
	
	static float AttackData = 0.5f;
	static boolean AttackDirection = true;
	
	static boolean CloseGame = false;
	
	static PlayerType playerType = PlayerType.Warrior;
	
	static String LoadName;
	static boolean CustomDungeon = false;
	static boolean CustomEditor = false;
	static boolean hasNextFloor = false;
	
	static void Draw(int ShaderLoc){
		
		//GL11.glEnable( GL11.GL_TEXTURE_2D); 
		
		GL13.glActiveTexture(GL13.GL_TEXTURE1);
    	GL11.glBindTexture(GL11.GL_TEXTURE_2D, DepthTexture);
		GL20.glUniform1i(ShaderLoc, 1);
		
		
	}

}
