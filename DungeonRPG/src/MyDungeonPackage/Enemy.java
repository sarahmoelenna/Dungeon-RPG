package MyDungeonPackage;

import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.util.vector.Vector3f;

public class Enemy {
	
	Vector3f Position;
	DisplayObject MyObject;
	float Size;
	
	int PosX;
	int PosY;
	int PosZ;
	
	int Up;
	int Down;
	int Left;
	int Right;
	Random Temp;
	int enemyDirection;
	
	EnemyState MyState;
	
	String EnemyName;
	
	float MovementTimer;
	float AttackTimer;
	float StatusTimer;
	
	float OffsetX;
	float OffsetZ;
	float Health;
	float MaxHealth;
	
	int PathLength;
	int PathX[];
	int PathY[];
	int CurrentPathPoint;
	boolean NeedPath;
	
	int PlayerRange;
	int FleeHealth;
	
	boolean PlayerHit;
	
	int enemyStrength;
	int enemyDefense;
	int enemyXP;
	
	int enemyType;
	
	float speed;
	
	boolean NoPath;
	float NoPathTimer;
	
	ArrayList<StatusEffects> StatusEffectList = new ArrayList<>();
	
	ParticleEmitter ColdEmitter;
	ParticleEmitter PoisonEmitter;
	ParticleEmitter BurnEmitter;
	
	public Enemy(String ModelName, String TextureName, int X, int Y, int Z, float size, int floornumber){
		
		ColdEmitter = new ParticleEmitter(X, Y, Z, new Vector3f(0.63f, 0.94f, 0.95f), 1);
		PoisonEmitter = new ParticleEmitter(X, Y, Z, new Vector3f(0.94f, 0.77f, 0.45f), 1);
		BurnEmitter = new ParticleEmitter(X, Y, Z, new Vector3f(1, 0, 0), 1);
		
		ParticleHandler.AddEmitter(ColdEmitter);
		ParticleHandler.AddEmitter(PoisonEmitter);
		ParticleHandler.AddEmitter(BurnEmitter);
		
		ColdEmitter = ParticleHandler.FindEmitter(ColdEmitter);
		BurnEmitter = ParticleHandler.FindEmitter(BurnEmitter);
		PoisonEmitter = ParticleHandler.FindEmitter(PoisonEmitter);
		
		PosX = X;
		PosY = Y;
		PosZ = Z;
		OffsetX = 0;
		OffsetZ = 0;
		
		MyState = EnemyState.Idle;
		
		MovementTimer = 1;
		AttackTimer = 1;
		Temp = new Random();
		
		enemyType = 0;
		int TypeChance = RanInt(0,100);
		if(TypeChance < 25){
			enemyType = 2;
		}
		else if(TypeChance < 80){
			enemyType = 3;
		}
		else{
			enemyType = 1;
		}
		
		if(enemyType == 1){
			Health = floornumber + 6;
			MaxHealth = floornumber + 6;
			enemyStrength = floornumber+4;
			enemyDefense = (floornumber+4)/2;
			EnemyName = "Red Scarab";
			FleeHealth = 3;
			PlayerRange = 7;
			speed = 1;
		}
		else if(enemyType == 2){
			Health = floornumber + 6;
			MaxHealth = floornumber + 6;
			enemyStrength = floornumber+4;
			enemyDefense = (floornumber+4)/2;
			EnemyName = "Blue Scarab";
			FleeHealth = 2;
			PlayerRange = 5;
			TextureName = "bugblue";
			speed = 1.2f;
		}
		else if(enemyType == 3){
			Health = floornumber + 6;
			MaxHealth = floornumber + 6;
			enemyStrength = floornumber+4;
			enemyDefense = (floornumber+4)/2;
			EnemyName = "Orange Scarab";
			FleeHealth = (int) (MaxHealth * 2);
			PlayerRange = 9;
			TextureName = "bugyellow";
			speed = 0.7f;
		}
		
		MyObject = new DisplayObject(ModelName, TextureName, DisplayObjectType.TexturedObject);
		
		ModelHandler.AddTexture("bugglow");
		MyObject.GlowMaptexture = ModelHandler.FindTexture("bugglow");
		
		Size = size;
		MyObject.SetScale(Size);
		//System.out.println("Enemy Made");
		
		PathLength = 0;
		PathX = new int [0];
		PathY = new int [0];
		CurrentPathPoint = 0;
		NeedPath = false;
		PlayerHit = false;
		
		NoPath = false;
		NoPathTimer = 0;
		
		enemyXP = 5;
		
		
	}
	
