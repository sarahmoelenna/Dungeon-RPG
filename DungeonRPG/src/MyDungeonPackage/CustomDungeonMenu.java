package MyDungeonPackage;

import java.io.File;

public class CustomDungeonMenu {
	
	String FileSave;
	File[] listOfFiles;
	
	float PauseTimer;
	int selection;
	boolean MainMenu;
	boolean AReleased;
	boolean Char;
	boolean Editor;
	
	public CustomDungeonMenu(){
		
		String username = System.getProperty("user.name");
		FileSave = "C:\\\\Users\\\\" + username + "\\\\AppData\\\\Roaming\\\\EndlessSanctuary\\\\";
		
		File directory = new File(FileSave);
		listOfFiles = directory.listFiles();
		PauseTimer = 0;
		selection = 0;
		MainMenu = false;
		AReleased = false;
		Char = false;
		Editor = false;
	}
	
	public void Update(int delta){
		PauseTimer += 0.005f * delta;
		if(PauseTimer > 1){PauseTimer = 1;}
		
		if(PauseTimer == 1 && Input.GetW() == true){
			selection --;
			PauseTimer = 0;
		}
		
		if(PauseTimer == 1 && Input.GetS() == true){
			selection ++;
			PauseTimer = 0;
		}
		
		if(selection < 0){selection = listOfFiles.length - 1;}
		if(selection > listOfFiles.length - 1){selection = 0;}
		
		if(Input.GetI() == true){
			MainMenu = true;
		}
		
		if(Input.GetF() == false && Input.GetEnter() == false){
			AReleased = true;
		}
		
		if((Input.GetF() || Input.GetEnter()) && PauseTimer == 1 && AReleased == true){
			//LoadDungeon TempLoad = new LoadDungeon(listOfFiles[selection].getName());
			GameData.LoadName = listOfFiles[selection].getName();
			GameData.CustomDungeon = true;
			PauseTimer = 0;
			AReleased = false;
			Char = true;
		}
		
		if(Input.GetM() && PauseTimer == 1){
			//LoadDungeon TempLoad = new LoadDungeon(listOfFiles[selection].getName());
			GameData.LoadName = listOfFiles[selection].getName();
			GameData.CustomEditor = true;
			PauseTimer = 0;
			Editor = true;
		}
	}
	
	public void drawCustomDungeonMenuUI(){
		
		//File[] listOfFiles = folder.listFiles();
		//System.out.println(listOfFiles.toString());
		ShaderHandler.TextShader.Activate();
		
		FontHandler.ElitePro.DisplayFont(700, 1000, 2.0f, "Dungeon List");
		
		int offset = 0;
		if(selection > 27){
			offset = selection - 27;
		}
		
		for(int i = offset; i < listOfFiles.length && i < 28 + offset; i++){
			String FileName = listOfFiles[i].getName();
			FileName = FileName.replaceAll(".Dungeon", "");
			if(i != selection){
				FontHandler.ElitePro.DisplayFont(700, 900 - i *32 + offset*32, 1.0f, FileName);
			}
			else{
				FontHandler.ElitePro.DisplayFont(700, 900 - i *32 + offset*32, 1.0f,"->"+FileName+"<-");
			}
		}
		
		ShaderHandler.TextShader.Activate();
	}
	
}
