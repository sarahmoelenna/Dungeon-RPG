package MyDungeonPackage;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

public class LevelEditor {

	MyTextureClass MyGridTexture;
	MyTextureClass MyGridSelectionTexture;
	MyTextureClass MyGridColourTexture;
	SaveLevel MySave;
	int Width;
	int Height;
	int GridTypeArray[];
	int GridEntityTypeArray[];
	int GridEntityRotationArray[];
	int GridEntityDataOneArray[];
	int GridLogicTypeArray[];
	int GridDataOneArray[];
	int GridDataTwoArray[];
	int GridDataThreeArray[];
	int GridFlatWallArray[];

	int gridX;
	int gridY;

	float PauseTimer;

	boolean TileSelected;
	boolean AReleased;
	boolean BReleased;
	boolean YReleased;
	boolean SpaceReleased;
	boolean SaveMenu;
	int selection;

	int GridData;
	String SaveName;

	boolean MainMenu;

	boolean HasNextFloor;
	boolean Lights;
	String LevelName;
	String NextLevelName;
	int FloorNumber;
	int Time;

	boolean InputText;
	boolean DialogueMenu;
	int StringInputType; // level name
	int DialogueNumber;

	ControllerStringInput MyInput;
	ArrayList<DialogueData> DialogueList = new ArrayList<>();

	public LevelEditor() {
		ModelHandler.AddTexture("levelgrid");
		MyGridTexture = ModelHandler.FindTexture("levelgrid");

		ModelHandler.AddTexture("gridselection");
		MyGridSelectionTexture = ModelHandler.FindTexture("gridselection");

		ModelHandler.AddTexture("gridcolour");
		MyGridColourTexture = ModelHandler.FindTexture("gridcolour");

		DialogueNumber = 0;
		
		Width = 32;
		Height = 32;
		GridTypeArray = new int[1024];
		GridEntityTypeArray = new int[1024];
		GridEntityRotationArray = new int[1024];
		GridDataOneArray = new int[1024];
		GridDataTwoArray = new int[1024];
		GridDataThreeArray = new int[1024];
		GridEntityDataOneArray = new int[1024];
		GridLogicTypeArray = new int[1024];
		GridFlatWallArray = new int[1024];

		gridX = 1;
		gridY = 1;
		PauseTimer = 1;
		GridData = 0;

		TileSelected = false;
		AReleased = false;
		BReleased = false;
		YReleased = false;
		SpaceReleased = false;
		selection = 0;
		SaveMenu = false;
		DialogueMenu = false;
		MainMenu = false;
		SaveName = "TestFile.Dungeon";
		// System.out.println(GameData.CustomEditor);

		HasNextFloor = false;
		Lights = true;
		LevelName = "Default";
		NextLevelName = "Blank";
		FloorNumber = 1;
		Time = 0;

		if (GameData.CustomEditor == false) {
			OnStart();
		} else {
			LoadLevelForEdit();
			SaveName = GameData.LoadName;
		}

		MySave = new SaveLevel(SaveName);
		InputText = false;
		MyInput = new ControllerStringInput(10, 1);
		StringInputType = 0;

	}

	private void LineEdge() {
		for (int i = 0; i < Width; i++) {
			GridTypeArray[i] = 0;
			GridTypeArray[i + Width * (Height - 1)] = 0;
		}
		for (int i = 0; i < Height; i++) {
			GridTypeArray[Width * i] = 0;
			GridTypeArray[Width * i + Width - 1] = 0;

		}
	}

	public void OnStart() {
		for (int i = 0; i < 1024; i++) {
			GridTypeArray[i] = 2;
			GridEntityTypeArray[i] = 0;
			GridEntityRotationArray[i] = 1;
			GridDataOneArray[i] = 0;
			GridDataTwoArray[i] = 0;
			GridDataThreeArray[i] = 0;
			GridEntityDataOneArray[i] = 0;
			GridLogicTypeArray[i] = 0;
			GridFlatWallArray[i] = 0;
		}
		FillDialogue();
		LineEdge();
	}

