package com.kirimchiqim;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class AddNew extends AppCompatActivity {
    // creating variables for our edittext, button and dbhandler
    //private EditText itemNameEdt, itemTypeEdt, itemTimeEdt, itemDescriptionEdt;
    private EditText itemNameEdt, itemTimeEdt, itemAmountEdt, itemDescriptionEdt;
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
        itemType1 = findViewById(R.id.an_Chbx_idItemType1);
        itemType2 = findViewById(R.id.an_Chbx_ItemType2);
        itemTimeEdt = findViewById(R.id.an_Edit_ItemDateTime);
        itemDescriptionEdt = findViewById(R.id.an_Edit_ItemDescription);
        itemAmountEdt = findViewById(R.id.an_Edit_ItemAmount);

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
                if (itemName.isEmpty() && itemDateTime.isEmpty() && !TextUtils.isEmpty(itemAmountEdt.getText())) {
                    Toast.makeText(AddNew.this, R.string.alert_enter_all_values, Toast.LENGTH_SHORT).show();
                    return;
                }
                Integer itemAmount = Integer.parseInt(itemAmountEdt.getText().toString());
                if ((!itemType1.isChecked() && !itemType2.isChecked()) || (itemType1.isChecked() && itemType2.isChecked())) {
                    Toast.makeText(AddNew.this, R.string.alert_check_only_one_box, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (itemType1.isChecked())
                    itemType = getString(R.string.TYPE_Income);
                if (itemType2.isChecked())
                    itemType = getString(R.string.TYPE_Expenditure);

                // on below line we are calling a method to add new
                // course to sqlite data and pass all our values to it.
                dbHandler.addNewItem(itemName, itemType, itemAmount.toString(), itemDateTime, itemDescription);

                // after adding the data we are displaying a toast message.
                Toast.makeText(AddNew.this, R.string.alert_item_added_successfully, Toast.LENGTH_SHORT).show();
                itemNameEdt.setText("");
                itemTimeEdt.setText("");
                itemType2.setSelected(false);
                itemType1.setSelected(false);
                itemAmountEdt.setText("");
                itemDescriptionEdt.setText("");
            }
        });

    }


}