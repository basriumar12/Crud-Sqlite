package info.blogbasbas.crudsqlite;

import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import info.blogbasbas.crudsqlite.db.NotaHelper;
import info.blogbasbas.crudsqlite.entity.Nota;

public class PendataanActivity extends AppCompatActivity implements View.OnClickListener {


    EditText edtTitle, edtDescription;
    Button btnSubmit;

    public static String EXTRA_NOTE = "extra_note";
    public static String EXTRA_POSITION = "extra_position";

    private boolean isEdit = false;
    public static int REQUEST_ADD = 100;
    public static int RESULT_ADD = 101;
    public static int REQUEST_UPDATE = 200;
    public static int RESULT_UPDATE = 201;
    public static int RESULT_DELETE = 301;

    private Nota nota;
    private int posisi;
    private NotaHelper notaHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pendataan);


        edtTitle = (EditText)findViewById(R.id.edt_title);
        edtDescription = (EditText)findViewById(R.id.edt_description);
        btnSubmit = (Button)findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(this);

        notaHelper = new NotaHelper(this);
        notaHelper.open();

        nota = getIntent().getParcelableExtra(EXTRA_NOTE);
        if (nota != null){
            posisi = getIntent().getIntExtra(EXTRA_POSITION,0);
            isEdit = true;

        }


        String actionBarTitel = null;
        String btnTitle = null;
        if (isEdit){
            actionBarTitel="ubah";
            btnTitle="update";
            edtTitle.setText(nota.getJudul());
            edtDescription.setText(nota.getDeskripsi());

        }else {
            actionBarTitel="tambah";
            btnTitle="Simpan";
        }

        getSupportActionBar().setTitle(actionBarTitel);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnSubmit.setText(btnTitle);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_submit){
            String judul = edtTitle.getText().toString().trim();
            String deskirpsi = edtDescription.getText().toString().trim();
            boolean isempty = false;

            if (TextUtils.isEmpty(judul)){
                isempty = true;
                edtTitle.setError("Judul tidak bisa Kosong bro");
            }

            if (TextUtils.isEmpty(deskirpsi)){
                isempty = true;
                edtDescription.setError("Deskripsi tidak bisa Kosong bro");
            }

            if (!isempty){
                 Nota newNota = new Nota();
                newNota.setJudul(judul);
                newNota.setDeskripsi(deskirpsi);

                Intent intent = new Intent();
                //jika merupakan edit setresutlnya UPDATE dan jika bukan maka set Resultnya ADD

                if (isEdit){
                    newNota.setTanggal(nota.getTanggal());
                    newNota.setId(nota.getId());
                    notaHelper.update(newNota);
                    intent.putExtra(EXTRA_POSITION, posisi);
                    setResult(RESULT_UPDATE, intent);
                    finish();
                } else {
                    newNota.setTanggal(getCurrentDate());
                    notaHelper.insert(newNota);
                    Snackbar.make(view, "Berhasil insert", Snackbar.LENGTH_LONG).show();

                    setResult(RESULT_ADD);
                    finish();

                }
            }

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isEdit){
            getMenuInflater().inflate(R.menu.menu_delete,menu);
        }

        return super.onCreateOptionsMenu(menu);

    }

        final int ALERT_DIALOG_DELETE = 130;
    final int ALERT_DIALOG_CLOSE = 180;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_delete:
                showAlertDialog(ALERT_DIALOG_DELETE);
                break;
            case android.R.id.home:
                showAlertDialog(ALERT_DIALOG_CLOSE);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showAlertDialog(int type) {
        final boolean isDialog = type == ALERT_DIALOG_CLOSE;
        String dialogTitle = null , dialogMessage = null;


        if (isDialog){
            dialogTitle ="batal";
            dialogMessage ="apakah anda ingin membatalakan perubahan form ?";
        } else {
            dialogMessage =" apakah anda yakin ingin menghapus item ini ?";
            dialogTitle= "hapus nota";
        }

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(dialogTitle);
        alertDialog
                    .setMessage(dialogMessage)
                    .setCancelable(false)
                    .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (isDialog){
                                finish();
                            }else {
                                notaHelper.delete(nota.getId());
                                Intent intent = new Intent();
                                intent.putExtra(EXTRA_POSITION, posisi);
                                setResult(RESULT_DELETE, intent);
                                finish();
                            }
                        }
                    })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();

                    }
                });
        alertDialog.show();



    }

    @Override
    public void onBackPressed() {
       showAlertDialog(ALERT_DIALOG_CLOSE);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (notaHelper!= null){
            notaHelper.close();
        }
    }

    private String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        return dateFormat.format(date);

    }
}