	private void LoadLevelForEdit() {
		LoadDungeon TempLoad;
		TempLoad = new LoadDungeon(GameData.LoadName, true);

		GridTypeArray = TempLoad.GridTypeArray;
		GridEntityTypeArray = TempLoad.GridEntityTypeArray;
		GridEntityRotationArray = TempLoad.GridRotationTypeArray;
		GridEntityDataOneArray = TempLoad.GridEntityDataArray;
		GridLogicTypeArray = TempLoad.GridLogicTypeArray;
		GridDataOneArray = TempLoad.GridDataOneArray;
		GridDataTwoArray = TempLoad.GridDataTwoArray;
		GridDataThreeArray = TempLoad.GridDataThreeArray;
		GridFlatWallArray = TempLoad.GridFlatWallArray;
		DialogueList.addAll(TempLoad.DialogueList);
		FillDialogue();

		HasNextFloor = TempLoad.HasNextFloor;
		Lights = TempLoad.Lights;
		NextLevelName = TempLoad.NextLevelName;
		FloorNumber = TempLoad.FloorNumber;
		Time = TempLoad.Time;

		LevelName = GameData.LoadName;
		LevelName = LevelName.replaceAll(".Dungeon", "");

		LineEdge();

		GameData.CustomEditor = false;
	}

