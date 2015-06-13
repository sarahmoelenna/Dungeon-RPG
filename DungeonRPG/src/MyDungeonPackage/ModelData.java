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

public class ModelData {
	
	int Temp;
	int TempB;
	int TempC;
	
	float VertArray[][] = new float [2000000][3];
	float TextArray[][] = new float [2000000][2];
	float NormArray[][] = new float [2000000][3];
	int FaceArray[][] = new int [200000][10];
	
	float FinVertArray[];
	float FinTextArray[];
	float FinNormArray[];
	int FinfaceArray[];
	
	float Transparency = 0;
	
	boolean Collis;
	boolean ShowModel = true;
	
	int Faces = 0;
	
	String ModelName;

	
	public ModelData(){
	
	}
			
    public ModelData(String ObjName, boolean Collision){
    	
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
        	
        	
        }
    }
    
    public float[] GetTranslatedAndRotatedVertexArray(float x, float y, float z, float rot){
    	float TempArray[];
    	TempArray = new float[FinVertArray.length];
    		
    	for(int i = 0; i < FinVertArray.length/3; i++){
    		
    		Vector3f MyVertex = new Vector3f(FinVertArray[i*3], FinVertArray[i*3 + 1], FinVertArray[i*3 + 2]);
    		
    		if(rot != 0){
    			//System.out.println(MyVertex + " F");
    			MyVertex = MyMath.RotateAboutOrigin(MyVertex, rot, true);
    			//System.out.println(MyVertex);
    		}
    		
    		MyVertex.x += x;
    		MyVertex.y += y;
    		MyVertex.z += z;
    		
    		TempArray[i*3] = MyVertex.x;
    		TempArray[i*3 + 1] = MyVertex.y;
    		TempArray[i*3 + 2] = MyVertex.z;
    		
    	}
    	
    	return TempArray;
    }
    
    public float[] GetTextArray(){
    	return FinTextArray;
    }
    
    public float[] GetNormArray(){
    	return FinNormArray;
    }
    
    public float[] GetRotatedNormArray(float rotation){
    	float TempArray[];
    	TempArray = new float[FinNormArray.length];
    		
    	for(int i = 0; i < FinNormArray.length/3; i++){
    		
    		Vector3f MyVertex = new Vector3f(FinNormArray[i*3], FinNormArray[i*3 + 1], FinNormArray[i*3 + 2]);
    		
    		if(rotation != 0){
    			//System.out.println(MyVertex + " F");
    			MyVertex = MyMath.RotateAboutOrigin(MyVertex, rotation, false);
    			//System.out.println(MyVertex);
    		}
    		
    		TempArray[i*3] = MyVertex.x;
    		TempArray[i*3 + 1] = MyVertex.y;
    		TempArray[i*3 + 2] = MyVertex.z;
    		
    	}
    	
    	return TempArray;
    }
   
    public String GetModelName(){
    	return ModelName;
    }
     
 }


