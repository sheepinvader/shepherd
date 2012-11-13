package com.shepherd;

import java.util.ArrayList;



import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView {
	private GameThread mThread;
	
	private final long DELTA_T = 100l; // in milliseconds
	
	private ShepherdTarget shepherdTarget;
	private Shepherd shepherd;
	
	private ArrayList<Sheep> sheeps;
	private ArrayList<Grass> grassPlots;
	
	private boolean running = false;
	    
	//-------------Start of GameThread--------------------------------------------------\\
	    
	    public class GameThread extends Thread
	    {
	    
	        private GameView view;	 
	        
	    
	        public GameThread(GameView view) 
	        {
	              this.view = view;
	        }

	    
	        public void setRunning(boolean run) 
	        {
	              running = run;
	        }

	    
	        public void run()
	        {
	            while (running)
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
	                catch (Exception e) { }
	                finally
	                {
	                    if (canvas != null)
	                    {
	                    	view.getHolder().unlockCanvasAndPost(canvas);
	                    }
	                }
	            }
	        }
	    }

	//-------------End of GameThread--------------------------------------------------\\
	    
	    public GameView(Context context, int countSheep,int contGrass) 
	    {
	        super(context);
	        
	        shepherdTarget = new ShepherdTarget(this);
	        shepherd = new Shepherd(this);	        	        
	        sheeps = new ArrayList<Sheep>();
	        for(int i = 0; i!= countSheep;++i){
	        	sheeps.add(new Sheep(this));
	        }
	        
	        
	        mThread = new GameThread(this);
	              
	        
	        
	        
	        getHolder().addCallback(new SurfaceHolder.Callback() 
	        {
	      	  	 
	               public void surfaceDestroyed(SurfaceHolder holder) 
	               {
	            	   boolean retry = true;
	            	    mThread.setRunning(false);
	            	    while (retry)
	            	    {
	            	        try
	            	        {
	            
	            	            mThread.join();
	            	            retry = false;
	            	        }
	            	        catch (InterruptedException e) { }
	            	    }
	               }

	               
	               public void surfaceCreated(SurfaceHolder holder) 
	               {
	            	   mThread.setRunning(true);
	            	   mThread.start();
	               }

	               
	               public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) 
	               {
	               }
	        });
	    }
	    
	    
	    public boolean onTouchEvent(MotionEvent e) 
	    {
	    	
	    	if(e.getAction() == MotionEvent.ACTION_DOWN){
	    		shepherdTarget.SetPosition(new PointF(e.getX(),e.getY()));
	    		shepherdTarget.show();
	    	}
	    	
	        return true;
	    }
	    
	    public ShepherdTarget getShepherdTarget()
	    {
	    	return shepherdTarget;
	    }
	    
	    public void update()
	    {
	    	shepherd.update();
	    	for(Sheep sheep: sheeps){
	    		sheep.update();
	    	}
	    }
	    protected void onDraw(Canvas canvas) {     	
	          canvas.drawColor(Color.WHITE);
	          //update and draw all objects
	          shepherd.onDraw(canvas);
	          shepherdTarget.onDraw(canvas);
	          for(Sheep sheep: sheeps){
		    		sheep.onDraw(canvas);
		      }	          
	    }
	
	
}
