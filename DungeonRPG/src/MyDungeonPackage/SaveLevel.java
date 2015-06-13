package MyDungeonPackage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SaveLevel {
	
	BufferedWriter out;
	String FileSave;
	
	boolean HasNextFloor;
	boolean Lights;
	String LevelName;
	String NextLevelName;
	int FloorNumber;
	int Time;
	
	public SaveLevel(String SaveName){
		String username = System.getProperty("user.name");
		//System.out.println(username);
		FileSave = "C:\\\\Users\\\\" + username + "\\\\AppData\\\\Roaming\\\\EndlessSanctuary\\\\" + SaveName;
		
		HasNextFloor = false;
		Lights = false;
		LevelName = "Test";
		NextLevelName = "TestTwo";
		FloorNumber = 1;
		Time = 0;
	}
	
	public void SetLevelData(String Name, String Name2, boolean hasfloor, boolean light, int time, int floor){
		
		HasNextFloor = hasfloor;
		Lights = light;
		LevelName = Name;
		NextLevelName = Name2;
		FloorNumber = floor;
		Time = time;
		
		String username = System.getProperty("user.name");
		FileSave = "C:\\\\Users\\\\" + username + "\\\\AppData\\\\Roaming\\\\EndlessSanctuary\\\\" + LevelName;
		
	}
	
	public void Save(int TypeArray[], int EntityArray[], int RotationArray[], int EntDataOne[], int LogicTpe[], int DataOne[], int DataTwo[], int DataThree[], int FlatWall[],  ArrayList<DialogueData> DialogueList){
		
		try {
			out = new BufferedWriter(new FileWriter (FileSave) );
			
			out.write("Lights " + Lights);
			out.newLine();
			
			out.write("HasNextFloor " + HasNextFloor);
			out.newLine();
			
			out.write("NextLevelName " + NextLevelName);
			out.newLine();
			
			out.write("FloorNumber " + FloorNumber);
			out.newLine();
			
			out.write("Time " + Time);
			out.newLine();
			
			for(DialogueData MyData: DialogueList){
				out.write("Dialogue " + MyData.reference + " " + MyData.LineOne + " " + MyData.LineTwo + " " + MyData.LineThree);
				out.newLine();
			}
			
			for(int i = 0; i < 1024; i++){
				out.write("Grid " + TypeArray[i] + " " + EntityArray[i] + " " + RotationArray[i] + " " + EntDataOne[i] + " " + LogicTpe[i] + " " + DataOne[i] + " " + DataTwo[i] + " " + DataThree[i]  + " " + FlatWall[i]);
	        	out.newLine();
			}
			
			
			
			if (out != null) {
                out.close();
            }
			
			System.out.println("Save Created");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			
		}
		
		
	}

}
