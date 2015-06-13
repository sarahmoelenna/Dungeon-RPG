package MyDungeonPackage;
import java.io.*;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Scanner;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Model {
	
	int Temp;
	int TempB;
	int TempC;
	
	float VertArray[][] = new float [2000000][3];
	float TextArray[][] = new float [2000000][2];
	float NormArray[][] = new float [2000000][3];
	int FaceArray[][] = new int [200000][10];
	
	//float FinVertArray[][];
	//float FinTextArray[][];
	//float FinNormArray[][];
	//int FinfaceArray[][];
	float FinVertArray[];
	float FinTextArray[];
	float FinNormArray[];
	int FinfaceArray[];
	/////////////////////////////////VBO STUFF
	FloatBuffer VertBuffer;
	FloatBuffer NormBuffer;
	FloatBuffer TextBuffer;
	IntBuffer FaceBuffer;
	
	int vbo_vertex_handle;
	int vbo_normal_handle;
	int vbo_texture_handle;
	
	Vector3f VA = new Vector3f();
	Vector3f VB= new Vector3f();
	Vector3f VC= new Vector3f();
	Vector3f VD= new Vector3f();
	
	float Transparency = 0;
	
	boolean Collis;
	boolean ShowModel = true;
	
	int Faces = 0;
	
	String ModelName;
	
	int Reference = 0;
	
	public Model(){
	
	}
			
    public Model(String ObjName, boolean Collision){
    	
    	ModelName = ObjName;
    	
    	Collis = Collision;
    	Scanner s = null;
    	int Vertice = 0;
    	int Normals = 0;
    	int Texts = 0;
    	String teststring;
    	String FileOpen;

    	FileOpen = "/Res/Models/" + ObjName + ".obj";
    	
        try {
        	InputStream i = Model.class.getResourceAsStream(FileOpen);
			s = new Scanner(new BufferedReader(new InputStreamReader(i)));
            //s = new Scanner(new BufferedReader(new FileReader(FileOpen)));
            s.useDelimiter("[\\/\\s+]");
            while (s.hasNext()) {  	
            	teststring = s.next();
            	if (teststring.equals("v")){
            		Vertice++;
            		VertArray[Vertice - 1][0] = s.nextFloat();
            		VertArray[Vertice - 1][1] = s.nextFloat();
            		VertArray[Vertice - 1][2] = s.nextFloat();
            	}
            	if (teststring.equals("vt")){
            		Texts++;
            		if (Texts == 1){
            		}
            		TextArray[Texts - 1][0] = s.nextFloat();
            		TextArray[Texts - 1][1] = s.nextFloat();
            	}
            	if (teststring.equals("vn")){
            		Normals++;
            		if (Normals == 1){
            		}
            		NormArray[Normals - 1][0] = s.nextFloat();
            		NormArray[Normals - 1][1] = s.nextFloat();
            		NormArray[Normals - 1][2] = s.nextFloat();
            	}
            	if (teststring.equals("f")){
            		Faces++;
            		FaceArray[Faces - 1][0] = s.nextInt();
            		FaceArray[Faces - 1][1] = s.nextInt();
            		FaceArray[Faces - 1][2] = s.nextInt();
            		FaceArray[Faces - 1][3] = s.nextInt();
            		FaceArray[Faces - 1][4] = s.nextInt();
            		FaceArray[Faces - 1][5] = s.nextInt();
            		FaceArray[Faces - 1][6] = s.nextInt();
            		FaceArray[Faces - 1][7] = s.nextInt();
            		FaceArray[Faces - 1][8] = s.nextInt();
            	}
            	
            }
        } finally {
        	s.close();
        	
        	
        	FinVertArray = new float[Faces * 9];
        	FinNormArray = new float[Faces * 9];
        	FinTextArray = new float[Faces * 6];
        	//System.out.println(Faces);
        	for(int i = 0; i < Faces; i ++){
        		
        		//System.out.println();
        		
        		FinVertArray[i*9] = VertArray[FaceArray[i][0] - 1][0];
        		FinVertArray[i*9+1] = VertArray[FaceArray[i][0] - 1][1];
        		FinVertArray[i*9+2] = VertArray[FaceArray[i][0] - 1][2];
        		
        		FinVertArray[i*9+3] = VertArray[FaceArray[i][3] - 1][0];
        		FinVertArray[i*9+4] = VertArray[FaceArray[i][3] - 1][1];
        		FinVertArray[i*9+5] = VertArray[FaceArray[i][3] - 1][2];
        		
        		FinVertArray[i*9+6] = VertArray[FaceArray[i][6] - 1][0];
        		FinVertArray[i*9+7] = VertArray[FaceArray[i][6] - 1][1];
        		FinVertArray[i*9+8] = VertArray[FaceArray[i][6] - 1][2];
        		
        		FinNormArray[i*9] = NormArray[FaceArray[i][1] - 1][0];
        		FinNormArray[i*9+1] = NormArray[FaceArray[i][1] - 1][1];
        		FinNormArray[i*9+2] = NormArray[FaceArray[i][1] - 1][2];
        		
        		FinNormArray[i*9+3] = NormArray[FaceArray[i][4] - 1][0];
        		FinNormArray[i*9+4] = NormArray[FaceArray[i][4] - 1][1];
        		FinNormArray[i*9+5] = NormArray[FaceArray[i][4] - 1][2];
        		
        		FinNormArray[i*9+6] = NormArray[FaceArray[i][7] - 1][0];
        		FinNormArray[i*9+7] = NormArray[FaceArray[i][7] - 1][1];
        		FinNormArray[i*9+8] = NormArray[FaceArray[i][7] - 1][2];
        		
        		FinTextArray[i*6] = TextArray[FaceArray[i][2] - 1][0];
        		FinTextArray[i*6+1] = TextArray[FaceArray[i][2] - 1][1] * -1;
        		
        		FinTextArray[i*6+2] = TextArray[FaceArray[i][5] - 1][0];
        		FinTextArray[i*6+3] = TextArray[FaceArray[i][5] - 1][1] * -1;
        		
        		FinTextArray[i*6+4] = TextArray[FaceArray[i][8] - 1][0];
        		FinTextArray[i*6+5] = TextArray[FaceArray[i][8] - 1][1] * -1;

        	}
        	
        	VertArray = null;
        	NormArray = null;
        	TextArray = null;
        	FaceArray = null;
        	
        	VertBuffer = BufferUtils.createFloatBuffer(Faces * 9);
	        NormBuffer = BufferUtils.createFloatBuffer(Faces * 9);
	        TextBuffer = BufferUtils.createFloatBuffer(Faces * 6);
	        
	        VertBuffer.put(FinVertArray);
	        NormBuffer.put(FinNormArray);
	        TextBuffer.put(FinTextArray);
	        
	        VertBuffer.rewind();
	        NormBuffer.rewind();
	        TextBuffer.rewind();
	        
	        vbo_vertex_handle = GL15.glGenBuffers();
	        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo_vertex_handle);
	        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, VertBuffer, GL15.GL_STATIC_DRAW);
	        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	        
	        vbo_normal_handle = GL15.glGenBuffers();
	        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo_normal_handle);
	        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, NormBuffer, GL15.GL_STATIC_DRAW);
	        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	        
	        vbo_texture_handle = GL15.glGenBuffers();
	        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo_texture_handle);
	        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, TextBuffer, GL15.GL_STATIC_DRAW);
	        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        	
        }
    }
    
    public void Draw(){
    	
    	GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
  		GL11.glEnableClientState(GL11.GL_NORMAL_ARRAY);
  		GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
  		  
  		GL15.glBindBuffer( GL15.GL_ARRAY_BUFFER, vbo_vertex_handle);
  		GL11.glVertexPointer(3, GL11.GL_FLOAT, 0, 0);

  		GL15.glBindBuffer( GL15.GL_ARRAY_BUFFER, vbo_normal_handle);
  		GL11.glNormalPointer(GL11.GL_FLOAT, 0, 0);
  		  
  		GL15.glBindBuffer( GL15.GL_ARRAY_BUFFER, vbo_texture_handle);
  		GL11.glTexCoordPointer(2, GL11.GL_FLOAT, 0, 0);

  		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, Faces*3);

    	}
    	
    public String GetModelName(){
    	return ModelName;
    }
     
 }


