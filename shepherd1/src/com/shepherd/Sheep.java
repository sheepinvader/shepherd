package com.shepherd;

 

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
//import android.graphics.PointF;
import android.graphics.PointF;

public class Sheep extends MovingGameObject {
	private final double Lx = 100;	/* размер ячейки вдоль оси x (в единицах sigma) */
	private final double Ly = 100;	/* размер ячейки вдоль оси y (в единицах sigma) */
	private final float Vmax = 1.4f;
	private final float dt = 0.2f;
	
	private GameView gameField;
	private Bitmap bmp;
	
	public Sheep(GameView gameField)
	{
		this.position.x = 0 ;
		this.position.y = 0 ;
		this.gameField = gameField;
		this.bmp = BitmapFactory.decodeResource(gameField.getResources(), R.drawable.pic_potato);
	}
	
	@Override
	public void update() 
	{
		/* находим новые координаты */
		this.position.x += (this.velocity.x + 0.5 * this.acceleration.x * dt) * dt;
		this.position.y += (this.velocity.y + 0.5 * this.acceleration.y * dt) * dt;
		
		/* находим новые скорости (часть 1) */
		if (this.acceleration.x * this.acceleration.x + this.acceleration.y * this.acceleration.y > 1)
		{
			  this.acceleration.x = 0.5f * this.acceleration.x;
		      this.acceleration.y = 0.5f * this.acceleration.y;
		}
		
		if (this.velocity.x * this.velocity.x + this.velocity.y * this.velocity.y > 1)
		{
			  this.velocity.x = 0.02f * this.velocity.x;
			  this.velocity.y = 0.02f * this.velocity.y;
		}
		
		this.velocity.x = 1.0f * this.velocity.x + 0.5f * this.acceleration.x * dt;
		this.velocity.y = 1.0f * this.velocity.y + 0.5f * this.acceleration.y * dt;
		
		/* находим новые ускорения и вычисляем новый потенциал */
		this.acceleration.x = 0;
		this.acceleration.y = 0;

	    MovingGameObject shepherd = gameField.getShepherd();
        float dx = shepherd.position.x - this.position.x;
        float dy = shepherd.position.y - this.position.y;
        float r2 = dx * dx + dy * dy;
        if (r2 < 0.5f)
        		r2 = 0.5f;
        
        float fsh = 0.0f;
        if (r2 < 1600f)
        	fsh = - 500.0f / r2;

        this.acceleration.x = fsh * dt;
        this.acceleration.y = fsh * dt;

		ArrayList<MovingGameObject> sheeps = gameField.getSheeps();
		float c = - this.velocity.x / this.velocity.y;
		float b = - c * (this.position.x + this.position.y);       
		float condition1 = this.position.y + this.velocity.y - c * (this.velocity.x - this.position.y);
		
		float f;            
        for (MovingGameObject sheep: sheeps) {
        //for(j = i + 1; j < count ; j++)
            if (condition1 > 0 && sheep.position.y > c * sheep.position.x + b
            	|| condition1 < 0 && sheep.position.y < c * sheep.position.x + b)
            {
                 dx = this.position.x - sheep.position.x;
                 dy = this.position.y - sheep.position.y;

                 r2 = dx * dx + dy * dy;

                 if (r2 < 0.05f)
                	 r2 = 0.05f;
                 float r2i  = 1.0f / r2;
                 f = 0.1f * r2i * (-25.0f * r2i + 1.0f);

                  this.acceleration.x += f * dx;
                  this.acceleration.y += f * dy;

                 /* используем третий закон Ньютона */
                 sheep.acceleration.x += - this.acceleration.x;
                 sheep.acceleration.y += - this.acceleration.y;
            }
        }

		/* находим новые скорости (часть 2) */
		this.velocity.x = this.velocity.x + 0.5f * this.acceleration.x * dt ;
		this.velocity.y = this.velocity.y + 0.5f * this.acceleration.y * dt ;
	}
	
	public void SetPosition(PointF p)
	{
		this.position.x = p.x;
		this.position.y = p.y;
	}
	
	@Override
	public void onDraw(Canvas c)
	{
		c.drawBitmap(bmp, this.position.x - bmp.getWidth()/2 , this.position.y - bmp.getHeight()/2 , null);
	}
}
