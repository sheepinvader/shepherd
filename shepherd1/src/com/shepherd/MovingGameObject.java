package com.shepherd;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.PointF;

public abstract class MovingGameObject extends GameObject implements IMoveable {
	PointF velocity = new PointF();
	PointF acceleration = new PointF();
	
	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDraw(Canvas c) {
		// TODO Auto-generated method stub

	}
}
