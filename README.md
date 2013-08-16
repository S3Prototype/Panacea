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
