package com.kirimchiqim;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.kirimchiqim.db.DBHelper;

public class AddNew extends AppCompatActivity {
    // creating variables for our edittext, button and dbhandler
    //private EditText itemNameEdt, itemTypeEdt, itemTimeEdt, itemDescriptionEdt;
    private EditText itemNameEdt, itemAmountEdt, itemDescriptionEdt;
    private CheckBox itemType1, itemType2;
    private Button addNewItem, dateButton_itemDateSelect;
    private DBHelper dbHelper;
    private DatePicker mydatePicker;

    public String getDateStamp() {
        return dateStamp;
    }

    public void setDateStamp(String dateStamp) {
        this.dateStamp = dateStamp;
    }

    private String dateStamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);
        addNewItem = findViewById(R.id.an_btn_addNew);

        dbHelper = new DBHelper(AddNew.this);


        // initializing all our variables.
        itemNameEdt = findViewById(R.id.an_Edit_ItemName);
        itemType1 = findViewById(R.id.an_Chbx_idItemType1);
        itemType2 = findViewById(R.id.an_Chbx_ItemType2);
        dateButton_itemDateSelect = findViewById(R.id.an_SelectItemDate);
        itemDescriptionEdt = findViewById(R.id.an_Edit_ItemDescription);
        itemAmountEdt = findViewById(R.id.an_Edit_ItemAmount);

        mydatePicker = new DatePicker(AddNew.this, dateButton_itemDateSelect);

        // creating a new dbhandler class
        // and passing our context to it.
        dbHelper = new DBHelper(AddNew.this);



        // below line is to add on click listener for our add course button.
        addNewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // below line is to get data from all edit text fields.

                String itemType = "";
                String itemName = itemNameEdt.getText().toString();
                String itemDateTime = mydatePicker.getDateStamp();

                String itemDescription = itemDescriptionEdt.getText().toString();

                // validating if the text fields are empty or not.
                if (itemName.isEmpty() || itemDateTime.isEmpty() || TextUtils.isEmpty(itemAmountEdt.getText())) {
                    //System.out.println("\n\n\n\nitemName.isEmpty():" + itemName.isEmpty() + " itemDateTime.isEmpty():" + itemDateTime.isEmpty() + "  !TextUtils.isEmpty(itemAmountEdt.getText()):" +TextUtils.isEmpty(itemAmountEdt.getText() ));
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
                dbHelper.addNewItem(itemName, itemType, itemAmount.toString(), itemDateTime, itemDescription);

                // after adding the data we are displaying a toast message.
                Toast.makeText(AddNew.this, R.string.alert_item_added_successfully, Toast.LENGTH_SHORT).show();
                itemNameEdt.setText("");
                dateButton_itemDateSelect.setText("");
                itemType2.setSelected(false);
                itemType1.setSelected(false);
                itemAmountEdt.setText("");
                itemDescriptionEdt.setText("");
            }
        });

        dateButton_itemDateSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydatePicker.openDatePicker(v);
            //setDateStamp(mydatePicker.getDateStamp());
                //System.out.println("TTTTTTTTTTTTTTTTTTime selected: " + getDateStamp());
                //dateButton_itemDateSelect.setText(mydatePicker.getBtnText());
            }
        });

    }


}