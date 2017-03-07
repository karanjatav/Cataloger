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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import android.view.WindowManager;
import android.view.animation.OvershootInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.github.amlcurran.showcaseview.OnShowcaseEventListener;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.SimpleShowcaseEventListener;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

import java.util.List;
import java.util.Objects;

import jp.wasabeef.recyclerview.animators.BaseItemAnimator;
import jp.wasabeef.recyclerview.animators.FadeInAnimator;
import jp.wasabeef.recyclerview.animators.FadeInDownAnimator;
import jp.wasabeef.recyclerview.animators.FadeInLeftAnimator;
import jp.wasabeef.recyclerview.animators.FadeInUpAnimator;

public class Profiles extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    SharedPreferences cataloger_data_prefs = null;

    private List<Object_Profile> object_profiles;
    private RecyclerView profiles_rv;
    ImageButton addProfile_btn;
    RecyclerViews_Adapter rvAdapter;
    DataBase_Handler dbHandler;
    static String db_name;
    String list_name;
    private List<Object_ListItem> object_listItems;
    TextView profiles_tb_tv, listNameProfiles_tv;
    Spinner course_spn;
    Dialog dialog_delete, dialog_addProfile;

    static String profile_type;
    Spinner profileType_spn;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_profiles);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        cataloger_data_prefs = getSharedPreferences("cataloger_data_prefs3", MODE_PRIVATE);

        Intent i = getIntent();
        list_name = i.getStringExtra("list_name");
        firstTimeUser = i.getBooleanExtra("firstTimeUser", true);

        listNameProfiles_tv = (TextView) findViewById(R.id.listNameProfiles_xtv);
        listNameProfiles_tv.setText(list_name);


        profiles_rv = (RecyclerView) findViewById(R.id.profiles_xrv);

        addProfile_btn = (ImageButton) findViewById(R.id.addProfile_xbtn);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        profiles_rv.setLayoutManager(llm);


        db_name = "test2";


        dbHandler = new DataBase_Handler(this, db_name, null, 1);
        object_profiles = dbHandler.getProfiles();


        rvAdapter = new RecyclerViews_Adapter(object_profiles, "Profiles");

        profiles_rv.setItemAnimator(Type.FadeInLeft.getAnimator());
        profiles_rv.getItemAnimator().setAddDuration(500);
        profiles_rv.getItemAnimator().setRemoveDuration(500);

        profiles_rv.setAdapter(rvAdapter);

        object_listItems = dbHandler.getListItems(list_name);
        profiles_tb_tv = (TextView) findViewById(R.id.profiles_tb_xtv);

        updateTextView(object_listItems.size());

        if (firstTimeUser) {
            ViewTarget target = new ViewTarget(R.id.addProfile_xbtn, this);
            sv = new ShowcaseView.Builder(this)
                    .withMaterialShowcase()
                    .setTarget(target)
                    .setContentTitle("Create your custom Catalog by touching  '+' Sign")
                    .setContentText("")
                    .setStyle(R.style.CustomShowcaseThemeKaran2)
                    .setOnClickListener(this)
                    .build();
        }


    }

    @Override
    public void onClick(View v) {

        ViewTarget target = new ViewTarget(R.id.shoppingBag, this);
        sv.hide();
        sv = new ShowcaseView.Builder(this)
                .withMaterialShowcase()
                .setTarget(target)
                .setContentTitle("You can always check on your items any time by touching on the 'Shopping Bag'.")
                .setContentText("")
                .setStyle(R.style.CustomShowcaseThemeKaran)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences.Editor editor = cataloger_data_prefs.edit();
                        editor.putBoolean("firstTimeUser", false);
                        editor.commit();
                        sv.hide();
                    }
                })
                .build();

    }


    public void updateTextView(int list_size) {
        profiles_tb_tv.setText(String.valueOf(list_size - 1));
    }


    public void onAddProfileBtnClicked(View v) {
        dialog_addProfile = new Dialog(Profiles.this);
        dialog_addProfile.setContentView(R.layout.add_profile_dialog);
        final String NO_ITEM = "no_item", ITEM_FOUND = "item_found";


        final EditText profileName_xet;
        Button addProflie_btn, cancelProfile_btn;
        addProflie_btn = (Button) dialog_addProfile.findViewById(R.id.Proflie_xbtn);
        cancelProfile_btn = (Button) dialog_addProfile.findViewById(R.id.cancelProfile_xbtn);

        profileName_xet = (EditText) dialog_addProfile.findViewById(R.id.profileName_xet);

        dialog_addProfile.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        profileType_spn = (Spinner) dialog_addProfile.findViewById(R.id.profileType_xspn);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.profileType, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assert profileType_spn != null;
        profileType_spn.setSelection(0, true);


        profileType_spn.setAdapter(adapter);
        profileType_spn.setOnItemSelectedListener(this);


        addProflie_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String profile_name = profileName_xet.getText().toString();
                if (!Objects.equals(profile_name, "")) {
                    if (Objects.equals(dbHandler.checkProfileName(profile_name), NO_ITEM)) {
                        Object_Profile object_profile = new Object_Profile(profile_name, profile_type);

                        rvAdapter.addPofile(object_profiles.size(), object_profile);
                        dbHandler.addProfiles(object_profile);
                        dialog_addProfile.dismiss();

                        // Toast.makeText(Profiles.this, "You clicked yes button", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(Profiles.this, "Profile Name Already Exist", Toast.LENGTH_LONG).show();

                    }
                } else {
                    Toast.makeText(Profiles.this, "Please enter a Profile Name", Toast.LENGTH_LONG).show();

                }
            }
        });

        cancelProfile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_addProfile.dismiss();

                //  Toast.makeText(Profiles.this, "You clicked no button", Toast.LENGTH_LONG).show();

            }
        });


        dialog_addProfile.show();
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        parent.getItemAtPosition(position).toString();

        switch (parent.getId()) {
            case R.id.profileType_xspn:

                profile_type = parent.getItemAtPosition(position).toString();
                //  course = item;
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void deleteButtonClicked(View v) {
        RecyclerView.ViewHolder holder = profiles_rv.findContainingViewHolder(v);
        final int positon = holder.getAdapterPosition();
        Object_Profile object_profile = rvAdapter.getProfile(positon);
        final String profile_name = object_profile.getProfile_name();


        dialog_delete = new Dialog(Profiles.this);
        dialog_delete.setContentView(R.layout.dialog_delete_stuff);

        TextView delete_title, delete_xtv;
        Button deletet_xbtn, cancel_xbtn;

        delete_title = (TextView) dialog_delete.findViewById(R.id.deleteStuff_xtitle);
        delete_xtv = (TextView) dialog_delete.findViewById(R.id.deleteStuff_xtv);
        deletet_xbtn = (Button) dialog_delete.findViewById(R.id.deleteStuff_xbtn);
        cancel_xbtn = (Button) dialog_delete.findViewById(R.id.cancelDeleteStuff_xbtn);

        delete_title.setText("Delete My List");
        delete_xtv.setText("Are you sure your want to delete \"" + profile_name + "\"?");

        deletet_xbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dbHandler.deleteProfile(profile_name);
                rvAdapter.remove(positon);
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

    public void editProfileClicked(View v) {
        final String NO_ITEM = "no_item", ITEM_FOUND = "item_found";

        dialog_addProfile = new Dialog(Profiles.this);
        dialog_addProfile.setContentView(R.layout.add_profile_dialog);


        dialog_addProfile.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        RecyclerView.ViewHolder holder = profiles_rv.findContainingViewHolder(v);
        final int positon = holder.getAdapterPosition();
        final Object_Profile[] object_profile = {rvAdapter.getProfile(positon)};
        final String old_profileName = object_profile[0].getProfile_name();
        String old_type = object_profile[0].getCategory();

        final String[] new_profileName = {""};


        final EditText profileName_xet;
        TextView title_xtv;

        Button addProflie_btn, cancelProfile_btn;

        title_xtv = (TextView) dialog_addProfile.findViewById(R.id.title_xtv);
        addProflie_btn = (Button) dialog_addProfile.findViewById(R.id.Proflie_xbtn);
        cancelProfile_btn = (Button) dialog_addProfile.findViewById(R.id.cancelProfile_xbtn);
        profileName_xet = (EditText) dialog_addProfile.findViewById(R.id.profileName_xet);

        title_xtv.setText("Edit Catalog");
        addProflie_btn.setText("Change");
        profileName_xet.setText(old_profileName);
        profileName_xet.setSelection(profileName_xet.getText().length());


        profileType_spn = (Spinner) dialog_addProfile.findViewById(R.id.profileType_xspn);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.profileType, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assert profileType_spn != null;
        profileType_spn.setSelection(0, true);


        profileType_spn.setAdapter(adapter);
        profileType_spn.setOnItemSelectedListener(this);

        addProflie_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new_profileName[0] = profileName_xet.getText().toString();

                if (!Objects.equals(new_profileName[0], "")) {
                    if (Objects.equals(old_profileName, dbHandler.checkProfileName(new_profileName[0])) || Objects.equals(dbHandler.checkProfileName(new_profileName[0]), NO_ITEM)) {
                        Object_Profile object_profile1 = new Object_Profile(new_profileName[0], profile_type);

                        dbHandler.editProfile(object_profile[0], object_profile1);
                        object_profile[0].setProfile_name(new_profileName[0]);
                        object_profile[0].setCategory(profile_type);
                        rvAdapter.notifyItemChanged(positon);

                        dialog_addProfile.dismiss();

                    } else {
                        Toast.makeText(Profiles.this, "Profile Name Already Exists", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(Profiles.this, "Please Enter A Profile Name", Toast.LENGTH_LONG).show();

                }
            }
        });

        cancelProfile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_addProfile.dismiss();


            }
        });


        dialog_addProfile.show();
    }

    public void openActivity(View v) {
        RecyclerView.ViewHolder holder = profiles_rv.findContainingViewHolder(v);
        int positon = holder.getAdapterPosition();
        Object_Profile object_profile = rvAdapter.getProfile(positon);
        String profile_name = object_profile.getProfile_name();

        Log.d("karan", "profile_name:" + profile_name);
        Intent i = new Intent(getApplicationContext(), ItemGroups.class);
        i.putExtra("databaseName", db_name);
        i.putExtra("profile_name", profile_name);
        i.putExtra("list_name", list_name);
        startActivity(i);
    }

    public void doneBtnClicked(View v) {
        Intent i = new Intent(Profiles.this, Activity_ListItems.class);

        startActivity(i);

    }

}
