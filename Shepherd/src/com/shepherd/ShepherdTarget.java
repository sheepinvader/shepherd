package com.shepherd;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PointF;

public class ShepherdTarget extends GameObject implements IHideable {
	private boolean visible;
	private Bitmap bmp;
	private PointF bmpOffset;
	
	public ShepherdTarget(GameView gameField)
	{
		this.gameField = gameField;
		this.visible = true;
		
		//this.position.x = 240; //gameField.getWidth() / 2 ; // central position
		//this.position.y = 400; //gameField.getHeight() / 2 ;
		
		Resources resources = gameField.getResources();
		this.bmp = BitmapFactory.decodeResource(resources, R.drawable.pic_target_transparent);
		this.bmpOffset = new PointF(bmp.getWidth() / 2, bmp.getHeight() / 2);
	}
	@Override
	public void draw(Canvas c)
	{
		if(this.visible)
			c.drawBitmap(bmp, this.position.x - this.bmpOffset.x, this.position.y - this.bmpOffset.y, null);
	}
	
	@Override
	public void show()
	{
		this.visible = true;		
	}
	
	@Override
	public void hide()
	{
		this.visible = false;
	}
	
	@Override
	public boolean isVisible()
	{
		return this.visible;
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
