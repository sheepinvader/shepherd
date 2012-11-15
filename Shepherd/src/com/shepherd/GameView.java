package com.shepherd;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.text.TextPaint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView {
	private GameThread thread;

	private ShepherdTarget shepherdTarget;
	private Shepherd shepherd;
	private ArrayList<Sheep> sheeps;
	
	private Paint paint;
	
	private boolean running = false;
	private boolean paused = false;
	private boolean gameover = false;
	private double scores = 0;
	    
	//-------------Start of GameThread--------------------------------------------------\\
	    
	    public class GameThread extends Thread
	    {
	        private GameView view;	 
	        
	        public GameThread(GameView view) 
	        {
	              this.view = view;
	        }
	    
	        public void run()
	        {
	            while (running)
	            {
	            	if (!paused)
		            {
		                Canvas canvas = null;
		                try
		                {                 
		                    canvas = view.getHolder().lockCanvas();
		                    synchronized (view.getHolder())
		                    {
		                        update();
		                        onDraw(canvas);		                        
		                    }
		                }
		                catch (Exception e) 
		                { 
		                	running = false;
		                }
		                finally
		                {
		                    if (canvas != null)
		                    	view.getHolder().unlockCanvasAndPost(canvas);
		                }
		                
		            	scores += 0.05;
		                
		            	if (gameover)
		            	{
		            		Context context = this.view.getContext();
		            		Intent intent = new Intent(context, GameOverActivity.class);
		            		
		                	((Activity)context).startActivityForResult(intent, 0);
		                	
		            		break;
		            	}
		            }
	            	else
	            	{
	            		try {
							GameThread.sleep(500);
						} 
	            		catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	            	}
	            }
	        }
	    }
	//-------------End of GameThread--------------------------------------------------\\
	           
	    public GameView(Context context, int countSheep) 
	    {
	        super(context);       

	        shepherdTarget 	= new ShepherdTarget(this);
	        shepherd 		= new Shepherd(this);
	        
	        sheeps = new ArrayList<Sheep>();
	        for(int i = 0; i!= countSheep;++i)
	        	sheeps.add(new Sheep(this));

	        thread = new GameThread(this);
	        
	        paint = new TextPaint();
        	paint.setColor(Color.BLUE);
        	paint.setTextSize(36);
        	
	        getHolder().addCallback(
	        	new SurfaceHolder.Callback() 
		        {
	               public void surfaceDestroyed(SurfaceHolder holder) 
	               {
	            	   killThread();
	               }
	               
	               public void surfaceCreated(SurfaceHolder holder) 
	               {
	            	   running = true;
            		   thread.start();
	               }
	               
	               public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) 
	               {
	               }
		        }
	        );
	    }
	    
	    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight)
	    {
	    	shepherdTarget.SetPosition(new PointF(width / 2, height / 2));
	    	shepherd.SetPosition(new PointF(width / 2, height / 2));
	    	
	    	PointF p = null;
	    	for(Sheep sheep: sheeps){
	    		p = new PointF(	(float)((Math.random() + 0.5) * width / 2),
	    						(float)((Math.random() + 0.5) * height / 2));
	    		sheep.SetPosition(p);
	    	}
	        
	    }
	    
	    public boolean onTouchEvent(MotionEvent e) 
	    {
	    	if (!paused && e.getAction() == MotionEvent.ACTION_DOWN){
	    		shepherdTarget.SetPosition(new PointF(e.getX(),e.getY()));
	    		shepherdTarget.show();
	    	}
	    	
	        return true;
	    }

	    protected void onDraw(Canvas canvas) {      	
	        canvas.drawColor(Color.rgb(0x6b, 0xe5, 0x06));

	        shepherdTarget.draw(canvas);
	        for (Sheep sheep: sheeps){
	        	sheep.draw(canvas);
		    }		          
	        shepherd.draw(canvas);
	          
	        canvas.drawText("Score: " + Integer.toString((int)scores), 10, 50, paint);
	    }

	    public void update()
	    {
	    	shepherd.update();
	    	for(Sheep sheep: sheeps){
	    		sheep.update();
	    		
	    		if (sheep.getX() < 0 || sheep.getX() > this.getWidth() 
	    			|| sheep.getY() < 0 || sheep.getY() > this.getHeight())
	    		{
	  	          	gameover = true;
	    		}
	    	}
	    }
	    
        public void togglePaused() 
        {
        	paused = !paused;
        }
        
        public ShepherdTarget getShepherdTarget()
	    {
	    	return shepherdTarget;
	    }
	    		
	    public MovingGameObject getShepherd() {
			return shepherd;
		}

		public ArrayList<MovingGameObject> getSheeps() {
			return (ArrayList<MovingGameObject>) sheeps.clone();
		}
	
		public void killThread()
		{
		//TODO fix this function in next version
		      running = false;	
		      while (thread.isAlive())
		      {
		    	  try
		    	  {
		    		  Thread.sleep(1000);
		    	  }
		    	  catch(InterruptedException e){}
		      }	
		}
}
