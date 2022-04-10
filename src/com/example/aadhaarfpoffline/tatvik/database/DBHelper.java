package com.example.aadhaarfpoffline.tatvik.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;
import com.example.aadhaarfpoffline.tatvik.UserAuth;
import com.example.aadhaarfpoffline.tatvik.model.VoterDataNewModel;
import com.example.aadhaarfpoffline.tatvik.model.VotingHistoryModel;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
/* loaded from: classes2.dex */
public class DBHelper extends SQLiteOpenHelper {
    public static final String Database_Name = "db_BiometricAttendance.db";
    public static final int Database_Version = 11;
    public static final String Key_ID = "_id";
    public Global global;
    private Context mContext;
    public String tbl_registration_master = "tbl_registration_master";
    public String tbl_lock_boothofficer = "tbl_lock_boothofficer";
    public String tbl_transaction = "tbl_transaction";
    protected SQLiteDatabase database = getWritableDatabase();

    public DBHelper(Context context) {
        super(context, Database_Name, (SQLiteDatabase.CursorFactory) null, 11);
        this.mContext = context;
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
        LinkedHashMap<String, String> tranTableCols = new LinkedHashMap<>();
        tranTableCols.put("FingerTemplate", "blob");
        tranTableCols.put("EPIC_NO", "varchar(50)");
        tranTableCols.put("DIST_NO", "varchar(100)");
        tranTableCols.put("BlockID", "INT8");
        tranTableCols.put("PanchayatID", "BIGINT");
        tranTableCols.put("VOTED", "INT DEFAULT 0");
        tranTableCols.put("ID_DOCUMENT_IMAGE", "varchar(255)");
        tranTableCols.put("VOTING_DATE", "text");
        tranTableCols.put("AADHAAR_MATCH", "int");
        tranTableCols.put("AADHAAR_NO", "varchar(15)");
        tranTableCols.put("SlNoInWard", "int");
        tranTableCols.put("GENDER", "varchar(50)");
        tranTableCols.put("AGE", "int");
        tranTableCols.put("SYNCED", "int DEFAULT 0");
        tranTableCols.put("USER_ID", "varchar(255) DEFAULT ''");
        tranTableCols.put("VOTING_TYPE", "varchar(30) DEFAULT ''");
        tranTableCols.put("IMAGE_SYNCED", "int DEFAULT 0");
        tranTableCols.put("MATCHED_ID_DOCUMENT_IMAGE", "varchar(255) DEFAULT ''");
        tranTableCols.put("MATCHED_USER_ID", "varchar(255) DEFAULT ''");
        tranTableCols.put("MATCHED_IMAGE_SYNCED", "int DEFAULT 0");
        tranTableCols.put("THUMBNAIL_ID_DOCUMENT_IMAGE", "varchar(255) DEFAULT ''");
        tranTableCols.put("THUMBNAIL_IMAGE_SYNCED", "int DEFAULT 0");
        if (createTransactionTable(this.tbl_transaction, tranTableCols, db)) {
            Log.w("Create Transaction Table", "Successful on transtable new database lock");
        } else {
            Log.w("Create Transaction Table", "Error on transtable database creation");
        }
        tranTableCols.clear();
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
        cols.put("BoothNo", "int");
        cols.put("USER_ID", "varchar(255) DEFAULT ''");
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
            String cmd2 = (cmd + " UNIQUE('USER_ID')") + ");";
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

    public boolean createTransactionTable(String tableName, LinkedHashMap<String, String> colums, SQLiteDatabase db) {
        try {
            String cmd = "create table " + tableName + "( _id INTEGER PRIMARY KEY AUTOINCREMENT,";
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
        db.execSQL("DROP TABLE IF EXISTS " + this.tbl_transaction);
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

    public long getTransTableCount() {
        SQLiteDatabase db = getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, this.tbl_transaction);
        db.close();
        return count;
    }

    public long getTransTableCountForAPanchayatBooth(String panchayatId, String boothNo) {
        return (long) getReadableDatabase().rawQuery("SELECT  COUNT(_id) FROM " + this.tbl_registration_master + " where PanchayatID='" + panchayatId + "' and BoothNo='" + boothNo + "'", null).getCount();
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

    public void updateVOTEDByUSER_ID_Maintable(String user_id, int voted) {
        ContentValues cv = new ContentValues();
        cv.put("VOTED", Integer.valueOf(voted));
        this.database = getWritableDatabase();
        this.database.update(this.tbl_registration_master, cv, "USER_ID = ?", new String[]{user_id});
    }

    public void updateVotingStatus(String voterid, int voted, String currenttime) {
        ContentValues cv = new ContentValues();
        cv.put("VOTED", Integer.valueOf(voted));
        this.database = getWritableDatabase();
        this.database.update(this.tbl_registration_master, cv, "EPIC_NO = ?", new String[]{voterid});
    }

    public void updateVotingStatusTransTable(String voterid, int voted, String currenttime, int synced, long transactionId, String voting_type, String userid) {
        ContentValues cv = new ContentValues();
        cv.put("VOTED", Integer.valueOf(voted));
        cv.put("VOTING_DATE", currenttime);
        cv.put("SYNCED", Integer.valueOf(synced));
        cv.put("VOTING_TYPE", voting_type);
        cv.put("USER_ID", userid);
        this.database = getWritableDatabase();
        this.database.update(this.tbl_transaction, cv, "_id = ?", new String[]{String.valueOf(transactionId)});
    }

    public void updateMatchedVoterData(long transactionId, String matched_user_id, String matched_id_document_image) {
        ContentValues cv = new ContentValues();
        cv.put("MATCHED_USER_ID", matched_user_id);
        cv.put("MATCHED_ID_DOCUMENT_IMAGE", matched_id_document_image);
        this.database = getWritableDatabase();
        this.database.update(this.tbl_transaction, cv, "_id = ?", new String[]{String.valueOf(transactionId)});
    }

    public String getfp(String voterid) {
        Cursor c = getReadableDatabase().rawQuery("SELECT  EnrollTemplate FROM " + this.tbl_registration_master + " where EPIC_NO='" + voterid + "'", null);
        return Base64.encodeToString(c.getBlob(c.getColumnIndex("EnrollTemplate")), 0);
    }

    public String getslinward(String voterid) {
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM " + this.tbl_registration_master + " where EPIC_NO='" + voterid + "'", null);
        String name = c.getString(c.getColumnIndex("SlNoInWard"));
        if (name == null || name.isEmpty()) {
            return "";
        }
        return name;
    }

    public String getuseridimage(String voterid) {
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM " + this.tbl_registration_master + " where EPIC_NO='" + voterid + "'", null);
        String name = c.getString(c.getColumnIndex("ID_DOCUMENT_IMAGE"));
        if (name == null || name.isEmpty()) {
            return "";
        }
        return name;
    }

    public String getDateFromSlNoinWard(String slnoinward) {
        String selectQuery = "SELECT  * FROM " + this.tbl_transaction + " where SlNoInWard='" + slnoinward + "' AND VOTING_DATE IS NOT NULL AND VOTING_DATE != ''";
        SQLiteDatabase db = getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (!cursor.moveToFirst()) {
                try {
                    cursor.close();
                } catch (Exception e) {
                }
                try {
                    db.close();
                    return "";
                } catch (Exception e2) {
                    return "";
                }
            } else if (cursor.moveToFirst()) {
                String votingdate = cursor.getString(cursor.getColumnIndex("VOTING_DATE"));
                try {
                    cursor.close();
                } catch (Exception e3) {
                }
                return votingdate;
            } else {
                do {
                } while (cursor.moveToNext());
                cursor.close();
                db.close();
                return "";
            }
        } finally {
            try {
                db.close();
            } catch (Exception e4) {
            }
        }
    }

    public int getSlNoInWardForAadhaar(String aadhaanum) {
        String selectQuery = "SELECT  * FROM " + this.tbl_transaction + " where AADHAAR_NO='" + aadhaanum + "' ";
        SQLiteDatabase db = getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (!cursor.moveToFirst()) {
                try {
                    cursor.close();
                } catch (Exception e) {
                }
                try {
                    db.close();
                    return -1;
                } catch (Exception e2) {
                    return -1;
                }
            } else if (cursor.moveToFirst()) {
                int slnoinward = cursor.getInt(cursor.getColumnIndex("SlNoInWard"));
                try {
                    cursor.close();
                } catch (Exception e3) {
                }
                return slnoinward;
            } else {
                do {
                } while (cursor.moveToNext());
                cursor.close();
                db.close();
                return -1;
            }
        } finally {
            try {
                db.close();
            } catch (Exception e4) {
            }
        }
    }

    public String getUSER_IDForAadhaar(String aadhaanum) {
        String selectQuery = "SELECT  * FROM " + this.tbl_transaction + " where AADHAAR_NO='" + aadhaanum + "' ";
        SQLiteDatabase db = getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (!cursor.moveToFirst()) {
                try {
                    cursor.close();
                } catch (Exception e) {
                }
                try {
                    db.close();
                    return "";
                } catch (Exception e2) {
                    return "";
                }
            } else if (cursor.moveToFirst()) {
                String user_id = cursor.getString(cursor.getColumnIndex("USER_ID"));
                try {
                    cursor.close();
                } catch (Exception e3) {
                }
                return user_id;
            } else {
                do {
                } while (cursor.moveToNext());
                cursor.close();
                db.close();
                return "";
            }
        } finally {
            try {
                db.close();
            } catch (Exception e4) {
            }
        }
    }