	public Enemy(String ModelName, String TextureName, int X, int Y, int Z, float size, int floornumber, int Type){
		
		ColdEmitter = new ParticleEmitter(X, Y, Z, new Vector3f(0.63f, 0.94f, 0.95f),1);
		PoisonEmitter = new ParticleEmitter(X, Y, Z, new Vector3f(0.94f, 0.77f, 0.45f),1);
		BurnEmitter = new ParticleEmitter(X, Y, Z, new Vector3f(1, 0, 0),1);
		
		ParticleHandler.AddEmitter(ColdEmitter);
		ParticleHandler.AddEmitter(PoisonEmitter);
		ParticleHandler.AddEmitter(BurnEmitter);
		
		ColdEmitter = ParticleHandler.FindEmitter(ColdEmitter);
		BurnEmitter = ParticleHandler.FindEmitter(BurnEmitter);
		PoisonEmitter = ParticleHandler.FindEmitter(PoisonEmitter);
		
		PosX = X;
		PosY = Y;
		PosZ = Z;
		OffsetX = 0;
		OffsetZ = 0;
		
		//System.out.println("Enemy Made");
		MyState = EnemyState.Idle;
		
		MovementTimer = 1;
		AttackTimer = 1;
		Temp = new Random();
		
		enemyType = Type;
		
		if(enemyType == 1){
			Health = floornumber + 6;
			MaxHealth = floornumber + 6;
			enemyStrength = floornumber+4;
			enemyDefense = (floornumber+4)/2;
			EnemyName = "Red Scarab";
			FleeHealth = 3;
			PlayerRange = 7;
			speed = 1;
		}
		else if(enemyType == 2){
			Health = (floornumber + 6) * 0.7f;
			MaxHealth = (floornumber + 6) * 0.7f;
			enemyStrength = (int) ((floornumber+4) * 1.2f);
			enemyDefense = (int) (((floornumber+4)/2)*0.8f);
			EnemyName = "Blue Scarab";
			FleeHealth = 2;
			PlayerRange = 5;
			TextureName = "bugblue";
			speed = 1.2f;
		}
		else if(enemyType == 3){
			Health = (floornumber + 6)*1.5f;
			MaxHealth = (floornumber + 6)*1.5f;
			enemyStrength = (int) ((floornumber+4)*0.8f);
			enemyDefense = (int) (((floornumber+4)/2) * 1.2f);
			EnemyName = "Orange Scarab";
			FleeHealth = (int) (MaxHealth * 2);
			PlayerRange = 9;
			TextureName = "bugyellow";
			speed = 0.7f;
		}
		
		MyObject = new DisplayObject(ModelName, TextureName, DisplayObjectType.TexturedObject);
		
		ModelHandler.AddTexture("bugglow");
		MyObject.GlowMaptexture = ModelHandler.FindTexture("bugglow");
		
		Size = size;
		MyObject.SetScale(Size);
		
		PathLength = 0;
		PathX = new int [0];
		PathY = new int [0];
		CurrentPathPoint = 0;
		NeedPath = false;
		PlayerHit = false;
		
		NoPath = false;
		NoPathTimer = 0;
		
		enemyXP = 5;
		
		
	}
	
