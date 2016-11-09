package me.khrystal.android_sqlcipher_ormlite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.table.TableUtils;

import net.sqlcipher.database.SQLiteDatabase;

import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {

    DBHelper mDBHelper;
    UserDao mUserDao;

    EditText etUsername, etUserPasswd, etId;
    TextView tvSql;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDBHelper = new DBHelper(this);
        try {
            mUserDao = mDBHelper.getDao(User.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tvSql = (TextView) findViewById(R.id.tvSql);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etUserPasswd = (EditText) findViewById(R.id.etPasswd);
        etId = (EditText) findViewById(R.id.etQuery);

    }

    public void onSave(View view) {
        String name = etUsername.getText().toString().trim();
        String passwd = etUserPasswd.getText().toString().trim();
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(passwd)) {
            User user = new User();
            user.username = name;
            user.password = passwd;
            try {
                mUserDao.create(user);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void onQuery(View view) {
        String strId = etId.getText().toString().trim();
        if (!TextUtils.isEmpty(strId)) {
            Long id = new Long(strId);
            User user = null;
            try {
                user = mUserDao.queryForId(id);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (user != null)
                tvSql.setText("Name:" + user.username + ",Passwd:" + user.password);
        }
    }
}
