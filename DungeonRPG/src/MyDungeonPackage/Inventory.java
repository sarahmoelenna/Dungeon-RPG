package MyDungeonPackage;

import java.util.ArrayList;

public class Inventory {

	ArrayList<Item> ItemList = new ArrayList<>();
	ArrayList<Item> ItemRemoveList = new ArrayList<>();
	
	ArrayList<StatusEffects> StatusEffectList = new ArrayList<>();
	
	Item RingItem;
	Item HelmItem;
	Item ChestItem;
	Item SwordItem;
	Item AmuletItem;
	Item GloveItem;
	Item GreavesItem;
	Item BootsItem;
	
	boolean RingOn;
	boolean HelmOn;
	boolean ChestOn;
	boolean SwordOn;
	boolean AmuletOn;
	boolean GloveOn;
	boolean GreavesOn;
	boolean BootsOn;
	
	int Strength;
	int Defense;
	int Evasiveness;
	int Accuracy;
	int Fortitude;
	int ConditionDamage;
	
	boolean invopen;
	
	float InvTimer;
	int ListPosition;
	
	float baseHealth;
	float baseAccuracy;
	float baseEvasiness;
	PlayerType MyPlayerType;
	
	int FuryAmount;
	int LeechAmount;
	int ProtectionAmount;
	int ThornsAmount;
	int TormentAmount;
	int ColdAmount;
	
	public Inventory(PlayerType myPlayerType){
		
		MyPlayerType = myPlayerType;
		
		RingOn = false;
		HelmOn = false;
		ChestOn = false;
		SwordOn = false;
		AmuletOn = false;
		GloveOn = false;
		GreavesOn = false;
		BootsOn = false;
		
		Strength = 0;
		Defense = 0;
		Evasiveness = 0;
		Accuracy = 0;
		Fortitude = 0;
		ConditionDamage = 0;
		
		invopen = false;
		
		ListPosition = 0;
		InvTimer = 0;
		
		GenerateStarterItems();
		
		baseHealth = 500.0f;
		baseAccuracy = 80.0f;
		baseEvasiness = 10.0f;
		
		
		FuryAmount = 0;
		LeechAmount = 0;
		ProtectionAmount = 0;
		ThornsAmount = 0;
		TormentAmount = 0;
		ColdAmount = 0;
	}

	public void Update(int delta){
		if(invopen == true){
			
			InvTimer += 0.01f * delta;
			if(InvTimer > 1){InvTimer = 1;}
			
			if(Input.GetW() == true && InvTimer == 1 && ListPosition > 0){
				ListPosition--;
				InvTimer = 0;
			}
			
			if(Input.GetS() == true && InvTimer == 1 && ListPosition < ItemList.size()-1){
				ListPosition++;
				InvTimer = 0;
			}
			
			if(ListPosition > ItemList.size()){ListPosition = ItemList.size()-1;}
			if(ListPosition < 0){ListPosition = 0;}
			
			if(Input.GetF() == true && InvTimer == 1){
				EquipItem(ItemList.get(ListPosition));
				InvTimer = 0;
			}
			
			if(Input.GetM() == true && InvTimer == 1 && ListPosition < ItemList.size()){
				if(ItemList.get(ListPosition).Equipped == false){
					ItemRemoveList.add(ItemList.get(ListPosition));
					InvTimer = 0;
				}
			}
			
			for(Item MyItem: ItemRemoveList){
				ItemList.remove(MyItem);
			}
			
			
		}
	}
	
	public void AddItem(Item MyItem){
		ItemList.add(MyItem);
	}
	
	public void DrawInventoryList(){
		//System.out.println(ItemList.size());
		ShaderHandler.TextShader.Activate();
		
		int offset = 0;
		if(ListPosition > GameData.Height/15 - 1){
			offset = ListPosition - (GameData.Height/15 - 2) ;
		}
		
		for(int i = 0; i < GameData.Height/15 - 1 + offset && i < ItemList.size(); i++){
			if(ItemList.get(i).Equipped == true){
				if(ListPosition == i){
					FontHandler.ElitePro.DisplayFont(20, GameData.Height - i*15 - 15 + 15*offset, 0.6f,"-> " + ItemList.get(i).Getname() + "-Equip <-");
				}
				else{
					FontHandler.ElitePro.DisplayFont(20, GameData.Height - i*15 - 15 + 15*offset, 0.6f, ItemList.get(i).Getname() + "-Equip");
				}
			}
			else{
				if(ListPosition == i){
					FontHandler.ElitePro.DisplayFont(20, GameData.Height - i*15 - 15 + 15*offset, 0.6f,"-> " +  ItemList.get(i).Getname());
				}
				else{
					FontHandler.ElitePro.DisplayFont(20, GameData.Height - i*15 - 15 + 15*offset, 0.6f, ItemList.get(i).Getname());
				}
			}
			
		}
		DrawCurrentInfo();
		ShaderHandler.TextShader.DeActivate();
		
	}

