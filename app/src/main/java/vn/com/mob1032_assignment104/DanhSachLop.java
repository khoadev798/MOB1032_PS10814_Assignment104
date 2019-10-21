package vn.com.mob1032_assignment104;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import vn.com.mob1032_assignment104.ClassAdapter.ClassAdapter;
import vn.com.mob1032_assignment104.DAO.ClassDAO;
import vn.com.mob1032_assignment104.DataModel.Classes;
import vn.com.mob1032_assignment104.util.DbHelper;

public class DanhSachLop extends AppCompatActivity {
    public static String DSL = "DSL OK";
    private ListView listViewDSL;
    private ClassAdapter classAdapter;
    private List<Classes> classList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_lop);
        mapComp();
        loadData();
        registerForContextMenu(listViewDSL);
    }
    private void mapComp(){
        listViewDSL = findViewById(R.id.lvClass);
    }
    private void loadData(){
        DbHelper dbHelper = new DbHelper(DanhSachLop.this);
        /*SQLiteDatabase db = dbHelper.getReadableDatabase();
        dbHelper.reset(db);*/
        ClassDAO classDAO = new ClassDAO(dbHelper.getWritableDatabase());

        classList = classDAO.readAllClasses();
        classAdapter = new ClassAdapter(DanhSachLop.this, R.layout.row_item_class, classList);
        listViewDSL.setAdapter(classAdapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.class_option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_class:
                addClass();
                return true;
            case R.id.delete_class:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void addClass() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DanhSachLop.this);
        // Get the layout inflater
        LayoutInflater inflater = DanhSachLop.this.getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setTitle("Create new class")
                .setView(inflater.inflate(R.layout.class_create, null))
                // Add action buttons
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        AlertDialog alertDialog  = (AlertDialog) dialog;
                        EditText etClassID = alertDialog.findViewById(R.id.etClassID);
                        EditText etClassName = alertDialog.findViewById(R.id.etClassName);
                        Classes classNew = new Classes(etClassID.getText().toString(),
                                etClassName.getText().toString());
                        DbHelper dbHelper = new DbHelper(DanhSachLop.this);
                        ClassDAO classDAO = new ClassDAO(dbHelper.getWritableDatabase());
                        long newID;
                        if((newID = classDAO.addClass(classNew))>0){
                            classList.add(classNew);
                            classAdapter.notifyDataSetChanged();
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //
                    }
                });
        builder.show();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.class_context_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()) {
            case R.id.update_class:
                updateClass(classList.get(info.position));
                return true;
            case R.id.delete_class:
                DbHelper dbHelper = new DbHelper(DanhSachLop.this);
                ClassDAO classDAO = new ClassDAO(dbHelper.getReadableDatabase());
                classDAO.deleteClass(classList.get(info.position).getClassID());
                classList.remove(info.position);
                classAdapter.notifyDataSetChanged();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
    public void updateClass(final Classes classNew){
        AlertDialog.Builder builder = new AlertDialog.Builder(DanhSachLop.this);
        LayoutInflater inflater = DanhSachLop.this.getLayoutInflater();
        View view = inflater.inflate(R.layout.class_create, null);
        EditText etClassID = view.findViewById(R.id.etClassID);
        EditText etClassName = view.findViewById(R.id.etClassName);
        etClassID.setText(classNew.getClassID());
        etClassName.setText(classNew.getClassName());

        builder.setTitle("Update class")
                .setView(view)
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        AlertDialog alertDialog = (AlertDialog) dialog;
                        EditText etClassID = alertDialog.findViewById(R.id.etClassID);
                        EditText etClassName = alertDialog.findViewById(R.id.etClassName);
                        classNew.setClassID(etClassID.getText().toString());
                        classNew.setClassName(etClassName.getText().toString());

                        DbHelper dbHelper = new DbHelper(DanhSachLop.this);
                        ClassDAO classDAO = new ClassDAO(dbHelper.getReadableDatabase());
                        long newId;
                        if((newId = classDAO.updateClass(classNew))>0){
                            classAdapter.notifyDataSetChanged();
                        }
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                //
            }
        });
        builder.show();
    }

}