	public void Update(int delta) {
		if (InputText == false) {
			PauseTimer += 0.008f * delta;
			if (PauseTimer > 1) {
				PauseTimer = 1;
			}

			if (PauseTimer == 1 && Input.GetW() == true && !TileSelected
					&& !SaveMenu) {
				gridY--;
				PauseTimer = 0;
			}

			if (PauseTimer == 1 && Input.GetS() == true && !TileSelected
					&& !SaveMenu) {
				gridY++;
				PauseTimer = 0;
			}

			if (PauseTimer == 1 && Input.GetA() == true && !TileSelected
					&& !SaveMenu) {
				gridX--;
				PauseTimer = 0;
			}

			if (PauseTimer == 1 && Input.GetD() == true && !TileSelected
					&& !SaveMenu) {
				gridX++;
				PauseTimer = 0;
			}

			if (gridX < 1) {
				gridX = 30;
			}
			if (gridX > 30) {
				gridX = 1;
			}

			if (gridY < 1) {
				gridY = 30;
			}
			if (gridY > 30) {
				gridY = 1;
			}

			if ((Input.GetF() != true && Input.GetEnter() != true)) {
				AReleased = true;
			}
			if ((Input.GetI() != true)) {
				BReleased = true;
			}
			if ((Input.GetR() != true)) {
				YReleased = true;
			}
			if ((Input.GetSpace() != true)) {
				SpaceReleased = true;
			}

			if (Input.GetSpace() && SpaceReleased == true) {
				GridData++;
				SpaceReleased = false;
			}
			if (GridData > 8) {
				GridData = 0;
			}

			if ((Input.GetF() || Input.GetEnter()) && AReleased
					&& !TileSelected) {
				TileSelected = true;
				AReleased = false;
			}
			if ((Input.GetI() == true) && BReleased && TileSelected && SaveMenu == false && DialogueMenu == false) {
				TileSelected = false;
				BReleased = false;
			}

			// tile selected
			if (PauseTimer == 1 && Input.GetD() == true && TileSelected) {
				int increment = 1;
				if (Input.GetY()) {
					increment = 50;
				}
				if (selection == 0) {
					GridTypeArray[gridX + gridY * 32] += increment;
					PauseTimer = 0;
				} else if (selection == 1) {
					GridEntityTypeArray[gridX + gridY * 32] += increment;
					PauseTimer = 0;
				} else if (selection == 2) {
					GridEntityRotationArray[gridX + gridY * 32] += increment;
					PauseTimer = 0;
					if (GridEntityRotationArray[gridX + gridY * 32] > 4) {
						GridEntityRotationArray[gridX + gridY * 32] = 1;
					}
				} else if (selection == 3) {
					GridEntityDataOneArray[gridX + gridY * 32] += increment;
					PauseTimer = 0;
				} else if (selection == 4) {
					GridLogicTypeArray[gridX + gridY * 32] += increment;
					PauseTimer = 0;
				} else if (selection == 5) {
					GridDataOneArray[gridX + gridY * 32] += increment;
					PauseTimer = 0;
				} else if (selection == 6) {
					GridDataTwoArray[gridX + gridY * 32] += increment;
					PauseTimer = 0;
				} else if (selection == 7) {
					GridDataThreeArray[gridX + gridY * 32] += increment;
					PauseTimer = 0;
				} else if (selection == 8) {
					GridFlatWallArray[gridX + gridY * 32] += increment;
					PauseTimer = 0;
				}
			}
			if (PauseTimer == 1 && Input.GetA() == true && TileSelected) {
				int increment = 1;
				if (Input.GetY()) {
					increment = 50;
				}
				if (selection == 0) {
					GridTypeArray[gridX + gridY * 32] -= increment;
					PauseTimer = 0;
					if (GridTypeArray[gridX + gridY * 32] < 0) {
						GridTypeArray[gridX + gridY * 32] = 0;
					}
				} else if (selection == 1) {
					GridEntityTypeArray[gridX + gridY * 32] -= increment;
					PauseTimer = 0;
					if (GridEntityTypeArray[gridX + gridY * 32] < 0) {
						GridEntityTypeArray[gridX + gridY * 32] = 0;
					}
				} else if (selection == 2) {
					GridEntityRotationArray[gridX + gridY * 32] -= increment;
					PauseTimer = 0;
					if (GridEntityRotationArray[gridX + gridY * 32] < 1) {
						GridEntityRotationArray[gridX + gridY * 32] = 4;
					}
				} else if (selection == 3) {
					GridEntityDataOneArray[gridX + gridY * 32] -= increment;
					PauseTimer = 0;
					if (GridEntityDataOneArray[gridX + gridY * 32] < 0) {
						GridEntityDataOneArray[gridX + gridY * 32] = 0;
					}
				} else if (selection == 4) {
					GridLogicTypeArray[gridX + gridY * 32] -= increment;
					PauseTimer = 0;
					if (GridLogicTypeArray[gridX + gridY * 32] < 0) {
						GridLogicTypeArray[gridX + gridY * 32] = 0;
					}
				} else if (selection == 5) {
					GridDataOneArray[gridX + gridY * 32] -= increment;
					PauseTimer = 0;
					if (GridDataOneArray[gridX + gridY * 32] < 0) {
						GridDataOneArray[gridX + gridY * 32] = 0;
					}
				} else if (selection == 6) {
					GridDataTwoArray[gridX + gridY * 32] -= increment;
					PauseTimer = 0;
					if (GridDataTwoArray[gridX + gridY * 32] < 0) {
						GridDataTwoArray[gridX + gridY * 32] = 0;
					}
				} else if (selection == 7) {
					GridDataThreeArray[gridX + gridY * 32] -= increment;
					PauseTimer = 0;
					if (GridDataThreeArray[gridX + gridY * 32] < 0) {
						GridDataThreeArray[gridX + gridY * 32] = 0;
					}
				} else if (selection == 8) {
					GridFlatWallArray[gridX + gridY * 32] -= increment;
					PauseTimer = 0;
					if (GridFlatWallArray[gridX + gridY * 32] < 0) {
						GridFlatWallArray[gridX + gridY * 32] = 0;
					}
				}
			}

			if (PauseTimer == 1 && Input.GetW() == true
					&& (TileSelected || SaveMenu)) {
				selection--;
				PauseTimer = 0;
			}
			if (PauseTimer == 1 && Input.GetS() == true
					&& (TileSelected || SaveMenu)) {
				selection++;
				PauseTimer = 0;
			}

			if (SaveMenu == false && DialogueMenu == false) {
				if (selection > 8) {selection = 0;}
				if (selection < 0) {selection = 8;}
			} 
			if (SaveMenu == true && DialogueMenu == false) {
				if (selection > 6) {selection = 0;}
				if (selection < 0) {selection = 6;}
			}
			if (DialogueMenu == true) {
				if (selection > 14) {selection = 0;}
				if (selection < 0) {selection = 14;}
			}

			if ((Input.GetI() == true) && BReleased && SaveMenu == true && DialogueMenu == false) {
				SaveMenu = false;
				BReleased = false;
			}

			if (Input.GetR() == true && SaveMenu == false && YReleased == true && !TileSelected && DialogueMenu == false) {
				SaveMenu = true;
				YReleased = false;
			}

			// System.out.println(PauseTimer + " " + gridX +" "+ gridY);
			if (Input.GetR() == true && SaveMenu == true && YReleased == true && DialogueMenu == false) {
				if (CheckLevelRequirements() == true) {
					MySave.SetLevelData(LevelName + ".Dungeon", NextLevelName,
							HasNextFloor, Lights, Time, FloorNumber);
					MySave.Save(GridTypeArray, GridEntityTypeArray,
							GridEntityRotationArray, GridEntityDataOneArray,
							GridLogicTypeArray, GridDataOneArray,
							GridDataTwoArray, GridDataThreeArray,
							GridFlatWallArray, DialogueList);
					YReleased = false;
					MainMenu = true;
				}
			}

			if (Input.GetEsc() == true) {
				MainMenu = true;
			}
			if(DialogueMenu == false){
				SaveMenuUpdate();
			}
			if(DialogueMenu == true){
				if ((Input.GetI() == true) && BReleased) {
					System.out.println("blargh");
					DialogueMenu = false;
					BReleased = false;
				}
				DialogueMenuUpdate();
			}
		} else {
			MyInput.Update(delta);
			if (MyInput.Done == true) {
				if (StringInputType == 1) {
					LevelName = MyInput.getLineOne(true);
					// System.out.println(MyInput.getLineOne(true));
				}
				if (StringInputType == 2) {
					NextLevelName = MyInput.getLineOne(true);
					// System.out.println(MyInput.getLineOne(true));
				}
				if (StringInputType == 3) {
					
					int r = FindDialogueRef(DialogueNumber);
					DialogueList.get(r).LineOne = MyInput.getLineOne(false);
					DialogueList.get(r).LineTwo = MyInput.getLineTwo(false);
					DialogueList.get(r).LineThree = MyInput.getLineThree(false);
					
					//NextLevelName = MyInput.getLineOne(true);
					// System.out.println(MyInput.getLineOne(true));
				}
				InputText = false;
			}
		}

	}

