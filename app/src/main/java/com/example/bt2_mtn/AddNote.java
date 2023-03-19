package com.example.bt2_mtn;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Calendar;

public class AddNote extends AppCompatActivity {
    Toolbar toolbar;
    EditText noteTitle,noteDetails;
    Calendar c;
    String todaysDate;
    String currentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_note);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Thêm ghi chú mới");

        noteDetails = findViewById(R.id.noteDetails);
        noteTitle = findViewById(R.id.noteTitle);

        noteTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() != 0){
                    getSupportActionBar().setTitle(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        /** THIẾT LẬP NGÀY GIỜ HIỆN TẠI THEO THIẾT BỊ **/
        c = Calendar.getInstance();
        todaysDate = c.get(Calendar.DAY_OF_MONTH) + "/" + (c.get(Calendar.MONTH)+1)+"/"+(c.get(Calendar.YEAR));
        Log.d("DATE", "Date: "+todaysDate);
        currentTime = pad(c.get(Calendar.HOUR))+":"+pad(c.get(Calendar.MINUTE));
        Log.d("TIME", "Time: "+currentTime);
    }

    private String pad(int time) {
        if(time < 10)
            return "0"+time;
        return String.valueOf(time);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.save){
            if(noteTitle.getText().length() != 0){
                Note note = new Note(noteTitle.getText().toString(),noteDetails.getText().toString(),todaysDate,currentTime);
                Database sDB = new Database(this);
                long id = sDB.addNote(note);
                Note check = sDB.getNote(id);
                Log.d("inserted", "Note: "+ id + " -> Title:" + check.getTitle()+" Date: "+ check.getDate());
                onBackPressed();

                Toast.makeText(this, "Đã thêm ghi chú", Toast.LENGTH_SHORT).show();
            }else {
                noteTitle.setError("Tiêu đề không được để trống");
            }

        }
        else if(item.getItemId() == R.id.delete){
            Toast.makeText(this, "Hủy bỏ", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}