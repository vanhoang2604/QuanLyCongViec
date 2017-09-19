package com.example.admin.quanlycongviec;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextView txtDate, txtTime;
    private EditText edtCv, edtNd;
    private Button btnDate, btnTime, btnAdd;

    ArrayList<JobWeek> arrJob = new ArrayList<JobWeek>();
    ArrayAdapter<JobWeek> adapter = null;
    ListView lvCv;
    Calendar cal;
    Date dateFinish;
    Date hourFinish;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getFormWidgets();
        getDefaultInfor();
        addEventFormWidgets();
    }

    public void getFormWidgets() {
        txtDate = (TextView) findViewById(R.id.txtdate);
        txtTime = (TextView) findViewById(R.id.txttime);
        edtCv = (EditText) findViewById(R.id.edtcongviec);
        edtNd = (EditText) findViewById(R.id.edtnoidung);
        btnDate = (Button) findViewById(R.id.btndate);
        btnTime = (Button) findViewById(R.id.btntime);
        btnAdd = (Button) findViewById(R.id.btncongviec);
        lvCv = (ListView) findViewById(R.id.lvcongviec);
        adapter = new ArrayAdapter<JobWeek>(this, android.R.layout.simple_list_item_1, arrJob);
        lvCv.setAdapter(adapter);
    }

    public void getDefaultInfor() {
        cal = Calendar.getInstance();
        SimpleDateFormat dft = null;

        dft = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String strDate = dft.format(cal.getTime());
        txtDate.setText(strDate);

        dft = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        String strTime = dft.format(cal.getTime());
        txtTime.setText(strTime);

        dft = new SimpleDateFormat("HH:mm", Locale.getDefault());
        txtTime.setTag(dft.format(cal.getTime()));
        edtCv.requestFocus();

        dateFinish = cal.getTime();
        hourFinish = cal.getTime();
    }

    public void addEventFormWidgets() {
        btnDate.setOnClickListener(new MyButtonEvent());
        btnTime.setOnClickListener(new MyButtonEvent());
        btnAdd.setOnClickListener(new MyButtonEvent());
        lvCv.setOnItemClickListener(new MyListViewEvent());
        lvCv.setOnItemLongClickListener(new MyListViewEvent());
    }

    private class MyButtonEvent implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btndate:
                    showDatePickerDialog();
                    break;
                case R.id.btntime:
                    showTimePickerDialog();
                    break;
                case R.id.btncongviec:
                    processAddJob();
                    break;
            }
        }
    }

    private class MyListViewEvent implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            Toast.makeText(MainActivity.this,
                    arrJob.get(arg2).getDesciption(),
                    Toast.LENGTH_LONG).show();

        }

        @Override
        public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            arrJob.remove(arg2);
            adapter.notifyDataSetChanged();
            return false;
        }
    }

    private void showDatePickerDialog() {
        DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                txtDate.setText((dayOfMonth) + "/" + (monthOfYear + 1) + "/" + year);

                cal.set(year, monthOfYear, dayOfMonth);
                dateFinish = cal.getTime();
            }
        };
        String s = txtDate.getText() + "";
        String strArrtmp[] = s.split("/");
        int ngay = Integer.parseInt(strArrtmp[0]);
        int thang = Integer.parseInt(strArrtmp[1]) - 1;
        int nam = Integer.parseInt(strArrtmp[2]);
        DatePickerDialog pic = new DatePickerDialog(
                MainActivity.this,
                callback, nam, thang, ngay);
        pic.setTitle("Chọn ngày hoàn thành");
        pic.show();
    }

    private void showTimePickerDialog() {
        TimePickerDialog.OnTimeSetListener callback = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String s = hourOfDay + ":" + minute;
                int hourTam = hourOfDay;
                if (hourTam > 12)
                    hourTam = hourTam - 12;
                txtTime.setText(hourTam + ":" + minute + (hourOfDay > 12 ? " PM" : " AM"));
                txtTime.setTag(s);

                cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                cal.set(Calendar.MINUTE, minute);
                hourFinish = cal.getTime();
            }
        };
        String s = txtTime.getTag() + "";
        String strArr[] = s.split(":");
        int gio = Integer.parseInt(strArr[0]);
        int phut = Integer.parseInt(strArr[1]);
        TimePickerDialog time = new TimePickerDialog(
                MainActivity.this,
                callback, gio, phut, true);
        time.setTitle("Chọn giờ hoàn thành");
        time.show();
    }

    private void processAddJob() {
        String title = edtCv.getText() + "";
        String description = edtNd.getText() + "";
        JobWeek job = new JobWeek(title, description, dateFinish, hourFinish);
        arrJob.add(job);
        adapter.notifyDataSetChanged();
        edtCv.setText("");
        edtNd.setText("");
        edtCv.requestFocus();
    }
}
