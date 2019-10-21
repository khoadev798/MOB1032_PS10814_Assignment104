package vn.com.mob1032_assignment104.ClassAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import vn.com.mob1032_assignment104.DataModel.Students;
import vn.com.mob1032_assignment104.R;

public class StudentAdapter extends ArrayAdapter<Students> {
    private Context context;
    private List<Students> objects;
    public StudentAdapter(@NonNull Context context, int resource, @NonNull List<Students> objects) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null){
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.row_item_student,null);
        }

        TextView tvId = convertView.findViewById(R.id.tvID);
        TextView tvName = convertView.findViewById(R.id.tvName);
        TextView tvClass = convertView.findViewById(R.id.tvClass);

        tvId.setText(objects.get(position).getStudentID()+"");
        tvName.setText(objects.get(position).getStudentName());
        tvClass.setText(objects.get(position).getClassID());
        return convertView;
    }
}
