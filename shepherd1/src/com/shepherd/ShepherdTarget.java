package com.shepherd;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PointF;

public class ShepherdTarget extends StaticGameObject {
	private GameView gameField;
	private boolean visible;
	private Bitmap bmp;
	
	public ShepherdTarget(GameView gameField)
	{
		this.gameField = gameField;
		this.visible = false;
		this.bmp = BitmapFactory.decodeResource(gameField.getResources(), R.drawable.ic_launcher);
	}
	
	@Override
	public void onDraw(Canvas c)
	{
		if(this.visible)
			c.drawBitmap(bmp, this.position.x, this.position.y, null);
	}
	
	public void show()
	{
		this.visible = true;		
	}
	
	public void hide()
	{
		this.visible = false;
	}
	
	public PointF getPosition()
	{
		return this.position;	
	}
	
	public void SetPosition(PointF p)
	{
		this.position.x = p.x;
		this.position.y = p.y;
	}
}
