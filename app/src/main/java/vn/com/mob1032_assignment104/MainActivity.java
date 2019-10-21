package vn.com.mob1032_assignment104;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btn_DSL = null;
    Button btn_DSSV = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapComp();
        btn_DSL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this, DanhSachLop.class);
                startActivity(intent1);
            }
        });
        btn_DSSV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MainActivity.this, DanhSachSV.class);
                startActivity(intent2);
            }
        });
    }
    public void mapComp(){
        if(btn_DSL == null)
            btn_DSL = findViewById(R.id.btn_DSL);
        if(btn_DSSV == null)
            btn_DSSV = findViewById(R.id.btn_DSSV);
    }
}