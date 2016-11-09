//package me.khrystal.android_sqlcipher_ormlite;
//
//import android.content.Context;
//import android.database.sqlite.SQLiteDatabase;
//import android.util.Log;
//
//import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
//import com.j256.ormlite.support.ConnectionSource;
//import com.j256.ormlite.table.TableUtils;
//
//
//import java.sql.SQLException;
//import java.util.concurrent.atomic.AtomicInteger;
//
///**
// * usage:
// * author: kHRYSTAL
// * create time: 16/11/9
// * update time:
// * email: 723526676@qq.com
// */
//
//public class DBHelperNoCipher extends OrmLiteSqliteOpenHelper {
//    // 模拟数据库升级操作
//    private static final int VERSION_USER = 1;
//    private static final int VERSION_USER_ADD_COL = 2;
//    private static final int VERSION_USER_AUTO_INC = 3;
//    private static final int VERSION_USER_ID_AUTO  = 10;
//
//    public static final int VERSION = VERSION_USER_ID_AUTO;
//
//    public static final String TAG = "DBHelper";
//
//    private static final String DB_NAME = "test3.db";
//    private Context mContext;
//    private static DBHelperNoCipher helper = null;
//
//    private static final AtomicInteger usageCounter = new AtomicInteger(0);
//
//    public static DBHelperNoCipher getManager() {
//        return getHelper(MainApplication.APP_CONTEXT);
//    }
//
//    private DBHelperNoCipher(Context context) {
//        super(context, DB_NAME, null, VERSION);
//        mContext = context;
//    }
//
//    private static synchronized DBHelperNoCipher getHelper(Context context) {
//        if (helper == null) {
//            helper = new DBHelperNoCipher(context);
//        }
//        usageCounter.incrementAndGet();
//        return helper;
//    }
//
//
//    @Override
//    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
//        createTable(connectionSource);
//    }
//
//    public void createTable(ConnectionSource cs) {
//        try {
//            TableUtils.createTableIfNotExists(connectionSource, User.class);
//        } catch (SQLException e) {
//            Log.e(TAG, "create table error");
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
//        Log.e("VERSION", "oldVersion:"+ oldVersion +", newVersion:" + newVersion);
//        DBUpdateUtilNoCipher.getInstance().update(database, connectionSource);
//        updateData(oldVersion);
//    }
//
//    /**
//     * 数据库更新后续操作 如数据转移
//     * @param oldVersion
//     */
//    private void updateData(int oldVersion) {
//
//    }
//
//
//    public static synchronized void release() {
//        if (helper != null) {
//            usageCounter.set(1);
//            helper.close();
//            helper = null;
//        }
//    }
//
//    @Override
//    public void close() {
//        if (usageCounter.decrementAndGet() == 0) {
//            super.close();
//            helper = null;
//        }
//    }
//}
