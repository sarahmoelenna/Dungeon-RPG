package MyDungeonPackage;

import java.util.Random;

public class ItemWeapon extends Item {
	
	ItemWeaponType MyWeaponType;
	
	public ItemWeapon(int FloorNumber) {
		super(FloorNumber);
		Temp = new Random();
		
		MyWeaponType = ItemWeaponType.getRandomWeaponType();
		
		int ChanceofAdj = FloorNumber/5;
		ChanceofAdj *= 10;
		
		int ChanceofDes = FloorNumber/10;
		ChanceofDes *= 5;
		
		int Chance = RanInt(0, 100);
		if(Chance < ChanceofAdj){
			HasAdj = true;
		}
		else{ HasAdj = false;}
		
		Chance = RanInt(0, 100);
		if(Chance < ChanceofDes){
			HasDes = true;
		}
		else{ HasDes = false;}
		
		if(FloorNumber < 11){
			MyMaterial = ItemMaterial.Wooden;
		}
		else if(FloorNumber < 23 && FloorNumber >= 11){
			MyMaterial = ItemMaterial.Bronze;
		}
		else if(FloorNumber < 34  && FloorNumber >= 23){
			MyMaterial = ItemMaterial.Gold;
		}
		else if(FloorNumber < 46  && FloorNumber >= 34){
			MyMaterial = ItemMaterial.Iron;
		}
		else if(FloorNumber < 57  && FloorNumber >= 46){
			MyMaterial = ItemMaterial.Steel;
		}
		else if(FloorNumber < 69 && FloorNumber >= 57){
			MyMaterial = ItemMaterial.Mithril;
		}
		else if(FloorNumber < 80 && FloorNumber >= 69){
			MyMaterial = ItemMaterial.Orichalcum;
		}
		else if(FloorNumber >= 80){
			MyMaterial = ItemMaterial.Adamantite;
		}
		
		
		MyType = ItemType.Sword;
		
		
		if(HasAdj == true){
			MyAdjective = ItemAdjective.getRandomAdjective();
		}
		if(HasDes == true){
			MyDescriber = ItemDescriber.getRandomDescriber();
		}
		
		if(HasAdj == true && HasDes == true){
			FullName = MyAdjective.toString() + " " + MyMaterial.toString() + " " + MyWeaponType.toString() + " of " + MyDescriber.toString();
		}
		if(HasAdj == true && HasDes == false){
			FullName = MyAdjective.toString() + " " + MyMaterial.toString() + " " + MyWeaponType.toString();
		}
		if(HasAdj == false && HasDes == true){
			FullName = MyMaterial.toString() + " " + MyWeaponType.toString() + " of " + MyDescriber.toString();
		}
		if(HasAdj == false && HasDes == false){
			FullName = MyMaterial.toString() + " " + MyWeaponType.toString();
		}
		
		Equipped = false;
		
		CalculateMyStats();
	}
	
	private void CalculateMyStats(){
		Condition = 0;
		if(MyWeaponType == ItemWeaponType.Axe){
			int Multiplier = getMaterialMultiplier();
			Strength = 6 * Multiplier;
			Defense = 0 * Multiplier;
			Evasiveness = 0 * Multiplier;
			Accuracy = 0 * Multiplier;
			Fortitude = 0 * Multiplier;
		}
		
		if(MyWeaponType == ItemWeaponType.Sword){
			int Multiplier = getMaterialMultiplier();
			Strength = 4 * Multiplier;
			Defense = 0 * Multiplier;
			Evasiveness = 0 * Multiplier;
			Accuracy = 0 * Multiplier;
			Fortitude = 5 * Multiplier;
		}
		
		if(MyWeaponType == ItemWeaponType.Scythe){
			int Multiplier = getMaterialMultiplier();
			Strength = 4 * Multiplier;
			Defense = 0 * Multiplier;
			Evasiveness = 0 * Multiplier;
			Accuracy = 0 * Multiplier;
			Fortitude = 0 * Multiplier;
			Condition = Multiplier; 
		}
		
		if(MyWeaponType == ItemWeaponType.Dagger){
			int Multiplier = getMaterialMultiplier();
			Strength = 4 * Multiplier;
			Defense = 0 * Multiplier;
			Evasiveness = 3 * Multiplier;
			Accuracy = 3 * Multiplier;
			Fortitude = 0 * Multiplier;
		}

		CalculateAdjectiveStats();
		
	}
	
	}
