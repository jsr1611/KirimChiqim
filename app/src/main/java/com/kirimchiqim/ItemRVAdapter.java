package com.kirimchiqim;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemRVAdapter extends RecyclerView.Adapter<ItemRVAdapter.ViewHolder> {

    // variable for our array list and context
    private ArrayList<ItemModal> itemModalArrayList;
    private Context context;

    // constructor
    public ItemRVAdapter(ArrayList<ItemModal> courseModalArrayList, Context context) {
        this.itemModalArrayList = courseModalArrayList;
        this.context = context;
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
        holder.itemDateTimeTV.setText(modal.getDateAndTime());
        System.out.println("DEBUGGGGGGGGGGGGGGGGG income: " +  context.getResources().getString(R.string.income));
        System.out.println("DEBUGGGGGGGGGGGGGGGGG expenditure: " + context.getResources().getString(R.string.expenditure));

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
        private TextView itemNameTV, itemDescTV, itemAmountTV, itemDateTimeTV, itemTypeTV;
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