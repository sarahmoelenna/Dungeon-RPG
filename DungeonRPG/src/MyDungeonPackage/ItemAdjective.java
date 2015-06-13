package MyDungeonPackage;

import java.util.Random;

public enum ItemAdjective {

	Enflamed, //inflicts burning
	Glacial, //inflicts cold
	Venomous, //inflicts poison
	Brittle, // less strength
	Sturdy, // more strength
	Cumbersome, //slows
	Cursed, //cant unequpi but double stats
	Feathered, //increases evasiveness
	Falcons, //increases accuracy
	Repears; //increases condition
	
	
	 private static final ItemAdjective[] VALUES = ItemAdjective.values();
	 private static final int SIZE = VALUES.length;
	 private static final Random RANDOM = new Random();

	 public static ItemAdjective getRandomAdjective()  {
	   return VALUES[RANDOM.nextInt(SIZE)];
	 }
	
}
