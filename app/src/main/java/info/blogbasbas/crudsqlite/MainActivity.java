package info.blogbasbas.crudsqlite;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.LinkedList;

import info.blogbasbas.crudsqlite.adapter.NotaAdapter;
import info.blogbasbas.crudsqlite.db.NotaHelper;
import info.blogbasbas.crudsqlite.entity.Nota;

public class MainActivity extends AppCompatActivity {


    RecyclerView rvNotes;
    ProgressBar progressBar;
    FloatingActionButton fabAdd;

    private LinkedList<Nota> list;
    private NotaAdapter adapter;
    private NotaHelper notaHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvNotes = (RecyclerView)findViewById(R.id.rv_notes);
        rvNotes.setLayoutManager(new LinearLayoutManager(this));
        rvNotes.setHasFixedSize(true);

        progressBar = (ProgressBar)findViewById(R.id.progressbar);

        notaHelper = new NotaHelper(this);
        notaHelper.open();
        list = new LinkedList<>();

        adapter = new NotaAdapter(this);
        adapter.setListNota(list);
        rvNotes.setAdapter(adapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PendataanActivity.class);
                startActivityForResult(intent, PendataanActivity.REQUEST_ADD);
            }
        });

        new LoadNotaAsyntask().execute();
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

    private class LoadNotaAsyntask extends AsyncTask<Void,Void, ArrayList<Nota>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressBar.setVisibility(View.VISIBLE);
            if (list.size()>0){
                list.clear();
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Nota> notas) {
            super.onPostExecute(notas);
            progressBar.setVisibility(View.GONE);
            list.addAll(notas);
            adapter.setListNota(list);
            adapter.notifyDataSetChanged();
            if (list.size() == 0){
                showSnackbar("data tidak ada coy");
            }
        }

        @Override
        protected ArrayList<Nota> doInBackground(Void... voids) {
            return notaHelper.query();
        }
    }

    private void showSnackbar(String s) {
        Snackbar.make(rvNotes,s,Snackbar.LENGTH_LONG).show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PendataanActivity.REQUEST_ADD){
            if (resultCode == PendataanActivity.RESULT_ADD){

                new LoadNotaAsyntask().execute();
                showSnackbar("Satu Item Berhasil Di tambah Coy");
                rvNotes.getLayoutManager().smoothScrollToPosition(rvNotes, new RecyclerView.State(), 0);
            }


        }

        else if (requestCode == PendataanActivity.REQUEST_UPDATE){
            if (resultCode == PendataanActivity.RESULT_UPDATE){
                new LoadNotaAsyntask().execute();
                int posisi = data.getIntExtra(PendataanActivity.EXTRA_POSITION,0);
                rvNotes.getLayoutManager().smoothScrollToPosition(rvNotes, new RecyclerView.State(), posisi);


            }

            else if (resultCode == PendataanActivity.RESULT_DELETE){
                int posisi = data.getIntExtra(PendataanActivity.EXTRA_POSITION, 0);
                list.remove(posisi);
                adapter.setListNota(list);
                adapter.notifyDataSetChanged();
                showSnackbar("Satu Item Berhasil DI Hapus COy");
            }


        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (notaHelper!=null){
            notaHelper.close();
        }
    }
}