	public void update(int delta, int PlayerX, int PlayerY){
		
		UpdateEmitters(delta);
		
		MovementTimer += 0.001f *delta * speed;
		AttackTimer += 0.001f *delta * speed; 
		StatusTimer += 0.001f *delta; 
		if(MovementTimer > 1){MovementTimer = 1;}
		if(AttackTimer > 1){AttackTimer = 1;}
		if(StatusTimer > 1){StatusTimer = 1;}
		
		if(NoPath == true){
			NoPathTimer += 0.001f *delta;
			if(NoPathTimer > 5){NoPathTimer = 0; NoPath = false;}
		}
		
		
		if(MovementTimer >= 1){
		
			if(MyState == EnemyState.Idle){
				SwitchtoWander();
				if(NoPath == false){
					SwitchtoChase(PlayerX, PlayerY);
				}
			}
			if(MyState == EnemyState.Wander){
				SwitchtoIdle();
				if(NoPath == false){
					SwitchtoChase(PlayerX, PlayerY);
				}
				if(MyState == EnemyState.Wander){
					Wander();
				}
			}
			if(MyState == EnemyState.Chase){
				SwitchChasetoIdle(PlayerX, PlayerY);
				SwitchtoAttack(PlayerX, PlayerY);
				SwitchtoFlee(PlayerX, PlayerY);
				if(NoPath == true && MyState == EnemyState.Chase){
					MyState = EnemyState.Wander;
				}
				if(MyState == EnemyState.Chase){
					Chase();
				}
			}
			if(MyState == EnemyState.Attack){
				SwitchAttacktoChase(PlayerX, PlayerY);
				SwitchtoFlee(PlayerX, PlayerY);
				if(MyState == EnemyState.Attack){
					Attack(PlayerX, PlayerY);
				}
			}
			if(MyState == EnemyState.Flee){
				SwitchChasetoIdle(PlayerX, PlayerY);
				SwitchFleetoAttack();
				if(MyState == EnemyState.Flee){
					Flee(PlayerX, PlayerY);
				}
			}
		}
		
		if(enemyDirection == 1){//z--
			MyObject.SetRotation(0);
			OffsetZ = -((1 - MovementTimer) * Size);
			OffsetX = 0;
		}
		if(enemyDirection == 2){//z++
			MyObject.SetRotation(180);
			OffsetZ = ((1 - MovementTimer) * Size);
			OffsetX = 0;
		}
		if(enemyDirection == 3){//x--
			MyObject.SetRotation(90);
			OffsetX = -((1 - MovementTimer) * Size);
			OffsetZ = 0;
		}
		if(enemyDirection == 4){//x++
			MyObject.SetRotation(270);
			OffsetX = ((1 - MovementTimer) * Size);
			OffsetZ = 0;
		}
		MyObject.SetPosition(PosX * Size - OffsetX + Size/2, PosY * Size, PosZ * Size - OffsetZ + Size/2);
	}
	
	private void SwitchtoIdle(){
		int chance;
		chance = RanInt(0,100);
		if(chance > 90){
			MyState = EnemyState.Idle;
		}
	}
	
	private void SwitchtoWander(){
		int chance;
		chance = RanInt(0,100);
		if(chance > 90){
			MyState = EnemyState.Wander;
		}
	}
	
	private void SwitchtoChase(int PlayerX, int PlayerY){

		if(PlayerX - PosX < PlayerRange && PlayerX - PosX > -PlayerRange){
			if(PlayerY - PosZ < PlayerRange && PlayerY - PosZ > -PlayerRange){
				MyState = EnemyState.Chase;
				NeedPath = true;
			}
		}

	}

	private void SwitchChasetoIdle(int PlayerX, int PlayerY){

		if(PlayerX - PosX > PlayerRange || PlayerX - PosX < -PlayerRange){
			MyState = EnemyState.Idle;
			NeedPath = false;
		}
		if(PlayerY - PosZ > PlayerRange || PlayerY - PosZ < -PlayerRange){
			MyState = EnemyState.Idle;
			NeedPath = false;
		}
	}

	private void SwitchtoAttack(int PlayerX, int PlayerY){


		if(PlayerX - PosX <2 && PlayerX - PosX > -2 && PosZ == PlayerY){
			MyState = EnemyState.Attack;
			NeedPath = false;
		}
		if(PlayerY - PosZ < 2 && PlayerY - PosZ > -2  && PosX == PlayerX){
			MyState = EnemyState.Attack;
			NeedPath = false;
		}

	}
	
