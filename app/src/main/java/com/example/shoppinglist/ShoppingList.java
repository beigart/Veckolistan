package com.example.shoppinglist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ShoppingList extends AppCompatActivity {

    //RecyclerView
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    //ArrayList
    ArrayList<Item> listItems = new ArrayList<>();

    //Connections
    private EditText nameInput, amountInput;
    private Button addItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        loadData();
        buildRecyclerView();

        nameInput = findViewById(R.id.titleInput);
        amountInput = findViewById(R.id.amountInput);
        addItem = findViewById(R.id.addItemButton);


        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                closeKeyboard();
                validation();
                clear();
                saveData();
            }
        });


    }

    private void validation() {
        if (nameInput.length() == 0) {
            nameInput.setError("Skriv in en produkt");
        }

        else if (amountInput.length() == 0) {
            amountInput.setError("Skriv in ett nummer");
        }
        else
            listItems.add(new Item(nameInput.getText().toString(), amountInput.getText() + " st".toString()));
            mAdapter.notifyDataSetChanged();
            //Toast.makeText(ShoppingList.this, nameInput.getText().toString().toUpperCase() + " tillagt i inköpslista", Toast.LENGTH_SHORT).show();



    }

    //Tömmer inputs vid knapptryck
    public void clear () {
        nameInput.setText("");
        amountInput.setText("");
    }

    //Stänger ned keyboard vid knapptryck
    private void closeKeyboard() {
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    //Sparar obejkten genom sharedPreferences
    private void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(listItems);
        editor.putString("task list", json);
        editor.apply();
    }

    //Laddar array genom sharedPreferences för att spara array även om appen avslutas
    private void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<Item>>() {}.getType();
        listItems = gson.fromJson(json, type);
        if (listItems == null) {
            listItems = new ArrayList<>();
        }
    }


    //Bygger recyclerview
    private void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.itemHolder);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,true);
        mAdapter = new Adapter(listItems);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        new ItemTouchHelper(itemTochHelperCallback).attachToRecyclerView(mRecyclerView);
        //mRecyclerView.setNestedScrollingEnabled(false);
    }

    //Swipefunktion för att ta bort objekt från array
    ItemTouchHelper.SimpleCallback itemTochHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            listItems.remove(viewHolder.getAdapterPosition());
            mAdapter.notifyDataSetChanged();
            Toast.makeText(ShoppingList.this,    "Borttaget från inköpslista", Toast.LENGTH_SHORT).show();
        }
    };
}