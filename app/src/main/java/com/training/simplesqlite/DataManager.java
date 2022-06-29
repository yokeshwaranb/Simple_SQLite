package com.training.simplesqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class DataManager {

    private static final String TAG = "MyDataManager";

    public static ArrayList<Employee> fetchAllEmployees(EmployeeDbHelper employeeDbHelper) {
        ArrayList<Employee> employees = new ArrayList<>();

        SQLiteDatabase sqLiteDb = employeeDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] columns = {
                EmployeeDbContract.EmployeeEntry.COLUMN_ID,
                EmployeeDbContract.EmployeeEntry.COLUMN_NAME,
                EmployeeDbContract.EmployeeEntry.COLUMN_DOB,
                EmployeeDbContract.EmployeeEntry.COLUMN_DESIGNATION
        };

        Cursor resultCursor = sqLiteDb.query(
                EmployeeDbContract.EmployeeEntry.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                null
        );

        int idPos = resultCursor.getColumnIndex(EmployeeDbContract.EmployeeEntry.COLUMN_ID);
        int namePos = resultCursor.getColumnIndex(EmployeeDbContract.EmployeeEntry.COLUMN_NAME);
        int dobPos = resultCursor.getColumnIndex(EmployeeDbContract.EmployeeEntry.COLUMN_DOB);
        int designationPos = resultCursor.getColumnIndex(EmployeeDbContract.EmployeeEntry.COLUMN_DESIGNATION);

        while (resultCursor.moveToNext()) {
            int id = resultCursor.getInt(idPos);
            String name = resultCursor.getString(namePos);
            Long dob = resultCursor.getLong(dobPos);
            String designation = resultCursor.getString(designationPos);

            Employee employee = new Employee(id, name, dob, designation);
            Log.d(TAG, " Employee: " + employee);
            employees.add(employee);
        }

        resultCursor.close();

        return employees;
    }

    public static long insertEmployee(EmployeeDbHelper employeeDbHelper, String name, long dob, String designation) {
        // Gets the data repository in write mode
        SQLiteDatabase db = employeeDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(EmployeeDbContract.EmployeeEntry.COLUMN_NAME, name);
        values.put(EmployeeDbContract.EmployeeEntry.COLUMN_DESIGNATION, designation);
        values.put(EmployeeDbContract.EmployeeEntry.COLUMN_DOB, dob);

        // Insert the new row, returning the primary key value of the new row
        long resultId = db.insert(EmployeeDbContract.EmployeeEntry.TABLE_NAME, null, values);

        return resultId;
    }

    public static Employee fetchEmployee(EmployeeDbHelper employeeDbHelper, long empId) {
        Log.d(TAG, "fetching Employee");
        SQLiteDatabase sqLiteDb = employeeDbHelper.getReadableDatabase();
        Employee employee = null;

        String[] columns = {
                EmployeeDbContract.EmployeeEntry.COLUMN_ID,
                EmployeeDbContract.EmployeeEntry.COLUMN_NAME,
                EmployeeDbContract.EmployeeEntry.COLUMN_DOB,
                EmployeeDbContract.EmployeeEntry.COLUMN_DESIGNATION,
        };

        // Filter results WHERE "id" = '3'
        String selection = EmployeeDbContract.EmployeeEntry.COLUMN_ID + " LIKE ? ";
        String[] selectionArgs = {String.valueOf(empId)};

        Cursor resultCursor = sqLiteDb.query(
                EmployeeDbContract.EmployeeEntry.TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);

        int idPos = resultCursor.getColumnIndex(EmployeeDbContract.EmployeeEntry.COLUMN_ID);
        int namePos = resultCursor.getColumnIndex(EmployeeDbContract.EmployeeEntry.COLUMN_NAME);
        int dobPos = resultCursor.getColumnIndex(EmployeeDbContract.EmployeeEntry.COLUMN_DOB);
        int designationPos = resultCursor.getColumnIndex(EmployeeDbContract.EmployeeEntry.COLUMN_DESIGNATION);

        if (resultCursor.getCount() == 0) {
            Log.d(TAG, empId + " id is not present in DB");
        }
        else if (resultCursor.getCount() > 1) {
            Log.d(TAG, "There are employees matching with this id " + empId);
        }
        else {
            while (resultCursor.moveToNext()) {
                int id = resultCursor.getInt(idPos);
                String name = resultCursor.getString(namePos);
                Long dob = resultCursor.getLong(dobPos);
                String designation = resultCursor.getString(designationPos);
                employee = new Employee(id, name, dob, designation);
            }
        }
        Log.d(TAG, "Employee: " + employee);
        resultCursor.close();
        return employee;

    }
}
