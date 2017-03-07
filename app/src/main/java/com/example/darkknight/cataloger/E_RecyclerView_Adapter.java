package com.example.darkknight.cataloger;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.bignerdranch.expandablerecyclerview.Model.ParentWrapper;

import java.util.List;
import java.util.Objects;

/**
 * Created by DARKKNIGHT on 2/12/2017.
 */

public class E_RecyclerView_Adapter extends ExpandableRecyclerAdapter<Holder_ItemGroup, Holder_Item> {

    private LayoutInflater mInflator;

    public E_RecyclerView_Adapter(Context context, @NonNull List<? extends ParentListItem> parentItemList) {
        super(parentItemList);

        mInflator = LayoutInflater.from(context);
    }


    @Override
    public Holder_ItemGroup onCreateParentViewHolder(ViewGroup itemGroupViewGroup) {
        View itemGroupView = mInflator.inflate(R.layout.e_recyclerviewlayout_itemgroup, itemGroupViewGroup, false);
        return new Holder_ItemGroup(itemGroupView);
    }

    @Override
    public Holder_Item onCreateChildViewHolder(ViewGroup itemViewGroup) {
        View itemView = mInflator.inflate(R.layout.e_recyclerviewlayout_items, itemViewGroup, false);
        return new Holder_Item(itemView);
    }

    @Override
    public void onBindParentViewHolder(Holder_ItemGroup holder_itemGroup, int position, ParentListItem parentListItem) {
        Object_ItemGroups object_itemGroups = (Object_ItemGroups) parentListItem;
        holder_itemGroup.attachParent(object_itemGroups);


    }

    @Override
    public void onBindChildViewHolder(Holder_Item holder_item, int position, Object childListItem) {
        Object_Items object_items = (Object_Items) childListItem;
        holder_item.attachChild(object_items);
    }

    @Override
    public Object getListItem(int position) {
        return super.getListItem(position);
    }


    public int getParentPosition(int position) {
        int parentPosition = 0;
        boolean parentFound = false;
        for (int i = position; i > 0; i--) {
            if (mItemList.get(i).getClass().equals(ParentWrapper.class)) {
                if (!parentFound) {
                    parentFound = true;
                }
                parentPosition++;
            }
        }
        return parentPosition;
    }

    public int getChildPostion(int position) {
        int childPosition = 0;
        boolean parentFound = false;
        for (int i = position; i > 0; i--) {
            if (mItemList.get(i).getClass().equals(ParentWrapper.class)) {
                if (!parentFound) {
                    parentFound = true;

                }
            } else if (!parentFound) {
                childPosition++;
            }
        }
        childPosition--;
        return childPosition;
    }

    public int getChildPostionByName(String name) {
        int childPosition = 0;
       // Log.d("karan55","mItemList==="+String.valueOf(mItemList.size()));
      int returnValue = 0;
        for (int i = mItemList.size(); i > 0 ; i--) {
            Object_Items object_items = getItem(i - 1);
            String name2 = object_items.getItemName();
            if(Objects.equals(name, name2)){
                Log.d("karan55", name + "---" + name2 + "----position---" + (i - 1));
                returnValue = i-1;
            }
        }
        return returnValue;
    }

    public void deleteParent(int position) {
        int parentPosition = 0;
        boolean parentFound = false;
        for (int i = position; i > 0; i--) {
            if (mItemList.get(i).getClass().equals(ParentWrapper.class)) {
                if (!parentFound) {
                    parentFound = true;
                }
                parentPosition++;
            }
        }
        getParentItemList().remove(parentPosition);
        notifyParentItemRemoved(parentPosition);

    }


    public String getItemGroupName(int position) {
        int itemGroupPosition;
        itemGroupPosition = getParentPosition(position);
        Object_ItemGroups p = (Object_ItemGroups) getParentItemList().get(itemGroupPosition);
        String itemGroupName = p.getItemGroupName();
        return itemGroupName;
    }

    public Object_ItemGroups getItemGroup(int position) {
        int itemGroupPosition;
        itemGroupPosition = getParentPosition(position);
        Object_ItemGroups object_itemGroups = (Object_ItemGroups) getParentItemList().get(itemGroupPosition);

        return object_itemGroups;
    }


    public Object_Items getItem(int position) {
        int itemPosition;
        int itemGroupPosition;
        itemGroupPosition = getParentPosition(position);
        itemPosition = getChildPostion(position);
        Object_ItemGroups p = (Object_ItemGroups) getParentItemList().get(itemGroupPosition);
        List<Object_Items> object_items = p.getChildItemList();

        Object_Items object_items1 = new Object_Items("no value", "no value", "no value", "no value", "no value", "no value","no value");

        try {
            object_items1 = object_items.get(itemPosition);

            return object_items1;
        } catch (IndexOutOfBoundsException e) {
            return object_items1;
        }
    }

    public List<Object_Items> getItemsofParent(int position) {

        int itemGroupPosition;
        itemGroupPosition = getParentPosition(position);
        Object_ItemGroups p = (Object_ItemGroups) getParentItemList().get(itemGroupPosition);
        return p.getChildItemList();
    }


    public void addItem(int position, Object_Items object_items) {
        int parentPosition;
        parentPosition = getParentPosition(position);
        Object_ItemGroups p = (Object_ItemGroups) getParentItemList().get(parentPosition);

        List<Object_Items> object_items1 = p.getChildItemList();
        object_items1.add(object_items);
        notifyChildItemInserted(parentPosition, object_items1.size() - 1);

    }



    Object[] removeChildItem(int position) {
        int itemPosition;
        int itemGroupPosition;
        itemGroupPosition = getParentPosition(position);
        itemPosition = getChildPostion(position);
        Object_ItemGroups p = (Object_ItemGroups) getParentItemList().get(itemGroupPosition);
        List<Object_Items> object_items = p.getChildItemList();
        object_items.remove(itemPosition);

        notifyChildItemRemoved(itemGroupPosition, itemPosition);
        Object[] o = {p, Integer.valueOf(itemGroupPosition)};
        return o;
    }

    public void editItemGroupName(String newItemGroupName, int position) {
        int itemGroupPosition = getParentPosition(position);
        Object_ItemGroups p = (Object_ItemGroups) getParentItemList().get(itemGroupPosition);
        p.setItemGroupName(newItemGroupName);
        notifyParentItemChanged(itemGroupPosition);
    }


}
