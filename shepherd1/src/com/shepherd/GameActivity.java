package com.shepherd;
 

 

import android.os.Bundle;
import android.app.Activity;
 
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;


public class GameActivity extends Activity {
	private View editText2;	 
	
     
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        try{
        setContentView(new GameView(this,3,0));
        }
        catch(Exception e){
        	String str = e.getMessage();
        	Log.v("error",str);
//        	System.err.println(e.getMessage());
//        	setContentView(R.layout.activity_game);
//        	((EditText)editText2).setText(e.getMessage());
        }
    }
    
 

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_game, menu);
        return true;
    }

 
}
