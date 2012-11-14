package com.shepherd;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
//import android.graphics.Point;
import android.graphics.PointF;
import android.util.Log;

public class Shepherd extends MovingGameObject {
	private final float RAPIDITY = 0.02f; 
	
	private GameView gameField;
	private Bitmap bmp;
	
	public Shepherd(GameView gameField)
	{
		this.velocity.x = 0;
		this.velocity.y = 0;

		this.position.x = gameField.getWidth() / 2 ; // central position
		this.position.y = gameField.getHeight() / 2 ;
	
		this.gameField = gameField;
		this.bmp = BitmapFactory.decodeResource(gameField.getResources(), R.drawable.pic_potato);
	}
	
	@Override
	public void update() {
		this.position.x += this.velocity.x;
		this.position.y += this.velocity.y;
		
		PointF targetPosition = gameField.getShepherdTarget().getPosition();
		float eps = 0.1f; 
		if ((this.position.x - targetPosition.x) < eps
				&& (this.position.y - targetPosition.y) < eps  )
		{
			gameField.getShepherdTarget().hide();
		}

		this.velocity.x = RAPIDITY * (targetPosition.x - this.position.x);
		this.velocity.y = RAPIDITY * (targetPosition.y - this.position.y);
	}
	
	@Override
	public void onDraw(Canvas c)
	{
		c.drawBitmap(bmp, this.position.x, this.position.y, null);
	}
}
