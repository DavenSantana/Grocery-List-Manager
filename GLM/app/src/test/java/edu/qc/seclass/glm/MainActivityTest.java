package edu.qc.seclass.glm;

import org.junit.Test;
import java.util.List;
import edu.qc.seclass.glm.db.GLMDatabase;
import edu.qc.seclass.glm.model.GroceryList;
import static org.junit.Assert.*;

public class MainActivityTest {
    private GLMDatabase db;
    private List<GroceryList> groceryListArray;

    @Test
    public void check_Status_db()
    {
        if(db==null)
        {
            assertNull(db);
            System.out.println("database is empty!");
        }
        else
        if(db!=null)
        {
            assertNotNull(db);
            System.out.println("database is not empty!");
        }
    }
    @Test
    public void GrocceryArrayList_Test()
    {
        if(groceryListArray!=null)
        {
            assertNotNull(groceryListArray);
            System.out.println("groccery list found");
        }
        else
        {
            assertNull(groceryListArray);
            System.out.println("groccery list empty!");
        }
    }
}
