package com.shepherd;


import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PointF;
//import android.graphics.PointF;

public class Sheep extends MovingGameObject {
	//private final float dt = 0.2f;
    private final double SHEEP_RUN     = 3.0;
    private final double SHEEP_BEE     = 1.0;
    
	private double lastRndAngle = 0;
	
	private GameView gameField;
	private Bitmap bmp;
	private PointF bmpOffset;
	
	public Sheep(GameView gameField)
	{
		this.position.x = (float)Math.random() * 240 + 120; //gameField.getWidth();
		this.position.y = (float)Math.random() * 400 + 200; //gameField.getHeight();
		this.gameField = gameField;
		this.bmp = BitmapFactory.decodeResource(gameField.getResources(), R.drawable.pic_sheep_main);
		this.bmpOffset = new PointF(bmp.getWidth() / 2, bmp.getHeight() / 2);
	}
	
	@Override
	public void update() 
	{
	    // рассчитываем центр масс овечек
	    double sum_x = 0.0;
	    double sum_y = 0.0;
	    
	    ArrayList<MovingGameObject> sheeps = gameField.getSheeps();
	    for (MovingGameObject sheep: sheeps)
	    {
	      sum_x += sheep.getX();
	      sum_y += sheep.getY();
	    }
	    
	    PointF sheeps_center = new PointF(	(float)sum_x / sheeps.size(),
	                         				(float)sum_y / sheeps.size());
	    
        // рассчитываем расстояния до пастуха и центра масс
	    MovingGameObject shepherd = gameField.getShepherd();

    	double dist_to_shepherd = Utilities.getDistance(this, shepherd);
    	double dist_shepherd_center = Utilities.getDistance(shepherd, sheeps_center);
    	double dist_to_center = Utilities.getDistance(this, sheeps_center);

    	double dist_shepherd_1 = 50.0;
    	double dist_shepherd_2 = 50.0;
    	double dist_sheep_center_1 = 10.0;

        if (dist_to_shepherd > dist_shepherd_1)
        {
        	this.randomMove(SHEEP_BEE);
        }
        else if (dist_shepherd_center > dist_shepherd_2)
        {
          if (dist_to_center < dist_sheep_center_1)
            this.randomMove(SHEEP_BEE);
          else
          {
            double a = Math.atan2(	sum_y / sheeps.size() - this.getY(),
                             		sum_x / sheeps.size() - this.getX());
            this.move(a, SHEEP_RUN);
          }
        }
        else
        {
          double a = Math.atan2(	this.getY() - shepherd.getY(),
                           			this.getX() - shepherd.getX());
          this.move(a, SHEEP_RUN);
        }
	}
	
	@Override
	public void onDraw(Canvas c)
	{
		c.drawBitmap(bmp, this.position.x - this.bmpOffset.x, this.position.y - this.bmpOffset.y, null);
	}
	
	public void SetPosition(PointF p)
	{
		this.position.x = p.x;
		this.position.y = p.y;
	}
	
	private void randomMove(double len)
	{
        double angle = Math.random() * Math.PI / 4.0 - Math.PI / 8.0;
        this.move(lastRndAngle + angle, len);
        lastRndAngle = lastRndAngle + angle;	
	}
	
	private void move(double angle, double len)
	{
        this.position.x += Math.cos(angle) * len;
        this.position.y += Math.sin(angle) * len;
	}
}
