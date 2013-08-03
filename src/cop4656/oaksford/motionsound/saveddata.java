package cop4656.oaksford.motionsound;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class saveddata extends ContentProvider {

	public final static String DBNAME = "TheDatabase";
    public final static String TABLE_PLAYERS = "players";
    public final static String COLUMN_TEXT = "name";
    public final static String COLUMN_SCORE = "score";

    public static final String AUTHORITY = "cop4656.oaksford.motionsound.provider";
    public static final Uri CONTENT_URI = Uri.parse("content://cop4656.oaksford.motionsound.provider/" + TABLE_PLAYERS);

    private static UriMatcher sUriMatcher;

    @SuppressWarnings("unused")
    private MainDatabaseHelper mOpenHelper;

    private static final String SQL_CREATE_MAIN = "CREATE TABLE " +
            TABLE_PLAYERS +                       // Table's name
            "(" +                           // The columns in the table
            " _ID INTEGER PRIMARY KEY, " +
            COLUMN_TEXT +
            " TEXT, " +
            COLUMN_SCORE +
            " INTEGER,)";

    @Override
    public boolean onCreate() {

		mOpenHelper = new MainDatabaseHelper(getContext());
		return((mOpenHelper == null) ? false : true);
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
    	String text = values.getAsString(COLUMN_TEXT).trim();
        
        if(text.equals(""))
           return null;

        long id = mOpenHelper.getWritableDatabase().insert(TABLE_PLAYERS, null, values);

        return Uri.withAppendedPath(CONTENT_URI, "" + id);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        return mOpenHelper.getWritableDatabase().update(TABLE_PLAYERS, values, selection, selectionArgs);

    }

    @Override
    public int delete(Uri uri, String whereClause, String[] whereArgs) {

        return mOpenHelper.getWritableDatabase().delete(TABLE_PLAYERS, whereClause, whereArgs);

    }

    @Override
    public Cursor query(Uri table, String[] columns, String selection, String[] args, String orderBy) {

        return mOpenHelper.getReadableDatabase().query(TABLE_PLAYERS, columns, selection, args, null, null, orderBy);
    }

    @Override
    public String getType(Uri arg0) {
        return null;
    }

    protected static final class MainDatabaseHelper extends SQLiteOpenHelper {

        MainDatabaseHelper(Context context) {
            super(context, DBNAME, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(SQL_CREATE_MAIN);
        }

        @Override
        public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        }
    }

}
