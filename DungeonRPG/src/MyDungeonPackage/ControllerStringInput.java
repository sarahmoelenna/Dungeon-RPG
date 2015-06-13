package MyDungeonPackage;

public class ControllerStringInput {

	int Length;
	int Lines;
	int Selected;
	
	float MoveTimer;
	
	Character CharLineOne[];
	Character CharLineTwo[];
	Character CharLineThree[];
	
	boolean Done;
	boolean spacereleased;
	int LineSelected;
	
	public ControllerStringInput(int length, int lines){
		Length = length;
		Lines = lines;
		MoveTimer = 1;
		Selected = 0;
		CharLineOne = new Character[length];
		CharLineTwo = new Character[length];
		CharLineThree = new Character[length];
		spacereleased = false;
		OnStart();
		Done = false;
		LineSelected = 0;
	}
	
	private void OnStart(){
		for(int i = 0; i < Length; i++){
			CharLineOne[i] = 'a';
			CharLineTwo[i] = 'a';
			CharLineThree[i] = 'a';
		}
	}
	
	public void Update(int delta){
		MoveTimer += 0.008f * delta;
		if(MoveTimer > 1){MoveTimer = 1;}
		
		if(Input.GetSpace() == false){
			spacereleased = true;
		}
		if(Input.GetSpace() && spacereleased){
			LineSelected++;
			spacereleased = false;
		}
		if(LineSelected > Lines - 1){
			LineSelected = 0;
		}
		
		if(Input.GetA() && MoveTimer == 1){
			Selected--;
			MoveTimer = 0;
		}
		if(Input.GetD() && MoveTimer == 1){
			Selected++;
			MoveTimer = 0;
		}
		if(Input.GetW() && MoveTimer == 1){
			if(LineSelected == 0){
				CharLineOne[Selected]--;
				if(CharLineOne[Selected] == '>'){CharLineOne[Selected] = 'z';}
			}
			if(LineSelected == 1){
				CharLineTwo[Selected]--;
				if(CharLineTwo[Selected] == '>'){CharLineTwo[Selected] = 'z';}
			}
			if(LineSelected == 2){
				CharLineThree[Selected]--;
				if(CharLineThree[Selected] == '>'){CharLineThree[Selected] = 'z';}
			}
			//System.out.println(CharLineOne[Selected]);
			MoveTimer = 0;
		}
		if(Input.GetS() && MoveTimer == 1){
			if(LineSelected == 0){
				CharLineOne[Selected]++;
				if(CharLineOne[Selected] == '>'){CharLineOne[Selected] = 'z';}
			}
			if(LineSelected == 1){
				CharLineTwo[Selected]++;
				if(CharLineTwo[Selected] == '>'){CharLineTwo[Selected] = 'z';}
			}
			if(LineSelected == 2){
				CharLineThree[Selected]++;
				if(CharLineThree[Selected] == '>'){CharLineThree[Selected] = 'z';}
			}
			MoveTimer = 0;
		}
		if(Input.GetR() && MoveTimer == 1){
			Finalize();
			Done = true;
			MoveTimer = 0;
		}
		//System.out.println(CharLineOne[Selected] + "");
		//System.out.println(MoveTimer);
		if(Selected < 0){Selected = Length - 1;}
		if(Selected > Length - 1){Selected = 0;}
	}
	
