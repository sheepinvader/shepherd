package com.shepherd;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
//import android.graphics.Point;
import android.graphics.PointF;
import android.util.Log;

public class Shepherd extends MovingGameObject {
	private final float RAPIDITY = 0.05f; 
	private final float EPS = 20f;
	private GameView gameField;
	private Bitmap bmp;
	
	public Shepherd(GameView gameField)
	{
		this.velocity.x = 0;
		this.velocity.y = 0;

		this.position.x = 0; // central position
		this.position.y = 0;
	
		this.gameField = gameField;
		this.bmp = BitmapFactory.decodeResource(gameField.getResources(), R.drawable.pic_sun);
	}
	
	@Override
	public void update() {
		this.position.x += this.velocity.x;
		this.position.y += this.velocity.y;
		
		PointF targetPosition = gameField.getShepherdTarget().getPosition();
		 
		if ((this.position.x - targetPosition.x) < EPS
				&& (this.position.y - targetPosition.y) < EPS  )
		{
			gameField.getShepherdTarget().hide();
		}

		this.velocity.x = RAPIDITY * (targetPosition.x - this.position.x);
		this.velocity.y = RAPIDITY * (targetPosition.y - this.position.y);
	}
	
	public void SetPosition(PointF p)
	{
		this.position.x = p.x;
		this.position.y = p.y;
	}
	
	@Override
	public void onDraw(Canvas c)
	{
		c.drawBitmap(bmp, this.position.x - bmp.getWidth()/2 , this.position.y - bmp.getHeight()/2 , null);
	}
}
