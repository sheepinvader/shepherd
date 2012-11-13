package com.shepherd;

 

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
//import android.graphics.PointF;

public class Sheep extends MovingGameObject {
	private GameView gameField;
	
	private Bitmap bmp;
	
	public Sheep(GameView gameField)
	{
		this.position.x = (float)Math.random()*gameField.getWidth();
		this.position.y = (float)Math.random()*gameField.getHeight();
		this.gameField = gameField;
		this.bmp = BitmapFactory.decodeResource(gameField.getResources(), R.drawable.ic_launcher);
	}
	
	@Override
	public void update() 
	{
		
	}
	
	@Override
	public void onDraw(Canvas c)
	{
		c.drawBitmap(bmp, this.position.x, this.position.y, null);
	}
}
