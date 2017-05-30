package cozycodes.project.realmsample;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Cozycodes on 5/22/2017.
 */

public class SampleAdapter extends RecyclerView.Adapter<SampleAdapter.MyHolder> {
    List<Sample> wishes = new ArrayList<>();
    Sample val;
    private Realm realm;
    Context mContext;
    ArrayList<Sample> sample;
    private LayoutInflater inflater;

    public SampleAdapter(Context context, ArrayList<Sample> sample) {
        this.mContext = context;
        this.sample = sample;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.listing,parent,false);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {

        realm = RealmConnect.getInstance().getRealm();
         val = sample.get(position);

        holder.in_name.setText(val.getName());
        holder.in_address.setText(val.getAddress());
        holder.in_age.setText(val.getAge());

        String imageUrl = val.getImage().replace("https","http");

        LoadImage.downloadImage(mContext,imageUrl,holder.profile_img);

        // Long Press to delete item
        holder.cardview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Delete User");
                builder.setMessage("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RealmResults<Sample> results = realm.where(Sample.class).findAll();

                        realm.beginTransaction();
                        results.remove(position);
                        realm.commitTransaction();

                    }
                });
                builder.setNegativeButton("No", null);
                builder.show();

                return false;
            }
        });

       // Click to Edit item values
        holder.cardview.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View content = inflater.inflate(R.layout.edit_listing, null);
                final EditText editTitle = (EditText) content.findViewById(R.id.Et_name);
                final EditText editAuthor = (EditText) content.findViewById(R.id.Et_address);
                final EditText editThumbnail = (EditText) content.findViewById(R.id.Et_age);
                final EditText editUrl = (EditText) content.findViewById(R.id.Et_url);

                editTitle.setText(sample.get(position).getName());
                editAuthor.setText(sample.get(position).getAddress());
                editThumbnail.setText(sample.get(position).getAge());
                editUrl.setText(sample.get(position).getImage());

//                Toast.makeText(mContext, sample.get(position).getName() , Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setView(content)
                        .setTitle("Edit User")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                RealmResults<Sample> results = realm.where(Sample.class).findAll();

                                realm.beginTransaction();
                                results.get(position).setAddress(editAuthor.getText().toString());
                                results.get(position).setName(editTitle.getText().toString());
                                results.get(position).setAge(editThumbnail.getText().toString());
                                results.get(position).setImage(editUrl.getText().toString());

                                realm.commitTransaction();

//                                notifyDataSetChanged();
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return sample.size();
    }


    public class MyHolder extends RecyclerView.ViewHolder {

        TextView in_name,in_address,in_age;
        ImageView profile_img;
        CardView cardview;

        public MyHolder(View itemView) {
            super(itemView);

            in_name= (TextView) itemView.findViewById(R.id.in_name);
            in_address= (TextView) itemView.findViewById(R.id.in_address);
            in_age= (TextView) itemView.findViewById(R.id.in_age);
            profile_img= (ImageView) itemView.findViewById(R.id.profile_img);
            cardview = (CardView) itemView.findViewById(R.id.cardview);
        }
    }

}

