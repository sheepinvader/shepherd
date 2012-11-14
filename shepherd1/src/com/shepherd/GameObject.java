package com.shepherd;

import android.graphics.Canvas;
import android.graphics.PointF;

public abstract class GameObject implements IViewable, ICartesian2D {
	protected GameView gameField;	
	protected PointF position = new PointF();
	
	@Override
	public void onDraw(Canvas c) {
	}

	@Override
	public double getX()
	{
		return position.x;
	}
	
	@Override
	public double getY()
	{
		return position.y;
	}
}
