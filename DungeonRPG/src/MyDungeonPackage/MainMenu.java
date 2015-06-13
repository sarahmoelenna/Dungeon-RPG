package MyDungeonPackage;

public class MainMenu {

	float PauseTimer;
	int UnPause; //1 random, //2 dungeon, //3 editor, //4 exit
	boolean ReleasedOnce;
	boolean ShowMenu;
	int selection; //0 start random, 1 custom dungeon, 2 dungeon editor, 3 exit
	
	public MainMenu(){
		PauseTimer = 1;
		UnPause = 0;
		ReleasedOnce = false;
		ShowMenu = false;
		selection = 0;
	}
	
	public void Update(int delta){
		PauseTimer += 0.005f * delta;
		if(PauseTimer > 1){PauseTimer = 1;}
		
		if((Input.GetF() != true && Input.GetEnter()!= true) && ReleasedOnce == false){
			ReleasedOnce = true;
		}
		
		if((Input.GetF() || Input.GetEnter()) && PauseTimer == 1  && ReleasedOnce == true && ShowMenu == false){
			//UnPause = true;
			PauseTimer = 0;
			ShowMenu = true;
		}
		
		
		if(ShowMenu == true){
			if(PauseTimer == 1 && Input.GetW() == true){
				selection --;
				PauseTimer = 0;
			}
			
			if(PauseTimer == 1 && Input.GetS() == true){
				selection ++;
				PauseTimer = 0;
			}
			
			if(selection < 0){selection = 3;}
			
			if(selection > 3){selection = 0;}

			
			if((Input.GetF() || Input.GetEnter()) && PauseTimer == 1 && ReleasedOnce){
				if(selection == 0){
					UnPause = 1;
					PauseTimer = 0;
				}
				if(selection == 1){
					UnPause = 2;
					PauseTimer = 0;
				}
				if(selection == 2){
					UnPause = 3;
					PauseTimer = 0;
				}
				if(selection == 3){
					GameData.CloseGame = true;
					PauseTimer = 0;
				}
			}
		
		
		}
	}
	
	public void DrawMainMenu(){
		ShaderHandler.TextShader.Activate();
		FontHandler.ElitePro.DisplayFont(50, 1000, 2.0f, "Endless Sanctuary");
		if(ShowMenu == false){
			if(Input.ControllerAvaliable == true){
				FontHandler.ElitePro.DisplayFont(700, 100, 1.0f, "Press A to start.");
			}
			else{
				FontHandler.ElitePro.DisplayFont(700, 100, 1.0f, "Press ENTER to start.");
			}
		}
		else{
			if(selection == 0){
				FontHandler.ElitePro.DisplayFont(700, 400, 1.0f, "->Random Dungeon<-");
			}
			else{
				FontHandler.ElitePro.DisplayFont(700, 400, 1.0f, "Random Dungeon");
			}
			
			if(selection == 1){
				FontHandler.ElitePro.DisplayFont(700, 300, 1.0f, "->Custom Dungeon<-");
			}
			else{
				FontHandler.ElitePro.DisplayFont(700, 300, 1.0f, "Custom Dungeon");
			}
			
			if(selection == 2){
				FontHandler.ElitePro.DisplayFont(700, 200, 1.0f, "->Dungeon Editor<-");
			}
			else{
				FontHandler.ElitePro.DisplayFont(700, 200, 1.0f, "Dungeon Editor");
			}
			
			if(selection == 3){
				FontHandler.ElitePro.DisplayFont(700, 100, 1.0f, "->Close Game<-");
			}
			else{
				FontHandler.ElitePro.DisplayFont(700, 100, 1.0f, "Close Game");
			}
			
		}
		
		ShaderHandler.TextShader.DeActivate();
	}
	
}
