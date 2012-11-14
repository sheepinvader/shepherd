package com.shepherd;

import java.util.ArrayList;



import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView {
	private GameThread thread;

	private ShepherdTarget shepherdTarget;
	private Shepherd shepherd;
	private ArrayList<Sheep> sheeps;
	private boolean Init = true;
	
	private boolean running = false;
	private boolean paused = false;
	    
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
	    
	    public void initSprites()
	    {
	    	this.shepherd.SetPosition(new PointF(getWidth()/2,getHeight()/2));
	    	PointF p = null;
	    	for(Sheep sheep: sheeps){
	    		p = new PointF((float) (Math.random() * this.getWidth()),(float)(Math.random() * this.getHeight()));
	    		sheep.SetPosition(p);
	    	}
	    }
	    
        public void togglePaused() 
        {
        	paused = !paused;
        }
        
	    public GameView(Context context, int countSheep) 
	    {
	        super(context);
	        shepherdTarget = new ShepherdTarget(this);
	        shepherd = new Shepherd(this);
	        	        
	        sheeps = new ArrayList<Sheep>();
	        for(int i = 0; i!= countSheep;++i)
	        	sheeps.add(new Sheep(this));

	        thread = new GameThread(this);
	        
	        getHolder().addCallback(new SurfaceHolder.Callback() 
	        {
	               public void surfaceDestroyed(SurfaceHolder holder) 
	               {
	            	   boolean retry = true;
	            	   running = false;
	            	   
	            	   while (retry)
	            	   {
	            	       try
	            	       {
	            	           thread.join(1500);
	            	           retry = false;
	            	       }
	            	       catch (InterruptedException e) { }
	            	   }
	               }

	               
	               public void surfaceCreated(SurfaceHolder holder) 
	               {
	            	   running = true;
	            	   thread.start();
	               }

	               
	               public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) 
	               {
	               }
	        });
	    }
	    
	    
	    public boolean onTouchEvent(MotionEvent e) 
	    {
	    	if(e.getAction() == MotionEvent.ACTION_DOWN && !paused){
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
	          canvas.drawColor(Color.rgb(0x6b, 0xe5, 0x06));
	          
	          if(Init){
	        	  initSprites();
	        	  Init = false;
	          }
	          
	          //update and draw all objects
	          shepherdTarget.onDraw(canvas);
	          for(Sheep sheep: sheeps){
		    		sheep.onDraw(canvas);
		      }		          
	          shepherd.onDraw(canvas);
	    }

		public MovingGameObject getShepherd() {
			return shepherd;
		}

		public ArrayList<MovingGameObject> getSheeps() {
			return (ArrayList<MovingGameObject>) sheeps.clone();
		}
	
	
}
