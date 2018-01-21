package info.blogbasbas.crudsqlite.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by User on 21/01/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    //nama db
    public static String DATABASE_NAME = "notadb";
    //veri db
    private static final int DATABASE_VERSION = 1;
    //query create db
    private static final String SQL_CREATE_TABLE_NOTA = String.format("CREATE TABLE %s"
                                +"(%s INTEGER PRIMARY KEY AUTOINCREMENT,"+
                                    "%s TEXT NOT NULL, "+
                                    "%s TEXT NOT NULL, " +
                                    "%s TEXT NOT NULL)",
                                DatabaseContract.TABLE_NOTA,
                                DatabaseContract.NotaColum._ID,
                                DatabaseContract.NotaColum.TITLE,
                                DatabaseContract.NotaColum.DESKRIPSI,
                                DatabaseContract.NotaColum.TANGGAL);
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_NOTA);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS"+DatabaseContract.TABLE_NOTA);
        onCreate(db);
    }
}
