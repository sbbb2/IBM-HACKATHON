package com.example.hackathonsample;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SingleItem extends ListActivity{
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.singleitem);
         
        TextView txtProduct = (TextView) findViewById(R.id.product_label); //(R.id.product_label);
         
        //Intent i = getIntent();
        // getting attached intent data
        //String product = i.getStringExtra("product");
        // displaying selected product name
       // txtProduct.setText(product);
        txtProduct.setText("testValue");
         
    }

}
