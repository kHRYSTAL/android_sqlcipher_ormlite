package me.khrystal.android_sqlcipher_ormlite;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;

/**
 * usage:
 * author: kHRYSTAL
 * create time: 16/11/8
 * update time:
 * email: 723526676@qq.com
 */

public class UserDao extends BaseDaoImpl<User, Long> {

    protected UserDao(Class<User> dataClass) throws SQLException {
        super(dataClass);
    }

    protected UserDao(ConnectionSource connectionSource, Class<User> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    protected UserDao(ConnectionSource connectionSource, DatabaseTableConfig<User> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }
}
