package com.shepherd;

import android.graphics.Canvas;
import android.graphics.PointF;

public abstract class MovingGameObject extends GameObject implements IMoveable {
	PointF velocity = new PointF();
	PointF acceleration = new PointF();
	
	@Override
	public void update() {}

	@Override
	public void onDraw(Canvas c) {}
}
