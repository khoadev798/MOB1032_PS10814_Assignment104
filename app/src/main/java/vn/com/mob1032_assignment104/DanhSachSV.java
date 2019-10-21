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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import vn.com.mob1032_assignment104.ClassAdapter.StudentAdapter;
import vn.com.mob1032_assignment104.DAO.ClassDAO;
import vn.com.mob1032_assignment104.DAO.StudentDAO;
import vn.com.mob1032_assignment104.DataModel.Students;
import vn.com.mob1032_assignment104.util.DbHelper;

public class DanhSachSV extends AppCompatActivity {
    private ListView lvStudent;
    private StudentAdapter studentAdapter;
    private List<Students> studentsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_sv);
        mapComp();
        loadData();
        registerForContextMenu(lvStudent);
    }

    public void mapComp() {
        lvStudent = findViewById(R.id.lvStudent);
    }

    public void loadData() {
        DbHelper dbHelper = new DbHelper(DanhSachSV.this);
        StudentDAO studentDAO = new StudentDAO(dbHelper.getWritableDatabase());
        studentsList = studentDAO.readAll();
        studentAdapter = new StudentAdapter(DanhSachSV.this, R.layout.row_item_student, studentsList);
        lvStudent.setAdapter(studentAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.student_option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_student:
                createStudent();
                return true;
            case R.id.delete_student:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.student_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.update_student:
                updateStudent(studentsList.get(info.position));
                return true;
            case R.id.delete_student:
                DbHelper dbHelper = new DbHelper(DanhSachSV.this);
                StudentDAO studentDAO = new StudentDAO(dbHelper.getWritableDatabase());

                studentDAO.delete(studentsList.get(info.position).getStudentID());
                studentsList.remove(info.position);
                studentAdapter.notifyDataSetChanged();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void createStudent() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DanhSachSV.this);
        LayoutInflater inflater = DanhSachSV.this.getLayoutInflater();
        final View mView = inflater.inflate(R.layout.student_create, null);
        final EditText etStudentName = mView.findViewById(R.id.etStudentName);
        final Spinner spinner = (Spinner) mView.findViewById(R.id.spinner);
        /*ArrayAdapter<String> adapter = new ArrayAdapter<>(DanhSachSV.this,
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.class_array));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);*/
        DbHelper dbHelper = new DbHelper(DanhSachSV.this);
        final StudentDAO studentDAO = new StudentDAO(dbHelper.getWritableDatabase());
        List<String> list = new ArrayList<>();
        list.clear();

        list.addAll(new ClassDAO(dbHelper.getReadableDatabase()).getClassNames());
        ArrayAdapter<String> adapter = new ArrayAdapter(DanhSachSV.this, android.R.layout.simple_spinner_dropdown_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        Toast.makeText(DanhSachSV.this, spinner.getSelectedItem() + "", Toast.LENGTH_LONG).show();
        builder.setTitle("Create new student")
                .setView(mView)
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        AlertDialog alertDialog = (AlertDialog) dialog;
                        Students student = new Students(etStudentName.getText().toString(), spinner.getSelectedItem().toString());
                        long newId;
                        if ((newId = studentDAO.createStudent(student)) > 0) {
                            studentsList.add(new Students((int) newId, etStudentName.getText().toString(), spinner.getSelectedItem().toString()));
                            studentAdapter.notifyDataSetChanged();
                        }
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //
            }
        });
        builder.show();
    }
    public void updateStudent(final Students student) {
        AlertDialog.Builder builder = new AlertDialog.Builder(DanhSachSV.this);
        LayoutInflater inflater = DanhSachSV.this.getLayoutInflater();
        final View mView = inflater.inflate(R.layout.student_create, null);
        final EditText etStudentName = mView.findViewById(R.id.etStudentName);
        final Spinner spinner = (Spinner) mView.findViewById(R.id.spinner);
        DbHelper dbHelper = new DbHelper(DanhSachSV.this);
        final StudentDAO studentDAO = new StudentDAO(dbHelper.getWritableDatabase());
        List<String> list = new ArrayList<>();
        list.clear();

        list.addAll(new ClassDAO(dbHelper.getReadableDatabase()).getClassNames());
        ArrayAdapter<String> adapter = new ArrayAdapter(DanhSachSV.this, android.R.layout.simple_spinner_dropdown_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        etStudentName.setText(student.getStudentName());
        builder.setTitle("Update student")
                .setView(mView)
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog alertDialog = (AlertDialog) dialog;
                        /*EditText edtName = alertDialog.findViewById(R.id.etStudentName);
                        Spinner spinner1 = alertDialog.findViewById(R.id.spinner);*/
                        student.setStudentName(etStudentName.getText().toString());
                        student.setClassID(spinner.getSelectedItem().toString());
                        long newId;
                        if((newId = studentDAO.update(student))>0) {
                            studentAdapter.notifyDataSetChanged();
                        }
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //
            }
        });
        builder.show();
        }
    }


