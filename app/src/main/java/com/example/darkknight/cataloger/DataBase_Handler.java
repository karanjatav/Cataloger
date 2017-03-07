package com.example.darkknight.cataloger;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DARKKNIGHT on 2/12/2017.
 */

public class DataBase_Handler extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    private static String DATABASE_NAME;


    public static final String TABLE_usersLists = "usersLists";
    public static final String TABLE_listItems = "listItems";
    public static final String TABLE_profiles = "profiles";
    public static final String TABLE_itemGroup = "itemGroup";
    public static final String TABLE_items = "items";

    public static final String COLUMN_ID = "id INTEGER PRIMARY KEY AUTOINCREMENT,";
    public static final String COLUMN_profile_name = "profile_name";
    public static final String COLUMN_category = "category";
    public static final String COLUMN_item_group_name = "item_group_name";
    public static final String COLUMN_items = "items";
    public static final String COLUMN_quantity_unit = "quantity_unit";
    public static final String COLUMN_type_company = "type_company";
    public static final String COLUMN_price = "price";
    public static final String COLUMN_listItem = "listItem";
    public static final String COLUMN_quantity = "quantity";
    public static final String COLUMN_total_cost = "total_cost";
    public static final String COLUMN_isChecked = "isChecked";
    public static final String COLUMN_position = "position";
    public static final String COLUMN_usersListName = "usersListName";

    //public static final String COLUMN_ = "";


    public DataBase_Handler(Context context, String dbNme, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, dbNme, factory, DATABASE_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {


        String query1 = "CREATE TABLE " + TABLE_usersLists + " (" +
                COLUMN_ID +
                "usersListName" + " TEXT " +
                ");";
        String query2 = "CREATE TABLE " + TABLE_listItems + "(" +
                COLUMN_ID +
                "listItem" + " TEXT, " +
                COLUMN_type_company + " TEXT, " +
                "quantity" + " TEXT, " +
                COLUMN_quantity_unit + " TEXT, " +
                "total_cost" + " TEXT, " +
                "usersListName" + " TEXT, " +
                COLUMN_isChecked + " VARCHAR, " +
                COLUMN_position + " INTEGER " +
                ");";

        String query3 = "CREATE TABLE " + TABLE_profiles + "(" + COLUMN_ID + " profile_name TEXT, category TEXT );";

        String query4 = "CREATE TABLE " + TABLE_itemGroup + "(" + COLUMN_ID + " item_group_name TEXT, profile_name TEXT );";
             
        String query5 = "CREATE TABLE " + TABLE_items + "(" +
                COLUMN_ID +
                "items  TEXT, " +
                "quantity_unit TEXT, " +
                "type_company TEXT,  " +
                "price TEXT,  " +
                "item_group_name TEXT,  " +
                "profile_name TEXT  " +
                ");";


        db.execSQL(query1);
        db.execSQL(query2);
        db.execSQL(query3);
        db.execSQL(query4);
        db.execSQL(query5);


        ContentValues values1 = new ContentValues();

        values1.put(COLUMN_profile_name, "profile_initial");
        values1.put(COLUMN_category, "category_initial");
        db.insert(TABLE_profiles, null, values1);

        ContentValues values1_2 = new ContentValues();
        values1_2.put(COLUMN_profile_name, "Sample Catalog");
        values1_2.put(COLUMN_category, "Kitchen");
        db.insert(TABLE_profiles, null, values1_2);



        ContentValues values3 = new ContentValues();
        values3.put(COLUMN_usersListName, "usersListInitial_initial");
        db.insert(TABLE_usersLists, null, values3);

        ContentValues values4 = new ContentValues();
        values4.put(COLUMN_item_group_name, "Chilli Potato");
        values4.put(COLUMN_profile_name, "Sample Catalog");
        db.insert(TABLE_itemGroup, null, values4);
        ContentValues values5 = new ContentValues();
        values5.put(COLUMN_item_group_name, "Sandwich");
        values5.put(COLUMN_profile_name, "Sample Catalog");
        db.insert(TABLE_itemGroup, null, values5);
        ContentValues values6 = new ContentValues();
        values6.put(COLUMN_item_group_name, "Burger");
        values6.put(COLUMN_profile_name, "Sample Catalog");
        db.insert(TABLE_itemGroup, null, values6);

        ContentValues values7_1 = new ContentValues();
        values7_1.put(COLUMN_items, "Potato");
        values7_1.put(COLUMN_quantity_unit, "kg");
        values7_1.put(COLUMN_type_company, "Veg");
        values7_1.put(COLUMN_price, "50");
        values7_1.put(COLUMN_item_group_name, "Chilli Potato");
        values7_1.put(COLUMN_profile_name, "Sample Catalog");
        db.insert(TABLE_items, null, values7_1);

        ContentValues values7_2 = new ContentValues();
        values7_2.put(COLUMN_items, "GreenChilli");
        values7_2.put(COLUMN_quantity_unit, "gm");
        values7_2.put(COLUMN_type_company, "Veg");
        values7_2.put(COLUMN_price, "20");
        values7_2.put(COLUMN_item_group_name, "Chilli Potato");
        values7_2.put(COLUMN_profile_name, "Sample Catalog");
        db.insert(TABLE_items, null, values7_2);

        ContentValues values7_3 = new ContentValues();
        values7_3.put(COLUMN_items, "CurryLeaves");
        values7_3.put(COLUMN_quantity_unit, "gms");
        values7_3.put(COLUMN_type_company, "Veg");
        values7_3.put(COLUMN_price, "20");
        values7_3.put(COLUMN_item_group_name, "Chilli Potato");
        values7_3.put(COLUMN_profile_name, "Sample Catalog");
        db.insert(TABLE_items, null, values7_3);

        ContentValues values7_4 = new ContentValues();
        values7_4.put(COLUMN_items, "Bread");
        values7_4.put(COLUMN_quantity_unit, "pkt");
        values7_4.put(COLUMN_type_company, "Modi Bakery");
        values7_4.put(COLUMN_price, "50");
        values7_4.put(COLUMN_item_group_name, "Sandwich");
        values7_4.put(COLUMN_profile_name, "Sample Catalog");
        db.insert(TABLE_items, null, values7_4);

        ContentValues values7_5 = new ContentValues();
        values7_5.put(COLUMN_items, "Cheese Slice");
        values7_5.put(COLUMN_quantity_unit, "pkt");
        values7_5.put(COLUMN_type_company, "Mozzarella");
        values7_5.put(COLUMN_price, "350");
        values7_5.put(COLUMN_item_group_name, "Sandwich");
        values7_5.put(COLUMN_profile_name, "Sample Catalog");
        db.insert(TABLE_items, null, values7_5);

        ContentValues values7_6 = new ContentValues();
        values7_6.put(COLUMN_items, "Cucumber");
        values7_6.put(COLUMN_quantity_unit, "kg");
        values7_6.put(COLUMN_type_company, "Veg");
        values7_6.put(COLUMN_price, "20");
        values7_6.put(COLUMN_item_group_name, "Sandwich");
        values7_6.put(COLUMN_profile_name, "Sample Catalog");
        db.insert(TABLE_items, null, values7_6);

        ContentValues values7_7 = new ContentValues();
        values7_7.put(COLUMN_items, "Bun");
        values7_7.put(COLUMN_quantity_unit, "pkt");
        values7_7.put(COLUMN_type_company, "English Oven");
        values7_7.put(COLUMN_price, "30");
        values7_7.put(COLUMN_item_group_name, "Burger");
        values7_7.put(COLUMN_profile_name, "Sample Catalog");
        db.insert(TABLE_items, null, values7_7);

        ContentValues values7_8 = new ContentValues();
        values7_8.put(COLUMN_items, "Mayonnaise");
        values7_8.put(COLUMN_quantity_unit, "kg");
        values7_8.put(COLUMN_type_company, "Home Made");
        values7_8.put(COLUMN_price, "50");
        values7_8.put(COLUMN_item_group_name, "Burger");
        values7_8.put(COLUMN_profile_name, "Sample Catalog");
        db.insert(TABLE_items, null, values7_8);

        ContentValues values7_9 = new ContentValues();
        values7_9.put(COLUMN_items, "Onion");
        values7_9.put(COLUMN_quantity_unit, "kg");
        values7_9.put(COLUMN_type_company, "Large");
        values7_9.put(COLUMN_price, "25");
        values7_9.put(COLUMN_item_group_name, "Burger");
        values7_9.put(COLUMN_profile_name, "Sample Catalog");
        db.insert(TABLE_items, null, values7_9);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_profiles);
        onCreate(db);
    }


    public String checkUserListName(String userListNAme) {

        String query = "SELECT * FROM " + TABLE_usersLists + " where "+COLUMN_usersListName+"='" + userListNAme +  "'";

        String NO_ITEM = "no_item", ITEM_FOUND = "item_found";
        String returnValue = NO_ITEM;
        SQLiteDatabase db = getWritableDatabase();
        Cursor recordSet = db.rawQuery(query, null);
        recordSet.moveToFirst();

        int c = recordSet.getCount();

        while (!recordSet.isAfterLast()) {

            if (recordSet.getString(recordSet.getColumnIndex(COLUMN_usersListName)) != null) {
                returnValue = ITEM_FOUND;
            }

            recordSet.moveToNext();
        }

        return returnValue;
    }

    public String checkProfileName(String profile_name) {

        String query = "SELECT * FROM " + TABLE_profiles + " where "+COLUMN_profile_name+"='" + profile_name +  "'";
        Log.d("karan","karan:"+profile_name);

        String NO_ITEM = "no_item", ITEM_FOUND = "item_found";
        String returnValue = NO_ITEM;
        SQLiteDatabase db = getWritableDatabase();
        Cursor recordSet = db.rawQuery(query, null);
        recordSet.moveToFirst();

        int c = recordSet.getCount();

        while (!recordSet.isAfterLast()) {

            if (recordSet.getString(recordSet.getColumnIndex(COLUMN_profile_name)) != null) {
                returnValue = recordSet.getString(recordSet.getColumnIndex(COLUMN_profile_name));
            }

            recordSet.moveToNext();
        }
        Log.d("karan","karan:"+returnValue);

        return returnValue;
    }

    public String checkListItem(Object_Items object_item, String listName) {
        String itemName = object_item.getItemName();
        String query = "SELECT * FROM " + TABLE_listItems + " where usersListName='" + listName + "' AND listItem='" + itemName + "'";

        String NO_ITEM = "no_item", ITEM_FOUND = "item_found";
        String returnValue = NO_ITEM;
        SQLiteDatabase db = getWritableDatabase();
        Cursor recordSet = db.rawQuery(query, null);
        recordSet.moveToFirst();

        int c = recordSet.getCount();

        while (!recordSet.isAfterLast()) {

            if (recordSet.getString(recordSet.getColumnIndex(COLUMN_listItem)) != null) {
                returnValue = ITEM_FOUND;
            }

            recordSet.moveToNext();
        }

        return returnValue;
    }

    public String checkItemGroup(Object_ItemGroups object_itemGroup) {
        String profileName = object_itemGroup.getProfileName();
        String itemGroupName = object_itemGroup.getItemGroupName();
        Log.d("karan","karan:"+itemGroupName+"-----"+profileName);
        String query = "SELECT * FROM " + TABLE_itemGroup + " where " +COLUMN_item_group_name+"='" + itemGroupName + "' AND "+COLUMN_profile_name+"='" + profileName + "'";

        String NO_ITEM = "no_item", ITEM_FOUND = "item_found";
        String returnValue = NO_ITEM;

        SQLiteDatabase db = getWritableDatabase();
        Cursor recordSet = db.rawQuery(query, null);
        recordSet.moveToFirst();

        int c = recordSet.getCount();

        while (!recordSet.isAfterLast()) {

            if (recordSet.getString(recordSet.getColumnIndex(COLUMN_item_group_name)) != null) {
                returnValue = ITEM_FOUND;
            }

            recordSet.moveToNext();
        }
        Log.d("karan","karan:"+returnValue);
        return returnValue;
    }

    public String checkItem(Object_Items object_item) {
        String itemName = object_item.getItemName();
        String itemGroupName = object_item.getItemGroupName();
        Log.d("karan","karan:"+itemName+"-----"+itemGroupName);
        String query = "SELECT * FROM " + TABLE_items + " where " +COLUMN_items+"='" + itemName + "' AND "+COLUMN_item_group_name+"='" + itemGroupName + "'";

        String NO_ITEM = "no_item", ITEM_FOUND = "item_found";
        String returnValue = NO_ITEM;
        SQLiteDatabase db = getWritableDatabase();
        Cursor recordSet = db.rawQuery(query, null);
        recordSet.moveToFirst();

        int c = recordSet.getCount();

        while (!recordSet.isAfterLast()) {

            if (recordSet.getString(recordSet.getColumnIndex(COLUMN_items)) != null) {
                returnValue = recordSet.getString(recordSet.getColumnIndex(COLUMN_items));
            }

            recordSet.moveToNext();
        }

        Log.d("karan","karan:"+returnValue);

        return returnValue;
    }


    public void addUsersList(Object_UsersList object_usersList) {
        String usersListName = object_usersList.getUsersListName();

        ContentValues values = new ContentValues();
        values.put(COLUMN_usersListName, usersListName);

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_usersLists, null, values);

        addListItem(new Object_ListItem("initial_data", "initial_data", "initial_data", "initial_data", "initial_data", usersListName, "0", "0"));
        db.close();
    }

    public void addListItem(Object_ListItem object_listItem) {
        String quantity = object_listItem.getQuantity(),
                quantity_unit = object_listItem.getQuantity_unit(),
                totalCost = object_listItem.getTotal_cost(),
                listItem = object_listItem.getListItem(),
                listItemType = object_listItem.getItem_type(),
                userListName = object_listItem.getUsersList(),
                itemPrice = object_listItem.getPrice(),
                item_position = object_listItem.getItem_position();

        ContentValues values = new ContentValues();
        values.put(COLUMN_listItem, listItem);
        values.put(COLUMN_type_company, listItemType);
        values.put(COLUMN_quantity, quantity);
        values.put(COLUMN_quantity_unit, quantity_unit);
        values.put(COLUMN_total_cost, totalCost);
        values.put(COLUMN_usersListName, userListName);
        values.put(COLUMN_isChecked, itemPrice);
        values.put(COLUMN_position, item_position);


        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_listItems, null, values);

        db.close();
    }


    public void addProfiles(Object_Profile object_profile) {
        String profile_name = object_profile.getProfile_name(),
                category = object_profile.getCategory();

        ContentValues values = new ContentValues();
        values.put(COLUMN_profile_name, profile_name);
        values.put(COLUMN_category, category);

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_profiles, null, values);

        db.close();
    }

    public void addItemsGroup(Object_ItemGroups itemGroups) {
        String itemGroup_name = itemGroups.getItemGroupName();
        String profile_name = itemGroups.getProfileName();

        ContentValues values = new ContentValues();
        values.put(COLUMN_item_group_name, itemGroup_name);
        values.put(COLUMN_profile_name, profile_name);

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_itemGroup, null, values);

        db.close();
    }

    public void addItem(Object_Items object_items) {


        ContentValues values = new ContentValues();
        values.put(COLUMN_items, object_items.getItemName());
        values.put(COLUMN_quantity_unit, object_items.getItemQuantityUnit());
        values.put(COLUMN_type_company, object_items.getItemType());
        values.put(COLUMN_price, object_items.getItemPrice());
        values.put(COLUMN_item_group_name, object_items.getItemGroupName());
        values.put(COLUMN_profile_name, object_items.getProfile_name());


        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_items, null, values);

        db.close();

    }

    public List<Object_UsersList> getUsersLists() {

        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_usersLists;

        List<Object_UsersList> object_usersLists = new ArrayList<>();
        String usersListNames;

        Cursor recordSet = db.rawQuery(query, null);
        recordSet.moveToFirst();

        int c = recordSet.getCount();

        while (!recordSet.isAfterLast()) {

            if (recordSet.getString(recordSet.getColumnIndex(COLUMN_usersListName)) != null) {
                usersListNames = recordSet.getString(recordSet.getColumnIndex(COLUMN_usersListName));

                Object_UsersList kobject_usersList = new Object_UsersList(usersListNames);
                object_usersLists.add(kobject_usersList);
            }
            recordSet.moveToNext();
        }

        return object_usersLists;
    }

    public List<Object_ListItem> getListItems(String listName) {

        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_listItems + " where usersListName='" + listName + "' ORDER BY " + COLUMN_position + " ASC";

        List<Object_ListItem> object_listItems = new ArrayList<>();
        String item, item_type, quantity, quantity_unit, total_cost, userListName, isChecked, item_position;

        Cursor recordSet = db.rawQuery(query, null);
        recordSet.moveToFirst();

        int c = recordSet.getCount();

        while (!recordSet.isAfterLast()) {

            if (recordSet.getString(recordSet.getColumnIndex(COLUMN_listItem)) != null) {
                item = recordSet.getString(recordSet.getColumnIndex(COLUMN_listItem));
                item_type = recordSet.getString(recordSet.getColumnIndex(COLUMN_type_company));
                quantity = recordSet.getString(recordSet.getColumnIndex(COLUMN_quantity));
                quantity_unit = recordSet.getString(recordSet.getColumnIndex(COLUMN_quantity_unit));

                total_cost = recordSet.getString(recordSet.getColumnIndex(COLUMN_total_cost));
                userListName = recordSet.getString(recordSet.getColumnIndex(COLUMN_usersListName));
                isChecked = recordSet.getString(recordSet.getColumnIndex(COLUMN_isChecked));
                item_position = String.valueOf(recordSet.getInt(recordSet.getColumnIndex(COLUMN_position)));


                Object_ListItem object_listItem = new Object_ListItem(item, item_type, quantity, quantity_unit, total_cost, userListName, isChecked, item_position);
                object_listItems.add(object_listItem);
            }
            recordSet.moveToNext();
        }

        return object_listItems;
    }

    public List getListItemsNames(String listName) {
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_listItems + " where usersListName='" + listName + "' ORDER BY POSITION ASC";

        List<Object_ListItem> object_listItems = new ArrayList<>();
        String item, item_type, quantity, quantity_unit, total_cost, userListName, isChecked, item_position;

        Cursor recordSet = db.rawQuery(query, null);
        recordSet.moveToFirst();

        int c = recordSet.getCount();

        while (!recordSet.isAfterLast()) {

            if (recordSet.getString(recordSet.getColumnIndex(COLUMN_listItem)) != null) {
                item = recordSet.getString(recordSet.getColumnIndex(COLUMN_listItem));
                userListName = recordSet.getString(recordSet.getColumnIndex(COLUMN_usersListName));


                Object_ListItem object_listItem = new Object_ListItem(item, userListName);
                object_listItems.add(object_listItem);
            }
            recordSet.moveToNext();
        }

        return object_listItems;
    }

    public List<Object_Profile> getProfiles() {


        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_profiles;
        List<Object_Profile> object_profiles = new ArrayList<>();
        String profile_name, category;


        Cursor recordSet = db.rawQuery(query, null);
        recordSet.moveToFirst();

        int c = recordSet.getCount();

        while (!recordSet.isAfterLast()) {

            if (recordSet.getString(recordSet.getColumnIndex(COLUMN_profile_name)) != null) {
                profile_name = recordSet.getString(recordSet.getColumnIndex(COLUMN_profile_name));
                category = recordSet.getString(recordSet.getColumnIndex(COLUMN_category));
                Object_Profile object_profile1 = new Object_Profile(profile_name, category);
                object_profiles.add(object_profile1);
            } else {
                Toast.makeText(MyApplication.getAppContext(), "issue while loading table:" + TABLE_profiles, Toast.LENGTH_LONG).show();
            }

            recordSet.moveToNext();

        }

        return object_profiles;
    }

    public List<Object_ItemGroups> getItemGroups(String profile_name) {

        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT " + COLUMN_item_group_name + " FROM " + TABLE_itemGroup + " where " + COLUMN_profile_name + " = '" + profile_name + "'";
        List<Object_ItemGroups> object_itemGroups = new ArrayList<>();
        String item_group_name;


        Cursor recordSet = db.rawQuery(query, null);
        recordSet.moveToFirst();

        int c = recordSet.getCount();

        while (!recordSet.isAfterLast()) {

            if (recordSet.getString(recordSet.getColumnIndex(COLUMN_item_group_name)) != null) {
                item_group_name = recordSet.getString(recordSet.getColumnIndex(COLUMN_item_group_name));
                //profile_name = recordSet.getString(recordSet.getColumnIndex(COLUMN_profile_name));

                Object_ItemGroups object_itemGroups1 = new Object_ItemGroups(getItems(item_group_name, profile_name), item_group_name, profile_name);
                object_itemGroups.add(object_itemGroups1);
            } else {
                Toast.makeText(MyApplication.getAppContext(), "issue while loading table:" + TABLE_itemGroup, Toast.LENGTH_LONG).show();

            }
            recordSet.moveToNext();
        }


        return object_itemGroups;
    }


    public List<Object_Items> getItems(String item_group_name, String profile_name) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_items + " where " + COLUMN_item_group_name + " = '" + item_group_name + "' AND " + COLUMN_profile_name + "='" + profile_name + "'";
        List<Object_Items> object_items = new ArrayList<>();
        String itemName, itemQuantiyUnit, itemType, itemPrice, itemGroupName;


        Cursor recordSet = db.rawQuery(query, null);
        recordSet.moveToFirst();

        int c = recordSet.getCount();

        while (!recordSet.isAfterLast()) {

            if (recordSet.getString(recordSet.getColumnIndex(COLUMN_items)) != null) {
                itemName = recordSet.getString(recordSet.getColumnIndex(COLUMN_items));
                itemQuantiyUnit = recordSet.getString(recordSet.getColumnIndex(COLUMN_quantity_unit));
                itemType = recordSet.getString(recordSet.getColumnIndex(COLUMN_type_company));
                itemPrice = recordSet.getString(recordSet.getColumnIndex(COLUMN_price));
                itemGroupName = recordSet.getString(recordSet.getColumnIndex(COLUMN_item_group_name));
                profile_name = recordSet.getString(recordSet.getColumnIndex(COLUMN_profile_name));

                Object_Items object_items1 = new Object_Items(itemName, itemQuantiyUnit, itemType, itemPrice, itemGroupName, profile_name,"false");
                object_items.add(object_items1);
            } else {
                Toast.makeText(MyApplication.getAppContext(), "issue while loading table:" + TABLE_items, Toast.LENGTH_LONG).show();
            }

            recordSet.moveToNext();
        }

        return object_items;
    }

    public void deleteUsersList(String userListName) {
        SQLiteDatabase db = getWritableDatabase();
        String query1 = "DELETE FROM " + TABLE_usersLists + " WHERE " + COLUMN_usersListName + "='" + userListName + "'";
        db.execSQL(query1);
        String query2 = "DELETE FROM " + TABLE_listItems + " WHERE  " + COLUMN_usersListName + "='" + userListName + "'";
        db.execSQL(query2);
    }

    public void deleteListItem(Object_ListItem object_listItem) {
        SQLiteDatabase db = getWritableDatabase();
        String listItemName = object_listItem.getListItem();
        String userListName = object_listItem.getUsersList();
        String query1 = "DELETE FROM " + TABLE_listItems + " WHERE " + COLUMN_listItem + "='" + listItemName + "' AND "
                + COLUMN_usersListName + "='" + userListName + "'";
        db.execSQL(query1);
    }


    public void deleteProfile(String profile_name) {
        SQLiteDatabase db = getWritableDatabase();

        String query1 = "DELETE FROM " + TABLE_profiles + " WHERE " + COLUMN_profile_name + "='" + profile_name + "'";
        db.execSQL(query1);
        String query2 = "DELETE FROM " + TABLE_itemGroup + " WHERE " + COLUMN_profile_name + "='" + profile_name + "'";
        db.execSQL(query2);
        String query3 = "DELETE FROM " + TABLE_items + " WHERE " + COLUMN_profile_name + "='" + profile_name + "'";
        db.execSQL(query3);

    }

    public void deleteItemGroup(Object_ItemGroups object_itemGroups) {
        SQLiteDatabase db = getWritableDatabase();
        String item_group_name = object_itemGroups.getItemGroupName();
        String profile_name = object_itemGroups.getProfileName();
        String query1 = "DELETE FROM " + TABLE_itemGroup + " WHERE " + COLUMN_item_group_name + "='" + item_group_name +
                "' AND " + COLUMN_profile_name + "= '" + profile_name + "'";
        db.execSQL(query1);
        String query2 = "DELETE FROM " + TABLE_items + " WHERE " + COLUMN_item_group_name + "='" + item_group_name +
                "' AND " + COLUMN_profile_name + "='" + profile_name + "'";
        db.execSQL(query2);
    }

    public void deleteItem(Object_Items object_items) {

        SQLiteDatabase db = getWritableDatabase();
        String item_name = object_items.getItemName();
        String item_group_name = object_items.getItemGroupName();
        String profile_name = object_items.getProfile_name();

        Log.d("karan","karan22");
        String query = "DELETE FROM " + TABLE_items + " WHERE " + COLUMN_items + " ='" + item_name +
                "' AND " + COLUMN_item_group_name + "='" + item_group_name +
                "' AND " + COLUMN_profile_name + "='" + profile_name + "'";

        Log.d("karan","query="+query);
        db.execSQL(query);


    }

    public void editUserList(String oldUserListName, String newUserListName) {
        SQLiteDatabase db = getWritableDatabase();

        Log.d("karan", "newUserListName=" + newUserListName);

        String query1 = "UPDATE " + TABLE_usersLists + " SET " + COLUMN_usersListName + "='" + newUserListName +
                "' WHERE " + COLUMN_usersListName + "='" + oldUserListName + "'";

        db.execSQL(query1);

        String query2 = "UPDATE " + TABLE_listItems + " SET " + COLUMN_usersListName + "='" + newUserListName +
                "' WHERE " + COLUMN_usersListName + "='" + oldUserListName + "'";

        db.execSQL(query2);


    }

    public void editListItem(Object_ListItem old_object_listItem, String listItem_NewQuantity, String listItem_NewTotalCost) {
        SQLiteDatabase db = getWritableDatabase();

        String listItemName = old_object_listItem.getListItem();
        String userListName = old_object_listItem.getUsersList();


        String query1 = "UPDATE " + TABLE_listItems + " SET " + COLUMN_quantity + "='" + listItem_NewQuantity +
                "'," + COLUMN_total_cost + "='" + listItem_NewTotalCost +
                "' WHERE " + COLUMN_listItem + "='" + listItemName + "' AND "
                + COLUMN_usersListName + "='" + userListName + "'";
                Log.d("karan", "query1=" + query1);
        db.execSQL(query1);


    }

    public void editProfile(Object_Profile old_object_profile, Object_Profile new_object_profile) {
        SQLiteDatabase db = getWritableDatabase();

        String oldProfileName = old_object_profile.getProfile_name();

        String newProfileName = new_object_profile.getProfile_name();
        String newTypeName = new_object_profile.getCategory();


        Log.d("karan", "newItemGroupName=" + newProfileName);

        String query1 = "UPDATE " + TABLE_profiles + " SET " + COLUMN_profile_name + "='" + newProfileName + "'," +
                COLUMN_category + "='" + newTypeName +
                "' WHERE " + COLUMN_profile_name + "='" + oldProfileName + "'";

        db.execSQL(query1);

        String query2 = "UPDATE " + TABLE_itemGroup + " SET " + COLUMN_profile_name + "='" + newProfileName +
                "' WHERE " + COLUMN_profile_name + "='" + oldProfileName + "'";

        db.execSQL(query2);

        String query3 = "UPDATE " + TABLE_items + " SET " + COLUMN_profile_name + "='" + newProfileName +
                "' WHERE " + COLUMN_profile_name + "='" + oldProfileName + "'";

        db.execSQL(query3);


    }


    public void editItemGroup(String newItemGroupName, Object_ItemGroups object_itemGroups) {
        SQLiteDatabase db = getWritableDatabase();
        String oldItemGroupName = object_itemGroups.getItemGroupName();
        String profile_name = object_itemGroups.getProfileName();

        Log.d("karan", "newItemGroupName=" + newItemGroupName);

        String query1 = "UPDATE " + TABLE_itemGroup + " SET " + COLUMN_item_group_name + "='" + newItemGroupName +
                "' WHERE " + COLUMN_item_group_name + "='" + oldItemGroupName
                + "' AND " + COLUMN_profile_name + "='" + profile_name + "'";
        db.execSQL(query1);
        String query2 = "UPDATE " + TABLE_items + " SET " + COLUMN_item_group_name + "='" + newItemGroupName +
                "' WHERE " + COLUMN_item_group_name + "='" + oldItemGroupName
                + "' AND " + COLUMN_profile_name + "='" + profile_name + "'";
        db.execSQL(query2);


    }

    public void editItem(Object_Items old_object_item, Object_Items new_object_item) {
        SQLiteDatabase db = getWritableDatabase();
        String newItemName = new_object_item.getItemName();
        String newItemQuantityUnit = new_object_item.getItemQuantityUnit();
        String newItemType = new_object_item.getItemType();
        String newItemPrice = String.valueOf(new_object_item.getItemPrice());

        String oldItemName = old_object_item.getItemName();
        String itemGroupName = old_object_item.getItemGroupName();
        String profile_name = old_object_item.getProfile_name();

//        Log.d("karan", "newItemGroupName=" + newItemGroupName);

        String query1 = "UPDATE " + TABLE_items + " SET " +
                COLUMN_items + "='" + newItemName + "'," +
                COLUMN_quantity_unit + "='" + newItemQuantityUnit + "'," +
                COLUMN_type_company + "='" + newItemType + "'," +
                COLUMN_price + "='" + newItemPrice + "'" +
                " WHERE " + COLUMN_items + "='" + oldItemName + "'" +
                " AND " + COLUMN_item_group_name + "='" + itemGroupName + "'" +
                " AND " + COLUMN_profile_name + "='" + profile_name + "'";
        Log.d("karan", "query=" + query1);
        db.execSQL(query1);


    }


}
