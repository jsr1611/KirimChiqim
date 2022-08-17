package com.kirimchiqim;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kirimchiqim.db.DBHelper;

import java.util.Calendar;

public class AddNew extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    // creating variables for our edittext, button and dbhandler
    //private EditText itemNameEdt, itemTypeEdt, itemTimeEdt, itemDescriptionEdt;
    private EditText itemNameEdt, itemAmountEdt, itemDescriptionEdt;
    private TextView dateText;
    private CheckBox itemType1, itemType2;
    private Button addNewBtn;
    private DBHelper dbHelper;
    private DatePicker datePicker;

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
        addNewBtn = findViewById(R.id.an_btn_addNew);
        dateText = findViewById(R.id.date_text);

        dbHelper = new DBHelper(AddNew.this);

        datePicker = new DatePicker(this);

        // initializing all our variables.
        itemNameEdt = findViewById(R.id.an_Edit_ItemName);
        itemType1 = findViewById(R.id.an_Chbx_idItemType1);
        itemType2 = findViewById(R.id.an_Chbx_ItemType2);
        itemDescriptionEdt = findViewById(R.id.an_Edit_ItemDescription);
        itemAmountEdt = findViewById(R.id.an_Edit_ItemAmount);

        // creating a new dbhandler class
        // and passing our context to it.
        dbHelper = new DBHelper(AddNew.this);

        // below line is to add on click listener for our add course button.
        addNewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // below line is to get data from all edit text fields.
                ItemModal item = new ItemModal();
                item.setItemName(itemNameEdt.getText().toString());
                item.setDateAndTime(datePicker.getDateStamp());
                item.setItemDescription(itemDescriptionEdt.getText().toString());
                String itemType = "";

                // validating if the text fields are empty or not.
                if (item.getItemName().isEmpty() || item.getDateAndTime().isEmpty() || TextUtils.isEmpty(itemAmountEdt.getText())) {
                    //System.out.println("\n\n\n\nitemName.isEmpty():" + itemName.isEmpty() + " itemDateTime.isEmpty():" + itemDateTime.isEmpty() + "  !TextUtils.isEmpty(itemAmountEdt.getText()):" +TextUtils.isEmpty(itemAmountEdt.getText() ));
                    Toast.makeText(AddNew.this, R.string.alert_enter_all_values, Toast.LENGTH_SHORT).show();
                    return;
                }
                int itemAmount = Integer.parseInt(itemAmountEdt.getText().toString());
                item.setItemAmount(itemAmount);
                if ((!itemType1.isChecked() && !itemType2.isChecked()) || (itemType1.isChecked() && itemType2.isChecked())) {
                    Toast.makeText(AddNew.this, R.string.alert_check_only_one_box, Toast.LENGTH_SHORT).show();
                    return;
                }
                itemType = itemType1.isChecked() ? getString(R.string.TYPE_Income) : getString(R.string.TYPE_Expenditure);
                item.setItemType(itemType);
                // on below line we are calling a method to add new
                // course to sqlite data and pass all our values to it.
                dbHelper.addNewItem(item);

                // after adding the data we are displaying a toast message.
                Toast.makeText(AddNew.this, R.string.alert_item_added_successfully, Toast.LENGTH_SHORT).show();
                itemNameEdt.setText("");
                //dateButton_itemDateSelect.setText("");
                itemType2.setSelected(false);
                itemType1.setSelected(false);
                itemAmountEdt.setText("");
                itemDescriptionEdt.setText("");
            }
        });

        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

    }

    private void showDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                );
        datePickerDialog.show();
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
        dateStamp = datePicker.makeDateString(dayOfMonth, month, year);
        datePicker.setDateStamp(String.format("%04d-%02d-%02d",year, month, dayOfMonth));
        dateText.setText(datePicker.getDateStamp());
    }
}