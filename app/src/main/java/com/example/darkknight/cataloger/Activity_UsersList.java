package com.example.darkknight.cataloger;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.OvershootInterpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Objects;

import jp.wasabeef.recyclerview.animators.BaseItemAnimator;
import jp.wasabeef.recyclerview.animators.FadeInAnimator;
import jp.wasabeef.recyclerview.animators.FadeInDownAnimator;
import jp.wasabeef.recyclerview.animators.FadeInLeftAnimator;
import jp.wasabeef.recyclerview.animators.FadeInUpAnimator;

public class Activity_UsersList extends AppCompatActivity{

    private List<Object_UsersList> object_usersLists;

    DataBase_Handler db_handler;
    String dbName = "test2";

    private RecyclerView rv_userLists;
    static String userListName;
    RecyclerViews_Adapter rv_Adapter;
    ImageButton addUserList_btn;
    EditText addUserList_et;
    SharedPreferences cataloger_data_prefs = null;
    static String listName;
    String current_list_name = "current_list_name";
    Dialog dialog_editUserList, dialog_delete;

    RelativeLayout text_rl;

    enum Type {
        FadeInLeft(new FadeInLeftAnimator(new OvershootInterpolator(1f)));

        private BaseItemAnimator mAnimator;

        Type(BaseItemAnimator animator) {
            mAnimator = animator;
        }

        public BaseItemAnimator getAnimator() {
            return mAnimator;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_activity__users_list);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        db_handler = new DataBase_Handler(this, dbName, null, 1);

        text_rl= (RelativeLayout)findViewById(R.id.text_xrl);

        addUserList_btn = (ImageButton) findViewById(R.id.addUserList_xbtn);

        rv_userLists = (RecyclerView) findViewById(R.id.rv_userLists);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv_userLists.setLayoutManager(llm);

        addUserList_et = (EditText) findViewById(R.id.addUserList_xet);


        object_usersLists = db_handler.getUsersLists();

        cataloger_data_prefs = getSharedPreferences("cataloger_data_prefs3", MODE_PRIVATE);
        listName = cataloger_data_prefs.getString(current_list_name, null);

        Intent k = getIntent();
        String Stop = k.getStringExtra("STOP");

        Log.d("karan", "listName:" + listName);
        if (listName != null) {
            if (!Objects.equals(Stop, "STOP")) {
                Intent i = new Intent(getApplicationContext(), Activity_ListItems.class);
                startActivity(i);
            }
        }

        rv_Adapter = new RecyclerViews_Adapter(object_usersLists, "UserLists");

        rv_userLists.setItemAnimator(Activity_UsersList.Type.FadeInLeft.getAnimator());
        rv_userLists.getItemAnimator().setAddDuration(500);
        rv_userLists.getItemAnimator().setRemoveDuration(500);

        rv_userLists.setAdapter(rv_Adapter);



