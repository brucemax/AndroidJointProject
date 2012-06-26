package com.brucemax;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ThreeActivity extends Activity implements OnClickListener{
	Button btnGo;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.three);
		btnGo = (Button) findViewById(R.id.button1);
		btnGo.setOnClickListener(this);
		Log.d("myLogs", "create ThreeActivity");
		
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		 
		Log.d("myLogs", "onclick in ThreeActivity");
			Intent intent = new Intent(this, GameActivity.class);
			
			startActivity(intent);
		
		
		
	}

}
