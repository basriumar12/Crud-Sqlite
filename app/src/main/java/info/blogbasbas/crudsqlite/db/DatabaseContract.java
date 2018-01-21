package info.blogbasbas.crudsqlite.db;

import android.provider.BaseColumns;

/**
 * Created by User on 21/01/2018.
 */

public class DatabaseContract {

    public static String TABLE_NOTA = "note";

    public static final class NotaColum implements BaseColumns {
        public static String TITLE = "title";
        public static String DESKRIPSI = "deskripsi";
        public static String TANGGAL = "tanggal";

    }

}
