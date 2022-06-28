package com.training.simplesqlite;

import android.provider.BaseColumns;

public class EmployeeDbContract {

    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private EmployeeDbContract() {}

    /* Inner class that defines the table contents */
    public static class EmployeeEntry implements BaseColumns {
        public static final String TABLE_NAME = "employee";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DOB = "dob";
        public static final String COLUMN_DESIGNATION = "designation";
    }
}