        addUserList_et.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                  addUserList();
                    return true;
                }
                return false;
            }
        });

        setStartText();

    }

    public void setStartText(){
        if(object_usersLists.size()>1){
            text_rl.setVisibility(View.INVISIBLE);
        }
        else{  text_rl.setVisibility(View.VISIBLE);}
    }


    public void onDeleteBtnClicked(View v) {
        RecyclerView.ViewHolder holder = rv_userLists.findContainingViewHolder(v);
        final int positon = holder.getAdapterPosition();
        final String userListName = rv_Adapter.getUserListName(positon);
        dialog_delete = new Dialog(Activity_UsersList.this);
        dialog_delete.setContentView(R.layout.dialog_delete_stuff);

        TextView delete_title, delete_xtv;
        Button deletet_xbtn, cancel_xbtn;

        delete_title = (TextView) dialog_delete.findViewById(R.id.deleteStuff_xtitle);
        delete_xtv = (TextView) dialog_delete.findViewById(R.id.deleteStuff_xtv);
        deletet_xbtn = (Button) dialog_delete.findViewById(R.id.deleteStuff_xbtn);
        cancel_xbtn = (Button) dialog_delete.findViewById(R.id.cancelDeleteStuff_xbtn);

        delete_title.setText("Delete My List");
        delete_xtv.setText("Are you sure your want to delete \"" + userListName + "\"?");

        deletet_xbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cataloger_data_prefs = v.getContext().getSharedPreferences("cataloger_data_prefs3", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = cataloger_data_prefs.edit();
                editor.remove(current_list_name);
                editor.commit();
                db_handler.deleteUsersList(userListName);
                rv_Adapter.remove(positon);
                dialog_delete.dismiss();
                setStartText();


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

    public void addUserList(){
        String NO_ITEM = "no_item", ITEM_FOUND = "item_found";

        userListName = addUserList_et.getText().toString();
        if (!Objects.equals(userListName, "")) {
            if (Objects.equals(db_handler.checkUserListName(userListName), NO_ITEM)) {
                Object_UsersList object_usersList = new Object_UsersList(userListName);
                rv_Adapter.addUserList(object_usersLists.size(), object_usersList);
                db_handler.addUsersList(object_usersList);
                addUserList_et.setText("");
                setStartText();
            } else {
                Toast.makeText(getApplicationContext(), "UserList already exists. ", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(getApplicationContext(), "Please enter your List Name", Toast.LENGTH_LONG).show();
        }
    }

    public void onAddUserListClicked(View v) {
        addUserList();
    }


    public void onEditUserListClicked(View v) {
        final String NO_ITEM = "no_item", ITEM_FOUND = "item_found";


        RecyclerView.ViewHolder holder = rv_userLists.findContainingViewHolder(v);
        final int positon = holder.getAdapterPosition();
        final Object_UsersList[] object_usersLists = {rv_Adapter.getUserList(positon)};
        final String old_userListName = object_usersLists[0].getUsersListName();


        final String[] new_userListName = {""};

        dialog_editUserList = new Dialog(Activity_UsersList.this);
        dialog_editUserList.setContentView(R.layout.dialog_edit_userlist);
        Button editUserList_btn, cancelEditUserList_btn;
        final EditText userListName_xet = (EditText) dialog_editUserList.findViewById(R.id.editUserList_xet);
        editUserList_btn = (Button) dialog_editUserList.findViewById(R.id.editUserList_xbtn);
        cancelEditUserList_btn = (Button) dialog_editUserList.findViewById(R.id.cancelEditUserList_xbtn);


        userListName_xet.setText(old_userListName);
        userListName_xet.setSelection(userListName_xet.getText().length());


        editUserList_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new_userListName[0] = userListName_xet.getText().toString();
                if (Objects.equals(db_handler.checkUserListName(new_userListName[0]), NO_ITEM)) {
                    db_handler.editUserList(old_userListName, new_userListName[0]);
                    object_usersLists[0].setUsersList(new_userListName[0]);
                    rv_Adapter.notifyItemChanged(positon);
                  //  Toast.makeText(Activity_UsersList.this, "You clicked yes button", Toast.LENGTH_LONG).show();
                    dialog_editUserList.dismiss();
                } else {
                    Toast.makeText(getApplicationContext(), "UserList already exists. ", Toast.LENGTH_LONG).show();

                }
            }
        });

        cancelEditUserList_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(Activity_UsersList.this, "You clicked no button", Toast.LENGTH_LONG).show();
                dialog_editUserList.dismiss();

            }
        });

        dialog_editUserList.show();
    }

    public void openUserList(View v) {
        RecyclerView.ViewHolder holder = rv_userLists.findContainingViewHolder(v);
        int position = holder.getAdapterPosition();

        userListName = rv_Adapter.getUserListName(position);
        Log.d("karan", "userListName=" + userListName);
        cataloger_data_prefs = v.getContext().getSharedPreferences("cataloger_data_prefs3", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = cataloger_data_prefs.edit();
        editor.putString(current_list_name, userListName);
        editor.commit();
        Intent i = new Intent(getApplicationContext(), Activity_ListItems.class);
        i.putExtra("userListName", userListName);

        startActivity(i);
    }







}
