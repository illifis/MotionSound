package cop4656.oaksford.motionsound;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void soundclick(View v){
    	//Toast.makeText(getApplicationContext(), "sound", Toast.LENGTH_SHORT).show();
    	Intent soundintent;
    	soundintent = new Intent(this, ActSounds.class);
    	startActivity(soundintent);
    }
    
    public void gameclick(View v){
    	//Toast.makeText(getApplicationContext(), "game", Toast.LENGTH_SHORT).show();
    	Intent gameintent;
    	gameintent = new Intent(this, ActGame.class);
    	startActivity(gameintent);
    }
    
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
