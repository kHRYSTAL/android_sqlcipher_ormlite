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

@DatabaseTable(daoClass = UserDao.class, tableName = "usertable")
public class User {

    @DatabaseField(columnName = "userId", id = true)
    public long id;

    @DatabaseField(columnName = "username")
    public String username;

    @DatabaseField(columnName = "password")
    public String password;
}
