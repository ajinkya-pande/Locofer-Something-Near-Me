package com.example.somethingnearme;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;

public class ItemDescription extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_desciption);
        DBHandler dbHandler = new DBHandler(getApplicationContext());

        // Get Card Data from Main Page
        DBHandler.ContentModel cardData = (DBHandler.ContentModel) getIntent().getSerializableExtra("cardData");

        // Imageview
        ImageView contentDescImage = findViewById(R.id.contentDescImage);
        // Textview
        TextView nameTextview = findViewById(R.id.nameTextview);
        TextView ratingTextview = findViewById(R.id.ratingTextview);
        TextView locationTextview = findViewById(R.id.locationTextview);
        TextView priceTextview = findViewById(R.id.priceTextview);
        // Button
        Button placeOrderButton = findViewById(R.id.placeOrderButton);
        Button rateItemButton = findViewById(R.id.rateItemButton);

        // Set content from card object to textview
        contentDescImage.setImageBitmap(cardData.getImage());
        nameTextview.setText(cardData.getName());
        ratingTextview.setText(String.valueOf(cardData.getRating()));
        locationTextview.setText(cardData.getLocation());
        priceTextview.setText(String.valueOf(cardData.getPrice()));

        // Handle order placement
        placeOrderButton.setOnClickListener(View -> {
            Toast.makeText(this, "Order Placed", Toast.LENGTH_SHORT).show();
            finish();
            try {
                dbHandler.addNewOrder("user", cardData.getName());
//                Log.d("Order", dbHandler.getCustomerOrderDetails("user").getOrderList().toString());
            } catch (Exception e) {
                Log.d("Error", e.getMessage());
            }
        });

        rateItemButton.setOnClickListener(View -> {

        });

    }
}