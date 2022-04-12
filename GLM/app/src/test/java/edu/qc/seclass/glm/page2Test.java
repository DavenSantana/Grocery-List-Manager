package edu.qc.seclass.glm;

import android.widget.ListView;
import org.junit.Test;
import java.util.List;
import edu.qc.seclass.glm.db.GLMDatabase;
import edu.qc.seclass.glm.model.GroceryList;
import static org.junit.Assert.*;

public class page2Test {
    private ListView groceryListView;
    @Test
    public void ArrayListItemsCheck() 
    {
        groceryListView = null;
        ListView expectedList = null ;
        if(groceryListView==null)
        {
            assertEquals(groceryListView,expectedList);
            System.out.println("The lists are equal");
        }
        else
        if(groceryListView!=null)
        {
            assertNotEquals(groceryListView,expectedList);
        }

    }

}
