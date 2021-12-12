# Brick_Destroy
This is a simple arcace video game.
Player's goal is to destroy a wall with a small ball.
The game has  very simple commmand:
SPACE start/pause the game
A move left the player
D move rigth the player
ESC enter/exit pause menu
ALT+SHITF+F1 open console
the game will automatically pause if the frame loses focus


Enjoy ;-)

## Refactory

 #### Organising Files 
  Separating all resources like sounds and images away from the source codes.
 #### Meaningful package nameing _ MVC
  I created packages namely: controller, view, model, test. Put in those packages class that satisfy the name.
  For example, all the object like: CementBrick; StealBrick; and ClayBrick is in the model class represented the object and be handled by Brick class in controller packages.
#### Breaking and Cleaning up Classes 
  Some classes from source codes were bronken up to differen classes. This is to make the classes to follow the Single Responsibility Principle.
  For examples, class Wall is responsible for the creation of player and level. In this source code, Lelvel has been broken up to specific Level class.
 
 #### Added new Classes 
   Added new classes to increase the modularity or add features of the program. For examples, class like sound is a class which is specifically in charge of the sound.
 
## Additions 
  #### Additional screen Info
   In the home menu when starting the game, user can click start button to play the game, info button to learn instructions, and exit to quit the game.
  #### Additioinal levels 
   Created a new level that can use 3 types of bricks.
  #### Additional fortune.
   In this game, when user start the game there will be a random number (0, 1, 2).
      If the number is 0, the user will have full length of the paddle.
      1 -> 1/2 paddle.
      2 -> 1/4 paddle.
  #### Simple feature
   Created a sound feature. It's a game theme and can be turned on or off in the pause menu.
  #### SaveScore _ HighScore
   When finish the game whether lose or win. If the user'score higher than the previous score, the user will be asked to put their name and it will show on the game board screen the next time playing.
  #### Maven
   Used Maven to crerate build files, indicating the dependencies of the codes.
  #### Javadocs 
   Added Javadocs for all classed to ensure easier understanding about the codes.
  #### JUnit
   Added 5 test classes, all of them contains many test cases for the model object.
      
     


