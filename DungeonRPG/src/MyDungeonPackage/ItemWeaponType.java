package MyDungeonPackage;

import java.util.Random;

public enum ItemWeaponType {

	Sword,
	Dagger,
	Scythe,
	Axe;
	
	 private static final ItemWeaponType[] VALUES = ItemWeaponType.values();
	 private static final int SIZE = VALUES.length;
	 private static final Random RANDOM = new Random();

	 public static ItemWeaponType getRandomWeaponType()  {
	   return VALUES[RANDOM.nextInt(SIZE)];
	 }
	
}
