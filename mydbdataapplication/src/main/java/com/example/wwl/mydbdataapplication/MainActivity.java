package com.example.wwl.mydbdataapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button createDatabase = (Button) findViewById(R.id.create_database);
        Button addData = (Button) findViewById(R.id.add_data);
        Button updateData = (Button) findViewById(R.id.update_data);
        Button deleteData = (Button) findViewById(R.id.delete_data);
        Button queryData = (Button) findViewById(R.id.query_data);
        //一、点击创建数据库
        createDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Connector.getDatabase();
            }
        });
        //二、点击添加数据
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book = new Book();
                book.setName("The Da Vinci Code");
                book.setAuthor("Dan Brown");
                book.setPages(456);
                book.setPrice(16.99);
                book.setPress("Unknow");
                book.save();
            }
        });
        //三、点击更新数据
        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1：有限制
                Book book = new Book();
                book.setName("The Lost Symbol");
                book.setAuthor("Dan Brown");
                book.setPages(510);
                book.setPrice(19.95);
                book.save();
                book.setPrice(19.95);
                book.save();

                //2:
                Book book2 = new Book();
                book2.setPrice(14.95);
                book2.setPress("Anchor");
                book2.updateAll("name = ? and author = ？", "The Lost Symbol", "Dan Brown");

                //3、更新至默认值
                Book book3 = new Book();
                book3.setToDefault("pages");
                book3.updateAll();

            }
        });
        //四、点击删除数据
        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataSupport.deleteAll(Book.class, "price < ？", "15");
            }
        });
        //五、点击查询数据
        queryData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1、查询所有数据
                List<Book> books = DataSupport.findAll(Book.class);
                for (Book book : books) {
                    Log.d(TAG, "onClick: " + book.getAuthor());
                    Log.d(TAG, "onClick: " + book.getName());
                    Log.d(TAG, "onClick: " + book.getPress());
                    Log.d(TAG, "onClick: " + book.getId());
                    Log.d(TAG, "onClick: " + book.getPages());
                    Log.d(TAG, "onClick: " + book.getPrice() );

                }
                //2、查询第一条数据
                Book firstBook = DataSupport.findFirst(Book.class);
                //3、查询最后一条数据
                Book lastBook = DataSupport.findLast(Book.class);
                //4、指定查哪几列数据
                List<Book> books1 = DataSupport.select("name", "author").find(Book.class);
                //5、where约束查询
                List<Book> books2 = DataSupport.where("pages > ？", "400").find(Book.class);
                //6、指定结果的排序查询order
                // DESC 表示按倒序排序(即：从大到小排序)  ACS   表示按正序排序(即：从小到大排序)
                List<Book> books3 = DataSupport.order("price desc").find(Book.class);
                //7、limit用于限制查询表中的数据量，如下，只查询表中前三条
                List<Book> books4 = DataSupport.limit(3).find(Book.class);

                //支持原生查询;
                DataSupport.findBySQL("select * from Booke where pages > ? and price < ?", "400", "20");

            }
        });

    }
}
