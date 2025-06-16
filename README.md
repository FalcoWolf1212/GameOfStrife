GAME OF STRIFE README!

## Installation

Download the zip file and extract. Run the executable to start the game.

## HOW TO PLAY

To run the game, execute the run.sh (linux, macos) or run.bat (windows), located in the 'GameOfStrife' folder.

### Setup

1. A settings menu will show up. Here, you can choose how many players you want (2, 3 or 4), how many faces the die has (3-10) and how many victory points are needed to win.

2. Press "Ok" to go to the next step of the settings screen. Here, you will be able to type each player's name and set their country.

3. Press "Ok" again. Now the game has started!

### Gameplay

The game plays like a board game, with sequential turns where a player can roll a die and move an amount of squares equal to the amount of faces rolled. 
The goal for each player is to gather enough Victory Points to win. Once one player has reached the required amount of Victory Points, the game ends and a choice will appear to restart or quit.

#### Die

The die is an object that can be rolled by pressing the "roll" button. The result that appears is how many steps the current player may take. 
This process is automatic, but when reaching a crossroad the player can choose to go left or right. Based on certain events, the amount of faces on the die can be increased 
or reduced, meaning a different range of values can be rolled.

Once the player has rolled and pressed all the buttons that pop up, the turn will automatically roll over to the next person.

#### Cards and Tiles

When a player moves, they can land on differently colored tiles. White tiles are empty and nothing will happen when you land on them. 
The Yellow tile works similarly, where the only difference is that it is the start tile. 
The other three tile colors are all connected to Card Decks based on their color. When a player lands on one, they will receive a card that corresponds to the color of the tile. 
Green tiles are Money Tiles, and are connected to a deck of Green Cards that all grant a sum of gold to the player that lands on it. 
Red tiles are Chaos Tiles, and are connected to a deck of Red Cards that apply a variety of chaotic effects. Most of the time, these cards apply negative bonuses, 
but sometimes they can harm the other players as well, or just do something entirely chaotic! 
Lastly, Blue tiles are the most interesting, as they are Choice Tiles connected to a deck of Blue Cards. They can offer a variety of choices, from switching places with another to 
gambling, the cards have lots of functionalities.

#### Victory Tiles

Victory Tiles are the most important tiles in the game, as they are the only way to obtain Victory Points. You'll know when a tile is a Victory Tile when you see a large star on it. Only one Victory Tile can exist at a time though, so be sure to get there before anyone else does. 
When the game starts, a Victory Tile is randomly placed somewhere near the end of the board and will remain there until someone buys the Victory Point from it.

