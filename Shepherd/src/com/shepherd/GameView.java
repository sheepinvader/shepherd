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
	private boolean paused  = false;
	private boolean failed  = false;
	private double  scores  = 0;
	    
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
		                catch (Exception e) { running = false; }
		                finally
		                {
		                    if (canvas != null)
		                    	view.getHolder().unlockCanvasAndPost(canvas);
		                }
		                if (failed)
		                {
		                	//TODO add failed dialog show
		                	break;
		                }
		                scores = scores + 0.1;
		                //TODO add scores out
		            }
	            	else
	            	{
	            		try
	            		{
	            			sleep(500);
	            		}catch(InterruptedException e) {}
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
        
        public void setFailed()
        {
        	failed = true;
        }
        
	    public GameView(Context context, int countSheep) 
	    {
	        super(context);
	        shepherdTarget = new ShepherdTarget(this);
	        shepherd       = new Shepherd(this);
	        	        
	        sheeps = new ArrayList<Sheep>();
	        for(int i = 0; i!= countSheep;++i)
	        	sheeps.add(new Sheep(this));

	        thread = new GameThread(this);
	        
	        getHolder().addCallback(new SurfaceHolder.Callback() 
	        {
	               public void surfaceDestroyed(SurfaceHolder holder) 
	               {
	            	   KillThread();
	               }
	               public void surfaceCreated(SurfaceHolder holder) 
	               {
            		   running = true;
            		   thread.start();
	               }
				   public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {}
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
	          //TODO move init to sizechanged
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
	
		public void KillThread()
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
