package me.khrystal.android_sqlcipher_ormlite;

import android.content.Context;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import net.sqlcipher.database.SQLiteDatabase;

import java.sql.SQLException;

/**
 * usage:
 * author: kHRYSTAL
 * create time: 16/11/8
 * update time:
 * email: 723526676@qq.com
 */

public class DBHelper extends OrmLiteSqliteOpenHelper {

    public static final String TAG = "DBHelper";
    public static final int VERSION = 1;
    private static final String DB_NAME = "test.db";
    private static final String AES_KEY = "khrystal";
    private Context mContext;

    Class<?>[] tableClasss = new Class<?>[] {User.class};

    public DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION, AES_KEY);
        mContext = context;
    }


    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            for (int i = 0; i < tableClasss.length; i++) {
                TableUtils.createTable(connectionSource, tableClasss[i]);
            }
        }
        catch (SQLException e) {
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {

            for (Class<?> cls : tableClasss) {
                TableUtils.dropTable(connectionSource, cls, true);
            }
            onCreate(database, connectionSource);
        }
        catch (SQLException e) {
        }
    }

    @Override
    public void close() {
        super.close();
    }
}