	public void EquipItem(Item MyItem){
		
		if(MyItem.Equipped == false){
		if(MyItem.MyType == ItemType.Sword){
			if(SwordOn == true){
				if(SwordItem.MyAdjective != ItemAdjective.Cursed){
					SwordItem.Equipped = false;
					MyItem.Equipped = true;
					SwordItem = MyItem;
				}
			}
			else{
				MyItem.Equipped = true;
				SwordItem = MyItem;
				SwordOn = true;
			}
		}
		
		
		if(MyItem.MyType == ItemType.Helm){
			if(HelmOn == true){
				if(HelmItem.MyAdjective != ItemAdjective.Cursed){
					HelmItem.Equipped = false;
					MyItem.Equipped = true;
					HelmItem = MyItem;
				}
			}
			else{
				MyItem.Equipped = true;
				HelmItem = MyItem;
				HelmOn = true;
			}
		}
		
		
		if(MyItem.MyType == ItemType.Boots){
			if(BootsOn == true){
				if(BootsItem.MyAdjective != ItemAdjective.Cursed){
					MyItem.Equipped = true;
					BootsItem.Equipped = false;
					BootsItem = MyItem;
				}
			}
			else{
				MyItem.Equipped = true;
				BootsItem = MyItem;
				BootsOn = true;
			}
		}
		
		if(MyItem.MyType == ItemType.Chest){
			if(ChestOn == true){
				if(ChestItem.MyAdjective != ItemAdjective.Cursed){
					MyItem.Equipped = true;
					ChestItem.Equipped = false;
					ChestItem = MyItem;
				}
			}
			else{
				MyItem.Equipped = true;
				ChestItem = MyItem;
				ChestOn = true;
			}
		}
		
		if(MyItem.MyType == ItemType.Gloves){
			if(GloveOn == true){
				if(GloveItem.MyAdjective != ItemAdjective.Cursed){
					MyItem.Equipped = true;
					GloveItem.Equipped = false;
					GloveItem = MyItem;
				}
			}
			else{
				MyItem.Equipped = true;
				GloveItem = MyItem;
				GloveOn = true;
			}
		}
		
		if(MyItem.MyType == ItemType.Greaves){
			if(GreavesOn == true){
				if(GreavesItem.MyAdjective != ItemAdjective.Cursed){
					MyItem.Equipped = true;
					GreavesItem.Equipped = false;
					GreavesItem = MyItem;
				}
			}
			else{
				MyItem.Equipped = true;
				GreavesItem = MyItem;
				GreavesOn = true;
			}
		}
		
		if(MyItem.MyType == ItemType.Ring){
			if(RingOn == true){
				if(RingItem.MyAdjective != ItemAdjective.Cursed){
					MyItem.Equipped = true;
					RingItem.Equipped = false;
					RingItem = MyItem;
				}
			}
			else{
				MyItem.Equipped = true;
				RingItem = MyItem;
				RingOn = true;
			}
		}
		
		if(MyItem.MyType == ItemType.Amulet){
			if(AmuletOn == true){
				if(AmuletItem.MyAdjective != ItemAdjective.Cursed){
					MyItem.Equipped = true;
					AmuletItem.Equipped = false;
					AmuletItem = MyItem;
				}
			}
			else{
				MyItem.Equipped = true;
				AmuletItem = MyItem;
				AmuletOn = true;
			}
		}
		}
		
		RecalculateStats();
		CalculateStatusEffects();
		PlayerStats();
	}

	private void GenerateStarterItems(){
		
		Item TempItem = new Item(0);
		while(TempItem.MyType != ItemType.Sword){
			TempItem = new Item(0);
		}
		EquipItem(TempItem);
		AddItem(TempItem);
		
		while(TempItem.MyType == ItemType.Sword){
			TempItem = new Item(0);
		}
		EquipItem(TempItem);
		AddItem(TempItem);
		
	}

