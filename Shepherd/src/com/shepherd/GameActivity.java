package com.shepherd;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;

import android.app.Activity;
import android.graphics.PointF;
import android.view.Menu;

public class GameActivity extends Activity {
	private final long DELTA_T = 100l; // in milliseconds
	
	private ShepherdTarget shepherdTarget;
	private Shepherd shepherd;
	
	private ArrayList<Sheep> sheeps;
	private ArrayList<Grass> grassPlots;
	
	private Timer timer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        
        timer = new Timer();
        timer.schedule(new TimerTask()
        {
        	@Override
        	public void run()
        	{
        		movingMethod();
        	}
        	
        }, 0l, DELTA_T);
        
        shepherdTarget = new ShepherdTarget(this);
        shepherd = new Shepherd(this);
        
        spawnSheeps();
        spawnGrass();
    }
    
    private void movingMethod()
    {
    	// TODO: implement recalculating parameters for MovingGameObjects
    }

    private void spawnGrass() {
		// TODO Auto-generated method stub
		
	}

	private void spawnSheeps() {
		// TODO Auto-generated method stub
		
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_game, menu);
        return true;
    }

	public PointF getShepherdTargetPosition() {
		return shepherdTarget.position;
	}
}
