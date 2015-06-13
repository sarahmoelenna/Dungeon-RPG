package MyDungeonPackage;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class WorldGrid {
	
	int Width;
	int Height;
	int GridTypeArray[];
	int GridPowerArray[];
	
	int GridSpecialWallArray[];
	
	int FinalGridPowerArray[];
	int PathChecked[];
	PathCalcData PathCalcArray[];
	int GridCollisionArray[];
	int GridEntityCollisionArray[];
	
	int GridDamageArray[];
	
	FloatBuffer VertBuffer;
	FloatBuffer NormBuffer;
	FloatBuffer ColourBuffer;
	
	int vbo_vertex_handle;
	int vbo_normal_handle;
	int vbo_colour_handle;
	Random Temp;
	int TempPosition;
	
	int PlayerX;
	int PlayerZ;
	float MovementTimer;
	int Direction;
	float DirectionTimer;
	int MovementDirection;
	
	float DamageTimer;
	boolean DamageUpdate;
	
	float AttackTimer;
	float PotionTimer;
	
	float DirectionOffset;
	float OffsetX;
	float OffsetZ;
	boolean DirectionMovement;
	boolean playerMoved;
	
	int FloorNumber;
	
	int ExitPosX;
	int ExitPosZ;
	
	float TileSize;
	
	TileSet MyTileSet;
	GridObject MyGridObject;
	GridWindObject MyWindObject;
	GridLightObject MyLightObject;
	
	DisplayObject ExitObject;
	DisplayObject ExitPortalObject;
	
	ArrayList<Enemy> EnemyList = new ArrayList<>();
	ArrayList<Chest> ChestList = new ArrayList<>();
	ArrayList<Door> DoorList = new ArrayList<>();
	ArrayList<DungeonObject> DungeonObjectList = new ArrayList<>();
	ArrayList<LogicObject> LogicObjectList = new ArrayList<>();
	ArrayList<GridMessageData> MessageList = new ArrayList<>();
	ArrayList<GridMessageData> MessageRemoveList = new ArrayList<>();
	Inventory PlayerInv;
	
	float PlayerHealth;
	float PlayerMaxHealth;
	int PlayerStrength;
	int PlayerDefense;
	int PlayerEvasiveness;
	int PlayerAccuracy;
	int PlayerFortitude;
	int Potions;
	
	boolean NeedReload;
	boolean Invuln;
	
	GridMap MyMap;
	
	float StatusTimer;
	
	public WorldGrid(int width, int height,int FNum){
		ParticleHandler.DeleteEmitters();
		
		NeedReload = false;
		
		Width = width;
		Height = height;
		GridTypeArray = new int[width*height];
		PathChecked = new int[width*height];
		PathCalcArray = new PathCalcData[width*height];
		GridCollisionArray = new int[width*height];
		GridEntityCollisionArray = new int[width*height];
		GridDamageArray = new int[width*height];
		GridPowerArray = new int[width*height];
		FinalGridPowerArray = new int[width*height];
		GridSpecialWallArray = new int[width*height];
		Temp = new Random();
		
		ExitObject = new DisplayObject("Gateway", "gateway", DisplayObjectType.TexturedObject);
		ExitObject.SetScale(GameData.GameScale);
		
		ExitPortalObject = new DisplayObject("portal", "portal", DisplayObjectType.GradientDisplacementObject);
		ExitPortalObject.SetScale(GameData.GameScale);
		
		FloorNumber = FNum;
		
		PlayerX = 0;
		PlayerZ = 0;
		MovementTimer = 1;
		Direction = 3;
		DirectionTimer = 1;
		MovementDirection = 1;
		OffsetX = 0;
		OffsetZ = 0;
		DirectionMovement = false;
		DirectionOffset = 0;
		AttackTimer = 1.5f;
		PotionTimer = 1;
		
		PlayerStrength = 0;
		PlayerDefense = 0;
		PlayerEvasiveness = 0;
		PlayerAccuracy = 0;
		PlayerFortitude = 0;
		
		//MyTileSet = new TileSet(GameData.GameScale);
		
		playerMoved = false;
		
		DamageTimer = 1;
		DamageUpdate = false;
		
		Invuln = false;
		
		GameData.Midnight = false;
		GameData.Lights = true;
		
		StatusTimer = 1;
		
	}
	
	public void SetTileset( TileSet myTileSet){
		MyTileSet = myTileSet;
	}
	
	public void UpdateGrid(int delta){
		
		CheckConsole();
		DebugLogic();
		
		PlayerMaxHealth = PlayerInv.Fortitude * 10 + PlayerInv.baseHealth;
		
		ExitPortalObject.UpdateDiplayObject(delta);
		
		MyWindObject.Update(delta);
		MovementTimer += 0.005f * delta;
		DirectionTimer += 0.005f * delta;
		AttackTimer  += 0.005f * delta;
		PotionTimer += 0.005f * delta;
		DamageTimer+= 0.005f * delta;
		StatusTimer+= 0.001f *delta; 
		
		
		PlayerStrength = PlayerInv.Strength;
		PlayerDefense = PlayerInv.Defense;
		PlayerEvasiveness = PlayerInv.Evasiveness;
		PlayerAccuracy = PlayerInv.Accuracy;
		PlayerFortitude = PlayerInv.Fortitude;
		
		
		GameData.AttackData = AttackTimer;
		
		if(StatusTimer > 1){StatusTimer = 1;}
		if(MovementTimer > 1){MovementTimer = 1;}
		if(DirectionTimer > 1){DirectionTimer = 1;}
		if(DamageTimer > 3){
			DamageTimer = 0;
			DamageUpdate = true;
			BuildDamageArray();
			DamageCheck();
		}
		else{
			DamageUpdate = false;
		}
		
		
		if(Input.GetQ() == true && DirectionTimer >= 1 && GameData.ConsoleOpen == false && PlayerInv.invopen == false){
			Direction--;
			DirectionTimer = 0;
			DirectionMovement = false;
		}
		if(Input.GetE() == true && DirectionTimer >= 1 && GameData.ConsoleOpen == false && PlayerInv.invopen == false){
			Direction++;
			DirectionTimer = 0;
			DirectionMovement = true;
		}
		if(Direction < 1){ Direction = 4;}
		if(Direction > 4){ Direction = 1;}
		
		if(DirectionMovement == true){
			DirectionOffset = -(1-DirectionTimer)*90;
		}
		if(DirectionMovement == false){
			DirectionOffset = (1-DirectionTimer)*90;
		}
		
		if(Direction == 1){GameData.CameraRotation.y = 0 + DirectionOffset;}
		if(Direction == 2){GameData.CameraRotation.y = 90 + DirectionOffset;}
		if(Direction == 3){GameData.CameraRotation.y = 180 + DirectionOffset;}
		if(Direction == 4){GameData.CameraRotation.y = 270 + DirectionOffset;}
		
		if(MovementTimer >= 1){
			if(Direction == 1){
				if(Input.GetW() == true && GameData.ConsoleOpen == false && PlayerInv.invopen == false){
					MoveNegativeZ();
				}
				if(Input.GetS() == true && GameData.ConsoleOpen == false && PlayerInv.invopen == false){
					MovePositiveZ();
				}
				if(Input.GetA() == true && GameData.ConsoleOpen == false && PlayerInv.invopen == false){
					MoveNegativeX();
				}
				if(Input.GetD() == true && GameData.ConsoleOpen == false && PlayerInv.invopen == false){
					MovePositiveX();
				}
			}
			
			if(Direction == 2){
				if(Input.GetA() == true && GameData.ConsoleOpen == false && PlayerInv.invopen == false){
					MoveNegativeZ();
				}
				if(Input.GetD() == true && GameData.ConsoleOpen == false && PlayerInv.invopen == false){
					MovePositiveZ();
				}
				if(Input.GetS() == true && GameData.ConsoleOpen == false && PlayerInv.invopen == false){
					MoveNegativeX();
				}
				if(Input.GetW() == true && GameData.ConsoleOpen == false && PlayerInv.invopen == false){
					MovePositiveX();
				}
			}
			
			if(Direction == 3){
				if(Input.GetS() == true && GameData.ConsoleOpen == false && PlayerInv.invopen == false){
					MoveNegativeZ();
				}
				if(Input.GetW() == true && GameData.ConsoleOpen == false && PlayerInv.invopen == false){
					MovePositiveZ();
				}
				if(Input.GetD() == true && GameData.ConsoleOpen == false && PlayerInv.invopen == false){
					MoveNegativeX();
				}
				if(Input.GetA() == true && GameData.ConsoleOpen == false && PlayerInv.invopen == false){
					MovePositiveX();
				}
			}
			
			if(Direction == 4){
				if(Input.GetD() == true && GameData.ConsoleOpen == false && PlayerInv.invopen == false){
					MoveNegativeZ();
				}
				if(Input.GetA() == true && GameData.ConsoleOpen == false && PlayerInv.invopen == false){
					MovePositiveZ();
				}
				if(Input.GetW() == true && GameData.ConsoleOpen == false && PlayerInv.invopen == false){
					MoveNegativeX();
				}
				if(Input.GetS() == true && GameData.ConsoleOpen == false && PlayerInv.invopen == false){
					MovePositiveX();
				}
			}
		}
		
		if(Input.GetSpace() == true && AttackTimer >= 2 && GameData.ConsoleOpen == false && PlayerInv.invopen == false){
			PlayerAttack();
			AttackTimer = 0;
		}
		
		if(Input.GetR() == true && GameData.ConsoleOpen == false && PlayerInv.invopen == false){
			UsePotion();
		}
		
		if(Input.GetF() == true && GameData.ConsoleOpen == false && PlayerInv.invopen == false){
			PlayerInteract();
		}
		
		if((Input.GetP() == true  && GameData.ConsoleOpen == false  && PlayerInv.invopen == false)|| (PlayerX == ExitPosX  && PlayerZ == ExitPosZ && MovementTimer > 0.8)){NeedReload = true;}
		
		if(MovementDirection == 1){ //-z
			OffsetX = 0;
			OffsetZ = (1-MovementTimer) * TileSize;
		}
		if(MovementDirection == 2){ //+z
			OffsetX = 0;
			OffsetZ = -(1-MovementTimer) * TileSize;
		}
		if(MovementDirection == 3){ //-X
			OffsetX = (1-MovementTimer) * TileSize;
			OffsetZ = 0;
		}
		if(MovementDirection == 4){ //+X
			OffsetX = -(1-MovementTimer) * TileSize;
			OffsetZ = 0;
		}
		
		//update camera position
		GameData.CameraPosition.x = -(PlayerX * TileSize) - TileSize/2 - OffsetX;
		GameData.CameraPosition.z = -(PlayerZ * TileSize) - TileSize/2 - OffsetZ;
		GameData.CameraPosition.y = -(0.5f * GameData.GameScale);
		
		for(LogicObject MyLogic: LogicObjectList){
			if(MyLogic instanceof LogicActivator){
				if(MyLogic.GetData() == 1 || MyLogic.GetData() == 4){
					if(PlayerX == MyLogic.PositionX && PlayerZ == MyLogic.PositionZ){
						MyLogic.Powered = true;
						PowerGrid(MyLogic);
					}
				}
			}
		}
		
		if(StatusTimer == 1){
			PlayerStatus();
		}
		
		UpdateEnemy(delta);
		UpdateChest(delta);
		UpdateDoor(delta);
		UpdateMessages(delta);
		UpdateObjects(delta);
		UpdateLogic(delta);
		ParticleHandler.UpdateParticles(delta);
		MyMap.UpdateVisibilty(PlayerX, PlayerZ);
		BuildEntityCollision();
		BuildGridPower();
		//System.out.println(GridTypeArray[PlayerZ * Width + PlayerX] + " type:col " + GridCollisionArray[PlayerZ * Width + PlayerX]);
		
	}
	
	private void MoveNegativeZ(){
		if(MovementTimer >= 1){
			if(GridCollisionArray[(PlayerZ * Width + PlayerX) - Width] != 1 && GridEntityCollisionArray[(PlayerZ * Width + PlayerX) - Width] < 1){
				if(GridSpecialWallArray[(PlayerZ * Width + PlayerX) - Width] != 1 && GridSpecialWallArray[(PlayerZ * Width + PlayerX) - Width] != 3){
					PlayerZ--;
					MovementDirection = 1;
					MovementTimer = 0;
					playerMoved = true;
				}
			}
		}
	}
	
	private void MovePositiveZ(){
		if(MovementTimer >= 1){
			if(GridCollisionArray[(PlayerZ * Width + PlayerX) + Width] != 1  && GridEntityCollisionArray[(PlayerZ * Width + PlayerX) + Width] < 1){
				if(GridSpecialWallArray[(PlayerZ * Width + PlayerX)] != 1 && GridSpecialWallArray[(PlayerZ * Width + PlayerX)] != 3){
					PlayerZ++;
					MovementDirection = 2;
					MovementTimer = 0;
					playerMoved = true;
				}
			}
		}
	}
	
	private void MoveNegativeX(){
		if(MovementTimer >= 1){
			if(GridCollisionArray[(PlayerZ * Width + PlayerX) - 1] != 1  && GridEntityCollisionArray[(PlayerZ * Width + PlayerX) - 1] < 1){
				if(GridSpecialWallArray[(PlayerZ * Width + PlayerX) - 1] != 2 && GridSpecialWallArray[(PlayerZ * Width + PlayerX) - 1] != 3){
					PlayerX--;
					MovementTimer = 0;
					MovementDirection = 3;
					playerMoved = true;
				}
			}
		}
	}
	
	private void MovePositiveX(){
		if(MovementTimer >= 1){
			if(GridCollisionArray[(PlayerZ * Width + PlayerX) + 1] != 1 && GridEntityCollisionArray[(PlayerZ * Width + PlayerX) + 1] < 1){
				if(GridSpecialWallArray[(PlayerZ * Width + PlayerX)] != 2 && GridSpecialWallArray[(PlayerZ * Width + PlayerX)] != 3){
					PlayerX++;
					MovementTimer = 0;
					MovementDirection = 4;
					playerMoved = true;
				}
			}
		}
	}

	private void PlayerAttack(){
		
		int TempX = 0;
		int TempZ = 0;
		
		if(Direction == 1 && GridSpecialWallArray[PlayerX + PlayerZ * Width - Width] != 1 && GridSpecialWallArray[PlayerX + PlayerZ * Width - Width] != 3){
			TempX = PlayerX;
			TempZ = PlayerZ - 1;
		}
		if(Direction == 3 && GridSpecialWallArray[PlayerX + PlayerZ * Width] != 1 && GridSpecialWallArray[PlayerX + PlayerZ * Width] != 3){
			TempX = PlayerX;
			TempZ = PlayerZ + 1;
		}
		if(Direction == 4 && GridSpecialWallArray[PlayerX + PlayerZ * Width - 1] != 2 && GridSpecialWallArray[PlayerX + PlayerZ * Width - 1] != 3){
			TempX = PlayerX - 1;
			TempZ = PlayerZ;
		}
		if(Direction == 2 && GridSpecialWallArray[PlayerX + PlayerZ * Width] != 2 && GridSpecialWallArray[PlayerX + PlayerZ * Width ] != 3){
			TempX = PlayerX + 1;
			TempZ = PlayerZ;
		}
		
		for(Enemy MyEnemy: EnemyList){
    		if(MyEnemy.PosX == TempX){
    			if(MyEnemy.PosZ == TempZ){
    				if(MyEnemy.Health > 0){
    					int hitChance = RanInt(0,100);
    					if(hitChance < PlayerInv.Accuracy * 0.5f + PlayerInv.baseAccuracy){
    						int Damage = (int) ((PlayerStrength - MyEnemy.enemyDefense) * (1 + PlayerInv.FuryAmount/20.0f));
    						if(Damage < 1){
    							String TempString = "You struggle to hit " + MyEnemy.EnemyName + " for 1 damage.";
    							AddMessage(TempString);
        						MyEnemy.Health -= 1;
    						}
    						else{
    							String TempString = "You hit " + MyEnemy.EnemyName + " for " + Damage +" damage.";
    							AddMessage(TempString);
    							MyEnemy.Health -= Damage;
    						}
    						
    						//leeching
    						if(PlayerInv.LeechAmount > 0){
    							int LeechAmount = (int) (Damage *  PlayerInv.LeechAmount/20.0f);
    							PlayerHealth += LeechAmount;
    							String TempString = "You Drained " + LeechAmount + " health from " + MyEnemy.EnemyName + ".";
    							AddMessage(TempString);
    						}
    						
    						if(MyEnemy.Health <= 0){    							
    							String TempString = "You killed " + MyEnemy.EnemyName + ".";
    							AddMessage(TempString);
    						}
    						else{
    							OnHitStatus(MyEnemy);
    						}
    						
    						
    						
    						
    						
    					}
    					else{
    						String TempString = "You missed " + MyEnemy.EnemyName + ".";
							AddMessage(TempString);
    					}
    				}
    			}
    		}
    	}
		
		
		GameData.AttackDirection = !GameData.AttackDirection;
		
		
	}
	
	private void PlayerInteract(){
		int TempX = 0;
		int TempZ = 0;
		
		if(Direction == 1){
			TempX = PlayerX;
			TempZ = PlayerZ - 1;
		}
		if(Direction == 3){
			TempX = PlayerX;
			TempZ = PlayerZ + 1;
		}
		if(Direction == 4){
			TempX = PlayerX - 1;
			TempZ = PlayerZ;
		}
		if(Direction == 2){
			TempX = PlayerX + 1;
			TempZ = PlayerZ;
		}
		
		for(Chest MyChest: ChestList){
    		if(MyChest.PosX == TempX){
    			if(MyChest.PosZ == TempZ){
    				if(MyChest.Open == false){
    					if(MyChest.Contents.equals("Potion.")){
    						Potions++;
    					}
    				}
    				
    				MyChest.OpenChest();
    				if(MyChest.Message == true){
    					if(MyChest.Contents.equals("Potion.")){
    						AddMessage(MyChest.AwaitingMessage);
    						MyChest.Message = false;
    					}
    					else{
    						MyChest.Message = false;
    						PlayerInv.AddItem(MyChest.GetItem());
    						AddMessage("Retrived " + MyChest.ChestItem.Getname());
    					}
    				}
    			}
    		}
    	}
		
		for(Door MyDoor: DoorList){
			if(MyDoor.NeedsPower == false){
				if(MyDoor.PosX == TempX){
					if(MyDoor.PosZ == TempZ){
						MyDoor.OpenChest();
					}
				}
			}
    	}
		
		for(DungeonObject MyObject: DungeonObjectList){
			if(MyObject instanceof PushObject){
				if(MyObject.PosX == TempX){
					if(MyObject.PosZ == TempZ){
						MyObject.PlayerInteract(PlayerX, PlayerZ, GridTypeArray, GridEntityCollisionArray, GridSpecialWallArray);
					}
				}
			}
			if(MyObject instanceof Button){
				if(MyObject.PosX == TempX){
					if(MyObject.PosZ == TempZ){
						MyObject.PlayerInteract(PlayerX, PlayerZ);
					}
				}
			}
			if(MyObject instanceof LeverObject){
				if(MyObject.PosX == PlayerX){
					if(MyObject.PosZ == PlayerZ){
						MyObject.PlayerInteract(PlayerX, PlayerZ, Direction);
					}
				}
			}
    	}
		
		for(LogicObject MyLogic: LogicObjectList){
			if(MyLogic instanceof LogicActivator){
				if(MyLogic.GetData() == 5){
					if(TempX == MyLogic.PositionX && TempZ == MyLogic.PositionZ){
						MyLogic.Powered = true;
						PowerGrid(MyLogic);
					}
				}
			}
		}
		
	}
	
	private void UsePotion(){
		if(PotionTimer > 2){
			if(Potions > 0){
				Potions--;
				PlayerHealth+=100;
				if(PlayerHealth > PlayerMaxHealth){PlayerHealth = PlayerMaxHealth;}
				String TempString = "You used a potion.";
				AddMessage(TempString);
				PotionTimer = 0;
				if(Potions == 0){
					TempString = "You are now out of Potions..";
					AddMessage(TempString);
				}
			}
			else{
				String TempString = "You are out of Potions.";
				AddMessage(TempString);
				PotionTimer = 0;
			}
		}
	}
	
	public void LoadLevel(String LevelName){
		ModelHandler.ClearLights();
		LoadDungeon TempLoad = new LoadDungeon(LevelName);
		for(int i = 0; i < 1024; i++){
			GridTypeArray[i] = TempLoad.GridTypeArray[i];
			GridCollisionArray[i] = 0;
			PathCalcArray[i] = new PathCalcData();
			PathChecked[i] = 0;
			GridDamageArray[i] = 0;
			GridSpecialWallArray[i] = TempLoad.GridFlatWallArray[i];
		}
		
		EnemyList.addAll(TempLoad.EnemyList);
		ChestList.addAll(TempLoad.ChestList);
		DoorList.addAll(TempLoad.DoorList);
		DungeonObjectList.addAll(TempLoad.DungeonObjectList);
		LogicObjectList.addAll(TempLoad.LogicObjectList);
		
		if(TempLoad.Time == 0){
			GameData.DayTime = true;
		}
		else if(TempLoad.Time == 1){
			GameData.DayTime = false;
		}
		else if(TempLoad.Time == 2){
			GameData.DayTime = false;
			GameData.Midnight = true;
		}
		
		if(TempLoad.Lights){
			GameData.Lights = true;
		}
		else{
			GameData.Lights = false;
		}
		
		GameData.hasNextFloor = TempLoad.HasNextFloor;
		GameData.LoadName = TempLoad.NextLevelName + ".Dungeon";
		
		FloorNumber = TempLoad.FloorNumber;
		
		PlayerX = TempLoad.PlayerX;
		PlayerZ = TempLoad.PlayerZ;
		ExitPosX = TempLoad.ExitPosX;
		ExitPosZ = TempLoad.ExitPosZ;
		
		ExitObject.SetPosition(ExitPosX * GameData.GameScale + GameData.GameScale/2, 0, ExitPosZ * GameData.GameScale + GameData.GameScale/2);
		ExitPortalObject.SetPosition(ExitPosX * GameData.GameScale + GameData.GameScale/2, 0, ExitPosZ * GameData.GameScale + GameData.GameScale/2);
		
	}
	
	public void GenerateFlatWorld(){

		for(int i = 0; i < Width*Height; i++){
			GridTypeArray[i] = 0;
			GridCollisionArray[i] = 0;
			PathCalcArray[i] = new PathCalcData();
			PathChecked[i] = 0;
			GridDamageArray[i] = 0;
			GridSpecialWallArray[i] = 0;
		}
		
		LineEdge(3);
		RimDetection(0,2,3);
		ReplaceAll(2,3);
		RimDetection(0,2,3);
		ReplaceAll(2,3);
		
		ReplaceAll(0,2);
		ReplaceAll(3,0);
		RimDetection(0,1,2);
		RimDetection(2,3,1);
		
		AddDots(3, 4, 5);
		AddDots(2, 5, 5);
		
		//player spawn
		while(GridTypeArray[PlayerZ*Width + PlayerX] != 3){
			PlayerX = RanInt(0, Height);
			PlayerZ = RanInt(0, Width);
		}
		
		PopulateEnemy(3, 10);
		PopulateChests(3, 10);
		
		BuildEntityCollision();
		
		PopulateDoor(3);
		
		BuildEntityCollision();
		
		while(GridTypeArray[ExitPosZ*Width + ExitPosX] != 2 || GridEntityCollisionArray[ExitPosZ*Width + ExitPosX] > 0){
			ExitPosX = RanInt(0, Height);
			ExitPosZ = RanInt(0, Width);
		}
		//System.out.println(ExitPosX +" " + ExitPosZ);
		ExitObject.SetPosition(ExitPosX * GameData.GameScale + GameData.GameScale/2, 0, ExitPosZ * GameData.GameScale + GameData.GameScale/2);
		ExitPortalObject.SetPosition(ExitPosX * GameData.GameScale + GameData.GameScale/2, 0, ExitPosZ * GameData.GameScale + GameData.GameScale/2);
	}
	
	public void GenerateWinding(){

		boolean safe = false;
		
		while(safe == false){
		for(int i = 0; i < Width*Height; i++){
			GridTypeArray[i] = 0;
			GridCollisionArray[i] = 0;
			PathCalcArray[i] = new PathCalcData();
			PathChecked[i] = 0;
			GridDamageArray[i] = 0;
			GridSpecialWallArray[i] = 0;
		}
		
		LineEdge(3);
		RimDetection(0,2,3);
		ReplaceAll(2,3);
		RimDetection(0,2,3);
		ReplaceAll(2,3);
		
		//ReplaceAll(0,2);
		
		GenerateSingularRandomPath(0, 2);
		RimDetection(0,8,2);
		ReplaceAll(8,2);
		GenerateRandomPath(0, 2);
		
		
		ReplaceAll(3,0);
		FloodSimulation(2, 0);
		RimDetection(0,1,2);
		
		RimDetection(2,3,1);
		
		if(CountType(2) > 0){
			safe = true;
		}
	}
		//System.out.println("safe");
		
		AddDots(3, 4, 5);
		AddDots(2, 5, 5);
		
		//player spawn

		while(GridTypeArray[PlayerZ*Width + PlayerX] != 3){
			PlayerX = RanInt(0, Height);
			PlayerZ = RanInt(0, Width);
		}
		//System.out.println("player done");
		
		PopulateEnemy(3, 10);
		//System.out.println("enemy done");
		PopulateChests(3, 10);
		//System.out.println("chest done");
		BuildEntityCollision();
		PopulateDoor(3);
		//System.out.println("door done");
		BuildEntityCollision();
		
		while(GridTypeArray[ExitPosZ*Width + ExitPosX] != 2 || GridEntityCollisionArray[ExitPosZ*Width + ExitPosX] > 0){
			ExitPosX = RanInt(0, Height);
			ExitPosZ = RanInt(0, Width);
		}
		//System.out.println(ExitPosX +" " + ExitPosZ);
		ExitObject.SetPosition(ExitPosX * GameData.GameScale + GameData.GameScale/2, 0, ExitPosZ * GameData.GameScale + GameData.GameScale/2);
		ExitPortalObject.SetPosition(ExitPosX * GameData.GameScale + GameData.GameScale/2, 0, ExitPosZ * GameData.GameScale + GameData.GameScale/2);
	}
	
	public void GenerateMaze(){
		for(int i = 0; i < Width*Height; i++){
			GridTypeArray[i] = 0;
			GridCollisionArray[i] = 0;
			PathCalcArray[i] = new PathCalcData();
			PathChecked[i] = 0;
			GridDamageArray[i] = 0;
			GridSpecialWallArray[i] = 0;
		}
		LineEdge(3);
		RimDetection(0,2,3);
		ReplaceAll(2,3);
		RimDetection(0,2,3);
		ReplaceAll(2,3);
		
		GenerateRandomPath(0,2);
		RimDetection(0, 1, 2);
		ReplaceAll(3,0);
		RimDetection(0, 1, 2);
		ReplaceAll(2,3);
		
		AddDots(3, 4, 5);
		
		//ReplaceAll(2,1);
		
		//player spawn
		while(GridTypeArray[PlayerZ*Width + PlayerX] != 3){
			PlayerX = RanInt(0, Height);
			PlayerZ = RanInt(0, Width);
		}
			
		PopulateEnemy(3, 10);
		PopulateChests(3, 10);
		
		BuildEntityCollision();
		
		PopulateDoor(3);
		
		BuildEntityCollision();
		
		while(GridTypeArray[ExitPosZ*Width + ExitPosX] != 3 || GridEntityCollisionArray[ExitPosZ*Width + ExitPosX] > 0){
			ExitPosX = RanInt(0, Height);
			ExitPosZ = RanInt(0, Width);
		}
		//System.out.println(ExitPosX +" " + ExitPosZ);
		ExitObject.SetPosition(ExitPosX * GameData.GameScale + GameData.GameScale/2, 0, ExitPosZ * GameData.GameScale + GameData.GameScale/2);
		ExitPortalObject.SetPosition(ExitPosX * GameData.GameScale + GameData.GameScale/2, 0, ExitPosZ * GameData.GameScale + GameData.GameScale/2);
		
	}
	
	public void GenerateOpenWorld(){
		for(int i = 0; i < Width*Height; i++){
			GridTypeArray[i] = 0;
			GridCollisionArray[i] = 0;
			PathCalcArray[i] = new PathCalcData();
			PathChecked[i] = 0;
			GridDamageArray[i] = 0;
			GridSpecialWallArray[i] = 0;
		}
		
		LineEdge(4);
		RimDetection(0,7,4);
		ReplaceAll(7, 4);

		StartCellSimulation(2,5,0);
		SimulationStep(2, 5);
		SimulationStep(2, 5);
		SimulationStep(2, 5);
		SimulationStep(2, 5);
		FloodSimulation(2, 5);

		ReplaceAll(5,0);
		ReplaceAll(4,0);

		//RimDetection(0,1,2);
		RimDetection(2,3,1);
		RimDetection(0,1,2);
		RimDetection(2,3,1);
		
		
		AddDots(3, 4, 5);
		AddDots(2, 5, 5);
		
		//player spawn
		while(GridTypeArray[PlayerZ*Width + PlayerX] != 2){
			PlayerX = RanInt(0, Height);
			PlayerZ = RanInt(0, Width);
		}
		
		PopulateEnemy(2, 10);
		PopulateChests(3, 4);
		
		BuildEntityCollision();
		
		PopulateDoor(3);
		
		while(GridTypeArray[ExitPosZ*Width + ExitPosX] != 2){
			ExitPosX = RanInt(0, Height);
			ExitPosZ = RanInt(0, Width);
		}
		ExitObject.SetPosition(ExitPosX * GameData.GameScale + GameData.GameScale/2, 0, ExitPosZ * GameData.GameScale + GameData.GameScale/2);
		ExitPortalObject.SetPosition(ExitPosX * GameData.GameScale + GameData.GameScale/2, 0, ExitPosZ * GameData.GameScale + GameData.GameScale/2);
		
	}
	
	public void GenerateLabyrinth(){
		for(int i = 0; i < Width*Height; i++){
			GridTypeArray[i] = 0;
			GridCollisionArray[i] = 0;
			PathCalcArray[i] = new PathCalcData();
			PathChecked[i] = 0;
			GridDamageArray[i] = 0;
			GridSpecialWallArray[i] = 0;
		}
		
		LineEdge(2);
		RimDetection(0, 3, 2);
		ReplaceAll(3, 2);
		RimDetection(0, 3, 2);
		ReplaceAll(3, 2);

		GenerateRooms(0, 1, 200, 1000, 20);
		GenerateRandomPath(0,3);
		ReplaceAll(1, 3);
		FloodSimulation(3, 0);
		RemoveDeadEnds(3, 0);
		ReplaceAll(2, 0);
		ReplaceAll(3, 2);
		RimDetection(0, 1, 2);
		RimDetection(2, 3, 1);
		
		AddDots(3, 4, 5);
		AddDots(2, 5, 5);
		
		//player spawn
		while(GridTypeArray[PlayerZ*Width + PlayerX] != 2){
			PlayerX = RanInt(0, Height);
			PlayerZ = RanInt(0, Width);
		}
		
		PopulateEnemy(2, 10);
		PopulateChests(3, 3);
		
		BuildEntityCollision();
		
		PopulateDoor(3);
		
		while(GridTypeArray[ExitPosZ*Width + ExitPosX] != 2){
			ExitPosX = RanInt(0, Height);
			ExitPosZ = RanInt(0, Width);
		}
		ExitObject.SetPosition(ExitPosX * GameData.GameScale + GameData.GameScale/2, 0, ExitPosZ * GameData.GameScale + GameData.GameScale/2);
		ExitPortalObject.SetPosition(ExitPosX * GameData.GameScale + GameData.GameScale/2, 0, ExitPosZ * GameData.GameScale + GameData.GameScale/2);
		
	}
	
	private void LineEdge(int newType){
		for(int i = 0; i < Width; i ++){
			GridTypeArray[i] = newType;
			GridTypeArray[i + Width*(Height-1)] = newType;
		}
		for(int i = 0; i < Height; i++){
			GridTypeArray[Width*i] = newType;
			GridTypeArray[Width*i + Width - 1] = newType;

		}
	}

	public void buildBuffers(float tileWidth){
		TileSize = tileWidth;
		float vertexArray[] = new float[Width*Height*6 * 3];
		float colourArray[] = new float[Width*Height*6 * 3];
		float normalArray[] = new float[Width*Height*6 * 3];
		
		for(int h = 0; h < Height; h++){
			for(int w = 0; w < Width; w++){
				int i = Width * h + w;
				//vertices
				//triangle one
				vertexArray[i*18] = tileWidth + (w * tileWidth); //top right vertex
				vertexArray[i*18+1] = 0f;
				vertexArray[i*18+2] = tileWidth + (h * tileWidth);
				//System.out.println(vertexArray[i*18] + " 0 " + vertexArray[i*18+2]);
				
				vertexArray[i*18+3] = (w * tileWidth); //bottom left vertex
				vertexArray[i*18+4] = 0f;
				vertexArray[i*18+5] = (h * tileWidth);
				//System.out.println(vertexArray[i*18+3] + " 0 " + vertexArray[i*18+5]);

				vertexArray[i*18+6] = (w * tileWidth); //top left vertex
				vertexArray[i*18+7] = 0f;
				vertexArray[i*18+8] = tileWidth + (h * tileWidth);
				//System.out.println(vertexArray[i*18+6] + " 0 " + vertexArray[i*18+8]);
				
			
			
				//triangle two
				vertexArray[i*18+9] = tileWidth + (w * tileWidth); //top right vertex
				vertexArray[i*18+10] = 0f;
				vertexArray[i*18+11] = tileWidth + (h * tileWidth);
				//System.out.println((tileWidth + (w * tileWidth)) + " 0 " + (tileWidth + (h * tileWidth)));
			
				vertexArray[i*18+12] = tileWidth + (w * tileWidth); //bottom rightd vertex
				vertexArray[i*18+13] = 0f;
				vertexArray[i*18+14] = (h * tileWidth);
				//System.out.println((tileWidth + (w * tileWidth)) + " 0 " + h * tileWidth);
				
				vertexArray[i*18+15] = (w * tileWidth); //bottom left vertex
				vertexArray[i*18+16] = 0f;
				vertexArray[i*18+17] = (h * tileWidth);
				//System.out.println(w * tileWidth + " 0 " + h * tileWidth);
			
				//colour
				Vector3f Mycolour = GetColour(i);
				for(int c = 0; c < 6; c ++){
					colourArray[i*18 + c*3] = Mycolour.x;
					colourArray[i*18 + c*3 + 1] = Mycolour.y;
					colourArray[i*18 + c*3 + 2] = Mycolour.z;
				
				}
			
				//normals
				Vector3f Mynormal = new Vector3f(0,1,0);
				for(int n = 0; n < 6; n ++){
					normalArray[i*18 + n*3] = Mynormal.x;
					normalArray[i*18 + n*3 + 1] = Mynormal.y;
					normalArray[i*18 + n*3 + 2] = Mynormal.z;
				}
			}
			
		}
		
		
		VertBuffer = BufferUtils.createFloatBuffer(Width*Height * 18);
        NormBuffer = BufferUtils.createFloatBuffer(Width*Height * 18);
        ColourBuffer = BufferUtils.createFloatBuffer(Width*Height * 18);
        
        VertBuffer.put(vertexArray);
        NormBuffer.put(normalArray);
        ColourBuffer.put(colourArray);
        
        VertBuffer.rewind();
        NormBuffer.rewind();
        ColourBuffer.rewind();
        
        vbo_vertex_handle = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo_vertex_handle);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, VertBuffer, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        
        vbo_normal_handle = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo_normal_handle);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, NormBuffer, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        
        vbo_colour_handle = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo_colour_handle);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, ColourBuffer, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        
        vertexArray = null;
		colourArray = null;
		normalArray = null;
		
	}
	
    public void GridRender(){
    	
    	ShaderHandler.ColNormShader.Activate();
    	
    	GL11.glLoadIdentity();
    	GL11.glRotatef(GameData.CameraRotation.y, 0, 1, 0);
    	GL11.glTranslatef(GameData.CameraPosition.x, GameData.CameraPosition.y, GameData.CameraPosition.z);
    	
    	
    	GL11.glEnable(GL11.GL_COLOR_MATERIAL);
    	GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
  		GL11.glEnableClientState(GL11.GL_NORMAL_ARRAY);
  		GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
  		  
  		GL15.glBindBuffer( GL15.GL_ARRAY_BUFFER, vbo_vertex_handle);
  		GL11.glVertexPointer(3, GL11.GL_FLOAT, 0, 0);

  		GL15.glBindBuffer( GL15.GL_ARRAY_BUFFER, vbo_normal_handle);
  		GL11.glNormalPointer(GL11.GL_FLOAT, 0, 0);
  		  
  		GL15.glBindBuffer( GL15.GL_ARRAY_BUFFER, vbo_colour_handle);
  		GL11.glColorPointer(3, GL11.GL_FLOAT, 0, 0);

  		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, Width*Height*18);
  		
  		ShaderHandler.ColNormShader.DeActivate();
  		GL11.glDisable(GL11.GL_COLOR_MATERIAL);
  	
    }
	
    public void TileRender(){
    	
    	MyGridObject.Draw();
    	
    	MyWindObject.Draw();
    	GL11.glDisable(GL11.GL_CULL_FACE);
    	ExitObject.Rotation  = MyMath.HeadingVectorToAngle(new Vector2f(ExitObject.Position.x - -GameData.CameraPosition.x, ExitObject.Position.z - -GameData.CameraPosition.z));
    	ExitObject.renderDisplayObject();
    	GL11.glEnable(GL11.GL_CULL_FACE);
    	RenderChest();
    	RenderDoor();
    	ParticleHandler.DrawParticles();
    	RenderPlainObjects();
    	RenderEnemy();
    	RenderObjects();
    	
    	
    	MyLightObject.Draw();

    	GL11.glDisable(GL11.GL_CULL_FACE);
    	ExitPortalObject.Rotation  = MyMath.HeadingVectorToAngle(new Vector2f(ExitPortalObject.Position.x - -GameData.CameraPosition.x, ExitPortalObject.Position.z - -GameData.CameraPosition.z));
    	ExitPortalObject.renderDisplayObject();
    	GL11.glEnable(GL11.GL_CULL_FACE);
    }
    
    public void TileRenderDepth(){
    	
    	MyGridObject.DrawDepth();
    	
    	MyWindObject.DrawDepth();
    	
    	MyLightObject.DrawDepth();
    	
    	ExitObject.renderDisplayObjectDepth();
    	
    	RenderEnemyDepth();
    	RenderChestDepth();
    	RenderDoorDepth();
    	RenderObjectsDepth();
    	
    }
    
    public void UIRender(){
    	ShaderHandler.TextAlphaShader.Activate();
    	if(MessageList.size() > 0){
    		FontHandler.ElitePro.DisplayFont(20, 150, 0.5f, MessageList.get(0).MyMessage, MessageList.get(0).alphatimer);
    	}
    	if(MessageList.size() > 1){
    		FontHandler.ElitePro.DisplayFont(20, 135, 0.5f, MessageList.get(1).MyMessage, MessageList.get(1).alphatimer);
    	}
    	if(MessageList.size() > 2){
    		FontHandler.ElitePro.DisplayFont(20, 120, 0.5f, MessageList.get(2).MyMessage, MessageList.get(2).alphatimer);
    	}
    	if(MessageList.size() > 3){
    		FontHandler.ElitePro.DisplayFont(20, 105, 0.5f, MessageList.get(3).MyMessage, MessageList.get(3).alphatimer);
    	}
    	if(MessageList.size() > 4){
    		FontHandler.ElitePro.DisplayFont(20, 90, 0.5f, MessageList.get(4).MyMessage, MessageList.get(4).alphatimer);
    	}
    	if(MessageList.size() > 5){
    		FontHandler.ElitePro.DisplayFont(20, 75, 0.5f, MessageList.get(5).MyMessage, MessageList.get(5).alphatimer);
    	}
    	if(MessageList.size() > 6){
    		FontHandler.ElitePro.DisplayFont(20, 60, 0.5f, MessageList.get(6).MyMessage, MessageList.get(6).alphatimer);
    	}
    	if(MessageList.size() > 7){
    		FontHandler.ElitePro.DisplayFont(20, 45, 0.5f, MessageList.get(7).MyMessage, MessageList.get(7).alphatimer);
    	}
    	if(MessageList.size() > 8){
    		FontHandler.ElitePro.DisplayFont(20, 30, 0.5f, MessageList.get(8).MyMessage, MessageList.get(8).alphatimer);
    	}
    	if(MessageList.size() > 9){
    		FontHandler.ElitePro.DisplayFont(20, 15, 0.5f, MessageList.get(9).MyMessage, MessageList.get(9).alphatimer);
    	}
    	ShaderHandler.TextAlphaShader.DeActivate();
    	
    	ShaderHandler.TextShader.Activate();
    	FontHandler.ElitePro.DisplayFont(20, 1040, 0.6f, "Health: " + (int)PlayerHealth + "/" + (int)PlayerMaxHealth);
    	//FontHandler.ElitePro.DisplayFont(20, 1020, 0.6f, "Strength: " + PlayerStrength);
    	//FontHandler.ElitePro.DisplayFont(20, 1000, 0.6f, "Defense: " + PlayerDefense);
    	
    	//FontHandler.ElitePro.DisplayFont(20, 980, 0.6f, "Fortitude: " + PlayerInv.Fortitude);
    	//FontHandler.ElitePro.DisplayFont(20, 960, 0.6f, "Accuracy: " + PlayerInv.Accuracy);
    	//FontHandler.ElitePro.DisplayFont(20, 940, 0.6f, "Evasiveness" + PlayerInv.Evasiveness);
    	
    	FontHandler.ElitePro.DisplayFont(20, 1020, 0.6f, "Potions: " + Potions);
    	FontHandler.ElitePro.DisplayFont(20, 1000, 0.6f, "Floor: " + FloorNumber);
    	
    	RenderObjects2D();
    	
    	if(Input.GetM() == true && GameData.ConsoleOpen == false){
    		MyMap.RenderMap(510, 90, PlayerX, PlayerZ);
    	}
    	
    	ShaderHandler.TextShader.DeActivate();
    }
    
    public void BuildGridObject(){
    	MyGridObject = new GridObject(MyTileSet, Width, Height, GridTypeArray, "Temple", GridSpecialWallArray);
    	MyLightObject = new GridLightObject(MyTileSet, Width, Height, GridTypeArray);
    }
    
    public void BuildWindObject(){
    	MyWindObject = new GridWindObject(Width, Height, GridTypeArray, "windgrad");
    }
    
    public void BuildCollision(){
    	for(int i = 0; i < Width*Height; i++){
    		
    		GridCollisionArray[i] = 0;
    		
    		if(GridTypeArray[i] == 1 || GridTypeArray[i] == 0){
    			GridCollisionArray[i] = 1;
    		}
    		
    	}
    }
    
    public void BuildMap(){
    	MyMap = new GridMap(Width, Height, GridTypeArray, "map");
    }
    
    private Vector3f GetColour(int position){
    	if(GridTypeArray[position] == 0){
    		return new Vector3f(0,0,0);
    	}
    	else if(GridTypeArray[position] == 1){
    		return new Vector3f(0,0,1);
    	}
    	else if(GridTypeArray[position] == 2){
    		return new Vector3f(0,1,0);
    	}
    	else{
    		return new Vector3f(0,0,0);
    	}
    }
    
    private void RimDetection(int oldType, int newType, int checkType){
    	
    	for(int w = 0; w < Width; w++){
    		for(int h = 0; h < Height; h++){
    			int i = h*Width + w;
    			if(GridTypeArray[i] == checkType){
    				if(h > 0){
    					if(GridTypeArray[i - Width] == oldType){
    						GridTypeArray[i - Width] = newType;
    					}
    					if(GridTypeArray[i - Width + 1] == oldType){
    						GridTypeArray[i - Width + 1] = newType;
    					}
    					if(i - Width - 1 > 0){
    						if(GridTypeArray[i - Width - 1] == oldType){
    							GridTypeArray[i - Width - 1] = newType;
    						}
    					}
    				}
    				
    				if(i< Width*Height - Width){
    					if(GridTypeArray[i + Width] == oldType){
    						GridTypeArray[i + Width] = newType;
    					}
    					if(i + Width + 1 < Width*Height){
    						if(GridTypeArray[i + Width + 1] == oldType){
    							GridTypeArray[i + Width + 1] = newType;
    						}
    					}
    					if(GridTypeArray[i + Width - 1] == oldType){
    						GridTypeArray[i + Width - 1] = newType;
    					}
    				}
    				if(i < Width*Height - 1){
    					if(GridTypeArray[i + 1] == oldType){
    						GridTypeArray[i + 1] = newType;
    					}
    				}
    				if(i > 0){
    					if(GridTypeArray[i - 1] == oldType){
    						GridTypeArray[i - 1] = newType;
    					}
    				}
    			
    			}
    		}
    	}
    	
    	
    }

    private void ReplaceAll(int oldType, int newType){
    	for(int i = 0; i < Width*Height; i++){
    		if(GridTypeArray[i] == oldType){
    			GridTypeArray[i] = newType;
    		}
    	}
    }
    
    private void GenerateSingularRandomPath(int oldType, int newType){
    	
    	int CurrentX = 0;
    	int CurrentY = 0;
    	int NextX = 0;
    	int NextY = 0;
    	boolean PathDone = false;
    	TempPosition = 0;
    	
    	while(GridTypeArray[CurrentY*Width + CurrentX] != oldType){
    		CurrentX = RanInt(0, Width);
    		CurrentY = RanInt(0, Height);
    	}
    	//System.out.println(CurrentX + " " + CurrentX +" " + GridTypeArray[CurrentY*Width + CurrentX]);
    	
    	while(PathDone == false){
    		GridTypeArray[(CurrentY) * Width + CurrentX] = newType; // set to new type
    		
    		PathCalcArray[TempPosition].Real = true;
    		PathCalcArray[TempPosition].PositionX = CurrentX;
    		PathCalcArray[TempPosition].PositionY = CurrentY;
    		PathCalcArray[TempPosition].ParentArrayPosition = TempPosition-1;
    		
    		int RanDirection = RanInt(1,4);
    		
    		if(RanDirection == 1){//up first,d , l, r
    			if(GridTypeArray[(CurrentY - 2) * Width + CurrentX] == oldType){
    				GridTypeArray[(CurrentY - 2) * Width + CurrentX] = newType;
    				GridTypeArray[(CurrentY - 1) * Width + CurrentX] = newType;
    				NextX = CurrentX;
    				NextY = CurrentY - 2;
    			}
    			else if(GridTypeArray[(CurrentY + 2) * Width + CurrentX] == oldType){
    				GridTypeArray[(CurrentY + 2) * Width + CurrentX] = newType;
    				GridTypeArray[(CurrentY + 1) * Width + CurrentX] = newType;
    				NextX = CurrentX;
    				NextY = CurrentY + 2;
    			}
    			else if(GridTypeArray[CurrentY * Width + CurrentX - 2] == oldType){
    				GridTypeArray[CurrentY * Width + CurrentX - 2] = newType;
    				GridTypeArray[CurrentY * Width + CurrentX - 1] = newType;
    				NextX = CurrentX - 2;
    				NextY = CurrentY;
    			}
    			else if(GridTypeArray[CurrentY * Width + CurrentX + 2] == oldType){
    				GridTypeArray[CurrentY * Width + CurrentX + 2] = newType;
    				GridTypeArray[CurrentY * Width + CurrentX + 1] = newType;
    				NextX = CurrentX + 2;
    				NextY = CurrentY;
    			}
    			else{
    				PathDone = true;
    			}
    		}
    		
    		
    		
    		if(RanDirection == 2){//d, l, r, u
    			if(GridTypeArray[(CurrentY + 2) * Width + CurrentX] == oldType){
    				GridTypeArray[(CurrentY + 2) * Width + CurrentX] = newType;
    				GridTypeArray[(CurrentY + 1) * Width + CurrentX] = newType;
    				NextX = CurrentX;
    				NextY = CurrentY + 2;
    			}
    			else if(GridTypeArray[CurrentY * Width + CurrentX - 2] == oldType){
    				GridTypeArray[CurrentY * Width + CurrentX - 2] = newType;
    				GridTypeArray[CurrentY * Width + CurrentX - 1] = newType;
    				NextX = CurrentX - 2;
    				NextY = CurrentY;
    			}
    			else if(GridTypeArray[CurrentY * Width + CurrentX + 2] == oldType){
    				GridTypeArray[CurrentY * Width + CurrentX + 2] = newType;
    				GridTypeArray[CurrentY * Width + CurrentX + 1] = newType;
    				NextX = CurrentX + 2;
    				NextY = CurrentY;
    			}
    			else if(GridTypeArray[(CurrentY - 2) * Width + CurrentX] == oldType){
    				GridTypeArray[(CurrentY - 2) * Width + CurrentX] = newType;
    				GridTypeArray[(CurrentY - 1) * Width + CurrentX] = newType;
    				NextX = CurrentX;
    				NextY = CurrentY - 2;
    			}
    			else{
    				PathDone = true;
    			}
    		}

    		if(RanDirection == 3){// l, r, u, d
    			if(GridTypeArray[CurrentY * Width + CurrentX - 2] == oldType){
    				GridTypeArray[CurrentY * Width + CurrentX - 2] = newType;
    				GridTypeArray[CurrentY * Width + CurrentX - 1] = newType;
    				NextX = CurrentX - 2;
    				NextY = CurrentY;
    			}
    			else if(GridTypeArray[CurrentY * Width + CurrentX + 2] == oldType){
    				GridTypeArray[CurrentY * Width + CurrentX + 2] = newType;
    				GridTypeArray[CurrentY * Width + CurrentX + 1] = newType;
    				NextX = CurrentX + 2;
    				NextY = CurrentY;
    			}
    			else if(GridTypeArray[(CurrentY - 2) * Width + CurrentX] == oldType){
    				GridTypeArray[(CurrentY - 2) * Width + CurrentX] = newType;
    				GridTypeArray[(CurrentY - 1) * Width + CurrentX] = newType;
    				NextX = CurrentX;
    				NextY = CurrentY - 2;
    			}
    			else if(GridTypeArray[(CurrentY + 2) * Width + CurrentX] == oldType){
    				GridTypeArray[(CurrentY + 2) * Width + CurrentX] = newType;
    				GridTypeArray[(CurrentY + 1) * Width + CurrentX] = newType;
    				NextX = CurrentX;
    				NextY = CurrentY + 2;
    			}
    			else{
    				PathDone = true;
    			}
    		}



    		if(RanDirection == 4){// r, u, d, l
    			if(GridTypeArray[CurrentY * Width + CurrentX + 2] == oldType){
    				GridTypeArray[CurrentY * Width + CurrentX + 2] = newType;
    				GridTypeArray[CurrentY * Width + CurrentX + 1] = newType;
    				NextX = CurrentX + 2;
    				NextY = CurrentY;
    			}
    			else if(GridTypeArray[(CurrentY - 2) * Width + CurrentX] == oldType){
    				GridTypeArray[(CurrentY - 2) * Width + CurrentX] = newType;
    				GridTypeArray[(CurrentY - 1) * Width + CurrentX] = newType;
    				NextX = CurrentX;
    				NextY = CurrentY - 2;
    			}
    			else if(GridTypeArray[(CurrentY + 2) * Width + CurrentX] == oldType){
    				GridTypeArray[(CurrentY + 2) * Width + CurrentX] = newType;
    				GridTypeArray[(CurrentY + 1) * Width + CurrentX] = newType;
    				NextX = CurrentX;
    				NextY = CurrentY + 2;
    			}
    			else if(GridTypeArray[CurrentY * Width + CurrentX - 2] == oldType){
    				GridTypeArray[CurrentY * Width + CurrentX - 2] = newType;
    				GridTypeArray[CurrentY * Width + CurrentX - 1] = newType;
    				NextX = CurrentX - 2;
    				NextY = CurrentY;
    			}
    			else{
    				PathDone = true;
    			}
    		}
    		
    		CurrentX =  NextX;
    		CurrentY = NextY;

    		if(PathDone == false){
     			TempPosition++;
    		}
    	}
    	
    }
    
    private void GenerateRandomPath(int oldType, int newType){
    	
    	int CurrentX = 0;
    	int CurrentY = 0;
    	int NextX = 0;
    	int NextY = 0;
    	boolean PathDone = false;
    	TempPosition = 0;
    	
    	while(GridTypeArray[CurrentY*Width + CurrentX] != oldType){
    		CurrentX = RanInt(0, Width);
    		CurrentY = RanInt(0, Height);
    	}
    	//System.out.println(CurrentX + " " + CurrentX +" " + GridTypeArray[CurrentY*Width + CurrentX]);
    	
    	while(PathDone == false){
    		GridTypeArray[(CurrentY) * Width + CurrentX] = newType; // set to new type
    		
    		PathCalcArray[TempPosition].Real = true;
    		PathCalcArray[TempPosition].PositionX = CurrentX;
    		PathCalcArray[TempPosition].PositionY = CurrentY;
    		PathCalcArray[TempPosition].ParentArrayPosition = TempPosition-1;
    		
    		int RanDirection = RanInt(1,4);
    		
    		if(RanDirection == 1){//up first,d , l, r
    			if(GridTypeArray[(CurrentY - 2) * Width + CurrentX] == oldType){
    				GridTypeArray[(CurrentY - 2) * Width + CurrentX] = newType;
    				GridTypeArray[(CurrentY - 1) * Width + CurrentX] = newType;
    				NextX = CurrentX;
    				NextY = CurrentY - 2;
    			}
    			else if(GridTypeArray[(CurrentY + 2) * Width + CurrentX] == oldType){
    				GridTypeArray[(CurrentY + 2) * Width + CurrentX] = newType;
    				GridTypeArray[(CurrentY + 1) * Width + CurrentX] = newType;
    				NextX = CurrentX;
    				NextY = CurrentY + 2;
    			}
    			else if(GridTypeArray[CurrentY * Width + CurrentX - 2] == oldType){
    				GridTypeArray[CurrentY * Width + CurrentX - 2] = newType;
    				GridTypeArray[CurrentY * Width + CurrentX - 1] = newType;
    				NextX = CurrentX - 2;
    				NextY = CurrentY;
    			}
    			else if(GridTypeArray[CurrentY * Width + CurrentX + 2] == oldType){
    				GridTypeArray[CurrentY * Width + CurrentX + 2] = newType;
    				GridTypeArray[CurrentY * Width + CurrentX + 1] = newType;
    				NextX = CurrentX + 2;
    				NextY = CurrentY;
    			}
    			else{
    				PathDone = true;
    			}
    		}
    		
    		
    		
    		if(RanDirection == 2){//d, l, r, u
    			if(GridTypeArray[(CurrentY + 2) * Width + CurrentX] == oldType){
    				GridTypeArray[(CurrentY + 2) * Width + CurrentX] = newType;
    				GridTypeArray[(CurrentY + 1) * Width + CurrentX] = newType;
    				NextX = CurrentX;
    				NextY = CurrentY + 2;
    			}
    			else if(GridTypeArray[CurrentY * Width + CurrentX - 2] == oldType){
    				GridTypeArray[CurrentY * Width + CurrentX - 2] = newType;
    				GridTypeArray[CurrentY * Width + CurrentX - 1] = newType;
    				NextX = CurrentX - 2;
    				NextY = CurrentY;
    			}
    			else if(GridTypeArray[CurrentY * Width + CurrentX + 2] == oldType){
    				GridTypeArray[CurrentY * Width + CurrentX + 2] = newType;
    				GridTypeArray[CurrentY * Width + CurrentX + 1] = newType;
    				NextX = CurrentX + 2;
    				NextY = CurrentY;
    			}
    			else if(GridTypeArray[(CurrentY - 2) * Width + CurrentX] == oldType){
    				GridTypeArray[(CurrentY - 2) * Width + CurrentX] = newType;
    				GridTypeArray[(CurrentY - 1) * Width + CurrentX] = newType;
    				NextX = CurrentX;
    				NextY = CurrentY - 2;
    			}
    			else{
    				PathDone = true;
    			}
    		}

    		if(RanDirection == 3){// l, r, u, d
    			if(GridTypeArray[CurrentY * Width + CurrentX - 2] == oldType){
    				GridTypeArray[CurrentY * Width + CurrentX - 2] = newType;
    				GridTypeArray[CurrentY * Width + CurrentX - 1] = newType;
    				NextX = CurrentX - 2;
    				NextY = CurrentY;
    			}
    			else if(GridTypeArray[CurrentY * Width + CurrentX + 2] == oldType){
    				GridTypeArray[CurrentY * Width + CurrentX + 2] = newType;
    				GridTypeArray[CurrentY * Width + CurrentX + 1] = newType;
    				NextX = CurrentX + 2;
    				NextY = CurrentY;
    			}
    			else if(GridTypeArray[(CurrentY - 2) * Width + CurrentX] == oldType){
    				GridTypeArray[(CurrentY - 2) * Width + CurrentX] = newType;
    				GridTypeArray[(CurrentY - 1) * Width + CurrentX] = newType;
    				NextX = CurrentX;
    				NextY = CurrentY - 2;
    			}
    			else if(GridTypeArray[(CurrentY + 2) * Width + CurrentX] == oldType){
    				GridTypeArray[(CurrentY + 2) * Width + CurrentX] = newType;
    				GridTypeArray[(CurrentY + 1) * Width + CurrentX] = newType;
    				NextX = CurrentX;
    				NextY = CurrentY + 2;
    			}
    			else{
    				PathDone = true;
    			}
    		}



    		if(RanDirection == 4){// r, u, d, l
    			if(GridTypeArray[CurrentY * Width + CurrentX + 2] == oldType){
    				GridTypeArray[CurrentY * Width + CurrentX + 2] = newType;
    				GridTypeArray[CurrentY * Width + CurrentX + 1] = newType;
    				NextX = CurrentX + 2;
    				NextY = CurrentY;
    			}
    			else if(GridTypeArray[(CurrentY - 2) * Width + CurrentX] == oldType){
    				GridTypeArray[(CurrentY - 2) * Width + CurrentX] = newType;
    				GridTypeArray[(CurrentY - 1) * Width + CurrentX] = newType;
    				NextX = CurrentX;
    				NextY = CurrentY - 2;
    			}
    			else if(GridTypeArray[(CurrentY + 2) * Width + CurrentX] == oldType){
    				GridTypeArray[(CurrentY + 2) * Width + CurrentX] = newType;
    				GridTypeArray[(CurrentY + 1) * Width + CurrentX] = newType;
    				NextX = CurrentX;
    				NextY = CurrentY + 2;
    			}
    			else if(GridTypeArray[CurrentY * Width + CurrentX - 2] == oldType){
    				GridTypeArray[CurrentY * Width + CurrentX - 2] = newType;
    				GridTypeArray[CurrentY * Width + CurrentX - 1] = newType;
    				NextX = CurrentX - 2;
    				NextY = CurrentY;
    			}
    			else{
    				PathDone = true;
    			}
    		}
    		
    		CurrentX =  NextX;
    		CurrentY = NextY;

    		if(PathDone == false){
     			TempPosition++;
    		}
    	}
    	
    	
    	
    	boolean mazeDone = false;
    	int mazePathsCreated = 0;
    	while(mazeDone == false){
    		mazePathsCreated = 0;
    		for(int i = TempPosition; i >= 0; i--){
    			int MyTempArrayPosition = PathCalcArray[i].PositionY * Width + PathCalcArray[i].PositionX;
    			if(GridTypeArray[MyTempArrayPosition - (Width*2)] == oldType || GridTypeArray[MyTempArrayPosition + (Width*2)] == oldType || GridTypeArray[MyTempArrayPosition + 2] == oldType || GridTypeArray[MyTempArrayPosition -2] == oldType){
    				//GridTypeArray[MyTempArrayPosition - (Width*2)] = newType;
    				//GridTypeArray[MyTempArrayPosition - (Width)] = newType;
    				SpreadRandomPath(oldType, newType, i);
    				mazePathsCreated++;
    			}
    		}
    		if(mazePathsCreated < 1){
    			mazeDone = true;
    		}
    		
    	}
    	
    	
    	
    }

    private void SpreadRandomPath(int oldType, int newType, int ArrayStartPosition){
    	
    	
    	boolean pathDone = false;
    	int CurrentY = PathCalcArray[ArrayStartPosition].PositionY;
    	int CurrentX = PathCalcArray[ArrayStartPosition].PositionX;
    	int NextX = 0;
    	int NextY = 0;

    	while(pathDone == false){

    		GridTypeArray[(CurrentY) * Width + CurrentX] = newType;

    		PathCalcArray[TempPosition].Real = true;
    		PathCalcArray[TempPosition].PositionX = CurrentX;
    		PathCalcArray[TempPosition].PositionY = CurrentY;
    		PathCalcArray[TempPosition].ParentArrayPosition = TempPosition-1;

    		int RanDirection = RanInt(0,4) + 1;
    		
    		if(RanDirection == 1){//up first,d , l, r
    			if(GridTypeArray[(CurrentY - 2) * Width + CurrentX] == oldType){
    				GridTypeArray[(CurrentY - 2) * Width + CurrentX] = newType;
    				GridTypeArray[(CurrentY - 1) * Width + CurrentX] = newType;
    				NextX = CurrentX;
    				NextY = CurrentY - 2;
    			}
    			else if(GridTypeArray[(CurrentY + 2) * Width + CurrentX] == oldType){
    				GridTypeArray[(CurrentY + 2) * Width + CurrentX] = newType;
    				GridTypeArray[(CurrentY + 1) * Width + CurrentX] = newType;
    				NextX = CurrentX;
    				NextY = CurrentY + 2;
    			}
    			else if(GridTypeArray[CurrentY * Width + CurrentX - 2] == oldType){
    				GridTypeArray[CurrentY * Width + CurrentX - 2] = newType;
    				GridTypeArray[CurrentY * Width + CurrentX - 1] = newType;
    				NextX = CurrentX - 2;
    				NextY = CurrentY;
    			}
    			else if(GridTypeArray[CurrentY * Width + CurrentX + 2] == oldType){
    				GridTypeArray[CurrentY * Width + CurrentX + 2] = newType;
    				GridTypeArray[CurrentY * Width + CurrentX + 1] = newType;
    				NextX = CurrentX + 2;
    				NextY = CurrentY;
    			}
    			else{
    				pathDone = true;
    			}
    		}



    		if(RanDirection == 2){//d, l, r, u
    			if(GridTypeArray[(CurrentY + 2) * Width + CurrentX] == oldType){
    				GridTypeArray[(CurrentY + 2) * Width + CurrentX] = newType;
    				GridTypeArray[(CurrentY + 1) * Width + CurrentX] = newType;
    				NextX = CurrentX;
    				NextY = CurrentY + 2;
    			}
    			else if(GridTypeArray[CurrentY * Width + CurrentX - 2] == oldType){
    				GridTypeArray[CurrentY * Width + CurrentX - 2] = newType;
    				GridTypeArray[CurrentY * Width + CurrentX - 1] = newType;
    				NextX = CurrentX - 2;
    				NextY = CurrentY;
    			}
    			else if(GridTypeArray[CurrentY * Width + CurrentX + 2] == oldType){
    				GridTypeArray[CurrentY * Width + CurrentX + 2] = newType;
    				GridTypeArray[CurrentY * Width + CurrentX + 1] = newType;
    				NextX = CurrentX + 2;
    				NextY = CurrentY;
    			}
    			else if(GridTypeArray[(CurrentY - 2) * Width + CurrentX] == oldType){
    				GridTypeArray[(CurrentY - 2) * Width + CurrentX] = newType;
    				GridTypeArray[(CurrentY - 1) * Width + CurrentX] = newType;
    				NextX = CurrentX;
    				NextY = CurrentY - 2;
    			}
    			else{
    				pathDone = true;
    			}
    		}

    		if(RanDirection == 3){// l, r, u, d
    			if(GridTypeArray[CurrentY * Width + CurrentX - 2] == oldType){
    				GridTypeArray[CurrentY * Width + CurrentX - 2] = newType;
    				GridTypeArray[CurrentY * Width + CurrentX - 1] = newType;
    				NextX = CurrentX - 2;
    				NextY = CurrentY;
    			}
    			else if(GridTypeArray[CurrentY * Width + CurrentX + 2] == oldType){
    				GridTypeArray[CurrentY * Width + CurrentX + 2] = newType;
    				GridTypeArray[CurrentY * Width + CurrentX + 1] = newType;
    				NextX = CurrentX + 2;
    				NextY = CurrentY;
    			}
    			else if(GridTypeArray[(CurrentY - 2) * Width + CurrentX] == oldType){
    				GridTypeArray[(CurrentY - 2) * Width + CurrentX] = newType;
    				GridTypeArray[(CurrentY - 1) * Width + CurrentX] = newType;
    				NextX = CurrentX;
    				NextY = CurrentY - 2;
    			}
    			else if(GridTypeArray[(CurrentY + 2) * Width + CurrentX] == oldType){
    				GridTypeArray[(CurrentY + 2) * Width + CurrentX] = newType;
    				GridTypeArray[(CurrentY + 1) * Width + CurrentX] = newType;
    				NextX = CurrentX;
    				NextY = CurrentY + 2;
    			}
    			else{
    				pathDone = true;
    			}
    		}



    		if(RanDirection == 4){// r, u, d, l
    			if(GridTypeArray[CurrentY * Width + CurrentX + 2] == oldType){
    				GridTypeArray[CurrentY * Width + CurrentX + 2] = newType;
    				GridTypeArray[CurrentY * Width + CurrentX + 1] = newType;
    				NextX = CurrentX + 2;
    				NextY = CurrentY;
    			}
    			else if(GridTypeArray[(CurrentY - 2) * Width + CurrentX] == oldType){
    				GridTypeArray[(CurrentY - 2) * Width + CurrentX] = newType;
    				GridTypeArray[(CurrentY - 1) * Width + CurrentX] = newType;
    				NextX = CurrentX;
    				NextY = CurrentY - 2;
    			}
    			else if(GridTypeArray[(CurrentY + 2) * Width + CurrentX] == oldType){
    				GridTypeArray[(CurrentY + 2) * Width + CurrentX] = newType;
    				GridTypeArray[(CurrentY + 1) * Width + CurrentX] = newType;
    				NextX = CurrentX;
    				NextY = CurrentY + 2;
    			}
    			else if(GridTypeArray[CurrentY * Width + CurrentX - 2] == oldType){
    				GridTypeArray[CurrentY * Width + CurrentX - 2] = newType;
    				GridTypeArray[CurrentY * Width + CurrentX - 1] = newType;
    				NextX = CurrentX - 2;
    				NextY = CurrentY;
    			}
    			else{
    				pathDone = true;
    			}
    		}


    		CurrentX =  NextX;
    		CurrentY = NextY;

    		if(pathDone == false){
     			TempPosition++;
    		}


    	}
    	
    	
    	
    }
    
    private void StartCellSimulation(int hollowType, int SolidType, int Checktype){

    	for(int i = 0; i < Width*Height; i++){
    		if(GridTypeArray[i] == Checktype){
    			int Chance = RanInt(0,100);
    			if(Chance > 45){
    				GridTypeArray[i] = SolidType;
    			}
    			else{
    				GridTypeArray[i] = hollowType;
    			}

    		}
    	}

    }

    private void SimulationStep(int hollowType, int SolidType){
    	int DeathLimit = 4;
    	int BirthLimit = 4;

    	for(int i = 0; i < Width*Height; i++){
    		if(GridTypeArray[i] != hollowType && GridTypeArray[i] != SolidType){
    			PathCalcArray[i].ParentArrayPosition = GridTypeArray[i];
    		}
    		else{
    			int SolidNeighbours = 0;
    			if(GridTypeArray[i + 1] == SolidType){
    				SolidNeighbours++;
    			}
    			if(GridTypeArray[i - 1] == SolidType){
    				SolidNeighbours++;
    			}
    			if(GridTypeArray[i + 1 + Width] == SolidType){
    				SolidNeighbours++;
    			}
    			if(GridTypeArray[i + 1 - Width] == SolidType){
    				SolidNeighbours++;
    			}
    			if(GridTypeArray[i - 1 - Width] == SolidType){
    				SolidNeighbours++;
    			}
    			if(GridTypeArray[i - 1 + Width] == SolidType){
    				SolidNeighbours++;
    			}
    			if(GridTypeArray[i - Width] == SolidType){
    				SolidNeighbours++;
    			}
    			if(GridTypeArray[i + Width] == SolidType){
    				SolidNeighbours++;
    			}


    			if(GridTypeArray[i] == hollowType && SolidNeighbours <= BirthLimit){
    				PathCalcArray[i].ParentArrayPosition = SolidType;
    			}
    			else{
    				PathCalcArray[i].ParentArrayPosition = hollowType;
    			}
    			if(GridTypeArray[i] == SolidType && SolidNeighbours >= DeathLimit){
    				PathCalcArray[i].ParentArrayPosition = hollowType;
    			}
    			else{
    				PathCalcArray[i].ParentArrayPosition = SolidType;
    			}

    		}
    	}

    	for(int i = 0; i < Width*Height; i++){
    		GridTypeArray[i] = PathCalcArray[i].ParentArrayPosition;
    		PathCalcArray[i].ParentArrayPosition = 0;
    	}
    }
    
    private void FloodSimulation(int hollowType, int SolidType){
    	int LargestFlood = 0;
    	for(int x = 0; x < Width*Height; x++){
    		GridCollisionArray[x] = 0;
    		PathCalcArray[x].Real = false;
    	}

    	for(int i = 0; i < Width*Height; i++){
    		if(GridTypeArray[i] == hollowType && GridCollisionArray[i] == 0){
    			int FloodNumber = 0;
    			int FloodPosition = 0;

    			PathCalcArray[FloodNumber].Real = true;
    			PathCalcArray[FloodNumber].ParentArrayPosition = i;
    			GridCollisionArray[i] = 1;
    			FloodNumber++;

    			for(int f = 0; f < FloodNumber; f++){
    				if(PathCalcArray[f].Real == true){
    					int TempFloodPosition = PathCalcArray[f].ParentArrayPosition - Width;
    					//up
    					if(GridTypeArray[TempFloodPosition] == hollowType && GridCollisionArray[TempFloodPosition] == 0){
    						PathCalcArray[FloodNumber].ParentArrayPosition = TempFloodPosition;
    						PathCalcArray[FloodNumber].Real = true;
    						GridCollisionArray[TempFloodPosition] = 1;
    						FloodNumber++;
    					}
    					//down
    					TempFloodPosition = PathCalcArray[f].ParentArrayPosition + Width;
    					if(GridTypeArray[TempFloodPosition] == hollowType && GridCollisionArray[TempFloodPosition] == 0){
    						PathCalcArray[FloodNumber].ParentArrayPosition = TempFloodPosition;
    						PathCalcArray[FloodNumber].Real = true;
    						GridCollisionArray[TempFloodPosition] = 1;
    						FloodNumber++;
    					}
    					//left
    					TempFloodPosition = PathCalcArray[f].ParentArrayPosition -1;
    					if(GridTypeArray[TempFloodPosition] == hollowType && GridCollisionArray[TempFloodPosition] == 0){
    						PathCalcArray[FloodNumber].ParentArrayPosition = TempFloodPosition;
    						PathCalcArray[FloodNumber].Real = true;
    						GridCollisionArray[TempFloodPosition] = 1;
    						FloodNumber++;
    					}
    					//right
    					TempFloodPosition = PathCalcArray[f].ParentArrayPosition +1;
    					if(GridTypeArray[TempFloodPosition] == hollowType && GridCollisionArray[TempFloodPosition] == 0){
    						PathCalcArray[FloodNumber].ParentArrayPosition = TempFloodPosition;
    						PathCalcArray[FloodNumber].Real = true;
    						GridCollisionArray[TempFloodPosition] = 1;
    						FloodNumber++;
    					}
    				}
    			}


    			for(int f = 0; f < FloodNumber; f++){
    				PathCalcArray[PathCalcArray[f].ParentArrayPosition].PositionY = FloodNumber;
    				PathCalcArray[f].Real = false;
    			}

    			if(FloodNumber > LargestFlood){
    				LargestFlood = FloodNumber;
    			}
    			FloodNumber = 0;


    		}
    		
    	}


    	for(int i = 0; i < Width*Height; i++){
    		if(PathCalcArray[i].PositionY < LargestFlood && GridTypeArray[i] == hollowType){
    			GridTypeArray[i] = SolidType;
    		}
    	}
    }
    
    private void GenerateRooms(int oldType, int newType, int MaxRoomNumber, int MaxAttempts, int MaxSize){

    	int RoomsDone = 0;
    	int Attempts = 0;
    	TempPosition = 0;

    	while(RoomsDone < MaxRoomNumber && Attempts < MaxAttempts){

    		int TempMyX = 0;
    		int TempMyY = 0;

    		while(GridTypeArray[TempMyY*Width + TempMyX] != oldType){
    			TempMyX = RanInt(0, Width);
    			TempMyY = RanInt(0, Height);
    		}
    		//GridTypeArray[TempMyY*Width + TempMyX] = newType;
    		//MaxRoomNumber++;

    		int RoomWidth = RanInt(3, MaxSize);
    		int RoomHeight = RanInt(3, MaxSize);
    		boolean RoomValid = true;

    		for(int w = 0; w < RoomWidth + 2; w++){
    			for(int h = 0; h < RoomHeight + 2; h++){
    				int TempPosition = 0;

    				TempPosition = ((TempMyY*Width + TempMyX) + w + (h*Width)) - RoomWidth/2 - (RoomHeight/2)*Width - Width - 1;
    				if(TempPosition < Width* Height - 1 && TempPosition > 0){
    					if(GridTypeArray[TempPosition] != oldType){
    						RoomValid = false;
    					}
    				}
    				else{
    					RoomValid = false;
    				}

    			}
    		}

    		if(RoomValid == true){
    			for(int w = 0; w < RoomWidth; w++){
    				for(int h = 0; h < RoomHeight; h++){
    					int TempPosition = 0;
    					TempPosition = ((TempMyY*Width + TempMyX) + w + (h*Width)) - RoomWidth/2 - (RoomHeight/2)*Width;
    					GridTypeArray[TempPosition] = newType;
    				}
    			}
    			MaxRoomNumber++;
    		}


    		Attempts++;
    	}

    }

    private void RemoveDeadEnds(int oldType, int newType){

    	int DeadEndsRemoved = 1;
    	boolean Done = false;

    	while(Done == false){
    		DeadEndsRemoved = 0;

    		for(int i = 0; i < Width*Height; i++){
    			if(GridTypeArray[i] == oldType){
    				int SolidNeighbours = 0;
    				if(GridTypeArray[i + 1] == oldType){
    					SolidNeighbours++;
    				}
    				if(GridTypeArray[i - 1] == oldType){
    					SolidNeighbours++;
    				}
    				if(GridTypeArray[i - Width] == oldType){
    					SolidNeighbours++;
    				}
    				if(GridTypeArray[i + Width] == oldType){
    					SolidNeighbours++;
    				}

    				if(SolidNeighbours == 1){
    					GridTypeArray[i] = newType;
    					DeadEndsRemoved++;
    				}

    			}
    		}


    		if(DeadEndsRemoved == 0){
    			Done = true;
    		}


    	}

    }
    
    private int RanInt(int Min, int Max){
    	return Temp.nextInt(Max - Min) + Min;
    
    }
    
    private void UpdateEnemy(int delta){
    	
    	for(Enemy MyEnemy: EnemyList){
    		if(MyEnemy.Health > 0){
    			
    		int u = 0;
    		if(GridCollisionArray[(MyEnemy.PosZ * Width + MyEnemy.PosX) - Width] == 1 || GridEntityCollisionArray[(MyEnemy.PosZ * Width + MyEnemy.PosX) - Width] > 0 || (MyEnemy.PosZ * Width + MyEnemy.PosX) - Width == PlayerZ * Width + PlayerX){
    			u = 1;
    		}
    		int d = 0;
    		if(GridCollisionArray[(MyEnemy.PosZ * Width + MyEnemy.PosX) + Width] == 1 || GridEntityCollisionArray[(MyEnemy.PosZ * Width + MyEnemy.PosX) + Width] > 0 || (MyEnemy.PosZ * Width + MyEnemy.PosX) + Width == PlayerZ * Width + PlayerX){
    			d = 1;
    		}
    		int l = 0;
    		if(GridCollisionArray[(MyEnemy.PosZ * Width + MyEnemy.PosX) - 1] == 1 || GridEntityCollisionArray[(MyEnemy.PosZ * Width + MyEnemy.PosX) - 1] > 0  || (MyEnemy.PosZ * Width + MyEnemy.PosX) - 1 == PlayerZ * Width + PlayerX){
    			l = 1;
    		}
    		int r = 0;
    		if(GridCollisionArray[(MyEnemy.PosZ * Width + MyEnemy.PosX) + 1] == 1 || GridEntityCollisionArray[(MyEnemy.PosZ * Width + MyEnemy.PosX) + 1] > 0 || (MyEnemy.PosZ * Width + MyEnemy.PosX) + 1 == PlayerZ * Width + PlayerX){
    			r = 1;
    		}
    		
    		if(GridSpecialWallArray[MyEnemy.PosZ * Width + MyEnemy.PosX] == 1 || GridSpecialWallArray[MyEnemy.PosZ * Width + MyEnemy.PosX] == 3){
    			d = 2;
    		}
    		if(GridSpecialWallArray[MyEnemy.PosZ * Width + MyEnemy.PosX - Width] == 1 || GridSpecialWallArray[MyEnemy.PosZ * Width + MyEnemy.PosX - Width] == 3){
    			u = 2;
    		}
    		if(GridSpecialWallArray[MyEnemy.PosZ * Width + MyEnemy.PosX] == 2 || GridSpecialWallArray[MyEnemy.PosZ * Width + MyEnemy.PosX] == 3){
    			r = 2;
    		}
    		if(GridSpecialWallArray[MyEnemy.PosZ * Width + MyEnemy.PosX - 1] == 2 || GridSpecialWallArray[MyEnemy.PosZ * Width + MyEnemy.PosX - 1] == 3){
    			l = 2;
    		}
    		
    		MyEnemy.UpdateSurroundings(u, d, l, r);
    		if(playerMoved == true){
    			MyEnemy.PlayerMoved();
    		}
    		
    		
    		if(MyEnemy.NeedPath == true){
    			CalculatePath(MyEnemy);
    		}
    		
    		
    		MyEnemy.update(delta, PlayerX, PlayerZ);
    		
    		if(MyEnemy.PlayerHit == true){
    			MyEnemy.PlayerHit = false;
    			int hitChance = RanInt(0,100);
				if(hitChance > PlayerInv.Evasiveness * 0.5f + PlayerInv.baseEvasiness){
					int Damage = (int) ((MyEnemy.enemyStrength - PlayerDefense)  * (1 - (PlayerInv.ProtectionAmount/20.0f)));
					if(Damage < 0){Damage = 0;}
					PlayerHealth -= Damage;
					String TempString = "Took " + Damage + " damage from " + MyEnemy.EnemyName +".";
					AddMessage(TempString);
					OnDamageStatus(MyEnemy);
				}
				else{
					String TempString = "Dodged attack from " + MyEnemy.EnemyName +".";
					AddMessage(TempString);
				}
    		}
    		EnemyStatusDamage(MyEnemy);
    		if(DamageUpdate == true){
    			DamageCheckEnemy(MyEnemy);
    		}
    		
    		for(LogicObject MyLogic: LogicObjectList){
				if(MyLogic instanceof LogicActivator){
					if(MyLogic.GetData() == 2 || MyLogic.GetData() == 4){
						if(MyEnemy.PosX == MyLogic.PositionX && MyEnemy.PosZ == MyLogic.PositionZ){
							MyLogic.Powered = true;
							PowerGrid(MyLogic);
						}
					}
				}
			}
    		BuildEntityCollision();
    		}
    	
    	}
    	
    	playerMoved = false;
    }
    
	private void UpdateChest(int delta){
    	
    	for(Chest MyChest: ChestList){
    		MyChest.Update(delta);
    	}
    	
    }
	
	private void UpdateDoor(int delta){
    	
    	for(Door MyDoor: DoorList){
    		MyDoor.Update(delta);
    		
    		if(MyDoor.PosX == PlayerX && MyDoor.PosZ == PlayerZ){
    			MyDoor.Open = true;
    		}
    		if(GridEntityCollisionArray[MyDoor.PosZ * Width + MyDoor.PosX] == 1){
    			MyDoor.Open = true;
    		}
    		
    		if(MyDoor.NeedsPower == true){
    			int i = MyDoor.PosX + MyDoor.PosZ*32;
    			if(FinalGridPowerArray[i] > 0){
    				MyDoor.Open = true;
    			}
    			else{
    				MyDoor.Open = false;
    			}
    		}
    	}
    	
    }
    
	private void UpdateLogic(int delta){
		for(LogicObject MyLogic: LogicObjectList){
			MyLogic.Update(delta);
			MyLogic.ActivateLogicObject(FinalGridPowerArray[MyLogic.PositionX + MyLogic.PositionZ * 32]);
			if(MyLogic instanceof LogicTeleporter){
				CheckTeleporters(MyLogic);
			}
			if(MyLogic instanceof LogicFlipFlop || MyLogic instanceof LogicTripleOutput || MyLogic instanceof LogicMultiInput || MyLogic instanceof LogicPushOnce || MyLogic instanceof LogicPushOnce || MyLogic instanceof LogicPoweredPulse || MyLogic instanceof LogicDelayedPulse){
				PowerGrid(MyLogic);
			}
			
		}
	}
	
	private void UpdateObjects(int delta){
	    	for(DungeonObject MyObject: DungeonObjectList){
	    		MyObject.Update(delta);
	    		MyObject.PowerObject(FinalGridPowerArray[(int) (MyObject.PosX + MyObject.PosZ * 32)]);
	    		if(MyObject instanceof PushObject){
	    			for(LogicObject MyLogic: LogicObjectList){
	    				if(MyLogic instanceof LogicActivator){
	    					if(MyLogic.GetData() == 3){
	    						if(MyObject.PosX == MyLogic.PositionX && MyObject.PosZ == MyLogic.PositionZ){
	    							MyLogic.Powered = true;
	    							PowerGrid(MyLogic);
	    						}
	    					}
	    				}
	    			}
	    		}
	    		if(MyObject.GetPowered() == true){
	    			GridPowerArray[MyObject.GetPosition()]++;
	    		}
	    	}
	    }
	
    private void RenderEnemy(){
    	
    	for(Enemy MyEnemy: EnemyList){
    		if(MyEnemy.Health > 0){
    			MyEnemy.DrawEnemy();
    		}
    		
    	}
    }
    
    private void RenderDoor(){
    	for(Door MyDoor: DoorList){
    		MyDoor.RenderChest();
    	}
    }
    
    private void RenderChest(){
    	
    	for(Chest MyChest: ChestList){
    		MyChest.RenderChest();
    	}
    
    }

    private void RenderPlainObjects(){
    	for(DungeonObject MyObject: DungeonObjectList){
    		if(MyObject instanceof SpikeObject){
    			MyObject.RenderObject();
    		}
    		if(MyObject instanceof PressurePad){
    			MyObject.RenderObject();
    		}
    		if(MyObject instanceof TreeObject){
    			MyObject.RenderObject();
    		}
    		if(MyObject instanceof Button){
    			MyObject.RenderObject();
    		}
    		if(MyObject instanceof FakeWallObject){
    			MyObject.RenderObject();
    		}
    		if(MyObject instanceof LeverObject){
    			MyObject.RenderObject();
    		}
    		if(MyObject instanceof SignObject){
    			MyObject.RenderObject();
    		}
    	}
    }
    
    private void RenderObjects(){
    	for(DungeonObject MyObject: DungeonObjectList){
    		if(MyObject instanceof CrystalCluster){
    			MyObject.RenderObject();
    		}
    		if(MyObject instanceof PushObject){
    			MyObject.RenderObject();
    		}
    	}
    	
    	for(DungeonObject MyObject: DungeonObjectList){
    		if(MyObject instanceof ForceFieldObject){
    			//System.out.println("draw forcefield");
    			MyObject.RenderObject();
    		}
    		if(MyObject instanceof TeleporterField){
    			MyObject.RenderObject();
    		}
    	}
    }
    
    private void RenderObjects2D(){
    	for(DungeonObject MyObject: DungeonObjectList){
    		MyObject.Render2DObject();
    	}
    }  
    
   private void RenderEnemyDepth(){
    	
    	for(Enemy MyEnemy: EnemyList){
    		if(MyEnemy.Health > 0){
    			MyEnemy.DrawEnemyDepth();
    		}
    		
    	}
    }
    
   private void RenderChestDepth(){
    	
    	for(Chest MyChest: ChestList){
    		MyChest.RenderChestDepth();
    	}
    
    }

   private void RenderDoorDepth(){
   	for(Door MyDoor: DoorList){
   		MyDoor.RenderChestDepth();
   	}
   }
   
   private void RenderObjectsDepth(){
   	for(DungeonObject MyObject: DungeonObjectList){
   		MyObject.RenderObjectDepth();
   	}
   }
   
    private void PopulateEnemy(int spawnType, int chance){
    	
    	for(int h = 0; h < Height; h++){
    		for(int w = 0; w < Height; w++){
    		
    			if(GridTypeArray[h*Width + w] == spawnType && (w != PlayerX && h != PlayerZ)){
    				int MyChance = RanInt(0,100);
    				if(MyChance >100 - chance){
    					Enemy TempEnemy = new Enemy("Bug", "bug", w, 0, h, GameData.GameScale, FloorNumber);
    					EnemyList.add(TempEnemy);
    				}
    				
    			}
    			
    		}
    	}
    	
    }

    private void PopulateChests(int spawnType, int NumChests){
    	
    	int TempX[] = new int[Width*Height];
    	int TempZ[] = new int[Width*Height];
    	int DeadEndNumber = 0;
    	//count and remember the dead ends
    	for(int h = 0; h < Height; h++){
    		for(int w = 0; w < Width; w++){
    			
    		int i = h * Width + w;
    			
    		TempX[i] = 0;
    		TempZ[i] = 0;
    		
    		if(GridTypeArray[i] == spawnType){
    			int Edges = 0;
    			if(GridTypeArray[i - Width] == 1){
    				Edges++;
    			}
    			if(GridTypeArray[i + Width] == 1){
    				Edges++;
    			}
    			if(GridTypeArray[i - 1] == 1){
    				Edges++;
    			}
    			if(GridTypeArray[i + 1] == 1){
    				Edges++;
    			}
    			
    			if(Edges == 3){
    				TempX[DeadEndNumber] = w;
    				TempZ[DeadEndNumber] = h;
    				DeadEndNumber++;
    			}	
    		}
    		}
    	}
    	
    	//System.out.println(NumChests);
    	if(DeadEndNumber < NumChests){
    		for(int c = 0; c < DeadEndNumber; c++){
    			
    			int i = TempZ[c] * Width + TempX[c];
				int rotation = 0;
				if(GridTypeArray[i - Width] != 1){rotation = 1;}
				if(GridTypeArray[i + Width] != 1){rotation = 3;}
				if(GridTypeArray[i - 1] != 1){rotation = 2;}
				if(GridTypeArray[i + 1] != 1){rotation = 4;}
    			
    			Chest TempChest = new Chest("ChestBase", "chest", TempX[c], 0, TempZ[c], GameData.GameScale, rotation, FloorNumber);
    			ChestList.add(TempChest);
    		}
    		NumChests -= DeadEndNumber;
    	}
    	else{
    		int MyAttempts = 200;
    		while(NumChests > 0 && MyAttempts > 0){
    			int CheckPosition = RanInt(0, DeadEndNumber) + 1;
    			if(TempX[CheckPosition] > 0){
    				int i = TempZ[CheckPosition] * Width + TempX[CheckPosition];
    				int rotation = 0;
    				if(GridTypeArray[i - Width] != 1){rotation = 1;}
    				if(GridTypeArray[i + Width] != 1){rotation = 3;}
    				if(GridTypeArray[i - 1] != 1){rotation = 2;}
    				if(GridTypeArray[i + 1] != 1){rotation = 4;}
    				
    				
    				Chest TempChest = new Chest("ChestBase", "chest", TempX[CheckPosition], 0, TempZ[CheckPosition], GameData.GameScale, rotation, FloorNumber);
        			ChestList.add(TempChest);
        			NumChests--;
        			TempX[CheckPosition] = 0;
    			}
    			MyAttempts --;
    		}
    	}
    	
    	//leftover chests
    	int Attempts = 500;
    	if(NumChests > 0 && Attempts  > 0){
    		while(NumChests > 0){
    			int MyX = RanInt(0, Width);
    			int MyZ = RanInt(0, Height);
    			
    			if(GridTypeArray[MyZ * Width + MyX] == spawnType){
    				int u = GridTypeArray[MyZ * Width + MyX - Width];
    				int ul = GridTypeArray[MyZ * Width + MyX - Width - 1];
    				int ur = GridTypeArray[MyZ * Width + MyX - Width + 1];
    				int d = GridTypeArray[MyZ * Width + MyX + Width];
    				int dl = GridTypeArray[MyZ * Width + MyX + Width - 1];
    				int dr = GridTypeArray[MyZ * Width + MyX + Width + 1];
    				int l = GridTypeArray[MyZ * Width + MyX - 1];
    				int r = GridTypeArray[MyZ * Width + MyX + 1];
    				boolean allowed = false;
    				int rotation = 0;
    				
    				//top check
    				if(u != 1 && d == 1){
    					if(ul != 1 && ur != 1){
    						allowed = true;
    						rotation = 1;
    					}
    				}
    				//down check
    				if(u == 1 && d != 1){
    					if(dl != 1 && dr != 1){
    						allowed = true;
    						rotation = 3;
    					}
    				}
    				//left check
    				if(r == 1 && l != 1){
    					if(ul != 1 && dl!= 1){
    						allowed = true;
    						rotation = 2;
    					}
    				}
    				//right check
    				if(l == 1 && r != 1){
    					if(ur != 1 && dr != 1){
    						allowed = true;
    						rotation = 4;
    					}
    				}
    				if(allowed == true){
    					Chest TempChest = new Chest("ChestBase", "chest", MyX, 0, MyZ, GameData.GameScale, rotation, FloorNumber);
    					ChestList.add(TempChest);
    					NumChests--;
    				}
    				
    			}
    			Attempts--;
    		}
    	}
    	
    }
    
    private void PopulateDoor(int spawnType){
    	
    	for(int h = 0; h < Height; h++){
    		for(int w = 0; w < Height; w++){
    		
    			if(GridTypeArray[h*Width + w] == spawnType && (w != PlayerX && h != PlayerZ) && GridEntityCollisionArray[h*Width + w] != 1){
    				
    				int u = GridTypeArray[h*Width + w - Width];
    				int d = GridTypeArray[h*Width + w + Width];
    				int l = GridTypeArray[h*Width + w - 1];
    				int r = GridTypeArray[h*Width + w + 1];
    				
    				int ul = GridTypeArray[h*Width + w - Width - 1];
    				int dl = GridTypeArray[h*Width + w + Width - 1];
    				int ur = GridTypeArray[h*Width + w + 1 - Width];
    				int dr = GridTypeArray[h*Width + w + 1 + Width];
    				
    				int ddu = GridTypeArray[h*Width + w - Width - Width];
    				int ddd = GridTypeArray[h*Width + w + Width + Width];
    				int ddl = GridTypeArray[h*Width + w - 2];
    				int ddr = GridTypeArray[h*Width + w + 2];
    				
    				boolean doordone = false;
    				
    				if(u == 1 && d == 1 && l != 1 && r != 1){	
    					if((ul != 1 && dl != 1) || (ur != 1 && dr != 1)){
    						if(doordone == false){
    							Door TempDoor = new Door("door", "Temple", w, 0, h, GameData.GameScale, 1);
    							DoorList.add(TempDoor);
    							doordone = true;
    							GridTypeArray[h*Width + w] = 10;
    						}
    					}
    					if(ddl != 1 && (ul != 1 || dl != 1)){
    						if(doordone == false){
    							Door TempDoor = new Door("door", "Temple", w, 0, h, GameData.GameScale, 1);
    							DoorList.add(TempDoor);
    							doordone = true;
    							GridTypeArray[h*Width + w] = 10;
    						}
    					}
    					if(ddr != 1 && (ur != 1 || dr != 1)){
    						if(doordone == false){
    							Door TempDoor = new Door("door", "Temple", w, 0, h, GameData.GameScale, 1);
    							DoorList.add(TempDoor);
    							doordone = true;
    							GridTypeArray[h*Width + w] = 10;
    						}
    					}
    				}
    				
    				if(r == 1 && l == 1 && d != 1 && u != 1){
    					if((ul != 1 && ur != 1) || (dl != 1 && dr != 1)){
    						if(doordone == false){
    							Door TempDoor = new Door("door", "Temple", w, 0, h, GameData.GameScale, 2);
    							DoorList.add(TempDoor);
    							doordone = true;
    							GridTypeArray[h*Width + w] = 10;
    						}
    					}
    					if(ddu != 1 && (ul != 1 || ur != 1)){
    						if(doordone == false){
    							Door TempDoor = new Door("door", "Temple", w, 0, h, GameData.GameScale, 2);
    							DoorList.add(TempDoor);
    							doordone = true;
    							GridTypeArray[h*Width + w] = 10;
    						}
    					}
    					if(ddd != 1 && (dl != 1 || dr != 1)){
    						if(doordone == false){
    							Door TempDoor = new Door("door", "Temple", w, 0, h, GameData.GameScale, 2);
    							DoorList.add(TempDoor);
    							doordone = true;
    							GridTypeArray[h*Width + w] = 10;
    						}
    					}
    				}
    			}
    			
    		}
    	}
    	
    }
    
    public void PopulateObjects(){
    	//System.out.println("Populate Objects");
    	for(int h = 0; h < Height; h++){
    		for(int w = 0; w < Height; w++){
    			if(GameData.CustomDungeon == false){
    				if(GridTypeArray[h * Width + w] == 4){
    					DungeonObject TempObject = new SpikeObject("Spikes", "Temple", w, 0, h, DisplayObjectType.TexturedObject, GameData.GameScale, 0.5f);
    					DungeonObjectList.add(TempObject);
    				}
    			}
    			
    			if(GridTypeArray[h * Width + w] == 5){
    				DungeonObject TempObject = new CrystalCluster("CrystalCluster", "black", w, 0, h, DisplayObjectType.GlowObject, GameData.GameScale, 0.001f);
    				DungeonObjectList.add(TempObject);
    			}
    		}
    	}
    }
    
    private void BuildEntityCollision(){
    	
    	for(int i = 0; i < Width*Height; i++){
    		GridEntityCollisionArray[i] = 0;
    	}
    	
    	
    	for(Enemy MyEnemy: EnemyList){
    		if(MyEnemy.Health > 0){
    			GridEntityCollisionArray[(MyEnemy.PosZ * Width + MyEnemy.PosX)] = 1;
    		}
    	}
    	
    	for(Chest MyChest: ChestList){
    		//if(MyEnemy.Health > 0){
    			GridEntityCollisionArray[(MyChest.PosZ * Width + MyChest.PosX)] = 3;
    		//}
    	}
    	
    	for(Door MyDoor: DoorList){
    		if(MyDoor.GetCollidable() == true){
    			GridEntityCollisionArray[(MyDoor.PosZ * Width + MyDoor.PosX)] = 100;
    		}
    	}
    	
    	for(DungeonObject MyObject: DungeonObjectList){
    		if(MyObject.GetCollidable() == true){
    			GridEntityCollisionArray[(int) (MyObject.PosZ * Width + MyObject.PosX)] = 4;
    			//System.out.println("Sup");
    		}
    	}
    	
    }
    
    private void BuildGridPower(){
    	for(int i = 0; i < Width*Height; i++){
    		FinalGridPowerArray[i] = GridPowerArray[i];
    		GridPowerArray[i] = 0;
    	}
    }
    
    private void BuildDamageArray(){
    	for(int i = 0; i < Width*Height; i++){
    		GridDamageArray[i] = 0;
    	}
    	
    	for(DungeonObject MyObject: DungeonObjectList){
    			GridDamageArray[(int) (MyObject.PosZ * Width + MyObject.PosX)] = MyObject.GetDamage();
    			//System.out.println(MyObject.GetDamage());
    	}
    }
    
    private void DamageCheck(){
    	//System.out.println("yo");
    	if(GridDamageArray[PlayerZ * Width + PlayerX] == 1){
    		int Damage = (int) (20 * (1 - (PlayerInv.ProtectionAmount/20.0f)));
    		PlayerHealth -= Damage;
    		String TempMessage = "You took " + Damage +  " damage from spike trap.";
    		AddMessage(TempMessage);
    	}
    }
    
    private void DamageCheckEnemy(Enemy MyEnemy){
    	if(GridDamageArray[MyEnemy.PosZ * Width + MyEnemy.PosX] == 1 && MyEnemy.OffsetX < 0.7f && MyEnemy.OffsetZ < 0.7f){
    		MyEnemy.Health -= 20;
    		String TempMessage = MyEnemy.EnemyName + " 20 damage from spike trap.";
    		AddMessage(TempMessage);
    	}
    	
    	if(MyEnemy.GetHealth() <= 0){
    		String TempMessage = MyEnemy.EnemyName + " died.";
    		AddMessage(TempMessage);
    	}
    }
    
    private void CalculatePath(Enemy TempEnemy){
    	
    	 //Enemy TempEnemy;
    	 //TempEnemy = EnemyList.get(e);
    	
    	if(TempEnemy.GetHealth()>0){
    	for(int x = 0; x < Width*Height; x++){
    		PathCalcArray[x].Real = false;
    		PathCalcArray[x].ParentArrayPosition = 0;
    		PathCalcArray[x].PositionX = 0;
    		PathCalcArray[x].PositionY = 0;
    		PathChecked[x] = 0;
    	}

    	int playerPositionX = PlayerX;
    	int playerPositionY = PlayerZ;
    	//int PathCalcState = 0; //0 - calculation, 1 - found, 2 - given up
    	//int StartPos = EnemyPositionY * width + EnemyPositionX;


    	//BFS pathfinding
    	//we start with a point on our grid, and search all 4 squares around it, we then search around those squares and so on and so forth
    	//so our start position is the position of our enemy
    	//and the goal is the position of our player

    	//first of all, lets add our start position to the queue
    	PathCalcArray[0].ParentArrayPosition = 0;
    	PathCalcArray[0].PositionX = TempEnemy.PosX;
    	PathCalcArray[0].PositionY = TempEnemy.PosZ;
    	PathCalcArray[0].Real = true;
    	PathChecked[TempEnemy.PosZ * Width + TempEnemy.PosX] = 1;


    	int CurrentQueuePosition = 0;
    	int NumberinQueue = 1;
    	TempPosition = 0;
    	int PathCalcState = 0;

    	int FinalArrayPosition = 0;

    	while(PathCalcState == 0){

    		TempPosition = PathCalcArray[CurrentQueuePosition].PositionY * Width + PathCalcArray[CurrentQueuePosition].PositionX;
    		if(PathCalcArray[CurrentQueuePosition].Real == true){
    		//above current position
    		if(GridTypeArray[TempPosition - Width] != 1 && GridTypeArray[TempPosition - Width] != 0 && GridSpecialWallArray[(TempPosition - Width)] != 1 && GridSpecialWallArray[(TempPosition - Width)] != 3){//we make sure above our current position isn't collidable
    			if(PathChecked[TempPosition - Width] == 0){//we make sure it hasn't already been added

    				PathCalcArray[NumberinQueue].ParentArrayPosition = CurrentQueuePosition;
    				PathCalcArray[NumberinQueue].PositionX = PathCalcArray[CurrentQueuePosition].PositionX;
    				PathCalcArray[NumberinQueue].PositionY = PathCalcArray[CurrentQueuePosition].PositionY - 1;
    				PathCalcArray[NumberinQueue].Real = true;
    				PathChecked[TempPosition - Width] = 1;

    				if((TempPosition - Width) == (playerPositionY * Width + playerPositionX)){
    					PathCalcState = 1;
    					FinalArrayPosition = NumberinQueue;
    				}

    				NumberinQueue++;
    			}
    		}

    		//below current position
    		if(GridTypeArray[TempPosition + Width] != 1 && GridTypeArray[TempPosition + Width] != 0 && GridSpecialWallArray[(TempPosition)] != 1 && GridSpecialWallArray[(TempPosition)] != 3){//we make sure above our current position isn't collidable
    			if(PathChecked[TempPosition + Width] == 0){//we make sure it hasn't already been added

    				PathCalcArray[NumberinQueue].ParentArrayPosition = CurrentQueuePosition;
    				PathCalcArray[NumberinQueue].PositionX = PathCalcArray[CurrentQueuePosition].PositionX;
    				PathCalcArray[NumberinQueue].PositionY = PathCalcArray[CurrentQueuePosition].PositionY + 1;
    				PathCalcArray[NumberinQueue].Real = true;
    				PathChecked[TempPosition + Width] = 1;

    				if((TempPosition + Width) == (playerPositionY * Width + playerPositionX)){
    					PathCalcState = 1;
    					FinalArrayPosition = NumberinQueue;
    				}

    				NumberinQueue++;
    			}
    		}

    		//left current position
    		if(GridTypeArray[TempPosition - 1] != 1 && GridTypeArray[TempPosition - 1] != 0 && GridSpecialWallArray[(TempPosition - 1)] != 2 && GridSpecialWallArray[(TempPosition - 1)] != 3){//we make sure above our current position isn't collidable
    			if(PathChecked[TempPosition - 1] == 0){//we make sure it hasn't already been added

    				PathCalcArray[NumberinQueue].ParentArrayPosition = CurrentQueuePosition;
    				PathCalcArray[NumberinQueue].PositionX = PathCalcArray[CurrentQueuePosition].PositionX - 1;
    				PathCalcArray[NumberinQueue].PositionY = PathCalcArray[CurrentQueuePosition].PositionY;
    				PathCalcArray[NumberinQueue].Real = true;
    				PathChecked[TempPosition - 1] = 1;

    				if((TempPosition - 1) == (playerPositionY * Width + playerPositionX)){
    					PathCalcState = 1;
    					FinalArrayPosition = NumberinQueue;
    				}

    				NumberinQueue++;
    			}
    		}

    		//right current position
    		if(GridTypeArray[TempPosition + 1] != 1  && GridTypeArray[TempPosition + 1] != 0 && GridSpecialWallArray[(TempPosition)] != 2 && GridSpecialWallArray[(TempPosition)] != 3){//we make sure above our current position isn't collidable
    			if(PathChecked[TempPosition + 1] == 0){//we make sure it hasn't already been added

    				PathCalcArray[NumberinQueue].ParentArrayPosition = CurrentQueuePosition;
    				PathCalcArray[NumberinQueue].PositionX = PathCalcArray[CurrentQueuePosition].PositionX + 1;
    				PathCalcArray[NumberinQueue].PositionY = PathCalcArray[CurrentQueuePosition].PositionY;
    				PathCalcArray[NumberinQueue].Real = true;
    				PathChecked[TempPosition + 1] = 1;

    				if((TempPosition +1) == (playerPositionY * Width + playerPositionX)){
    					PathCalcState = 1;
    					FinalArrayPosition = NumberinQueue;
    				}

    				NumberinQueue++;
    			}
    		}
    		}
    		else{
    			PathCalcState = 2;
    		}

    		CurrentQueuePosition++;
    	}

    	if(PathCalcState == 1){

    	//with the pathfinding working correctly
    	//we now need to pass the path to the enemy
    	//we start at the our finalarrayposition and work our way back through the array following the parent array positions

    	//to start with, lets find the length of our path
    	int CurrentSearchPosition = FinalArrayPosition;
    	int Length = 0;
    	while (CurrentSearchPosition > 0){ //now we loop through our path to calculate the length of of the path
    		CurrentSearchPosition = PathCalcArray[CurrentSearchPosition].ParentArrayPosition;
    		Length++;
    	}
    	TempEnemy.SetPathLength(Length); //now we tell the enemy how long its path is so we have an array thr right size for the path
    	//we misnused 1 from the length as we dont want the enmy going to the same position as the player, only up to an adjoining position

    	CurrentSearchPosition = FinalArrayPosition;//reset our starting position for our loop

    	while (CurrentSearchPosition > 1){ //now we loop through our path again this time passing the positions into the enemies path array
    		//System.out.println(CurrentSearchPosition + " " + Length);
    		Length--;
    		CurrentSearchPosition = PathCalcArray[CurrentSearchPosition].ParentArrayPosition;
    		TempEnemy.AddPathPoint(Length-1, PathCalcArray[CurrentSearchPosition].PositionX, PathCalcArray[CurrentSearchPosition].PositionY);
    	}

    	TempEnemy.UpdatePath();
    	}
    	else{
    		TempEnemy.NoPath = true;
    	}
    	}
    }

    public void AddMessage(String NewMessage){
    	
    	GridMessageData TempMessage = new GridMessageData(NewMessage);
    	MessageList.add(TempMessage);
    	
    }

    private void UpdateMessages(int delta){
    	
    	for(GridMessageData MyMessage: MessageList){
    		if(MyMessage.alphatimer <= 0){
    			MessageRemoveList.add(MyMessage);
    		}
    	}
    	
    	for(GridMessageData MyMessage: MessageRemoveList){
    		if(MyMessage.alphatimer <= 0){
    			MessageList.remove(MyMessage);
    		}
    	}
    	MessageRemoveList.clear();
    	
    	
    	if(MessageList.size() > 0){
    		if(MessageList.size() > 8){
    			MessageList.get(0).Update(delta);
    		}
    		if(MessageList.size() > 16){
    			MessageList.get(0).Update(delta);
    		}
    		if(MessageList.size() > 32){
    			MessageList.get(0).Update(delta);
    		}
    		if(MessageList.size() > 64){
    			MessageList.get(0).Update(delta);
    		}
    		MessageList.get(0).Update(delta);
    	}
    }

    private void AddDots(int spawnType, int newType, int chance){
    	for(int i = 0; i < Width * Height; i++){
    		if(GridTypeArray[i] == spawnType){
    			int mychance = RanInt(0,100);
    			if(mychance < chance){
    				GridTypeArray[i] = newType;
    			}
    		}
    	}
    }
    
    public void SetPlayerStats(float ph, float pmh, int ps, int pd, int pp){
    	PlayerHealth = ph;
    	PlayerMaxHealth = pmh;
    	PlayerStrength = ps;
    	PlayerDefense = pd;
    	Potions = pp;
    }

    private void CheckConsole(){
		if(GameConsole.GetAwaiting() == true){
			//System.out.println(GameConsole.GetMessage());
			if(GameConsole.GetMessage().equals("killall")){
				EnemyList.clear();
				GameConsole.MessageRecieved();
			}
			if(GameConsole.GetMessage().equals("player invuln on")){
				Invuln = true;
				GameConsole.MessageRecieved();
			}
			if(GameConsole.GetMessage().equals("player invuln off")){
				Invuln = false;
				GameConsole.MessageRecieved();
			}
			if(GameConsole.GetMessage().equals("regen level")){
				NeedReload = true;
				GameConsole.MessageRecieved();
			}
			
			if(GameConsole.GetMessage().equals("give item")){
				Item TempItem = new Item(FloorNumber);
				if(TempItem.MyType == ItemType.Sword){
					TempItem = new ItemWeapon(FloorNumber);
				}
				PlayerInv.AddItem(TempItem);
				GameConsole.MessageRecieved();
			}
			
			if(GameConsole.GetMessage().equals("give many item")){
				for(int i = 0; i < 120; i++){
					Item TempItem = new Item(FloorNumber);
					if(TempItem.MyType == ItemType.Sword){
						TempItem = new ItemWeapon(FloorNumber);
					}
					PlayerInv.AddItem(TempItem);
				}
				GameConsole.MessageRecieved();
			}
			
			if(GameConsole.GetMessage().equals("give many potion")){
				Potions = 99;
				GameConsole.MessageRecieved();
			}
			
			if(GameConsole.GetMessage().equals("give potion")){
				Potions ++;
				GameConsole.MessageRecieved();
			}

		}
	}
	
    private void DebugLogic(){
    	if(Invuln == true){
    		PlayerHealth = PlayerMaxHealth;
    	}
    }

    public void SetInventory(Inventory MyInventory){
    	PlayerInv = MyInventory;
    }
    
    private int CountType(int countType){
    	int total = 0;
    	
    	for(int i = 0; i < Width * Height; i++){
    		if(GridTypeArray[i] == countType){
    			total++;
    		}
    	}
    	
    	return total;
    }

	private void OnHitStatus(Enemy MyEnemy){
		for(StatusEffects MyEffect: PlayerInv.StatusEffectList){
			if(MyEffect.MyCondition == StatusEffectCondition.OnHit){
				if(MyEnemy.InflictCondition(MyEffect.MyStatusType, StatusEffectCondition.Permanent) == true){
					String TempString = "You Inflicted " + MyEffect.MyStatusType.toString() +" on " + MyEnemy.EnemyName + ".";
					AddMessage(TempString);
				}
			}
		}
	}
	
	private void OnDamageStatus(Enemy MyEnemy){
		for(StatusEffects MyEffect: PlayerInv.StatusEffectList){
			if(MyEffect.MyCondition == StatusEffectCondition.OnDamage){
				if(MyEnemy.InflictCondition(MyEffect.MyStatusType, StatusEffectCondition.Permanent) == true){
					String TempString = MyEnemy.EnemyName + " was inflicted with " +  MyEffect.MyStatusType.toString() + ".";
					AddMessage(TempString);
				}
			}
		}
	}

	private void EnemyStatusDamage(Enemy MyEnemy){
		if(MyEnemy.StatusTimer == 1 && MyEnemy.Health > 0){
			//thorns
			if(PlayerInv.ThornsAmount > 0){
				if(MyEnemy.PosX - PlayerX > -2 && MyEnemy.PosX - PlayerX <2){
					if(MyEnemy.PosZ - PlayerZ > -2 && MyEnemy.PosZ - PlayerZ < 2){
						int Damage = PlayerInv.ThornsAmount + 1 * PlayerInv.ConditionDamage;
						MyEnemy.Health -= Damage;
						String TempString = MyEnemy.EnemyName + " suffers " + Damage + " thorns damage.";
						AddMessage(TempString);
						MyEnemy.StatusTimer = 0;
					}
				}
			}
			
			
		for(StatusEffects MyEffect: MyEnemy.StatusEffectList){
			if(MyEffect.MyStatusType == StatusEffectType.Burning && MyEffect.MyCondition == StatusEffectCondition.Permanent){
				int Damage = 2 + 1 * PlayerInv.ConditionDamage;
				MyEnemy.Health -= Damage;
				String TempString = MyEnemy.EnemyName + " suffers " + Damage + " " +  MyEffect.MyStatusType.toString() + " damage.";
				AddMessage(TempString);
				MyEnemy.StatusTimer = 0;
			}
			if(MyEffect.MyStatusType == StatusEffectType.Cold && MyEffect.MyCondition == StatusEffectCondition.Permanent){
				int Damage = 2 + 1 * PlayerInv.ConditionDamage;
				MyEnemy.Health -= Damage;
				String TempString = MyEnemy.EnemyName + " suffers " + Damage + " " +  MyEffect.MyStatusType.toString() + " damage.";
				AddMessage(TempString);
				MyEnemy.StatusTimer = 0;
			}
			if(MyEffect.MyStatusType == StatusEffectType.Poison && MyEffect.MyCondition == StatusEffectCondition.Permanent){
				int Damage = 2 + 1 * PlayerInv.ConditionDamage;
				MyEnemy.Health -= Damage;
				String TempString = MyEnemy.EnemyName + " suffers " + Damage + " " +  MyEffect.MyStatusType.toString() + " damage.";
				AddMessage(TempString);
				MyEnemy.StatusTimer = 0;
			}
			
		}
			if(MyEnemy.Health <= 0){
				String TempString = MyEnemy.EnemyName + " died.";
				AddMessage(TempString);
			}
		}
	}

	public void KillAll(){
		EnemyList.clear();
	}
	
	private void PowerGrid(LogicObject MyLogic){
		if(MyLogic.IsPowered()){
			GridPowerArray[MyLogic.GetPowered()[0]]++;
			//System.out.println("Powered: " + MyLogic.GetPowered()[0]);
			GridPowerArray[MyLogic.GetPowered()[1]]++;
			//System.out.println("Powered: " + MyLogic.GetPowered()[1]);
			GridPowerArray[MyLogic.GetPowered()[2]]++;
			//System.out.println("Powered: " + MyLogic.GetPowered()[2]);
		}
	}
	
	private void PlayerStatus(){
		StatusTimer = 0;
		if(PlayerInv.ColdAmount > 0){
			int Damage = (int) (PlayerInv.ColdAmount);
			String TempString ="You suffered " + Damage + " Cold Damage.";
			AddMessage(TempString);
		}
		if(PlayerInv.TormentAmount > 0){
			int Damage = (int) (2 * PlayerInv.TormentAmount);
			String TempString ="You suffered " + Damage + " Torment Damage.";
			AddMessage(TempString);
		}
	}

	public void SlowlyRotate(int delta){
		GameData.CameraRotation.y += 0.002 * delta;
		GameData.CameraPosition.x = -(PlayerX * TileSize) - TileSize/2;
		GameData.CameraPosition.z = -(PlayerZ * TileSize) - TileSize/2;
		GameData.CameraPosition.y = -(0.5f * GameData.GameScale);
		ParticleHandler.UpdateParticles(delta);
		UpdateObjects(delta);
		MyWindObject.Update(delta);
	}

	private void CheckTeleporters(LogicObject MyLogic){
		if(MyLogic.IsPowered() == true){
			if(PlayerX == MyLogic.PositionX && PlayerZ == MyLogic.PositionZ && MovementTimer > 0.5f){
				int Y = MyLogic.GetData() / 32;
		        int X = MyLogic.GetData() % 32;
		        
		        PlayerX = X;
		        PlayerZ = Y;
		        Direction = MyLogic.PowerPosition[2];
		        MovementTimer = 1;
		        
			}
		}
	}
}
