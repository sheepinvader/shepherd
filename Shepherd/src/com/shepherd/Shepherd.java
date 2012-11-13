package com.shepherd;

import android.graphics.Point;
import android.graphics.PointF;

public class Shepherd extends MovingGameObject {
	private final float RAPIDITY = 0.5f; 
	
	private GameActivity gameField;
	
	public Shepherd(GameActivity gameField)
	{
		this.gameField = gameField;
	}
	
	@Override
	public void update() {
		this.position.x += this.velocity.x;
		this.position.y += this.velocity.y;
		
		PointF targetPosition = gameField.getShepherdTargetPosition();
		if (this.position.x == targetPosition.x 
				&& this.position.y == targetPosition.y)
		{
			// raise event to hide target
		}

		this.velocity.x = RAPIDITY * (targetPosition.x - this.position.x);
		this.velocity.y = RAPIDITY * (targetPosition.y - this.position.y);
	}
	
	@Override
	public void draw()
	{
		
	}
}
