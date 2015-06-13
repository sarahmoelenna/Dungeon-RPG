package MyDungeonPackage;

public class PauseMenu {

	int PauseState; // 0 - return, 1, main menu, 2 - exit
	float PauseTimer;
	boolean UnPause;
	boolean MainMenu;
	public PauseMenu(){
		PauseState = 0;
		PauseTimer = 1;
		UnPause = false;
		MainMenu = false;
		
	}
	
	public void Update(int delta){
		PauseTimer += 0.005f * delta;
		if(PauseTimer > 1){PauseTimer = 1;}
		
		if(PauseTimer == 1 && Input.GetW() == true){
			PauseState --;
			PauseTimer = 0;
		}
		
		if(PauseTimer == 1 && Input.GetS() == true){
			PauseState ++;
			PauseTimer = 0;
		}
		
		if(PauseState < 0){PauseState = 2;}
		
		if(PauseState > 2){PauseState = 0;}
		
		if((Input.GetF() || Input.GetEnter()) && PauseTimer == 1){
			if(PauseState == 0){
				UnPause = true;
				PauseTimer = 0;
			}
			if(PauseState == 1){
				MainMenu = true;
				PauseTimer = 0;
			}
			if(PauseState == 2){
				GameData.CloseGame = true;
				PauseTimer = 0;
			}
		}
		
	}
	
	public void DrawPauseMenu(){
		ShaderHandler.TextShader.Activate();
		FontHandler.ElitePro.DisplayFont(700, 700, 2.0f, "Game Paused");
		
		if(PauseState == 0){
			FontHandler.ElitePro.DisplayFont(700, 600, 1.0f, "->Return to Game<-");
		}
		else{
			FontHandler.ElitePro.DisplayFont(700, 600, 1.0f, "Return to Game");
		}
		
		if(PauseState == 1){
			FontHandler.ElitePro.DisplayFont(700, 500, 1.0f, "->Return to Main Menu<-");
		}
		else{
			FontHandler.ElitePro.DisplayFont(700, 500, 1.0f, "Return to Main Menu");
		}
		
		if(PauseState == 2){
			FontHandler.ElitePro.DisplayFont(700, 400, 1.0f, "->Exit Game<-");
		}
		else{
			FontHandler.ElitePro.DisplayFont(700, 400, 1.0f, "Exit Game");
		}
		
		ShaderHandler.TextShader.DeActivate();
	}
	
}
