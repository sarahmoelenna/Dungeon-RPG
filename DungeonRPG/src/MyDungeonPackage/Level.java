package MyDungeonPackage;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Random;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

public class Level {
	
	WorldGrid MyGrid;
	Vector3f LightPosition;
	Random Temp;
	int FloorNumber;
	boolean consoledown;
	boolean ForceMap;
	Inventory PlayerInventory;
	
	TileSet TempleTileSet;
	
	boolean invDown;
	boolean InvOpen;
	boolean MainMenu;
	
	public Level(){
		MainMenu = false;
		GameData.DayTime = true;
		PlayerInventory = new Inventory(GameData.playerType);
		TempleTileSet = new TileSet(GameData.GameScale);
		MyGrid = new WorldGrid(32, 32, 0);
		MyGrid.SetTileset(TempleTileSet);
		LoadScreen.ShowLoad();
		if(GameData.CustomDungeon == false){
			MyGrid.GenerateOpenWorld();
		}
		else{
			MyGrid.LoadLevel(GameData.LoadName);
		}
		LoadScreen.ShowLoad();
		MyGrid.buildBuffers(GameData.GameScale);
		LoadScreen.ShowLoad();
		MyGrid.BuildCollision();
		LoadScreen.ShowLoad();
		MyGrid.BuildGridObject();
		LoadScreen.ShowLoad();
		MyGrid.BuildWindObject();
		LoadScreen.ShowLoad();
		MyGrid.PopulateObjects();
		LoadScreen.ShowLoad();
		MyGrid.BuildMap();
		LightPosition = new Vector3f(-200.0f, 500.0f, -200.0f);
		//MyGrid.SetPlayerStats(500, 500, 3, 2, 1);
		MyGrid.PlayerHealth = 500;
		MyGrid.PlayerMaxHealth = 500;
		MyGrid.SetInventory(PlayerInventory);
		Temp = new Random();
		FloorNumber = 1;
		ForceMap = false;
		
		consoledown = false;
	}
	
	public void UpdateLevel(int delta){
		
		if(Input.GetF12() == true && invDown == false){
			consoledown = true;
		}
		else{
			if(consoledown == true){
				consoledown = false;
				GameData.ConsoleOpen = !GameData.ConsoleOpen;
				GameConsole.ClearMessage();
			}
		}
		
		if(Input.GetI() == true && GameData.ConsoleOpen == false){
			invDown = true;
		}
		else{
			if(invDown == true){
				invDown = false;
				InvOpen = !InvOpen;
				PlayerInventory.invopen = ! PlayerInventory.invopen;
			}
		}
		
		if(InvOpen == true){
			PlayerInventory.Update(delta);
		}
		
		if(GameData.ConsoleOpen == true)
		{
			GameConsole.Update();
		}
		
		
		if(GameData.DayTime == true){
			GameData.SkyClear = new Vector3f(0.52f, 0.81f, 0.92f);
			GameData.LightColour = new Vector3f(1.0f, 1.0f, 1.0f);
		}
		else{
			GameData.SkyClear = new Vector3f(0.1f, 0.1f, 0.29f);
			GameData.LightColour = new Vector3f(0.1f, 0.1f, 0.29f);
		}
		if(GameData.Midnight == true){
			GameData.SkyClear = new Vector3f(0.0f, 0.0f, 0.0f);
			GameData.LightColour = new Vector3f(0.0f, 0.0f, 0.0f);
		}
		
		
		CheckConsole();
		MyGrid.UpdateGrid(delta);
		
		if(MyGrid.PlayerHealth <= 0){
			MainMenu = true;
		}
		
		if(MyGrid.NeedReload == true){
			
			if((GameData.CustomDungeon == false || GameData.hasNextFloor) && FloorNumber != 100){
			int MyTime = RanInt(0,10);
			if(MyTime < 5){
				GameData.DayTime = true;
			}
			else{
				GameData.DayTime = false;
			}
			float PlayerHealth = MyGrid.PlayerHealth;
			float PlayerMaxHealth = MyGrid.PlayerMaxHealth;
			int Potions = MyGrid.Potions;
			ModelHandler.ClearLights();
			LoadScreen.ShowLoad();
			MyGrid = new WorldGrid(32, 32, FloorNumber);
			MyGrid.SetTileset(TempleTileSet);
			
			if(GameData.hasNextFloor == false){
			int Type = RanInt(0 , 100);
			if(Type < 30){
				MyGrid.GenerateOpenWorld();
			}
			else if(Type >= 30 && Type < 60){
				MyGrid.GenerateLabyrinth();
			}
			else if(Type >= 60 && Type < 90){
				MyGrid.GenerateWinding();
			}
			else{
				MyGrid.GenerateMaze();
			}
			}
			else{
				MyGrid.LoadLevel(GameData.LoadName);
			}
			
			LoadScreen.ShowLoad();
			MyGrid.buildBuffers(GameData.GameScale);
			LoadScreen.ShowLoad();
			MyGrid.BuildCollision();
			LoadScreen.ShowLoad();
			MyGrid.BuildGridObject();
			LoadScreen.ShowLoad();
			MyGrid.BuildWindObject();
			LoadScreen.ShowLoad();
			MyGrid.PopulateObjects();
			LoadScreen.ShowLoad();
			MyGrid.BuildMap();
			MyGrid.SetInventory(PlayerInventory);
			MyGrid.PlayerHealth = PlayerHealth;
			MyGrid.PlayerMaxHealth = PlayerMaxHealth;
			MyGrid.Potions = Potions;
			if(ForceMap == true){
				MyGrid.MyMap.RevealMap();
			}
			FloorNumber++;
			}
			else{
				MainMenu = true;
			}
		}
		GameConsole.MessageRecieved();
	}
	