	public void drawInput(){
		FontHandler.ElitePro.DisplayFont(50, 1050, 1.0f, "Input Text");
		
		for(int i = 0; i < CharLineOne.length; i++){
			Character MyChar = CharLineOne[i];
			
			if(MyChar == '`'){MyChar = ' ';}
			if(MyChar == '_'){MyChar = '.';}
			if(MyChar == '^'){MyChar = '\'';}
			if(MyChar == '\\'){MyChar = ':';}
			if(MyChar == ']'){MyChar = ',';}
			if(MyChar == '['){MyChar = '!';}
			if(MyChar == '@'){MyChar = '-';}
			
			if(i == Selected && LineSelected == 0){
				FontHandler.EliteProBlack.DisplayFont(50 + i*27, 1000, 0.8f, MyChar +"");
			}
			else{
				FontHandler.ElitePro.DisplayFont(50 + i*27, 1000, 0.8f, MyChar +"");
			}
		}
		
		if(Lines > 1){
			for(int i = 0; i < CharLineTwo.length; i++){
				Character MyChar = CharLineTwo[i];
				
				if(MyChar == '`'){MyChar = ' ';}
				if(MyChar == '_'){MyChar = '.';}
				if(MyChar == '^'){MyChar = '\'';}
				if(MyChar == '\\'){MyChar = ':';}
				if(MyChar == ']'){MyChar = ',';}
				if(MyChar == '['){MyChar = '!';}
				if(MyChar == '@'){MyChar = '-';}
				
				if(i == Selected && LineSelected == 1){
					FontHandler.EliteProBlack.DisplayFont(50 + i*27, 900, 0.8f, MyChar +"");
				}
				else{
					FontHandler.ElitePro.DisplayFont(50 + i*27, 900, 0.8f, MyChar +"");
				}
			}
		}
		
		
		if(Lines > 2){
			for(int i = 0; i < CharLineThree.length; i++){
				Character MyChar = CharLineThree[i];
				
				if(MyChar == '`'){MyChar = ' ';}
				if(MyChar == '_'){MyChar = '.';}
				if(MyChar == '^'){MyChar = '\'';}
				if(MyChar == '\\'){MyChar = ':';}
				if(MyChar == ']'){MyChar = ',';}
				if(MyChar == '['){MyChar = '!';}
				if(MyChar == '@'){MyChar = '-';}
				
				if(i == Selected && LineSelected == 2){
					FontHandler.EliteProBlack.DisplayFont(50 + i*27, 800, 0.8f, MyChar +"");
				}
				else{
					FontHandler.ElitePro.DisplayFont(50 + i*27, 800, 0.8f, MyChar +"");
				}
			}
		}
		
	}
	
	private void Finalize(){
		for(int i = 0; i < CharLineOne.length; i++){
			Character MyChar = CharLineOne[i];
			if(MyChar == '`'){MyChar = ' ';}
			if(MyChar == '_'){MyChar = '.';}
			if(MyChar == '^'){MyChar = '\'';}
			if(MyChar == '\\'){MyChar = ':';}
			if(MyChar == ']'){MyChar = ',';}
			if(MyChar == '['){MyChar = '!';}
			if(MyChar == '@'){MyChar = '-';}
			CharLineOne[i] = MyChar;
		}
		
		for(int i = 0; i < CharLineTwo.length; i++){
			Character MyChar = CharLineTwo[i];
			if(MyChar == '`'){MyChar = ' ';}
			if(MyChar == '_'){MyChar = '.';}
			if(MyChar == '^'){MyChar = '\'';}
			if(MyChar == '\\'){MyChar = ':';}
			if(MyChar == ']'){MyChar = ',';}
			if(MyChar == '['){MyChar = '!';}
			if(MyChar == '@'){MyChar = '-';}
			CharLineTwo[i] = MyChar;
		}
		
		for(int i = 0; i < CharLineThree.length; i++){
			Character MyChar = CharLineThree[i];
			if(MyChar == '`'){MyChar = ' ';}
			if(MyChar == '_'){MyChar = '.';}
			if(MyChar == '^'){MyChar = '\'';}
			if(MyChar == '\\'){MyChar = ':';}
			if(MyChar == ']'){MyChar = ',';}
			if(MyChar == '['){MyChar = '!';}
			if(MyChar == '@'){MyChar = '-';}
			CharLineThree[i] = MyChar;
		}
	}
	
	public String getLineOne(boolean File){
		String Line = "";
		
		for(int i = 0; i < CharLineOne.length; i++){
			Character MyChar = CharLineOne[i];
			if(MyChar == ' '){
				MyChar = '_';
			}
			if(File && MyChar == '_'){
				
			}
			else{
				Line = Line + MyChar;
			}
		}
		return Line;
	}
	
	public String getLineTwo(boolean File){
		String Line = "";
		
		for(int i = 0; i < CharLineTwo.length; i++){
			Character MyChar = CharLineTwo[i];
			if(MyChar == ' '){
				MyChar = '_';
			}
			if(File && MyChar == '_'){
				
			}
			else{
				Line = Line + MyChar;
			}
		}
		return Line;
	}
	
	public String getLineThree(boolean File){
		String Line = "";
		
		for(int i = 0; i < CharLineThree.length; i++){
			Character MyChar = CharLineThree[i];
			if(File){
				if(MyChar == ' '){
					MyChar = '_';
				}
			}
			Line = Line + MyChar;
		}
		return Line;
	}
	
}
