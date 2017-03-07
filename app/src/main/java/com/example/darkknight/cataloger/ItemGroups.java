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
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ItemGroups extends AppCompatActivity{
//        implements NavigationView.OnNavigationItemSelectedListener {

    public List<Object_ItemGroups> object_itemGroups;

    private RecyclerView itemGroup_rv;

    E_RecyclerView_Adapter e_rv_Adapter;

    DataBase_Handler db_Handler;

    SharedPreferences karan_data_prefs = null;

    TextView listItems_tb_tv,listNameItemgroup_tv;

    static String profile_name;
    String dbName, list_name;

    EditText addItemGroup_et;

    private List<Object_ListItem> object_listItems;

    int shoppingBagNumber;

    Boolean itemIsAdded = false;

    Dialog dialog_add_item, dialog_edit_item, dialog_edit_itemGroup, dialog_addTOList, dialog_delete, dialog_edit_listItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_item_groups);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent i = getIntent();
        profile_name = i.getStringExtra("profile_name");
        dbName = i.getStringExtra("databaseName");
        list_name = i.getStringExtra("list_name");

        listNameItemgroup_tv = (TextView) findViewById(R.id.listNameItemgroup_xtv);
        listNameItemgroup_tv.setText(list_name);



        itemGroup_rv = (RecyclerView) findViewById(R.id.itemGroup_xrv);

        karan_data_prefs = getSharedPreferences("karan_data_prefs", MODE_PRIVATE);

        addItemGroup_et = (EditText) findViewById(R.id.addItemGroup_xet);

        listItems_tb_tv = (TextView) findViewById(R.id.listItems_tb_xtv);


        object_itemGroups = new ArrayList<>();




        // dbName = "test2";
        db_Handler = new DataBase_Handler(this, dbName, null, 1);

        object_itemGroups = db_Handler.getItemGroups(profile_name);

        object_listItems = db_Handler.getListItems(list_name);

        e_rv_Adapter = new E_RecyclerView_Adapter(this, object_itemGroups);

        itemGroup_rv.setAdapter(e_rv_Adapter);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        itemGroup_rv.setLayoutManager(llm);

        e_rv_Adapter.getChildPostionByName("Bread");

     /*   shoppingBagNumber = object_listItems.size() - 1;

        updateTextView(shoppingBagNumber);
*/

        addItemGroup_et.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                  addItemGroup();
                    return true;
                }
                return false;
            }
        });



    }

    @Override
    protected void onResume() {
        e_rv_Adapter.collapseAllParents();
        object_listItems = db_Handler.getListItems(list_name);
        shoppingBagNumber = object_listItems.size() - 1;
        updateTextView(shoppingBagNumber);
        e_rv_Adapter.setExpandCollapseListener(new ExpandableRecyclerAdapter.ExpandCollapseListener() {
                                                   @Override
                                                   public void onListItemExpanded(int position) {
                                                       for (int k = 1; k < object_listItems.size(); k++) {
                                                           Object_ListItem object_listItem = object_listItems.get(k);
                                                           String listItemName = object_listItem.getListItem();
                                                           int childPosition = e_rv_Adapter.getChildPostionByName(listItemName);
                                                           Object_Items object_items = e_rv_Adapter.getItem(childPosition);
                                                           object_items.setIsAdded("true");

                                                           if (e_rv_Adapter.getItemsofParent(position).size() > 0) {
                                                               e_rv_Adapter.notifyChildItemChanged(position, 0);
                                                           }
                                                       }
                                                   }


                                                   @Override
                                                   public void onListItemCollapsed(int position) {

                                                   }
                                               }

        );
        super.onResume();
    }


    public void updateTextView(int shoppingBagNumber) {

        listItems_tb_tv.setText(String.valueOf(shoppingBagNumber));
    }


    public void deleteChiildButtonClickedK(View v) {
        RecyclerView.ViewHolder holder = itemGroup_rv.findContainingViewHolder(v);
        if (holder != null && holder.getClass().equals(Holder_Item.class)) {
           // Toast.makeText(getApplicationContext(), "current positionadsf:" + holder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
            final int position = holder.getAdapterPosition();
            String parentName = e_rv_Adapter.getItemGroupName(position);
            final Object_Items object_items = e_rv_Adapter.getItem(position);
            String childName = object_items.getItemName();

            Log.d("karan", "karan");


            dialog_delete = new Dialog(ItemGroups.this);
            dialog_delete.setContentView(R.layout.dialog_delete_stuff);

            TextView delete_title, delete_xtv;
            Button deletet_xbtn, cancel_xbtn;

            delete_title = (TextView) dialog_delete.findViewById(R.id.deleteStuff_xtitle);
            delete_xtv = (TextView) dialog_delete.findViewById(R.id.deleteStuff_xtv);
            deletet_xbtn = (Button) dialog_delete.findViewById(R.id.deleteStuff_xbtn);
            cancel_xbtn = (Button) dialog_delete.findViewById(R.id.cancelDeleteStuff_xbtn);

            delete_title.setText("Delete My List");
            delete_xtv.setText("Are you sure your want to delete \"" + childName + "\"?");

            deletet_xbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    db_Handler.deleteItem(object_items);
                    e_rv_Adapter.removeChildItem(position);
                    dialog_delete.dismiss();

                }
            });
            cancel_xbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog_delete.dismiss();
                }
            });

            dialog_delete.show();

        }

    }


    public void deleteParentButtonClickedK(View v) {
        RecyclerView.ViewHolder holder = itemGroup_rv.findContainingViewHolder(v);
        if (holder != null && holder.getClass().equals(Holder_ItemGroup.class)) {
            final int position = holder.getAdapterPosition();
           // Toast.makeText(getApplicationContext(), "current position:" + holder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
            final Object_ItemGroups object_itemGroups = e_rv_Adapter.getItemGroup(position);
            String itemGroupName = object_itemGroups.getItemGroupName();

            dialog_delete = new Dialog(ItemGroups.this);
            dialog_delete.setContentView(R.layout.dialog_delete_stuff);

            TextView delete_title, delete_xtv;
            Button deletet_xbtn, cancel_xbtn;

            delete_title = (TextView) dialog_delete.findViewById(R.id.deleteStuff_xtitle);
            delete_xtv = (TextView) dialog_delete.findViewById(R.id.deleteStuff_xtv);
            deletet_xbtn = (Button) dialog_delete.findViewById(R.id.deleteStuff_xbtn);
            cancel_xbtn = (Button) dialog_delete.findViewById(R.id.cancelDeleteStuff_xbtn);

            delete_title.setText("Delete My List");
            delete_xtv.setText("Are you sure your want to delete \"" + itemGroupName + "\"?");

            deletet_xbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    db_Handler.deleteItemGroup(object_itemGroups);
                    e_rv_Adapter.deleteParent(position);
                    dialog_delete.dismiss();

                }
            });
            cancel_xbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog_delete.dismiss();
                }
            });

            dialog_delete.show();
        }

    }

    public void addItemGroup(){
        String NO_ITEM = "no_item", ITEM_FOUND = "item_found";

        String itemGroupName = addItemGroup_et.getText().toString();
        if (!Objects.equals(itemGroupName, "")) {
            if (Objects.equals(db_Handler.checkItemGroup(new Object_ItemGroups(null, itemGroupName, profile_name)), NO_ITEM)) {
                addItemGroup_et.setText("");
                List<Object_Items> initial_child;
                initial_child = new ArrayList<>();

                Object_ItemGroups object_itemGroups1 = new Object_ItemGroups(initial_child, itemGroupName, profile_name);
                db_Handler.addItemsGroup(object_itemGroups1);

                object_itemGroups.add(object_itemGroups1);
                int k = object_itemGroups.size();
                e_rv_Adapter.notifyParentItemInserted(k - 1);
                e_rv_Adapter.expandParent(k - 1);
                addItemGroup_et.setText("");
            } else {
                Toast.makeText(getApplicationContext(), "Item Group Already Exists.", Toast.LENGTH_LONG).show();

            }
        } else {
            Toast.makeText(getApplicationContext(), "Please Enter an Item Group Name.", Toast.LENGTH_LONG).show();
        }
    }

    public void addItemsGroupClicked(View v) {
       addItemGroup();
    }


    public void addItemClicked(final View view) {

        dialog_add_item = new Dialog(ItemGroups.this);
        dialog_add_item.setContentView(R.layout.dialog_add_item);


        final EditText itemName_et, quantityUnits_et, type_et, price_et;
        Button addItem_btn, cancelAddItem_btn;
        addItem_btn = (Button) dialog_add_item.findViewById(R.id.addItem_xbtn);
        cancelAddItem_btn = (Button) dialog_add_item.findViewById(R.id.cancelAddItem_xbtn);

        itemName_et = (EditText) dialog_add_item.findViewById(R.id.itemName_xet);
        quantityUnits_et = (EditText) dialog_add_item.findViewById(R.id.quantityUnits_xet);
        type_et = (EditText) dialog_add_item.findViewById(R.id.type_xet);
        price_et = (EditText) dialog_add_item.findViewById(R.id.price_xet);

        dialog_add_item.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);


        addItem_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemName = itemName_et.getText().toString();
                String quantityUnits = quantityUnits_et.getText().toString();
                String type = type_et.getText().toString();
                String price = price_et.getText().toString();

                if (!Objects.equals(itemName, "") && !Objects.equals(quantityUnits, "") && !Objects.equals(type, "") && !Objects.equals(price, "")) {

                    addItem(itemName, quantityUnits, type, price, view);
                  //  Toast.makeText(ItemGroups.this, "You clicked yes button", Toast.LENGTH_LONG).show();
                    dialog_add_item.dismiss();
                } else {
                    Toast.makeText(ItemGroups.this, "Please fill all the fields.", Toast.LENGTH_LONG).show();
                }
            }
        });

        cancelAddItem_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(ItemGroups.this, "You clicked cancel button", Toast.LENGTH_LONG).show();
                dialog_add_item.dismiss();
            }
        });
        dialog_add_item.show();
    }


    public void addItem(String ingrdntName, String quantityUnits, String type, String price, View v) {
        String NO_ITEM = "no_item", ITEM_FOUND = "item_found";
        RecyclerView.ViewHolder holder = itemGroup_rv.findContainingViewHolder(v);
        if (holder != null && holder.getClass().equals(Holder_ItemGroup.class)) {
            //Object_Items object_items = e_rv_Adapter.getItem(holder.getAdapterPosition());
            String itemGroupName = e_rv_Adapter.getItemGroupName(holder.getAdapterPosition());
            Object_Items object_Items = new Object_Items(ingrdntName, quantityUnits, type, price, itemGroupName, profile_name, "false");
            Log.d("karan", "childPosition :" + holder.getAdapterPosition());

            e_rv_Adapter.expandParent(e_rv_Adapter.getParentPosition(holder.getAdapterPosition()));
            String checkItem = db_Handler.checkItem(object_Items);
            if (Objects.equals(checkItem, NO_ITEM)) {
                e_rv_Adapter.addItem(holder.getAdapterPosition(), object_Items);
                db_Handler.addItem(object_Items);
            } else if (Objects.equals(checkItem, ingrdntName)) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                LayoutInflater inflater = ItemGroups.this.getLayoutInflater();
                View layout = inflater.inflate(R.layout.dialog_edit_listitem, null);

                TextView listItemString = (TextView) layout.findViewById(R.id.listItemNameString_xtv);


                final String itemName = ingrdntName;


                listItemString.setText(itemName + " already exists in your current list.");
                alertDialogBuilder.setView(layout)
                        .setPositiveButton("Okay",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                   //     Toast.makeText(ItemGroups.this, "You clicked cancel button", Toast.LENGTH_LONG).show();
                                    }
                                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }

        }
    }


    public void addToItemList(View v) {
        Log.d("karan", "itemIsAdded1:" + itemIsAdded);
        String ITEM_FOUND = "item_found";

        RecyclerView.ViewHolder holder = itemGroup_rv.findContainingViewHolder(v);
        int position = holder.getAdapterPosition();
        final Object_Items object_Items = e_rv_Adapter.getItem(position);
        String checkString = db_Handler.checkListItem(object_Items, list_name);

        if (Objects.equals(checkString, ITEM_FOUND)) {
            editListItemDialog(object_Items, position);
            itemIsAdded = false;
        } else {
            setListItemQuantityDialog(object_Items, position);


            Log.d("karan", "itemIsAdded2:" + itemIsAdded);
            itemIsAdded = true;

        }


    }

    public void setBgColor(int position) {
        final Object_Items object_Items = e_rv_Adapter.getItem(position);
        String isAdded = object_Items.getIsAdded();
        if(Objects.equals(isAdded, "true")) {
            object_Items.setIsAdded("false");
        }
        else if(Objects.equals(isAdded,"false")) {
            object_Items.setIsAdded("true");
        }

        e_rv_Adapter.notifyChildItemChanged(e_rv_Adapter.getParentPosition(position), e_rv_Adapter.getChildPostion(position));
        db_Handler.editItem(object_Items, object_Items);
    }


    public void setListItemQuantityDialog(Object_Items object_Items, final int position) {

        dialog_addTOList = new Dialog(ItemGroups.this);
        dialog_addTOList.setContentView(R.layout.dialog_item_to_list);

        final TextView itemName_tv, quantityUnitDialog_tv, quantity_tv, costOf1_tv, totalCost_tv;
        final EditText quantity_et;
        final Button addTOList_btn, cancelAddToList_btn;

        addTOList_btn = (Button) dialog_addTOList.findViewById(R.id.addTOList_xbtn);
        cancelAddToList_btn = (Button) dialog_addTOList.findViewById(R.id.cancelAddToList_xbtn);

        itemName_tv = (TextView) dialog_addTOList.findViewById(R.id.itemName_xtv);
        quantityUnitDialog_tv = (TextView) dialog_addTOList.findViewById(R.id.quantityUnitDialog_xtv);
        quantity_tv = (TextView) dialog_addTOList.findViewById(R.id.quantity_xtv);
        costOf1_tv = (TextView) dialog_addTOList.findViewById(R.id.costOf1_xtv);
        totalCost_tv = (TextView) dialog_addTOList.findViewById(R.id.totalCost_xtv);
        quantity_et = (EditText) dialog_addTOList.findViewById(R.id.quantity_xet);



        final String itemName = object_Items.getItemName(), quantity_unit;
        String quantityUnitDialog = object_Items.getItemQuantityUnit();
        final String[] totalCostStr = new String[1];
        final String itemType = object_Items.getItemType();
        final int[] costOf1 = new int[1];
        final int[] quantity = new int[1];
        final int[] totalCost = new int[1];


        dialog_addTOList.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        Log.d("karan", "itemName:" + itemName);

        quantity_et.requestFocus();

        itemName_tv.setText(itemName);
        quantityUnitDialog_tv.setText(quantityUnitDialog);

        costOf1[0] = object_Items.getItemPrice();
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

        quantity_unit = object_Items.getItemQuantityUnit();

        addTOList_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Quantity = quantity_et.getText().toString();
                if (!Objects.equals(Quantity, "")) {
                    Object_ListItem object_listItem = new Object_ListItem(itemName, itemType, Quantity, quantity_unit, totalCostStr[0], list_name, String.valueOf(costOf1[0]), "1");
                    Log.d("karan", "itemIsAdded3:" + itemIsAdded);
                    if (itemIsAdded) {
                        db_Handler.addListItem(object_listItem);
                        shoppingBagNumber = shoppingBagNumber + 1;
                        updateTextView(shoppingBagNumber);
                        setBgColor(position);
                        itemIsAdded = false;
                    } else {
                        Log.d("karan", "itemIsAdded4:" + itemIsAdded);
                        db_Handler.deleteListItem(new Object_ListItem(itemName, list_name));
                        db_Handler.addListItem(object_listItem);

                    }
                //    Toast.makeText(ItemGroups.this, "You clicked yes button.", Toast.LENGTH_LONG).show();

                    dialog_addTOList.dismiss();


                } else {
                    Toast.makeText(ItemGroups.this, "Please enter the Quantity.", Toast.LENGTH_LONG).show();

                }

            }

        });

        cancelAddToList_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(ItemGroups.this, "You clicked cancel button.", Toast.LENGTH_LONG).show();
                dialog_addTOList.dismiss();


            }
        });

        dialog_addTOList.show();
    }


    public void editListItemDialog(final Object_Items object_items, final int position) {
        dialog_edit_listItem = new Dialog(ItemGroups.this);
        dialog_edit_listItem.setContentView(R.layout.dialog_delete_stuff);

        final String listItemName = object_items.getItemName();


        TextView delete_title, delete_xtv;
        Button deletet_xbtn, cancel_xbtn;
        ImageView bin_iv;

        delete_title = (TextView) dialog_edit_listItem.findViewById(R.id.deleteStuff_xtitle);
        delete_xtv = (TextView) dialog_edit_listItem.findViewById(R.id.deleteStuff_xtv);
        deletet_xbtn = (Button) dialog_edit_listItem.findViewById(R.id.deleteStuff_xbtn);
        cancel_xbtn = (Button) dialog_edit_listItem.findViewById(R.id.cancelDeleteStuff_xbtn);
        bin_iv = (ImageView) dialog_edit_listItem.findViewById(R.id.bin_xiv);

        bin_iv.setVisibility(View.INVISIBLE);
        delete_title.setText("Item Already Added");
        delete_xtv.setText("\"" + listItemName + "\" already exists in your current list.\n Would like to change the quantity or remove it from your list?");
        deletet_xbtn.setText("Change");
        cancel_xbtn.setText("Remove");

        deletet_xbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setListItemQuantityDialog(object_items, 0);
                dialog_edit_listItem.dismiss();

            }
        });
        cancel_xbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db_Handler.deleteListItem(new Object_ListItem(listItemName, list_name));
                setBgColor(position);
                shoppingBagNumber = shoppingBagNumber - 1;
                updateTextView(shoppingBagNumber);
                dialog_edit_listItem.dismiss();
            }
        });

        dialog_edit_listItem.show();

    }


    public void onEditItemGroupClicked(View v) {
        final RecyclerView.ViewHolder holder = itemGroup_rv.findContainingViewHolder(v);
        dialog_edit_itemGroup = new Dialog(ItemGroups.this);
        final Object_ItemGroups object_itemGroups = e_rv_Adapter.getItemGroup(holder.getAdapterPosition());

        String oldItemGroupName = object_itemGroups.getItemGroupName();

        if (holder.getClass().equals(Holder_ItemGroup.class)) {
            dialog_edit_itemGroup.setContentView(R.layout.edit_itemgroup_dialog);

            dialog_edit_itemGroup.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

            Button editItemGroup_btn, cancelItemGroup_btn;
            final EditText editItemGroupStr = (EditText) dialog_edit_itemGroup.findViewById(R.id.newItemGroupName_xet);
            final String[] newItemGroupName = new String[1];
            editItemGroup_btn = (Button) dialog_edit_itemGroup.findViewById(R.id.editItemGroup_xbtn);
            cancelItemGroup_btn = (Button) dialog_edit_itemGroup.findViewById(R.id.cancelItemGroup_xbtn);
            editItemGroupStr.setText(oldItemGroupName);
            editItemGroupStr.setSelection(editItemGroupStr.getText().length());


            editItemGroup_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    newItemGroupName[0] = editItemGroupStr.getText().toString();
                    if (!Objects.equals(newItemGroupName[0], "")) {
                        String NO_ITEM = "no_item", ITEM_FOUND = "item_found";


                        if (Objects.equals(db_Handler.checkItemGroup(new Object_ItemGroups(null, newItemGroupName[0], profile_name)), NO_ITEM)) {


                            db_Handler.editItemGroup(newItemGroupName[0], object_itemGroups);
                            e_rv_Adapter.editItemGroupName(newItemGroupName[0], holder.getAdapterPosition());
                            dialog_edit_itemGroup.dismiss();
                        } else if (Objects.equals(db_Handler.checkItemGroup(new Object_ItemGroups(null, newItemGroupName[0], profile_name)), ITEM_FOUND)) {
                            Toast.makeText(getApplicationContext(), "Item Group Already Exists.", Toast.LENGTH_LONG).show();

                        }
                    } else {
                        Toast.makeText(ItemGroups.this, "Please enter new Item Group Name", Toast.LENGTH_LONG).show();

                    }
                }

            });

            cancelItemGroup_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 //   Toast.makeText(ItemGroups.this, "You clicked cancel button", Toast.LENGTH_LONG).show();
                    dialog_edit_itemGroup.dismiss();
                }
            });


            dialog_edit_itemGroup.show();
        }
    }


    public void editItemClicked(final View view) {
        final String NO_ITEM = "no_item", ITEM_FOUND = "item_found";

        RecyclerView.ViewHolder holder = itemGroup_rv.findContainingViewHolder(view);
        if (holder != null && holder.getClass().equals(Holder_Item.class)) {
          //  Toast.makeText(getApplicationContext(), "current positionadsf:" + holder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
            final int position = holder.getAdapterPosition();

            final Object_Items object_items = e_rv_Adapter.getItem(position);
            String groupName = object_items.getItemGroupName();
            final String itemName = object_items.getItemName();
            String item_quantityUnit = object_items.getItemQuantityUnit();
            String item_type = object_items.getItemType();
            int item_price = object_items.getItemPrice();
            final String item_groupName = object_items.getItemGroupName();
            final String item_profileName = object_items.getProfile_name();
            final String isAdded = object_items.getIsAdded();

            dialog_edit_item = new Dialog(ItemGroups.this);
            dialog_edit_item.setContentView(R.layout.dialog_add_item);


            TextView addItemTitle_tv;
            final EditText itemName_et, quantityUnits_et, type_et, price_et;
            final Button editItem_btn, cancelAddItem_btn;

            addItemTitle_tv = (TextView) dialog_edit_item.findViewById(R.id.addItemTitle_xtv);
            editItem_btn = (Button) dialog_edit_item.findViewById(R.id.addItem_xbtn);
            cancelAddItem_btn = (Button) dialog_edit_item.findViewById(R.id.cancelAddItem_xbtn);
            itemName_et = (EditText) dialog_edit_item.findViewById(R.id.itemName_xet);
            quantityUnits_et = (EditText) dialog_edit_item.findViewById(R.id.quantityUnits_xet);
            type_et = (EditText) dialog_edit_item.findViewById(R.id.type_xet);
            price_et = (EditText) dialog_edit_item.findViewById(R.id.price_xet);

            addItemTitle_tv.setText("Edit Item");
            editItem_btn.setText("Edit Item");
            itemName_et.setText(itemName);
            quantityUnits_et.setText(item_quantityUnit);
            type_et.setText(item_type);
            price_et.setText(String.valueOf(item_price));

            itemName_et.setSelection(itemName_et.getText().length());
            quantityUnits_et.setSelection(quantityUnits_et.getText().length());
            type_et.setSelection(type_et.getText().length());
            price_et.setSelection(price_et.getText().length());

            editItem_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String name = itemName_et.getText().toString();
                    String quantityUnits = quantityUnits_et.getText().toString();
                    String type = type_et.getText().toString();
                    String price = price_et.getText().toString();
                    Object_Items object_items1 = new Object_Items(name, quantityUnits, type, price, item_groupName, item_profileName, isAdded);
                    if (!Objects.equals(name, "") && !Objects.equals(quantityUnits, "") && !Objects.equals(type, "") && !Objects.equals(price, "")) {
                        if (Objects.equals(db_Handler.checkItem(object_items1), itemName) || Objects.equals(db_Handler.checkItem(object_items1), NO_ITEM)) {
                            db_Handler.editItem(object_items, object_items1);
                            object_items.setItemName(name);
                            object_items.setItemQuantityUnit(quantityUnits);
                            object_items.setItemType(type);
                            object_items.setItemPrice(price);

                            e_rv_Adapter.notifyChildItemChanged(e_rv_Adapter.getParentPosition(position), e_rv_Adapter.getChildPostion(position));
                         //   Toast.makeText(ItemGroups.this, "You clicked yes button", Toast.LENGTH_LONG).show();
                            dialog_edit_item.dismiss();
                        } else {
                            Toast.makeText(ItemGroups.this, "Item already exits.", Toast.LENGTH_LONG).show();
                        }

                    } else {
                        Toast.makeText(ItemGroups.this, "Please fill all the fields.", Toast.LENGTH_LONG).show();
                    }
                }
            });

            cancelAddItem_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 //   Toast.makeText(ItemGroups.this, "You clicked cancel button", Toast.LENGTH_LONG).show();
                    dialog_edit_item.dismiss();
                }
            });

            dialog_edit_item.show();
        }
    }


    public void doneBtnClicked(View v) {
        Intent i = new Intent(ItemGroups.this, Activity_ListItems.class);

        startActivity(i);

    }


}

