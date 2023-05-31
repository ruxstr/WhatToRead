package com.example.whattoread;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Utils {
    private static final String ALL_BOOKS_KEY = "all_books";
    private static final String ALREADY_READ_BOOKS = "already_read_books";
    private static final String WANT_TO_READ_BOOKS = "want_to_read_books";
    private static final String CURRENTLY_READING_BOOKS = "currently_reading_books";
    private static final String FAVORITE_BOOKS = "favorite_books";


    private static Utils instance;
    private SharedPreferences sharedPreferences;

//    private static ArrayList<Book> allBooks;
//    private static ArrayList<Book> alreadyReadBooks;
//    private static ArrayList<Book> wantToReadBooks;
//    private static ArrayList<Book> currentlyReadingBooks;
//    private static ArrayList<Book> favoriteBooks;

    private Utils(Context context) {
        sharedPreferences = context.getSharedPreferences("alternate_db",Context.MODE_PRIVATE);
        if(null == getAllBooks()){
            //allBooks = new ArrayList<>();
            initData();
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        if(null == getAlreadyReadBooks()){
            //alreadyReadBooks = new ArrayList<>();
            editor.putString(ALREADY_READ_BOOKS,gson.toJson(new ArrayList<Book>()));
            editor.commit();
        }
        if(null == getWantToReadBooks()){
            //wantToReadBooks = new ArrayList<>();
            editor.putString(WANT_TO_READ_BOOKS,gson.toJson(new ArrayList<Book>()));
            editor.commit();
        }
        if(null == getCurrentlyReadingBooks()){
            //currentlyReadingBooks = new ArrayList<>();
            editor.putString(CURRENTLY_READING_BOOKS,gson.toJson(new ArrayList<Book>()));
            editor.commit();
        }
        if(null == getFavoriteBooks()){
            //favoriteBooks = new ArrayList<>();
            editor.putString(FAVORITE_BOOKS,gson.toJson(new ArrayList<Book>()));
            editor.commit();
        }

    }

    private void initData() {

        ArrayList<Book> books = new ArrayList<>();
        books.add(new Book(1,"The Way of Kings", "Brandon Sanderson", 1007,"https://m.media-amazon.com/images/I/91KzZWpgmyL._AC_UF1000,1000_QL80_.jpg"
                ,"The entryway to a universe-encompassing conflict","Cosmere experts might advise against using the first installment of The Stormlight Archive as an entrypoint " +
                "to Sanderson's universe, as the series is really shaping up to be the carpet that ties the Cosmere itself together. Previous knowledge is therefore advised. Featuring an intricate and fascinating native magic system" +
                "in the author's characteristic way, this book paves the way to what will undoubtedly become a fantasy classic, building grounded and complex characters, masterfully weaving the threads" +
                "of the larger epic and cementing Sanderson as the unrivaled Master of Hard Magic Systems."
        ));
        books.add(new Book(2,"The Shadow Rising", "Robert Jordan", 1007,"https://m.media-amazon.com/images/I/41IP4WOS-xL.jpg"
                ,"The fourth installment of an epic","No spoilers as of yet."));
        books.add(new Book(3,"Dracula", "Bram Stoker", 418,"https://upload.wikimedia.org/wikipedia/en/7/7c/Dracula_Book_Cover_1916.jpg"
                ,"It's a classic","Dracula is a novel by Bram Stoker, published in 1897. An epistolary novel, the narrative is related through letters, " +
                "diary entries, and newspaper articles. It has no single protagonist, but opens with solicitor Jonathan Harker taking a business trip to stay at the castle " +
                "of a Transylvanian nobleman, Count Dracula. Mischief, murder and mayhem ensue as the Count finds his way to the previously tranquil shores of England" +
                "as the rest of the cast must piece his plan together and find a way to stop him. Permanently."));
        books.add(new Book(4,"The Dragon Reborn","Robert Jordan", 624, "https://m.media-amazon.com/images/I/41xhdxCJnQS.jpg"
                ,"The plot thickens","Road trips and river adventures see our beloved cast of characters once again separated and united in the seemingly everlasting hunt" +
                "for the Dark One's servants. Will they succeed in securing yet another priceless relic while still remaining out of The Lord of the Grave's everreaching grasp?"));
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        editor.putString(ALL_BOOKS_KEY,gson.toJson(books));
        editor.commit();
    }

    public static Utils getInstance(Context context) {
        if (null == instance) {
            instance = new Utils(context);
        }
        return instance;
    }

    public ArrayList<Book> getAllBooks() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType();
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(ALL_BOOKS_KEY,null),type);
        return books;
    }

    public ArrayList<Book> getAlreadyReadBooks() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType();
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(ALREADY_READ_BOOKS,null),type);
        return books;
    }

    public ArrayList<Book> getWantToReadBooks() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType();
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(WANT_TO_READ_BOOKS,null),type);
        return books;
    }

    public ArrayList<Book> getCurrentlyReadingBooks() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType();
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(CURRENTLY_READING_BOOKS,null),type);
        return books;
    }

    public ArrayList<Book> getFavoriteBooks() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType();
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(FAVORITE_BOOKS,null),type);
        return books;
    }

    public Book getBookById(int id){
        ArrayList<Book> books = getAllBooks();
        if(null!=books){
            for (Book b: books){
                if(b.getId()==id){
                    return b;
                }
            }

        }

        return null;
    }

    public boolean addToAlreadyRead(Book book){
        //return alreadyReadBooks.add(book);
        ArrayList<Book> books = getAlreadyReadBooks();
        if(null!=books){
            if(books.add(book)){
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(ALREADY_READ_BOOKS);
                editor.putString(ALREADY_READ_BOOKS,gson.toJson(books));
                editor.commit();
                return true;
            }
        }
        return false;
    }
    public boolean addToWantToReadBooks(Book book){
        //return wantToReadBooks.add(book);
        ArrayList<Book> books = getWantToReadBooks();
        if(null!=books){
            if(books.add(book)){
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(WANT_TO_READ_BOOKS);
                editor.putString(WANT_TO_READ_BOOKS,gson.toJson(books));
                editor.commit();
                return true;
            }
        }
        return false;
    }

    public boolean addToCurrentlyReadingBooks(Book book){
        //return currentlyReadingBooks.add(book);
        ArrayList<Book> books = getCurrentlyReadingBooks();
        if(null!=books){
            if(books.add(book)){
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(CURRENTLY_READING_BOOKS);
                editor.putString(CURRENTLY_READING_BOOKS,gson.toJson(books));
                editor.commit();
                return true;
            }
        }
        return false;
    }

    public boolean addToFavoriteBooks(Book book){
        //return favoriteBooks.add(book);
        ArrayList<Book> books = getFavoriteBooks();
        if(null!=books){
            if(books.add(book)){
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(FAVORITE_BOOKS);
                editor.putString(FAVORITE_BOOKS,gson.toJson(books));
                editor.commit();
                return true;
            }
        }
        return false;
    }

    public boolean removeFromAlreadyRead(Book book){
        //return alreadyReadBooks.remove(book);
        ArrayList<Book> books = getAlreadyReadBooks();
        if(null!=books){
            for(Book b:books){
                if(b.getId() == book.getId()){
                    if(books.remove(b)){
                        Gson gson = new Gson();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(ALREADY_READ_BOOKS);
                        editor.putString(ALREADY_READ_BOOKS,gson.toJson(books));
                        editor.commit();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean removeFromWantToRead(Book book){
        //return wantToReadBooks.remove(book);
        ArrayList<Book> books = getWantToReadBooks();
        if(null!=books){
            for(Book b:books){
                if(b.getId() == book.getId()){
                    if(books.remove(b)){
                        Gson gson = new Gson();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(WANT_TO_READ_BOOKS);
                        editor.putString(WANT_TO_READ_BOOKS,gson.toJson(books));
                        editor.commit();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean removeFromCurrentlyReading(Book book){
        //return currentlyReadingBooks.remove(book);
        ArrayList<Book> books = getCurrentlyReadingBooks();
        if(null!=books){
            for(Book b:books){
                if(b.getId() == book.getId()){
                    if(books.remove(b)){
                        Gson gson = new Gson();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(CURRENTLY_READING_BOOKS);
                        editor.putString(CURRENTLY_READING_BOOKS,gson.toJson(books));
                        editor.commit();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean removeFromFavorites(Book book){
        //return favoriteBooks.remove(book);
        ArrayList<Book> books = getFavoriteBooks();
        if(null!=books){
            for(Book b:books){
                if(b.getId() == book.getId()){
                    if(books.remove(b)){
                        Gson gson = new Gson();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(FAVORITE_BOOKS);
                        editor.putString(FAVORITE_BOOKS,gson.toJson(books));
                        editor.commit();
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
