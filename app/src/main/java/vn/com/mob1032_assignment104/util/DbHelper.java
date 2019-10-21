package vn.com.mob1032_assignment104.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import vn.com.mob1032_assignment104.DAO.ClassDAO;
import vn.com.mob1032_assignment104.DAO.ClassDAO;
import vn.com.mob1032_assignment104.DAO.StudentDAO;

public class DbHelper extends SQLiteOpenHelper {
    public static final int DB_VERSION = 4;
    public static final String DB_NAME = "FpolyManage";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(StudentDAO.CREATE_TABLE_STUDENTS);
        db.execSQL(ClassDAO.CREATE_TABLE_CLASS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
        reset(db);
        db.execSQL(StudentDAO.CREATE_TABLE_STUDENTS);
        db.execSQL(ClassDAO.CREATE_TABLE_CLASS);
    }

    public void reset(SQLiteDatabase db){
        db.execSQL(ClassDAO.DROP_TABLE_CLASS);
        db.execSQL(ClassDAO.CREATE_TABLE_CLASS);
    }
}
