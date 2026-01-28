package com.example.prova1_stival_franco;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private List<Object> elements;

    public MyRecyclerViewAdapter(List<Object> elements) {
        this.elements = elements;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewElement = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder, parent, false);
        return new ViewHolder(viewElement);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Object item = elements.get(position);
        Context context = holder.itemView.getContext();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String favoriteGame = sharedPreferences.getString("favorite_game", "");

        if (item instanceof Juego) {
            Juego juego = (Juego) item;
            String displayName = juego.getName();
            if (!favoriteGame.isEmpty() && favoriteGame.equalsIgnoreCase(juego.getName())) {
                displayName += " *";
            }
            holder.getTxtElement().setText(displayName);
        } else if (item instanceof Plataforma) {
            holder.getTxtElement().setText(((Plataforma) item).getName());
        }
    }

    @Override
    public int getItemCount() {
        return elements.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtElement;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Object item = elements.get(getAdapterPosition());
                    if (item instanceof Juego) {
                        mostraJoc(v, (Juego) item);
                    } else if (item instanceof Plataforma) {
                        cercaPlataforma(v, (Plataforma) item);
                    }
                }
            });
            txtElement = itemView.findViewById(R.id.textElement);
        }

        private void mostraJoc(View v, Juego juego) {
            Intent mostrarJoc = new Intent(v.getContext(), MostrarLlistaJocs.class);
            mostrarJoc.putExtra("id", juego.getId());
            mostrarJoc.putExtra("slug", juego.getSlug());
            mostrarJoc.putExtra("name", juego.getName());
            mostrarJoc.putExtra("released", juego.getReleased());
            mostrarJoc.putExtra("rating", juego.getRating());
            v.getContext().startActivity(mostrarJoc);
        }

        private void cercaPlataforma(View v, Plataforma plataforma) {
            Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
            intent.putExtra(SearchManager.QUERY, plataforma.getName());
            v.getContext().startActivity(intent);
        }

        public TextView getTxtElement() {
            return txtElement;
        }
    }
}