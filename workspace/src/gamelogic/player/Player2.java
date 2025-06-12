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

public class Player2 extends Player{
	public float walkSpeed = 400;
	public float jumpPower = 1350;
	private long gasTracker2 = 0;

//This class represents the second player in the game and extends the Player class. it moves according to the WASD keys.
	public Player2(float x, float y, Level level) {
	
		super(x, y, level);
		int offset =(int)(level.getLevelData().getTileSize()*0.1); //hitbox is offset by 10% of the player size.
		this.hitbox = new RectHitbox(this, offset,offset, width -offset, height - offset);
	}


	@Override
	public void update(float tslf) {
		
		movementVector.x = 0;
		if(PlayerInput.isLeftKeyDown()) {
			movementVector.x = -walkSpeed;
		}
		if(PlayerInput.isRightKeyDown()) {
			movementVector.x = +walkSpeed;
		}
		if(PlayerInput.isUpKeyDown() && !isJumping) {
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
					if (g.getHitbox().isIntersecting(getLevel().getPlayers()[1].getHitbox())) {
						touchedGas = true;
					}
				}
			}
		}

		if (!touchedGas) {
			touchedGas = false;
			gasTracker2 = 0;
		}
			if(touchedGas && gasTracker2 == 0){
				gasTracker2 = System.currentTimeMillis();
				getLevel().getPlayers()[1].displayGasTimer(gasTracker2);

			}
			if(gasTracker2 != 0){
				if((System.currentTimeMillis() - gasTracker2)/1000 >=3){
					getLevel().onPlayerDeath();
				}
			}

		super.update(tslf);
		
	}

	//draws the character as well as creates the gas timer bar above the character.
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.BLUE);
		MyGraphics.fillRectWithOutline(g, (int)getX(), (int)getY(), width, height);
		
		if(gasTracker2!= 0){
			g.setColor(Color.black);
			g.drawRect((int)position.x, (int)position.y-10, width, (int)(getLevel().getMap().getTileSize()/5));
			g.setColor(Color.green);
			g.fillRect((int)position.x, (int)position.y-10, (int)(width * (1-((System.currentTimeMillis()-gasTracker2)/3000.0))), (int)(getLevel().getMap().getTileSize()/5));
		}
		
		hitbox.draw(g);
	}
}
