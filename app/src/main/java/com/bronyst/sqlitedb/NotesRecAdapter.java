package com.bronyst.sqlitedb;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bronyst.sqlitedb.models.Note;

import java.util.ArrayList;
import java.util.List;

public class NotesRecAdapter extends RecyclerView.Adapter<NotesRecAdapter.ViewHolder> {
    private Context context;

    private List<Note> notes = new ArrayList<>();

    public NotesRecAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
         holder.noteTitle.setText(notes.get(position).getNoteTitle());
         holder.noteContent.setText(notes.get(position).getNoteContent());

//         navigate  to single note activity
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NoteActivity.class);
                intent.putExtra("note_id", notes.get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder {
        private TextView noteTitle, noteContent;
        private RelativeLayout parent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

             noteTitle = itemView.findViewById(R.id.txt_note_title);
             noteContent = itemView.findViewById(R.id.txt_note_content);

             parent = itemView.findViewById(R.id.parent);
        }
    }
}
