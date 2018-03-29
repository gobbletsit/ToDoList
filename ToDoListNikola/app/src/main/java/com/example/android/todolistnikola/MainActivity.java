package com.example.android.todolistnikola;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.database.DatabaseErrorHandler;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<User> korisnici;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        GetAllUsersTask task = new GetAllUsersTask();
        task.execute();

        adapter = new CustomAdapter();
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, CreateUser.class);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {


        @Override
        public CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_row, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(CustomAdapter.ViewHolder holder, int position) {

            User currentUser = korisnici.get(position);

            holder.tvFirstName.setText(currentUser.getFirstName());
            holder.tvLastName.setText(currentUser.getLastName());
            holder.tvEmail.setText(currentUser.getEmail());
        }

        @Override
        public int getItemCount() {
            return korisnici.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView tvFirstName;
            public TextView tvLastName;
            public TextView tvEmail;

            public ViewHolder(View itemView) {
                super(itemView);

                tvFirstName = itemView.findViewById(R.id.tv_first_name);
                tvLastName = itemView.findViewById(R.id.tv_last_name);
                tvEmail = itemView.findViewById(R.id.tv_email_name);

            }
        }

    }

    public class GetAllUsersTask extends AsyncTask<Void, Void, List<User>>{

        private List<User> list;

        @Override
        protected List<User> doInBackground(Void... voids) {
            AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "production.db")
                    .build();

            try {

                list = db.userDao().getListOfUsers();


            } catch (Exception e){
                e.printStackTrace();
            }

            return list;
        }

        @Override
        protected void onPostExecute(List<User> users) {
            korisnici = users;
        }
    }
}
