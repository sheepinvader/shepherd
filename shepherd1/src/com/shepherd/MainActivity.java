package com.shepherd;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements View.OnClickListener{
	private View buttonStartNewGame;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        this.buttonStartNewGame = (Button) this.findViewById(R.id.buttonStartNewGame);
        this.buttonStartNewGame.setOnClickListener(this);
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
    }
}
