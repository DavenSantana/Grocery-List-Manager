package edu.qc.seclass.glm.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;
import androidx.appcompat.app.AlertDialog;
import edu.qc.seclass.glm.R;
import edu.qc.seclass.glm.db.GLMDatabase;
import edu.qc.seclass.glm.model.GroceryListItems;
import edu.qc.seclass.glm.model.Item;

public class ItemListAdapter extends ArrayAdapter<GroceryListItems> {

    private List<GroceryListItems> listOfItems;
    GLMDatabase db;

    public ItemListAdapter(Context context, List<GroceryListItems> listOfItems , GLMDatabase db) {
        super(context, 0, listOfItems);
        Collections.sort(listOfItems);
        this.listOfItems = listOfItems;
        this.db = db;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final GroceryListItems items = getItem(position);
        final int quantity = items.getQuantity();
        System.out.println(quantity);
        final Item item = items.getItem();
        System.out.println(item);
        String itemName = item.getItemName();
        String itemTypeName = item.getTypeName();
        int checked = items.isCheck();

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.page2_itemlistview,parent,false);
        }

        final TextView itemNameView = (TextView)convertView.findViewById(R.id.itemName);
        final TextView itemTypeNameView = (TextView)convertView.findViewById(R.id.itemTypeName);
        final CheckBox checkItem = convertView.findViewById(R.id.checkItem);
        final EditText quantityEditText = (EditText) convertView.findViewById(R.id.quantity);
        final ImageButton subtractQuantityBtn = (ImageButton) convertView.findViewById(R.id.subtractQuantity);
        final ImageButton addQuantityBtn = (ImageButton) convertView.findViewById(R.id.addQuantity);
        final ImageButton deleteItemBtn = (ImageButton) convertView.findViewById(R.id.deleteItemBtn);

        if(checked == 1){
            checkItem.setChecked(true);
        } else if(checked == 0){
            checkItem.setChecked(false);
        }

        itemNameView.setText(itemName);
        itemTypeNameView.setText(itemTypeName);
        quantityEditText.setText(Integer.toString(quantity) + " " + item.getMetric());
        quantityEditText.setSelection(quantityEditText.getText().length());



        quantityEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {

        @Override
        public void onFocusChange(View view, boolean hasFocus) {
            if(!hasFocus) {
                String crrQText = ((EditText)view).getText().toString();
                int newQuantity = quantity;
                try {
                    newQuantity = Integer.parseInt(crrQText);
                } catch (Exception e) {}
                items.setQuantity(newQuantity);
                db.groceryListItemsDao().updateGLItem(items);
                ItemListAdapter.this.notifyDataSetChanged();
            }
        }
    });

        subtractQuantityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quantity > 0) {
                    items.setQuantity(quantity - 1);
                    db.groceryListItemsDao().updateGLItem(items);
                    ItemListAdapter.this.notifyDataSetChanged();

                }
            }
        });

        addQuantityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                items.setQuantity(quantity+1);
                db.groceryListItemsDao().updateGLItem(items);
                ItemListAdapter.this.notifyDataSetChanged();
            }
        });

        deleteItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDeleteItemDialog(view, position,items);
            }
        });


        checkItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((CheckBox) view).isChecked();

                if(checked){
                    items.setCheck(1);
                }
                else{
                    items.setCheck(0);//might need to change to true and false
                }
                db.groceryListItemsDao().updateGLItem(items);
                ItemListAdapter.this.notifyDataSetChanged();

            }
        });

        return convertView;
    }

    public void openDeleteItemDialog(View view, final int position, final GroceryListItems deleteItem) {
        final AlertDialog dialog = new AlertDialog.Builder(view.getContext())
                .setTitle("Confirm Delete")
                .setMessage("Do you want to delete this Item ?")
                .setPositiveButton("Yes", null)
                .setNegativeButton("Cancel", null)
                .show();

        Button yesBtn = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ItemListAdapter.this.listOfItems.remove(position);
                db.groceryListItemsDao().deleteGLItem(deleteItem);
                Toast.makeText(view.getContext(), "Item deleted", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                ItemListAdapter.this.notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getCount() {
        return listOfItems.size();
    }
}