    public String getID_DOCUMENT_IMAGEForAadhaar(String aadhaanum) {
        String selectQuery = "SELECT  * FROM " + this.tbl_transaction + " where AADHAAR_NO='" + aadhaanum + "' ";
        SQLiteDatabase db = getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (!cursor.moveToFirst()) {
                try {
                    cursor.close();
                } catch (Exception e) {
                }
                try {
                    db.close();
                    return "";
                } catch (Exception e2) {
                    return "";
                }
            } else if (cursor.moveToFirst()) {
                String user_id = cursor.getString(cursor.getColumnIndex("ID_DOCUMENT_IMAGE"));
                try {
                    cursor.close();
                } catch (Exception e3) {
                }
                return user_id;
            } else {
                do {
                } while (cursor.moveToNext());
                cursor.close();
                db.close();
                return "";
            }
        } finally {
            try {
                db.close();
            } catch (Exception e4) {
            }
        }
    }

    public String getuseridimageTransTable(String voterid) {
        Cursor c = getReadableDatabase().rawQuery("SELECT  ID_DOCUMENT_IMAGE FROM " + this.tbl_transaction + " where EPIC_NO='" + voterid + "'", null);
        String name = c.getString(c.getColumnIndex("SlNoInWard"));
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

    public Cursor SingleTransactionRow(long trid) {
        return getReadableDatabase().rawQuery("SELECT  * FROM " + this.tbl_transaction + " where _id='" + trid + "'", null);
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

    public String getImageFromTransactionTable(String slnoinward) {
        String selectQuery = "SELECT  * FROM " + this.tbl_transaction + " where SlNoInWard='" + slnoinward + "' ";
        SQLiteDatabase db = getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (!cursor.moveToFirst()) {
                try {
                    cursor.close();
                } catch (Exception e) {
                }
                try {
                    db.close();
                    return "";
                } catch (Exception e2) {
                    return "";
                }
            } else if (cursor.moveToFirst()) {
                String documentimage = cursor.getString(cursor.getColumnIndex("ID_DOCUMENT_IMAGE"));
                try {
                    cursor.close();
                } catch (Exception e3) {
                }
                return documentimage;
            } else {
                do {
                } while (cursor.moveToNext());
                cursor.close();
                db.close();
                return "";
            }
        } finally {
            try {
                db.close();
            } catch (Exception e4) {
            }
        }
    }

    public String getUser_IdBySlNoinWard(String slnoinward) {
        String selectQuery = "SELECT  * FROM " + this.tbl_transaction + " where SlNoInWard='" + slnoinward + "' ";
        SQLiteDatabase db = getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (!cursor.moveToFirst()) {
                try {
                    cursor.close();
                } catch (Exception e) {
                }
                try {
                    db.close();
                    return "";
                } catch (Exception e2) {
                    return "";
                }
            } else if (cursor.moveToFirst()) {
                String documentimage = cursor.getString(cursor.getColumnIndex("USER_ID"));
                try {
                    cursor.close();
                } catch (Exception e3) {
                }
                return documentimage;
            } else {
                do {
                } while (cursor.moveToNext());
                cursor.close();
                db.close();
                return "";
            }
        } finally {
            try {
                db.close();
            } catch (Exception e4) {
            }
        }
    }

    public VoterDataNewModel getVoterBySlNoInWard(String slnoinward) {
        List<VoterDataNewModel> list = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + this.tbl_registration_master + " where SlNoInWard='" + slnoinward + "'";
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
                    obj.setPanchayatID(cursor.getString(cursor.getColumnIndex("PanchayatID")));
                    obj.setBoothNo("" + cursor.getInt(cursor.getColumnIndex("BoothNo")));
                    list.add(obj);
                } while (cursor.moveToNext());
                try {
                    cursor.close();
                } catch (Exception e) {
                }
                if (!list.isEmpty() || list.size() <= 0) {
                    return null;
                }
                return list.get(0);
            }
            cursor.close();
            if (!list.isEmpty()) {
            }
            return null;
        } finally {
            try {
                db.close();
            } catch (Exception e2) {
            }
        }
    }

    public VoterDataNewModel getVoterByuser_id(String user_id) {
        List<VoterDataNewModel> list = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + this.tbl_registration_master + " where USER_ID='" + user_id + "'";
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
                if (!list.isEmpty() || list.size() <= 0) {
                    return null;
                }
                return list.get(0);
            }
            cursor.close();
            if (!list.isEmpty()) {
            }
            return null;
        } finally {
            try {
                db.close();
            } catch (Exception e2) {
            }
        }
    }

    public Cursor getAllRowsofTransTableCursor() {
        try {
            return getReadableDatabase().rawQuery("SELECT  * FROM " + this.tbl_transaction, null);
        } catch (Exception e) {
            return null;
        }
    }

    public Cursor getAllRowsofTransTableCursorRejectedVoters() {
        try {
            return getReadableDatabase().rawQuery("SELECT  * FROM " + this.tbl_transaction + " where IMAGE_SYNCED='0' AND VOTED='2' ", null);
        } catch (Exception e) {
            return null;
        }
    }

    public Cursor getAllRowsofTransTableCursorRejectedVotersMatch() {
        try {
            return getReadableDatabase().rawQuery("SELECT  * FROM " + this.tbl_transaction + " where MATCHED_IMAGE_SYNCED='0' AND VOTED='2' ", null);
        } catch (Exception e) {
            return null;
        }
    }

    public Cursor getAllRowsofTransTableCursorThumbNail() {
        try {
            return getReadableDatabase().rawQuery("SELECT  * FROM " + this.tbl_transaction + " where THUMBNAIL_IMAGE_SYNCED='0' AND (VOTED='2' OR VOTED='1' OR VOTED='3')  ", null);
        } catch (Exception e) {
            return null;
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
                    obj.setBoothNo(cursor.getString(cursor.getColumnIndex("BoothNo")));
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

    public List<VotingHistoryModel> getAllTransactionTableData() {
        List<VotingHistoryModel> list = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + this.tbl_transaction;
        SQLiteDatabase db = getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    VotingHistoryModel obj = new VotingHistoryModel();
                    obj.setSlNoInWard(cursor.getString(cursor.getColumnIndex("SlNoInWard")));
                    obj.setVoted(cursor.getString(cursor.getColumnIndex("VOTED")));
                    obj.setVotingDate(cursor.getString(cursor.getColumnIndex("VOTING_DATE")));
                    obj.setSynced(cursor.getInt(cursor.getColumnIndex("SYNCED")));
                    obj.setGender(cursor.getString(cursor.getColumnIndex("GENDER")));
                    obj.setMATCHED_ID_DOCUMENT_IMAGE(cursor.getString(cursor.getColumnIndex("MATCHED_ID_DOCUMENT_IMAGE")));
                    obj.setMATCHED_USER_ID(cursor.getString(cursor.getColumnIndex("MATCHED_USER_ID")));
                    list.add(obj);
                } while (cursor.moveToNext());
                cursor.close();
                return list;
            }
            cursor.close();
            return list;
        } finally {
            try {
                db.close();
            } catch (Exception e) {
            }
        }
    }

    public List<VoterDataNewModel> getAllElementsForAPanchayatBooth(String panchayatid, String boothno) {
        List<VoterDataNewModel> list = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + this.tbl_registration_master + " where PanchayatId='" + panchayatid + "' and BoothNo='" + boothno + "'";
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
                    obj.setBoothNo(cursor.getString(cursor.getColumnIndex("BoothNo")));
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

    public void updateAadhaarResultTransTable(String voterid, String aadhaar, String aadhaarmatchstatus, String voted, String currenttime, long lastTransactionId) {
        ContentValues cv = new ContentValues();
        cv.put("AADHAAR_NO", aadhaar);
        cv.put("AADHAAR_MATCH", aadhaarmatchstatus);
        cv.put("VOTED", voted);
        cv.put("VOTING_DATE", currenttime);
        cv.put("VOTING_TYPE", "AADHAAR");
        this.database = getWritableDatabase();
        this.database.update(this.tbl_transaction, cv, "_id = ?", new String[]{String.valueOf(lastTransactionId)});
    }

    public void updateFingerprintTemplate(String voterid, byte[] finger_template) {
        ContentValues cv = new ContentValues();
        this.database = getWritableDatabase();
        cv.put("EnrollTemplate", finger_template);
        this.database.update(this.tbl_registration_master, cv, "EPIC_NO = ?", new String[]{voterid});
    }

    public void updateFingerprintTemplateTransTable(String voterid, byte[] finger_template, long transactionId) {
        ContentValues cv = new ContentValues();
        cv.put("FingerTemplate", finger_template);
        this.database = getWritableDatabase();
        this.database.update(this.tbl_transaction, cv, "_id = ?", new String[]{String.valueOf(transactionId)});
    }

    public void updateVoterIdImage(String voterid, String voteridImageName) {
        ContentValues cv = new ContentValues();
        cv.put("ID_DOCUMENT_IMAGE", voteridImageName);
        this.database = getWritableDatabase();
        this.database.update(this.tbl_registration_master, cv, "EPIC_NO = ?", new String[]{voterid});
    }

    public void updateVoterIdImageTransTable(String voterid, String voteridImageName) {
        ContentValues cv = new ContentValues();
        cv.put("ID_DOCUMENT_IMAGE", voteridImageName);
        this.database = getWritableDatabase();
        this.database.update(this.tbl_transaction, cv, "EPIC_NO = ?", new String[]{voterid});
    }

    public void insertVoterIdImageTransTable(String voterid, String voteridImageName) {
        ContentValues cv = new ContentValues();
        cv.put("ID_DOCUMENT_IMAGE", voteridImageName);
        cv.put("EPIC_NO", voterid);
        this.database = getWritableDatabase();
        this.database.update(this.tbl_transaction, cv, "EPIC_NO = ?", new String[]{voterid});
    }

    public void clearFingerprint(String voterid) {
        ContentValues cv = new ContentValues();
        cv.put("EnrollTemplate", "");
        this.database = getWritableDatabase();
        this.database.update(this.tbl_registration_master, cv, "EPIC_NO = ?", new String[]{voterid});
    }

    public void clearFingerprintTransTable(long transactionId) {
        ContentValues cv = new ContentValues();
        cv.put("FingerTemplate", "");
        this.database = getWritableDatabase();
        this.database.update(this.tbl_transaction, cv, "_id = ?", new String[]{String.valueOf(transactionId)});
    }

    public Cursor fpcompare(String voterid) {
        return getReadableDatabase().rawQuery("SELECT  * FROM " + this.tbl_registration_master + " where EnrollTemplate IS NOT NULL AND EnrollTemplate != ''", null);
    }

    public Cursor fpcompareTransTable(String slnoinward) {
        return getReadableDatabase().rawQuery("SELECT  _id,EPIC_NO,FingerTemplate,SlNoInWard,USER_ID,ID_DOCUMENT_IMAGE FROM " + this.tbl_transaction + " where FingerTemplate IS NOT NULL AND FingerTemplate != '' AND SlNoInWard !='" + slnoinward + "' AND (VOTED ='1'  OR VOTED ='3') ", null);
    }

    public Cursor fpcompareLock() {
        return getReadableDatabase().rawQuery("SELECT  * FROM " + this.tbl_lock_boothofficer + " where FingerTemplate  IS NOT NULL", null);
    }

    /* JADX INFO: Multiple debug info for r1v11 'printWriter'  java.io.PrintWriter: [D('df' java.text.DateFormat), D('printWriter' java.io.PrintWriter)] */
    /* JADX INFO: Multiple debug info for r1v37 'printWriter'  java.io.PrintWriter: [D('printWriter' java.io.PrintWriter), D('MOBILE_NO' java.lang.String)] */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0396  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x03a5  */
    /* JADX WARN: Removed duplicated region for block: B:75:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump */
    public boolean exportDatabase() {
        PrintWriter printWriter;
        Throwable th;
        String str = ",";
        DateFormat df = DateFormat.getDateInstance(3, Locale.getDefault());
        String state = Environment.getExternalStorageState();
        if (!"mounted".equals(state)) {
            return false;
        }
        File exportDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        if (!exportDir.exists()) {
            exportDir.mkdirs();
        }
        try {
            File file = new File(exportDir, "MyCSVFile.csv");
            file.createNewFile();
            PrintWriter printWriter2 = new PrintWriter(new FileWriter(file));
            try {
                SQLiteDatabase db = getReadableDatabase();
                Cursor curCSV = db.rawQuery("select * from " + this.tbl_registration_master, null);
                printWriter2.println("FIRST TABLE OF THE DATABASE");
                printWriter2.println("ID,DIST_NO,AC_NO,PART_NO,SECTION_NO,SLNOINPART,C_HOUSE_NO,C_HOUSE_NO_V1,FM_NAME_EN,LASTNAME_EN,FM_NAME_V1,LASTNAME_V1,RLN_TYPE,STATUS_TYPE,RLN_L_NM_EN,RLN_FM_NM_V1,RLN_L_NM_V1,EPIC_NO,RLN_FM_NM_EN,GENDER,AGE,DOB,EMAIL_ID,MOBILE_NO,ELECTOR_TYPE,BlockID,PanchayatID,VillageName,WardNo,SlNoInWard,UserId,VOTED,FACE_MATCH,VOTER_IMAGE,VOTER_FINGERPRINT,ID_DOCUMENT_IMAGE,FINGERPRINT_MATCH,VOTING_DATE,AADHAAR_MATCH,AADHAAR_NO,EnrollTemplate");
                while (curCSV.moveToNext()) {
                    long ID = curCSV.getLong(curCSV.getColumnIndex("ID"));
                    String DIST_NO = curCSV.getString(curCSV.getColumnIndex("DIST_NO"));
                    int AC_NO = curCSV.getInt(curCSV.getColumnIndex("AC_NO"));
                    int PART_NO = curCSV.getInt(curCSV.getColumnIndex("PART_NO"));
                    int SECTION_NO = curCSV.getInt(curCSV.getColumnIndex("SECTION_NO"));
                    String SLNOINPART = curCSV.getString(curCSV.getColumnIndex("SLNOINPART"));
                    try {
                        String C_HOUSE_NO = curCSV.getString(curCSV.getColumnIndex("C_HOUSE_NO"));
                        try {
                            String C_HOUSE_NO_V1 = curCSV.getString(curCSV.getColumnIndex("C_HOUSE_NO_V1"));
                            try {
                                String FM_NAME_EN = curCSV.getString(curCSV.getColumnIndex("FM_NAME_EN"));
                                String LASTNAME_EN = curCSV.getString(curCSV.getColumnIndex("LASTNAME_EN"));
                                String FM_NAME_V1 = curCSV.getString(curCSV.getColumnIndex("FM_NAME_V1"));
                                String LASTNAME_V1 = curCSV.getString(curCSV.getColumnIndex("LASTNAME_V1"));
                                try {
                                    String RLN_TYPE = curCSV.getString(curCSV.getColumnIndex("RLN_TYPE"));
                                    String STATUS_TYPE = curCSV.getString(curCSV.getColumnIndex("STATUS_TYPE"));
                                    String RLN_L_NM_EN = curCSV.getString(curCSV.getColumnIndex("RLN_L_NM_EN"));
                                    String RLN_FM_NM_V1 = curCSV.getString(curCSV.getColumnIndex("RLN_FM_NM_V1"));
                                    String RLN_L_NM_V1 = curCSV.getString(curCSV.getColumnIndex("RLN_L_NM_V1"));
                                    String EPIC_NO = curCSV.getString(curCSV.getColumnIndex("EPIC_NO"));
                                    String RLN_FM_NM_EN = curCSV.getString(curCSV.getColumnIndex("RLN_FM_NM_EN"));
                                    String GENDER = curCSV.getString(curCSV.getColumnIndex("GENDER"));
                                    int AGE = curCSV.getInt(curCSV.getColumnIndex("AGE"));
                                    String DOB = curCSV.getString(curCSV.getColumnIndex("DOB"));
                                    String EMAIL_ID = curCSV.getString(curCSV.getColumnIndex("EMAIL_ID"));
                                    String MOBILE_NO = curCSV.getString(curCSV.getColumnIndex("MOBILE_NO"));
                                    String ELECTOR_TYPE = curCSV.getString(curCSV.getColumnIndex("ELECTOR_TYPE"));
                                    curCSV.getInt(curCSV.getColumnIndex("BlockID"));
                                    curCSV.getInt(curCSV.getColumnIndex("PanchayatID"));
                                    curCSV.getString(curCSV.getColumnIndex("VillageName"));
                                    curCSV.getString(curCSV.getColumnIndex("WardNo"));
                                    curCSV.getString(curCSV.getColumnIndex("SlNoInWard"));
                                    curCSV.getString(curCSV.getColumnIndex("UserId"));
                                    curCSV.getInt(curCSV.getColumnIndex("VOTED"));
                                    curCSV.getInt(curCSV.getColumnIndex("FACE_MATCH"));
                                    curCSV.getString(curCSV.getColumnIndex("VOTER_IMAGE"));
                                    curCSV.getString(curCSV.getColumnIndex("VOTER_FINGERPRINT"));
                                    curCSV.getString(curCSV.getColumnIndex("ID_DOCUMENT_IMAGE"));
                                    curCSV.getInt(curCSV.getColumnIndex("FINGERPRINT_MATCH"));
                                    curCSV.getString(curCSV.getColumnIndex("VOTING_DATE"));
                                    curCSV.getInt(curCSV.getColumnIndex("AADHAAR_MATCH"));
                                    curCSV.getString(curCSV.getColumnIndex("AADHAAR_NO"));
                                    curCSV.getBlob(curCSV.getColumnIndex("EnrollTemplate"));
                                    printWriter = printWriter2;
                                    try {
                                        printWriter.println(ID + str + DIST_NO + str + AC_NO + str + PART_NO + str + SECTION_NO + str + SLNOINPART + str + C_HOUSE_NO + str + C_HOUSE_NO_V1 + str + FM_NAME_EN + str + LASTNAME_EN + str + FM_NAME_V1 + str + LASTNAME_V1 + str + RLN_TYPE + str + STATUS_TYPE + str + RLN_L_NM_EN + str + RLN_FM_NM_V1 + str + RLN_L_NM_V1 + str + EPIC_NO + str + RLN_FM_NM_EN + str + GENDER + str + AGE + str + DOB + str + EMAIL_ID + str + MOBILE_NO + str + ELECTOR_TYPE);
                                        printWriter2 = printWriter;
                                        df = df;
                                        state = state;
                                        exportDir = exportDir;
                                        file = file;
                                        db = db;
                                        str = str;
                                    } catch (Exception e) {
                                        if (printWriter != null) {
                                            return false;
                                        }
                                        printWriter.close();
                                        return false;
                                    } catch (Throwable th2) {
                                        th = th2;
                                        if (printWriter != null) {
                                            printWriter.close();
                                        }
                                        throw th;
                                    }
                                } catch (Exception e2) {
                                    printWriter = printWriter2;
                                    if (printWriter != null) {
                                    }
                                } catch (Throwable th3) {
                                    th = th3;
                                    printWriter = printWriter2;
                                    if (printWriter != null) {
                                    }
                                    throw th;
                                }
                            } catch (Exception e3) {
                                printWriter = printWriter2;
                            } catch (Throwable th4) {
                                th = th4;
                                printWriter = printWriter2;
                            }
                        } catch (Exception e4) {
                            printWriter = printWriter2;
                        } catch (Throwable th5) {
                            th = th5;
                            printWriter = printWriter2;
                        }
                    } catch (Exception e5) {
                        printWriter = printWriter2;
                    } catch (Throwable th6) {
                        th = th6;
                        printWriter = printWriter2;
                    }
                }
                printWriter = printWriter2;
                curCSV.close();
                db.close();
                printWriter.close();
                return true;
            } catch (Exception e6) {
                printWriter = printWriter2;
            } catch (Throwable th7) {
                th = th7;
                printWriter = printWriter2;
            }
        } catch (Exception e7) {
            printWriter = null;
        } catch (Throwable th8) {
            th = th8;
            printWriter = null;
        }
    }

    /* JADX INFO: Multiple debug info for r1v25 'printWriter'  java.io.PrintWriter: [D('printWriter' java.io.PrintWriter), D('FpTemplate' byte[])] */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0349  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0352  */
    /* JADX WARN: Removed duplicated region for block: B:93:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump */
    public boolean exportDatabaseTransTable(String dist, String block, String panchayat, String wardNo, String boothNo, String currTime) {
        Exception exc;
        PrintWriter printWriter;
        new UserAuth(this.mContext);
        if (getTransTableCount() < 1) {
            Toast.makeText(this.mContext, "No data in Transaction table", 1).show();
            return false;
        }
        DateFormat df = DateFormat.getDateInstance(3, Locale.getDefault());
        String state = Environment.getExternalStorageState();
        if (!"mounted".equals(state)) {
            return false;
        }
        File exportDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        if (!exportDir.exists()) {
            exportDir.mkdirs();
        }
        PrintWriter printWriter2 = null;
        try {
            File file = new File(exportDir, "SECPhase9_" + dist + "_" + block + "_" + panchayat + "_" + wardNo + "_" + boothNo + "_" + currTime + ".csv");
            file.createNewFile();
            printWriter2 = new PrintWriter(new FileWriter(file));
            try {
                SQLiteDatabase db = getReadableDatabase();
                StringBuilder sb = new StringBuilder();
                try {
                    sb.append("select * from ");
                    sb.append(this.tbl_transaction);
                    Cursor curCSV = db.rawQuery(sb.toString(), null);
                    printWriter2.println("FIRST TABLE OF THE DATABASE");
                    printWriter2.println("_id,FingerTemplate,EPIC_NO,DIST_NO,BlockID,PanchayatID,VOTED,ID_DOCUMENT_IMAGE,VOTING_DATE,AADHAAR_MATCH,AADHAAR_NO,SlNoInWard,GENDER,AGE,SYNCED,USER_ID,VOTING_TYPE,IMAGE_SYNCED,MATCHED_ID_DOCUMENT_IMAGE,MATCHED_USER_ID,MATCHED_IMAGE_SYNCED,THUMBNAIL_ID_DOCUMENT_IMAGE,THUMBNAIL_IMAGE_SYNCED");
                    while (curCSV.moveToNext()) {
                        Long id_ = Long.valueOf(curCSV.getLong(curCSV.getColumnIndex(Key_ID)));
                        byte[] FpTemplate = curCSV.getBlob(curCSV.getColumnIndex("FingerTemplate"));
                        try {
                            String EPIC_NO = curCSV.getString(curCSV.getColumnIndex("EPIC_NO"));
                            String DIST_NO = curCSV.getString(curCSV.getColumnIndex("DIST_NO"));
                            try {
                                int BlockID = curCSV.getInt(curCSV.getColumnIndex("BlockID"));
                                try {
                                    Long PanchayatID = Long.valueOf(curCSV.getLong(curCSV.getColumnIndex("PanchayatID")));
                                    int VOTED = curCSV.getInt(curCSV.getColumnIndex("VOTED"));
                                    String ID_DOCUMENT_IMAGE = curCSV.getString(curCSV.getColumnIndex("ID_DOCUMENT_IMAGE"));
                                    String VOTING_DATE = curCSV.getString(curCSV.getColumnIndex("VOTING_DATE"));
                                    String AADHAAR_MATCH = curCSV.getString(curCSV.getColumnIndex("AADHAAR_MATCH"));
                                    String AADHAAR_NO = curCSV.getString(curCSV.getColumnIndex("AADHAAR_NO"));
                                    int SlNoInWard = curCSV.getInt(curCSV.getColumnIndex("SlNoInWard"));
                                    String GENDER = curCSV.getString(curCSV.getColumnIndex("GENDER"));
                                    try {
                                        int AGE = curCSV.getInt(curCSV.getColumnIndex("AGE"));
                                        int SYNCED = curCSV.getInt(curCSV.getColumnIndex("SYNCED"));
                                        int IMAGE_SYNCED = curCSV.getInt(curCSV.getColumnIndex("IMAGE_SYNCED"));
                                        String userId = curCSV.getString(curCSV.getColumnIndex("USER_ID"));
                                        String voting_type = curCSV.getString(curCSV.getColumnIndex("VOTING_TYPE"));
                                        String MATCHED_USER_ID = curCSV.getString(curCSV.getColumnIndex("MATCHED_USER_ID"));
                                        printWriter = printWriter2;
                                        try {
                                            printWriter.println(id_ + "," + FpTemplate + "," + EPIC_NO + "," + DIST_NO + "," + BlockID + "," + PanchayatID + "," + VOTED + "," + ID_DOCUMENT_IMAGE + "," + VOTING_DATE + "," + AADHAAR_MATCH + "," + AADHAAR_NO + "," + SlNoInWard + "," + GENDER + "," + AGE + "," + SYNCED + "," + userId + "," + voting_type + "," + IMAGE_SYNCED + "," + curCSV.getString(curCSV.getColumnIndex("MATCHED_ID_DOCUMENT_IMAGE")) + "," + MATCHED_USER_ID + "," + curCSV.getInt(curCSV.getColumnIndex("MATCHED_IMAGE_SYNCED")) + "," + curCSV.getString(curCSV.getColumnIndex("THUMBNAIL_ID_DOCUMENT_IMAGE")) + "," + curCSV.getInt(curCSV.getColumnIndex("THUMBNAIL_IMAGE_SYNCED")));
                                            printWriter2 = printWriter;
                                            df = df;
                                            state = state;
                                            exportDir = exportDir;
                                            file = file;
                                            db = db;
                                            curCSV = curCSV;
                                        } catch (Exception e) {
                                            exc = e;
                                            printWriter2 = printWriter;
                                            try {
                                                Log.d("dbtransexport", exc.getMessage());
                                                if (printWriter2 != null) {
                                                    return false;
                                                }
                                                printWriter2.close();
                                                return false;
                                            } catch (Throwable th) {
                                                exc = th;
                                                printWriter = printWriter2;
                                                if (printWriter != null) {
                                                    printWriter.close();
                                                }
                                                throw exc;
                                            }
                                        } catch (Throwable th2) {
                                            exc = th2;
                                            if (printWriter != null) {
                                            }
                                            throw exc;
                                        }
                                    } catch (Exception e2) {
                                        exc = e2;
                                        printWriter2 = printWriter2;
                                        Log.d("dbtransexport", exc.getMessage());
                                        if (printWriter2 != null) {
                                        }
                                    } catch (Throwable th3) {
                                        exc = th3;
                                        printWriter = printWriter2;
                                        if (printWriter != null) {
                                        }
                                        throw exc;
                                    }
                                } catch (Exception e3) {
                                    exc = e3;
                                } catch (Throwable th4) {
                                    exc = th4;
                                    printWriter = printWriter2;
                                }
                            } catch (Exception e4) {
                                exc = e4;
                            } catch (Throwable th5) {
                                exc = th5;
                                printWriter = printWriter2;
                            }
                        } catch (Exception e5) {
                            exc = e5;
                        } catch (Throwable th6) {
                            exc = th6;
                            printWriter = printWriter2;
                        }
                    }
                    printWriter = printWriter2;
                    curCSV.close();
                    db.close();
                    printWriter.close();
                    return true;
                } catch (Exception e6) {
                    exc = e6;
                } catch (Throwable th7) {
                    exc = th7;
                    printWriter = printWriter2;
                }
            } catch (Exception e7) {
                exc = e7;
            } catch (Throwable th8) {
                exc = th8;
                printWriter = printWriter2;
            }
        } catch (Exception e8) {
            exc = e8;
        } catch (Throwable th9) {
            exc = th9;
            printWriter = null;
        }
    }

    public long getNumbersVoted() {
        return (long) getReadableDatabase().rawQuery("SELECT  _id FROM " + this.tbl_transaction + " where VOTED='1' OR VOTED='3' ", null).getCount();
    }

    public long getNumbersRejected() {
        return (long) getReadableDatabase().rawQuery("SELECT  _id FROM " + this.tbl_transaction + " where VOTED='2'", null).getCount();
    }

    public long getNumberFemalesVoted() {
        return (long) getReadableDatabase().rawQuery("SELECT  * FROM " + this.tbl_transaction + " where (VOTED='1' OR VOTED='3') AND GENDER like '%F%'", null).getCount();
    }

    public long getNumberMalesVoted() {
        return (long) getReadableDatabase().rawQuery("SELECT  * FROM " + this.tbl_transaction + " WHERE (VOTED='1' OR VOTED='3') AND GENDER like '%M%'", null).getCount();
    }

    public long getAadhaarVotedCount() {
        return (long) getReadableDatabase().rawQuery("SELECT  VOTING_TYPE FROM " + this.tbl_transaction + " where VOTING_TYPE='AADHAAR' AND VOTED!='2' AND VOTED!='0'", null).getCount();
    }

    public long getNonAadhaarVotedCount() {
        return (long) getReadableDatabase().rawQuery("SELECT  VOTING_TYPE FROM " + this.tbl_transaction + " where VOTING_TYPE='NON_AADHAAR' AND VOTED!='2' AND VOTED!='0'", null).getCount();
    }

    public void updateSync(int transid, int sync) {
        this.database = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("SYNCED", Integer.valueOf(sync));
        this.database.update(this.tbl_transaction, cv, "_id = ?", new String[]{String.valueOf(transid)});
    }

    public void updateImageSync(int transid, int sync) {
        this.database = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("IMAGE_SYNCED", Integer.valueOf(sync));
        this.database.update(this.tbl_transaction, cv, "_id = ?", new String[]{String.valueOf(transid)});
    }

    public void updateImageSyncThumbnail(int transid, int sync) {
        this.database = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("THUMBNAIL_IMAGE_SYNCED", Integer.valueOf(sync));
        this.database.update(this.tbl_transaction, cv, "_id = ?", new String[]{String.valueOf(transid)});
    }

    public void updateImageSyncMatched(int transid, int sync) {
        this.database = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("MATCHED_IMAGE_SYNCED", Integer.valueOf(sync));
        this.database.update(this.tbl_transaction, cv, "_id = ?", new String[]{String.valueOf(transid)});
    }

    public long getUnSyncCount() {
        return (long) getReadableDatabase().rawQuery("SELECT  SYNCED FROM " + this.tbl_transaction + " where SYNCED='0' AND VOTED!='0' ", null).getCount();
    }

    public long getImageUnSyncCount() {
        return (long) getReadableDatabase().rawQuery("SELECT  IMAGE_SYNCED FROM " + this.tbl_transaction + " where IMAGE_SYNCED='0' AND VOTED!='0' ", null).getCount();
    }

    public long getImageUnSyncCountRejectedVoters() {
        return (long) getReadableDatabase().rawQuery("SELECT  IMAGE_SYNCED FROM " + this.tbl_transaction + " where IMAGE_SYNCED='0' AND VOTED='2' ", null).getCount();
    }

    public long getImageUnSyncCountRejectedVotersMatch() {
        return (long) getReadableDatabase().rawQuery("SELECT  MATCHED_IMAGE_SYNCED FROM " + this.tbl_transaction + " where MATCHED_ID_DOCUMENT_IMAGE IS NOT NULL AND MATCHED_ID_DOCUMENT_IMAGE != '' AND  MATCHED_IMAGE_SYNCED='0' AND (VOTED='2' OR VOTED='1' OR VOTED='3') ", null).getCount();
    }

    public long getImageUnSyncCountThumb() {
        return (long) getReadableDatabase().rawQuery("SELECT  THUMBNAIL_IMAGE_SYNCED FROM " + this.tbl_transaction + " where THUMBNAIL_IMAGE_SYNCED='0' AND (VOTED='2' OR VOTED='1' OR VOTED='3' ) ", null).getCount();
    }
}
