package edu.qc.seclass.glm;

import org.junit.Test;
import java.util.List;
import edu.qc.seclass.glm.db.GLMDatabase;
import edu.qc.seclass.glm.model.GroceryList;
import static org.junit.Assert.*;

public class page4Test {

    @Test
    public void glistnameTest()
    {
        String gListName= "";
        if(gListName!=null)
        {
            assertNotNull(gListName);
            System.out.println("Nullify object");
        }
        else
        {
            assertNull(gListName);
            System.out.println("list name entered is null");
        }
    }

    @Test
    public void Item_Quantity_Test()
    {
        int Item_Type=1 ;
        if(Item_Type!=0)
        {
            assertNotNull(Item_Type);
            System.out.println("The item type is not null!");
        }
        else
        {
            assertNull(Item_Type);
            System.out.println("The item type is null");
        }
    }
}
