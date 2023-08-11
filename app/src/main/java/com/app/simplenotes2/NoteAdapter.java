package com.app.simplenotes2;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteViewHolder> {
    private Context context;
    private List<NoteModel> noteModelList;

    public NoteAdapter(Context context, List<NoteModel> noteModelList) {
        this.context = context;
        this.noteModelList = noteModelList;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view,parent,false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.title.setText(noteModelList.get(position).getTitle());
        holder.description.setText(noteModelList.get(position).getDescription());
        holder.date.setText(noteModelList.get(position).getCreatedTime());

        holder.itemCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NoteDetailsActivity.class);
                intent.putExtra("Title",noteModelList.get(holder.getAdapterPosition()).getTitle());
                intent.putExtra("Description",noteModelList.get(holder.getAdapterPosition()).getDescription());
                intent.putExtra("Key",noteModelList.get(holder.getAdapterPosition()).getKey());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return noteModelList.size();
    }
}

class NoteViewHolder extends RecyclerView.ViewHolder{

    TextView title,description,date;
    CardView itemCard;

    public NoteViewHolder(@NonNull View itemView) {
        super(itemView);

        title = itemView.findViewById(R.id.titleoutput);
        description = itemView.findViewById(R.id.descriptionoutput);
        date = itemView.findViewById(R.id.timeoutput);
        itemCard = itemView.findViewById(R.id.itemCard);
    }
}

//Signature :
//10120146 - Irshal Mulky H - IF4