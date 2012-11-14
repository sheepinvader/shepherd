package com.shepherd;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.util.Log;

public class ShepherdTarget extends StaticGameObject {
	private GameView gameField;
	private boolean visible;
	private Bitmap bmp;
	
	public ShepherdTarget(GameView gameField)
	{
		this.gameField = gameField;
		this.visible = false;
		
		Resources resources = gameField.getResources();
		this.bmp = BitmapFactory.decodeResource(resources, R.drawable.pic_target);
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
