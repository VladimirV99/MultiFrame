# MultiFrame

MultiFrame is a cross-platform obstacle avoidance game where you control multiple entities at the same time.

The game screen is split into multiple parts we call *frames*. Each frame contains one or more entities you control by tapping on a certain part of the screen, depending on the game mode. However, the controls result in different actions being performed by different entities. Those in the currently focused frames (frames discerned by the red banner) move to the tapped side while the others move in the opposite direction. To make things even more difficult the focused frames switch up after a certain amount of gathered points depending on the chosen difficulty. Hitting an obstacle with any entity destroys all of them, and results in a game over. Your goal is to stay alive as long as you can.

**You can use this code as a foundation for your own project, but please don't publish this same game online**

## Game modes

### Dual Frame

The screen is split vertically in two halves and there is one player controlled entity in each of them. By tapping the left or right frame the entity in the frame with the red banner moves to that side, while the other one moves in the opposite direction. The obstacles come from the bottom at a constant speed and distance which are increased by increasing the difficulty.

### More game modes are planned

## Game difficulty

All game modes have multiple difficulties for players to choose from:

- **Easy** - Controls switch every 10 points. Obstacles are slower and more distant
- **Medium** - Controls switch every point. This is the default dual frame mode
- **Hard** - Controls switch randomly each received point. Distance between obstacles is shorter
- **Custom** - You can manually configure all mode options to make the game difficult as you like. Note that not all possible configurations are playable

## Programming

The game is written in Java using LibGDX framework and can run on desktop, android, ios and in a browser. Textures are made using [paint.net](https://www.getpaint.net/) and the sounds are downloaded from [freesound.org](https://freesound.org/) and edited using [audacity](https://www.audacityteam.org/). 

## License

MultiFrame is licenced under the GNU GPL v3.0 license. You are free to modify and distribute the software for commercial purposes or use is privately, but you have to make the source code available and under the same license.

## Contributing

Feel free to contribute to the project either by submitting issues or making pull requests.

Thank you for the help :blush: