package com.example.gand;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gand.R;
import com.example.gand.msgModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class messagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<msgModel> messages;

    public messagesAdapter(Context context, ArrayList<msgModel> messages) {
        this.context = context;
        this.messages = messages;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1) {
            View view = LayoutInflater.from(context).inflate(R.layout.sender_message_layout, parent, false);
            return new senderViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.reciver_message_layout, parent, false);
            return new receiverViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        msgModel message = messages.get(position);
        if (holder instanceof senderViewHolder) {
            ((senderViewHolder) holder).msgtxt.setText(message.getMessage());
        } else if (holder instanceof receiverViewHolder) {
            ((receiverViewHolder) holder).msgtxt.setText(message.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public int getItemViewType(int position) {
        return FirebaseAuth.getInstance().getCurrentUser().getUid().equals(messages.get(position).getSender_id()) ? 1 : 2;
    }

    static class senderViewHolder extends RecyclerView.ViewHolder {
        TextView msgtxt;

        public senderViewHolder(@NonNull View itemView) {
            super(itemView);
            msgtxt = itemView.findViewById(R.id.msgsendertyp);
        }
    }

    static class receiverViewHolder extends RecyclerView.ViewHolder {
        TextView msgtxt;

        public receiverViewHolder(@NonNull View itemView) {
            super(itemView);
            msgtxt = itemView.findViewById(R.id.recivertextset);
        }
    }
}