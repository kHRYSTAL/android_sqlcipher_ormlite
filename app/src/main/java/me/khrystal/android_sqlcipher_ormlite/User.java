package me.khrystal.android_sqlcipher_ormlite;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * usage:
 * author: kHRYSTAL
 * create time: 16/11/8
 * update time:
 * email: 723526676@qq.com
 */

@DatabaseTable(daoClass = UserDao.class, tableName = User.TAB_NAME)
public class User {

    public static final String TAB_NAME = "usertable";

    @DatabaseField(columnName = "uid", generatedId = true)
    public long id;

    @DatabaseField(columnName = "username")
    public String username;

    @DatabaseField(columnName = "password")
    public String password;

    /** 新增字段 性别*/
    @DatabaseField(columnName = "gender", defaultValue = "1")
    public int gender;

    @DatabaseField(columnName = "class")
    public int clazz;

    @DatabaseField(columnName = "age")
    public int age;

}
