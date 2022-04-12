package edu.qc.seclass.glm.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class ItemType implements Comparable<ItemType> {
    @PrimaryKey
    @NonNull
    private String typeName;

    public ItemType(){}

    @Ignore
    public ItemType(String typeName){
        this.typeName = typeName;
    }


    public String getTypeName() {
        return this.typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @NonNull
    @Override
    public String toString() {
        return this.typeName;
    }

    @Override
    public int compareTo(ItemType itemType) {
        return this.typeName.compareToIgnoreCase(itemType.getTypeName());
    }
}