	private void SwitchAttacktoChase(int PlayerX, int PlayerY){

		boolean InAttackRange = false;

		if(PosX == PlayerX && PosZ == PlayerY + 1){//up
			InAttackRange = true;
		}
		if(PosX == PlayerX && PosZ == PlayerY - 1){//down
			InAttackRange = true;
		}
		if(PosX == PlayerX + 1 && PosZ == PlayerY){//left
			InAttackRange = true;
		}
		if(PosX == PlayerX - 1 && PosZ == PlayerY){//right
			InAttackRange = true;
		}

		if(InAttackRange == false){
			SwitchtoChase(PlayerX, PlayerY);
		}

	}

	private void SwitchtoFlee(int PlayerX, int PlayerY){
		if(FleeHealth > 0){
			if(Health < MaxHealth/FleeHealth){
				if(Up != 1 || Down != 1 || Left != 1 || Right !=1){
					MyState = EnemyState.Flee;
					NeedPath = false;
				}
			}
		}
	}
	
	private void SwitchFleetoAttack(){
	if(Up == 1 && Down == 1 && Left == 1 && Right == 1){
		MyState = EnemyState.Attack;
	}
}
	
	private void Wander(){
		int chance;
		chance = RanInt(0,100);
		if(chance > 20 && chance <= 40){ //up
			if(Up == 0){
				PosZ --;
				enemyDirection = 1;
				MovementTimer = 0;
			}
		}
		if(chance > 40 && chance <= 60){ //down
			if(Down == 0){
				PosZ++;
				enemyDirection = 2;
				MovementTimer = 0;
			}
		}
		if(chance > 60 && chance <= 80){ //Left
			if(Left == 0){
				PosX--;
				enemyDirection = 3;
				MovementTimer = 0;
			}
		}
		if(chance > 80 && chance <= 100){ //Left
			if(Right == 0){
				PosX++;
				enemyDirection = 4;
				MovementTimer = 0;
			}
		}
		
	}

	private void Chase(){
		if(NeedPath == false){

		int direction = 0;
		//System.out.println(PosX +" " + PosZ + " " + PathX[CurrentPathPoint] + " " + PathY[CurrentPathPoint]);
		if(PosX > PathX[CurrentPathPoint] && PosZ == PathY[CurrentPathPoint]){
			direction = 3; //left
			enemyDirection = direction;
		}
		if(PosX < PathX[CurrentPathPoint] && PosZ == PathY[CurrentPathPoint]){
			direction = 4; //right
			enemyDirection = direction;
		}
		if(PosX == PathX[CurrentPathPoint] && PosZ > PathY[CurrentPathPoint]){
			direction = 1; //up
			enemyDirection = direction;
		}
		if(PosX == PathX[CurrentPathPoint] && PosZ < PathY[CurrentPathPoint]){
			direction = 2; //down
			enemyDirection = direction;
		}
		boolean goToNext = false;
		if(direction == 1 && Up== 0){
			goToNext = true;
		}
		if(direction == 2 && Down== 0){
			goToNext = true;
		}
		if(direction == 3 && Left== 0){
			goToNext = true;
		}
		if(direction == 4 && Right== 0){
			goToNext = true;
		}

		if(goToNext == true){
			PosX = PathX[CurrentPathPoint];
			PosZ = PathY[CurrentPathPoint];
			MovementTimer = 0;
				if(CurrentPathPoint < PathLength - 1){
					CurrentPathPoint++;
				
				}
			}
		}
	}

	private void Attack(int PlayerX, int PlayerY){
		if(AttackTimer >= 1){
		if(PosX == PlayerX && PosZ == PlayerY + 1 && Up!= 2){//up
			enemyDirection = 1;
			PlayerHit = true;
			AttackTimer = 0;
		}
		if(PosX == PlayerX && PosZ == PlayerY - 1 && Down!= 2){//down
			enemyDirection = 2;
			PlayerHit = true;
			AttackTimer = 0;
		}
		if(PosX == PlayerX + 1 && PosZ == PlayerY && Left!= 2){//left
			enemyDirection = 3;
			PlayerHit = true;
			AttackTimer = 0;
		}
		if(PosX == PlayerX - 1 && PosZ == PlayerY  && Right!= 2){//right
			enemyDirection = 4;
			PlayerHit = true;
			AttackTimer = 0;
		}
		}


	}
	
