package edu.qc.seclass.glm.db;

//import android.arch.persistence.room.RoomDatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import edu.qc.seclass.glm.model.GroceryList;
import edu.qc.seclass.glm.model.GroceryListItems;
import edu.qc.seclass.glm.model.Item;
import edu.qc.seclass.glm.model.ItemType;


@Database(entities =  {GroceryList.class, GroceryListItems.class, Item.class, ItemType.class}, version = 10, exportSchema = false)
public abstract class GLMDatabase extends RoomDatabase {
    //making the Database a singleton
    private static GLMDatabase INSTANCE = null;

    public static GLMDatabase getInstance(Context context){
        if (INSTANCE == null)
            synchronized (GLMDatabase.class)
            {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        GLMDatabase.class,
                        "GLM")
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build();
            }
        return INSTANCE;

    }

    public static void destroyInstance(){
        INSTANCE = null;
    }
    public abstract ItemTypeDao itemTypeDao();
    public abstract ItemDao itemDao();
  //  public abstract MetricDao metricDao();
    public abstract GroceryListDao groceryListDao();
    public abstract GroceryListItemsDao groceryListItemsDao();


}
