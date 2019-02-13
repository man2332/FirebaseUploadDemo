package com.example.firebaseuploaddemo;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder>  {
    private Context mContext;
    private List<Upload> mUploads;
    private OnItemClickListener mlistener;

    public ImageAdapter(Context context, List<Upload> uploads) {
        mContext = context;
        mUploads = uploads;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.image_item, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        Upload uploadCurrent = mUploads.get(position);
        holder.textViewName.setText(uploadCurrent.getName());
        Picasso.get()
                .load(uploadCurrent.getImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

        public TextView textViewName;
        public ImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.text_view_name);
            imageView = itemView.findViewById(R.id.image_view_upload);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (mlistener != null) {
                        if (position != RecyclerView.NO_POSITION) {
                            mlistener.onItemClicked(position);
                        }
                    }
                }
            });

            imageView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Title here");

            MenuItem doWhatever = menu.add(Menu.NONE, 1, 1, "Whatever bro..");
            MenuItem delete = menu.add(Menu.NONE, 2, 2, "Delete");

            doWhatever.setOnMenuItemClickListener(this);
            delete.setOnMenuItemClickListener(this);
            //MenuItem setOnMenuItemClickListener(MenuItem.OnMenuItemClickListener menuItemClickListener)

            //public interface OnMenuItemClickListener {
            //        public boolean onMenuItemClick(MenuItem item);
            //    }
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if(mlistener != null) {
                int position = getAdapterPosition();

                switch (item.getItemId()) {
                    case 1:
                        mlistener.onWhateverClicked(position);
                        break;
                    case 2:
                        mlistener.onDeleteClicked(position);
                        break;
                }
            }

            return true;
        }
    }

    public interface OnItemClickListener {
        void onItemClicked(int pos);

        void onWhateverClicked(int pos);

        void onDeleteClicked(int pos);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mlistener = onItemClickListener;
    }


}