package MyDungeonPackage;

public class TileSet {
	
	ModelData TileData;
	ModelData TileRoofData;
	ModelData WallData;
	ModelData WallStandaloneData;
	ModelData WallStraightData;
	ModelData WallCornerData;
	ModelData WallCapData;
	ModelData WallTData;
	ModelData WallCrossData;
	ModelData GrassData;
	ModelData TileSpikeData;
	ModelData LampData;
	
	ModelData TileActiveData;
	ModelData TileInActiveData;
	ModelData PressurePadData;
	
	ModelData GrateData;
	
	int Rotation;
	
	public TileSet(float TileSize){
		
		ModelHandler.AddModelData("TempleTileTwo");
		ModelHandler.AddModelData("TempleTileTwoRoof");
		ModelHandler.AddModelData("TempleWall");
		ModelHandler.AddModelData("TempleGrass");
		ModelHandler.AddModelData("TempleWallStandalone");
		ModelHandler.AddModelData("TempleWallStraight");
		ModelHandler.AddModelData("TempleWallCorner");
		ModelHandler.AddModelData("TempleWallCap");
		ModelHandler.AddModelData("TempleWallT");
		ModelHandler.AddModelData("TempleWallCross");
		ModelHandler.AddModelData("TempleSpikeTile");
		ModelHandler.AddModelData("lamp");
		ModelHandler.AddModelData("pressurepadbase");
		ModelHandler.AddModelData("grate");
		ModelHandler.AddModelData("templetileactive");
		ModelHandler.AddModelData("templetileinactive");
		
		TileData = ModelHandler.FindModelData("TempleTileTwo");
		TileRoofData = ModelHandler.FindModelData("TempleTileTwoRoof");
		WallData = ModelHandler.FindModelData("TempleWall");
		GrassData = ModelHandler.FindModelData("TempleGrass");
		WallStandaloneData = ModelHandler.FindModelData("TempleWallStandalone");
		WallStraightData = ModelHandler.FindModelData("TempleWallStraight");
		WallCornerData = ModelHandler.FindModelData("TempleWallCorner");
		WallCapData = ModelHandler.FindModelData("TempleWallCap");
		WallTData = ModelHandler.FindModelData("TempleWallT");
		WallCrossData = ModelHandler.FindModelData("TempleWallCross");
		TileSpikeData = ModelHandler.FindModelData("TempleSpikeTile");
		LampData = ModelHandler.FindModelData("lamp");
		PressurePadData = ModelHandler.FindModelData("pressurepadbase");
		GrateData = ModelHandler.FindModelData("grate");
		
		TileActiveData = ModelHandler.FindModelData("templetileactive");
		TileInActiveData = ModelHandler.FindModelData("templetileinactive");
		
		Rotation = 0;
	}
	
	public ModelData GetObject(int Center, int Up, int Down, int Left, int Right, int UpLeft, int UpRight, int DownLeft, int DownRight){
		Rotation = 0;
		if(Center == 2 || Center == 5){
			return GrassData;
		}
		else if(Center == 3 || Center == 10 || Center == 11 || Center == 12 || Center == 13 || Center == 14){
			return TileData;
		}
		else if(Center == 4 || Center == 9){
			return TileSpikeData;
		}
		else if(Center == 6){
			return TileInActiveData;
		}
		else if(Center == 7){
			return TileActiveData;
		}
		else if(Center == 8){
			return PressurePadData;
		}
		else if(Center == 1){
			
			
			if(Up != 1 && Down != 1 && Left != 1 && Right != 1){
				return WallStandaloneData;
			}
			//caps
			else if(Up == 1 && Down != 1 && Left != 1 && Right != 1){
				Rotation = 270;
				return WallCapData;
			}
			else if(Up != 1 && Down != 1 && Left == 1 && Right != 1){
				Rotation = 180;
				return WallCapData;
			}
			else if(Up != 1 && Down == 1 && Left != 1 && Right != 1){
				Rotation = 90;
				return WallCapData;
			}
			else if(Up != 1 && Down != 1 && Left != 1 && Right == 1){
				//Rotation = 270;
				return WallCapData;
			}
			//horizontal
			else if(Up != 1 && Down != 1 && Left == 1 && Right == 1){
				return WallStraightData;
			}
			else if(Up == 1 && Down == 1 && Left != 1 && Right != 1){
				Rotation = 90;
				return WallStraightData;
			}
			//corner
			else if(Up == 1 && Down != 1 && Left != 1 && Right == 1){
				//Rotation = 270;
				return WallCornerData;
			}
			else if(Up == 1 && Down != 1 && Left == 1 && Right != 1){
				Rotation = 270;
				return WallCornerData;
			}
			else if(Up != 1 && Down == 1 && Left == 1 && Right != 1){
				Rotation = 180;
				return WallCornerData;
			}
			else if(Up != 1 && Down == 1 && Left != 1 && Right == 1){
				Rotation = 90;
				return WallCornerData;
			}
			//T section
			else if(Up == 1 && Down == 1 && Left != 1 && Right == 1){
				//Rotation = 270;
				return WallTData;
			}
			else if(Up == 1 && Down != 1 && Left == 1 && Right == 1){
				Rotation = 270;
				return WallTData;
			}
			else if(Up == 1 && Down == 1 && Left == 1 && Right != 1){
				Rotation = 180;
				return WallTData;
			}
			else if(Up != 1 && Down == 1 && Left == 1 && Right == 1){
				Rotation = 90;
				return WallTData;
			}
			//cross
			else if(Up == 1 && Down == 1 && Left == 1 && Right == 1){
				Rotation = 90;
				return WallCrossData;
			}
			
			else{
				return WallData;
			}
			
			
			//return WallData;
		}
		else{
			return WallData;
		}
		
		
	}

	public int GetRotation(){
		return Rotation;
	}
	
}
