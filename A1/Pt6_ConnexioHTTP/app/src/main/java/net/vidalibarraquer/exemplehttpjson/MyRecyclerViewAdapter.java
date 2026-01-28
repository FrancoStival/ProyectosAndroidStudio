package net.vidalibarraquer.exemplehttpjson;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private List<Personatge> elements;

    public MyRecyclerViewAdapter(List<Personatge> elements) {
        this.elements = elements;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View viewElement = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder, parent, false);

        return new ViewHolder(viewElement);
    }


    @Override
    public void onBindViewHolder( ViewHolder holder, int position) {
        holder.getTxtElement().setText(elements.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return elements.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtElement;

        public ViewHolder(View itemView) {
            super(itemView);
            //Quan fem click a la llista mostrem l'element
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewHolder.this.mostraElement(v);
                }
            });

            txtElement = itemView.findViewById(R.id.textElement);
        }

        private void mostraElement(View v) {

            // Cridem la pantalla de mostrar personatge i li passem les dades
            Intent mostrarPersonatge = new Intent(v.getContext(), MostraPersonatge.class);
            Personatge personatge = elements.get(getAdapterPosition());
            mostrarPersonatge.putExtra("name", personatge.getName());
            mostrarPersonatge.putExtra("planet", personatge.getPlanet());
            mostrarPersonatge.putExtra("image", personatge.getImage());
            v.getContext().startActivity(mostrarPersonatge);

        }

        public TextView getTxtElement() {
            return txtElement;
        }
    }

}
