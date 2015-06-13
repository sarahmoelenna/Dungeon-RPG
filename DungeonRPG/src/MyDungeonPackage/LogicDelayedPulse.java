package MyDungeonPackage;

public class LogicDelayedPulse extends LogicObject {

	boolean On;
	boolean Pulse;
	float PulseTimer;
	int speed;
	
	public LogicDelayedPulse(int X, int Z, int time, int Position) {
		super(X, Z);

		PowerPosition[0] = Position;
		speed = time;
		On = false;
		Pulse = false;
		PulseTimer = 0;
		
	}
	
	public void Update(int delta){
		if(Powered == true){
			Powered = false;
		}
		if(Pulse == true){
			PulseTimer += 0.005f * delta;
			//System.out.println(PulseTimer);
		}
		if(PulseTimer > 1 * speed){
			//System.out.println("pulse");
			PulseTimer = 0; 
			Pulse = false;
			Powered = true;
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
