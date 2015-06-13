package MyDungeonPackage;

import org.lwjgl.util.vector.Vector3f;

public class Particle {

	Vector3f Colour;
	Vector3f Position;
	Vector3f Direction;
	Vector3f StartPosition;
	float Scale;
	float Speed;
	float alpha;
	
	public Particle(Vector3f pos, Vector3f dir, Vector3f col, Vector3f start){
		
		Position = new Vector3f(0,0,0);
		StartPosition = new Vector3f(0,0,0);
		Colour = new Vector3f(0,0,1);
		Direction = new Vector3f(0,0,0);
		
		Position.x = pos.x;
		Position.y = pos.y;
		Position.z = pos.z;
		StartPosition.x = start.x;
		StartPosition.y = start.y;
		StartPosition.z = start.z;
		Direction.x = dir.x;
		Direction.y = dir.y;
		Direction.z = dir.z;
		Colour.x = col.x;
		Colour.y = col.y;
		Colour.z = col.z;
		Speed = 3;
		Scale = 0.1f * GameData.GameScale;
		alpha = 1;
	}
	
	public Particle(Vector3f pos, Vector3f dir, Vector3f col, Vector3f start, float speed, float scale){
		
		Position = new Vector3f(0,0,0);
		StartPosition = new Vector3f(0,0,0);
		Colour = new Vector3f(0,0,1);
		Direction = new Vector3f(0,0,0);
		
		Position.x = pos.x;
		Position.y = pos.y;
		Position.z = pos.z;
		StartPosition.x = start.x;
		StartPosition.y = start.y;
		StartPosition.z = start.z;
		Direction.x = dir.x;
		Direction.y = dir.y;
		Direction.z = dir.z;
		Colour.x = col.x;
		Colour.y = col.y;
		Colour.z = col.z;
		Speed = speed;
		Scale = scale * GameData.GameScale;
		alpha = 1;
	}
	
	public void UpdateParticle(int delta){
		
		Position.x += Direction.x * 0.0001f * delta * Speed * GameData.GameScale;
		Position.y += Direction.y * 0.0001f * delta * Speed * GameData.GameScale;
		Position.z += Direction.z * 0.0001f * delta * Speed * GameData.GameScale;
		Scale -= 0.00001f * delta * Speed * GameData.GameScale;
		//alpha -= 0.00004f * delta * Speed;
		
	}
}
