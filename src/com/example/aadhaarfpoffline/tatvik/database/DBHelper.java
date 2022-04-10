package com.example.aadhaarfpoffline.tatvik.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Base64;
import android.util.Log;
import com.example.aadhaarfpoffline.tatvik.model.VoterDataNewModel;
import java.io.PrintStream;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
/* loaded from: classes2.dex */
public class DBHelper extends SQLiteOpenHelper {
    public static final String Database_Name = "db_BiometricAttendance.db";
    public static final int Database_Version = 1;
    public static final String Key_ID = "_id";
    public Global global;
    public String tbl_registration_master = "tbl_registration_master";
    public String tbl_lock_boothofficer = "tbl_lock_boothofficer";
    protected SQLiteDatabase database = getWritableDatabase();

    public DBHelper(Context context) {
        super(context, Database_Name, (SQLiteDatabase.CursorFactory) null, 1);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase db) {
        Log.w("Create URL", "reading onCreate database");
        LinkedHashMap<String, String> lockCols = new LinkedHashMap<>();
        lockCols.put("FingerTemplate", "blob");
        if (createTableLock(this.tbl_lock_boothofficer, lockCols, db)) {
            Log.w("Create URL lock", "Successful on new database lock");
        } else {
            Log.w("Create URL", "Error on database creation");
        }
        lockCols.clear();
        LinkedHashMap<String, String> cols = new LinkedHashMap<>();
        cols.put("ID", "BIGINT");
        cols.put("DIST_NO", "varchar(100)");
        cols.put("AC_NO", "INT8");
        cols.put("PART_NO", "INT8");
        cols.put("SECTION_NO", "INT8");
        cols.put("SLNOINPART", "varchar(100)");
        cols.put("C_HOUSE_NO", "varchar(100)");
        cols.put("C_HOUSE_NO_V1", "varchar(100)");
        cols.put("FM_NAME_EN", "varchar(255)");
        cols.put("LASTNAME_EN", "varchar(255)");
        cols.put("FM_NAME_V1", "varchar(255)");
        cols.put("LASTNAME_V1", "varchar(255)");
        cols.put("RLN_TYPE", "varchar(100)");
        cols.put("STATUS_TYPE", "varchar(255)");
        cols.put("RLN_L_NM_EN", "varchar(255)");
        cols.put("RLN_FM_NM_V1", "varchar(255)");
        cols.put("RLN_L_NM_V1", "varchar(255)");
        cols.put("EPIC_NO", "varchar(50)");
        cols.put("RLN_FM_NM_EN", "varchar(50)");
        cols.put("GENDER", "varchar(50)");
        cols.put("AGE", "int");
        cols.put("DOB", "text");
        cols.put("EMAIL_ID", "varchar(200)");
        cols.put("MOBILE_NO", "varchar(100)");
        cols.put("ELECTOR_TYPE", "varchar(50)");
        cols.put("BlockID", "INT8");
        cols.put("PanchayatID", "BIGINT");
        cols.put("VillageName", "varchar(255)");
        cols.put("WardNo", "int");
        cols.put("SlNoInWard", "int");
        cols.put("UserId", "varchar(100)");
        cols.put("VOTED", "INT");
        cols.put("FACE_MATCH", "int");
        cols.put("VOTER_IMAGE", "varchar(255)");
        cols.put("VOTER_FINGERPRINT", "varchar(255)");
        cols.put("ID_DOCUMENT_IMAGE", "varchar(255)");
        cols.put("FINGERPRINT_MATCH", "INT");
        cols.put("VOTING_DATE", "text");
        cols.put("AADHAAR_MATCH", "int");
        cols.put("AADHAAR_NO", "varchar(15)");
        cols.put("EnrollTemplate", "blob");
        if (createTable(this.tbl_registration_master, cols, db)) {
            Log.w("Create URL", "Successful on new database");
        } else {
            Log.w("Create URL", "Error on database creation");
        }
        cols.clear();
    }

