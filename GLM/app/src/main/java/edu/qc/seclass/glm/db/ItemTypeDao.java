package edu.qc.seclass.glm.db;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import edu.qc.seclass.glm.model.Item;
import edu.qc.seclass.glm.model.ItemType;

@Dao
public interface ItemTypeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllItemTypes(List<ItemType> itemTypes);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllItemTypes(ItemType... itemTypes);

    @Query("SELECT * FROM ItemType ORDER BY typeName")
    List<ItemType> getAllItemTypeNames();

   /*CRUD methods to implement
    * void deleteItemType(String name);//make sure to delete all objects that are under that itemType
    * void updateItemType(String oldName, String newName);
    */
}
