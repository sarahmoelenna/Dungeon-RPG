package MyDungeonPackage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class LoadDungeon {

	
	ArrayList<Enemy> EnemyList = new ArrayList<>();
	ArrayList<Chest> ChestList = new ArrayList<>();
	ArrayList<Door> DoorList = new ArrayList<>();
	ArrayList<DungeonObject> DungeonObjectList = new ArrayList<>();
	ArrayList<LogicObject> LogicObjectList = new ArrayList<>();
	ArrayList<DialogueData> DialogueList = new ArrayList<>();
	int PlayerX;
	int PlayerZ;
	int ExitPosX;
	int ExitPosZ;
	int GridTypeArray[];
	int GridEntityTypeArray[];
	int GridRotationTypeArray[];
	int GridEntityDataArray[];
	int GridLogicTypeArray[];
	int GridDataOneArray[];
	int GridDataTwoArray[];
	int GridDataThreeArray[];
	int GridFlatWallArray[];
	//int floorNumber;
	int arrayPosition;
	
	boolean HasNextFloor;
	boolean Lights;
	String NextLevelName;
	int FloorNumber;
	int Time;
	
	public LoadDungeon(String DungeonName){
		PlayerX = 0;
		PlayerZ = 0;
		ExitPosX = 0;
		ExitPosZ = 0;
		//floorNumber = 1;
		GridTypeArray = new int[1024];
		GridEntityTypeArray = new int[1024];
		GridRotationTypeArray = new int[1024];
		GridEntityDataArray = new int[1024];
		GridLogicTypeArray = new int[1024];
		GridDataOneArray = new int[1024];
		GridDataTwoArray = new int[1024];
		GridDataThreeArray = new int[1024];
		GridFlatWallArray = new int[1024];
		
		String username = System.getProperty("user.name");
		
		Scanner s = null;
		String FileOpen = "C:\\\\Users\\\\" + username + "\\\\AppData\\\\Roaming\\\\EndlessSanctuary\\\\" + DungeonName;
		
		arrayPosition = 0;
		
		 try {
			 s = new Scanner(new BufferedReader(new FileReader(FileOpen)));
				
				int Type = 0;
				int Entity = 0;
				int Rotation = 0;
				int EntData = 0;
				int Logic = 0;
				int D1 = 0;
				int D2 = 0;
				int D3 = 0;
				int Wall = 0;
				
				while (s.hasNext()) { 
					
					String Temp = s.next();
					
					if(Temp.equals("Dialogue")){
						int R = s.nextInt();
						String L1 = s.next();
						String L2 = s.next();
						String L3 = s.next();
						DialogueData TempDialogue = new DialogueData(R, L1, L2, L3);
						DialogueList.add(TempDialogue);
					}
					
					if(Temp.equals("Grid")){
						Type = s.nextInt();
						GridTypeArray[arrayPosition] = Type; //tile type
						
						Entity = s.nextInt();
						Rotation = s.nextInt();
						EntData = s.nextInt();
						Logic = s.nextInt();
						D1 = s.nextInt();
						D2 = s.nextInt();
						D3 = s.nextInt();
						
						Wall = s.nextInt();
						GridFlatWallArray[arrayPosition] = Wall;
						
						CreateEntity(Entity, Rotation, Type, EntData);
						CreateLogic(Logic, D1, D2, D3);
						arrayPosition++;
					}
					
					if(Temp.equals("Lights")){
						Lights = s.nextBoolean();
					}
					if(Temp.equals("HasNextFloor")){
						HasNextFloor = s.nextBoolean();
					}
					if(Temp.equals("FloorNumber")){
						FloorNumber = s.nextInt();
					}
					if(Temp.equals("Time")){
						Time = s.nextInt();
					}
					if(Temp.equals("NextLevelName")){
						NextLevelName = s.next();
					}
					//System.out.println(Type + " " + Entity + " " + Rotation + " ");
				}
				System.out.println("Level Loaded. Name: " + DungeonName);
				
		 } catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally {
			 s.close();
		 }
		
	}

	public LoadDungeon(String DungeonName, boolean editor){
		PlayerX = 0;
		PlayerZ = 0;
		ExitPosX = 0;
		ExitPosZ = 0;
		//floorNumber = 1;
		GridTypeArray = new int[1024];
		GridEntityTypeArray = new int[1024];
		GridRotationTypeArray = new int[1024];
		GridEntityDataArray = new int[1024];
		GridLogicTypeArray = new int[1024];
		GridDataOneArray = new int[1024];
		GridDataTwoArray = new int[1024];
		GridDataThreeArray = new int[1024];
		GridFlatWallArray = new int[1024];
		
		String username = System.getProperty("user.name");
		
		Scanner s = null;
		String FileOpen = "C:\\\\Users\\\\" + username + "\\\\AppData\\\\Roaming\\\\EndlessSanctuary\\\\" + DungeonName;
		
		arrayPosition = 0;
		
		 try {
			 s = new Scanner(new BufferedReader(new FileReader(FileOpen)));
				
				int Type = 0;
				int Entity = 0;
				int Rotation = 0;
				int EntData = 0;
				int Logic = 0;
				int D1 = 0;
				int D2 = 0;
				int D3 = 0;
				int Wall = 0;
				
				while (s.hasNext()) { 
					String Temp = s.next();
					
					if(Temp.equals("Dialogue")){
						int R = s.nextInt();
						String L1 = s.next();
						String L2 = s.next();
						String L3 = s.next();
						DialogueData TempDialogue = new DialogueData(R, L1, L2, L3);
						DialogueList.add(TempDialogue);
					}
					
					if(Temp.equals("Grid")){
						Type = s.nextInt();
						Entity = s.nextInt();
						Rotation = s.nextInt();
						EntData = s.nextInt();
						Logic = s.nextInt();
						D1 = s.nextInt();
						D2 = s.nextInt();
						D3 = s.nextInt();
						Wall = s.nextInt();
						
						
						GridTypeArray[arrayPosition] = Type; //tile type
						GridEntityTypeArray[arrayPosition] = Entity;
						GridRotationTypeArray[arrayPosition] = Rotation;
						GridEntityDataArray[arrayPosition] = EntData;
						GridLogicTypeArray[arrayPosition] = Logic;
						GridDataOneArray[arrayPosition] = D1;
						GridDataTwoArray[arrayPosition] = D2;
						GridDataThreeArray[arrayPosition] = D3;
						GridFlatWallArray[arrayPosition] = Wall;
						
						
						arrayPosition++;
					}
					
					if(Temp.equals("Lights")){
						Lights = s.nextBoolean();
					}
					if(Temp.equals("HasNextFloor")){
						HasNextFloor = s.nextBoolean();
					}
					if(Temp.equals("FloorNumber")){
						FloorNumber = s.nextInt();
					}
					if(Temp.equals("Time")){
						Time = s.nextInt();
					}
					if(Temp.equals("NextLevelName")){
						NextLevelName = s.next();
					}
					
					//System.out.println(Type + " " + Entity + " " + Rotation + " ");
				}
				//System.out.println("Level Loaded. Name: " + DungeonName);
				
		 } catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally {
			 s.close();
		 }
		
	}
	
	private void CreateEntity(int type, int rotation, int floor, int EntityData){
		
		int Y = arrayPosition / 32;
        int X = arrayPosition % 32;
		
		if(type == 1){
			PlayerX = X;
			PlayerZ = Y;
		}
		else if(type == 2){
			ExitPosX = X;
			ExitPosZ = Y;
		}
		else if(type == 3){
			if(EntityData > 0){
				Enemy TempEnemy = new Enemy("Bug", "bug", X, 0, Y, GameData.GameScale, FloorNumber, EntityData);
				EnemyList.add(TempEnemy);
			}
			else{
				Enemy TempEnemy = new Enemy("Bug", "bug", X, 0, Y, GameData.GameScale, FloorNumber);
				EnemyList.add(TempEnemy);
			}
		}
		else if(type == 4){
			if(rotation == 2){rotation = 4;}
			else if(rotation == 4){rotation = 2;}
			Chest TempChest = new Chest("ChestBase", "chest", X, 0, Y, GameData.GameScale, rotation, FloorNumber);
			ChestList.add(TempChest);
		}
		else if(type == 5){
			if(rotation == 3){rotation = 1;}
			if(rotation == 4){rotation = 2;}
			Door TempDoor = new Door("door", "Temple", X, 0, Y, GameData.GameScale, rotation);
			DoorList.add(TempDoor);
		}
		else if(type == 6){
			DungeonObject TempObject = new CrystalCluster("CrystalCLuster", "black", X, 0, Y, DisplayObjectType.GlowObject, GameData.GameScale, 0.001f);
			DungeonObjectList.add(TempObject);
		}
		else if(type == 7){
			DungeonObject TempObject = new PushObject( X, 0, Y, DisplayObjectType.TexturedObject, GameData.GameScale);
			DungeonObjectList.add(TempObject);
		}
		else if(type == 8){
			if(rotation == 3){rotation = 1;}
			if(rotation == 4){rotation = 2;}
			Door TempDoor = new Door("door", "Temple", X, 0, Y, GameData.GameScale, rotation, true);
			DoorList.add(TempDoor);
		}
		else if(type == 9){
			DungeonObject TempObject = new PressurePad( X, 0, Y, GameData.GameScale);
			DungeonObjectList.add(TempObject);
		}
		else if(type == 10){
			DungeonObject TempObject = new TreeObject( X, 0, Y, GameData.GameScale);
			DungeonObjectList.add(TempObject);
		}
		else if(type == 11){
			DungeonObject TempObject = new Button( X, 0, Y,rotation , GameData.GameScale);
			DungeonObjectList.add(TempObject);
		}
		else if(type == 12){
			DungeonObject TempObject = new ForceFieldObject( X, 0, Y , GameData.GameScale, EntityData);
			DungeonObjectList.add(TempObject);
		}
		else if(type == 13){
			if(EntityData == 0){
				DungeonObject TempObject = new FakeWallObject( X, 0, Y , GameData.GameScale, true, rotation);
				DungeonObjectList.add(TempObject);
			}
			else{
				DungeonObject TempObject = new FakeWallObject( X, 0, Y , GameData.GameScale, false, rotation);
				DungeonObjectList.add(TempObject);
			}
			
		}
		else if(type == 14){
			DungeonObject TempObject = new TeleporterField( X, 0, Y , GameData.GameScale, EntityData);
			DungeonObjectList.add(TempObject);
		}
		else if(type == 15){
			DungeonObject TempObject = new DialogueObject( X, 0, Y , GameData.GameScale, FindDialogue(EntityData));
			DungeonObjectList.add(TempObject);
		}
		else if(type == 16){
			DungeonObject TempObject = new LeverObject( X, 0, Y , GameData.GameScale, rotation, true, EntityData);
			DungeonObjectList.add(TempObject);
		}
		else if(type == 17){
			DungeonObject TempObject = new LeverObject( X, 0, Y , GameData.GameScale, rotation, false, EntityData);
			DungeonObjectList.add(TempObject);
		}
		else if(type == 18){
			DungeonObject TempObject = new SignObject( X, 0, Y , GameData.GameScale, rotation);
			DungeonObjectList.add(TempObject);
		}
		if(floor == 4){
			DungeonObject TempObject = new SpikeObject("Spikes", "Temple", X, 0, Y, DisplayObjectType.TexturedObject, GameData.GameScale, (float)(EntityData/100.0f));
			//System.out.println((float)(EntityData/100.0f));
			DungeonObjectList.add(TempObject);
		}
		else if(floor == 9){
			if(EntityData == 0){
				DungeonObject TempObject = new SpikeObject("Spikes", "Temple", X, 0, Y, DisplayObjectType.TexturedObject, GameData.GameScale, 0.5f, true);
				DungeonObjectList.add(TempObject);
			}
			else{
				DungeonObject TempObject = new SpikeObject("Spikes", "Temple", X, 0, Y, DisplayObjectType.TexturedObject, GameData.GameScale, 0.5f, false);
				DungeonObjectList.add(TempObject);
			}
			
		}
		
	}
	
	private void CreateLogic(int type, int d1, int d2, int d3){
		
		int Y = arrayPosition / 32;
        int X = arrayPosition % 32;
		
		if(type == 1){
			LogicObject TempLogic;
			TempLogic = new LogicActivator(X, Y, d1, d2);
			//System.out.println(d2+d3 * 32);
			//System.out.println(d2+ " " + d3);
			LogicObjectList.add(TempLogic);
		}
		else if(type == 2){
			LogicObject TempLogic;
			TempLogic = new LogicFlipFlop(X, Y, d1, d2);
			//System.out.println(d1+d2 * 32);
			LogicObjectList.add(TempLogic);
		}
		else if(type == 3){
			LogicObject TempLogic;
			TempLogic = new LogicTripleOutput(X, Y, d1, d2, d3);
			LogicObjectList.add(TempLogic);
		}
		else if(type == 4){
			LogicObject TempLogic;
			TempLogic = new LogicMultiInput(X, Y, d1, d2);
			LogicObjectList.add(TempLogic);
		}
		else if(type == 5){
			LogicObject TempLogic;
			TempLogic = new LogicPushOnce(X, Y, d1, d2);
			LogicObjectList.add(TempLogic);
		}
		else if(type == 6){
			LogicObject TempLogic;
			TempLogic = new LogicTeleporter(X, Y, d1, d2, d3);
			LogicObjectList.add(TempLogic);
		}
		else if(type == 7){
			LogicObject TempLogic;
			TempLogic = new LogicPoweredPulse(X, Y, d1, d2);
			LogicObjectList.add(TempLogic);
		}
		else if(type == 8){
			LogicObject TempLogic;
			TempLogic = new LogicDelayedPulse(X, Y, d1, d2);
			LogicObjectList.add(TempLogic);
		}
		else if(type == 9){
			LogicObject TempLogic;
			TempLogic = new LogicDelayedPulseOnce(X, Y, d1, d2);
			LogicObjectList.add(TempLogic);
		}
	}
	
	private DialogueData FindDialogue(int ref){
		int r = -1;
		
		for(int i = 0; i < DialogueList.size(); i++){
			if(DialogueList.get(i).reference == ref){
				r = i;
			}
		}
		
		return DialogueList.get(r);
	}
}
