package com.example.wwl.myrxjavademo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

public class RxFlatMapActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mET;
    private TextView mTV;
    private Button mBT;
    private SchoolClass[] mSchoolClasses = new SchoolClass[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_flat_map);

        mET = (EditText) findViewById(R.id.et_rx_map);
        mTV = (TextView) findViewById(R.id.tv_rx_map);
        mBT = (Button) findViewById(R.id.bt_rx_map);

        mBT.setOnClickListener(this);
        //模仿数据
        initData();

    }

    private void initData() {

        Student[] students = new Student[5];
        for (int i = 0; i < 5; i++) {
            Student s = new Student("张三" + i, "20" + i);
            students[i] = s;
        }
        mSchoolClasses[0] = new SchoolClass(students);

        Student[] students2 = new Student[5];
        for (int i = 0; i < 5; i++) {
            Student s = new Student("李四" + i, "30" + i);
            students2[i] = s;
        }
        mSchoolClasses[1] = new SchoolClass(students2);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.bt_rx_map){
            if(mTV.getText().toString().length() > 0){
                mTV.setText("");
            }
            start();
        }
    }

    private void start() {
        Observable.from(mSchoolClasses)
                .flatMap(new Func1<SchoolClass, Observable<Student>>() {

                    @Override
                    public Observable<Student> call(SchoolClass schoolClass) {
                        return Observable.from(schoolClass.getStud());
                    }
                })
                .subscribe(new Action1<Student>() {
                    @Override
                    public void call(Student student) {
                        mTV.append("打印单个学生信息：\n");
                        mTV.append("name:"+student.name + "  age:" + student.age + "\n\n");
                    }
                });


    }


    class Student{
        String name;
        String age;

        public Student(String name, String age) {
            this.name = name;
            this.age = age;
        }
    }

    class SchoolClass {
        Student[] stud;

        public SchoolClass(Student[] stud) {
            this.stud = stud;
        }

        public Student[] getStud() {
            return stud;
        }
    }


}
