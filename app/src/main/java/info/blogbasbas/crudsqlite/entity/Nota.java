package info.blogbasbas.crudsqlite.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by User on 21/01/2018.
 */

public class Nota implements Parcelable {

    String judul, deskripsi, tanggal ;
    int id;

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.judul);
        dest.writeString(this.deskripsi);
        dest.writeString(this.tanggal);
        dest.writeInt(this.id);
    }

    public Nota() {
    }

    protected Nota(Parcel in) {
        this.judul = in.readString();
        this.deskripsi = in.readString();
        this.tanggal = in.readString();
        this.id = in.readInt();
    }

    public static final Parcelable.Creator<Nota> CREATOR = new Parcelable.Creator<Nota>() {
        @Override
        public Nota createFromParcel(Parcel source) {
            return new Nota(source);
        }

        @Override
        public Nota[] newArray(int size) {
            return new Nota[size];
        }
    };
}
