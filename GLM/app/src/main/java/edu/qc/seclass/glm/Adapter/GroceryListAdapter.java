package edu.qc.seclass.glm.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.appcompat.app.AlertDialog;
import edu.qc.seclass.glm.Activity.page2;
import edu.qc.seclass.glm.R;
import edu.qc.seclass.glm.db.GLMDatabase;
import edu.qc.seclass.glm.model.GroceryList;

public class GroceryListAdapter extends ArrayAdapter<GroceryList> {
    private List<GroceryList> lists;
    GLMDatabase db;

    public GroceryListAdapter(Context context, List<GroceryList> lists, GLMDatabase db) {
        super(context, 0, lists);
        this.lists = lists;
        this.db = db;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final GroceryList item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.page1_grocerylistview,parent,false);
        }

        final TextView listText = (TextView)convertView.findViewById(R.id.listName);
        ImageButton deleteBtn = (ImageButton) convertView.findViewById(R.id.imageButton);

        listText.setText(item.getGListName());

        listText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPage2(view, position, item);
                System.out.println(item.getGListName());
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(view, position, item);

            }
        });
        return convertView;
    }

    public void openPage2(View view, final int position, final GroceryList gl) {
        Intent intent = new Intent(view.getContext(), page2.class);
        intent.putExtra("gListName", gl.getGListName());
        intent.putExtra("gListId", gl.getGListId());
        view.getContext().startActivity(intent);
    }

    public void openDialog(View view, final int position, final GroceryList gl) {
        final AlertDialog dialog = new AlertDialog.Builder(view.getContext())
                .setTitle("Confirm Delete")
                .setMessage("Do you want to delete this List ?")
                .setPositiveButton("Yes", null)
                .setNegativeButton("Cancel", null)
                .show();

        Button positiveBtn = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.groceryListDao().deleteLists(gl);
                GroceryListAdapter.this.lists.remove(position);
                Toast.makeText(view.getContext(), "List deleted", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                GroceryListAdapter.this.notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getCount() {
        return lists.size();
    }
}

