package info.blogbasbas.crudsqlite;

import android.view.View;

/**
 * Created by User on 21/01/2018.
 */

public class CustomOnItemCLickListener implements View.OnClickListener{
    private int posititon;
    private OnItemClickCallBack onItemClickCallBack;

    public CustomOnItemCLickListener(int posititon, OnItemClickCallBack onItemClickCallBack){
        this.posititon = posititon;
        this.onItemClickCallBack = onItemClickCallBack;

    }

    @Override
    public void onClick(View view) {
        onItemClickCallBack.OnItemClicked(view, posititon);

    }

    public interface OnItemClickCallBack {
        void OnItemClicked(View view, int position);
    }
}
