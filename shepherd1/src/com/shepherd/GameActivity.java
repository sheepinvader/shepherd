package com.shepherd;

import android.os.Bundle;
import android.app.Activity;
 
import android.util.Log;
import android.view.Menu;

public class GameActivity extends Activity {
     
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try{
        	GameView gameView = new GameView(this, 3, 0);
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

 
}
