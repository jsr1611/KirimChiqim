package com.kirimchiqim;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class AddNew extends AppCompatActivity {
    // creating variables for our edittext, button and dbhandler
    //private EditText itemNameEdt, itemTypeEdt, itemTimeEdt, itemDescriptionEdt;
    private EditText itemNameEdt, itemTimeEdt, itemDescriptionEdt;
    private CheckBox itemType1, itemType2;
    private Button addNewItem;
    private DBHandler dbHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);
        addNewItem = findViewById(R.id.an_btn_addNew);

        dbHandler = new DBHandler(AddNew.this);

        // initializing all our variables.
        itemNameEdt = findViewById(R.id.an_Edit_ItemName);
        itemType1 = findViewById(R.id.an_Chbx_idItemType1chbox);
        itemType2 = findViewById(R.id.an_Chbx_ItemType2chbox);
        itemTimeEdt = findViewById(R.id.an_Edit_ItemDateTime);
        itemDescriptionEdt = findViewById(R.id.an_Edit_ItemDescription);

        // creating a new dbhandler class
        // and passing our context to it.
        dbHandler = new DBHandler(AddNew.this);

        // below line is to add on click listener for our add course button.
        addNewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // below line is to get data from all edit text fields.

                String itemType = "";
                String itemName = itemNameEdt.getText().toString();
                String itemDateTime = itemTimeEdt.getText().toString();
                String itemDescription = itemDescriptionEdt.getText().toString();

                // validating if the text fields are empty or not.
                if (itemName.isEmpty() && itemDateTime.isEmpty() && itemDescription.isEmpty()) {
                    Toast.makeText(AddNew.this, "Please enter all the data...", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ((!itemType1.isChecked() && !itemType2.isChecked()) || (itemType1.isChecked() && itemType2.isChecked())) {
                    Toast.makeText(AddNew.this, "Please,  check only one box...", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (itemType1.isChecked())
                    itemType = "kirim";
                if (itemType2.isChecked())
                    itemType = "chiqim";

                // on below line we are calling a method to add new
                // course to sqlite data and pass all our values to it.
                dbHandler.addNewItem(itemName, itemDateTime, itemDescription, itemType);

                // after adding the data we are displaying a toast message.
                Toast.makeText(AddNew.this, "Course has been added.", Toast.LENGTH_SHORT).show();
                itemNameEdt.setText("");
                itemTimeEdt.setText("");
                itemType2.setSelected(false);
                itemType1.setSelected(false);
                itemDescriptionEdt.setText("");
            }
        });

    }


}