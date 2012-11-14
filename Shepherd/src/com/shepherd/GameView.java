package com.shepherd;

import android.content.Context;
<<<<<<< HEAD
import android.graphics.Canvas;
=======
>>>>>>> 046c3b627b55ce5c9c4a76438b8e8eebfb5f2c43
import android.view.SurfaceView;

public class GameView extends SurfaceView {
	private GameThread thread;
	
	private boolean running = false;
	
	public GameView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
<<<<<<< HEAD
	public class GameThread extends Thread {
		private GameView view;
		
		public GameThread(GameView view) {
			this.view = view;
		}
		
		public void setRunning(boolean _running) {
			running = _running;
		}
		
		public void run() {
			Canvas canvas = null;
			while (running) {
				try {
					canvas = view.getHolder().lockCanvas();
					synchronized(view.getHolder())
					
				}
			}
		}
	}
=======
	
>>>>>>> 046c3b627b55ce5c9c4a76438b8e8eebfb5f2c43
}
