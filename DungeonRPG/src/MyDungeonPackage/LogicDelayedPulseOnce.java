package MyDungeonPackage;

public class LogicDelayedPulseOnce extends LogicObject {

	boolean FireOnce;
	boolean On;
	boolean Pulse;
	float PulseTimer;
	int speed;
	
	public LogicDelayedPulseOnce(int X, int Z, int time, int Position) {
		super(X, Z);

		PowerPosition[0] = Position;
		speed = time;
		On = false;
		Pulse = false;
		PulseTimer = 0;
		FireOnce = false;
		
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
		if(power > 0 && Pulse == false && FireOnce == false){
			On = true;
			FireOnce = true;
		}
		if(power == 0 && On == true){
			Pulse = true;
			On = false;
		}
	}

}
