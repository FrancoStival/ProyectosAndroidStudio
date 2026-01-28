package net.vidalibarraquer.exemplesqlite;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private List<VideoGame> elements;

     public MyRecyclerViewAdapter(List<VideoGame> elements) {
        this.elements = elements;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View viewElement = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder, parent, false);
        return new ViewHolder(viewElement);
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, int position) {
        holder.getTxtElement().setText("Nom: "+elements.get(position).getNom()+"\nEmpresa: "+elements.get(position).getEmpresa());
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
                    ViewHolder.this.eliminaElement(v);
                }
            });

            txtElement = itemView.findViewById(R.id.textElement);
        }

        private void eliminaElement(View v) {
            //Al clicar l'element podem modificar o eliminar el registre
            ManegadorDades db = new ManegadorDades(v.getContext());
            VideoGame videoGame = elements.get(getAdapterPosition());
            AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
            alert.setMessage(R.string.borrar);
            alert.setPositiveButton(R.string.si,
                    new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            db.deleteGame(videoGame);
                            elements.remove(videoGame);
                            // No acaba de funcionar
                            notifyItemRemoved(getAdapterPosition());
                            //notifyItemRangeChanged(getAdapterPosition(), getItemCount());
                            Toast.makeText(v.getContext(),"Registre esborrat",Toast.LENGTH_SHORT).show();
                        }
                    });
            alert.setNegativeButton(R.string.no,new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    Toast.makeText(v.getContext(),"Registre no esborrat",Toast.LENGTH_SHORT).show();
                }
            });

            AlertDialog alertDialog = alert.create();
            alertDialog.show();
        }

        public TextView getTxtElement() {
            return txtElement;
        }
    }

}
