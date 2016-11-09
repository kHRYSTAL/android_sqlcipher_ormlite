package me.khrystal.android_sqlcipher_ormlite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {


    EditText etUsername, etUserPasswd, etId;
    TextView tvSql;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        SQLiteDatabase.loadLibs(this);
        setContentView(R.layout.activity_main);
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
            user.clazz = 9;
            user.age = 22;
            try {
                DBHelper.getManager().getDao(User.class).create(user);
                DBHelper.getManager().close();
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
                UserDao userDao = DBHelper.getManager().getDao(User.class);
                user = userDao.queryForId(id);
                DBHelper.getManager().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (user != null)
                tvSql.setText("Name:" + user.username + ",Passwd:" + user.password + ", Gender:" + user.gender
                    + "class:"  + user.clazz + "age:" + user.age);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DBHelper.getManager().close();
    }
}