If you move over a victory tile (you don't have to land on one exactly!) 
you will be stopped and provided a choice: Buy a victory point for 1000 gold, or... don't. If you don't buy a victory point, you will continue on your way to the tile you were originally going to. 
If you do buy a Victory Point however, your remaining moves are deleted and you are immediately brought back to the starting square. The Victory tile will move to a different location, still somewhere near the end of the map. 
Lastly, and most importantly, the player who spent the 1000 gold will receive a Victory Point, giving them a massive boost towards victory.

Other players beware: while there are many cards that can take money away from other players, there is no way to rob someone of their Victory Points!

## AI Statement

We hereby declare that AI was used to help us with generating code and JUnit tests. It also helped with formatting our json files. 

## Directory Tree

For validation purposes, the following directory tree must be present for the project:

.

├── GameOfStrife  
│   ├── bin  
│   │   ├── game  
│   │   │   ├── BlueCard.class  
│   │   │   ├── Board.class  
│   │   │   ├── CardAction.class  
│   │   │   ├── Card.class  
│   │   │   ├── CardDecks.class  
│   │   │   ├── Die.class  
│   │   │   ├── GreenCard.class  
│   │   │   ├── Player.class  
│   │   │   ├── RedCard.class  
│   │   │   ├── SymbolicConstants.class  
│   │   │   └── Tile.class  
│   │   ├── gui  
│   │   │   ├── BoardPanel\$1.class  
│   │   │   ├── BoardPanel\$2.class  
│   │   │   ├── BoardPanel\$3.class  
│   │   │   ├── BoardPanel.class  
│   │   │   ├── GameofStrife\$1.class  
│   │   │   ├── GameofStrife\$2.class  
│   │   │   ├── GameofStrife.class  
│   │   │   ├── GameOfStrifeInterface.class  
│   │   │   ├── PlayerPanel.class  
│   │   │   └── SettingsPanel.class  
│   │   └── test  
│   │       ├── BlueCardTest.class  
│   │       ├── BoardTest.class  
│   │       ├── CardDecksTest.class  
│   │       ├── DieTest.class  
│   │       ├── GreenCardTest.class  
│   │       ├── MockUpBoard.class  
│   │       ├── PlayerTest$DummyGUI.class  
│   │       ├── PlayerTest.class  
│   │       ├── RedCardTest.class  
│   │       └── TileTest.class  
│   ├── data  
│   │   ├── background.png  
│   │   ├── cards1.json  
│   │   ├── emptyPath.json  
│   │   ├── Ethiopia.png  
│   │   ├── France.png  
│   │   ├── Friesland.png  
│   │   ├── Hungary.png  
│   │   ├── icon.png  
│   │   ├── Iran.png  
│   │   ├── Morocco.png  
│   │   ├── Netherlands.png  
│   │   ├── path1.json  
│   │   ├── path2.json  
│   │   ├── pathTestBlue.json  
│   │   ├── pathTest.json  
│   │   ├── pathWithoutStartTile.json  
│   │   ├── star.png  
│   │   └── Turkiye.png  
│   ├── documentation  
│   │   ├── ChecklistA1Product.docx  
│   │   ├── ChecklistA2Process.docx  
│   │   ├── GAME OF STRIFE - Player Icons illustrator.ai  
│   │   ├── LICENSE.txt  
│   │   ├── UML_diagrams  
│   │   │   ├── ClassdDiagramWeek3.png  
│   │   │   ├── ClassDiagram_Card.png  
│   │   │   ├── ClassDiagramPlayerAndInterface.png  
│   │   │   ├── ClassDiagram_week1.jpg  
│   │   │   ├── ClassDiagram_Week2.png  
│   │   │   ├── SequenceDiagram_GameOfStrife_Newest.pdf  
│   │   │   ├── SequenceDiagram_GameOfStrife.pdf  
│   │   │   ├── SequenceDiagram_PressDieRollButton.png  
│   │   │   ├── SequenceDiagram_TileRendering.png  
│   │   │   ├── SimpleClassDiagram.png  
│   │   │   ├── use_case_descriptions.docx  
│   │   │   ├── Use_case_diagram_DrawBlueCard.docx  
│   │   │   ├── Use_case_diagram_PlayersTurn.docx  
│   │   │   ├── UseCaseDiagram.png  
│   │   │   └── Use_case_diagram_Start-up_of_Game_of_Strife.pdf  
│   │   └── User_stories  
│   │       ├── FinalUserStories.docx  
│   │       ├── UserStoriesIt_groupA.xlsx  
│   │       ├── UserStoriesWeek1.docx  
│   │       ├── UserStoriesWeek2.docx  
│   │       ├── UserStoriesWeek3.docx  
│   │       └── UserStoriesWeek4.docx  
│   ├── GameOfStrife.jar  
│   ├── libs  
│   │   └── json-20250107.jar  
│   ├── run.bat  
│   ├── run.sh  
│   └── src  
│       ├── game  
│       │   ├── BlueCardInterface.java  
│       │   ├── BlueCard.java  
│       │   ├── Board.java  
│       │   ├── CardAction.java  
│       │   ├── CardDecks.java  
│       │   ├── Card.java  
│       │   ├── Die.java  
│       │   ├── GreenCard.java  
│       │   ├── Player.java  
│       │   ├── RedCard.java  
│       │   ├── SymbolicConstants.java  
│       │   └── Tile.java  
│       ├── gui  
│       │   ├── BoardPanel.java  
│       │   ├── GameMovementController.java  
│       │   ├── GameOfStrife.java  
│       │   ├── PlayerPanel.java  
│       │   └── SettingsPanel.java  
│       └── test  
│           ├── BlueCardTest.java  
│           ├── BoardTest.java  
│           ├── CardDecksTest.java  
│           ├── DieTest.java  
│           ├── GreenCardTest.java  
│           ├── MockUpBoard.java  
│           ├── PlayerTest.java  
│           ├── RedCardTest.java  
│           └── TileTest.java  
└── README.md  