	private void RecalculateStats(){
		
		Strength = 0;
		Defense = 0;
		Evasiveness = 0;
		Accuracy = 0;
		Fortitude = 0;
		ConditionDamage = 0;
		
		if(SwordOn == true){
			int TempArray[] = SwordItem.GetStats();
			Strength += TempArray[0];
			Defense += TempArray[1];
			Evasiveness += TempArray[2];
			Accuracy += TempArray[3];
			Fortitude += TempArray[4];
			ConditionDamage += TempArray[5];
		}
		//System.out.println(Strength);
		if(HelmOn == true){
			int TempArray[] = HelmItem.GetStats();
			Strength += TempArray[0];
			Defense += TempArray[1];
			Evasiveness += TempArray[2];
			Accuracy += TempArray[3];
			Fortitude += TempArray[4];
			ConditionDamage += TempArray[5];
		}
		//System.out.println(Strength);
		if(RingOn == true){
			int TempArray[] = RingItem.GetStats();
			Strength += TempArray[0];
			Defense += TempArray[1];
			Evasiveness += TempArray[2];
			Accuracy += TempArray[3];
			Fortitude += TempArray[4];
			ConditionDamage += TempArray[5];
		}
		//System.out.println(Strength);
		if(ChestOn == true){
			int TempArray[] = ChestItem.GetStats();
			Strength += TempArray[0];
			Defense += TempArray[1];
			Evasiveness += TempArray[2];
			Accuracy += TempArray[3];
			Fortitude += TempArray[4];
			ConditionDamage += TempArray[5];
		}
		//System.out.println(Strength);
		if(AmuletOn == true){
			int TempArray[] = AmuletItem.GetStats();
			Strength += TempArray[0];
			Defense += TempArray[1];
			Evasiveness += TempArray[2];
			Accuracy += TempArray[3];
			Fortitude += TempArray[4];
			ConditionDamage += TempArray[5];
		}
		//System.out.println(Strength);
		if(GloveOn == true){
			int TempArray[] = GloveItem.GetStats();
			Strength += TempArray[0];
			Defense += TempArray[1];
			Evasiveness += TempArray[2];
			Accuracy += TempArray[3];
			Fortitude += TempArray[4];
			ConditionDamage += TempArray[5];
		}
		//System.out.println(Strength);
		if(BootsOn == true){
			int TempArray[] = BootsItem.GetStats();
			Strength += TempArray[0];
			Defense += TempArray[1];
			Evasiveness += TempArray[2];
			Accuracy += TempArray[3];
			Fortitude += TempArray[4];
			ConditionDamage += TempArray[5];
		}
		//System.out.println(Strength);
		if(GreavesOn == true){
			int TempArray[] = GreavesItem.GetStats();
			Strength += TempArray[0];
			Defense += TempArray[1];
			Evasiveness += TempArray[2];
			Accuracy += TempArray[3];
			Fortitude += TempArray[4];
			ConditionDamage += TempArray[5];
		}
		//System.out.println(Strength);
	}

	private void CalculateStatusEffects(){
		StatusEffectList.clear();
		FuryAmount = 0;
		LeechAmount = 0;
		ProtectionAmount = 0;
		ThornsAmount = 0;
		TormentAmount = 0;
		ColdAmount = 0;
		
		//boots
		if(BootsOn == true){
			if(BootsItem.HasAdj == true){
				CheckForStatus(BootsItem, false);
			}
			if(BootsItem.HasDes == true){
				CheckForStatusDescriber(BootsItem);
			}
		}
		
		if(SwordOn == true){
			if(SwordItem.HasAdj == true){
				CheckForStatus(SwordItem, true);
			}
			if(SwordItem.HasDes == true){
				CheckForStatusDescriber(SwordItem);
			}
		}
		
		if(ChestOn == true){
			if(ChestItem.HasAdj == true){
				CheckForStatus(ChestItem, false);
			}
			if(ChestItem.HasDes == true){
				CheckForStatusDescriber(ChestItem);
			}
		}
		
		if(RingOn == true){
			if(RingItem.HasAdj == true){
				CheckForStatus(RingItem, false);
			}
			if(RingItem.HasDes == true){
				CheckForStatusDescriber(RingItem);
			}
		}
		
		if(AmuletOn == true){
			if(AmuletItem.HasAdj == true){
				CheckForStatus(AmuletItem, false);
			}
			if(AmuletItem.HasDes == true){
				CheckForStatusDescriber(AmuletItem);
			}
		}
		
		if(HelmOn == true){
			if(HelmItem.HasAdj == true){
				CheckForStatus(HelmItem, false);
			}
			if(HelmItem.HasDes == true){
				CheckForStatusDescriber(HelmItem);
			}
		}
		
		if(GreavesOn == true){
			if(GreavesItem.HasAdj == true){
				CheckForStatus(GreavesItem, false);
			}
			if(GreavesItem.HasDes == true){
				CheckForStatusDescriber(GreavesItem);
			}
		}
		
		if(GloveOn == true){
			if(GloveItem.HasAdj == true){
				CheckForStatus(GloveItem, false);
			}
			if(GloveItem.HasDes == true){
				CheckForStatusDescriber(GloveItem);
			}
		}
	}

