package MyDungeonPackage;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

public class ParticleHandler {
	
	static int vbo_vertex_handle;
	static int vbo_normal_handle;
	static int vbo_texture_handle;
	static int vbo_Colour_handle;
	static FloatBuffer VertBuffer;
	static FloatBuffer NormBuffer;
	static FloatBuffer TextBuffer;
	static FloatBuffer ColourBuffer;
	
	static boolean genOnce = false;

	static ArrayList<ParticleEmitter> EmitterList = new ArrayList<>();
	
	public static void AddEmitter(ParticleEmitter MyEmitter){
		EmitterList.add(MyEmitter);
	}
	
	public static ParticleEmitter FindEmitter(ParticleEmitter MyEmitter){
		int Reference = 0;
		
		for(int i = 0; i < EmitterList.size(); i++){
			if(EmitterList.get(i).equals(MyEmitter)){
				Reference = i;
			}
		}
		
		return(EmitterList.get(Reference));
	}
	
	public static void UpdateParticles(int delta){
		for(ParticleEmitter MyEmitter: EmitterList){
			MyEmitter.UpdateParticles(delta);
		}
	}
	
	public static void DrawParticles(){
		if(GetParticleCount() > 0){
		//long Start = (Sys.getTime() * 1000) / Sys.getTimerResolution();
		for(ParticleEmitter MyEmitter: EmitterList){
			if(MyEmitter.ParticleList.size() > 0 && MyEmitter.Render == true){
				MyEmitter.BuildParticleBuffers();
			}
		}
		BuildBuffers();
		//long End = (Sys.getTime() * 1000) / Sys.getTimerResolution();
		//System.out.println("Build Time: " + (float)(End - Start));
		
		
		//Start = (Sys.getTime() * 1000) / Sys.getTimerResolution();
		DrawAll();
		//End = (Sys.getTime() * 1000) / Sys.getTimerResolution();
		//System.out.println("Draw Time: " + (float)(End - Start));
		}
	}
	
	public static void DeleteEmitters(){
		EmitterList.clear();
	}
	
	public static int GetParticleCount(){
		int count = 0;
		
		for(ParticleEmitter MyEmitter: EmitterList){
			count += MyEmitter.ParticleList.size();
		}
		
		return count;
	}
	
	private static void DrawAll(){
		if(GetParticleCount() > 0){
			ShaderHandler.ParticleColourShader.Activate();
			GL11.glLoadIdentity();
			GL11.glRotatef(GameData.CameraRotation.y, 0, 1, 0);
			GL11.glTranslatef(GameData.CameraPosition.x, GameData.CameraPosition.y, GameData.CameraPosition.z);

			GL11.glEnable(GL11.GL_COLOR);
			GL11.glEnable(GL11.GL_COLOR_MATERIAL);
			GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
  			GL11.glEnableClientState(GL11.GL_NORMAL_ARRAY);
  			GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
  		  
  			GL15.glBindBuffer( GL15.GL_ARRAY_BUFFER, vbo_vertex_handle);
  			GL11.glVertexPointer(3, GL11.GL_FLOAT, 0, 0);

  			GL15.glBindBuffer( GL15.GL_ARRAY_BUFFER, vbo_normal_handle);
  			GL11.glNormalPointer(GL11.GL_FLOAT, 0, 0);
  		  
  			GL15.glBindBuffer( GL15.GL_ARRAY_BUFFER, vbo_Colour_handle);
  			GL11.glColorPointer(4, GL11.GL_FLOAT, 0, 0);

  			GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, GetParticleCount()*6);
  			
  			ShaderHandler.ParticleColourShader.DeActivate();
		}
	}
	
	private static void BuildBuffers(){
		
		//long Start = (Sys.getTime() * 1000) / Sys.getTimerResolution();
		VertBuffer = BufferUtils.createFloatBuffer(GetParticleCount()*2 * 9);
        NormBuffer = BufferUtils.createFloatBuffer(GetParticleCount()*2 * 9);
        ColourBuffer = BufferUtils.createFloatBuffer(GetParticleCount()*2 * 12);
        TextBuffer = BufferUtils.createFloatBuffer(GetParticleCount()*2 * 6);
        //long End = (Sys.getTime() * 1000) / Sys.getTimerResolution();
        //System.out.println("Buffer Create Time: " + (float)(End - Start));
		
        //Start = (Sys.getTime() * 1000) / Sys.getTimerResolution();
		for(ParticleEmitter MyEmitter: EmitterList){
			if(MyEmitter.ParticleList.size() > 0 && MyEmitter.Render == true){
				VertBuffer.put(MyEmitter.FinVertArray);
				NormBuffer.put(MyEmitter.FinNormArray);
				TextBuffer.put(MyEmitter.FinTextArray);
				ColourBuffer.put(MyEmitter.FinColourArray);
			}
		}
		//End = (Sys.getTime() * 1000) / Sys.getTimerResolution();
        //System.out.println("Buffer Put Time: " + (float)(End - Start));
        
        //Start = (Sys.getTime() * 1000) / Sys.getTimerResolution();
        VertBuffer.rewind();
        NormBuffer.rewind();
        TextBuffer.rewind();
        ColourBuffer.rewind();
        //End = (Sys.getTime() * 1000) / Sys.getTimerResolution();
        ///System.out.println("Buffer Rewind Time: " + (float)(End - Start));
		
        //Start = (Sys.getTime() * 1000) / Sys.getTimerResolution();
        if(genOnce == false){
        	vbo_vertex_handle = GL15.glGenBuffers();
        }
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo_vertex_handle);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, VertBuffer, GL15.GL_DYNAMIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        
        if(genOnce == false){
        	vbo_normal_handle = GL15.glGenBuffers();
        }
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo_normal_handle);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, NormBuffer, GL15.GL_DYNAMIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        
        	vbo_texture_handle = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo_texture_handle);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, TextBuffer, GL15.GL_DYNAMIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        
        if(genOnce == false){
        	vbo_Colour_handle = GL15.glGenBuffers();
        }
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo_Colour_handle);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, ColourBuffer, GL15.GL_DYNAMIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        //End = (Sys.getTime() * 1000) / Sys.getTimerResolution();
        //System.out.println("Buffer Gen Time: " + (float)(End - Start));
        genOnce = true;
	}
	
}
