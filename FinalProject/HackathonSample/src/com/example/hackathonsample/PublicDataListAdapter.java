package com.example.hackathonsample;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class PublicDataListAdapter extends ArrayAdapter<PublicData> {

	public final Context context;
    public  List<PublicData> objects;
    public  List<PublicData> oldObjects = new ArrayList<PublicData>();
    public String zipcode = null;

    public PublicDataListAdapter(Context context, List<PublicData> objects) {
        super(context, R.layout.data_item, objects);

        this.context = context;
        this.objects = objects;
    }
    
    public  void setObjects(List<PublicData> objects) {
		// TODO Auto-generated method stub
    	this.objects = objects;

	}
    
    public void filter(String zipcode) {
    	
    	this.zipcode = zipcode;
    	notifyDataSetChanged();
    	oldObjects.addAll(objects);
    	objects.clear();
    	for (int i = 0; i < oldObjects.size(); i++) {
			if(oldObjects.get(i).getZipCode().equals(zipcode)) {
				objects.add(oldObjects.get(i));
				notifyDataSetChanged();
			}
		}
    }

    
    public void setObject(PublicData data) {
		this.objects.add(data);
		this.notifyDataSetChanged();
	}
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View itemView = convertView;
        if(itemView == null) {
            itemView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.data_item, parent, false);
        }
        // Bucket
        PublicData data = objects.get(position);
        
        

        TextView requestType = (TextView) itemView.findViewById(R.id.type);
        TextView objectCreationDate = (TextView) itemView.findViewById(R.id.bCreatedDate);        
        TextView zipCode = (TextView) itemView.findViewById(R.id.zipcode);
        TextView street = (TextView) itemView.findViewById(R.id.street);
      
        	requestType.setText(data.getRequestType());
        
        objectCreationDate.setText(data.getCreationDate());
        zipCode.setText(data.getZipCode()+ " Area: " + data.getArea() ); 
        street.setText(data.getStreet());
        
        
        return itemView;
    }

	public List<PublicData> getOldObjects() {
		return oldObjects;
	}

	public void setOldObjects(List<PublicData> oldObjects) {
		this.oldObjects = oldObjects;
	}

	public List<PublicData> getObjects() {
		return objects;
	}
}
