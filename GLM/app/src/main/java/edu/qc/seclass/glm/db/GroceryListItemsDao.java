package edu.qc.seclass.glm.db;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import edu.qc.seclass.glm.model.GroceryListItems;
import edu.qc.seclass.glm.model.Item;

/*

 */


@Dao
public interface GroceryListItemsDao {
    @Query("INSERT INTO GroceryListItems(gListId, itemId, quantity) VALUES(:gListId, :itemId, :quantity)")
    void insertGLIsByParts(int gListId, int itemId, int quantity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllGLIs(List<GroceryListItems> items);

    //insert one or more Grocery List Items
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllGLIs(GroceryListItems... groceryListItems);

    //will update to the new GroceryListItem
    @Update
    void updateGLItem(GroceryListItems groceryListItems);

   /* @Query()
    void updateGLItemQuery()*/

    @Query("SELECT * FROM GroceryListItems WHERE gListId = :gListId ")
    List<GroceryListItems> getGLItems(int gListId);

    //delete one or more groceryList items
    @Delete
    void deleteGLItem(GroceryListItems... groceryListItems);

    @Query("SELECT 1 FROM GroceryListItems WHERE gListId =:gListId AND itemId =:itemId")
    int contains(int gListId, int itemId);

}