	private void Flee(int PlayerX, int PlayerY){

		int RanDirection;
		RanDirection = RanInt(0,4);

		int DistanceFromPlayer;
		int CurrentDistanceFromPlayer;
		int TempX;
		int TempY;
		boolean Done = false;

		TempX = PosX - PlayerX;
		TempY = PosZ - PlayerY;
		if(TempX < 0){
			TempX = TempX * -1;
		}
		if(TempY < 0){
			TempY = TempY * -1;
		}
		CurrentDistanceFromPlayer = TempY + TempX;

		if(RanDirection == 0){//check up first, then down, left, right
			if(Done == false){//up
				TempX = PosX - PlayerX;
				TempY = (PosZ - 1) - PlayerY ;
				if(TempX < 0){
					TempX = TempX * -1;
				}
				if(TempY < 0){
					TempY = TempY * -1;
				}
				DistanceFromPlayer = TempY + TempX;
				if(DistanceFromPlayer > CurrentDistanceFromPlayer  && Up == 0){
					PosZ--;
					enemyDirection = 1;
					MovementTimer = 0;
					Done = true;
				}
			}

			if(Done == false){//down
				TempX = PosX - PlayerX;
				TempY = (PosZ + 1) - PlayerY ;
				if(TempX < 0){
					TempX = TempX * -1;
				}
				if(TempY < 0){
					TempY = TempY * -1;
				}
				DistanceFromPlayer = TempY + TempX;
				if(DistanceFromPlayer > CurrentDistanceFromPlayer && Down == 0){
					PosZ++;
					enemyDirection = 2;
					MovementTimer = 0;
					Done = true;
				}
			}

			if(Done == false){//left
				TempX = (PosX - 1) - PlayerX;
				TempY = (PosZ) - PlayerY ;
				if(TempX < 0){
					TempX = TempX * -1;
				}
				if(TempY < 0){
					TempY = TempY * -1;
				}
				DistanceFromPlayer = TempY + TempX;
				if(DistanceFromPlayer > CurrentDistanceFromPlayer && Left == 0){
					enemyDirection = 3;
					MovementTimer = 0;
					PosX--;
					Done = true;
				}
			}

			if(Done == false){//right
				TempX = (PosX + 1) - PlayerX;
				TempY = (PosZ) - PlayerY ;
				if(TempX < 0){
					TempX = TempX * -1;
				}
				if(TempY < 0){
					TempY = TempY * -1;
				}
				DistanceFromPlayer = TempY + TempX;
				if(DistanceFromPlayer > CurrentDistanceFromPlayer && Right == 0){
					enemyDirection = 4;
					MovementTimer = 0;
					PosX++;
					Done = true;
				}
			}
		}


		if(RanDirection == 1){//check down first, then left, right, up
			if(Done == false){//down
				TempX = PosX - PlayerX;
				TempY = (PosZ + 1) - PlayerY ;
				if(TempX < 0){
					TempX = TempX * -1;
				}
				if(TempY < 0){
					TempY = TempY * -1;
				}
				DistanceFromPlayer = TempY + TempX;
				if(DistanceFromPlayer > CurrentDistanceFromPlayer && Down == 0){
					PosZ++;
					enemyDirection = 2;
					MovementTimer = 0;
					Done = true;
				}
			}

			if(Done == false){//left
				TempX = (PosX - 1) - PlayerX;
				TempY = (PosZ) - PlayerY ;
				if(TempX < 0){
					TempX = TempX * -1;
				}
				if(TempY < 0){
					TempY = TempY * -1;
				}
				DistanceFromPlayer = TempY + TempX;
				if(DistanceFromPlayer > CurrentDistanceFromPlayer && Left == 0){
					enemyDirection = 3;
					MovementTimer = 0;
					PosX--;
					Done = true;
				}
			}

			if(Done == false){//right
				TempX = (PosX + 1) - PlayerX;
				TempY = (PosZ) - PlayerY ;
				if(TempX < 0){
					TempX = TempX * -1;
				}
				if(TempY < 0){
					TempY = TempY * -1;
				}
				DistanceFromPlayer = TempY + TempX;
				if(DistanceFromPlayer > CurrentDistanceFromPlayer && Right == 0){
					enemyDirection = 4;
					MovementTimer = 0;
					PosX++;
					Done = true;
				}
			}

			if(Done == false){//up
				TempX = PosX - PlayerX;
				TempY = (PosZ - 1) - PlayerY ;
				if(TempX < 0){
					TempX = TempX * -1;
				}
				if(TempY < 0){
					TempY = TempY * -1;
				}
				DistanceFromPlayer = TempY + TempX;
				if(DistanceFromPlayer > CurrentDistanceFromPlayer  && Up == 0){
					PosZ--;
					enemyDirection = 1;
					MovementTimer = 0;
					Done = true;
				}
			}
		}


		if(RanDirection == 2){//check left first right, up, down
			if(Done == false){//left
				TempX = (PosX - 1) - PlayerX;
				TempY = (PosZ) - PlayerY ;
				if(TempX < 0){
					TempX = TempX * -1;
				}
				if(TempY < 0){
					TempY = TempY * -1;
				}
				DistanceFromPlayer = TempY + TempX;
				if(DistanceFromPlayer > CurrentDistanceFromPlayer && Left == 0){
					enemyDirection = 3;
					MovementTimer = 0;
					PosX--;
					Done = true;
				}
			}

			if(Done == false){//right
				TempX = (PosX + 1) - PlayerX;
				TempY = (PosZ) - PlayerY ;
				if(TempX < 0){
					TempX = TempX * -1;
				}
				if(TempY < 0){
					TempY = TempY * -1;
				}
				DistanceFromPlayer = TempY + TempX;
				if(DistanceFromPlayer > CurrentDistanceFromPlayer && Right == 0){
					enemyDirection = 4;
					MovementTimer = 0;
					PosX++;
					Done = true;
				}
			}

			if(Done == false){//up
				TempX = PosX - PlayerX;
				TempY = (PosZ - 1) - PlayerY ;
				if(TempX < 0){
					TempX = TempX * -1;
				}
				if(TempY < 0){
					TempY = TempY * -1;
				}
				DistanceFromPlayer = TempY + TempX;
				if(DistanceFromPlayer > CurrentDistanceFromPlayer  && Up == 0){
					PosZ--;
					enemyDirection = 1;
					MovementTimer = 0;
					Done = true;
				}
			}

			if(Done == false){//down
				TempX = PosX - PlayerX;
				TempY = (PosZ + 1) - PlayerY ;
				if(TempX < 0){
					TempX = TempX * -1;
				}
				if(TempY < 0){
					TempY = TempY * -1;
				}
				DistanceFromPlayer = TempY + TempX;
				if(DistanceFromPlayer > CurrentDistanceFromPlayer && Down == 0){
					PosZ++;
					enemyDirection = 2;
					MovementTimer = 0;
					Done = true;
				}
			}
		}


		if(RanDirection == 3){//check right first up, down, left
			if(Done == false){//right
				TempX = (PosX + 1) - PlayerX;
				TempY = (PosZ) - PlayerY ;
				if(TempX < 0){
					TempX = TempX * -1;
				}
				if(TempY < 0){
					TempY = TempY * -1;
				}
				DistanceFromPlayer = TempY + TempX;
				if(DistanceFromPlayer > CurrentDistanceFromPlayer && Right == 0){
					enemyDirection = 4;
					MovementTimer = 0;
					PosX++;
					Done = true;
				}
			}

			if(Done == false){//up
				TempX = PosX - PlayerX;
				TempY = (PosZ - 1) - PlayerY ;
				if(TempX < 0){
					TempX = TempX * -1;
				}
				if(TempY < 0){
					TempY = TempY * -1;
				}
				DistanceFromPlayer = TempY + TempX;
				if(DistanceFromPlayer > CurrentDistanceFromPlayer  && Up == 0){
					PosZ--;
					enemyDirection = 1;
					MovementTimer = 0;
					Done = true;
				}
			}

			if(Done == false){//down
				TempX = PosX - PlayerX;
				TempY = (PosZ + 1) - PlayerY ;
				if(TempX < 0){
					TempX = TempX * -1;
				}
				if(TempY < 0){
					TempY = TempY * -1;
				}
				DistanceFromPlayer = TempY + TempX;
				if(DistanceFromPlayer > CurrentDistanceFromPlayer && Down == 0){
					PosZ++;
					enemyDirection = 2;
					MovementTimer = 0;
					Done = true;
				}
			}

			if(Done == false){//left
				TempX = (PosX - 1) - PlayerX;
				TempY = (PosZ) - PlayerY ;
				if(TempX < 0){
					TempX = TempX * -1;
				}
				if(TempY < 0){
					TempY = TempY * -1;
				}
				DistanceFromPlayer = TempY + TempX;
				if(DistanceFromPlayer > CurrentDistanceFromPlayer && Left == 0){
					enemyDirection = 3;
					MovementTimer = 0;
					PosX--;
					Done = true;
				}
			}
		}

	}

