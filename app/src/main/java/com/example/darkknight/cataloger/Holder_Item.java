package com.example.darkknight.cataloger;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;

import java.util.Objects;

/**
 * Created by DARKKNIGHT on 2/12/2017.
 */

public class Holder_Item extends ChildViewHolder {

    private TextView item_tv, itemQuantityUnit_tv, itemType_tv, itemPrice_tv;
    LinearLayout items_ll;


    public Holder_Item(View itemView) {
        super(itemView);
        items_ll = (LinearLayout) itemView.findViewById(R.id.items_xll);
        item_tv = (TextView) itemView.findViewById(R.id.itemName_xtv);
        itemQuantityUnit_tv = (TextView) itemView.findViewById(R.id.itemQuantityUnit_xtv);
        itemType_tv = (TextView) itemView.findViewById(R.id.itemType_xtv);
        itemPrice_tv = (TextView) itemView.findViewById(R.id.itemPrice_xtv);

    }

    public void attachChild(final Object_Items object_items) {
        item_tv.setText(object_items.getItemName());

        itemType_tv.setText(object_items.getItemType());
        int k = object_items.getItemPrice();


        String isAdded = object_items.getIsAdded();
        if (Objects.equals(isAdded, "true")) {
            Log.d("karan22", "item name:" + object_items.getItemName() + "-----item isAdded" + isAdded);
            items_ll.setBackgroundColor(Color.parseColor("#fcd45d"));
        } else {
            Log.d("karan22", "item name:" + object_items.getItemName() + "-----item isAdded" + isAdded);
            items_ll.setBackgroundColor(Color.WHITE);
        }
        if(object_items.getItemQuantityUnit().length()<=4){
            itemQuantityUnit_tv.setText("/" + object_items.getItemQuantityUnit());
            itemPrice_tv.setText(String.valueOf(k));
        }
        else if (object_items.getItemQuantityUnit().length()>4){
            itemQuantityUnit_tv.setText("/" + object_items.getItemQuantityUnit());
            itemQuantityUnit_tv.setTextSize(7);
            itemPrice_tv.setText(String.valueOf(k));
            itemPrice_tv.setTextSize(10);
        }
    }

}
