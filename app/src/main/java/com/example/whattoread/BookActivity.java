package com.example.whattoread;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class BookActivity extends AppCompatActivity {

    public static final String BOOK_ID_KEY = "bookId";
    private TextView bookTitle, bookAuthor, bookPageCount, bookSynopsis;
    private ImageView bookImage;
    private Button btnAddToCurrentlyReading, btnTBR, btnFinished, btnFavorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        initViews();


        Intent intent = getIntent();
        if(null!=intent){
            int bookId = intent.getIntExtra(BOOK_ID_KEY,-1);
            if(bookId!=-1){
                Book incomingBook = Utils.getInstance(this).getBookById(bookId);
                if(null!=incomingBook){
                    setData(incomingBook);
                    handleAlreadyRead(incomingBook);
                    handleTBR(incomingBook);
                    handleCurrentlyReading(incomingBook);
                    handleFavorite(incomingBook);
                }

            }
        }


    }

    private void handleFavorite(final Book book){
        ArrayList<Book> favoriteBooks = Utils.getInstance(this).getFavoriteBooks();
        boolean existsInFavoriteBooks = false;
        for(Book b: favoriteBooks){
            if (b.getId() == book.getId()){
                existsInFavoriteBooks = true;
            }
        }

        if(existsInFavoriteBooks){
            btnFavorites.setEnabled(false);
        }else{
            btnFavorites.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Utils.getInstance(BookActivity.this).addToFavoriteBooks(book)){
                        Toast.makeText(BookActivity.this, "Book Added", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(BookActivity.this, FavoriteBooksActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(BookActivity.this, "Try again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void handleCurrentlyReading(final Book book){
        ArrayList<Book> currentlyReadingBooks = Utils.getInstance(this).getCurrentlyReadingBooks();
        boolean existsInCurrentlyReadingBooks = false;
        for(Book b: currentlyReadingBooks){
            if (b.getId() == book.getId()){
                existsInCurrentlyReadingBooks = true;
            }
        }

        if(existsInCurrentlyReadingBooks){
            btnAddToCurrentlyReading.setEnabled(false);
        }else{
            btnAddToCurrentlyReading.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Utils.getInstance(BookActivity.this).addToCurrentlyReadingBooks(book)){
                        Toast.makeText(BookActivity.this, "Book Added", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(BookActivity.this, CurrentlyReadingActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(BookActivity.this, "Try again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void handleTBR(final Book book){
        ArrayList<Book> wantToReadBooks = Utils.getInstance(this).getWantToReadBooks();
        boolean existsInTBRBooks = false;
        for(Book b: wantToReadBooks){
            if (b.getId() == book.getId()){
                existsInTBRBooks = true;
            }
        }

        if(existsInTBRBooks){
            btnTBR.setEnabled(false);
        }else{
            btnTBR.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Utils.getInstance(BookActivity.this).addToWantToReadBooks(book)){
                        Toast.makeText(BookActivity.this, "Book Added", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(BookActivity.this, WantToReadActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(BookActivity.this, "Try again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void handleAlreadyRead(Book book){
        ArrayList<Book> alreadyReadBooks = Utils.getInstance(this).getAlreadyReadBooks();
        boolean existsInAlreadyReadBooks = false;
        for(Book b: alreadyReadBooks){
            if (b.getId() == book.getId()){
                existsInAlreadyReadBooks = true;
            }
        }

        if(existsInAlreadyReadBooks){
            btnFinished.setEnabled(false);
        }else{
            btnFinished.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Utils.getInstance(BookActivity.this).addToAlreadyRead(book)){
                        Toast.makeText(BookActivity.this, "Book Added", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(BookActivity.this, AlreadyReadBooksActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(BookActivity.this, "Try again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void setData(Book book){
        bookTitle.setText(book.getTitle());
        bookAuthor.setText(book.getAuthor());
        bookPageCount.setText(String.valueOf(book.getPages()));
        bookSynopsis.setText(book.getLongDesc());
        Glide.with(this)
                .asBitmap().load(book.getImgUrl())
                .into(bookImage);

    }

    private void initViews(){
        bookTitle = findViewById(R.id.txtBookTitle);
        bookAuthor = findViewById(R.id.txtBookAuthor);
        bookPageCount = findViewById(R.id.txtBookPageCount);
        bookSynopsis = findViewById(R.id.txtBookSynopsis);

        bookImage = findViewById(R.id.imgBookImage);
        btnAddToCurrentlyReading = findViewById(R.id.btnAddToCurrentlyReading);
        btnTBR = findViewById(R.id.btnTBR);
        btnFinished = findViewById(R.id.btnFinished);
        btnFavorites = findViewById(R.id.btnAddToFavorites);
    }
}