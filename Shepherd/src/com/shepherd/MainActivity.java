package com.shepherd;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener{
	private View buttonStartNewGame;
	private View buttonExit;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        this.buttonStartNewGame = (Button) this.findViewById(R.id.buttonStartNewGame);
        this.buttonExit         = (Button) this.findViewById(R.id.buttonExit);
        
        this.buttonStartNewGame.setOnClickListener(this);
        this.buttonExit.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void onClick(View view) {
        if(view == this.buttonStartNewGame){
          Intent intent = new Intent(this, GameActivity.class);
          this.startActivity(intent);
        }
        else if (view == this.buttonExit){
        		this.finish();
        }
    }
}
