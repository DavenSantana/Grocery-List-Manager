package edu.qc.seclass.glm.db;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import edu.qc.seclass.glm.model.GroceryList;
import edu.qc.seclass.glm.model.Item;

@Dao
public interface GroceryListDao {

    //inserting with a String
    @Query("INSERT INTO GroceryList(gListName) VALUES(:name)")
    void insertGLFromParts(String name);

    //inserting with a list of GroceryLists
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllGLs(List<GroceryList> groceryLists);

    //inserting with 1 or more groceryLists
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllGLs(GroceryList... groceryLists);



    @Query("SELECT gListId FROM GroceryList WHERE gListName = :name ORDER BY gListName")
    int returnGLId(String name);

    @Query("SELECT * FROM GroceryList ORDER BY gListName")
    List<GroceryList> returnAllGLs();

    //to delete 1 or more lists
    @Delete
    void deleteLists(GroceryList... groceryLists);

    @Query("DELETE FROM GroceryList WHERE gListName =:name")
    void deleteList(String name);

    @Update
    void renameGroceryList(GroceryList groceryList);

    @Query("SELECT 1 from GroceryList WHERE gListName = :gListName")
    int contains(String gListName);

    @Query("SELECT * FROM GroceryList WHERE gListName =:name")
    GroceryList getListFromName(String name);
}
