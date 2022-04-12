package edu.qc.seclass.glm;

import org.junit.Test;
import java.util.List;
import edu.qc.seclass.glm.db.GLMDatabase;
import edu.qc.seclass.glm.model.GroceryList;
import static org.junit.Assert.*;

public class page3Test {
    @Test
    public void OnitemSelect_Test()
    {
        String itemnameselected_actual = "";
        String itemnameselected_expected = "";

        if(itemnameselected_actual!=null)
        {
            assertEquals(itemnameselected_expected,itemnameselected_actual);
            System.out.println("equal lists");
        }
        else
        {
            assertNotEquals(itemnameselected_expected,itemnameselected_actual);
            System.out.println("both lists are not equal");
        }
    }

    @Test
    public void TestNulllist()
    {
        String itemnameselected = "";
        if(itemnameselected.equals(""))
        {
            assertEquals("", itemnameselected);
            System.out.println("The item name is empty!");
        }
        else
        {
            assertNotNull(itemnameselected);
            System.out.println("Item name selected is not null");
        }
    }
}
