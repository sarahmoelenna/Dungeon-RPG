package MyDungeonPackage;

import java.util.Random;

public enum ItemDescriber {

	Leeching,
	Thorns,
	Torment,
	Shivering,
	Fury,
	Protection;
	
	
	private static final ItemDescriber[] VALUES = ItemDescriber.values();
	private static final int SIZE = VALUES.length;
	private static final Random RANDOM = new Random();

	public static ItemDescriber getRandomDescriber()  {
		return VALUES[RANDOM.nextInt(SIZE)];
	}
	
	
}
