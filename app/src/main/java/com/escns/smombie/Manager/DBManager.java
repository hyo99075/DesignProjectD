package com.escns.smombie.Manager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.escns.smombie.DAO.Record;
import com.escns.smombie.DAO.User;
import com.escns.smombie.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hyo99 on 2016-08-02.
 */

public class DBManager extends SQLiteOpenHelper {

    private String DB_NAME; // 테이블 이름
    private String USER_TABLE;
    private String RECORD_TABLE;
    private String STATISTIC_TABLE;

    /**
     * 생성자
     *
     * @param context MainActivity의 Context
     */
    public DBManager(Context context) {
        super(context, context.getResources().getString(R.string.app_name), null, 1);
        DB_NAME = context.getResources().getString(R.string.app_name);        // app name의 DB table 생성
        USER_TABLE = "USERS";
        RECORD_TABLE = "RECORDS";
        STATISTIC_TABLE = "STATISTICS";
    }

    /**
     * onCreate
     *
     * @param db SQLite에서 데이터베이스를 쓰기위한 파라미터
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        StringBuffer sb = new StringBuffer();

        sb.append(" CREATE TABLE " + USER_TABLE + " ( ");
        sb.append(" USER_ID_INT INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sb.append(" USER_ID_TEXT TEXT, ");
        sb.append(" NAME TEXT, ");
        sb.append(" EMAIL TEXT, ");
        sb.append(" GENDER TEXT, ");
        sb.append(" AGE INTEGER, ");
        sb.append(" POINT INTEGER, ");
        sb.append(" GOAL INTEGER, ");
        sb.append(" REWORD INTEGER, ");
        sb.append(" SUCCESSCNT INTEGER, ");
        sb.append(" FAILCNT INTEGER, ");
        sb.append(" AVGDIST INTEGER ) ");

        db.execSQL(sb.toString());

        sb = new StringBuffer();

        sb.append(" CREATE TABLE " + RECORD_TABLE + " ( ");
        sb.append(" _id INTEGER PRIMARY KEY AUTOINCREMENT, "); // 의미없음
        sb.append(" USER_ID_INT INTEGER, ");
        sb.append(" YEAR INTEGER, ");
        sb.append(" MONTH INTEGER, ");
        sb.append(" DAY INTEGER, ");
        sb.append(" HOUR INTEGER, ");
        sb.append(" DIST INTEGER ) ");

        db.execSQL(sb.toString());

        sb = new StringBuffer();

        sb.append(" CREATE TABLE " + STATISTIC_TABLE + " ( ");
        sb.append(" _d INTEGER PRIMARY KEY AUTOINCREMENT, "); // 의미없음
        sb.append(" AVGMALE DOUBLE, ");
        sb.append(" AVGFEMALE DOUBLE, ");
        sb.append(" AVG10S DOUBLE, ");
        sb.append(" AVG20S DOUBLE, ");
        sb.append(" AVG30S DOUBLE, ");
        sb.append(" AVG40S DOUBLE, ");
        sb.append(" AVG50S DOUBLE ) ");

        db.execSQL(sb.toString());
    }

    /**
     * onUpgrade
     *
     * @param db         SQLite에서 데이터베이스를 쓰기위한 파라미터
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("DROP TABLE IF EXISTS RECORD_LIST"); // 테이블 삭제 - 초기화
        //onCreate(db); // RECORD_LIST 테이블 다시 생성
    }

    /**
     * 파라미터로 받은 data를 Record테이블에 저장
     *
     * @param data
     */
    public void insertRecord(Record data) {
        SQLiteDatabase db = null;
        try {
            db = getWritableDatabase();

            StringBuffer sb = new StringBuffer();
            sb.append(" INSERT INTO " + RECORD_TABLE + " ( ");
            sb.append(" USER_ID_INT, YEAR, MONTH, DAY, HOUR, DIST) ");
            sb.append(" VALUES ( ?, ?, ?, ?, ?, ?, ? ) ");

            db.execSQL(sb.toString(),
                    new Object[]{
                            data.getmIdInt(),
                            data.getmYear(),
                            data.getmMonth(),
                            data.getmDay(),
                            data.getmHour(),
                            data.getmDist()
                    });

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    /**
     * id 값에 해당하는 Data를 Record테이블에서 찾아서 List 형식으로 반환
     *
     * @param id
     * @return
     */
    public List<Record> getRecord(int id) {

        SQLiteDatabase db = null;
        List<Record> list = new ArrayList<>();
        Record record = null;

        try {
            db = getReadableDatabase();

            StringBuffer sb = new StringBuffer();
            sb.append(" SELECT * FROM " + RECORD_TABLE);
            sb.append(" WHERE USER_ID_INT is ? ");

            Cursor cursor = db.rawQuery(sb.toString(),
                    new String[]{
                            "" + id
                    });

            while (cursor.moveToNext()) {
                record = new Record(cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getInt(2),
                        cursor.getInt(3),
                        cursor.getInt(4),
                        cursor.getInt(5));
                list.add(record);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }

        return list;
    }
}
