package edu.qc.seclass.glm.Activity;

import edu.qc.seclass.glm.R;
import edu.qc.seclass.glm.db.GLMDatabase;
import edu.qc.seclass.glm.model.GroceryList;
import edu.qc.seclass.glm.Adapter.GroceryListAdapter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import edu.qc.seclass.glm.model.GroceryListItems;
import edu.qc.seclass.glm.model.Item;
import edu.qc.seclass.glm.model.ItemType;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.*;

public class MainActivity extends AppCompatActivity {

    private GLMDatabase db;
    private GroceryListAdapter adapter;
    private FloatingActionButton createListBtn;
    private ListView groceryListView;
    private ImageButton deleteBtn;
    private List<GroceryList> groceryListArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page1);

        db = GLMDatabase.getInstance(this);

        //check to make sure that its only added once
        if(db.itemTypeDao().getAllItemTypeNames().size()<=0){
            String[] itemTypesHolder = {"Beverages", "Bakery", "Canned Goods", "Dairy",
                    "Dry Goods", "Frozen Foods", "Meat", "Produce", "Paper Goods","Spices" , "Other"};
            ItemType[] itemTypes = new ItemType[itemTypesHolder.length];
            for(int i = 0; i< itemTypesHolder.length; i++){
                itemTypes[i]= new ItemType(itemTypesHolder[i]);
            }
            db.itemTypeDao().insertAllItemTypes(itemTypes);

            //list of items to be added to the DB on initialization
            String[][] itemHolder = {{"Coffee", "Ltr"}, {"Orange Juice", "Ltr"}, {"Coca-Cola", "Ltr"},
                    {"Rye Bread", "Loaf"}, {"Whole Wheat Bread", "Loaf"}, {"Bagels", "Pkgs"},
                    {"Corn", "Can"}, {"Beans", "Can"}, {"Peas", "Can"},
                    {"Milk", "Ltr"},{"Cheese", "oz"}, {"Yogurt", "oz"},
                    {"Cereal", "Box"}, {"Flour","lb"}, {"Pasta","oz" },
                    {"Pizza", "Box"}, {"Waffles", "Box"}, {"Vegetable","oz" },
                    {"Beef", "lb"}, {"Poultry", "lb"},{"Lamb", "lb"},
                    {"Apple", "Qty"}, {"Pepper", "Qty"},{"Orange", "Qty"},
                    {"Plates", "Pkgs"},{"Utensils", "Pkgs"},{"Paper Napkins", "Pkgs"},
                    {"Pepper", "gm"},{"Garlic","gm"},{"Onion", "gm"},
                    {"Hand Soap", "CTR"}, {"Batteries", "Qty"}, {"Shampoo", "CTR"}};

            Item[] itemsToLoad = new Item[itemTypes.length * 3];
            int typeCounter = -1;

            for(int i = 0; i< itemHolder.length; i++){
                if(i % 3 ==0) typeCounter++;
                itemsToLoad[i]= new Item(itemTypesHolder[typeCounter], itemHolder[i][0], itemHolder[i][1]);
            }

            db.itemDao().insertAllItems(itemsToLoad);

            db.groceryListDao().insertAllGLs(new GroceryList("My First Grocery List"));
            int tempGLId = db.groceryListDao().returnGLId("My First Grocery List");
            int tempItemId = db.itemDao().getItemId("Coffee");
            int tempItemId2 = db.itemDao().getItemId("Beans");
            db.groceryListItemsDao().insertAllGLIs(new GroceryListItems(tempGLId, tempItemId2, 7));
            db.groceryListItemsDao().insertAllGLIs(new GroceryListItems(tempGLId, tempItemId, 4));

        }



        groceryListView = findViewById(R.id.groceryListView);
        this.groceryListArray = db.groceryListDao().returnAllGLs();
        adapter = new GroceryListAdapter(this, groceryListArray, db);
        groceryListView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        GLMDatabase.destroyInstance();
        super.onDestroy();
    }

    public void openDialog(View view) {

        final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.createnewlistdialog, null);

        final EditText text_input = (EditText) mView.findViewById(R.id.text_input);
        Button btn_back = (Button) mView.findViewById(R.id.btn_back);
        Button btn_create = (Button) mView.findViewById(R.id.btn_create);

        alert.setView(mView);

        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //create new list
                GroceryList newListName = new GroceryList(text_input.getText().toString());
                //check that the DB does not contain a list of this name- if it does- display a toast- already have a list under this name
                if(db.groceryListDao().contains(text_input.getText().toString()) != 1){
                    MainActivity.this.groceryListArray.add(newListName);
                    Collections.sort(groceryListArray);
                    db.groceryListDao().insertAllGLs(newListName);
                    adapter.notifyDataSetChanged();
                    alertDialog.dismiss();
                    Toast.makeText(view.getContext(), "New list created "+newListName.getGListName(), Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(view.getContext(), "You already have a list with the name "+ newListName.getGListName(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        alertDialog.show();
    }
}
