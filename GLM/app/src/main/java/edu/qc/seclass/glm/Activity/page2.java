package edu.qc.seclass.glm.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import edu.qc.seclass.glm.Adapter.GroceryListAdapter;
import edu.qc.seclass.glm.Adapter.ItemListAdapter;
import edu.qc.seclass.glm.R;
import edu.qc.seclass.glm.db.GLMDatabase;
import edu.qc.seclass.glm.model.GroceryList;
import edu.qc.seclass.glm.model.GroceryListItems;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import edu.qc.seclass.glm.model.Item;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Array;
import java.util.*;

public class page2 extends AppCompatActivity {
    private ListView itemsListView;
    private ItemListAdapter adapter;
    private GLMDatabase db;
    private List<GroceryListItems> itemsListArray;
    private int gListId;
    private String gListName;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page2);
        db = GLMDatabase.getInstance(this);

        Intent intent = getIntent();
        gListId = intent.getIntExtra("gListId", 0);
        gListName = intent.getStringExtra("gListName");

        Toolbar toolbar =  findViewById(R.id.actionbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(gListName); //This should get the grocery list name that was clicked on
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.itemsListView = (ListView) findViewById(R.id.listViewForItems);

        this.itemsListArray =
                GroceryListItems.setItemObjectsInGLFromItemId(db, db.groceryListItemsDao().getGLItems(gListId));

        adapter = new ItemListAdapter(this, this.itemsListArray, db);
        this.itemsListView.setAdapter(adapter);

    }

    public void openPage3(View view){
        Intent page3 = new Intent(this, page3.class);
        page3.putExtra("gListName", gListName);
        page3.putExtra("gListId", gListId);
        startActivity(page3);
    }

    public void openPage4(View view){
        Intent page4 = new Intent(this, page4.class);
        page4.putExtra("gListName", gListName);
        page4.putExtra("gListId", gListId);
        startActivity(page4);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.rename_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.renameList:
                Toast.makeText(this, "rename list is selected", Toast.LENGTH_SHORT).show();
                openRenameDialog(item);
                return true;
            case R.id.clearAll:
                clearAllChecks(itemsListView);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void openRenameDialog(MenuItem item) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(page2.this);
        View mView = getLayoutInflater().inflate(R.layout.rename_grocery_list_dialog, null);

        final EditText text_input = (EditText) mView.findViewById(R.id.input_new_name);
        Button btn_rename_cancel = (Button) mView.findViewById(R.id.btn_rename_cancel);
        Button btn_rename_done = (Button) mView.findViewById(R.id.btn_rename_done);

        alert.setView(mView);

        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);

        btn_rename_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        btn_rename_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newListName = text_input.getText().toString();

                //TODO call DB to change the list name
                GroceryList tempGL = db.groceryListDao().getListFromName(gListName);
                tempGL.setGListName(newListName);
                db.groceryListDao().renameGroceryList(tempGL);

                page2.this.gListName = newListName;
                page2.this.getSupportActionBar().setTitle(gListName);


                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    public void clearAllChecks(View view){
        openClearAllDialog(view);
    }
    public void openClearAllDialog(View view){
        final AlertDialog dialog = new AlertDialog.Builder(view.getContext())
                .setMessage("Do you want to clear all marked checks ?")
                .setPositiveButton("Yes", null)
                .setNegativeButton("Cancel", null)
                .show();

        Button yesBtn = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            for (GroceryListItems items: page2.this.itemsListArray) {
                items.setCheck(0);
            }
            ((ItemListAdapter)itemsListView.getAdapter()).notifyDataSetChanged();
            dialog.dismiss();
            }
        });
    }
}