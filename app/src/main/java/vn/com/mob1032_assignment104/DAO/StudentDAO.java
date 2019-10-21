package vn.com.mob1032_assignment104.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import vn.com.mob1032_assignment104.DataModel.Students;

public class StudentDAO {

    private SQLiteDatabase db;
    public StudentDAO(SQLiteDatabase db){
        this.db = db;
    }
    public static final String TABLE_NAME = "students";
    public static final String COLUMN_NAME_ID = "StudentID";
    public static final String COLUMN_NAME_NAME = "StudentName";
    public static final String COLUMN_NAME_CLASS = "Class";

    public static final String CREATE_TABLE_STUDENTS = "CREATE TABLE " + TABLE_NAME +" ("
            + COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NAME_NAME + " VARCHAR NOT NULL,"
            + COLUMN_NAME_CLASS + " VARCHAR NOT NULL," +
            "FOREIGN KEY ("+COLUMN_NAME_CLASS+") REFERENCES classes (class_id)"
            + ");";

    public long createStudent(Students student){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_NAME, student.getStudentName());
        contentValues.put(COLUMN_NAME_CLASS, student.getClassID());
        return db.insert(TABLE_NAME, null, contentValues);
    }

    public List readAll(){
        List students = new ArrayList();
        String[] columns ={COLUMN_NAME_ID,COLUMN_NAME_NAME,COLUMN_NAME_CLASS};
        Cursor cursor = db.query(TABLE_NAME,columns,null,null,null,null,null);
        while(cursor.moveToNext()){
            students.add(new Students(cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_NAME_NAME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_NAME_CLASS))));
        }
        return students;
    }
    public Students readOne(int id){
        String[] columns = {COLUMN_NAME_ID,COLUMN_NAME_NAME,COLUMN_NAME_CLASS};
        String whereClause = COLUMN_NAME_ID + " = ?";
        String[] whereArgs = {id+""};
        Cursor cursor = db.query(TABLE_NAME,columns,whereClause,whereArgs,null,null,null);
        if(cursor.moveToNext()){
            return new Students(cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_NAME_NAME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_NAME_CLASS)));
        }
        return null;
    }
    public int update(Students student){
        ContentValues contentValues = new ContentValues();
        String whereClause = COLUMN_NAME_ID + " = ?";
        String[] whereArgs = {student.getStudentID() + ""};

        contentValues.put(COLUMN_NAME_NAME,student.getStudentName());
        contentValues.put(COLUMN_NAME_CLASS,student.getClassID());

        return  db.update(TABLE_NAME,contentValues,whereClause,whereArgs);
    }
    public int delete(int id){
        String whereClause =  COLUMN_NAME_ID + " = ?";
        String[] whereArgs = {id + ""};
        return db.delete(TABLE_NAME,whereClause,whereArgs);
    }
}