	public void RenderLevel(){
		float lightPosition[] = { LightPosition.x, LightPosition.y, LightPosition.z, 0.0f };
		ByteBuffer temp = ByteBuffer.allocateDirect(16);
		temp.order(ByteOrder.nativeOrder());
		
		GL11.glLoadIdentity();
		GL11.glLight(GL11.GL_LIGHT1, GL11.GL_POSITION, (FloatBuffer) temp.asFloatBuffer().put(lightPosition).flip());
		
		//MyGrid.GridRender();
		MyGrid.TileRender();
		//MyGrid.TileRenderDepth();
	}
	
	public void RenderLevelDepth(){
		MyGrid.TileRenderDepth();
	}

	public void RenderUI(){
		if(InvOpen == true){
			PlayerInventory.DrawInventoryList();
		}
		else{
			MyGrid.UIRender();
			if(GameData.ConsoleOpen == true)
			{
				GameConsole.drawConsole();
			}
		}
		
	}
	
	private int RanInt(int Min, int Max){
	    return Temp.nextInt(Max - Min) + Min;
	}
	
	private void CheckConsole(){
		if(GameConsole.GetAwaiting() == true){
			//System.out.println(GameConsole.GetMessage());
			if(GameConsole.GetMessage().equals("time day")){
				GameData.DayTime = true;
				GameConsole.MessageRecieved();
			}
			if(GameConsole.GetMessage().equals("time night")){
				GameData.DayTime = false;
				GameConsole.MessageRecieved();
			}
			
			if(GameConsole.GetMessage().equals("reveal map on")){
				ForceMap = true;
				MyGrid.MyMap.RevealMap();
				GameConsole.MessageRecieved();
			}
			
			if(GameConsole.GetMessage().equals("reveal map off")){
				ForceMap = false;
				GameConsole.MessageRecieved();
			}
			
			if(GameConsole.GetMessage().equals("increment ten")){
				FloorNumber+=10;
				GameConsole.MessageRecieved();
			}
			
			if(GameConsole.GetMessage().equals("floor ninetynine")){
				FloorNumber=99;
				GameConsole.MessageRecieved();
			}
			if(GameConsole.GetMessage().equals("player warrior")){
				PlayerInventory.MyPlayerType = PlayerType.Warrior;
				GameConsole.MessageRecieved();
			}
			if(GameConsole.GetMessage().equals("player paladin")){
				PlayerInventory.MyPlayerType = PlayerType.Paladin;
				GameConsole.MessageRecieved();
			}
			if(GameConsole.GetMessage().equals("player necromancer")){
				PlayerInventory.MyPlayerType = PlayerType.Necromancer;
				GameConsole.MessageRecieved();
			}
			if(GameConsole.GetMessage().equals("player thief")){
				PlayerInventory.MyPlayerType = PlayerType.Thief;
				GameConsole.MessageRecieved();
			}
				
			if(GameConsole.GetMessage().equals("gen maze")){

				float PlayerHealth = MyGrid.PlayerHealth;
				float PlayerMaxHealth = MyGrid.PlayerMaxHealth;
				int Potions = MyGrid.Potions;
				ModelHandler.ClearLights();
				LoadScreen.ShowLoad();
				MyGrid = new WorldGrid(32, 32, FloorNumber);
				MyGrid.SetTileset(TempleTileSet);
				MyGrid.GenerateMaze();
				LoadScreen.ShowLoad();
				MyGrid.buildBuffers(GameData.GameScale);
				LoadScreen.ShowLoad();
				MyGrid.BuildCollision();
				LoadScreen.ShowLoad();
				MyGrid.BuildGridObject();
				LoadScreen.ShowLoad();
				MyGrid.BuildWindObject();
				LoadScreen.ShowLoad();
				MyGrid.PopulateObjects();
				LoadScreen.ShowLoad();
				MyGrid.BuildMap();
				MyGrid.PlayerHealth = PlayerHealth;
				MyGrid.PlayerMaxHealth = PlayerMaxHealth;
				MyGrid.Potions = Potions;
				MyGrid.SetInventory(PlayerInventory);
				int MyTime = RanInt(0,10);
				if(MyTime < 5){
					GameData.DayTime = true;
				}
				else{
					GameData.DayTime = false;
				}
				if(ForceMap == true){
					MyGrid.MyMap.RevealMap();
				}
				FloorNumber++;
				
				GameConsole.MessageRecieved();
			}
			
			
			if(GameConsole.GetMessage().equals("gen lab")){

				float PlayerHealth = MyGrid.PlayerHealth;
				float PlayerMaxHealth = MyGrid.PlayerMaxHealth;
				int Potions = MyGrid.Potions;
				ModelHandler.ClearLights();
				LoadScreen.ShowLoad();
				MyGrid = new WorldGrid(32, 32, FloorNumber);
				MyGrid.SetTileset(TempleTileSet);
				MyGrid.GenerateLabyrinth();
				LoadScreen.ShowLoad();
				MyGrid.buildBuffers(GameData.GameScale);
				LoadScreen.ShowLoad();
				MyGrid.BuildCollision();
				LoadScreen.ShowLoad();
				MyGrid.BuildGridObject();
				LoadScreen.ShowLoad();
				MyGrid.BuildWindObject();
				LoadScreen.ShowLoad();
				MyGrid.PopulateObjects();
				LoadScreen.ShowLoad();
				MyGrid.BuildMap();
				MyGrid.PlayerHealth = PlayerHealth;
				MyGrid.PlayerMaxHealth = PlayerMaxHealth;
				MyGrid.Potions = Potions;
				MyGrid.SetInventory(PlayerInventory);
				int MyTime = RanInt(0,10);
				if(MyTime < 5){
					GameData.DayTime = true;
				}
				else{
					GameData.DayTime = false;
				}
				if(ForceMap == true){
					MyGrid.MyMap.RevealMap();
				}
				FloorNumber++;
				
				GameConsole.MessageRecieved();
			}
			
			
			if(GameConsole.GetMessage().equals("gen open")){

				float PlayerHealth = MyGrid.PlayerHealth;
				float PlayerMaxHealth = MyGrid.PlayerMaxHealth;
				int Potions = MyGrid.Potions;
				ModelHandler.ClearLights();
				LoadScreen.ShowLoad();
				MyGrid = new WorldGrid(32, 32, FloorNumber);
				MyGrid.SetTileset(TempleTileSet);
				MyGrid.GenerateOpenWorld();
				LoadScreen.ShowLoad();
				MyGrid.buildBuffers(GameData.GameScale);
				LoadScreen.ShowLoad();
				MyGrid.BuildCollision();
				LoadScreen.ShowLoad();
				MyGrid.BuildGridObject();
				LoadScreen.ShowLoad();
				MyGrid.BuildWindObject();
				LoadScreen.ShowLoad();
				MyGrid.PopulateObjects();
				LoadScreen.ShowLoad();
				MyGrid.BuildMap();
				MyGrid.PlayerHealth = PlayerHealth;
				MyGrid.PlayerMaxHealth = PlayerMaxHealth;
				MyGrid.Potions = Potions;
				MyGrid.SetInventory(PlayerInventory);
				int MyTime = RanInt(0,10);
				if(MyTime < 5){
					GameData.DayTime = true;
				}
				else{
					GameData.DayTime = false;
				}
				if(ForceMap == true){
					MyGrid.MyMap.RevealMap();
				}
				FloorNumber++;
				
				GameConsole.MessageRecieved();
			}
			
			
			if(GameConsole.GetMessage().equals("gen flat")){

				float PlayerHealth = MyGrid.PlayerHealth;
				float PlayerMaxHealth = MyGrid.PlayerMaxHealth;
				int Potions = MyGrid.Potions;
				ModelHandler.ClearLights();
				LoadScreen.ShowLoad();
				MyGrid = new WorldGrid(32, 32, FloorNumber);
				MyGrid.SetTileset(TempleTileSet);
				MyGrid.GenerateFlatWorld();
				LoadScreen.ShowLoad();
				MyGrid.buildBuffers(GameData.GameScale);
				LoadScreen.ShowLoad();
				MyGrid.BuildCollision();
				LoadScreen.ShowLoad();
				MyGrid.BuildGridObject();
				LoadScreen.ShowLoad();
				MyGrid.BuildWindObject();
				LoadScreen.ShowLoad();
				MyGrid.PopulateObjects();
				LoadScreen.ShowLoad();
				MyGrid.BuildMap();
				MyGrid.PlayerHealth = PlayerHealth;
				MyGrid.PlayerMaxHealth = PlayerMaxHealth;
				MyGrid.Potions = Potions;
				MyGrid.SetInventory(PlayerInventory);
				int MyTime = RanInt(0,10);
				if(MyTime < 5){
					GameData.DayTime = true;
				}
				else{
					GameData.DayTime = false;
				}
				if(ForceMap == true){
					MyGrid.MyMap.RevealMap();
				}
				FloorNumber++;
				
				GameConsole.MessageRecieved();
			}
			
			
			if(GameConsole.GetMessage().equals("gen winding")){

				int MyTime = RanInt(0,10);
				if(MyTime < 5){
					GameData.DayTime = true;
				}
				else{
					GameData.DayTime = false;
				}
				
				float PlayerHealth = MyGrid.PlayerHealth;
				float PlayerMaxHealth = MyGrid.PlayerMaxHealth;
				int Potions = MyGrid.Potions;
				ModelHandler.ClearLights();
				LoadScreen.ShowLoad();
				MyGrid = new WorldGrid(32, 32, FloorNumber);
				MyGrid.SetTileset(TempleTileSet);
				MyGrid.GenerateWinding();
				LoadScreen.ShowLoad();
				MyGrid.buildBuffers(GameData.GameScale);
				LoadScreen.ShowLoad();
				MyGrid.BuildCollision();
				LoadScreen.ShowLoad();
				MyGrid.BuildGridObject();
				LoadScreen.ShowLoad();
				MyGrid.BuildWindObject();
				LoadScreen.ShowLoad();
				MyGrid.PopulateObjects();
				LoadScreen.ShowLoad();
				MyGrid.BuildMap();
				MyGrid.PlayerHealth = PlayerHealth;
				MyGrid.PlayerMaxHealth = PlayerMaxHealth;
				MyGrid.Potions = Potions;
				MyGrid.SetInventory(PlayerInventory);
				if(ForceMap == true){
					MyGrid.MyMap.RevealMap();
				}
				FloorNumber++;
				
				GameConsole.MessageRecieved();
			}
			
			
		}
	}

}