    public boolean createTable(String tableName, LinkedHashMap<String, String> colums, SQLiteDatabase db) {
        try {
            String cmd = "create table " + tableName + "( _id INTEGER PRIMARY KEY,";
            for (String key : colums.keySet()) {
                cmd = cmd + key + " " + colums.get(key) + ",";
            }
            String cmd2 = (cmd + " UNIQUE('EPIC_NO')") + ");";
            Log.w("Create Table", "query : " + cmd2);
            db.execSQL(cmd2);
            Log.w("Create Table", "Success : " + tableName);
            return true;
        } catch (Exception ex) {
            Log.w("Create Error", ex.toString());
            return false;
        }
    }

    public boolean createTableLock(String tableName, LinkedHashMap<String, String> colums, SQLiteDatabase db) {
        try {
            String cmd = "create table " + tableName + "( _id INTEGER PRIMARY KEY,";
            for (String key : colums.keySet()) {
                cmd = cmd + key + " " + colums.get(key) + ",";
            }
            String cmd2 = cmd.substring(0, cmd.length() - 1) + ");";
            Log.w("Create Table", "query : " + cmd2);
            db.execSQL(cmd2);
            Log.w("Create Table", "Success : " + tableName);
            return true;
        } catch (Exception ex) {
            Log.w("Create Error", ex.toString());
            return false;
        }
    }

    public Cursor getCursor(String tableName, String[] columns, String whereClause, String[] whereArgs, String groupBy, String having, String orderBy) {
        try {
            this.database = getReadableDatabase();
            return this.database.query(tableName, columns, whereClause, whereArgs, groupBy, having, orderBy);
        } catch (Exception ex) {
            Log.w("Error", ex.toString());
            return null;
        }
    }

    public String getText(String tableName, String returnValue, String whereClause, String[] whereArgs) {
        Cursor cur = getCursor(tableName, new String[]{returnValue}, whereClause, whereArgs, null, null, null);
        if (cur.moveToNext()) {
            return cur.getString(0);
        }
        return null;
    }

    public String getMin(String tableName, String returnValue, String whereClause, String[] whereArgs) {
        Cursor cur = getCursor(tableName, new String[]{"MIN(" + returnValue + ")"}, whereClause, whereArgs, null, null, null);
        if (cur.moveToNext()) {
            return cur.getString(0);
        }
        return null;
    }

    public String getMax(String tableName, String returnValue, String whereClause, String[] whereArgs) {
        Cursor cur = getCursor(tableName, new String[]{"MAX(" + returnValue + ")"}, whereClause, whereArgs, null, null, null);
        if (cur.moveToNext()) {
            return cur.getString(0);
        }
        return null;
    }

    public String getSum(String tableName, String returnValue, String whereClause, String[] whereArgs) {
        Cursor cur = getCursor(tableName, new String[]{"SUM(" + returnValue + ")"}, whereClause, whereArgs, null, null, null);
        if (cur.moveToNext()) {
            return cur.getString(0);
        }
        return null;
    }

    public long getCount(String tableName, String whereClause, String[] whereArgs) {
        return (long) getCursor(tableName, new String[]{"*"}, whereClause, whereArgs, null, null, null).getCount();
    }

    public long getColumnCount(String tableName, String[] columns, String whereClause, String[] whereArgs) {
        return (long) getCursor(tableName, columns, whereClause, whereArgs, null, null, null).getColumnCount();
    }

    public long insertData(String tableName, ContentValues values) {
        try {
            this.database = getWritableDatabase();
            return this.database.insert(tableName, null, values);
        } catch (Exception e) {
            PrintStream printStream = System.out;
            printStream.println("insertexception" + e.getMessage());
            return -100;
        }
    }

    public int updateData(String tableName, ContentValues values, String whereClause, String[] whereArgs) {
        this.database = getWritableDatabase();
        return this.database.update(tableName, values, whereClause, whereArgs);
    }

