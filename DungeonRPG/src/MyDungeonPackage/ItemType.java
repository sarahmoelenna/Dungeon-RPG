package MyDungeonPackage;

import java.util.Random;

public enum ItemType {

	Sword,
	Chest,
	Greaves,
	Gloves,
	Boots,
	Ring,
	Amulet,
	Helm;
	
	
	
	 private static final ItemType[] VALUES = ItemType.values();
	 private static final int SIZE = VALUES.length;
	 private static final Random RANDOM = new Random();

	 public static ItemType getRandomType()  {
	   return VALUES[RANDOM.nextInt(SIZE)];
	 }
	
}
