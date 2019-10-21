package vn.com.mob1032_assignment104.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import vn.com.mob1032_assignment104.DataModel.Classes;
import vn.com.mob1032_assignment104.DataModel.Classes;

public class ClassDAO {
    private SQLiteDatabase db;

    public ClassDAO(SQLiteDatabase db) {
        this.db = db;
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    public void setDb(SQLiteDatabase db) {
        this.db = db;
    }

    public ClassDAO(){}
    private static final String TABLE_CLASS = "classes";
    private static final String TABLE_CLASS_COLUMN_CLASSID = "class_id";
    private static final String TABLE_CLASS_COLUMN_CLASSNAME = "class_name";

    public static final String CREATE_TABLE_CLASS = "CREATE TABLE " + TABLE_CLASS + " (" +
            TABLE_CLASS_COLUMN_CLASSID + " VARCHAR PRIMARY KEY," +
            TABLE_CLASS_COLUMN_CLASSNAME + " VARCHAR NOT NULL" +
            ");";

    public static final String DROP_TABLE_CLASS = "DROP TABLE IF EXISTS " + TABLE_CLASS;

    public long addClass(Classes classNew){
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_CLASS_COLUMN_CLASSID, classNew.getClassID());
        contentValues.put(TABLE_CLASS_COLUMN_CLASSNAME, classNew.getClassName());
        return db.insert(TABLE_CLASS, null, contentValues);
    }

    public int deleteClass(String classID){
        String whereClause = TABLE_CLASS_COLUMN_CLASSID + "= ?";
        String[] whereArgs = {classID};
        return  db.delete(TABLE_CLASS, whereClause,whereArgs);
    }
    public Classes findClass(String classID){
        String[] columns ={TABLE_CLASS_COLUMN_CLASSID, TABLE_CLASS_COLUMN_CLASSNAME};
        String whereClause = TABLE_CLASS_COLUMN_CLASSID + "= ?";
        String[] whereArgs = {classID};
        Cursor cursor = db.query(TABLE_CLASS,columns,whereClause,whereArgs,null, null, null);
        while(cursor.moveToNext()){
            return new Classes(cursor.getString(cursor.getColumnIndex(TABLE_CLASS_COLUMN_CLASSID)),
                    cursor.getString(cursor.getColumnIndex(TABLE_CLASS_COLUMN_CLASSNAME)));
        }
        return null;
    }
    public List readAllClasses(){
        List classList = new ArrayList();
        String[] columns = {TABLE_CLASS_COLUMN_CLASSID, TABLE_CLASS_COLUMN_CLASSNAME};
        Cursor cursor = db.query(TABLE_CLASS, columns, null,null,null,null,null);
        while (cursor.moveToNext()){
           classList.add(new Classes(cursor.getString(cursor.getColumnIndex(TABLE_CLASS_COLUMN_CLASSID)),
                    cursor.getString(cursor.getColumnIndex(TABLE_CLASS_COLUMN_CLASSNAME))));
        }
        return classList;
    }
    public int updateClass(Classes classTarget){
        ContentValues contentValues = new ContentValues();
        String whereClause =  TABLE_CLASS_COLUMN_CLASSID + " = ?";
        String[] whereArgs = {classTarget.getClassID()+""};
        contentValues.put(TABLE_CLASS_COLUMN_CLASSID, classTarget.getClassID());
        contentValues.put(TABLE_CLASS_COLUMN_CLASSNAME, classTarget.getClassName());
        return db.update(TABLE_CLASS,contentValues,whereClause,whereArgs);
    }
    public List<String> getClassNames(){
        List<String> classNames = new ArrayList<>();
        String[] columns = {TABLE_CLASS_COLUMN_CLASSID, TABLE_CLASS_COLUMN_CLASSNAME};
        Cursor cursor = db.query(TABLE_CLASS, columns, null,null,null,null,null);
        while (cursor.moveToNext()){
            classNames.add(cursor.getString(cursor.getColumnIndex(TABLE_CLASS_COLUMN_CLASSID)));
        }
        return classNames;
    }
}
