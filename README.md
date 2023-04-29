# Fish-Adventure-Game
This is a platform game, which is built in JAVA

FISH ADVENTURE
INSTRUCTION
(Important note: Please turn off Unikey before playing)
1. Download and configure
Once you have downloaded this game, you are now having 4 files like this:
 
1.1. STEP 1: Import the game’s data
Firstly, you must have MySQL on your device to contain the data, if you currently don’t have it, please download XAMPP for your device, here is the link: https://www.apachefriends.org/download.html
Open the app XAMPP you can see this window, then please turn on MySQL workspace by clicking on these buttons “Start” with the module Apache and MySQL
 
Then clicking on the button Admin in Module MySQL to open the workspace
 
Next, open or copy the SQL code in the file “fishadventure.sql” and then click Run to execute the query, you can have the database like this:
*Note: you can create another database if the database name is the same as one of your databases before by changing the name database when CREATE

1.2. STEP 2: Configuring the properties
Open the file ‘config.properties’, you have to configure some properties for connecting the game with the database

You need to change these properties to match with the database in your device including:
- HOST_NAME (default is localhost)
- PORT (you can take it from the XAMPP Panel, the Port of MySQL)
- DB_NAME(default is ‘fishadventure’, or depending on the name you want for the database)
- USER and PASS(if your MySQL workspace requires password and username)

1.3. STEP 3: Open the game
If your configuration is correct in STEP 1 AND STEP 2 you can open the game by clicking the file ‘FishAdvantureV01.jar’ and have the window like this

*NOTE: You can resize the window of the game by changing the properties SCALE in the ‘config.properties’ file (by default SCALE = 1).
- Button “SETTINGS: adjust the volume of the sound and other settings properties.
-  Button “EXIT”: exit the game.
-  Press the Button “PLAY”: Begin the game, you can choose the game’s modes to play, we have 2 modes: DOUBLE FISH and SURVIVAL FISH

We will give you the instructions for these modes below. Having a good time playing! GOOD LUCK!
 
2. Double Fish Mode
2.1. Entities
- Player: Two Fish


- Enemies: Octopus, Crab, Grid


- Traps:

  
- Hints:
 
- Lock:
 

2.2. Rule
You have to control 2 fishes at the same time to unlock by typing an answer, you can take the hints to get closer to the key. However, your challenge is to overcome the traps, blocks, and enemies to get these hints and you only have a limited time to open the lock.
The game will be over once your player touches the traps and enemies or open the lock more than the limitation.
Once you type the correct answer, you will win the current level and can move on to the next level.
2.3. Levels
There are 6 levels for you to play, you can go to the next level once you finish the previous level. Each level has its difficulty, it can be in the limitation of typing key, it can be difficult hints and the map can be complicated.
2.4. Playing
Note: Please turn off Unikey before playing
Make a move by pressing these buttons:
- W: go to the top
- D: go to the right
- S: go to the bottom
- A: go to the left
Touching the hints and lock by moving the players near to it.
 
3. Survival Fish
Note: Please turn off Unikey before playing
3.1. Rule
- Avoid contact with traps and monsters, collect orbs.
- Up on starting a game, you will be given 10s of invincibility.
- Mini goldfish follows you, if he is near you will receive double the amount of points per orb.
- Each time you reach 100, 200, or 300 points, …, you will be given 10s of invincibility.
- Orb:
 
- Fish:
 
3.2. Control
W: UP
A: LEFT
S: DOWN
D: RIGHT
Press SHIFT to speed up. 
P: Pause game
Please note that the pause game does not stop the counter of the player’s effect.
3.3. Trap
 
Figure 1. 14. Trap

3.4. Monster
3.4.1. BLADE:

Move diagonally, horizontally, or vertically.
Able to pass through tiles.
3.4.2. BLUE GUARD:

Moving on a circle, able to pass through tiles.
3.4.3. CHASER:
 
Dash into the player with high speed, able to pass through tiles.

