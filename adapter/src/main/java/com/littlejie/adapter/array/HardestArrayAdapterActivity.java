package com.littlejie.adapter.array;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.littlejie.adapter.R;

import java.util.ArrayList;
import java.util.List;

public class HardestArrayAdapterActivity extends Activity {

    private ListView mLv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hardest_array_adapter);

        mLv = (ListView) findViewById(R.id.lv);
        mLv.setAdapter(new MyArrayAdapter(this, generateData(10)));
    }

    private List<Student> generateData(int num) {
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            Student student = new Student();
            student.setName("学生 " + i);
            student.setGender(i % 2 == 0 ? "男" : "女");
            student.setScore(String.valueOf(100 - i));
            students.add(student);
        }
        return students;
    }

    /**
     * 自定义ArrayAdapter,重写getView(int, View, ViewGroup)方法
     */
    private class MyArrayAdapter extends ArrayAdapter<Student> {

        private List<Student> mStudents;

        public MyArrayAdapter(Context context, List<Student> objects) {
            super(context, 0, objects);
            mStudents = objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_hardest_list_item, null);
            TextView tvName = (TextView) convertView.findViewById(R.id.tv_name);
            TextView tvGender = (TextView) convertView.findViewById(R.id.tv_gender);
            TextView tvScore = (TextView) convertView.findViewById(R.id.tv_score);

            Student student = mStudents.get(position);
            tvName.setText("姓名:" + student.getName());
            tvGender.setText("性别:" + student.getGender());
            tvScore.setText("成绩:" + student.getScore());
            return convertView;
        }
    }

    /**
     * 定义学生对象,存放姓名、性别、成绩
     */
    private class Student {
        private String name;
        private String gender;
        private String score;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        @Override
        public String toString() {
            return "姓名:" + name + "\n性别:" + gender + "\n成绩:" + score;
        }
    }
}
