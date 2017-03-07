package com.example.darkknight.cataloger;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import android.view.KeyEvent;
import android.view.View;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.WindowManager;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.github.amlcurran.showcaseview.ShowcaseView;

import com.github.amlcurran.showcaseview.targets.ViewTarget;

import java.util.List;
import java.util.Objects;

import jp.wasabeef.recyclerview.animators.BaseItemAnimator;
import jp.wasabeef.recyclerview.animators.FadeInAnimator;
import jp.wasabeef.recyclerview.animators.FadeInDownAnimator;
import jp.wasabeef.recyclerview.animators.FadeInLeftAnimator;
import jp.wasabeef.recyclerview.animators.FadeInUpAnimator;

public class Activity_ListItems extends AppCompatActivity {


    SharedPreferences cataloger_data_prefs = null;
    String current_list_name = "current_list_name";

    private List<Object_ListItem> object_listItems;
    DataBase_Handler db_handler;
    String dbName = "test2";

    private RecyclerView rv_frag;

    RecyclerViews_Adapter rv_Adapter;
    String klistName = Activity_UsersList.listName;
    private final String test2 = "test2";
    Dialog dialog_edit_listItem;
    TextView total_items_tv, total_amount_tv;

    ImageButton addListItem_btn, addItem_btn;

    TextView listName_tv;

    enum Type {
        FadeIn(new FadeInAnimator(new OvershootInterpolator(1f))),
        FadeInDown(new FadeInDownAnimator(new OvershootInterpolator(1f))),
        FadeInUp(new FadeInUpAnimator(new OvershootInterpolator(1f))),
        FadeInLeft(new FadeInLeftAnimator(new OvershootInterpolator(1f)));

        private BaseItemAnimator mAnimator;

        Type(BaseItemAnimator animator) {
            mAnimator = animator;
        }

        public BaseItemAnimator getAnimator() {
            return mAnimator;
        }
    }

    ShowcaseView sv;

    Boolean firstTimeUser;

    EditText addItem_et;

