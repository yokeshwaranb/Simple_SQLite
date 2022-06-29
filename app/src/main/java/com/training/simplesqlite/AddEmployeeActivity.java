package com.training.simplesqlite;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddEmployeeActivity extends AppCompatActivity {

    private Calendar myCalendar = Calendar.getInstance();
    private EmployeeDbHelper employeeDbHelper;

    private EditText etDOB;
    private EditText etEmpName;
    private EditText etDesignation;
    private Button btnSave;
    private Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        etDOB = findViewById(R.id.etDOB);
        etEmpName = findViewById(R.id.etEmpName);
        etDesignation = findViewById(R.id.etDesignation);
        btnSave = findViewById(R.id.bSave);
        btnCancel = findViewById(R.id.bCancel);

        employeeDbHelper = new EmployeeDbHelper(this);

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                etDOB.setText(getFormattedDate(myCalendar.getTimeInMillis()));
            }
        };

        etDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpCalender(dateSetListener);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEmployee();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void saveEmployee() {
        boolean isValid = true;

        if (etEmpName.getText().toString().isEmpty()) {
            isValid = false;
            etEmpName.setError("Required Field");
        }

        if (etDesignation.getText().toString().isEmpty()) {
            isValid = false;
            etDesignation.setError("Required Field");
        }

        if (isValid) {
            String name = etEmpName.getText().toString();
            String designation = etDesignation.getText().toString();
            long dob = myCalendar.getTimeInMillis();

            // Gets the data repository in write mode
            SQLiteDatabase db = employeeDbHelper.getWritableDatabase();

            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(EmployeeDbContract.EmployeeEntry.COLUMN_NAME, name);
            values.put(EmployeeDbContract.EmployeeEntry.COLUMN_DESIGNATION, designation);
            values.put(EmployeeDbContract.EmployeeEntry.COLUMN_DOB, dob);

            // Insert the new row, returning the primary key value of the new row
            long result = db.insert(EmployeeDbContract.EmployeeEntry.TABLE_NAME, null, values);

            setResult(RESULT_OK, new Intent());

            Toast.makeText(getApplicationContext(), "Employee Added", Toast.LENGTH_SHORT).show();

            finish();
        }
    }

    private void setUpCalender(DatePickerDialog.OnDateSetListener dateSetListener) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getApplicationContext(),
                dateSetListener,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private String getFormattedDate(long timeInMillis) {
        SimpleDateFormat sdf = new SimpleDateFormat("d MMM, yyyy", Locale.getDefault());
        String str = sdf.format(timeInMillis);
        if (str != null && !str.isEmpty()) {
            return str;
        }
        return "Not Found";
    }
}