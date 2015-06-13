package MyDungeonPackage;
import java.util.ArrayList;


class ModelHandler {

	static ArrayList<Model> ModelList = new ArrayList<>();
	static ArrayList<ModelData> ModelDataList = new ArrayList<>();
	static ArrayList<MyTextureClass> TextureList = new ArrayList<>();
	static ArrayList<PointLight> LightList = new ArrayList<>();
	static boolean exist = false;
	static boolean found = false;
	
	public static void AddModel(String ModelName){
		
		for(Model MyModel: ModelList){
			if(MyModel.GetModelName().equals(ModelName)){
				exist = true;
			}
		}
		
		if(exist == false){
			Model TempModel = new Model(ModelName, false);
			ModelList.add(TempModel);
			System.out.println("Model Added: " + ModelName);
		}
		
		exist = false;
	}
	
	public static void AddTexture(String TextureName){
		
		for(MyTextureClass MyTexture: TextureList){
			if(MyTexture.GetTextureName().equals(TextureName)){
				exist = true;
			}
		}
		
		if(exist == false){
			MyTextureClass TempTexture = new MyTextureClass(TextureName);
			TextureList.add(TempTexture);
			System.out.println("Texture Added: " + TextureName);
		}
		
		exist = false;
	}
	
	public static void AddLight(PointLight MyLight){
		
		LightList.add(MyLight);
	}
	
	public static PointLight FindLight(PointLight MyLight){
		
		int Reference = 0;
		
		for(int i = 0; i < LightList.size(); i++){
			if(LightList.get(i).equals(MyLight)){
				Reference = i;
			}
		}
		
		return(LightList.get(Reference));
		
	}
	
	public static void AddModelData(String ModelDataName){
		
		for(ModelData MyModel: ModelDataList){
			if(MyModel.GetModelName().equals(ModelDataName)){
				exist = true;
			}
		}
		
		if(exist == false){
			ModelData TempModel = new ModelData(ModelDataName, false);
			ModelDataList.add(TempModel);
			System.out.println("Model Data Added: " + ModelDataName);
		}
		
		exist = false;
	}
	
	public static ModelData FindModelData(String ModelDataName){
		
		//Model TempModel = new Model();
		int Reference = 0;
		
		for(int i = 0; i < ModelDataList.size(); i++){
			if(ModelDataList.get(i).GetModelName().equals(ModelDataName)){
				Reference = i;
			}
		}
		
		if (found = true){
			//System.out.println("Reference Passed: " + ModelName + "  " + TextureName);
		}
		
		if (found = false){
			System.out.println("Failed To Pass Reference: " + ModelDataName);
		}
		
		found = false;
		return ModelDataList.get(Reference);
	}

	public static void ClearLights(){
		LightList.clear();
	}
	
	public static Model FindModel(String ModelName){
		
		//Model TempModel = new Model();
		int Reference = 0;
		
		for(int i = 0; i < ModelList.size(); i++){
			if(ModelList.get(i).GetModelName().equals(ModelName)){
				Reference = i;
			}
		}
		
		if (found = true){
			//System.out.println("Reference Passed: " + ModelName + "  " + TextureName);
		}
		
		if (found = false){
			System.out.println("Failed To Pass Reference: " + ModelName);
		}
		
		found = false;
		return ModelList.get(Reference);
	}

	public static MyTextureClass FindTexture(String TextureName){
		
		//Model TempModel = new Model();
		int Reference = 0;
		
		for(int i = 0; i < TextureList.size(); i++){
			if(TextureList.get(i).GetTextureName().equals(TextureName)){
				Reference = i;
			}
		}
		
		if (found = true){
			//System.out.println("Reference Passed: " + ModelName + "  " + TextureName);
		}
		
		if (found = false){
			System.out.println("Failed To Pass Reference: " + TextureName);
		}
		
		found = false;
		return TextureList.get(Reference);
	}

	public static void ClearModelsandTextures(){
		ModelList.clear();
		TextureList.clear();
	}
}
