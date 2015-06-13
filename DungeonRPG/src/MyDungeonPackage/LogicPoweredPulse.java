package MyDungeonPackage;

public class LogicPoweredPulse extends LogicObject {

	boolean On;
	boolean Pulse;
	float PulseTimer;
	int speed;
	
	public LogicPoweredPulse(int X, int Z, int time, int Position) {
		super(X, Z);

		PowerPosition[0] = Position;
		speed = time;
		On = false;
		Pulse = false;
		PulseTimer = 0;
		
	}
	
	public void Update(int delta){
		
		if(Pulse == true){
			PulseTimer += 0.005f * delta;
			//System.out.println(PulseTimer);
		}
		if(PulseTimer > 1 * speed){
			PulseTimer = 0; 
			Pulse = false;
		}
		if(PulseTimer > 0){
			Powered = true;
		}
		else{
			Powered = false;
		}
		
	}
	
	public void ActivateLogicObject(int power){
		//System.out.println(power);
		if(power > 0 && Pulse == false){
			On = true;
		}
		if(power == 0 && On == true){
			Pulse = true;
			On = false;
		}
	}

}