	private void SaveMenuUpdate() {
		if (PauseTimer == 1 && Input.GetF() == true && SaveMenu) {
			if (selection == 0) {
				MyInput = new ControllerStringInput(15, 1);
				InputText = true;
				StringInputType = 1;
				YReleased = false;
			}
			if (selection == 5) {
				MyInput = new ControllerStringInput(15, 1);
				InputText = true;
				StringInputType = 2;
				YReleased = false;
			}
			if (selection == 6) {
				DialogueMenu = true;
			}
		}

		if (PauseTimer == 1 && Input.GetD() == true && SaveMenu) {
			if (selection == 1) {
				Time++;
				if (Time > 2) {
					Time = 0;
				}
				PauseTimer = 0;
			} else if (selection == 2) {
				Lights = !Lights;
				PauseTimer = 0;
			} else if (selection == 3) {
				FloorNumber++;
				if (FloorNumber > 100) {
					FloorNumber = 1;
				}
				PauseTimer = 0;
			} else if (selection == 4) {
				HasNextFloor = !HasNextFloor;
				PauseTimer = 0;
			}

		}

		if (PauseTimer == 1 && Input.GetA() == true && SaveMenu) {
			if (selection == 1) {
				Time--;
				if (Time < 0) {
					Time = 2;
				}
				PauseTimer = 0;
			} else if (selection == 2) {
				Lights = !Lights;
				PauseTimer = 0;
			} else if (selection == 3) {
				FloorNumber--;
				if (FloorNumber < 1) {
					FloorNumber = 100;
				}
				PauseTimer = 0;
			} else if (selection == 4) {
				HasNextFloor = !HasNextFloor;
				PauseTimer = 0;
			}
		}
	}

	private void DialogueMenuUpdate(){
		if (PauseTimer == 1 && Input.GetF() == true && DialogueMenu && AReleased) {
			MyInput = new ControllerStringInput(58, 3);
			InputText = true;
			StringInputType = 3;
			DialogueNumber = selection;
			AReleased = false;
			YReleased = false;
		}
	}
	
