package com.example.LyfeRisk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {
    private List<String> userMessages;
    private List<String> botMessages;
    private LayoutInflater mInflate;
    private ItemClickListener mClickListener;


    public MyRecyclerViewAdapter(List<String> userMessages, List<String> botMessages, Context context) {
        this.userMessages = userMessages;
        this.botMessages = botMessages;
        this.mInflate = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflate.inflate(R.layout.activity_chat_recycler, parent, false );
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerViewAdapter.ViewHolder holder, int position) {
        String uMesseage  = userMessages.get(position);
        String bResponse = botMessages.get(position);
        holder.userMessageTV.setText(uMesseage);
        holder.botMessageTV.setText(bResponse);
    }


    @Override
    public int getItemCount() {
        return userMessages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView userMessageTV;
        TextView botMessageTV;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            userMessageTV = itemView.findViewById(R.id.userMessageTV);
            botMessageTV = itemView.findViewById(R.id.BotResponseTV);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null)
                mClickListener.onItemClick(v, getAdapterPosition());
        }
    }
    public void setClickListener(ItemClickListener itemClickListener){
        this.mClickListener = itemClickListener;
    }
    public interface ItemClickListener {

        void onItemClick(View view, int position);
    }
}
