package com.example.bt2_mtn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity{
    RecyclerView recyclerView;
    Adapter adapter;
    TextView noItemText;
    Database Database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Khai báo toolbar kèm thư viện
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        noItemText = findViewById(R.id.noItemText);
        Database = new Database(this);
        List<Note> allNotes = Database.getAllNotes();
        recyclerView = findViewById(R.id.allNotesList);

        // Ẩn hiện trạng thái "có ghi chú chưa ?"
        if(allNotes.isEmpty()){
            noItemText.setVisibility(View.VISIBLE);
        }
        else {
            noItemText.setVisibility(View.GONE);
            displayList(allNotes);
        }
    }

    private void displayList(List<Note> allNotes) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this,allNotes);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.add){
            Toast.makeText(this, "Thêm ghi chú mới", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this,AddNote.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Note> getAllNotes = Database.getAllNotes();
        if(getAllNotes.isEmpty()){
            noItemText.setVisibility(View.VISIBLE);
        }else {
            noItemText.setVisibility(View.GONE);
            displayList(getAllNotes);
        }
    }
}