package edu.qc.seclass.glm.model;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import edu.qc.seclass.glm.db.GLMDatabase;
import edu.qc.seclass.glm.db.GroceryListItemsDao;

import static androidx.room.ForeignKey.CASCADE;
import static androidx.room.ForeignKey.RESTRICT;

@Entity(foreignKeys = {
        @ForeignKey(entity = GroceryList.class,
                parentColumns = "gListId",
                childColumns = "gListId",
                onDelete = CASCADE,
                onUpdate = CASCADE),
        @ForeignKey(entity = Item.class,
                parentColumns = "itemId",
                childColumns = "itemId",
                onDelete = RESTRICT,
                onUpdate = CASCADE)},
        indices = {@Index(value = "gListId"), @Index(value = "itemId")} ,
        primaryKeys =  {"gListId", "itemId"} )
public class GroceryListItems implements Comparable<GroceryListItems> {

  //  @PrimaryKey
    @NonNull
    public int gListId;

    @NonNull
    public int itemId;

    @ColumnInfo(defaultValue = "1")
    public int quantity;
    //check constraint
    @ColumnInfo(defaultValue = "0")
    public int check;

    //not included in the DB
    @Ignore
    private Item item;

    public GroceryListItems(){}

    @Ignore
    public GroceryListItems(int gListId, int itemId){
        this.gListId = gListId;
        this.itemId = itemId;
        this.quantity = 1;
    }

    @Ignore
    public GroceryListItems(int gListId, int itemId, int quantity){
        this.gListId = gListId;
        this.itemId = itemId;
        this.quantity = quantity;
    }


    @Ignore
    public GroceryListItems(Item item, int gListId, int quantity){
        this.item = item;
        this.gListId = gListId;
        this.itemId = item.getItemId();
        this.quantity = quantity;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getGListId() {
        return gListId;
    }

  /*  public void setGListId(int gListId) {
        this.gListId = gListId;
    }*/

    public int getItemId() {
        return itemId;
    }

    /*
    public void setItem(Item item) {
        this.item = item;
    }*/

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Item getItem() {
        return item;
    }

    public int isCheck() {
        return check;
    }

    public void setCheck(int check) {
        this.check = check;
    }

    @NonNull
    @Override
    public String toString() {
        try{
            return   this.gListId+", "+this.item+", "+this.quantity+", "+this.check;
        }
        catch (Exception e){
            return this.gListId+", "+this.itemId+", "+this.quantity+", "+this.check;
        }
    }

    public static List<GroceryListItems> setItemObjectsInGLFromItemId(GLMDatabase db, List<GroceryListItems> myList){
        for(GroceryListItems item: myList){
            if(item.getItem() == null ){
                item.setItem(db.itemDao().getItemById(item.getItemId()));
                System.out.println("Adding Item to id: "+item.getItem());
            }
        }
        return myList;
    }

    @Override
    public int compareTo(GroceryListItems groceryListItems) {
        int value;
        if(this.item.getTypeName().equals(groceryListItems.getItem().getTypeName())){
            value = this.item.getItemName().compareToIgnoreCase(groceryListItems.getItem().getItemName());
        }
        else{
            value = this.item.getTypeName().compareToIgnoreCase(groceryListItems.getItem().getTypeName());
        }
        return value;
    }
}