    public int deleteData(String tableName, String whereClause, String[] whereArgs) {
        this.database = getWritableDatabase();
        return this.database.delete(tableName, whereClause, whereArgs);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + this.tbl_registration_master);
        db.execSQL("DROP TABLE IF EXISTS " + this.tbl_lock_boothofficer);
        onCreate(db);
    }

    public long DateToInt(String dd_MM_yyyy) {
        return StringToDate(dd_MM_yyyy).getTime() / 1000;
    }

    public Date IntToDate(Object seconds) {
        return new Date(1000 * Long.parseLong(seconds.toString()));
    }

    public String IntToDateStr(Object seconds) {
        return new SimpleDateFormat("dd-MM-yyyy").format(new Date(1000 * Long.parseLong(seconds.toString())));
    }

    public String SysDateStr() {
        return new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
    }

    public String SysTimeStr() {
        return new SimpleDateFormat("hh:mm a").format(Calendar.getInstance().getTime());
    }

    public Date SysDate() {
        return Calendar.getInstance().getTime();
    }

    public Calendar GetCalendar(String dd_MM_yyyy) {
        String[] arr = dd_MM_yyyy.split("-");
        Calendar cal = Calendar.getInstance();
        try {
            cal.set(Integer.parseInt(arr[2]), Integer.parseInt(arr[1]) - 1, Integer.parseInt(arr[0]), 0, 0, 0);
        } catch (Exception ex) {
            Log.w("GetCalender", ex.toString());
        }
        return cal;
    }

    public Date StringToDate(String dd_MM_yyyy) {
        return GetCalendar(dd_MM_yyyy).getTime();
    }

    public int GetWeekDayInt(String dd_MM_yyyy) {
        return GetCalendar(dd_MM_yyyy).get(7);
    }

    public String WeekDayStr(String dd_MM_yyyy) {
        return new String[]{"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"}[GetWeekDayInt(dd_MM_yyyy) - 1];
    }

    public int GetMonthInt(String dd_MM_yyyy) {
        return GetCalendar(dd_MM_yyyy).get(2) + 1;
    }

    public String GetMonthStr(String dd_MM_yyyy) {
        return new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"}[GetMonthInt(dd_MM_yyyy) - 1];
    }

    public int GetYear(String dd_MM_yyyy) {
        return GetCalendar(dd_MM_yyyy).get(1);
    }

    public String DateToString(Date date) {
        return new SimpleDateFormat("dd-MM-yyyy").format(date);
    }

    public String AddMonth(String dd_MM_yyyy, int months) {
        Calendar cal = GetCalendar(dd_MM_yyyy);
        cal.add(2, months);
        return DateToString(cal.getTime());
    }

    public String AddDay(String dd_MM_yyyy, int days) {
        Calendar cal = GetCalendar(dd_MM_yyyy);
        cal.add(6, days);
        return DateToString(cal.getTime());
    }

    public String AddYear(String dd_MM_yyyy, int years) {
        Calendar cal = GetCalendar(dd_MM_yyyy);
        cal.add(1, years);
        return DateToString(cal.getTime());
    }

    public long TimeToInt(String time) {
        String[] arrTime = time.split(":");
        int sec = 0;
        int hours = Integer.parseInt(arrTime[0]);
        int index = 1;
        int min = Integer.parseInt(arrTime[1].substring(0, 2));
        if (time.toUpperCase().contains("M")) {
            if (arrTime.length > 2) {
                index = 2;
            }
            if (arrTime[index].toUpperCase().contains("P")) {
                hours += 12;
            }
            if (index == 2) {
                sec = Integer.parseInt(arrTime[2].substring(0, 2));
            }
        } else if (arrTime.length > 2) {
            sec = Integer.parseInt(arrTime[2]);
        }
        return Time.valueOf(hours + ":" + min + ":" + sec).getTime();
    }

    public String IntToTime24(Object millisec) {
        return new SimpleDateFormat("HH:mm").format((Date) new Time(Long.parseLong(millisec.toString())));
    }

    public String IntToTime12(Object millisec) {
        return new SimpleDateFormat("hh:mm a").format((Date) new Time(Long.parseLong(millisec.toString())));
    }

    public long getUsersCount() {
        SQLiteDatabase db = getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, this.tbl_registration_master);
        db.close();
        return count;
    }

    public long getBoothOfficerCount() {
        SQLiteDatabase db = getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, this.tbl_lock_boothofficer);
        db.close();
        return count;
    }

    public void clearAllTableData(String tableName) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from " + tableName);
        db.close();
    }

    public void getAllUsers(String tableName) {
        getReadableDatabase();
    }

    public String getCurrentTimeInFormat() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String timenow = formatter.format(date);
        System.out.println(formatter.format(date));
        return timenow;
    }

    public void updateUserIdImage(String voterid, String imagename) {
        ContentValues cv = new ContentValues();
        cv.put("ID_DOCUMENT_IMAGE", imagename);
        this.database = getWritableDatabase();
        this.database.update(this.tbl_registration_master, cv, "EPIC_NO = ?", new String[]{voterid});
    }

    public void updateVotingStatus(String voterid, int voted, String currenttime) {
        ContentValues cv = new ContentValues();
        cv.put("VOTED", Integer.valueOf(voted));
        cv.put("VOTING_DATE", currenttime);
        this.database = getWritableDatabase();
        this.database.update(this.tbl_registration_master, cv, "EPIC_NO = ?", new String[]{voterid});
    }

    public String getfp(String voterid) {
        Cursor c = getReadableDatabase().rawQuery("SELECT  EnrollTemplate FROM " + this.tbl_registration_master + " where EPIC_NO='" + voterid + "'", null);
        return Base64.encodeToString(c.getBlob(c.getColumnIndex("EnrollTemplate")), 0);
    }

    public String getuseridimage(String voterid) {
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM " + this.tbl_registration_master + " where EPIC_NO='" + voterid + "'", null);
        String name = c.getString(c.getColumnIndex("ID_DOCUMENT_IMAGE"));
        if (name == null || name.isEmpty()) {
            return "";
        }
        return name;
    }

    public long getTotalVoters() {
        String str = "SELECT  count(*) FROM " + this.tbl_registration_master;
        return DatabaseUtils.longForQuery(getReadableDatabase(), "SELECT  count(*) FROM " + this.tbl_registration_master, null);
    }

    public long getFingerCount() {
        return DatabaseUtils.longForQuery(getReadableDatabase(), "SELECT  count(*) FROM " + this.tbl_registration_master + " where EnrollTemplate IS NOT NULL AND EnrollTemplate != ''", null);
    }

    public long getIdDocumentCount() {
        return DatabaseUtils.longForQuery(getReadableDatabase(), "SELECT  count(*) FROM " + this.tbl_registration_master + " where ID_DOCUMENT_IMAGE IS NOT NULL AND EnrollTemplate != ''", null);
    }

    public Cursor SingleUserRowByVoterId(String voterid) {
        return getReadableDatabase().rawQuery("SELECT  * FROM " + this.tbl_registration_master + " where EPIC_NO='" + voterid + "'", null);
    }

    public VoterDataNewModel getVoter(String voterid) {
        List<VoterDataNewModel> list = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + this.tbl_registration_master + " where EPIC_NO='" + voterid + "'";
        SQLiteDatabase db = getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    VoterDataNewModel obj = new VoterDataNewModel();
                    obj.setId(cursor.getString(0));
                    obj.setGENDER(cursor.getString(cursor.getColumnIndex("GENDER")));
                    obj.setLASTNAME_V1(cursor.getString(cursor.getColumnIndex("LASTNAME_V1")));
                    obj.setFM_NAME_EN(cursor.getString(cursor.getColumnIndex("FM_NAME_EN")));
                    obj.setLASTNAME_EN(cursor.getString(cursor.getColumnIndex("LASTNAME_EN")));
                    obj.setFM_NAME_V1(cursor.getString(cursor.getColumnIndex("FM_NAME_V1")));
                    obj.setLASTNAME_V1(cursor.getString(cursor.getColumnIndex("LASTNAME_V1")));
                    obj.setBlockID(cursor.getString(cursor.getColumnIndex("BlockID")));
                    obj.setWardNo(cursor.getString(cursor.getColumnIndex("WardNo")));
                    obj.setEPIC_NO(cursor.getString(cursor.getColumnIndex("EPIC_NO")));
                    obj.setAge(cursor.getString(cursor.getColumnIndex("AGE")));
                    obj.setVOTED(cursor.getString(cursor.getColumnIndex("VOTED")));
                    obj.setID_DOCUMENT_IMAGE(cursor.getString(cursor.getColumnIndex("ID_DOCUMENT_IMAGE")));
                    list.add(obj);
                } while (cursor.moveToNext());
                try {
                    cursor.close();
                } catch (Exception e) {
                }
                return list.get(0);
            }
            cursor.close();
            return list.get(0);
        } finally {
            try {
                db.close();
            } catch (Exception e2) {
            }
        }
    }

    public List<VoterDataNewModel> getAllElements() {
        List<VoterDataNewModel> list = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + this.tbl_registration_master;
        SQLiteDatabase db = getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    VoterDataNewModel obj = new VoterDataNewModel();
                    obj.setId(cursor.getString(0));
                    obj.setGENDER(cursor.getString(cursor.getColumnIndex("GENDER")));
                    obj.setLASTNAME_V1(cursor.getString(cursor.getColumnIndex("LASTNAME_V1")));
                    obj.setFM_NAME_EN(cursor.getString(cursor.getColumnIndex("FM_NAME_EN")));
                    obj.setLASTNAME_EN(cursor.getString(cursor.getColumnIndex("LASTNAME_EN")));
                    obj.setFM_NAME_V1(cursor.getString(cursor.getColumnIndex("FM_NAME_V1")));
                    obj.setLASTNAME_V1(cursor.getString(cursor.getColumnIndex("LASTNAME_V1")));
                    obj.setBlockID(cursor.getString(cursor.getColumnIndex("BlockID")));
                    obj.setWardNo(cursor.getString(cursor.getColumnIndex("WardNo")));
                    obj.setEPIC_NO(cursor.getString(cursor.getColumnIndex("EPIC_NO")));
                    obj.setAge(cursor.getString(cursor.getColumnIndex("AGE")));
                    obj.setVOTED(cursor.getString(cursor.getColumnIndex("VOTED")));
                    obj.setID_DOCUMENT_IMAGE(cursor.getString(cursor.getColumnIndex("ID_DOCUMENT_IMAGE")));
                    obj.setSlNoInWard(cursor.getString(cursor.getColumnIndex("SlNoInWard")));
                    list.add(obj);
                } while (cursor.moveToNext());
                try {
                    cursor.close();
                } catch (Exception e) {
                }
                return list;
            }
            cursor.close();
            return list;
        } finally {
            try {
                db.close();
            } catch (Exception e2) {
            }
        }
    }

    public void updateFingerprintTemplate(String voterid, byte[] finger_template) {
        ContentValues cv = new ContentValues();
        cv.put("EnrollTemplate", finger_template);
        this.database.update(this.tbl_registration_master, cv, "EPIC_NO = ?", new String[]{voterid});
    }

    public void updateVoterIdImage(String voterid, String voteridImageName) {
        ContentValues cv = new ContentValues();
        cv.put("ID_DOCUMENT_IMAGE", voteridImageName);
        this.database.update(this.tbl_registration_master, cv, "EPIC_NO = ?", new String[]{voterid});
    }

    public void clearFingerprint(String voterid) {
        ContentValues cv = new ContentValues();
        cv.put("EnrollTemplate", "");
        this.database.update(this.tbl_registration_master, cv, "EPIC_NO = ?", new String[]{voterid});
    }

    public Cursor fpcompare(String voterid) {
        return getReadableDatabase().rawQuery("SELECT  * FROM " + this.tbl_registration_master + " where EnrollTemplate IS NOT NULL AND EnrollTemplate != ''", null);
    }

    public Cursor fpcompareLock() {
        return getReadableDatabase().rawQuery("SELECT  * FROM " + this.tbl_lock_boothofficer + " where FingerTemplate  IS NOT NULL", null);
    }
}
