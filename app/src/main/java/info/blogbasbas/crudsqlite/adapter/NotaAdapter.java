package info.blogbasbas.crudsqlite.adapter;

import android.app.Activity;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.LinkedList;

import info.blogbasbas.crudsqlite.CustomOnItemCLickListener;
import info.blogbasbas.crudsqlite.PendataanActivity;
import info.blogbasbas.crudsqlite.R;
import info.blogbasbas.crudsqlite.entity.Nota;

/**
 * Created by User on 21/01/2018.
 */

public class NotaAdapter extends RecyclerView.Adapter<NotaAdapter.NoteViewHolder> {
    private LinkedList<Nota> listNota;
    private Activity activity;

    public NotaAdapter(LinkedList<Nota> listNota) {
        this.listNota = listNota;
    }

    public NotaAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setListNota(LinkedList<Nota> listNota) {
        this.listNota = listNota;
    }

    public LinkedList<Nota> getListNota() {
        return listNota;
    }

    @Override
    public NotaAdapter.NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return  new NoteViewHolder(view);

    }

    @Override
    public void onBindViewHolder(NotaAdapter.NoteViewHolder holder, int position) {
        holder.tvJudul.setText(getListNota().get(position).getJudul());
        holder.tvDeskripsi.setText(getListNota().get(position).getDeskripsi());
        holder.TvTanggal.setText(getListNota().get(position).getTanggal());
       holder.cvNota.setOnClickListener(new CustomOnItemCLickListener
               (position, new CustomOnItemCLickListener.OnItemClickCallBack() {
           @Override
           public void OnItemClicked(View view, int position) {
               Intent intent = new Intent(activity, PendataanActivity.class);
               intent.putExtra(PendataanActivity.EXTRA_POSITION, position);
               intent.putExtra(PendataanActivity.EXTRA_NOTE, getListNota().get(position));
               activity.startActivityForResult(intent,PendataanActivity.REQUEST_UPDATE);


           }
       }));



    }

    @Override
    public int getItemCount() {
        return getListNota().size();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView tvJudul, tvDeskripsi,TvTanggal;
        CardView cvNota;

        public NoteViewHolder(View itemView) {
            super(itemView);

            tvJudul = (TextView)itemView.findViewById(R.id.tv_item_title);
            tvDeskripsi = (TextView)itemView.findViewById(R.id.tv_item_description);
            TvTanggal = (TextView)itemView.findViewById(R.id.tv_item_date);
            cvNota = (CardView)itemView.findViewById(R.id.cv_item_note);
        }
    }
}
