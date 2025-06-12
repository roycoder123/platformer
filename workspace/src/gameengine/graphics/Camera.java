package gameengine.graphics;

import java.awt.Color;
import java.awt.Graphics;

import gameengine.maths.Vector2D;
import gamelogic.Main;
import gamelogic.player.Player;
import gamelogic.level.Level;

public class Camera {

	public static final boolean SHOW_CAMERA = false;
	
	private Vector2D position;
	private Player player;
	
	private int width;
	private int height;
	
	private float velocityX = 1.6f; 
	private float velocityY = 1.6f;

	private float delay = 1.0f;
	
	private float setValue = 0.5f;
	
	private float offsetX = 450;
	private float offsetY = 100; //to rather keep the camera down
	
	private int size = 10; //used to draw the oval
	
	private float borderLeft;
	private float borderRight;
	private float borderBot;
	
	private Vector2D goalPosition;
	
	public Camera(int width, int height, int borderLeft, int borderRight, int borderBot) {
		this.position = new Vector2D();
		this.goalPosition = new Vector2D();
		this.width = width;
		this.height = height;
		this.borderLeft = borderLeft;
		this.borderRight = borderRight;
		this.borderBot = borderBot;
	}
	
	public void update(float tslf, Level level) {
		if(player != null) {
			
			calulateGoalPositon(level);
			checkBorders();
			
			//Calculating the differences between the goal position and the current position of the camera
			float diffX = goalPosition.x - position.x;
			float amountX = diffX * velocityX;
			position.x += amountX * tslf;

			float diffY = goalPosition.y - position.y;
			float amountY = diffY * velocityY;
			position.y += amountY * tslf;
			
			//At this point the difference is too small so the value gets set to avoid shaking of the camera
			if(-setValue < diffX && diffX < setValue) position.x = goalPosition.x;
			if(-setValue < diffY && diffY < setValue) position.y = goalPosition.y;
			
			//after a delay of 1 second (set previously) the game will now be over if the players are not in camera
			if(delay > 0) {
				delay -= tslf;
			}
			else if(!isPlayerVisible(level.getPlayers()[0]) || !isPlayerVisible(level.getPlayers()[1])){
				level.onPlayerDeath();
			}
		}
	}
	
	private void checkBorders() {
		if(borderLeft != -1 && goalPosition.x < borderLeft) goalPosition.x = borderLeft;
		if(borderRight != -1 && borderRight < goalPosition.x + width) goalPosition.x = borderRight - width; 
		
		if(borderBot != -1 && borderBot < goalPosition.y + height) goalPosition.y = borderBot - height;
	}
	
	private void calulateGoalPositon(Level level) {
		Player player1 = level.getPlayers()[0];
		float offsetX = Math.copySign(this.offsetX, player1.getMovementX());
		if (player1.getMovementX() == 0) offsetX = 0;

		goalPosition.x = player1.getX() + player1.getWidth() / 2 - Main.SCREEN_WIDTH / 2 + offsetX;
		goalPosition.y = player1.getY() + player1.getHeight() / 2 - Main.SCREEN_HEIGHT / 2 + offsetY;
	}
	
	//used for debugging
	public void draw (Graphics g) {
		g.setColor(Color.RED);
		g.drawOval((int)(position.x + width/2) - size/2, (int)(position.y + height/2) - size/2, size, size);
	}
	
	public void setFocusedObject(Player object, Level level) {
		this.player = object;
		
		calulateGoalPositon(level);
		checkBorders();
		
		position.x = goalPosition.x;
		position.y = goalPosition.y;
	}
	
	public boolean isVisibleOnCamera(float x, float y, int width, int height) {
		if(x + width > position.x && x < position.x + this.width && y + height > position.y && y < position.y + this.height) return true;
		return false;
	}

	//This function checks if the player is visible on the camera
	//pre-condition: the player has to be set
	//post-condition: returns true if the player is visible on the camera and false if not
	public boolean isPlayerVisible(Player p){
		float x = p.getX();
		float y = p.getY();
		int w = p.getWidth();
		int h = p.getHeight();
		return isVisibleOnCamera(x, y, w, h);
	}
	
	//--------------------------------Getters
	public void setX(float x) {
		position.x = x;
	}
	
	public void setY(float y) {
		position.y = y;
	}
	
	public float getX(){
		return position.x;
	}
	
	public float getY() {
		return position.y;
	}
}
