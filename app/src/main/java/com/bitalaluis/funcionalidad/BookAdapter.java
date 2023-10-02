package com.bitalaluis.funcionalidad;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {

    private List<Book> bookList; // Una lista de objetos Book

    public BookAdapter(List<Book> bookList) {
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_book, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.textViewTitulo.setText(book.getTitulo());
        holder.textViewSubtitulo.setText(book.getSubtitulo());
        holder.textViewAutor.setText(book.getAutor());
        holder.textViewIsbn.setText(book.getIsbn());
        holder.textViewAnio.setText(book.getAnio());
        holder.textViewprecio.setText(Float.toString(book.getPrecio()));
        // Asigna otros datos del libro a las vistas
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitulo, textViewSubtitulo, textViewAutor, textViewIsbn, textViewAnio, textViewprecio;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitulo = itemView.findViewById(R.id.titulo);
            textViewSubtitulo = itemView.findViewById(R.id.subtitulo);
            textViewAutor = itemView.findViewById(R.id.autor);
            textViewIsbn = itemView.findViewById(R.id.isbn);
            textViewAnio = itemView.findViewById(R.id.anio);
            textViewprecio = itemView.findViewById(R.id.precio);
        }
    }
}
