package MyDungeonPackage;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.BufferUtils;
import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class ParticleEmitter {

	ArrayList<Particle> ParticleList = new ArrayList<>();
	ArrayList<Particle> ParticleRemoveList = new ArrayList<>();
	
	Vector3f Position;
	float EmitTimer;
	
	Random Temp;
	
	int vbo_vertex_handle;
	int vbo_normal_handle;
	int vbo_texture_handle;
	int vbo_Colour_handle;
	FloatBuffer VertBuffer;
	FloatBuffer NormBuffer;
	FloatBuffer TextBuffer;
	FloatBuffer ColourBuffer;
	
	 float FinVertArray[];
     float FinNormArray[];
     float FinColourArray[];
     float FinTextArray[];
	
	int type;
	Vector3f Colour;
	
	boolean Render;
	
	public ParticleEmitter(float x, float y, float z){
		Render = true;
		Position = new Vector3f(0,0,0);
		Colour = new Vector3f(1,1,1);
		
		Position.x = x;
		Position.y = y;
		Position.z = z;
		EmitTimer = 0;
		Position = new Vector3f(Position.x * GameData.GameScale + GameData.GameScale/2, Position.y * GameData.GameScale, Position.z * GameData.GameScale + GameData.GameScale/2);
		
		type = 1;
		
		Temp = new Random();
		
	}
	
	public ParticleEmitter(float x, float y, float z, Vector3f MyColour, int Type){
		Render = true;
		Position = new Vector3f(0,0,0);
		Colour = new Vector3f(1,1,1);
		
		Position.x = x;
		Position.y = y;
		Position.z = z;
		Colour.x = MyColour.x;
		Colour.y = MyColour.y;
		Colour.z = MyColour.z;
		EmitTimer = 0;
		Position = new Vector3f(Position.x * GameData.GameScale + GameData.GameScale/2, Position.y * GameData.GameScale, Position.z * GameData.GameScale + GameData.GameScale/2);
		
		type = Type;
		
		Temp = new Random();
		
	}
	
	public void UpdateEmitter(int delta){
		
		EmitTimer += 0.003f * delta;
		//if(EmitTimer > 1){EmitTimer = 1;}
		//System.out.println(EmitTimer);
		
		if(type == 0){
			if(EmitTimer == 1){
				//Particle TempParticle = new Particle(Position, new Vector3f(0,0,1f));
				ParticleList.add(new Particle(new Vector3f(0,0,0), new Vector3f(0,0,0.1f),Colour, Position));
				EmitTimer = 0;
			}
		}
		
		if(type == 1){
			if(EmitTimer > 1){
				//for(int i = 0; i < 1; i++){
				float x = (RanInt(0,200) - 100)/100.0f;
				float y = (RanInt(0,200) - 100)/100.0f;
				float z = (RanInt(0,200) - 100)/100.0f;
				ParticleList.add(new Particle(new Vector3f(0,0,0), new Vector3f(x,y,z), Colour, Position));
				//}
				EmitTimer = 0;
				//System.out.println("made");
			}
		}
		
		if(type == 2){
			if(EmitTimer > 1){
				for(int i = 0; i < 5; i++){
					float x = (RanInt(0,200) - 100)/200.0f * GameData.GameScale;
					float y = (RanInt(0,200) - 100)/200.0f * GameData.GameScale;
					float z = (RanInt(0,200) - 100)/200.0f * GameData.GameScale;
					ParticleList.add(new Particle(new Vector3f(x,y,z), new Vector3f(0,0,0), Colour, Position, 0.3f, 0.01f));
				}
				EmitTimer = 0;
				//System.out.println("made");
			}
		}
		
		
	}
	
	public void UpdateParticles(int delta){
		//if(ParticleList.size() > 0){
			//System.out.println(ParticleList.size() + " " + type);
		//}
		for(Particle MyParticle :  ParticleList){
			MyParticle.UpdateParticle(delta);
			//System.out.println(MyParticle.Position.y < -(1 * GameData.GameScale));
			if(MyParticle.Scale < 0){
				ParticleRemoveList.add(MyParticle);
			}
		}
		
		for(Particle MyParticle :  ParticleRemoveList){
			ParticleList.remove(MyParticle);
		}
		ParticleRemoveList.clear();
		
	}
	
	public void DrawParticle(){
		//System.out.println(ParticleList.size());
		if(ParticleList.size() > 0 && Render == true){
			
			ShaderHandler.ParticleColourShader.Activate();
			GL11.glLoadIdentity();
			GL11.glRotatef(GameData.CameraRotation.y, 0, 1, 0);
			GL11.glTranslatef(GameData.CameraPosition.x, GameData.CameraPosition.y, GameData.CameraPosition.z);
		
			//GL11.glTranslatef(Position.x, Position.y, Position.z);
			//GL11.glRotatef(0, 0, 1, 0);
		
			//GL11.glScalef(GameData.GameScale, GameData.GameScale, GameData.GameScale);
		
			
			
			BuildParticleBuffers();
		
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

  			GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, ParticleList.size()*6);
  			
  			ShaderHandler.ParticleColourShader.DeActivate();
		}
	}
	
	public void BuildParticleBuffers(){
		
		//VertBuffer = BufferUtils.createFloatBuffer(ParticleList.size()*2 * 9);
        //NormBuffer = BufferUtils.createFloatBuffer(ParticleList.size()*2 * 9);
        //ColourBuffer = BufferUtils.createFloatBuffer(ParticleList.size()*2 * 12);
        //TextBuffer = BufferUtils.createFloatBuffer(ParticleList.size()*2 * 6);
        
		//long Start = (Sys.getTime() * 1000) / Sys.getTimerResolution();
        FinVertArray = new float[ParticleList.size()*2 * 3 * 3];
        FinNormArray = new float[ParticleList.size()*2 * 3 * 3];
        FinColourArray = new float[ParticleList.size()*2 * 3 * 4];
        FinTextArray = new float[ParticleList.size()*2 * 3 * 2];
        //long End = (Sys.getTime() * 1000) / Sys.getTimerResolution();
        //System.out.println("Array Construction Time: " + (float)(End - Start));
        
        int Position = 0;
        
        //Start = (Sys.getTime() * 1000) / Sys.getTimerResolution();
		for(Particle MyParticle :  ParticleList){
			//triangle one
				Vector3f VA;
				Vector3f VB;
				Vector3f VC;
				float Angle = MyMath.HeadingVectorToAngle(new Vector2f(GameData.CameraPosition.x - MyParticle.Position.x + MyParticle.StartPosition.x, GameData.CameraPosition.z - MyParticle.Position.z + MyParticle.StartPosition.z));
				Angle = Angle * - 1;
				//System.out.println(Angle);
				//v1
				VA = new Vector3f(-1 * MyParticle.Scale, -1 * MyParticle.Scale, 0 * MyParticle.Scale);
				VA = MyMath.RotateAboutOrigin(VA, Angle, false);
				FinVertArray[Position*18] = VA.x + MyParticle.Position.x + MyParticle.StartPosition.x;
				FinVertArray[Position*18 + 1] = VA.y + MyParticle.Position.y + MyParticle.StartPosition.y;
				FinVertArray[Position*18 + 2] = VA.z + MyParticle.Position.z + MyParticle.StartPosition.z;
				//if(type == 3){
				//	System.out.println(FinVertArray[Position*18] + " " + FinVertArray[Position*18 + 2]);
				//	System.out.println(GameData.CameraPosition + " Camera");
				//}
				//v2
				VB = new Vector3f(1 * MyParticle.Scale, 1 * MyParticle.Scale, 0 * MyParticle.Scale);
				VB = MyMath.RotateAboutOrigin(VB, Angle, false);
				FinVertArray[Position*18 + 3] = VB.x + MyParticle.Position.x + MyParticle.StartPosition.x;
				FinVertArray[Position*18 + 4] = VB.y + MyParticle.Position.y + MyParticle.StartPosition.y;
				FinVertArray[Position*18 + 5] = VB.z + MyParticle.Position.z + MyParticle.StartPosition.z;
				//v2
				VC = new Vector3f(1 * MyParticle.Scale, -1 * MyParticle.Scale, 0 * MyParticle.Scale);
				VC = MyMath.RotateAboutOrigin(VC, Angle, false);
				FinVertArray[Position*18 + 6] = VC.x + MyParticle.Position.x + MyParticle.StartPosition.x;
				FinVertArray[Position*18 + 7] = VC.y + MyParticle.Position.y + MyParticle.StartPosition.y;
				FinVertArray[Position*18 + 8] = VC.z + MyParticle.Position.z + MyParticle.StartPosition.z;
				
				//normal
				FinNormArray[Position*18] = 0;
				FinNormArray[Position*18 + 1] = 0;
				FinNormArray[Position*18 + 2] = 1;
				FinNormArray[Position*18 + 3] = 0;
				FinNormArray[Position*18 + 4] = 0;
				FinNormArray[Position*18 + 5] = 1;
				FinNormArray[Position*18 + 6] = 0;
				FinNormArray[Position*18 + 7] = 0;
				FinNormArray[Position*18 + 8] = 1;
				
				//colour
				FinColourArray[Position*24] = MyParticle.Colour.x;
				FinColourArray[Position*24 + 1] = MyParticle.Colour.y;
				FinColourArray[Position*24 + 2] = MyParticle.Colour.z;
				FinColourArray[Position*24 + 3] = MyParticle.alpha;
				FinColourArray[Position*24 + 4] = MyParticle.Colour.x;
				FinColourArray[Position*24 + 5] = MyParticle.Colour.y;
				FinColourArray[Position*24 + 6] = MyParticle.Colour.z;
				FinColourArray[Position*24 + 7] = MyParticle.alpha;
				FinColourArray[Position*24 + 8] = MyParticle.Colour.x;
				FinColourArray[Position*24 + 9] = MyParticle.Colour.y;
				FinColourArray[Position*24 + 10] = MyParticle.Colour.z;
				FinColourArray[Position*24 + 11] = MyParticle.alpha;
				
			//triangle two
				//v1
				VA = new Vector3f(-1 * MyParticle.Scale, -1 * MyParticle.Scale, 0 * MyParticle.Scale);
				VA = MyMath.RotateAboutOrigin(VA, Angle, false);
				FinVertArray[Position*18 + 9] = VA.x + MyParticle.Position.x + MyParticle.StartPosition.x;
				FinVertArray[Position*18 + 10] = VA.y + MyParticle.Position.y + MyParticle.StartPosition.y;
				FinVertArray[Position*18 + 11] = VA.z + MyParticle.Position.z + MyParticle.StartPosition.z;
				//v2
				VB = new Vector3f(-1 * MyParticle.Scale, 1 * MyParticle.Scale, 0 * MyParticle.Scale);
				VB = MyMath.RotateAboutOrigin(VB, Angle, false);
				FinVertArray[Position*18 + 12] = VB.x + MyParticle.Position.x + MyParticle.StartPosition.x;
				FinVertArray[Position*18 + 13] = VB.y + MyParticle.Position.y + MyParticle.StartPosition.y;
				FinVertArray[Position*18 + 14] = VB.z + MyParticle.Position.z + MyParticle.StartPosition.z;
				//v2
				VC = new Vector3f(1 * MyParticle.Scale, 1 * MyParticle.Scale, 0 * MyParticle.Scale);
				VC = MyMath.RotateAboutOrigin(VC, Angle, false);
				FinVertArray[Position*18 + 15] = VC.x + MyParticle.Position.x + MyParticle.StartPosition.x;
				FinVertArray[Position*18 + 16] = VC.y + MyParticle.Position.y + MyParticle.StartPosition.y;
				FinVertArray[Position*18 + 17] = VC.z + MyParticle.Position.z + MyParticle.StartPosition.z;
				
				//normal
				FinNormArray[Position*18 + 9] = 0;
				FinNormArray[Position*18 + 10] = 0;
				FinNormArray[Position*18 + 11] = 1;
				FinNormArray[Position*18 + 12] = 0;
				FinNormArray[Position*18 + 13] = 0;
				FinNormArray[Position*18 + 14] = 1;
				FinNormArray[Position*18 + 15] = 0;
				FinNormArray[Position*18 + 16] = 0;
				FinNormArray[Position*18 + 17] = 1;
				
				//colour
				FinColourArray[Position*24 + 12] = MyParticle.Colour.x;
				FinColourArray[Position*24 + 13] = MyParticle.Colour.y;
				FinColourArray[Position*24 + 14] = MyParticle.Colour.z;
				FinColourArray[Position*24 + 15] = MyParticle.alpha;
				FinColourArray[Position*24 + 16] = MyParticle.Colour.x;
				FinColourArray[Position*24 + 17] = MyParticle.Colour.y;
				FinColourArray[Position*24 + 18] = MyParticle.Colour.z;
				FinColourArray[Position*24 + 19] = MyParticle.alpha;
				FinColourArray[Position*24 + 20] = MyParticle.Colour.x;
				FinColourArray[Position*24 + 21] = MyParticle.Colour.y;
				FinColourArray[Position*24 + 22] = MyParticle.Colour.z;
				FinColourArray[Position*24 + 23] = MyParticle.alpha;
				
			Position++;
		}
		
		//End = (Sys.getTime() * 1000) / Sys.getTimerResolution();
        //System.out.println("Array Calculation Time: " + (float)(End - Start));
		/*
		VertBuffer.put(FinVertArray);
        NormBuffer.put(FinNormArray);
        TextBuffer.put(FinTextArray);
        ColourBuffer.put(FinColourArray);
        
        FinVertArray = null;
        FinNormArray = null;
        FinTextArray = null;
        FinColourArray = null;
        
        VertBuffer.rewind();
        NormBuffer.rewind();
        TextBuffer.rewind();
        ColourBuffer.rewind();
		
		vbo_vertex_handle = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo_vertex_handle);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, VertBuffer, GL15.GL_DYNAMIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        
        vbo_normal_handle = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo_normal_handle);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, NormBuffer, GL15.GL_DYNAMIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        
        vbo_texture_handle = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo_texture_handle);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, TextBuffer, GL15.GL_DYNAMIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        
        vbo_Colour_handle = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo_Colour_handle);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, ColourBuffer, GL15.GL_DYNAMIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        */
	}
	
	private int RanInt(int Min, int Max){
    	return Temp.nextInt(Max - Min) + Min;
    
    }
	
}
