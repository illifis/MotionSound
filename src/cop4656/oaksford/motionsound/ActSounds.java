package cop4656.oaksford.motionsound;

import java.util.ArrayList;

import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class ActSounds extends Activity implements SensorEventListener{

	ArrayList<String> soundlist = new ArrayList<String>();
	ListView myList;
	ArrayAdapter<String> theone;
	public float lx, ly, lz;
	public boolean mInitialized; 
	public SensorManager mSensorManager; 
	public Sensor mAccelerometer; 
	public final float NOISE = (float) 6;
	public int selected;
	
	private SoundPool soundPool;
	private int soundID;
	boolean loaded = false;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.soundslayout);
        
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        // Load the sound
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        soundPool.setOnLoadCompleteListener(new OnLoadCompleteListener() {
          @Override
          public void onLoadComplete(SoundPool soundPool, int sampleId,
              int status) {
            loaded = true;
          }
        });
       
        //soundID = soundPool.load(this, R.raw.ltsaberhit, 1);
        
        selected = 0;
        
        soundlist.add("Sword");
        soundlist.add("LightSaber");
        soundlist.add("Sound3");
        soundlist.add("Sound4");
        
        myList = (ListView)findViewById(R.id.listView);
        theone = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, soundlist){

            @Override
            public View getView(int position, View convertView,
                    ViewGroup parent) {
                View view =super.getView(position, convertView, parent);
                TextView textView=(TextView) view.findViewById(android.R.id.text1);
                textView.setTextColor(Color.WHITE);
                return view;
            }
        };
        
        myList.setAdapter(theone);
        
        
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
  
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {      
                //selected = soundlist.get(position);
                selected = position;  
                if(selected == 0)
                	 soundID = soundPool.load(getApplicationContext(), R.raw.sword, 1);
                else if(selected == 1)
                	soundID = soundPool.load(getApplicationContext(), R.raw.ltsaberhit, 1);
            }            
        });   
        
        
        
        // motion sensing stuff
        mInitialized = false;
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        
        
        
        
    }

    
    protected void onResume(){
    	super.onResume();
    	mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }
    
    protected void onPause(){
    	super.onPause();
    	mSensorManager.unregisterListener(this);
    }
    
    protected void onStop(){
    	super.onStop();
    	mSensorManager.unregisterListener(this);
    }
    
    protected void onDestroy(){
    	super.onDestroy();
    	mSensorManager.unregisterListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onSensorChanged(SensorEvent event) {
		TextView tx = (TextView) findViewById(R.id.textView1);
		
		//Gets x,y, and z values for the accelerometer.
		float x = event.values[0];
		float y = event.values[1];
		float z = event.values[2];
		if (!mInitialized) {
		lx = x;
		ly = y;
		lz = z;
		

		mInitialized = true;
		}
		else {
			float deltaX = Math.abs(lx - x);
			
			
			// check the values of accelerometer to trigger sound only on proper motion
			// then plays sound
			if((deltaX > NOISE) && (x > -0.5) && (lx < -0.5)){
				AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
		      float actualVolume = (float) audioManager
		          .getStreamVolume(AudioManager.STREAM_MUSIC);
		      float maxVolume = (float) audioManager
		          .getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		      float volume = actualVolume / maxVolume;
		     
		      if (loaded) {
		        soundPool.play(soundID, volume, volume, 1, 0, 1f);
		      }
			//tx.setText(Integer.toString(selected));
			}
			lx = x;

		
		}
	}
	
}