	private void CheckForStatus(Item MyItem, boolean sword){
		StatusEffects TempEffect = new StatusEffects(StatusEffectType.Poison, StatusEffectCondition.Random);
		boolean safe = false;
		if(MyItem.MyAdjective == ItemAdjective.Venomous){
			if(sword == false){
				TempEffect = new StatusEffects(StatusEffectType.Poison, StatusEffectCondition.OnDamage);
			}
			else{
				TempEffect = new StatusEffects(StatusEffectType.Poison, StatusEffectCondition.OnHit);
			}
			safe = true;
		}
		else if(MyItem.MyAdjective == ItemAdjective.Enflamed){
			if(sword == false){
				TempEffect = new StatusEffects(StatusEffectType.Burning, StatusEffectCondition.OnDamage);
			}
			else{
				TempEffect = new StatusEffects(StatusEffectType.Burning, StatusEffectCondition.OnHit);
			}
			safe = true;
		}
		else if(MyItem.MyAdjective == ItemAdjective.Glacial){
			if(sword == false){
				TempEffect = new StatusEffects(StatusEffectType.Cold, StatusEffectCondition.OnDamage);
			}
			else{
				TempEffect = new StatusEffects(StatusEffectType.Cold, StatusEffectCondition.OnHit);
			}
			safe = true;
		}
		
		
		boolean existing = false;
		if(safe == true){
			for(StatusEffects MyEffect: StatusEffectList){
				if(MyEffect.MyStatusType == TempEffect.MyStatusType && MyEffect.MyCondition == TempEffect.MyCondition){
					existing = true;
				}
			}
		}
		
		if(existing == false){
			//System.out.println("status added");
			StatusEffectList.add(TempEffect);
		}
		
	}

	private void CheckForStatusDescriber(Item MyItem){
		StatusEffects TempEffect = new StatusEffects(StatusEffectType.Poison, StatusEffectCondition.Random);
		boolean safe = false;
		if(MyItem.MyDescriber == ItemDescriber.Protection){
			TempEffect = new StatusEffects(StatusEffectType.Protection, StatusEffectCondition.Permanent);
			ProtectionAmount++;
			safe = true;
		}
		else if(MyItem.MyDescriber == ItemDescriber.Fury){
			TempEffect = new StatusEffects(StatusEffectType.Fury, StatusEffectCondition.Permanent);
			FuryAmount++;
			safe = true;
		}
		else if(MyItem.MyDescriber == ItemDescriber.Shivering){
			TempEffect = new StatusEffects(StatusEffectType.Cold, StatusEffectCondition.Permanent);
			ColdAmount++;
			safe = true;
		}
		else if(MyItem.MyDescriber == ItemDescriber.Torment){
			TempEffect = new StatusEffects(StatusEffectType.Torment, StatusEffectCondition.Permanent);
			TormentAmount++;
			safe = true;
		}
		else if(MyItem.MyDescriber == ItemDescriber.Thorns){
			TempEffect = new StatusEffects(StatusEffectType.Torment, StatusEffectCondition.Surrounding);
			ThornsAmount++;
			safe = true;
		}
		else if(MyItem.MyDescriber == ItemDescriber.Leeching){
			TempEffect = new StatusEffects(StatusEffectType.Leeching, StatusEffectCondition.Permanent);
			LeechAmount++;
			safe = true;
		}
		
		
		boolean existing = false;
		if(safe == true){
			for(StatusEffects MyEffect: StatusEffectList){
				if(MyEffect.MyStatusType == TempEffect.MyStatusType && MyEffect.MyCondition == TempEffect.MyCondition){
					existing = true;
				}
			}
		}
		
		if(existing == false){
			//System.out.println("status added");
			StatusEffectList.add(TempEffect);
		}
	}

	private void PlayerStats(){
		if(MyPlayerType == PlayerType.Warrior){
			Strength += 5;
		}
		else if(MyPlayerType == PlayerType.Paladin){
			Fortitude += 10;
			Defense += 2;
		}
		else if(MyPlayerType == PlayerType.Thief){
			Evasiveness += 4;
			Accuracy += 4;
		}
		if(MyPlayerType == PlayerType.Necromancer){
			ConditionDamage += 2;
		}
	}

