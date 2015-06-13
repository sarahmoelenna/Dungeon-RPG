package MyDungeonPackage;

import java.util.Random;

public enum ItemMaterial {
	Wooden,
	Bronze,
	Gold,
	Iron,
	Steel,
	Mithril,
	Orichalcum,
	Adamantite;
	
	
	 private static final ItemMaterial[] VALUES = ItemMaterial.values();
	 private static final int SIZE = VALUES.length;
	 private static final Random RANDOM = new Random();

	 public static ItemMaterial getRandomMaterial()  {
	   return VALUES[RANDOM.nextInt(SIZE)];
	 }
	
}
