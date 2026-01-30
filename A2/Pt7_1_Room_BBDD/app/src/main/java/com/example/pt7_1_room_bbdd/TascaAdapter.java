package com.example.pt7_1_room_bbdd;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pt7_1_room_bbdd.model.Tag;
import com.example.pt7_1_room_bbdd.model.TascaWithTags;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TascaAdapter extends RecyclerView.Adapter<TascaAdapter.TascaViewHolder> {

    private List<TascaWithTags> tasques = new ArrayList<>();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

    public void setTasques(List<TascaWithTags> tasques) {
        this.tasques = tasques;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TascaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tasca, parent, false);
        return new TascaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TascaViewHolder holder, int position) {
        TascaWithTags current = tasques.get(position);
        holder.tvTitol.setText(current.tasca.titol);
        holder.tvDescripcio.setText(current.tasca.descripcio);
        holder.tvEstat.setText("Estat: " + current.tasca.estat);

        StringBuilder tagsStr = new StringBuilder("Tags: ");
        if (current.tags != null) {
            for (int i = 0; i < current.tags.size(); i++) {
                tagsStr.append(current.tags.get(i).nom);
                if (i < current.tags.size() - 1) tagsStr.append(", ");
            }
        }
        holder.tvTags.setText(tagsStr.toString());

        String dates = "Creada: " + dateFormat.format(new Date(current.tasca.dataCreacio));
        if (current.tasca.dataCanvi > 0) {
            dates += " | Canvi: " + dateFormat.format(new Date(current.tasca.dataCanvi));
        }
        holder.tvDates.setText(dates);
    }

    @Override
    public int getItemCount() {
        return tasques.size();
    }

    static class TascaViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitol, tvDescripcio, tvEstat, tvTags, tvDates;

        public TascaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitol = itemView.findViewById(R.id.tvTitol);
            tvDescripcio = itemView.findViewById(R.id.tvDescripcio);
            tvEstat = itemView.findViewById(R.id.tvEstat);
            tvTags = itemView.findViewById(R.id.tvTags);
            tvDates = itemView.findViewById(R.id.tvDates);
        }
    }
}
