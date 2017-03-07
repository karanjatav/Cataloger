package com.example.darkknight.cataloger;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by DARKKNIGHT on 2/12/2017.
 */

public class RecyclerViews_Adapter extends RecyclerView.Adapter<RecyclerViews_Adapter.RecyclerViewHolder> {

    private int k = 0;


    static String userListName;
    DataBase_Handler dataBase_handler;
    String db_name = "test2";

    SharedPreferences cataloger_data_prefs = null;
    String current_list_name = "current_list_name";

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        CardView profile_cv;
        TextView profileName, profile_cat;
        ImageView profileType_iv;
        ImageButton deleteProfile_btn;

        CardView listView_cv;
        CheckBox listItem_cb;
        TextView listItemType_xtv;
        ImageView rupee_xiv;
        TextView quantityFrag_tv, quantityUnitFrag_tv;
        TextView totalCostFrag_tv;
        ImageButton listItemDelete_btn, listItemEdit_btn;

        CardView userList_cv;
        TextView userList_tv;
        ImageButton userListDelete_btn;


        RecyclerViewHolder(View itemView) {
            super(itemView);


            profile_cv = (CardView) itemView.findViewById(R.id.profile_xcv);
            profileName = (TextView) itemView.findViewById(R.id.profileName_xtv);
            profile_cat = (TextView) itemView.findViewById(R.id.category_xtv);
            profileType_iv = (ImageView) itemView.findViewById(R.id.profileType_xiv);
            deleteProfile_btn = (ImageButton) itemView.findViewById(R.id.deleteProfile_xbtn);

            listView_cv = (CardView) itemView.findViewById(R.id.listItem_xcv);
            listItem_cb = (CheckBox) itemView.findViewById(R.id.listItem_xcb);
            listItemType_xtv = (TextView) itemView.findViewById(R.id.listItemType_xtv);
            quantityFrag_tv = (TextView) itemView.findViewById(R.id.quantityFrag_xtv);
            rupee_xiv = (ImageView) itemView.findViewById(R.id.rupee_xiv);


            quantityUnitFrag_tv = (TextView) itemView.findViewById(R.id.quantityUnitFrag_xtv);

            totalCostFrag_tv = (TextView) itemView.findViewById(R.id.totalCostFrag_xtv);
            listItemDelete_btn = (ImageButton) itemView.findViewById(R.id.deleteListItem_xbtn);
            listItemEdit_btn = (ImageButton) itemView.findViewById(R.id.editListItem_xbtn);


            if (Objects.equals(className, "List_Fragment")) {
                quantityUnitFrag_tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = getAdapterPosition();
                        Object_ListItem object_listItem = getListItemList().get(position);
                        //   Toast.makeText(v.getContext(), "item_position=" + object_listItem.getItem_position(), Toast.LENGTH_SHORT).show();
                    }
                });

                listItem_cb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dataBase_handler = new DataBase_Handler(v.getContext(), db_name, null, 1);
                        int position = getAdapterPosition();
                        Object_ListItem object_listItem = getListItemList().get(position);
                        if (Objects.equals(object_listItem.getItem_position(), "2")) {

                            remove(position);
                            dataBase_handler.deleteListItem(object_listItem);
                            object_listItem.setItem_position("1");
                            dataBase_handler.addListItem(object_listItem);
                            addListItem(1, object_listItem);
                        } else {
                            remove(position);
                            dataBase_handler.deleteListItem(object_listItem);
                            object_listItem.setItem_position("2");
                            dataBase_handler.addListItem(object_listItem);
                            addListItem(getListItemList().size(), object_listItem);
                        }
                    }


                });
            }

            userList_cv = (CardView) itemView.findViewById(R.id.userList_xcv);
            userList_tv = (TextView) itemView.findViewById(R.id.userList_xtv);
            userListDelete_btn = (ImageButton) itemView.findViewById(R.id.deleteUserList_xbtn);


        }

    }

    List<Object_ListItem> object_listItems;

    List<Object_UsersList> object_usersLists;

    List<Object_Profile> object_profiles;

    String className;


    RecyclerViews_Adapter(List listType, String className) {
        object_profiles = new ArrayList<>();
        object_usersLists = new ArrayList<>();
        object_listItems = new ArrayList<>();
        this.className = className;
        switch (className) {
            case "Profiles":
                this.object_profiles = listType;
                break;
            case "List_Fragment":
                this.object_listItems = listType;
                break;
            case "UserLists":
                this.object_usersLists = listType;
        }


    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = null;
        // Log.d("karan", "value of int:" + k);
        switch (className) {
            case "Profiles":
                if (k == 0) {
                    v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardviewlayout_profiles_init, viewGroup, false);
                } else {
                    v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardviewlayout_profiles, viewGroup, false);
                }

                break;
            case "List_Fragment":
                if (k == 0) {
                    v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardviewlayout_list_item_init, viewGroup, false);
                } else {
                    v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardviewlayout_list_item, viewGroup, false);
                }
                break;
            case "UserLists":
                if (k == 0) {
                    v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardviewlayout_users_list_init, viewGroup, false);
                } else {
                    v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardviewlayout_users_list, viewGroup, false);
                }
                break;
        }


        k++;
        return new RecyclerViewHolder(v);
    }

    ColorStateList colorStateList = new ColorStateList(
            new int[][]{
                    new int[]{Color.parseColor("#ffffff")} //enabled
            },
            new int[]{Color.parseColor("#ffffff")}
    );
    ColorStateList colorStateList2 = new ColorStateList(
            new int[][]{
                    new int[]{Color.parseColor("#594009")} //enabled
            },
            new int[]{Color.parseColor("#594009")}
    );


    @Override
    public void onBindViewHolder(RecyclerViewHolder recyclerViewHolder, int position) {
        switch (className) {
            case "Profiles":
                recyclerViewHolder.profileName.setText(object_profiles.get(position).profile_name);

                recyclerViewHolder.profile_cat.setText(object_profiles.get(position).category);
                switch (object_profiles.get(position).category) {
                    case "Select Profile Type":
                        recyclerViewHolder.profileType_iv.setImageResource(R.drawable.ic_other_list);
                        recyclerViewHolder.profile_cat.setText("Others");
                        break;
                    case "Kitchen":
                        recyclerViewHolder.profileType_iv.setImageResource(R.drawable.ic_kitchen);
                        break;
                    case "Office":
                        recyclerViewHolder.profileType_iv.setImageResource(R.drawable.ic_office);
                        break;
                    case "Home":
                        recyclerViewHolder.profileType_iv.setImageResource(R.drawable.ic_home);
                        break;
                    case "Party":
                        recyclerViewHolder.profileType_iv.setImageResource(R.drawable.ic_party);
                        break;
                    case "Shop":
                        recyclerViewHolder.profileType_iv.setImageResource(R.drawable.ic_shop);
                        break;
                    case "Others":
                        recyclerViewHolder.profileType_iv.setImageResource(R.drawable.ic_other_list);
                        break;
                }

                break;
            case "List_Fragment":

                if (Objects.equals(object_listItems.get(position).item_position, "2")) {
                    recyclerViewHolder.listItem_cb.setChecked(true);
                    recyclerViewHolder.listItem_cb.setButtonTintList(colorStateList);
                    recyclerViewHolder.listView_cv.setCardBackgroundColor(Color.parseColor("#db9501"));
                    recyclerViewHolder.listItem_cb.setTextColor(Color.parseColor("#ffffff"));
                    recyclerViewHolder.listItemType_xtv.setTextColor(Color.parseColor("#ffffff"));
                    recyclerViewHolder.quantityFrag_tv.setTextColor(Color.parseColor("#ffffff"));
                    recyclerViewHolder.quantityUnitFrag_tv.setTextColor(Color.parseColor("#ffffff"));
                    recyclerViewHolder.rupee_xiv.setBackgroundResource(R.drawable.ic_rupee_white);
                    recyclerViewHolder.totalCostFrag_tv.setTextColor(Color.parseColor("#ffffff"));
                    recyclerViewHolder.listItemDelete_btn.setBackgroundResource(R.drawable.ic_rubbish_bin_white);
                    recyclerViewHolder.listItemEdit_btn.setBackgroundResource(R.drawable.ic_edit_pencil_white);
                }
                if (Objects.equals(object_listItems.get(position).item_position, "1")) {
                    recyclerViewHolder.listItem_cb.setChecked(false);
                    recyclerViewHolder.listItem_cb.setButtonTintList(colorStateList2);
                    recyclerViewHolder.listView_cv.setCardBackgroundColor(Color.parseColor("#ffffff"));
                    recyclerViewHolder.listItem_cb.setTextColor(Color.parseColor("#594009"));
                    recyclerViewHolder.listItemType_xtv.setTextColor(Color.parseColor("#594009"));
                    recyclerViewHolder.quantityFrag_tv.setTextColor(Color.parseColor("#594009"));
                    recyclerViewHolder.quantityUnitFrag_tv.setTextColor(Color.parseColor("#594009"));
                    recyclerViewHolder.rupee_xiv.setBackgroundResource(R.drawable.ic_rupee);
                    recyclerViewHolder.totalCostFrag_tv.setTextColor(Color.parseColor("#594009"));
                    recyclerViewHolder.listItemDelete_btn.setBackgroundResource(R.drawable.ic_delete);
                    recyclerViewHolder.listItemEdit_btn.setBackgroundResource(R.drawable.ic_edit_pencil);

                }
                recyclerViewHolder.listItem_cb.setText(object_listItems.get(position).listItem);
                if (object_listItems.get(position).item_type != null) {
                    recyclerViewHolder.listItemType_xtv.setText("(" + object_listItems.get(position).item_type + ")");
                }
                recyclerViewHolder.quantityFrag_tv.setText(object_listItems.get(position).quantity);
                if (object_listItems.get(position).quantity_unit != null) {
                    recyclerViewHolder.quantityUnitFrag_tv.setText("-" + object_listItems.get(position).quantity_unit);
                }

                if(object_listItems.get(position).total_cost!=null) {
                    Log.d("karan_t", object_listItems.get(position).total_cost);
                    recyclerViewHolder.totalCostFrag_tv.setText(object_listItems.get(position).total_cost);
                }
                    break;
            case "UserLists":
                recyclerViewHolder.userList_tv.setText(object_usersLists.get(position).usersList);
                break;
        }

    }


    public List<Object_Profile> getProfileItemList() {
        return object_profiles;
    }

    public List<Object_ListItem> getListItemList() {
        return object_listItems;
    }

    public List<Object_UsersList> getUserListsList() {
        return object_usersLists;
    }

    @Override
    public int getItemCount() {
        int size = 0;
        switch (className) {
            case "Profiles":
                size = object_profiles.size();
                break;
            case "List_Fragment":
                size = object_listItems.size();
                break;
            case "UserLists":
                size = object_usersLists.size();
                break;
        }
        return size;
    }

    public void remove(int position) {

        switch (className) {
            case "Profiles":
                object_profiles.remove(position);
                notifyItemRemoved(position);
                break;

            case "List_Fragment":
                object_listItems.remove(position);
                notifyItemRemoved(position);
                break;

            case "UserLists":
                object_usersLists.remove(position);
                notifyItemRemoved(position);
                break;

        }

    }

    public void addPofile(int position, Object_Profile k_object_profile) {

        object_profiles.add(position, k_object_profile);
        notifyItemInserted(position);
    }

    public void addListItem(int position, Object_ListItem k_object_listItem) {

        object_listItems.add(position, k_object_listItem);
        notifyItemInserted(position);
    }

    public void addUserList(int position, Object_UsersList k_object_usersList) {

        object_usersLists.add(position, k_object_usersList);
        notifyItemInserted(position);
    }

    public Object_Profile getProfile(int position) {
        Object_Profile object_profile = getProfileItemList().get(position);
        return object_profile;
    }


    public Object_UsersList getUserList(int position) {
        Object_UsersList Object_UsersList = getUserListsList().get(position);
        return Object_UsersList;
    }

    public String getListItemName(int position) {

        Object_ListItem object_listItem = getListItemList().get(position);

        return object_listItem.getListItem();
    }

    public Object_ListItem getListItem(int position) {

        Object_ListItem object_listItem = getListItemList().get(position);

        return object_listItem;
    }

    public String getUserListName(int position) {

        Object_UsersList object_usersList = getUserListsList().get(position);

        return object_usersList.getUsersListName();
    }

}