	public void UpdateSurroundings(int u, int d, int l, int r){
		Up = u;
		Down = d;
		Left = l;
		Right = r;
	}
	
	public void DrawEnemy(){
		
		MyObject.renderDisplayObject();
	}
	
	public void DrawEnemyDepth(){
		
		MyObject.renderDisplayObjectDepth();
	}

	private int RanInt(int Min, int Max){
    	return Temp.nextInt(Max - Min) + Min;
    
    }
	
	public void SetPathLength(int length){
		PathLength = length;
		PathX = new int[PathLength];
		PathY = new int[PathLength];
	}
	
	public void AddPathPoint(int Position, int X, int Y){
		if(Position >= 0){
			PathX[Position] = X;
			PathY[Position] = Y;
		}
		//System.out.println(X + " " + Y);
		//PathLength = PathLength;
		CurrentPathPoint = 0;
	}
	
	public void UpdatePath(){
		NeedPath = false;
	}
	
	public void PlayerMoved(){
		if(MyState == EnemyState.Chase){
			NeedPath = true;
		}
	}
	
	public float GetHealth(){
		return Health;
	}

	public int GetDefense(){
		return enemyDefense;
	}
			
	public int GetStrength(){
		return enemyStrength;
	}

	public boolean InflictCondition(StatusEffectType Type, StatusEffectCondition Condition){
		
		boolean existing = false;
		
		for(StatusEffects MyEffect: StatusEffectList){
			if(MyEffect.MyStatusType == Type){
				existing = true;
			}
		}
		if(existing == false){
			StatusEffects TempStatus = new StatusEffects(Type, Condition);
			StatusEffectList.add(TempStatus);
		}
		
		return !existing;
	}

	private void UpdateEmitters(int delta){
		
		boolean HasPoison = false;
		boolean HasCold = false;
		boolean HasBurn = false;
		
		for(StatusEffects MyEffect: StatusEffectList){
			if(MyEffect.MyStatusType == StatusEffectType.Cold){
				HasCold = true;
			}
			if(MyEffect.MyStatusType == StatusEffectType.Poison){
				HasPoison = true;
			}
			if(MyEffect.MyStatusType == StatusEffectType.Burning){
				HasBurn = true;
			}
		}
		
		if(HasPoison == true){
			PoisonEmitter.UpdateEmitter(delta);
		}
		if(HasCold == true){
			ColdEmitter.UpdateEmitter(delta);
		}
		if(HasBurn == true){
			BurnEmitter.UpdateEmitter(delta);
		}
		
		PoisonEmitter.Position = MyObject.Position;
		PoisonEmitter.Position.y += Size/10;
		ColdEmitter.Position = MyObject.Position;
		ColdEmitter.Position.y += Size/10;
		BurnEmitter.Position = MyObject.Position;
		BurnEmitter.Position.y += Size/10;
		
	}
	
}
