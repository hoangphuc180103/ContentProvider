package com.example.contentprovider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null)
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }

    public void onClickAddDetails(View view) {
        ContentValues values = new ContentValues();
        values.put(UsersProvider.name, ((EditText) findViewById(R.id.txtName)).getText().toString());
        getContentResolver().insert(UsersProvider.CONTENT_URI, values);
        Toast.makeText(getBaseContext(), "New Record Inserted", Toast.LENGTH_LONG).show();
    }

    public void onClickShowDetails(View view) {
        TextView resultView = findViewById(R.id.res);
        Cursor cursor = getContentResolver().query(
                Uri.parse("content://com.example.contentprovider.UserProvider/users"),
                null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            StringBuilder strBuild = new StringBuilder();
            do {
                strBuild.append("\n")
                        .append(cursor.getString(cursor.getColumnIndex("id")))
                        .append(" - ")
                        .append(cursor.getString(cursor.getColumnIndex("name")));
            } while (cursor.moveToNext());
            resultView.setText(strBuild);
            cursor.close();
        } else {
            resultView.setText("No Records Found");
        }
    }
}
