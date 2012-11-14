package com.shepherd;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PointF;


public class Shepherd extends MovingGameObject {
    private final double SHEPHERD_MOVE = 4.5;
    private final double SHEPHERD_TARGET_EPS = 2.5;
	
	private GameView gameField;
	private Bitmap bmp;
	private PointF bmpOffset;
	
	public Shepherd(GameView gameField)
	{
		this.velocity.x = 0;
		this.velocity.y = 0;

		this.position.x = 240; //gameField.getWidth() / 2 ; // central position
		this.position.y = 400; //gameField.getHeight() / 2 ;
	
		this.gameField = gameField;
		this.bmp = BitmapFactory.decodeResource(gameField.getResources(), R.drawable.pic_shepherd_main);
		this.bmpOffset = new PointF(bmp.getWidth() / 2, bmp.getHeight() / 2);
	}
	
	@Override
	public void update() {
		ShepherdTarget target = gameField.getShepherdTarget();
		if (target.isVisible())
		{
	        double angle = Utilities.getAngle(this, target);

			this.position.x += Math.cos(angle) * SHEPHERD_MOVE;
			this.position.y += Math.sin(angle) * SHEPHERD_MOVE;
			
	        double distance = Utilities.getDistance(this, target);
	        if (distance < SHEPHERD_TARGET_EPS)
	          target.hide();
		}
	}
	
	public void SetPosition(PointF p)
	{
		this.position.x = p.x;
		this.position.y = p.y;
	}
	
	@Override
	public void onDraw(Canvas c)
	{
		c.drawBitmap(bmp, this.position.x - this.bmpOffset.x, this.position.y - this.bmpOffset.y, null);
	}
}