	private void DrawCurrentInfo(){
		//boots
		FontHandler.ElitePro.DisplayFont(950, GameData.Height - 50 , 1.0f,"Current Equipment");
		FontHandler.ElitePro.DisplayFont(950, GameData.Height - 90 , 0.6f,"Head: ");
		if(HelmOn == true){
			FontHandler.ElitePro.DisplayFont(1080, GameData.Height - 90 , 0.6f, HelmItem.Getname());
		}
		FontHandler.ElitePro.DisplayFont(950, GameData.Height - 130 , 0.6f,"Neck: ");
		if(AmuletOn == true){
			FontHandler.ElitePro.DisplayFont(1080, GameData.Height - 130 , 0.6f, AmuletItem.Getname());
		}
		FontHandler.ElitePro.DisplayFont(950, GameData.Height - 170 , 0.6f,"Chest: ");
		if(ChestOn == true){
			FontHandler.ElitePro.DisplayFont(1080, GameData.Height - 170 , 0.6f, ChestItem.Getname());
		}
		FontHandler.ElitePro.DisplayFont(950, GameData.Height - 210 , 0.6f,"Weapon: ");
		if(SwordOn == true){
			FontHandler.ElitePro.DisplayFont(1080, GameData.Height - 210 , 0.6f, SwordItem.Getname());
		}
		FontHandler.ElitePro.DisplayFont(950, GameData.Height - 250 , 0.6f,"Hand: ");
		if(GloveOn == true){
			FontHandler.ElitePro.DisplayFont(1080, GameData.Height - 250 , 0.6f, GloveItem.Getname());
		}
		FontHandler.ElitePro.DisplayFont(950, GameData.Height - 290 , 0.6f,"Ring: ");
		if(RingOn == true){
			FontHandler.ElitePro.DisplayFont(1080, GameData.Height - 290 , 0.6f, RingItem.Getname());
		}
		FontHandler.ElitePro.DisplayFont(950, GameData.Height - 340 , 0.6f,"Legs: ");
		if(GreavesOn == true){
			FontHandler.ElitePro.DisplayFont(1080, GameData.Height - 340 , 0.6f, GreavesItem.Getname());
		}
		FontHandler.ElitePro.DisplayFont(950, GameData.Height - 370 , 0.6f,"Feet: ");
		if(BootsOn == true){
			FontHandler.ElitePro.DisplayFont(1080, GameData.Height - 370 , 0.6f, BootsItem.Getname());
		}
		
		FontHandler.ElitePro.DisplayFont(950, GameData.Height - 450 , 1.0f,"Current Stats");
		FontHandler.ElitePro.DisplayFont(950, GameData.Height - 490 , 0.6f, "Strength: " + Strength);
		FontHandler.ElitePro.DisplayFont(950, GameData.Height - 530 , 0.6f, "Defense: " + Defense);
		FontHandler.ElitePro.DisplayFont(950, GameData.Height - 570 , 0.6f, "Fortitude: " + Fortitude);
		FontHandler.ElitePro.DisplayFont(950, GameData.Height - 610 , 0.6f, "Accuracy: " + (80 + Accuracy * 0.5));
		FontHandler.ElitePro.DisplayFont(950, GameData.Height - 650 , 0.6f, "Evasiveness: " + (10 + Evasiveness * 0.5));
		FontHandler.ElitePro.DisplayFont(950, GameData.Height - 690 , 0.6f, "Condition: " + ConditionDamage);
		
		FontHandler.ElitePro.DisplayFont(950, GameData.Height - 750 , 1.0f,"Current Status Effects");
		int i = 1;
		if(ProtectionAmount > 0){
			FontHandler.ElitePro.DisplayFont(950, GameData.Height - 750 - 40*i , 0.6f, "Protection x" + ProtectionAmount);
			i++;
		}
		if(FuryAmount > 0){
			FontHandler.ElitePro.DisplayFont(950, GameData.Height - 750 - 40*i , 0.6f, "Fury x" + FuryAmount);
			i++;
		}
		if(LeechAmount > 0){
			FontHandler.ElitePro.DisplayFont(950, GameData.Height - 750 - 40*i , 0.6f, "Leeching x" + LeechAmount);
			i++;
		}
		if(ThornsAmount > 0){
			FontHandler.ElitePro.DisplayFont(950, GameData.Height - 750 - 40*i , 0.6f, "Thorns x" + ThornsAmount);
			i++;
		}
		if(TormentAmount > 0){
			FontHandler.ElitePro.DisplayFont(950, GameData.Height - 750 - 40*i , 0.6f, "Torment x" + TormentAmount);
			i++;
		}
		if(ColdAmount > 0){
			FontHandler.ElitePro.DisplayFont(950, GameData.Height - 750 - 40*i , 0.6f, "Cold x" + ColdAmount);
			i++;
		}
	}
}
