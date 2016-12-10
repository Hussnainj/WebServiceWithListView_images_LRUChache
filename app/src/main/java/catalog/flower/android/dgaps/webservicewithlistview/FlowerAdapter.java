package catalog.flower.android.dgaps.webservicewithlistview;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wasim on 12/9/2016.
 */

public class FlowerAdapter extends ArrayAdapter<Flower> {

    private  Context context;
    private  List<Flower>flowerList;



    public FlowerAdapter(Context context, int resource, List<Flower> objects) {
        super(context, resource, objects);

        this.context=context;
        this.flowerList= objects;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_flower,parent,false);

        Flower flower = flowerList.get(position);
        TextView tv = (TextView) view.findViewById(R.id.textView1);
        tv.setText(flower.getName());
        ImageView imageView = (ImageView)  view.findViewById(R.id.imageView1);
        imageView.setImageBitmap(flower.getBitmap());

        return view;
    }
}
