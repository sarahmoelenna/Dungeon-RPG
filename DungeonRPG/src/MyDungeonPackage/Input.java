package MyDungeonPackage;

import org.lwjgl.input.Controller;
import org.lwjgl.input.Keyboard;


class Input {
	
	static boolean EnterDown = false;
	static boolean EnterReleased = false;
	
	static boolean VDown = false;
	static boolean VReleased = false;
	
	static Controller MyController;
	static boolean ControllerAvaliable = false;
	
	static boolean GetEnter(){
		if(Keyboard.isKeyDown(Keyboard.KEY_RETURN)) {  
            return true;
        }
		else{
			return false;
		}
	}
	
	static boolean GetEsc(){
		if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) || ControllerAvaliable == true) {
			 if(ControllerAvaliable == false){
				 return true;
			 }
			 else{
				 if(MyController.isButtonPressed(7) == true || Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
					 return true;
				 }
				 else{ return false;}
			 }
	     }
		 else { return false;}
	}
	
	static boolean GetEnterReleased(){
		if(GetEnter() == true){
			EnterDown = true;
			EnterReleased = false;
		}
		else{
			EnterReleased = true;
		}
		if(EnterReleased == true && EnterDown == true){
			EnterDown = false;
			return true;
		}
		else{
			return false;
		}
	}
	
	static boolean GetV(){
		if(Keyboard.isKeyDown(Keyboard.KEY_V)) {  
            return true;
        }
		else{
			return false;
		}
	}
	
	static boolean GetVReleased(){
		if(GetV() == true){
			VDown = true;
			VReleased = false;
		}
		else{
			VReleased = true;
		}
		if(VReleased == true && VDown == true){
			VDown = false;
			return true;
		}
		else{
			return false;
		}
	}
	
	static boolean GetRegularSpace(){
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
			return true;
		}
		else{return false;}
	}
	
	static boolean GetSpace(){
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE) || ControllerAvaliable == true) {
			 if(ControllerAvaliable == false){
				 return true;
			 }
			 else{
				 if(MyController.getAxisValue(4) < -0.8f || Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
					 return true;
				 }
				 else{ return false;}
			 }
	     }
		 else { return false;}
	}
	
	static boolean GetUp(){
		if(Keyboard.isKeyDown(Keyboard.KEY_UP)) {  
            return true;
        }
		else{
			return false;
		}
	}
	
	static boolean GetDown(){
		if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {   
            return true;
        }
		else{
			return false;
		}
	}
	
	static boolean GetRight(){
		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) { 
            return true;
        }
		else{
			return false;
		}
	}
	
	static boolean GetLeft(){
		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {   
            return true;
        }
		else{
			return false;
		}
	}
	
	static boolean GetZ(){
		if(Keyboard.isKeyDown(Keyboard.KEY_Z)) {   
            return true;
        }
		else{
			return false;
		}
	}
	
	static boolean GetR(){
		if(Keyboard.isKeyDown(Keyboard.KEY_R) || ControllerAvaliable == true) {
			 if(ControllerAvaliable == false){
				 return true;
			 }
			 else{
				 if(MyController.isButtonPressed(3) == true || Keyboard.isKeyDown(Keyboard.KEY_R)){
					 return true;
				 }
				 else{ return false;}
			 }
	     }
		 else { return false;}
	}

	static boolean GetM(){
		if(Keyboard.isKeyDown(Keyboard.KEY_M) || ControllerAvaliable == true) {
			 if(ControllerAvaliable == false){
				 return true;
			 }
			 else{
				 if(MyController.isButtonPressed(2) == true || Keyboard.isKeyDown(Keyboard.KEY_M)){
					 return true;
				 }
				 else{ return false;}
			 }
	     }
		 else { return false;}
	}

	static boolean GetF(){
		if(Keyboard.isKeyDown(Keyboard.KEY_F) || ControllerAvaliable == true) {
			 if(ControllerAvaliable == false){
				 return true;
			 }
			 else{
				 if(MyController.isButtonPressed(0) == true || Keyboard.isKeyDown(Keyboard.KEY_F)){
					 return true;
				 }
				 else{ return false;}
			 }
	     }
		 else { return false;}
	}
	
	static boolean GetX(){
		if(Keyboard.isKeyDown(Keyboard.KEY_X)) {   
            return true;
        }
		else{
			return false;
		}
	}
	
	static boolean GetC(){
		if(Keyboard.isKeyDown(Keyboard.KEY_C)) {   
            return true;
        }
		else{
			return false;
		}
	}

	static boolean GetB(){
		if(Keyboard.isKeyDown(Keyboard.KEY_B)) {   
            return true;
        }
		else{
			return false;
		}
	}

	static boolean GetG(){
		if(Keyboard.isKeyDown(Keyboard.KEY_G)) {   
            return true;
        }
		else{
			return false;
		}
	}
	
	static boolean GetH(){
		if(Keyboard.isKeyDown(Keyboard.KEY_H)) {   
            return true;
        }
		else{
			return false;
		}
	}

	static boolean GetI(){
		if(Keyboard.isKeyDown(Keyboard.KEY_I) || ControllerAvaliable == true) {
			 if(ControllerAvaliable == false){
				 return true;
			 }
			 else{
				 if(MyController.isButtonPressed(1) == true || Keyboard.isKeyDown(Keyboard.KEY_I)){
					 return true;
				 }
				 else{ return false;}
			 }
	     }
		 else { return false;}
	}

	static boolean GetJ(){
		if(Keyboard.isKeyDown(Keyboard.KEY_J)) {   
            return true;
        }
		else{
			return false;
		}
	}
	
	static boolean GetK(){
		if(Keyboard.isKeyDown(Keyboard.KEY_K)) {   
            return true;
        }
		else{
			return false;
		}
	}

	static boolean GetL(){
		if(Keyboard.isKeyDown(Keyboard.KEY_L)) {   
            return true;
        }
		else{
			return false;
		}
	}

	static boolean GetN(){
		if(Keyboard.isKeyDown(Keyboard.KEY_N)) {   
            return true;
        }
		else{
			return false;
		}
	}

	static boolean GetO(){
		if(Keyboard.isKeyDown(Keyboard.KEY_O)) {   
            return true;
        }
		else{
			return false;
		}
	}

	static boolean GetP(){
		if(Keyboard.isKeyDown(Keyboard.KEY_P)) {   
            return true;
        }
		else{
			return false;
		}
	}

	static boolean GetT(){
		if(Keyboard.isKeyDown(Keyboard.KEY_T)) {   
            return true;
        }
		else{
			return false;
		}
	}

	static boolean GetU(){
		if(Keyboard.isKeyDown(Keyboard.KEY_U)) {   
            return true;
        }
		else{
			return false;
		}
	}

	static boolean GetY(){
		if(Keyboard.isKeyDown(Keyboard.KEY_Y) || ControllerAvaliable == true) {
			 if(ControllerAvaliable == false){
				 return true;
			 }
			 else{
				 if(MyController.getAxisValue(4) > 0.8f || Keyboard.isKeyDown(Keyboard.KEY_Y)){
					 return true;
				 }
				 else{ return false;}
			 }
	     }
		 else { return false;}
	}
	
	public static boolean GetW(){
		
		 if(Keyboard.isKeyDown(Keyboard.KEY_W) || ControllerAvaliable == true) {
			 if(ControllerAvaliable == false){
				 return true;
			 }
			 else{
				 if(MyController.getPovY() < -0.5f  || Keyboard.isKeyDown(Keyboard.KEY_W)){
					 return true;
				 }
				 else{ return false;}
			 }
	     }
		 else { return false;}
	
	}
	
	public static boolean GetS(){
		
		if(Keyboard.isKeyDown(Keyboard.KEY_S) || ControllerAvaliable == true) {
			 if(ControllerAvaliable == false){
				 return true;
			 }
			 else{
				 if(MyController.getPovY() > 0.5f  || Keyboard.isKeyDown(Keyboard.KEY_S)){
					 return true;
				 }
				 else{ return false;}
			 }
	     }
		 else { return false;}
		
	
	}
	
	public static boolean GetD(){
		
		if(Keyboard.isKeyDown(Keyboard.KEY_D) || ControllerAvaliable == true) {
			 if(ControllerAvaliable == false){
				 return true;
			 }
			 else{
				 if(MyController.getPovX() > 0.5f || Keyboard.isKeyDown(Keyboard.KEY_D)){
					 return true;
				 }
				 else{ return false;}
			 }
	     }
		 else { return false;}
	
	}
	
	public static boolean GetA(){
		
		if(Keyboard.isKeyDown(Keyboard.KEY_A) || ControllerAvaliable == true) {
			 if(ControllerAvaliable == false){
				 return true;
			 }
			 else{
				 if(MyController.getPovX() < -0.5f  || Keyboard.isKeyDown(Keyboard.KEY_A)){
					 return true;
				 }
				 else{ return false;}
			 }
	     }
		 else { return false;}
	
	}
	
	public static boolean GetQ(){
		
		if(Keyboard.isKeyDown(Keyboard.KEY_Q) || ControllerAvaliable == true) {
			 if(ControllerAvaliable == false){
				 return true;
			 }
			 else{
				 if(MyController.isButtonPressed(4) == true  || Keyboard.isKeyDown(Keyboard.KEY_Q)){
					 return true;
				 }
				 else{ return false;}
			 }
	     }
		 else { return false;}
	
	}
	
	public static boolean GetE(){
		
		if(Keyboard.isKeyDown(Keyboard.KEY_E) || ControllerAvaliable == true) {
			 if(ControllerAvaliable == false){
				 return true;
			 }
			 else{
				 if(MyController.isButtonPressed(5) == true || Keyboard.isKeyDown(Keyboard.KEY_E)){
					 return true;
				 }
				 else{ return false;}
			 }
	     }
		 else { return false;}
	
	}
	
	
	static boolean GetF2(){
		if(Keyboard.isKeyDown(Keyboard.KEY_F2)) {   
            return true;
        }
		else{
			return false;
		}
	}
	
	static boolean GetF3(){
		if(Keyboard.isKeyDown(Keyboard.KEY_F3)) {   
            return true;
        }
		else{
			return false;
		}
	}
	
	static boolean GetF4(){
		if(Keyboard.isKeyDown(Keyboard.KEY_F4)) {   
            return true;
        }
		else{
			return false;
		}
	}
	
	static boolean GetF5(){
		if(Keyboard.isKeyDown(Keyboard.KEY_F5)) {   
            return true;
        }
		else{
			return false;
		}
	}
	
	static boolean GetF6(){
		if(Keyboard.isKeyDown(Keyboard.KEY_F6)) {   
            return true;
        }
		else{
			return false;
		}
	}
	
	static boolean GetF7(){
		if(Keyboard.isKeyDown(Keyboard.KEY_F7)) {   
            return true;
        }
		else{
			return false;
		}
	}
	
	static boolean GetF8(){
		if(Keyboard.isKeyDown(Keyboard.KEY_F8)) {   
            return true;
        }
		else{
			return false;
		}
	}
	
	static boolean GetF9(){
		if(Keyboard.isKeyDown(Keyboard.KEY_F9)) {   
            return true;
        }
		else{
			return false;
		}
	}
	
	static boolean GetF10(){
		if(Keyboard.isKeyDown(Keyboard.KEY_F10)) {   
            return true;
        }
		else{
			return false;
		}
	}
	
	static boolean GetF11(){
		if(Keyboard.isKeyDown(Keyboard.KEY_F11)) {   
            return true;
        }
		else{
			return false;
		}
	}
	
	static boolean GetF12(){
		if(Keyboard.isKeyDown(Keyboard.KEY_F12)) {   
            return true;
        }
		else{
			return false;
		}
	}
	
	static boolean GetBackSpace(){
		if(Keyboard.isKeyDown(Keyboard.KEY_BACK)) {   
            return true;
        }
		else{
			return false;
		}
	}

}
