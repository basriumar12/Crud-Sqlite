package info.blogbasbas.crudsqlite.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import info.blogbasbas.crudsqlite.entity.Nota;

import static android.provider.BaseColumns._ID;
import static info.blogbasbas.crudsqlite.db.DatabaseContract.NotaColum.DESKRIPSI;
import static info.blogbasbas.crudsqlite.db.DatabaseContract.NotaColum.TANGGAL;
import static info.blogbasbas.crudsqlite.db.DatabaseContract.NotaColum.TITLE;
import static info.blogbasbas.crudsqlite.db.DatabaseContract.TABLE_NOTA;

/**
 * Created by User on 21/01/2018.
 */

public class NotaHelper {
    private static String DATABASE_TABEL = TABLE_NOTA;
    private Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public NotaHelper(Context context) {
        this.context = context;
    }
    public NotaHelper open() throws SQLException{
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }
    public void close (){
        databaseHelper.close();
    }
    public ArrayList<Nota> query (){
        ArrayList<Nota> arrayList = new ArrayList<Nota>();
        Cursor cursor = database.query(DATABASE_TABEL,null,
                null,
                null,
                null,
                null,
                _ID +" DESC",
                null);
        cursor.moveToNext();
        Nota nota;
        if (cursor.getCount()>0){
            do {
                nota = new Nota();
                nota.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                nota.setJudul(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                nota.setDeskripsi(cursor.getString(cursor.getColumnIndexOrThrow(DESKRIPSI)));
                nota.setTanggal(cursor.getString(cursor.getColumnIndexOrThrow(TANGGAL)));

                arrayList.add(nota);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());


        }
        cursor.close();
        return arrayList;


    }
    //insert data
    public long insert (Nota nota){
        ContentValues args = new ContentValues();
        args.put(TITLE, nota.getJudul());
        args.put(DESKRIPSI, nota.getDeskripsi());
        args.put(TANGGAL, nota.getTanggal());

        return database.insert(DATABASE_TABEL, null, args);
    }

    //update data
    public long update (Nota nota){
        ContentValues args = new ContentValues();
        args.put(TITLE, nota.getJudul());
        args.put(DESKRIPSI, nota.getDeskripsi());
        args.put(TANGGAL, nota.getTanggal());
        return database.update(DATABASE_TABEL, args, _ID +" = '"+nota.getId()+"'", null);
    }
    //delete
    public int delete (int id){
        return database.delete(TABLE_NOTA, _ID +" = '"+id+"'", null);
    }



}
