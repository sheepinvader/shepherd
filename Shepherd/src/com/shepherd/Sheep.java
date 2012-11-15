package com.shepherd;


import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PointF;

public class Sheep extends MovingGameObject {
    private final double SHEEP_RUN     = 3.0;
    private final double SHEEP_BEE     = 1.0;
    
	private double lastRndAngle = 0;
	
	private GameView gameField;
	private Bitmap bmp;
	private PointF bmpOffset;
	
	public Sheep(GameView gameField)
	{
		this.gameField = gameField;
		
		this.bmp = BitmapFactory.decodeResource(gameField.getResources(), R.drawable.pic_sheep_main);
		this.bmpOffset = new PointF(bmp.getWidth() / 2, 
									bmp.getHeight() / 2);
	}
	
	@Override
	public void update() 
	{
	    // рассчитываем центр масс овечек
	    double sumX = 0.0;
	    double sumY = 0.0;
	    
	    ArrayList<MovingGameObject> sheeps = gameField.getSheeps();
	    for (MovingGameObject sheep: sheeps)
	    {
	      sumX += sheep.getX();
	      sumY += sheep.getY();
	    }
	    
	    PointF sheeps_center = new PointF(	(float)sumX / sheeps.size(),
	                         				(float)sumY / sheeps.size());
	    
        // рассчитываем расстояния до пастуха и центра масс
	    MovingGameObject shepherd = gameField.getShepherd();

    	double distanceToShepherd = Utilities.getDistance(this, shepherd);
    	double distanceToShepherdCenter = Utilities.getDistance(shepherd, sheeps_center);
    	double distanceToCenter = Utilities.getDistance(this, sheeps_center);

    	double distanceForShepherd1 = 50.0;
    	double distanceForShepherd2 = 50.0;
    	double distanceForSheepCenter1 = 10.0;

        if (distanceToShepherd > distanceForShepherd1)
        {
        	this.randomMove(SHEEP_BEE);
        }
        else if (distanceToShepherdCenter > distanceForShepherd2)
        {
        	if (distanceToCenter < distanceForSheepCenter1)
        		this.randomMove(SHEEP_BEE);
        	else
        	{
        		double a = Math.atan2(	sumY / sheeps.size() - this.getY(),
                             			sumX / sheeps.size() - this.getX());
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
	public void draw(Canvas c)
	{
		c.drawBitmap(	bmp, 	
						this.position.x - this.bmpOffset.x, 
						this.position.y - this.bmpOffset.y, 
						null);
	}
	
	public void SetPosition(PointF p)
	{
		this.position.x = p.x;
		this.position.y = p.y;
	}
	
	private void randomMove(double len)
	{
        double angle = Math.random() * Math.PI / 4.0 - Math.PI / 8.0;
        
        lastRndAngle += angle;
        this.move(lastRndAngle, len);
        	
	}
	
	private void move(double angle, double len)
	{
        this.position.x += Math.cos(angle) * len;
        this.position.y += Math.sin(angle) * len;
	}
}
