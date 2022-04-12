package edu.qc.seclass.glm.Activity;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.room.Query;
import edu.qc.seclass.glm.R;
import edu.qc.seclass.glm.db.GLMDatabase;
import edu.qc.seclass.glm.model.GroceryListItems;
import edu.qc.seclass.glm.model.Item;
import edu.qc.seclass.glm.model.ItemType;

public class page4 extends AppCompatActivity {
    private int gListId;
    private GLMDatabase db;
    private String gListName;
    private Spinner itemTypesSpinner;
    private EditText searchForItemNameText;
    private Button searchButton;
    private ListView ItemNames;
    private EditText quantity;
    private Button addToGroceryListButton;
    private EditText newItemsName;
    private Button addToDbButton;
    private EditText metric;
    private static String nameToSearch = "";
    private static String itemType = "";
    private static String itemName = "";
    private static int itemQuantity;
    private static int itemId;
    private static String metricForNewItem;
    private static String newItemToAddToDataBasesName = "";
    private List<String> allItemsSimilarToName;
    private ArrayAdapter<String> arrayAdapterForListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page4);
        db = GLMDatabase.getInstance(this);

        Intent intent = getIntent();
        gListId = intent.getIntExtra("gListId", 0);
        gListName = intent.getStringExtra("gListName");

        Toolbar toolbar = findViewById(R.id.searchForItem);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Search For Item");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        itemTypesSpinner =  findViewById(R.id.itemTypesSpinner);
        searchForItemNameText = findViewById(R.id.searchForItemNameText);
        searchButton = findViewById(R.id.searchButton);
        ItemNames = findViewById(R.id.ItemNames);
        quantity = findViewById(R.id.quantity);
        addToGroceryListButton = findViewById(R.id.addToGroceryListButton);
        newItemsName = findViewById(R.id.newItemsName);
        addToDbButton = findViewById(R.id.addToDbButton);
        metric = findViewById(R.id.metric);

        allItemsSimilarToName = new ArrayList<String>();
        arrayAdapterForListView = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, allItemsSimilarToName);
        ItemNames.setAdapter(arrayAdapterForListView);

        searchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String temp = searchForItemNameText.getText().toString();
                if(temp.equals(""))
                    Toast.makeText(view.getContext(), " No name was entered to search", Toast.LENGTH_SHORT).show();
                else{
                    //returns any item starting with the same letter
                    nameToSearch = temp.substring(0, 1) + "%";

                    List<Item> tempSimilarItemList = db.itemDao().returnSimilarItems(nameToSearch);
                    allItemsSimilarToName.clear();
                    for(Item similarItem: tempSimilarItemList){
                        allItemsSimilarToName.add(similarItem.getItemName());
                    }
                    //Thoeretically should be sorted already- but just in case
                    Collections.sort(allItemsSimilarToName);
                    arrayAdapterForListView.notifyDataSetChanged();
                }
           }
        });

        ItemNames.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemNameSelected = (String) parent.getItemAtPosition(position);
                itemName = itemNameSelected;
                Toast.makeText(view.getContext(), itemName+" selected.", Toast.LENGTH_SHORT).show();
            }
        });

        addToGroceryListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp = quantity.getText().toString();
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


       //Code below handles if the item doesn't exist and person wants to add

        List<ItemType> itemTypes = new ArrayList<>();
        itemTypes = db.itemTypeDao().getAllItemTypeNames();
        ArrayAdapter<ItemType> arrayAdapterItemCategory = new ArrayAdapter<ItemType>(this, android.R.layout.simple_list_item_1, itemTypes);
        arrayAdapterItemCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itemTypesSpinner.setAdapter(arrayAdapterItemCategory);

        itemTypesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                String itemTypeSelected = parent.getItemAtPosition(position).toString();
                itemType = itemTypeSelected;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        addToDbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp = newItemsName.getText().toString();
                newItemToAddToDataBasesName = temp;
                String tempMetric = metric.getText().toString();
                metricForNewItem = tempMetric;
                try{
                    if(itemType.equals(""))
                        Toast.makeText(view.getContext(), "You did not choose an item type.", Toast.LENGTH_SHORT).show();
                    else if(newItemToAddToDataBasesName.equals(""))
                        Toast.makeText(view.getContext(), "You did not give the item a name.", Toast.LENGTH_SHORT).show();
                    else if(metricForNewItem.equals(""))
                        Toast.makeText(view.getContext(), "You did not give the item a metric.", Toast.LENGTH_SHORT).show();
                    else if(metricForNewItem.length()>4){
                        Toast.makeText(view.getContext(), "The metric name can be maximum 4 charachters.", Toast.LENGTH_SHORT).show();
                    }
                    else if(db.itemDao().contains(newItemToAddToDataBasesName) != 1){
                        db.itemDao().insertAllItems(new Item(itemType, newItemToAddToDataBasesName, metricForNewItem));
                        //get item id- and add that item to the groceryList!
                        itemId = db.itemDao().getItemId(newItemToAddToDataBasesName);
                        db.groceryListItemsDao().insertAllGLIs(new GroceryListItems(gListId, itemId));
                        Toast.makeText(view.getContext(), newItemToAddToDataBasesName+" was added to the your groceryList", Toast.LENGTH_SHORT).show();
                        //Add metricForNewItem to db here

                    }
                    else{
                        itemId = db.itemDao().getItemId(newItemToAddToDataBasesName);
                        db.groceryListItemsDao().insertAllGLIs(new GroceryListItems(gListId, itemId));
                        Toast.makeText(view.getContext(), newItemToAddToDataBasesName+" was added to the your groceryList", Toast.LENGTH_SHORT).show();
                    }
                }
               catch (SQLiteException e){
                    e.printStackTrace();
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