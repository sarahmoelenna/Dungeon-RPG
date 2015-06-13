package MyDungeonPackage;

import org.lwjgl.util.vector.Vector3f;

public class GameHandler {
	
	Level MyLevel;
	GameState MyState;
	PauseMenu MyPause;
	CharacterSelectMenu MyCSMenu;
	MainMenu MyMainMenu;
	LevelEditor MyEditor;
	CustomDungeonMenu MyCustom;
	
	boolean EscPressed;
	
	public GameHandler(){
		MyLevel = new Level();
		MyState = GameState.MAIN_MENU;
		MyPause = new PauseMenu();
		MyCSMenu = new CharacterSelectMenu();
		EscPressed = false;
		MyMainMenu= new MainMenu();
		MyEditor = new LevelEditor();
		MyCustom = new CustomDungeonMenu();
		MyLevel.MyGrid.KillAll();
	}
	
	public void UpdateGame(int delta){
		
		if(Input.GetEsc() == true){
			EscPressed = true;
		}
		else{
			if(EscPressed == true){
				EscPressed = false;
				if(MyState == GameState.RUNNING){
					MyState = GameState.PAUSE_MENU;
				}
				else if(MyState == GameState.PAUSE_MENU){
					MyState = GameState.RUNNING;
				}
			}
		}
		
		if(MyState == GameState.RUNNING){
			MyLevel.UpdateLevel(delta);
			if(MyLevel.MainMenu){
				MyState = GameState.MAIN_MENU;
				GameData.CustomDungeon = false;
				MyLevel = new Level();
			}
		}
		else if(MyState == GameState.PAUSE_MENU){
			MyPause.Update(delta);
			if(MyPause.UnPause == true){
				MyPause.UnPause = false;
				MyState = GameState.RUNNING;
			}
			if(MyPause.MainMenu == true){
				MyPause.MainMenu = false;
				MyState = GameState.MAIN_MENU;
			}
		}
		else if(MyState == GameState.CHARACTER_SELECT){
			MyCSMenu.UpdateCharacterSelect(delta);
			if(MyCSMenu.UnPause == true){
				MyCSMenu.UnPause = false;
				MyCSMenu.ReleasedOnce = false;
				MyLevel = new Level();
				MyState = GameState.RUNNING;
			}
		}
		else if(MyState == GameState.MAIN_MENU){
			GameData.Midnight = false;
			GameData.Lights = true;
			GameData.CustomDungeon = false;
			
			
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
			
			
			MyMainMenu.Update(delta);
			MyLevel.MyGrid.SlowlyRotate(delta);
			MyLevel.MyGrid.KillAll();
			if(MyMainMenu.UnPause == 1){
				GameData.CustomDungeon = false;
				GameData.hasNextFloor = false;
				MyState = GameState.CHARACTER_SELECT;
				MyMainMenu.UnPause = 0;
				MyMainMenu.ReleasedOnce = false;
			}
			if(MyMainMenu.UnPause == 2){
				MyState = GameState.CUSTOM_DUNGEON_MENU;
				MyCustom = new CustomDungeonMenu();
				MyMainMenu.UnPause = 0;
				MyMainMenu.ReleasedOnce = false;
			}
			if(MyMainMenu.UnPause == 3){
				MyState = GameState.LEVEL_EDITOR;
				MyEditor = new LevelEditor();
				MyMainMenu.UnPause = 0;
				MyMainMenu.ReleasedOnce = false;
			}
		}
		else if(MyState == GameState.LEVEL_EDITOR){
			MyEditor.Update(delta);
			if(MyEditor.MainMenu == true){
				MyState = GameState.MAIN_MENU;
			}
		}
		else if(MyState == GameState.CUSTOM_DUNGEON_MENU){
			MyCustom.Update(delta);
			MyLevel.MyGrid.SlowlyRotate(delta);
			if(MyCustom.MainMenu){
				MyState = GameState.MAIN_MENU;
			}
			if(MyCustom.Char){
				MyState = GameState.CHARACTER_SELECT;
			}
			if(MyCustom.Editor){
				MyState = GameState.LEVEL_EDITOR;
				MyEditor = new LevelEditor();
			}
		}
		
	}
	
	public void RenderGame(){
		if(MyState == GameState.RUNNING || MyState == GameState.PAUSE_MENU  || MyState == GameState.MAIN_MENU || MyState == GameState.CUSTOM_DUNGEON_MENU){
			MyLevel.RenderLevel();
			//System.out.println("???");
		}
	}
	
	public void RenderGameDepth(){
		if(MyState == GameState.RUNNING || MyState == GameState.PAUSE_MENU  || MyState == GameState.MAIN_MENU || MyState == GameState.CUSTOM_DUNGEON_MENU){	
			MyLevel.RenderLevelDepth();
		}
	}
	
	public void RenderUI(){
		if(MyState == GameState.RUNNING){
			MyLevel.RenderUI();
		}
		else if(MyState == GameState.PAUSE_MENU){
			MyPause.DrawPauseMenu();
		}
		else if(MyState == GameState.CHARACTER_SELECT){
			MyCSMenu.DrawCharacterSelect();
		}
		else if(MyState == GameState.MAIN_MENU){
			MyMainMenu.DrawMainMenu();
		}
		else if(MyState == GameState.LEVEL_EDITOR){
			MyEditor.DrawLevelEditor();
		}
		else if(MyState == GameState.CUSTOM_DUNGEON_MENU){
			MyCustom.drawCustomDungeonMenuUI();
		}
	}

}
