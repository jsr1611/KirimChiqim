package com.kirimchiqim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Objects;

public class ViewItems extends AppCompatActivity {

    // creating variables for our array list,
    // dbhandler, adapter and recycler view.
    private ArrayList<ItemModal> itemModalArrayList;
    private DBHelper dbHelper;
    private ItemRVAdapter itemRVAdapter;
    private RecyclerView itemsRV;
    private CheckBox checkBox1, checkBox2;
    private Button button_startDate, button_EndDate;
    private TextView totalCount, Sum;
    private Button btn_Check;
    private DatePicker myDatePicker_startDate, myDatePicker_endDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_items);
        checkBox1 = findViewById(R.id.an_Chbx_idItemType1);
        checkBox2 = findViewById(R.id.an_Chbx_ItemType2);
        button_startDate = findViewById(R.id.view_ItemDateTime);
        button_EndDate = findViewById(R.id.view_ItemDateTime2);
        btn_Check = findViewById(R.id.btn_check);
        totalCount = findViewById(R.id.txt_total_count);
        Sum = findViewById(R.id.txt_total_amount);
        myDatePicker_startDate = new DatePicker(ViewItems.this, button_startDate);
        myDatePicker_endDate = new DatePicker(ViewItems.this, button_EndDate);

        btn_Check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String itemStartDate = myDatePicker_startDate.getDateStamp();
                String itemEndDate = myDatePicker_endDate.getDateStamp();


                // validating if the text fields are empty or not.
                if (!checkBox1.isChecked() && !checkBox2.isChecked()) {
                    Toast.makeText(ViewItems.this, R.string.alert_check_only_one_box, Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    String itemTypeRes = "";
                    if (checkBox1.isChecked() && checkBox2.isChecked()) {
                        itemTypeRes = String.format("'%s', '%s'", getString(R.string.TYPE_Income), getString(R.string.TYPE_Expenditure));
                    } else if (checkBox2.isChecked()) {
                        itemTypeRes = String.format("'%s'", getString(R.string.TYPE_Expenditure));
                    } else {
                        itemTypeRes = String.format("'%s'", getString(R.string.TYPE_Income));
                    }
                    // initializing our all variables.
                    itemModalArrayList = new ArrayList<>();
                    dbHelper = new DBHelper(ViewItems.this);

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    try {

                        if (!itemStartDate.isEmpty() && !itemEndDate.isEmpty() && !sdf.parse(itemStartDate).before(sdf.parse(itemEndDate))) {
                            if (Objects.requireNonNull(sdf.parse(itemStartDate)).compareTo(sdf.parse(itemEndDate)) != 0) {
                                Toast.makeText(ViewItems.this, R.string.startDateBeforeEndDate, Toast.LENGTH_SHORT).show();
                                return;

                            }
                        }


                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                    // getting our item array
                    // list from db handler class.

                    String[] datesSelected;
                    if (!itemStartDate.isEmpty() && !itemEndDate.isEmpty()) {
                        datesSelected = new String[]{itemStartDate, itemEndDate};
                    } else if (!itemStartDate.isEmpty()) {
                        datesSelected = new String[]{itemStartDate, ""};
                    } else if (!itemEndDate.isEmpty()) {
                        datesSelected = new String[]{"", itemEndDate};
                    } else {
                        datesSelected = new String[]{"", ""};
                    }
                    itemModalArrayList = dbHelper.readItems(itemTypeRes, datesSelected);
                    // on below line passing our array lost to our adapter class.
                    itemRVAdapter = new ItemRVAdapter(itemModalArrayList, ViewItems.this, myDatePicker_startDate);
                    itemsRV = findViewById(R.id.idRVItems);


                    // setting layout manager for our recycler view.
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ViewItems.this, RecyclerView.VERTICAL, false);
                    itemsRV.setLayoutManager(linearLayoutManager);

                    // setting our adapter to recycler view.
                    itemsRV.setAdapter(itemRVAdapter);
                    // getting total count
                    int totalCount = dbHelper.readTotalCount(itemTypeRes);
                    ViewItems.this.totalCount.setText(String.valueOf(totalCount));

                    // getting total sum
                    int totalSum = dbHelper.readSum(itemTypeRes);
                    Sum.setText(String.valueOf(totalSum));

                    if (itemModalArrayList.size() == 0) {

                        Toast.makeText(ViewItems.this, R.string.no_data_found, Toast.LENGTH_SHORT).show();
                        return;
                    }
                  


                }
            }
        });
        button_startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDatePicker_startDate.openDatePicker(v);
            }
        });
        button_EndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDatePicker_endDate.openDatePicker(v);
            }
        });
    }
}