package com.kirimchiqim;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemRVAdapter extends RecyclerView.Adapter<ItemRVAdapter.ViewHolder> {

    // variable for our array list and context
    private ArrayList<ItemModal> itemModalArrayList;
    private Context context;
    private DatePicker myDatePicker;

    // constructor
    public ItemRVAdapter(ArrayList<ItemModal> itemModalArrayList, Context context, DatePicker datePicker) {
        this.itemModalArrayList = itemModalArrayList;
        this.context = context;
        myDatePicker = datePicker;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // on below line we are inflating our layout
        // file for our recycler view items.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // on below line we are setting data
        // to our views of recycler view item.
        ItemModal modal = itemModalArrayList.get(position);
        holder.itemNameTV.setText(modal.getItemName());
        holder.itemDescTV.setText(modal.getItemDescription());
        holder.itemAmountTV.setText(String.valueOf(modal.getItemAmount()));
        String dateTimeStr = modal.getDateAndTime();
        //System.out.printf("\n\n\n\ndateStam before:" + dateTimeStr);
        int _year = 0, _month = 0, _day = 0;
        if(!dateTimeStr.isEmpty()){

            _year = Integer.parseInt(dateTimeStr.substring(0,4));
            _month = Integer.parseInt(dateTimeStr.substring(5,7));
            _day = Integer.parseInt(dateTimeStr.substring(8));
        }

        dateTimeStr =  myDatePicker.makeDateString( _day, _month,_year);
       // System.out.printf("\n\n\ndateStam after:" + dateTimeStr);
        holder.itemDateTimeTV.setText(dateTimeStr);
        //System.out.println("DEBUGGGGGGGGGGGGGGGGG income: " +  context.getResources().getString(R.string.income));
        //System.out.println("DEBUGGGGGGGGGGGGGGGGG expenditure: " + context.getResources().getString(R.string.expenditure));

        String itemTypeStr = modal.getItemType().equals("Income") ? context.getResources().getString(R.string.income) : context.getResources().getString(R.string.expenditure);

        holder.itemTypeTV.setText(String.valueOf(itemTypeStr));
    }

    @Override
    public int getItemCount() {
        // returning the size of our array list
        return itemModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our text views.
        private TextView itemNameTV, itemDescTV, itemAmountTV, itemTypeTV, itemDateTimeTV;
        private CheckBox itemType1TV, itemType2TV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views
            itemNameTV = itemView.findViewById(R.id.idTVItemName);
            itemDescTV = itemView.findViewById(R.id.idTVItemDescription);
            itemAmountTV = itemView.findViewById(R.id.idTVItemAmount);
            itemDateTimeTV = itemView.findViewById(R.id.idTVDateTime);
            itemTypeTV = itemView.findViewById(R.id.idTVItemType);
        }
    }
}