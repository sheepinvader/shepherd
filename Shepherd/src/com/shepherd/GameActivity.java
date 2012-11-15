package com.shepherd;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
 
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class GameActivity extends Activity {
    private GameView gameView;
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try{   	
        	gameView = new GameView(this, 8);
        	setContentView(gameView);
        }
        catch(Exception e){
        	Log.d("MyLog in GameActivity", e.toString());
        }
    }
    
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_game, menu);
        return true;
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int item_id = item.getItemId();
		
		if (item_id == R.id.menu_pause)
		{
			gameView.togglePaused();
		}
		else if (item_id == R.id.menu_back)
		{
	        this.finish();
		}
		
		return true;
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		this.finish();
	}
	
	public void onStop() {
		// let's kill game if user paused in alpha release
		gameView.killThread();
	    	super.onPause();
	}
	public void onDestroy() {
		gameView.killThread();
	    super.onDestroy();
	}
}