	public void DrawLevelEditor() {
		ShaderHandler.TextShader.Activate();

		if (InputText == false) {
			if (DialogueMenu == false) {
				if (GridData == 0) {
					FontHandler.ElitePro.DisplayFont(50, 1050, 1.0f,
							"Tile Type Data");
				} else if (GridData == 1) {
					FontHandler.ElitePro.DisplayFont(50, 1050, 1.0f,
							"Entity Type Data");
				} else if (GridData == 2) {
					FontHandler.ElitePro.DisplayFont(50, 1050, 1.0f,
							"Entity Rotation Data");
				} else if (GridData == 3) {
					FontHandler.ElitePro.DisplayFont(50, 1050, 1.0f,
							"Entity Data");
				} else if (GridData == 4) {
					FontHandler.ElitePro.DisplayFont(50, 1050, 1.0f,
							"Logic Type Data");
				} else if (GridData == 5) {
					FontHandler.ElitePro.DisplayFont(50, 1050, 1.0f,
							"Logic Data One");
				} else if (GridData == 6) {
					FontHandler.ElitePro.DisplayFont(50, 1050, 1.0f,
							"Logic Data Two");
				} else if (GridData == 7) {
					FontHandler.ElitePro.DisplayFont(50, 1050, 1.0f,
							"Logic Data Three");
				} else if (GridData == 8) {
					FontHandler.ElitePro.DisplayFont(50, 1050, 1.0f,
							"Flat Wall Data");
				}

				// grid
				MyGridTexture.Draw();
				drawTexture(28, 28, 1024, 1024);

				// grid type
				for (int w = 1; w < 31; w++) {
					for (int h = 1; h < 31; h++) {
						int i = w + h * 32;
						MyGridColourTexture.Draw();
						if (GridTypeArray[i] == 1) {
							drawTexture(28 + w * 32, 1052 - h * 32 - 32, 32,
									32, 0, 0.25f, 0.25f, -0.25f);
						} else if (GridTypeArray[i] == 2
								|| GridTypeArray[i] == 5) {
							drawTexture(28 + w * 32, 1052 - h * 32 - 32, 32,
									32, 0.5f, 0.25f, 0.25f, -0.25f);
						} else if (GridTypeArray[i] == 3) {
							drawTexture(28 + w * 32, 1052 - h * 32 - 32, 32,
									32, 0.25f, 0.25f, 0.25f, -0.25f);
						} else if (GridTypeArray[i] == 4
								|| GridTypeArray[i] == 9) {
							drawTexture(28 + w * 32, 1052 - h * 32 - 32, 32,
									32, 0.75f, 0.25f, 0.25f, -0.25f);
						}
						if (GridTypeArray[i] == 6) {
							drawTexture(28 + w * 32, 1052 - h * 32 - 32, 32,
									32, 0, 0.5f, 0.25f, -0.25f);
						}
						if (GridTypeArray[i] == 7) {
							drawTexture(28 + w * 32, 1052 - h * 32 - 32, 32,
									32, 0.25f, 0.5f, 0.25f, -0.25f);
						}
					}
				}

				MyGridSelectionTexture.Draw();
				drawTexture(28 + gridX * 32, 1052 - gridY * 32 - 32, 32, 32);

				for (int w = 0; w < 32; w++) {
					for (int h = 0; h < 32; h++) {
						int i = w + h * 32;
						if (GridData == 0) {
							FontHandler.EliteProBlack.DisplayFont(28 + w * 32,
									1052 - h * 32 - 32, 0.6f, GridTypeArray[i]
											+ "");
						} else if (GridData == 1) {
							FontHandler.EliteProBlack.DisplayFont(28 + w * 32,
									1052 - h * 32 - 32, 0.6f,
									GridEntityTypeArray[i] + "");
						} else if (GridData == 2) {
							FontHandler.EliteProBlack.DisplayFont(28 + w * 32,
									1052 - h * 32 - 32, 0.6f,
									GridEntityRotationArray[i] + "");
						} else if (GridData == 3) {
							FontHandler.EliteProBlack.DisplayFont(28 + w * 32,
									1052 - h * 32 - 32, 0.6f,
									GridEntityDataOneArray[i] + "");
						} else if (GridData == 4) {
							FontHandler.EliteProBlack.DisplayFont(28 + w * 32,
									1052 - h * 32 - 32, 0.6f,
									GridLogicTypeArray[i] + "");
						} else if (GridData == 5) {
							FontHandler.EliteProBlack.DisplayFont(28 + w * 32,
									1052 - h * 32 - 32, 0.6f,
									GridDataOneArray[i] + "");
						} else if (GridData == 6) {
							FontHandler.EliteProBlack.DisplayFont(28 + w * 32,
									1052 - h * 32 - 32, 0.6f,
									GridDataTwoArray[i] + "");
						} else if (GridData == 7) {
							FontHandler.EliteProBlack.DisplayFont(28 + w * 32,
									1052 - h * 32 - 32, 0.6f,
									GridDataThreeArray[i] + "");
						} else if (GridData == 8) {
							FontHandler.EliteProBlack.DisplayFont(28 + w * 32,
									1052 - h * 32 - 32, 0.6f,
									GridFlatWallArray[i] + "");
						}
					}
				}

				if (SaveMenu == false) {
					// current tile
					if (selection == 0 && TileSelected) {
						FontHandler.ElitePro.DisplayFont(1060, 850, 0.8f, "->");
					}
					FontHandler.ElitePro.DisplayFont(1100, 850, 0.8f,
							"Tile Type");
					FontHandler.ElitePro.DisplayFont(1400, 850, 0.8f,
							GridTypeArray[gridX + gridY * 32] + "");
					FontHandler.ElitePro.DisplayFont(1450, 850, 0.8f,
							GetTileTypeName(GridTypeArray[gridX + gridY * 32]));

					if (selection == 1 && TileSelected) {
						FontHandler.ElitePro.DisplayFont(1060, 800, 0.8f, "->");
					}
					FontHandler.ElitePro.DisplayFont(1100, 800, 0.8f,
							"Entity Type");
					FontHandler.ElitePro.DisplayFont(1400, 800, 0.8f,
							GridEntityTypeArray[gridX + gridY * 32] + "");
					FontHandler.ElitePro.DisplayFont(1450, 800, 0.8f,
							GetEntityTypeName(GridEntityTypeArray[gridX + gridY
									* 32]));

					if (selection == 2 && TileSelected) {
						FontHandler.ElitePro.DisplayFont(1060, 750, 0.8f, "->");
					}
					FontHandler.ElitePro.DisplayFont(1100, 750, 0.8f,
							"Rotation");
					FontHandler.ElitePro.DisplayFont(1400, 750, 0.8f,
							GridEntityRotationArray[gridX + gridY * 32] + "");
					FontHandler.ElitePro.DisplayFont(1450, 750, 0.8f,
							GetRotationName(GridEntityRotationArray[gridX
									+ gridY * 32]));

					if (selection == 3 && TileSelected) {
						FontHandler.ElitePro.DisplayFont(1060, 700, 0.8f, "->");
					}
					FontHandler.ElitePro.DisplayFont(1100, 700, 0.8f, "Data");
					FontHandler.ElitePro.DisplayFont(1400, 700, 0.8f,
							GridEntityDataOneArray[gridX + gridY * 32] + "");
					// FontHandler.ElitePro.DisplayFont(1450, 700, 0.8f,
					// GetRotationName(GridEntityDataOneArray[gridX + gridY
					// *32]));

					if (selection == 4 && TileSelected) {
						FontHandler.ElitePro.DisplayFont(1060, 650, 0.8f, "->");
					}
					FontHandler.ElitePro.DisplayFont(1100, 650, 0.8f, "Logic");
					FontHandler.ElitePro.DisplayFont(1400, 650, 0.8f,
							GridLogicTypeArray[gridX + gridY * 32] + "");
					FontHandler.ElitePro.DisplayFont(1450, 650, 0.8f,
							GetLogicTypeName(GridLogicTypeArray[gridX + gridY
									* 32]));

					if (selection == 5 && TileSelected) {
						FontHandler.ElitePro.DisplayFont(1060, 600, 0.8f, "->");
					}
					FontHandler.ElitePro.DisplayFont(1100, 600, 0.8f,
							"Data One");
					FontHandler.ElitePro.DisplayFont(1400, 600, 0.8f,
							GridDataOneArray[gridX + gridY * 32] + "");
					FontHandler.ElitePro
							.DisplayFont(
									1450,
									600,
									0.8f,
									GetDataOneTypeName(
											GridLogicTypeArray[gridX + gridY
													* 32],
											GridDataOneArray[gridX + gridY * 32]));

					if (selection == 6 && TileSelected) {
						FontHandler.ElitePro.DisplayFont(1060, 550, 0.8f, "->");
					}
					FontHandler.ElitePro.DisplayFont(1100, 550, 0.8f,
							"Data Two");
					FontHandler.ElitePro.DisplayFont(1400, 550, 0.8f,
							GridDataTwoArray[gridX + gridY * 32] + "");
					// FontHandler.ElitePro.DisplayFont(1450, 550, 0.8f,
					// GetLogicTypeName(GridDataTwoArray[gridX + gridY *32]));

					if (selection == 7 && TileSelected) {
						FontHandler.ElitePro.DisplayFont(1060, 500, 0.8f, "->");
					}
					FontHandler.ElitePro.DisplayFont(1100, 500, 0.8f,
							"Data Three");
					FontHandler.ElitePro.DisplayFont(1400, 500, 0.8f,
							GridDataThreeArray[gridX + gridY * 32] + "");
					// FontHandler.ElitePro.DisplayFont(1450, 550, 0.8f,
					// GetLogicTypeName(GridDataThreeArray[gridX + gridY *32]));

					if (selection == 8 && TileSelected) {
						FontHandler.ElitePro.DisplayFont(1060, 450, 0.8f, "->");
					}
					FontHandler.ElitePro.DisplayFont(1100, 450, 0.8f,
							"Flat Wall");
					FontHandler.ElitePro.DisplayFont(1400, 450, 0.8f,
							GridFlatWallArray[gridX + gridY * 32] + "");
					// FontHandler.ElitePro.DisplayFont(1450, 550, 0.8f,
					// GetLogicTypeName(GridDataThreeArray[gridX + gridY *32]));

					FontHandler.ElitePro.DisplayFont(1060, 100, 0.8f,
							"Grid X: " + gridX + " Grid Y: " + gridY);
					FontHandler.ElitePro.DisplayFont(1060, 50, 0.8f,
							"Position: " + (gridX + gridY * 32));
				} else {
					DrawSaveMenu();
				}
			}
			else{
				DrawDialogueMenu();
			}
		} else {
			MyInput.drawInput();
		}

		ShaderHandler.TextShader.DeActivate();
	}

