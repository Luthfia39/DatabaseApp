package com.example.databaseapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "DB_Toko";

//    versi digunakan jika terjadi perubahan dan table harus di drop,
//    maka versi dapat dinaikkan dan menaikkan versi
    private static final int DATABASE_VERSION = 1;

//    merepresentasikan kolom di table
    private static final String PRODUCT_TABLE = "product";
    private static final String PRODUCT_ID = "id";
    private static final String PRODUCT_NAME = "name";
    private static final String PRODUCT_PRICE = "price";

    public DatabaseHelper(Context context){
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        buat table
        String CREATE_TABLE_PRODUCT = "CREATE TABLE " + PRODUCT_TABLE
                + "(" + PRODUCT_ID + " INTEGER PRIMARY KEY, "
                +  PRODUCT_NAME + " TEXT, "
                + PRODUCT_PRICE + " INTEGER)";
//        Eksekusi
        sqLiteDatabase.execSQL(CREATE_TABLE_PRODUCT);
    }

    @Override
//    untuk mengecek apakah ada versi lama atau tidak
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
        onCreate(sqLiteDatabase);
    }

//    script untuk insert data
    public void insertProduct(Product product){
        SQLiteDatabase db = getReadableDatabase();
//        object product harus diubah menjadi contentValues sebelum masuk DB
        ContentValues values = new ContentValues();
//        primary key akan tergenerate
        values.put(PRODUCT_NAME, product.getName());
        values.put(PRODUCT_PRICE, product.getPrice());

//        insert ke database
        db.insert(PRODUCT_TABLE, null, values);
        db.close();
    }

    public Product getProductFromName (String name){
        SQLiteDatabase db = getReadableDatabase();
//        untuk membantu menjalankan query
        Cursor cursor = db.query(PRODUCT_TABLE,
                new String[] {PRODUCT_ID, PRODUCT_NAME, PRODUCT_PRICE},
                PRODUCT_NAME + "=?",
                new String[]{name}, null, null, null);


        if (cursor != null){
            cursor.moveToFirst();
        }
//        parameter get berdasarkan urutan new String pada cursor di atas
        Product product = new Product();
        product.setId(cursor.getInt(0));
        product.setName(cursor.getString(1));
        product.setPrice(cursor.getInt(2));

        return product;
    }

    public List<Product> getAllProduct(){
        List<Product> allProduct = new ArrayList<Product>();

        SQLiteDatabase db = getReadableDatabase();
        String selectQuery = "SELECT * from " + PRODUCT_TABLE;
//        looping ambil data hingga habis
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do {
                Product product = new Product();
                product.setId(cursor.getInt(0));
                product.setName(cursor.getString(1));
                product.setPrice(cursor.getInt(2));

                allProduct.add(product);
            } while (cursor.moveToNext());
        }
        return allProduct;
    }

    public int updateProduct(Product product){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PRODUCT_NAME, product.getName());
        values.put(PRODUCT_PRICE, product.getPrice());

        return db.update(PRODUCT_TABLE, values, PRODUCT_ID +"=?",
                new String[] {String.valueOf(product.getId())});
    }

    public void deleteProduct(Product product){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(PRODUCT_TABLE, PRODUCT_ID +"=?",
                new String[] {String.valueOf(product.getId())});
        db.close();
    }
}
