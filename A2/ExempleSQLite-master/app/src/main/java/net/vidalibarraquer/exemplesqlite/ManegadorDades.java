package net.vidalibarraquer.exemplesqlite;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class ManegadorDades extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    //NOM DE LA BASE DE DADES
    private static final String DATABASE_NAME = "videogames";
    // NOM DE LA TAULA
    private static final String TABLE_VIDEOGAMES = "registre";
    //CAMPS DE LA BASE DE DADES
    private static final String KEY_ID = "id";
    private static final String KEY_NOM = "nom";
    private static final String KEY_EMPRESA = "empresa";

    public ManegadorDades(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String Create_Table = "CREATE TABLE " + TABLE_VIDEOGAMES + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_NOM + " TEXT, " + KEY_EMPRESA + " TEXT " + ")";
        db.execSQL(Create_Table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        //DROP SI EXISTEIX
        db.execSQL("DROP TABLE  IF EXISTS " + TABLE_VIDEOGAMES);
        // CREAR LA TABLA DE NOU
        onCreate(db);

    }

    //AFEGIS UN VIDEOJOC NOU
    public void addVideogames(VideoGame game) {
        //
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues valors = new ContentValues();
        valors.put(KEY_NOM, game.getNom());
        valors.put(KEY_EMPRESA, game.getEmpresa());

        //Instertar a un registre
        db.insert(TABLE_VIDEOGAMES,null,valors);
        // tancar  conexio base de dades
        db.close();
    }

    //Query que retorna tots els  registres de la taula
    public List<VideoGame> getAllVideoGames(){
        List<VideoGame> vg = new ArrayList<VideoGame>();
        // QUERY DE TOTS ELS REGISTRES DE LA TAULA
        String selectTots ="SELECT * FROM "+ TABLE_VIDEOGAMES;
        //Executem la query  //CUANDO SOLO RETORNA UN REGISTRO ES getReadableDatabase si son mas de uno seria getWritableDatabase()
        SQLiteDatabase db = this.getWritableDatabase();
        // guarda los resultados en la variable "cursor"
        Cursor cursor = db.rawQuery(selectTots, null);
        //desplaçament pel cursor

        if (cursor.moveToFirst()) {
            do{
                //creamos el objeto "game" de la clase VideoGame
                VideoGame game = new VideoGame();
                //creamos los atributos de la clase game
                game.setId(Integer.parseInt(cursor.getString(0)));
                game.setNom(cursor.getString(1));
                game.setEmpresa(cursor.getString(2));
                //añadimos ala LISTA el objeto "game"
                vg.add(game);

            } while (cursor.moveToNext());
        }

        return vg;

    }

    //Query que retorna un sol registre de la taula
    public VideoGame vGame(int id){
        SQLiteDatabase db =this.getReadableDatabase();
        //Creem un cursor per desplaçarse pels registres per localitzar l'element en concrete
        Cursor cursor = db.query(TABLE_VIDEOGAMES, new String[]{KEY_ID, KEY_NOM, KEY_EMPRESA},
                KEY_ID+ "= ?",new String[]{String.valueOf(id)}, null, null, null, null);

        if(cursor != null) {
            cursor.moveToFirst();
        }

        VideoGame vg = new VideoGame(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2));

        return vg;
    }

    //Query per actualitzar un registre de la Taula
    public int updateGame(VideoGame vg){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues valors = new ContentValues();
        valors.put(KEY_NOM, vg.getNom());
        valors.put(KEY_EMPRESA, vg.getEmpresa());

        //Actualitzem a la Base de dades
        return db.update(TABLE_VIDEOGAMES, valors, KEY_ID+"=?",
                new String[]{String.valueOf(vg.getId())});

    }

    //Query per esborrar un registre de la Taula
    public void deleteGame(VideoGame vg){
        SQLiteDatabase db =this.getWritableDatabase();
        db.delete(TABLE_VIDEOGAMES, KEY_ID+ "= ?",
                new String[]{String.valueOf(vg.getId())});
        db.close();
    }
}

