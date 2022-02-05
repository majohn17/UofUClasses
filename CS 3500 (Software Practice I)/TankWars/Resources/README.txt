Created by Isaac Gibson (u1173413) and Matthew Johnsen (u1173601)

PS8:

- For the movement/firing/angle, we can use the onNetworkAction to decide if the tank is moving, firing, and what direction is facing
by having a bool be set to true when the button is pushed down, and false when the button is realeased for movement and firing. 

- Short cuircut the events by if the bool is true, we don't need to go any farther.

- One thing we decided to do was use hearts instead of a health bar because it looked better and it turned out very well.

- We decided to have the beams fired from tanks match the color of the tank rather than having particles come from the beams.

- For drawing the explosions, we decided to draw a sprite rather than particles.

- We changed our deserialize method later in to imporve performance, and a major thing we did to improve performance was load the sprites
we use into a dictionary when they were initialized which removed most of the lag and jitter we had in our game.

- Beams need to be made able to fire only once.

- From what we can tell, everything seems to be working correctly.

-----------------------------------------------------------------------------------------------------------------------------------------
PS9:

- One specific thing we decided was to limit the amount of powerups in the world to 5 as to not flood the server when left on.

- We decided to go with the 2 table database approach beacuse it was easier to implement and we were running very short on time.

- One thing we added was a command class that uses json to serailize the command requests sent by the server to make them easier to 
use.

- We made a settings reader class to take all the old and new XML settings in to allow for more customization and easier reading of the 
settings.