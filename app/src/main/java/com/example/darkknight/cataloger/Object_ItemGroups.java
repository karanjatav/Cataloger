package com.example.darkknight.cataloger;

import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;

import java.util.List;

/**
 * Created by DARKKNIGHT on 2/12/2017.
 */

public class Object_ItemGroups implements ParentListItem {

    private List<Object_Items> object_Items;
    private String itemGroupName;
    private String profileName;


    @Override
    public List<Object_Items> getChildItemList() {
        return object_Items;

    }



    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }

    public Object_ItemGroups(List<Object_Items> object_Items, String itemGroupName, String profileName) {
        this.object_Items = object_Items;
        this.itemGroupName = itemGroupName;
        this.profileName = profileName;
    }

    public String getItemGroupName() {
        return itemGroupName;
    }

    public List<Object_Items> getObject_Items() {
        return object_Items;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setItemGroupName(String itemGroupName) {
        this.itemGroupName = itemGroupName;
    }


}
