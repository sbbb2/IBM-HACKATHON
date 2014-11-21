package com.example.hackathonsample;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import bolts.Continuation;
import bolts.Task;

import com.ibm.mobile.services.data.IBMDataException;
import com.ibm.mobile.services.data.IBMDataObject;
import com.ibm.mobile.services.data.IBMQuery;


public class MainActivity extends ActionBarActivity {

	public static final String CLASS_NAME = "MainActivity"; 
	List<PublicData> objects = new ArrayList<PublicData>();
	
	PublicDataListAdapter dataAdapter = null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showData();
        
        populateListView();
        
    }
    
    public void find(View view) {
		EditText et = (EditText) findViewById(R.id.zipcode);
		String zipcode = et.getText().toString();
		dataAdapter.filter(zipcode);
		System.out.println("List called");
	}
    
    public void populateListView() {
    	dataAdapter = new PublicDataListAdapter(this, objects);
        ListView bucketListView = (ListView) findViewById(R.id.dataList);
        bucketListView.setAdapter(dataAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    private void showData() {
		try {
			IBMQuery<PublicData> query = IBMQuery.queryForClass(PublicData.class);
			// Query all the Item objects from the server.
			query.find().continueWith(new Continuation<List<PublicData>, Void>() {

				
				@Override
				public Void then(Task<List<PublicData>> task) throws Exception {
					 objects = task.getResult();
					// Log if the find was cancelled.
					if (task.isCancelled()) {
						Log.e(CLASS_NAME, "Exception : Task " + task.toString()
								+ " was cancelled.");
					}
					// Log error message, if the find task fails.
					else if (task.isFaulted()) {
						Log.e(CLASS_NAME, "Exception : "
								+ task.getError().getMessage());
					}

					// If the result succeeds, load the list.
					else {
						// Clear local itemList.
						// We'll be reordering and repopulating from
						// DataService.
						//itemList.clear();
						for (IBMDataObject item : objects) {
							PublicData data = (PublicData) item;
							Log.i("Jaggu",data.getRequestType());
							/*data.getZipCode()
							data.getArea()
							data.getStreet()
							data.getCreationDate()*/
							dataAdapter.setObject(data);
							
						}
					}
					return null;
				}
			}, Task.UI_THREAD_EXECUTOR);

		} catch (IBMDataException error) {
			Log.e(CLASS_NAME, "Exception : " + error.getMessage());
		}
	}

}