    Dialog addListItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_activity__list_items);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        listName_tv = (TextView) findViewById(R.id.listName_xtv);


        db_handler = new DataBase_Handler(this, dbName, null, 1);
        rv_frag = (RecyclerView) findViewById(R.id.rv_frag);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv_frag.setLayoutManager(llm);

        total_items_tv = (TextView) findViewById(R.id.total_items);
        total_amount_tv = (TextView) findViewById(R.id.total_amount);


        cataloger_data_prefs = getSharedPreferences("cataloger_data_prefs3", MODE_PRIVATE);
        klistName = cataloger_data_prefs.getString(current_list_name, null);
        object_listItems = db_handler.getListItems(klistName);
        addListItem_btn = (ImageButton) findViewById(R.id.addListItem_xbtn);

        firstTimeUser = cataloger_data_prefs.getBoolean("firstTimeUser", true);

        listName_tv.setText(klistName);
        // list_adapter = new List_Adapter(itemObjectses);
        rv_Adapter = new RecyclerViews_Adapter(object_listItems, "List_Fragment");

        rv_frag.setItemAnimator(Activity_ListItems.Type.FadeInLeft.getAnimator());
        rv_frag.getItemAnimator().setAddDuration(500);
        rv_frag.getItemAnimator().setRemoveDuration(500);


        rv_frag.setAdapter(rv_Adapter);
        updateListDetails();

        addItem_et = (EditText) findViewById(R.id.addItem_xet);
        addItem_btn = (ImageButton) findViewById(R.id.addItem_xbtn);

        if (object_listItems.size() == 1) {
            ViewTarget target = new ViewTarget(R.id.addListItem_xbtn, this);
            sv = new ShowcaseView.Builder(this)
                    .withMaterialShowcase()
                    .setTarget(target)
                    .setContentTitle("Start adding Items to your list from your Catalogs by touching  '+' Sign")
                    .setContentText("")
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            sv.hide();
                            showCase2();
                        }
                    })
                    .setStyle(R.style.CustomShowcaseThemeKaran)
                    .build();
        }

        addItem_et.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    addItem();
                    return true;
                }
                return false;
            }
        });


    }

    public void showCase2() {
        ViewTarget target = new ViewTarget(R.id.addItem_xbtn, this);
        sv = new ShowcaseView.Builder(this)
                .withMaterialShowcase()
                .setTarget(target)
                .setContentTitle("You can also add items manually by typing in your item name and adding the quantity.")
                .setContentText("")
                .setStyle(R.style.CustomShowcaseThemeKaran)
                .build();
    }

    public void addItem() {
        if(!Objects.equals(addItem_et.getText().toString(), "")) {
            final Button addTOList_btn, cancelAddToList_btn;

            final String itemName = addItem_et.getText().toString();
            addListItem = new Dialog(Activity_ListItems.this);
            addListItem.setContentView(R.layout.dialog_item_to_list2);

            final TextView itemName_tv;
            itemName_tv = (TextView) addListItem.findViewById(R.id.itemName_xtv);
            itemName_tv.setText(itemName);
            addItem_et.setText("");

            addTOList_btn = (Button) addListItem.findViewById(R.id.addTOList_xbtn);
            cancelAddToList_btn = (Button) addListItem.findViewById(R.id.cancelAddToList_xbtn);

            final EditText quantity_et;
            quantity_et = (EditText) addListItem.findViewById(R.id.quantity_xet);

            addListItem.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

            addTOList_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String Quantity = quantity_et.getText().toString();
                    if (!Objects.equals(Quantity, "")) {
                        Object_ListItem object_listItem = new Object_ListItem(itemName, null, Quantity, null, null, klistName, null, "1");
                        db_handler.addListItem(object_listItem);
                        addListItem.dismiss();
                        rv_Adapter.addListItem(1, object_listItem);
                    } else {
                        Toast.makeText(Activity_ListItems.this, "Please enter the Quantity.", Toast.LENGTH_LONG).show();

                    }

                }
            });

            cancelAddToList_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addListItem.dismiss();


                }
            });

            addListItem.show();
        }
        else{
            Toast.makeText(Activity_ListItems.this, "Please type your item name.", Toast.LENGTH_LONG).show();
        }
    }

    public void onAddItemsClicked(View v) {
        addItem();
    }

    public void updateListDetails() {
        int total_amount = 0;
        int i = object_listItems.size();
        if (i == 1) {
            total_items_tv.setText(String.valueOf(0));
            total_amount_tv.setText(String.valueOf(0));
        } else {
            for (i = 1; i < object_listItems.size(); i++) {
                Object_ListItem object_listItem = object_listItems.get(i);
                if (object_listItem.getTotal_cost() != null) {
                    int eachTotalCost = Integer.parseInt(object_listItem.getTotal_cost());
                    total_amount += eachTotalCost;
                    Log.d("karan", "total_amount=" + total_amount);
                    total_items_tv.setText(String.valueOf(i));
                    total_amount_tv.setText(String.valueOf(total_amount));
                }
            }
        }
    }


    public void addItems(View v) {
        Intent i = new Intent(getApplicationContext(), Profiles.class);
        i.putExtra("list_name", klistName);
        i.putExtra("firstTimeUser", firstTimeUser);
        startActivity(i);
    }

    public void onEditListItemClicked(View v) {
        dialog_edit_listItem = new Dialog(Activity_ListItems.this);
        dialog_edit_listItem.setContentView(R.layout.dialog_item_to_list);
        RecyclerView.ViewHolder holder = rv_frag.findContainingViewHolder(v);
        final int positon = holder.getAdapterPosition();
        final Object_ListItem object_listItems = rv_Adapter.getListItem(positon);

        dialog_edit_listItem.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);


        final TextView itemName_tv, quantityUnitDialog_tv, quantity_tv, costOf1_tv, totalCost_tv, title_tv;
        final EditText quantity_et;
        Button addTOList_btn, cancelAddToList_btn;

        addTOList_btn = (Button) dialog_edit_listItem.findViewById(R.id.addTOList_xbtn);
        cancelAddToList_btn = (Button) dialog_edit_listItem.findViewById(R.id.cancelAddToList_xbtn);

        title_tv = (TextView) dialog_edit_listItem.findViewById(R.id.addItemTitle_xtv);
        itemName_tv = (TextView) dialog_edit_listItem.findViewById(R.id.itemName_xtv);
        quantityUnitDialog_tv = (TextView) dialog_edit_listItem.findViewById(R.id.quantityUnitDialog_xtv);
        quantity_tv = (TextView) dialog_edit_listItem.findViewById(R.id.quantity_xtv);
        costOf1_tv = (TextView) dialog_edit_listItem.findViewById(R.id.costOf1_xtv);
        totalCost_tv = (TextView) dialog_edit_listItem.findViewById(R.id.totalCost_xtv);

        quantity_et = (EditText) dialog_edit_listItem.findViewById(R.id.quantity_xet);


        final String itemName = object_listItems.getListItem(), quantity_unit;
        String quantityUnitDialog = object_listItems.getQuantity_unit();
        final String[] totalCostStr = new String[1];
        final String itemType = object_listItems.getItem_type();
        final int[] costOf1 = new int[1];
        final int[] quantity = new int[1];
        final int[] totalCost = new int[1];

        //   alertDialogBuilder.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        title_tv.setText("Edit List Item");
        addTOList_btn.setText("Change");

        quantity_et.requestFocus();

        itemName_tv.setText(itemName);
        quantityUnitDialog_tv.setText(quantityUnitDialog);
        Log.d("karan", "price:" + object_listItems.getPrice());
        costOf1[0] = Integer.parseInt(object_listItems.getPrice());
        costOf1_tv.setText(String.valueOf(costOf1[0]));

        quantity_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString();
                quantity_tv.setText(text);


                // totalCost_tv.setText(totalCost[0]);

                if (!Objects.equals(quantity_et.getText().toString(), "")) {
                    quantity[0] = Integer.parseInt(quantity_et.getText().toString());
                    totalCost[0] = costOf1[0] * quantity[0];
                    totalCostStr[0] = String.valueOf(totalCost[0]);

                    totalCost_tv.setText(totalCostStr[0]);
                } else if (Objects.equals(quantity_et.getText().toString(), "")) {
                    quantity_tv.setText("Quantity");
                    totalCost_tv.setText("0");
                }
            }

        });

        quantity_unit = object_listItems.getQuantity_unit();

        addTOList_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Quantity = quantity_et.getText().toString();
                Object_ListItem object_listItem1 = new Object_ListItem(itemName, itemType, Quantity, quantity_unit, totalCostStr[0], String.valueOf(costOf1[0]), "false", "1");

                db_handler.editListItem(object_listItems, Quantity, totalCostStr[0]);
                object_listItems.setQuantity(Quantity);
                object_listItems.setTotal_cost(totalCostStr[0]);
                rv_Adapter.notifyItemChanged(positon);


                //   Toast.makeText(Activity_ListItems.this, "You clicked yes button", Toast.LENGTH_LONG).show();
                dialog_edit_listItem.dismiss();
                updateListDetails();
            }
        });

        cancelAddToList_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   Toast.makeText(Activity_ListItems.this, "You clicked cancel button", Toast.LENGTH_LONG).show();
                dialog_edit_listItem.dismiss();

            }
        });


        dialog_edit_listItem.show();
    }

    public void onDeletListItemCicked(View v) {
        RecyclerView.ViewHolder holder = rv_frag.findContainingViewHolder(v);
        final int position = holder.getAdapterPosition();

        Object_ListItem object_listItem = rv_Adapter.getListItemList().get(position);

        db_handler.deleteListItem(object_listItem);

        rv_Adapter.remove(position);

        updateListDetails();
    }

    public void goToUsersList(View v) {
        Intent i = new Intent(getApplicationContext(), Activity_UsersList.class);
        i.putExtra("STOP", "STOP");
        startActivity(i);
    }

}
