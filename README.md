This is an android rpg made with free art found online.
It runs a tile-based 2D engine for movement around the world,
and a simple collision-detection system for combat (which
consists of spaceship battles).

(8/13/13 - 00:12) 
Second commit: Tile engine works (at least it draws to scale). Must remember
that important information can't be saved on the drawing thread, because
that thread is joined or left somewhere lost in memory once the player
puts the app in the background. Could potentially lose position data, etc.

(8/15/2013 - 20:08)
Third commit: Not much has changed. Been trying desperately to get the app
to save user state when onPause() is called. No success yet. Very
frustrating.

(8/17/2013 - 22:59)
Fourth commit: Fixed the user state data saving problem, temporarily. Next up,
add code for updating where each character is (what tile they're on), code for
changing rooms, and code to load up tiled maps. Then the conversation system,
and the RPG portion will be complete.

(8/19/2013 - 12:31)
Fifth commit: Refactored the game, added Room and Engine class, refactored
GameTile class and DrawThread as well. Now All I need to do is fix a small bug
that makes the user start off screen, add some extra information for starting
x and y info for each player (possibly), and then make the tiled maps and load
them up.
