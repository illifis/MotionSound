package cop4656.oaksford.motionsound;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ActGame extends Activity {

	ArrayList<String> soundlist = new ArrayList<String>();
	ListView myList;
	ArrayAdapter<String> theone;
	Cursor myCur;
	String fill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamelayout);
     /*   
        addentry("Sally", 0);
        addentry("Jim", 0);
        addentry("Steven", 0);
        
        myCur = getall();
        
        for(int i = 0; i< myCur.getCount(); i++)
        {
            if(myCur.moveToNext())
            {
                fill = myCur.getString(1).toString();
                soundlist.add(fill);
            }
            
        }
     
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

            }            
        });   
        */
        
    }

    public Cursor getall() {
        Cursor allcursor;
        allcursor = getContentResolver().query(saveddata.CONTENT_URI, null, null, null, null);
        return allcursor;
    }
    
    
    public void addentry(String name, int score){
        // Function takes in all of the variables for one row/entry in the database, and inserts it.
        
        Uri mNewUri;
        ContentValues mNewValues = new ContentValues();
        
        mNewValues.put(saveddata.COLUMN_TEXT, name.toString().trim());
        mNewValues.put(saveddata.COLUMN_SCORE, score);

        
        mNewUri = getContentResolver().insert(
                saveddata.CONTENT_URI, mNewValues);
    }
    
    public int deleteentry(String n){
        // deletes the entry with the name of the string input. returns number of items deleted (should be 1 everytime)
        int rowsdeleted = 0;
        String mSelectionClause = saveddata.COLUMN_TEXT +  " = ? ";
        String[] mSelectionArgs = {n};
        
        rowsdeleted = getContentResolver().delete(saveddata.CONTENT_URI, mSelectionClause, mSelectionArgs);
        return rowsdeleted;
    }
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
	
}
