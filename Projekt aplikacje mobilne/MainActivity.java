package com.example.myapplication;

import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText editTextHabit;
    Button buttonAdd, buttonDone;
    ListView listView;

    ArrayList<String> habits;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextHabit = findViewById(R.id.editTextHabit);
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonDone = findViewById(R.id.buttonDone);
        listView = findViewById(R.id.listView);

        habits = new ArrayList<>();

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_multiple_choice,
                habits);

        listView.setAdapter(adapter);

        buttonAdd.setOnClickListener(v -> {
            String text = editTextHabit.getText().toString().trim();

            if (text.isEmpty()) {
                Toast.makeText(MainActivity.this, "Wpisz nawyk", Toast.LENGTH_SHORT).show();
            } else {
                habits.add(text);
                adapter.notifyDataSetChanged();
                editTextHabit.setText("");
                Toast.makeText(MainActivity.this, "Dodano", Toast.LENGTH_SHORT).show();
            }
        });

        buttonDone.setOnClickListener(v -> {
            SparseBooleanArray checked = listView.getCheckedItemPositions();

            boolean cosZaznaczone = false;

            for (int i = habits.size() - 1; i >= 0; i--) {
                if (checked.get(i)) {
                    habits.remove(i);
                    cosZaznaczone = true;
                }
            }

            if (!cosZaznaczone) {
                Toast.makeText(MainActivity.this, "Zaznacz coś", Toast.LENGTH_SHORT).show();
            } else {
                adapter.notifyDataSetChanged();
                listView.clearChoices();
                Toast.makeText(MainActivity.this, "Usunięto zrealizowane", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
