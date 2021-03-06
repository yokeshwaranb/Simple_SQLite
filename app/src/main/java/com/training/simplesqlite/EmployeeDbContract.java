package com.training.simplesqlite;

import android.provider.BaseColumns;

public class EmployeeDbContract {

    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private EmployeeDbContract() {}

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Employee.db";

    /* Inner class that defines the table contents */
    public static class EmployeeEntry implements BaseColumns {
        public static final String TABLE_NAME = "employee";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DOB = "dob";
        public static final String COLUMN_DESIGNATION = "designation";
    }

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + EmployeeEntry.TABLE_NAME + " (" +
                    EmployeeEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    EmployeeEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                    EmployeeEntry.COLUMN_DOB + " INTEGER NOT NULL, " +
                    EmployeeEntry.COLUMN_DESIGNATION + " TEXT NOT NULL)";

    public static final String SQL_DROP_TABLE =
            "DROP TABLE IF EXISTS " + EmployeeEntry.TABLE_NAME;
}
