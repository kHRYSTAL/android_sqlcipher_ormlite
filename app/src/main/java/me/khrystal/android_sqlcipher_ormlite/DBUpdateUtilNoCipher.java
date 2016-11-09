//package me.khrystal.android_sqlcipher_ormlite;
//
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//
//import com.j256.ormlite.support.ConnectionSource;
//
//
//import java.util.ArrayList;
//import java.util.HashMap;
//
///**
// * usage:
// * author: kHRYSTAL
// * create time: 16/11/9
// * update time:
// * email: 723526676@qq.com
// */
//
//public class DBUpdateUtilNoCipher {
//
//    private static DBUpdateUtilNoCipher dbUpdateUtil;
//
//    private DBUpdateUtilNoCipher() {
//    }
//
//    public static DBUpdateUtilNoCipher getInstance() {
//        if (dbUpdateUtil == null) {
//            dbUpdateUtil = new DBUpdateUtilNoCipher();
//        }
//        return dbUpdateUtil;
//    }
//
//    public void update(SQLiteDatabase db, ConnectionSource connection) {
//        getAllColumsByOrg(db);
//        // 创建临时表,创建方式是将原来的表名前面加上temp_
//        for (String tableName : dbHashMap.keySet()) {
//            copyTmp(db, tableName);
//        }
//        // 创建新表，也就是和原来表的名字一样，现在数据库中就有了新表和临时表
//        // 而数据库升级的时候，新表有可能会添加新的字段什么的
//        DBHelperNoCipher.getManager().createTable(connection);
//        // 这时候数据库中的表发生了变化，所以再获取一次所有的表和字段
//        getAllColumsByOrg(db);
//        // 将临时表中的数据都插入对应的表和记录中
//        for (String tableName : dbHashMap.keySet()) {
//            if (tableName.startsWith("temp_")) {
//                continue;
//            }
//            insertDataBack(db, tableName);
//        }
//        // 删除所有的临时表，数据库更新和数据迁移完成
//        for (String tableName : dbHashMap.keySet()) {
//            if (tableName.startsWith("temp_")) {
//                dropTable(db, tableName);
//            }
//        }
//    }
//
//    // 将数据从临时表中倒入到新表中
//    // 数据库字段名不能更改，要么加要么减
//    public void insertDataBack(SQLiteDatabase db, String tableName) {
//        ArrayList<String> newList = dbHashMap.get(tableName);
//        ArrayList<String> orgList = dbHashMap.get("temp_" + tableName);
//        if (newList == null || orgList == null) {
//            return;
//        }
//        int dcount = newList.size() - orgList.size();
//        // 字段数量不变时，把temp表还原不变
//        if (dcount == 0) {
//            dropTable(db, tableName);
//            db.execSQL("ALTER TABLE " + "temp_" + tableName + " RENAME TO "
//                    + tableName);
//            return;
//        }
//        String sqlColStr = "";
//        ArrayList<String> tempList = null;
//        // 字段多或少的话，选择最少字段的列表进行选择插入
//        if (dcount > 0) {
//            tempList = orgList;
//        } else if (dcount < 0) {
//            tempList = newList;
//        }
//        for (int i = 0; i < tempList.size(); i++) {
//            sqlColStr += "`" + tempList.get(i) + "`,";// 加了单引号
//        }
//        if (sqlColStr.length() > 0) {
//            sqlColStr = sqlColStr.substring(0, sqlColStr.length() - 1);
//            sqlColStr += " ";
//        }
//        db.execSQL("INSERT INTO " + tableName + "(" + sqlColStr + ")"
//                + " SELECT " + sqlColStr + " FROM " + "temp_" + tableName);
//
//    }
//
//    // 创建临时表
//    public void copyTmp(SQLiteDatabase db, String orgTableName) {
//        String sql = "ALTER TABLE " + orgTableName + " RENAME TO " + "temp_"
//                + orgTableName;
//        db.execSQL(sql);
//    }
//
//    // 获取此时此刻数据库中的所有的表名和对应的字段名
//    public void getAllColumsByOrg(SQLiteDatabase db) {
//        HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
//        Cursor tableCursor = db
//                .rawQuery(
//                        "select name from sqlite_master where type='table' order by name;",
//                        null);
//        while (tableCursor.moveToNext()) {
//            String table = tableCursor.getString(0);
//            if (!allTableList.contains(table)) {
//                continue;
//            }
//            Cursor colCursor = db.rawQuery("PRAGMA table_info(" + table + ")",
//                    null);
//            ArrayList<String> colList = new ArrayList<String>();
//            while (colCursor.moveToNext()) {
//                String colName = colCursor.getString(colCursor
//                        .getColumnIndex("name"));
//                colList.add(colName);
//            }
//            map.put(table, colList);
//        }
//        dbHashMap = map;
//    }
//
//    // 删除临时表， 如果不存在，就算了
//    public void dropTable(SQLiteDatabase db, String tableName) {
//        String sql = "drop table if exists " + tableName;
//        db.execSQL(sql);
//    }
//
//    // 存储在升级数据库过程中的所有的表名和字段名的映射关系
//    private HashMap<String, ArrayList<String>> dbHashMap;
//    // 初始化在升级数据库过程中的所有的表名和临时表名
//    public static ArrayList<String> allTableList = new ArrayList<String>();
//
//    static {
//        // 加入要创建的表名
//        allTableList.add(User.TAB_NAME);
//        // 加入升级时要创建的临时表表名
//        allTableList.add("temp_" + User.TAB_NAME);
//    }
//}
