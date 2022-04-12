package edu.qc.seclass.glm.db;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import edu.qc.seclass.glm.model.Item;
import edu.qc.seclass.glm.model.ItemType;

@Dao
public interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllItems(List<Item> items);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllItems(Item... items);

    @Query("SELECT * FROM Item ORDER BY typeName, itemName")
    List<Item> getAllItems();

    @Query("SELECT * FROM Item WHERE itemName LIKE :name ORDER BY itemName ")
    List<Item> getSimilarItems(String name);
    //pass in the first letter or first few letter of the word...
    //need to fix up

    @Query("SELECT * FROM Item WHERE itemId = :id")
    Item getItemById(int id);

    //returns all items of a specific type
    @Query("SELECT * FROM Item WHERE typeName = :typeName ORDER BY itemName")
    List<Item> getItemsOfType(String typeName);

    @Query("SELECT itemId FROM Item WHERE itemName = :name")
    int getItemId(String name);


    //input should be String temp = "first2%";
    @Query("SELECT * FROM Item WHERE itemName LIKE :first2letters")
    List<Item> returnSimilarItems(String first2letters);

    @Query("SELECT 1 from Item WHERE itemName = :itemName")
    int contains(String itemName);

    /*CRUD methods to implement if have time- not necessary for what we are doing...
    * void updateItem(String oldName, String newName);
    *  void deleteItem(String name);//need to delete all instances of that item
    * */

}
