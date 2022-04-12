package edu.qc.seclass.glm.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import edu.qc.seclass.glm.model.ItemType;

import static androidx.room.ForeignKey.CASCADE;

@Entity(indices = {@Index(value = {"typeName", "itemName"}) },
        foreignKeys = @ForeignKey(entity = ItemType.class,
                parentColumns = "typeName",
                childColumns = "typeName",
                onDelete = ForeignKey.RESTRICT,
                onUpdate = CASCADE))
public class Item implements Comparable<Item>{
    @PrimaryKey(autoGenerate = true)
    private int itemId;


    @NonNull
    private String itemName;

    @NonNull
    private String typeName;

    @NonNull
    private String metric;


    public Item(){}

/*
    @Ignore
    public Item(String typeName, String itemName){
        this.typeName = typeName;
        this.itemName = itemName;
    } */

    @Ignore
    public Item(String typeName, String itemName, String metric){
        this.typeName = typeName;
        this.itemName = itemName;
        this.metric = metric;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    @NonNull
    public String getItemName() {
        return itemName;
    }

    public void setItemName(@NonNull String itemName) {
        this.itemName = itemName;
    }

    @NonNull
    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(@NonNull String typeName) {
        this.typeName = typeName;
    }

    @NonNull
    public String getMetric() {
        return metric;
    }

    public void setMetric(@NonNull String metric) {
        this.metric = metric;
    }

    @NonNull
    @Override
    public String toString() {
        return this.typeName+", "+this.itemName+", "+this.metric;
    }

    @Override
    public int compareTo(Item item) {
        int value;
        if(this.typeName.equals(item.getTypeName())){
            value = this.itemName.compareToIgnoreCase(item.getItemName());
        }
        else{
            value = this.typeName.compareToIgnoreCase(item.getTypeName());
        }
        return value;
    }
}
