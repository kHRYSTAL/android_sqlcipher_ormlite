package me.khrystal.android_sqlcipher_ormlite;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import net.sqlcipher.database.SQLiteDatabase;

import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * usage: 如果对未加密的数据库 使用该类去进行数据库加密
 * 目前想到的办法是:
 * 新建一个db 先把AES_KEY 设置为"" 进行表的读取,然后设置KEY,再拷贝到新的db中
 * author: kHRYSTAL
 * create time: 16/11/8
 * update time:
 * email: 723526676@qq.com
 */

public class DBHelper extends OrmLiteSqliteOpenHelper {
    // 模拟数据库升级操作
    private static final int VERSION_USER = 1;
    private static final int VERSION_USER_ADD_COL = 2;
    private static final int VERSION_USER_AUTO_INC = 3;
    private static final int VERSION_USER_ID_AUTO  = 11;

    public static final int VERSION = VERSION_USER_ID_AUTO;

    public static final String TAG = "DBHelper";

    private static final String DB_NAME = "test3.db";
//    当KEY为空时 相当与不加密

    private static final String AES_KEY = "";
    private Context mContext;
    private static DBHelper helper = null;

    private static final AtomicInteger usageCounter = new AtomicInteger(0);

    public static DBHelper getManager() {
        return getHelper(MainApplication.APP_CONTEXT);
    }

    private DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION, AES_KEY);
        mContext = context;
    }

    private static synchronized DBHelper getHelper(Context context) {
        if (helper == null) {
            helper = new DBHelper(context);
        }
        usageCounter.incrementAndGet();
        return helper;
    }


    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
       createTable(connectionSource);
    }

    public void createTable(ConnectionSource cs) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, User.class);
        } catch (SQLException e) {
            Log.e(TAG, "create table error");
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        Log.e("VERSION", "oldVersion:"+ oldVersion +", newVersion:" + newVersion);
        DBUpdateUtil.getInstance().update(database, connectionSource);
        updateData(oldVersion);
    }

    /**
     * 数据库更新后续操作 如数据转移
     * @param oldVersion
     */
    private void updateData(int oldVersion) {

    }


    public static synchronized void release() {
        if (helper != null) {
            usageCounter.set(1);
            helper.close();
            helper = null;
        }
    }

    @Override
    public void close() {
        if (usageCounter.decrementAndGet() == 0) {
            super.close();
            helper = null;
        }
    }
}
