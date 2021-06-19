package com.kirimchiqim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewItems extends AppCompatActivity {

    // creating variables for our array list,
    // dbhandler, adapter and recycler view.
    private ArrayList<ItemModal> itemModalArrayList;
    private DBHandler dbHandler;
    private ItemRVAdapter itemRVAdapter;
    private RecyclerView itemsRV;
    private CheckBox checkBox1, checkBox2;
    private EditText date_time;
    private TextView totalCount, Sum;
    private Button btn_Check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_items);
        checkBox1 = findViewById(R.id.an_Chbx_idItemType1);
        checkBox2 = findViewById(R.id.an_Chbx_ItemType2);
        date_time = findViewById(R.id.an_Edit_ItemDateTime);
        btn_Check = findViewById(R.id.btn_check);
        totalCount = findViewById(R.id.txt_total_count);
        Sum = findViewById(R.id.txt_total_amount);

        btn_Check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String itemDateTime = date_time.getText().toString();

                // validating if the text fields are empty or not.
                if ((!checkBox1.isChecked() && !checkBox2.isChecked()) || (checkBox1.isChecked() && checkBox2.isChecked())) {
                    Toast.makeText(ViewItems.this, R.string.alert_check_only_one_box, Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    String itemTypeRes = "";
                    if (checkBox1.isChecked()) {
                        itemTypeRes = String.format("'%s'", getString(R.string.TYPE_Income));
                    } else if (checkBox2.isChecked()) {
                        itemTypeRes = String.format("'%s'", getString(R.string.TYPE_Expenditure));
                    } /*else {
                    checkBox1.setSelected(true);
                    itemTypeRes = "kirim";
                }*/


                    // initializing our all variables.
                    itemModalArrayList = new ArrayList<>();
                    dbHandler = new DBHandler(ViewItems.this);

                    // getting our course array
                    // list from db handler class.
                    itemModalArrayList = dbHandler.readItems(itemTypeRes, new String[]{itemDateTime});
                    if (itemModalArrayList.size() == 0) {
                        Toast.makeText(ViewItems.this, R.string.no_data_found, Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        // on below line passing our array lost to our adapter class.
                        itemRVAdapter = new ItemRVAdapter(itemModalArrayList, ViewItems.this);
                        itemsRV = findViewById(R.id.idRVItems);

                        // setting layout manager for our recycler view.
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ViewItems.this, RecyclerView.VERTICAL, false);
                        itemsRV.setLayoutManager(linearLayoutManager);

                        // setting our adapter to recycler view.
                        itemsRV.setAdapter(itemRVAdapter);
                    }


                    // getting total count
                    int totalCount = dbHandler.readTotalCount(itemTypeRes);
                    System.out.println("TotalCounttttttttttttttttttttttttttt: " + totalCount);
                    ViewItems.this.totalCount.setText(String.valueOf(totalCount));

                    // getting total sum
                    int totalSum = dbHandler.readSum(itemTypeRes);
                    System.out.println("TotalSummmmmmmmmmmmmmmmmmmmmmmmmmmm: " + totalSum);
                    Sum.setText(String.valueOf(totalSum));


                }
            }
        });

    }
}