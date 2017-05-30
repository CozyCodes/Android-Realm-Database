package cozycodes.project.realmsample;

import android.content.Context;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;

/**
 * Created by Cozycodes on 5/22/2017.
 */

public class LoadImage {

    public static void downloadImage(Context c, String imageUrl, ImageView img)
    {
        if(imageUrl != null && imageUrl.length()>0)
        {

            Picasso.with(c).load(imageUrl).placeholder(R.drawable.female).into(img);

        }else {
            Picasso.with(c).load(R.drawable.female).into(img);
        }
    }
}