	private void DrawSaveMenu() {
		if (InputText == false) {
			FontHandler.ElitePro.DisplayFont(1060, 1000, 1.0f, "Save Menu");

			if (selection == 0) {
				FontHandler.ElitePro.DisplayFont(1060, 800, 0.8f, "->");
			}
			FontHandler.ElitePro.DisplayFont(1100, 800, 0.8f, "Name: ");
			FontHandler.ElitePro.DisplayFont(1500, 800, 0.8f, LevelName);

			if (selection == 1) {
				FontHandler.ElitePro.DisplayFont(1060, 750, 0.8f, "->");
			}
			FontHandler.ElitePro.DisplayFont(1100, 750, 0.8f, "Time: ");
			if (Time == 0) {
				FontHandler.ElitePro.DisplayFont(1500, 750, 0.8f, "Day");
			} else if (Time == 1) {
				FontHandler.ElitePro.DisplayFont(1500, 750, 0.8f, "Night");
			} else if (Time == 2) {
				FontHandler.ElitePro.DisplayFont(1500, 750, 0.8f, "MidNight");
			}

			if (selection == 2) {
				FontHandler.ElitePro.DisplayFont(1060, 700, 0.8f, "->");
			}
			FontHandler.ElitePro.DisplayFont(1100, 700, 0.8f, "Lights : ");
			FontHandler.ElitePro.DisplayFont(1500, 700, 0.8f, Lights + "");

			if (selection == 3) {
				FontHandler.ElitePro.DisplayFont(1060, 650, 0.8f, "->");
			}
			FontHandler.ElitePro.DisplayFont(1100, 650, 0.8f, "Floor Number: ");
			FontHandler.ElitePro.DisplayFont(1500, 650, 0.8f, FloorNumber + "");

			if (selection == 4) {
				FontHandler.ElitePro.DisplayFont(1060, 600, 0.8f, "->");
			}
			FontHandler.ElitePro.DisplayFont(1100, 600, 0.8f,
					"Has Next Floor: ");
			FontHandler.ElitePro
					.DisplayFont(1500, 600, 0.8f, HasNextFloor + "");

			if (selection == 5) {
				FontHandler.ElitePro.DisplayFont(1060, 550, 0.8f, "->");
			}
			FontHandler.ElitePro.DisplayFont(1100, 550, 0.8f,
					"Next Floor Name: ");
			FontHandler.ElitePro.DisplayFont(1500, 550, 0.8f, NextLevelName);

			if (selection == 6) {
				FontHandler.ElitePro.DisplayFont(1060, 500, 0.8f, "->");
			}
			FontHandler.ElitePro.DisplayFont(1100, 500, 0.8f, "Edit Dialogue ");
		}
	}

