package com.example.android.flavor;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder> {
    public List<GattInfoItem> collections = new ArrayList<>();

    public ServiceAdapter(List<GattInfoItem> collections) {
        this.collections = collections;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_service, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(collections.get(i));
    }

    @Override
    public int getItemCount() {
        return collections.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        TextView characterTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            characterTextView = itemView.findViewById(R.id.characterTextView);
        }

        private void bind(GattInfoItem item) {
            textView.setText("Service UUID:" + item.getGattService().getUuid().toString());
            for(int i = 0; i < item.getGattService().getCharacteristics().size(); i++) {
                characterTextView.setText("Character uuid:" + item.getGattService().getCharacteristics().get(i).getUuid() + "   Character properties:" + item.getGattService().getCharacteristics().get(i).getProperties());

            }
        }
    }
}
