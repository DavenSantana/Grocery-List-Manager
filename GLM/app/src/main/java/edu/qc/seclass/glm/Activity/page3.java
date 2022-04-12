package edu.qc.seclass.glm.Activity;

import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import edu.qc.seclass.glm.Adapter.ItemListAdapter;
import edu.qc.seclass.glm.R;
import edu.qc.seclass.glm.db.GLMDatabase;
import edu.qc.seclass.glm.model.GroceryList;
import edu.qc.seclass.glm.model.GroceryListItems;
import edu.qc.seclass.glm.model.Item;
import edu.qc.seclass.glm.model.ItemType;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.*;

public class page3 extends AppCompatActivity {

    private TextView itemTypeText;
    private Spinner itemTypeList;
    private ListView itemsInItemTypesList;
    private Button addItemButton;
    private EditText editQuantityForItem;
    private static String itemType;
    private static String itemName = "";
    private static int itemId;
    private static int itemQuantity = 0;
    private int gListId;
    private String gListName;
    private GLMDatabase db;
    private List<String> allItemsOfThatType;
    private List<Item> tempHolder;
    private ArrayAdapter<String> arrayAdapterForListView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page3);
        db = GLMDatabase.getInstance(this);

        Intent intent = getIntent();
        gListId = intent.getIntExtra("gListId", 0);
        gListName = intent.getStringExtra("gListName");

        Toolbar toolbar = findViewById(R.id.addItemByTypeText);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Item By Type");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        itemTypeText = findViewById(R.id.itemTypeText);
        itemTypeList = findViewById(R.id.itemTypeList);
        itemsInItemTypesList = findViewById(R.id.itemsInItemTypesList);
        addItemButton = findViewById(R.id.addItemButton);
        editQuantityForItem = findViewById(R.id.editQuantityForItem);
        List<ItemType> itemTypes = new ArrayList<>();
        allItemsOfThatType = new ArrayList<String>();

        itemTypes = db.itemTypeDao().getAllItemTypeNames();

        ArrayAdapter<ItemType> arrayAdapterForSpinner = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemTypes);
        arrayAdapterForSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itemTypeList.setAdapter(arrayAdapterForSpinner);

        arrayAdapterForListView = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, allItemsOfThatType);
        itemsInItemTypesList.setAdapter(arrayAdapterForListView);

        itemTypeList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                String itemTypeSelected = parent.getItemAtPosition(position).toString();
                itemType = itemTypeSelected;

                //changes made below
                allItemsOfThatType.clear();
                tempHolder = db.itemDao().getItemsOfType(itemType);
                for(Item myItems: tempHolder){
                    allItemsOfThatType.add(myItems.getItemName());
                }
                Collections.sort(allItemsOfThatType);

                arrayAdapterForListView.notifyDataSetChanged();
                System.out.println("DataSet changed");
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });


        itemsInItemTypesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemNameSelected = (String) parent.getItemAtPosition(position);
                itemName = itemNameSelected;
                Toast.makeText(view.getContext(), itemName+" selected.", Toast.LENGTH_SHORT).show();
            }
        });

        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp = editQuantityForItem.getText().toString();
                if(!"".equals(temp))
                    itemQuantity = Integer.parseInt(temp);
                else itemQuantity = 0;
                try{
                    if(!itemName.equals("")){
                        System.out.println(itemName);
                        itemId = db.itemDao().getItemId(itemName);
                        db.groceryListItemsDao().insertGLIsByParts(gListId, itemId, itemQuantity);
                        Toast.makeText(view.getContext(), itemName+" was added to your grocery list.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(view.getContext(), "You did not choose an item to add.", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (SQLiteException e){
                    Toast.makeText(view.getContext(), gListName+" already contains "+ itemName, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                openPage2();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void openPage2(){
        Intent page2 = new Intent(this, page2.class);
        page2.putExtra("gListName", gListName);
        page2.putExtra("gListId", gListId);
        startActivity(page2);
    }
}