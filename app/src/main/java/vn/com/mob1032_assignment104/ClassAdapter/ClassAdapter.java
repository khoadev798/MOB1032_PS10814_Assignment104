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

import vn.com.mob1032_assignment104.DataModel.Classes;
import vn.com.mob1032_assignment104.R;
import vn.com.mob1032_assignment104.DataModel.Classes;

public class ClassAdapter extends ArrayAdapter<Classes> {
    Context context;
    private List<Classes> objects;

    public ClassAdapter(Context context, int resource, List<Classes> objects) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.row_item_class,null);
        }
        TextView tvClassID = convertView.findViewById(R.id.tvClassID);
        TextView tvClassName = convertView.findViewById(R.id.tvClassName);

        tvClassID.setText(objects.get(position).getClassID());
        tvClassName.setText(objects.get(position).getClassName());

        return convertView;
    }
}
