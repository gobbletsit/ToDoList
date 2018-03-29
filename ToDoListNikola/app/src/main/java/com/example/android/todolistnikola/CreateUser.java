package com.example.android.todolistnikola;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateUser extends AppCompatActivity {

    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText emailEditText;
    private Button saveButton;



    private String nameString;
    private String lastNameString;
    private String emailString;
    private User newUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        firstNameEditText = findViewById(R.id.name_edit_text);
        lastNameEditText = findViewById(R.id.surname_edit_text);
        emailEditText = findViewById(R.id.email_edit_text);
        saveButton = findViewById(R.id.save_button);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if (firstNameEditText.getText().toString() != null){
                    nameString = firstNameEditText.getText().toString();
                }

                if (lastNameEditText.getText().toString() != null){
                    lastNameString = firstNameEditText.getText().toString();
                }

                if (emailEditText.getText().toString() != null){
                    emailString = firstNameEditText.getText().toString();
                }

                newUser = new User(nameString, lastNameString, emailString);

                InsertUserTask task = new InsertUserTask();
                task.execute(newUser);

            }
        });

    }

    private class InsertUserTask extends AsyncTask<User, Void, Void>{
        @Override
        protected Void doInBackground(User... users) {
            AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "production.db").build();
            db.userDao().insertSingleUser(users[0]);
            return null;
        }
    }
}
