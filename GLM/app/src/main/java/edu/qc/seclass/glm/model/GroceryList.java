package edu.qc.seclass.glm.model;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = @Index(value = "gListName", unique = true))
public class GroceryList implements Comparable<GroceryList>{
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo()
    private int gListId;

    @NonNull
    private String gListName;

    public GroceryList(){}

    @Ignore
    public GroceryList(String gListName){
        this.gListName = gListName;
    }

    public int getGListId() {
        return this.gListId;
    }

    public void setGListId(int gListId) {
        this.gListId = gListId;
    }

    @NonNull
    public String getGListName() {
        return this.gListName;
    }

    public void setGListName(@NonNull String gListName) {
        this.gListName = gListName;
    }

    @NonNull
    @Override
    public String toString() {
        return this.gListId+", "+this.gListName;
    }

    @Override
    public int compareTo(GroceryList groceryList) {
        return this.gListName.compareToIgnoreCase(groceryList.getGListName());
    }

    //make sort function (to be called by arrayList)
}
