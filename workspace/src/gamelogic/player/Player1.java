package gamelogic.player;

import java.awt.Color;
import java.awt.Graphics;

import gameengine.PhysicsObject;
import gameengine.graphics.MyGraphics;
import gameengine.hitbox.RectHitbox;
import gamelogic.Main;
import gamelogic.level.Level;
import gamelogic.tiles.Gas;
import gamelogic.tiles.Tile;

//This class represents the first player in the game and extends the Player class. it moves according to the arrow keys.
public class Player1 extends Player{
	public float walkSpeed = 400;
	public float jumpPower = 1350;
	private long gasTracker1 = 0;
	

	public Player1(float x, float y, Level level) {
	
		super(x, y, level);
		int offset =(int)(level.getLevelData().getTileSize()*0.1); //hitbox is offset by 10% of the player size.
		this.hitbox = new RectHitbox(this, offset,offset, width -offset, height - offset);
	}


	@Override
	public void update(float tslf) {
		
		movementVector.x = 0;
		if(PlayerInput.isAKeyDown()) {
			movementVector.x = -walkSpeed;
		}
		if(PlayerInput.isDKeyDown()) {
			movementVector.x = +walkSpeed;
		}
		if(PlayerInput.isWKeyDown() && !isJumping) {
			movementVector.y = -jumpPower;
			isJumping = true;
		}

		//This code checks if the player is touching gas tiles. If the player is touching gas, a timer starts. 
		//If the timer reaches 3 seconds, the player dies.
		boolean touchedGas = false;
		for (Tile[] tileRow : getLevel().getMap().getTiles()) {
			for (Tile tile : tileRow) {
				if (tile instanceof Gas) {
					Gas g = (Gas) tile;
					if (g.getHitbox().isIntersecting(getLevel().getPlayers()[0].getHitbox())) {
						touchedGas = true;
					}
				}
			}
		}

		if (!touchedGas) {
			touchedGas = false;
			gasTracker1 = 0;
		}
			if(touchedGas && gasTracker1 == 0){
				gasTracker1 = System.currentTimeMillis();
				getLevel().getPlayers()[0].displayGasTimer(gasTracker1);

			}
			if(gasTracker1 != 0){
				if((System.currentTimeMillis() - gasTracker1)/1000 >= 3){
					getLevel().onPlayerDeath();
				}
			}
		

		super.update(tslf);
		
	}

	//draws the character as well as creates the gas timer bar above the character.	
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.YELLOW);
		MyGraphics.fillRectWithOutline(g, (int)getX(), (int)getY(), width, height);
		
		if(gasTracker1!= 0){
			g.setColor(Color.black);
			g.drawRect((int)position.x, (int)position.y-10, width, (int)(getLevel().getMap().getTileSize()/5));
			g.setColor(Color.green);
			g.fillRect((int)position.x, (int)position.y-10, (int)(width * (1-((System.currentTimeMillis()-gasTracker1)/3000.0))), (int)(getLevel().getMap().getTileSize()/5));
		}
		
		hitbox.draw(g);
	}
}