	private void DrawDialogueMenu() {
		FontHandler.ElitePro.DisplayFont(60, 1000, 1.0f, "Dialogue Menu");
		for (int i = 0; i < 15; i++) {
			int r = FindDialogueRef(i);
			if(selection == i){
				FontHandler.ElitePro.DisplayFont(30, 920 - i * 50, 1.0f, "->");
			}
			FontHandler.ElitePro.DisplayFont(85, 920 - i * 50, 1.0f,DialogueList.get(r).reference + " "+ DialogueList.get(r).LineOne);
		}
	}

	private String GetTileTypeName(int type) {
		if (type == 1) {
			return "Wall";
		} else if (type == 2) {
			return "Grass";
		} else if (type == 3) {
			return "Tile";
		} else if (type == 4) {
			return "Spike";
		} else if (type == 5) {
			return "Grass Crystal";
		} else if (type == 6) {
			return "Inactive Tile";
		} else if (type == 7) {
			return "Active Tile";
		} else if (type == 8) {
			return "Pressure Pad Base";
		} else if (type == 9) {
			return "Powered Spike";
		} else if (type == 10) {
			return "Tile No Lamp";
		} else if (type == 11) {
			return "Tile With Lamp U";
		} else if (type == 12) {
			return "Tile With Lamp R";
		} else if (type == 13) {
			return "Tile With Lamp D";
		} else if (type == 14) {
			return "Tile With Lamp L";
		} else {
			return "Blank";
		}
	}

	private String GetRotationName(int type) {
		if (type == 1) {
			return "Up";
		} else if (type == 2) {
			return "Right";
		} else if (type == 3) {
			return "Down";
		} else if (type == 4) {
			return "Left";
		} else {
			return "Blank";
		}
	}

	private String GetEntityTypeName(int type) {
		if (type == 1) {
			return "Player";
		} else if (type == 2) {
			return "Exit";
		} else if (type == 3) {
			return "Enemy";
		} else if (type == 4) {
			return "Chest";
		} else if (type == 5) {
			return "Door";
		} else if (type == 6) {
			return "Crystal";
		} else if (type == 7) {
			return "Push Object";
		} else if (type == 8) {
			return "Powered Door";
		} else if (type == 9) {
			return "Pressure pad";
		} else if (type == 10) {
			return "Tree";
		} else if (type == 11) {
			return "Button";
		} else if (type == 12) {
			return "Force Field";
		} else if (type == 13) {
			return "Fake Wall";
		} else if (type == 14) {
			return "Teleporter Field";
		} else if (type == 15) {
			return "Dialogue Object";
		} else if (type == 16) {
			return "Lever Off";
		} else if (type == 17) {
			return "Lever On";
		} else if (type == 18) {
			return "Sign";
		} else {
			return "Blank";
		}
	}

	private String GetLogicTypeName(int type) {
		if (type == 1) {
			return "Activator";
		} else if (type == 2) {
			return "Flip Flop";
		} else if (type == 3) {
			return "Triple Output";
		} else if (type == 4) {
			return "Multi Input";
		} else if (type == 5) {
			return "Push Once";
		} else if (type == 6) {
			return "Teleporter";
		} else if (type == 7) {
			return "Powered Pulse";
		} else if (type == 8) {
			return "Delayed Pulse";
		} else if (type == 9) {
			return "Pulse Once";
		} else {
			return "Blank";
		}
	}

	private String GetDataOneTypeName(int type, int D1) {
		if (type == 1) {
			if (D1 == 1) {
				return "Player";
			} else if (D1 == 2) {
				return "Enemy";
			} else if (D1 == 3) {
				return "Push Object";
			} else if (D1 == 4) {
				return "Player/Enemy";
			} else if (D1 == 5) {
				return "Interact";
			} else {
				return "Blank";
			}
		} else {
			return "";
		}
	}

	private void drawTexture(float x, float y, int width, int height) {
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0f, 0f);
		GL11.glVertex2f(x, y);

		GL11.glTexCoord2f(1f, 0f);
		GL11.glVertex2f(x + width, y);

		GL11.glTexCoord2f(1f, 1f);
		GL11.glVertex2f(x + width, y + height);

		GL11.glTexCoord2f(0f, 1f);
		GL11.glVertex2f(x, y + height);
		GL11.glEnd();
	}

	private void drawTexture(float x, float y, int width, int height, float U,
			float V, float UWidth, float VWidth) {
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(U, V);
		GL11.glVertex2f(x, y);

		GL11.glTexCoord2f(U + UWidth, V);
		GL11.glVertex2f(x + width, y);

		GL11.glTexCoord2f(U + UWidth, V + VWidth);
		GL11.glVertex2f(x + width, y + height);

		GL11.glTexCoord2f(U, V + VWidth);
		GL11.glVertex2f(x, y + height);
		GL11.glEnd();
	}

	private boolean CheckLevelRequirements() {
		boolean hasplayer = false;
		boolean hasexit = false;
		for (int i = 0; i < 1024; i++) {
			if (GridEntityTypeArray[i] == 1) {
				hasplayer = true;
			}
			if (GridEntityTypeArray[i] == 2) {
				hasexit = true;
			}
		}
		if (hasplayer && hasexit) {
			return true;
		} else {
			return false;
		}
	}

	private int FindDialogueRef(int ref) {

		int r = -1;

		for (int i = 0; i < DialogueList.size(); i++) {
			if (DialogueList.get(i).reference == ref) {
				r = i;
			}
		}

		return r;

	}

	private void FillDialogue() {
		for (int i = 0; i < 15; i++) {
			if (FindDialogueRef(i) < 0) {
				DialogueData TempDialogue = new DialogueData(i, "blank",
						"blank", "blank");
				DialogueList.add(TempDialogue);
			}
		}
	}
}
