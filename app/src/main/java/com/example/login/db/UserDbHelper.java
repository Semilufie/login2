package com.example.login.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.login.entity.UserInfo;

public class UserDbHelper extends SQLiteOpenHelper {
    private static UserDbHelper sHelper;
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "user.db";

    public UserDbHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // 创建单例，供调用增删改查的方法
    public static UserDbHelper getInstance(Context context) {
        if (sHelper == null) {
            sHelper = new UserDbHelper(context, DB_NAME, null, DB_VERSION);
        }
        return sHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建user_table表
        db.execSQL("create table user_table(user_id integer primary key autoincrement," +
                "username text," + // 用户名
                "password text," + // 密码
                "nickname type" + ")" //
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    /*
     * 登录模块
     * */
    @SuppressLint("Range")
    public UserInfo login(String username) {
        SQLiteDatabase db = getReadableDatabase();
        UserInfo userInfo = null;
        String sql = "select user_id,username,password,nickname from user_table where username = ?";
        String[] selectionArgs = {username};
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        if (cursor.moveToNext()) {
            int user_id = cursor.getInt(cursor.getColumnIndex("user_id"));
            String username1 = cursor.getString(cursor.getColumnIndex("username"));
            String password1 = cursor.getString(cursor.getColumnIndex("password"));
            String nickname1 = cursor.getString(cursor.getColumnIndex("nickname"));
            userInfo = new UserInfo(user_id, username1, password1, nickname1);
        }
        cursor.close();
//        db.close();
        return userInfo;
    }


    /*
     * 注册模块
     * */
    public int register(String username, String password, String nickname) {
        // 获取SQLiteDatabase对象
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        // 填充占位符
        values.put("username", username);
        values.put("password", password);
        values.put("nickname", nickname);
        String nullColumnHack = "values(null,?,?,?)";

        // 执行
        int insert = (int) db.insert("user_table", nullColumnHack, values);
//        db.close();
        return insert;
    }

    /**
     * 根据用户名修改密码
     */
    public int updatePwd(String username, String password) {
        //获取SQLiteDatabase实例
        SQLiteDatabase db = getWritableDatabase();
        // 填充占位符
        ContentValues values = new ContentValues();
        values.put("password", password);
        // 执行SQL
        int update = db.update("user_table", values, "username=?", new String[]{username});
        // 关闭数据库连接
        db.close();
        return update;

    }
}
