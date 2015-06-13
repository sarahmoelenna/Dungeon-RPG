package MyDungeonPackage;

import java.util.Random;

public class Item {

	ItemMaterial MyMaterial;
	ItemType MyType;
	ItemAdjective MyAdjective;
	ItemDescriber MyDescriber;
	
	String FullName;
	
	boolean Equipped;
	
	boolean HasAdj;
	boolean HasDes;
	
	Random Temp;
	
	int Strength;
	int Defense;
	int Evasiveness;
	int Accuracy;
	int Fortitude;
	int Condition;
	
	public Item(int FloorNumber){
		
		Temp = new Random();
		
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
		
		
		MyType = ItemType.getRandomType();
		
		
		if(HasAdj == true){
			MyAdjective = ItemAdjective.getRandomAdjective();
		}
		if(HasDes == true){
			MyDescriber = ItemDescriber.getRandomDescriber();
		}
		
		if(HasAdj == true && HasDes == true){
			FullName = MyAdjective.toString() + " " + MyMaterial.toString() + " " + MyType.toString() + " of " + MyDescriber.toString();
		}
		if(HasAdj == true && HasDes == false){
			FullName = MyAdjective.toString() + " " + MyMaterial.toString() + " " + MyType.toString();
		}
		if(HasAdj == false && HasDes == true){
			FullName = MyMaterial.toString() + " " + MyType.toString() + " of " + MyDescriber.toString();
		}
		if(HasAdj == false && HasDes == false){
			FullName = MyMaterial.toString() + " " + MyType.toString();
		}
		
		Equipped = false;
		
		CalculateStats();
	}
	
	public String Getname(){
		return FullName;
	}
	
	protected int RanInt(int Min, int Max){
    	return Temp.nextInt(Max - Min) + Min;
    
    }
    
	private void CalculateStats(){
		Condition = 0;
		if(MyType == ItemType.Amulet){
			AmuletStats();
		}
		else if(MyType == ItemType.Boots){
			BootsStats();
		}
		else if(MyType == ItemType.Gloves){
			GlovesStats();
		}
		else if(MyType == ItemType.Greaves){
			GreavesStats();
		}
		else if(MyType == ItemType.Chest){
			ChestStats();
		}
		else if(MyType == ItemType.Sword){
			SwordStats();
		}
		else if(MyType == ItemType.Ring){
			RingStats();
		}
		else if(MyType == ItemType.Helm){
			HelmStats();
		}
		
		CalculateAdjectiveStats();
		
	}
	
	protected int getMaterialMultiplier(){
		int Multiplier = 0;
		if(MyMaterial == ItemMaterial.Wooden){
			Multiplier = 1;
		}
		else if(MyMaterial == ItemMaterial.Bronze){
			Multiplier = 2;
		}
		else if(MyMaterial == ItemMaterial.Gold){
			Multiplier = 3;
		}
		else if(MyMaterial == ItemMaterial.Iron){
			Multiplier = 4;
		}
		else if(MyMaterial == ItemMaterial.Steel){
			Multiplier = 5;
		}
		else if(MyMaterial == ItemMaterial.Mithril){
			Multiplier = 6;
		}
		else if(MyMaterial == ItemMaterial.Orichalcum){
			Multiplier = 7;
		}
		else if(MyMaterial == ItemMaterial.Adamantite){
			Multiplier = 8;
		}
		
		
		return Multiplier;
	}
	
	protected void CalculateAdjectiveStats(){
		if(MyAdjective == ItemAdjective.Brittle){
			Strength -= 2;
		}
		if(MyAdjective == ItemAdjective.Sturdy){
			Strength += 2;
		}
		if(MyAdjective == ItemAdjective.Feathered){
			Evasiveness += 2;
		}
		if(MyAdjective == ItemAdjective.Falcons){
			Accuracy += 2;
		}
		if(MyAdjective == ItemAdjective.Cumbersome){
			Evasiveness -= 4;
			Strength +=1;
		}
		if(MyAdjective == ItemAdjective.Cursed){
			Strength *= 2;
			Defense *= 2;
			Evasiveness *=2;
			Accuracy *=2;
			Fortitude *=2;
		}
		if(MyAdjective == ItemAdjective.Repears){
			Condition += (1 * getMaterialMultiplier());
		}
	}
	
	private void HelmStats(){
		int Multiplier = getMaterialMultiplier();
		Strength = 1 * Multiplier;
		Defense = 1 * Multiplier;
		Evasiveness = 0 * Multiplier;
		Accuracy = 0 * Multiplier;
		Fortitude = 0 * Multiplier;
	}
	
	private void SwordStats(){
		int Multiplier = getMaterialMultiplier();
		Strength = 4 * Multiplier;
		Defense = 0 * Multiplier;
		Evasiveness = 0 * Multiplier;
		Accuracy = 0 * Multiplier;
		Fortitude = 2 * Multiplier;
	}
	
	private void RingStats(){
		int Multiplier = getMaterialMultiplier();
		Strength = 0 * Multiplier;
		Defense = 0 * Multiplier;
		Evasiveness = 0 * Multiplier;
		Accuracy = 1 * Multiplier;
		Fortitude = 3 * Multiplier;
	}
	
	private void ChestStats(){
		int Multiplier = getMaterialMultiplier();
		Strength = 2 * Multiplier;
		Defense = 3 * Multiplier;
		Evasiveness = 0 * Multiplier;
		Accuracy = 0 * Multiplier;
		Fortitude = 0 * Multiplier;
	}
	
	private void GreavesStats(){
		int Multiplier = getMaterialMultiplier();
		Strength = 0 * Multiplier;
		Defense = 3 * Multiplier;
		Evasiveness = 3 * Multiplier;
		Accuracy = 0 * Multiplier;
		Fortitude = 0 * Multiplier;
	}
	
	private void GlovesStats(){
		int Multiplier = getMaterialMultiplier();
		Strength = 0 * Multiplier;
		Defense = 1 * Multiplier;
		Evasiveness = 0 * Multiplier;
		Accuracy = 1 * Multiplier;
		Fortitude = 0 * Multiplier;
	}
	
	private void BootsStats(){
		int Multiplier = getMaterialMultiplier();
		Strength = 0 * Multiplier;
		Defense = 1 * Multiplier;
		Evasiveness = 2 * Multiplier;
		Accuracy = 0 * Multiplier;
		Fortitude = 0 * Multiplier;
	}
	
	private void AmuletStats(){
		int Multiplier = getMaterialMultiplier();
		Strength = 2 * Multiplier;
		Defense = 0 * Multiplier;
		Evasiveness = 0 * Multiplier;
		Accuracy = 0 * Multiplier;
		Fortitude = 2 * Multiplier;
	}
	
	public int[] GetStats(){
		
		int TempStatArray[] = new int[6];
		TempStatArray[0] = Strength;
		TempStatArray[1] = Defense;
		TempStatArray[2] = Evasiveness;
		TempStatArray[3] = Accuracy;
		TempStatArray[4] = Fortitude;
		TempStatArray[5] = Condition;
		
		return TempStatArray;
		
	}
	
}
