package MyDungeonPackage;

 class GameConsole {

	static String LastPressed = "blank";
	static String message = "";
	static String PostedMessage = "";
	static boolean awatingmessage = false;
	
	static String PrevMessage[] = new String[10];
	static int MessageNum = 0;
	static int CurrentNum = 0;
	
	static void Update(){
		//System.out.println(LastPressed);
		if(message == null){
			message = "";
		}
		
		if(Input.GetEnter() == true){
			if(LastPressed.equals("enter") == false){
				LastPressed = "enter";
			}
		}
		else{
			if(LastPressed.equals("enter")){
				LastPressed = "blank";
				PostedMessage = message;
				AddMessagetoArray(PostedMessage);
				GameData.ConsoleOpen = false;
				awatingmessage = true;
			}
		}
		
		
		if(Input.GetBackSpace() == true){
			if(LastPressed.equals("backspace") == false){
				LastPressed = "backspace";
			}
		}
		else{
			if(LastPressed.equals("backspace")){
				LastPressed = "blank";
				if(message.length() > 0){
					//System.out.println("....");
					message = message.substring(0,message.length()-1);
				}
			}
		}
		
		if(Input.GetUp() == true){
			if(LastPressed.equals("up") == false){
				LastPressed = "up";
			}
		}
		else{
			if(LastPressed.equals("up")){
				LastPressed = "blank";
				CurrentNum--;
				if(CurrentNum < 0){
					CurrentNum = 0;
				}
				message = PrevMessage[CurrentNum];
			}
		}
		
		if(Input.GetDown() == true){
			if(LastPressed.equals("down") == false){
				LastPressed = "down";
			}
		}
		else{
			if(LastPressed.equals("down")){
				LastPressed = "blank";
				CurrentNum++;
				if(CurrentNum > MessageNum - 1){
					CurrentNum = MessageNum - 1;
				}
				if(MessageNum == 0){
					CurrentNum = 0;
				}
				message = PrevMessage[CurrentNum];
			}
		}
		
		if(Input.GetRegularSpace() == true){
			if(LastPressed.equals("Space") == false){
				LastPressed = "Space";
			}
		}
		else{
			if(LastPressed.equals("Space")){
				LastPressed = "blank";
				message = message +" ";
			}
		}
		
		
		if(Input.GetA() == true){
			if(LastPressed.equals("a") == false){
				LastPressed = "a";
			}
		}
		else{
			if(LastPressed.equals("a")){
				LastPressed = "blank";
				message = message +"a";
			}
		}
		
		if(Input.GetB() == true){
			if(LastPressed.equals("b") == false){
				LastPressed = "b";
			}
		}
		else{
			if(LastPressed.equals("b")){
				LastPressed = "blank";
				message = message +"b";
			}
		}
		
		if(Input.GetC() == true){
			if(LastPressed.equals("c") == false){
				LastPressed = "c";
			}
		}
		else{
			if(LastPressed.equals("c")){
				LastPressed = "blank";
				message = message +"c";
			}
		}
		
		if(Input.GetD() == true){
			if(LastPressed.equals("d") == false){
				LastPressed = "d";
			}
		}
		else{
			if(LastPressed.equals("d")){
				LastPressed = "blank";
				message = message +"d";
			}
		}
		
		if(Input.GetE() == true){
			if(LastPressed.equals("e") == false){
				LastPressed = "e";
			}
		}
		else{
			if(LastPressed.equals("e")){
				LastPressed = "blank";
				message = message +"e";
			}
		}
		
		if(Input.GetF() == true){
			if(LastPressed.equals("f") == false){
				LastPressed = "f";
			}
		}
		else{
			if(LastPressed.equals("f")){
				LastPressed = "blank";
				message = message +"f";
			}
		}
		
		if(Input.GetG() == true){
			if(LastPressed.equals("g") == false){
				LastPressed = "g";
			}
		}
		else{
			if(LastPressed.equals("g")){
				LastPressed = "blank";
				message = message +"g";
			}
		}
		
		if(Input.GetH() == true){
			if(LastPressed.equals("h") == false){
				LastPressed = "h";
			}
		}
		else{
			if(LastPressed.equals("h")){
				LastPressed = "blank";
				message = message +"h";
			}
		}
		
		if(Input.GetI() == true){
			if(LastPressed.equals("i") == false){
				LastPressed = "i";
			}
		}
		else{
			if(LastPressed.equals("i")){
				LastPressed = "blank";
				message = message +"i";
			}
		}
		
		if(Input.GetJ() == true){
			if(LastPressed.equals("j") == false){
				LastPressed = "j";
			}
		}
		else{
			if(LastPressed.equals("j")){
				LastPressed = "blank";
				message = message +"j";
			}
		}
		
		if(Input.GetK() == true){
			if(LastPressed.equals("k") == false){
				LastPressed = "k";
			}
		}
		else{
			if(LastPressed.equals("k")){
				LastPressed = "blank";
				message = message +"k";
			}
		}
		
		if(Input.GetL() == true){
			if(LastPressed.equals("l") == false){
				LastPressed = "l";
			}
		}
		else{
			if(LastPressed.equals("l")){
				LastPressed = "blank";
				message = message +"l";
			}
		}
		
		if(Input.GetM() == true){
			if(LastPressed.equals("m") == false){
				LastPressed = "m";
			}
		}
		else{
			if(LastPressed.equals("m")){
				LastPressed = "blank";
				message = message +"m";
			}
		}
		
		if(Input.GetN() == true){
			if(LastPressed.equals("n") == false){
				LastPressed = "n";
			}
		}
		else{
			if(LastPressed.equals("n")){
				LastPressed = "blank";
				message = message +"n";
			}
		}
		
		
		if(Input.GetO() == true){
			if(LastPressed.equals("o") == false){
				LastPressed = "o";
			}
		}
		else{
			if(LastPressed.equals("o")){
				LastPressed = "blank";
				message = message +"o";
			}
		}
		
		if(Input.GetP() == true){
			if(LastPressed.equals("p") == false){
				LastPressed = "p";
			}
		}
		else{
			if(LastPressed.equals("p")){
				LastPressed = "blank";
				message = message +"p";
			}
		}
		
		if(Input.GetQ() == true){
			if(LastPressed.equals("q") == false){
				LastPressed = "q";
			}
		}
		else{
			if(LastPressed.equals("q")){
				LastPressed = "blank";
				message = message +"q";
			}
		}
		
		if(Input.GetR() == true){
			if(LastPressed.equals("r") == false){
				LastPressed = "r";
			}
		}
		else{
			if(LastPressed.equals("r")){
				LastPressed = "blank";
				message = message +"r";
			}
		}
		
		if(Input.GetS() == true){
			if(LastPressed.equals("s") == false){
				LastPressed = "s";
			}
		}
		else{
			if(LastPressed.equals("s")){
				LastPressed = "blank";
				message = message +"s";
			}
		}
		
		if(Input.GetT() == true){
			if(LastPressed.equals("t") == false){
				LastPressed = "t";
			}
		}
		else{
			if(LastPressed.equals("t")){
				LastPressed = "blank";
				message = message +"t";
			}
		}
		
		if(Input.GetU() == true){
			if(LastPressed.equals("u") == false){
				LastPressed = "u";
			}
		}
		else{
			if(LastPressed.equals("u")){
				LastPressed = "blank";
				message = message +"u";
			}
		}
		
		if(Input.GetV() == true){
			if(LastPressed.equals("v") == false){
				LastPressed = "v";
			}
		}
		else{
			if(LastPressed.equals("v")){
				LastPressed = "blank";
				message = message +"v";
			}
		}
		
		if(Input.GetW() == true){
			if(LastPressed.equals("w") == false){
				LastPressed = "w";
			}
		}
		else{
			if(LastPressed.equals("w")){
				LastPressed = "blank";
				message = message +"w";
			}
		}
		
		if(Input.GetX() == true){
			if(LastPressed.equals("x") == false){
				LastPressed = "x";
			}
		}
		else{
			if(LastPressed.equals("x")){
				LastPressed = "blank";
				message = message +"x";
			}
		}
		
		if(Input.GetY() == true){
			if(LastPressed.equals("y") == false){
				LastPressed = "y";
			}
		}
		else{
			if(LastPressed.equals("y")){
				LastPressed = "blank";
				message = message +"y";
			}
		}
		
		if(Input.GetZ() == true){
			if(LastPressed.equals("z") == false){
				LastPressed = "z";
			}
		}
		else{
			if(LastPressed.equals("z")){
				LastPressed = "blank";
				message = message +"z";
			}
		}
		
	}

	static void ClearMessage(){
		message = "";
		CurrentNum = MessageNum;
	}
	
	static void drawConsole(){
		ShaderHandler.TextShader.Activate();
		FontHandler.ElitePro.DisplayFont(550, 20, 0.8f,"Console: " + message);
		ShaderHandler.TextShader.DeActivate();
	}
	
	static String GetMessage(){
		return PostedMessage;
	}
	
	static boolean GetAwaiting(){
		return awatingmessage;
	}
	
	static void MessageRecieved(){
		awatingmessage = false;
		PostedMessage = "";
	}
	
	private static void MoveArray(){
		for(int i = 0; i < 9; i++){
			PrevMessage[i] = PrevMessage[i+1];
		}
	}
	
	private static void AddMessagetoArray(String AddMessage){
		if(MessageNum < 10){
			PrevMessage[MessageNum] = AddMessage;
			MessageNum++;
		}
		else{
			MoveArray();
			PrevMessage[9] = AddMessage;
		}
	}
	
}
