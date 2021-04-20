package com.ksrsac.hasiru.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class SqliteHelper extends SQLiteOpenHelper {

    private final String TAG = SqliteHelper.this.getClass().getName();
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "DataBase_nic";

    public static final String TABLE_MASTER_MAIN = "master_table_main";
    public static final String TABLE_Villagename = "master_village";
    public static final String TABLE_FULLDETAILS = "table_fullDetails";
    public static final String TABLE_LOGIN= "table_login";
    public static final String TABLE_LGDCODE="tablelgdcode";
    public static final String TABLE_STAGE2= "Stage2";

    public static final String KEY_ID = "key_id";
    public static final String VILLAGE_NAME = "villagename";
    public static final String VILLAGE_CODE = "village_code";
    public static final String 	BENEFICIARY_NAME ="name";
    public static final String SERVEY_NO = "servey_no";
    public static final String HISSA_NO = "hissano";

    public static final String SCHEME= "scheme";
    public static final String CROP_CATEGORY = "crop_category";
    public static final String CROP_TYPE = "crop_type";
    public static final String TECHNOLOGY ="tecnology";
    public static final String BENEFICIARY_ID ="benificiaty_id";
    public static final String TOTAL_EXTENT = "totalextent";
    public static final String TOTAL_REVENUE = "totalrevenue";
    public static final String 	remars ="remars";

    public static final String STATUS = "status";
    public static final String USerID = "USerID";

    public static final String PASSWORD = "password";
    public static final String SHAPE_FILE_GEOMETRY ="shapefilegeometry";
    public static final String STAGE_PROGress = "stageprogress";
    public static final String OBJ_ID = "obj_id";
    public static final String DISTRICT = "district";
    public static final String HOBLI_NAME ="hobliname";
    public static final String TALUK = "taluk";
    public static final String TALUKCODE = "talukcode";
    public static final String DISTCODE = "distcode";
    public static final String LGDCODE = "lgdcode";

    /*
      Data Collector
       */
    public SqliteHelper(Context context) {
        /*  super(context, context.getExternalFilesDir(null).getAbsolutePath() + "/" + DATABASE_NAME, null, DATABASE_VERSION);*/
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    String table_login = "CREATE TABLE IF NOT EXISTS " + TABLE_LOGIN +
            "(" + KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+ USerID + " VARCHAR,"
            + PASSWORD +" VARCHAR,"+
            STATUS + " VARCHAR);";

    String table_village = "CREATE TABLE IF NOT EXISTS " + TABLE_Villagename +
            "(" + KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+ DISTRICT + " VARCHAR,"+VILLAGE_NAME + " VARCHAR,"+TALUK + " VARCHAR,"+HOBLI_NAME + " VARCHAR,"
            + VILLAGE_CODE +" VARCHAR,"+ TALUKCODE + " VARCHAR,"+DISTCODE + " VARCHAR,"+LGDCODE+" VARCHAR,"+
            STATUS + " VARCHAR);";

    String table_fulldetails = "CREATE TABLE IF NOT EXISTS " + TABLE_FULLDETAILS +
            "(" + KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+ DISTRICT + " VARCHAR,"+ VILLAGE_NAME + " VARCHAR,"+OBJ_ID +
            " VARCHAR,"+ VILLAGE_CODE +" VARCHAR,"
            + USerID +" VARCHAR,"+ BENEFICIARY_NAME + " VARCHAR,"+ SERVEY_NO + " VARCHAR,"+ HISSA_NO + " VARCHAR,"
            + SCHEME + " VARCHAR,"+ CROP_CATEGORY + " VARCHAR,"+ CROP_TYPE + " VARCHAR,"+ TECHNOLOGY + " VARCHAR,"+
            TOTAL_EXTENT+ " VARCHAR,"+TOTAL_REVENUE+ " VARCHAR,"+STAGE_PROGress+ " VARCHAR,"+ SHAPE_FILE_GEOMETRY +" VARCHAR,"+
            STATUS + " VARCHAR);";
    String table_lgd = "CREATE TABLE IF NOT EXISTS " + TABLE_LGDCODE +
            "(" + KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+ LGDCODE + " VARCHAR,"+
            STATUS + " VARCHAR);";
 /*   String savefirst_table = "CREATE TABLE IF NOT EXISTS " + TABLE1 +
            "(" + KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+ VILLAGE_NAME + " VARCHAR,"+OBJ_ID + " VARCHAR,"
            + USerID +" VARCHAR,"+ BENEFICIARY_NAME + " VARCHAR,"+ SERVEY_NO + " VARCHAR,"+ HISSA_NO + " VARCHAR,"
            + SCHEME + " VARCHAR,"+ CROP_CATEGORY + " VARCHAR,"+ CROP_TYPE + " VARCHAR,"+ TECHNOLOGY + " VARCHAR,"+
            TOTAL_EXTENT+ " VARCHAR,"+TOTAL_REVENUE+ " VARCHAR,"+
            STATUS + " VARCHAR);";


    String table_third = "CREATE TABLE IF NOT EXISTS " + TABLE_PHOTO +
            "(" + KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+ USerID + " VARCHAR,"
            + VILLAGE_NAME +" VARCHAR,"+ STAGE_TYPE + " VARCHAR,"+ PHOTO1 + " VARCHAR," + PHOTO2 +
            " VARCHAR,"+P1_LAT + " VARCHAR," + P1_LNG + " VARCHAR, " +
            P1_DATETIME + " VARCHAR, " + P2_LAT + " VARCHAR, "+ P2_LNG + " VARCHAR, "+ P2_DATE + " VARCHAR,"
            +GPS_LAT + " VARCHAR,"+GPS_LNG + " VARCHAR,"+OBJ_ID + " VARCHAR,"+
            STATUS + " VARCHAR);";*/

    public SqliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate: ");
        db.execSQL(table_village);
        db.execSQL(table_fulldetails);
        db.execSQL(table_lgd);
        db.execSQL(table_login);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert_values(String table, ContentValues value){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "insert_values: ");
        db.insert(table,null,value);
        db.close();
    }

    public void  delete_login(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "get_sync_count:delete ");
        String ss="DELETE  FROM "+TABLE_LOGIN+" WHERE "+USerID+"='"+username+"' ;";
        Log.d("deletelogin", ss);
        db.execSQL(ss);
    }
    public void  delete_fulldetails(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "get_sync_count:delete_fulldetails ");
        String ss="DELETE  FROM "+TABLE_FULLDETAILS+" WHERE "+USerID+"='"+username+"' ;";
        Log.d("delete_fulldetails", ss);
        db.execSQL(ss);
    }
    public void  delete_villagename(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "delete_villagename: ");
        String ss="DELETE  FROM "+TABLE_Villagename+" WHERE "+USerID+"='"+username+"' ;";
        Log.d("delete_villagename", ss);
        db.execSQL(ss);
    }
    public void deletrecordlogin() {

        SQLiteDatabase db = this.getWritableDatabase();
        String ss="DELETE FROM "+TABLE_LOGIN+" ;";
        db.execSQL(ss);

    }
    public List<String> getVillageNameFromDetails(String userid){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "get_sync_count: ");
        List<String> lib_name=new ArrayList<>();
        String s="SELECT DISTINCT "+VILLAGE_NAME+" FROM "+TABLE_FULLDETAILS+ " WHERE "+STATUS+"='1' AND "+USerID+ "= '"+userid+"' ;";
        Cursor c=db.rawQuery(s,null);
        if(c.getCount()!=0){
            c.moveToFirst();
            while(!c.isAfterLast()){
                lib_name.add(c.getString(c.getColumnIndex(VILLAGE_NAME)));
                c.moveToNext();
            }
        }
        c.close();
        db.close();
        lib_name.add(0,"Select");
        return lib_name;
    }
    public List<String> getlgdcode(){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "get_sync_count: ");
        List<String> lib_name=new ArrayList<>();
        String s="SELECT DISTINCT "+LGDCODE+" FROM "+TABLE_LGDCODE+ " WHERE "+ STATUS+"='1' ;";
        Cursor c=db.rawQuery(s,null);
        if(c.getCount()!=0){
            c.moveToFirst();
            while(!c.isAfterLast()){
                lib_name.add(c.getString(c.getColumnIndex(LGDCODE)));
                c.moveToNext();
            }
        }
        c.close();
        db.close();
        //   lib_name.add(0,"Select");
        return lib_name;
    }
    public int get_count() {

        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "get_count_download: ");
        int count=0;
        String query="SELECT * FROM "+TABLE_Villagename+" ;";

        Log.d("cursor_query",query);
        Cursor c= db.rawQuery(query, null);
        count=c.getCount();
        Log.d("cursor_query",String.valueOf(c.getCount()));

        c.close();
        db.close();

        return count;
    }
    public List<String> getdistrict(){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "get_sync_count: ");
        List<String> lib_name=new ArrayList<>();
        String s="SELECT DISTINCT "+DISTRICT+" FROM "+TABLE_Villagename+ " WHERE "+ STATUS+"='1' ;";
        Cursor c=db.rawQuery(s,null);
        if(c.getCount()!=0){
            c.moveToFirst();
            while(!c.isAfterLast()){
                lib_name.add(c.getString(c.getColumnIndex(DISTRICT)));
                c.moveToNext();
            }
        }
        c.close();
        db.close();
        lib_name.add(0,"Select");
        return lib_name;
    }
    public void delete_record_mis(String value) {
        Log.d(TAG, "addbhusampleData: ");
        SQLiteDatabase db = this.getWritableDatabase();

        String s="DELETE FROM "+TABLE_Villagename+" WHERE "+VILLAGE_CODE+"='"+value+"' ;";
        db.execSQL(s);
        Log.d(TAG, "delete sucess ");
        db.close(); // Closing database connection
    }
    public List<String> gethissano(String villagecode,String Serveyno){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "get_sync_count: ");
        List<String> lib_name=new ArrayList<>();
        String s="SELECT DISTINCT "+HISSA_NO+" FROM "+TABLE_FULLDETAILS+ " WHERE "+ VILLAGE_CODE+" = '"+villagecode+"' AND "+
                SERVEY_NO +" ='"+Serveyno+ "' AND "+ STATUS+"='1' ;";
        Cursor c=db.rawQuery(s,null);
        if(c.getCount()!=0){
            c.moveToFirst();
            while(!c.isAfterLast()){
                lib_name.add(c.getString(c.getColumnIndex(HISSA_NO)));
                c.moveToNext();
            }
        }
        c.close();
        db.close();
        lib_name.add(0,"Select");
        return lib_name;
    }
    public String getVillageName1(String lgdcode){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "get_sync_count: ");
        String lib_name="";
        String s="SELECT DISTINCT "+VILLAGE_NAME+" FROM "+TABLE_Villagename+ " WHERE "+STATUS+"='1' AND "+LGDCODE+ "= '"+lgdcode+"' ;";
        Cursor c=db.rawQuery(s,null);
        if(c.getCount()!=0){
            c.moveToFirst();
            while(!c.isAfterLast()){
                lib_name = c.getString(c.getColumnIndex(VILLAGE_NAME));
                c.moveToNext();
            }
        }
        c.close();
        db.close();
        return lib_name;
    }
    public String getDistname(String lgdcode){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "get_sync_count: ");
        String lib_name="";
        String s="SELECT DISTINCT "+DISTRICT+" FROM "+TABLE_Villagename+ " WHERE "+STATUS+"='1' AND "+LGDCODE+ "= '"+lgdcode+"' ;";
        Cursor c=db.rawQuery(s,null);
        if(c.getCount()!=0){
            c.moveToFirst();
            while(!c.isAfterLast()){
                lib_name = c.getString(c.getColumnIndex(DISTRICT));
                c.moveToNext();
            }
        }
        c.close();
        db.close();
        return lib_name;
    }
    public String getTaluk(String lgdcode){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "get_sync_count: ");
        String lib_name="";
        String s="SELECT DISTINCT "+TALUK+" FROM "+TABLE_Villagename+ " WHERE "+STATUS+"='1' AND "+LGDCODE+ "= '"+lgdcode+"' ;";
        Cursor c=db.rawQuery(s,null);
        if(c.getCount()!=0){
            c.moveToFirst();
            while(!c.isAfterLast()){
                lib_name = c.getString(c.getColumnIndex(TALUK));
                c.moveToNext();
            }
        }
        c.close();
        db.close();
        return lib_name;
    }
    public String getTaluk1(String villagecode){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "get_sync_count: ");
        String lib_name="";
        String s="SELECT DISTINCT "+TALUK+" FROM "+TABLE_Villagename+ " WHERE "+STATUS+"='1' AND "+VILLAGE_CODE+ "= '"+villagecode+"' ;";
        Cursor c=db.rawQuery(s,null);
        if(c.getCount()!=0){
            c.moveToFirst();
            while(!c.isAfterLast()){
                lib_name = c.getString(c.getColumnIndex(TALUK));
                c.moveToNext();
            }
        }
        c.close();
        db.close();
        return lib_name;
    }
    public String getVillageName(String villagecode){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "get_sync_count: ");
        String lib_name="";
        String s="SELECT DISTINCT "+VILLAGE_NAME+" FROM "+TABLE_Villagename+ " WHERE "+STATUS+"='1' AND "+VILLAGE_CODE+ "= '"+villagecode+"' ;";
        Cursor c=db.rawQuery(s,null);
        if(c.getCount()!=0){
            c.moveToFirst();
            while(!c.isAfterLast()){
                lib_name = c.getString(c.getColumnIndex(VILLAGE_NAME));
                c.moveToNext();
            }
        }
        c.close();
        db.close();
        return lib_name;
    }
    public String getVillagecode(String villagename){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "get_sync_count: ");
        String lib_name="";
        String s="SELECT DISTINCT "+VILLAGE_CODE+" FROM "+TABLE_Villagename+ " WHERE "+STATUS+"='1' AND "+VILLAGE_NAME+ "= '"+villagename+"' ;";
        Cursor c=db.rawQuery(s,null);
        if(c.getCount()!=0){
            c.moveToFirst();
            while(!c.isAfterLast()){
                lib_name = c.getString(c.getColumnIndex(VILLAGE_CODE));
                c.moveToNext();
            }
        }
        c.close();
        db.close();
        return lib_name;
    }
    public String getHobliName(String Username){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "get_sync_count: ");
        String lib_name="";
        String s="SELECT "+HOBLI_NAME+" FROM "+TABLE_Villagename+ " WHERE "+USerID+"='"+Username+"';";
        Cursor c=db.rawQuery(s,null);
        if(c.getCount()!=0){
            c.moveToFirst();
            while(!c.isAfterLast()){
                lib_name=c.getString(c.getColumnIndex(HOBLI_NAME));
                c.moveToNext();
            }
        }
        c.close();
        db.close();
        return lib_name;
    }

    public int getVillageNameList(String talukcode,String distcode) {

        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "get_count_download: ");
        int count=0;
        String query="SELECT DISTINCT "+VILLAGE_NAME+" FROM "+TABLE_Villagename+ " WHERE "+STATUS+"='1' AND "+TALUKCODE+ "= '"+talukcode+
                "' AND "+DISTCODE+ "= '"+distcode+"' ;";
        Log.d("cursor_query",query);
        Cursor c= db.rawQuery(query, null);
        count=c.getCount();
        Log.d("cursor_query",String.valueOf(c.getCount()));

        c.close();
        db.close();

        return count;
    }
    public List<String> getVillageNameListspinner(String talukcode,String distcode){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "get_sync_count: ");
        List<String> lib_name=new ArrayList<>();
        String s="SELECT DISTINCT "+VILLAGE_NAME+" FROM "+TABLE_Villagename+ " WHERE "+STATUS+"='1' AND "+TALUKCODE+ "= '"+talukcode+
                "' AND "+DISTCODE+ "= '"+distcode+"' ;";
        Cursor c=db.rawQuery(s,null);
        if(c.getCount()!=0){
            c.moveToFirst();
            while(!c.isAfterLast()){
                lib_name.add(c.getString(c.getColumnIndex(VILLAGE_NAME)));
                c.moveToNext();
            }
        }
        c.close();
        db.close();
        lib_name.add(0,"Select");
        return lib_name;
    }
    public List<String> getdistListspinner(){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "get_sync_count: ");
        List<String> lib_name=new ArrayList<>();
        String s="SELECT DISTINCT "+DISTRICT+" FROM "+TABLE_Villagename+ " WHERE "+STATUS+"='2"+"' ;";
        Cursor c=db.rawQuery(s,null);
        if(c.getCount()!=0){
            c.moveToFirst();
            while(!c.isAfterLast()){
                lib_name.add(c.getString(c.getColumnIndex(DISTRICT)));
                c.moveToNext();
            }
        }
        c.close();
        db.close();
        lib_name.add(0,"Select");
        return lib_name;
    }
    public List<String> gettalukListspinner(String Dist){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "get_sync_count: ");
        List<String> lib_name=new ArrayList<>();
        String s="SELECT DISTINCT "+TALUK+" FROM "+TABLE_Villagename+ " WHERE "+STATUS+"='1"+
                "' AND "+DISTRICT+ "= '"+Dist+"' ;";
        Cursor c=db.rawQuery(s,null);
        if(c.getCount()!=0){
            c.moveToFirst();
            while(!c.isAfterLast()){
                lib_name.add(c.getString(c.getColumnIndex(TALUK)));
                c.moveToNext();
            }
        }
        c.close();
        db.close();
        lib_name.add(0,"Select");
        return lib_name;
    }
    public List<String> getvillageListspinner(String Dist,String taluk){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "get_sync_count: ");
        List<String> lib_name=new ArrayList<>();
        String s="SELECT DISTINCT "+VILLAGE_NAME+" FROM "+TABLE_Villagename+ " WHERE "+STATUS+"='1' AND "+TALUK+ "= '"+taluk+
                "' AND "+DISTRICT+ "= '"+Dist+"' ;";
        Cursor c=db.rawQuery(s,null);
        if(c.getCount()!=0){
            c.moveToFirst();
            while(!c.isAfterLast()){
                lib_name.add(c.getString(c.getColumnIndex(VILLAGE_NAME)));
                c.moveToNext();
            }
        }
        c.close();
        db.close();
        lib_name.add(0,"Select");
        return lib_name;
    }
    public void  update_recordvillage(String talukcode,String distcode,String Vill){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "get_sync_count: ");
        String ss="UPDATE "+TABLE_Villagename+" SET "+STATUS+"='2"+"' WHERE "+TALUKCODE+"='"+talukcode+
                "' AND "+DISTCODE+ "= '"+distcode+ "' AND "+VILLAGE_CODE+ "= '"+Vill+"' ;";
        db.execSQL(ss);

    }
    /*    public String getVillageNameList(String talukcode,String distcode){
            SQLiteDatabase db = this.getWritableDatabase();
            Log.d(TAG, "get_sync_count: ");
            String lib_name="";
            String s="SELECT DISTINCT "+VILLAGE_NAME+" FROM "+TABLE_Villagename+ " WHERE "+STATUS+"='1' AND "+TALUKCODE+ "= '"+talukcode+
            "' AND "+DISTCODE+ "= '"+distcode+"' ;";
            Cursor c=db.rawQuery(s,null);
            if(c.getCount()!=0){
                c.moveToFirst();
                while(!c.isAfterLast()){
                    lib_name=c.getString(c.getColumnIndex(VILLAGE_NAME));
                    c.moveToNext();
                }
            }
            c.close();
            db.close();
            return lib_name;
        }*/
    public List<String> getVillageNameList(String userid){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "get_sync_count: ");
        List<String> lib_name=new ArrayList<>();
        String s="SELECT DISTINCT "+VILLAGE_NAME+" FROM "+TABLE_Villagename+ " WHERE "+STATUS+"='2' AND "+USerID+ "= '"+userid+"' ;";
        Cursor c=db.rawQuery(s,null);
        if(c.getCount()!=0){
            c.moveToFirst();
            while(!c.isAfterLast()){
                lib_name.add(c.getString(c.getColumnIndex(VILLAGE_NAME)));
                c.moveToNext();
            }
        }
        c.close();
        db.close();
        return lib_name;
    }
    public int get_count_download(String villagename) {

        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "get_count_download: ");
        int count=0;
        String query="SELECT * FROM "+TABLE_Villagename+" WHERE "+VILLAGE_NAME+"='"+villagename+"' ;";
        Log.d("cursor_query",query);
        Cursor c= db.rawQuery(query, null);
        count=c.getCount();
        Log.d("cursor_query",String.valueOf(c.getCount()));

        c.close();
        db.close();

        return count;
    }
    public int get_count_stage2(String username,String villagecode,String StageProgress) {

        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "get_count_download: ");
        int count=0;
        String query="SELECT * FROM "+TABLE_STAGE2+" WHERE "+USerID+"='"+username+"' AND "+VILLAGE_CODE+" = '"+villagecode+
                "' AND "+STAGE_PROGress+" = '"+StageProgress+"';";
        Log.d("cursor_query",query);
        Cursor c= db.rawQuery(query, null);
        count=c.getCount();
        Log.d("cursor_query",String.valueOf(c.getCount()));

        c.close();
        db.close();

        return count;
    }
    public String getgeometryshapefile(String UserID,String Villagecode,String Serveyno,String Hissano,String Stageprog){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "get_sync_count: ");
        String lib_name="";
        String s="SELECT DISTINCT "+SHAPE_FILE_GEOMETRY+" FROM "+TABLE_FULLDETAILS+ " WHERE "+ USerID+
                "= '"+UserID+"' AND "+VILLAGE_CODE +"= '"+Villagecode+"' AND "+SERVEY_NO +"= '"+Serveyno+"' AND "+
                HISSA_NO +"= '"+Hissano+"' AND "+STAGE_PROGress +"= '"+Stageprog+"' AND "+STATUS+"='1' ;";
        Cursor c=db.rawQuery(s,null);
        if(c.getCount()!=0){
            c.moveToFirst();
            while(!c.isAfterLast()){
                lib_name =c.getString(c.getColumnIndex(SHAPE_FILE_GEOMETRY));
                c.moveToNext();
            }
        }
        c.close();
        db.close();
        return lib_name;
    }
    public String getvillagecode(String VillageName){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "get_sync_count: ");
        String lib_name="";
        String s="SELECT DISTINCT "+VILLAGE_CODE+" FROM "+TABLE_Villagename+ " WHERE "+ VILLAGE_NAME+
                "= '"+VillageName+"' AND "+STATUS+"='1' ;";
        Cursor c=db.rawQuery(s,null);
        if(c.getCount()!=0){
            c.moveToFirst();
            while(!c.isAfterLast()){
                lib_name =c.getString(c.getColumnIndex(VILLAGE_CODE));
                c.moveToNext();
            }
        }
        c.close();
        db.close();
        return lib_name;
    }
    public String getvillagecodemap(String VillageName){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "get_sync_count: ");
        String lib_name="";
        String s="SELECT DISTINCT "+VILLAGE_CODE+" FROM "+TABLE_Villagename+ " WHERE "+ VILLAGE_NAME+
                "= '"+VillageName+"' AND "+STATUS+"='1' ;";
        Cursor c=db.rawQuery(s,null);
        if(c.getCount()!=0){
            c.moveToFirst();
            while(!c.isAfterLast()){
                lib_name =c.getString(c.getColumnIndex(VILLAGE_CODE));
                c.moveToNext();
            }
        }
        c.close();
        db.close();
        return lib_name;
    }
    public String getdistrict(String villagecode){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "get_sync_count: ");
        String lib_name="";
        String s="SELECT DISTINCT "+DISTRICT+" FROM "+TABLE_FULLDETAILS+ " WHERE "+ VILLAGE_CODE+
                "= '"+villagecode+"' AND "+STATUS+"='1' ;";
        Cursor c=db.rawQuery(s,null);
        if(c.getCount()!=0){
            c.moveToFirst();
            while(!c.isAfterLast()){
                lib_name =c.getString(c.getColumnIndex(DISTRICT));
                c.moveToNext();
            }
        }
        c.close();
        db.close();
        return lib_name;
    }
    public String getdistrict1(String villagecode){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "get_sync_count: ");
        String lib_name="";
        String s="SELECT DISTINCT "+DISTRICT+" FROM "+TABLE_Villagename+ " WHERE "+ VILLAGE_CODE+
                "= '"+villagecode+"' AND "+STATUS+"='1' ;";
        Cursor c=db.rawQuery(s,null);
        if(c.getCount()!=0){
            c.moveToFirst();
            while(!c.isAfterLast()){
                lib_name =c.getString(c.getColumnIndex(DISTRICT));
                c.moveToNext();
            }
        }
        c.close();
        db.close();
        return lib_name;
    }
    public String getvillagecodeafterdownload(String VillageName){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "get_sync_count: ");
        String lib_name="";
        String s="SELECT DISTINCT "+VILLAGE_CODE+" FROM "+TABLE_Villagename+ " WHERE "+ VILLAGE_NAME+
                "= '"+VillageName+"' AND "+STATUS+"='2' ;";
        Cursor c=db.rawQuery(s,null);
        if(c.getCount()!=0){
            c.moveToFirst();
            while(!c.isAfterLast()){
                lib_name =c.getString(c.getColumnIndex(VILLAGE_CODE));
                c.moveToNext();
            }
        }
        c.close();
        db.close();
        return lib_name;
    }
    public String getBenificiary_Id(String Viilagecode,String hissa,String Serveyno){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "get_sync_count: ");
        String lib_name="";
        String s="SELECT DISTINCT "+OBJ_ID+" FROM "+TABLE_FULLDETAILS+ " WHERE "+ VILLAGE_CODE+
                "= '"+Viilagecode+"' AND "+ HISSA_NO+
                "= '"+hissa+"' AND " +SERVEY_NO+
                "= '"+Serveyno+"' AND "+STATUS+"='1' ;";
        Cursor c=db.rawQuery(s,null);
        if(c.getCount()!=0){
            c.moveToFirst();
            while(!c.isAfterLast()){
                lib_name =c.getString(c.getColumnIndex(OBJ_ID));
                c.moveToNext();
            }
        }
        c.close();
        db.close();
        return lib_name;
    }
    public String getBenificiary_Id_1(String Viilagecode,String hissa,String Serveyno){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "get_sync_count: ");
        String lib_name="";
        String s="SELECT DISTINCT "+OBJ_ID+" FROM "+TABLE_FULLDETAILS+ " WHERE "+ VILLAGE_CODE+
                "= '"+Viilagecode+"' AND "+ HISSA_NO+
                "= '"+hissa+"' AND " +SERVEY_NO+
                "= '"+Serveyno+"' AND "+STATUS+"='3' ;";
        Cursor c=db.rawQuery(s,null);
        if(c.getCount()!=0){
            c.moveToFirst();
            while(!c.isAfterLast()){
                lib_name =c.getString(c.getColumnIndex(OBJ_ID));
                c.moveToNext();
            }
        }
        c.close();
        db.close();
        return lib_name;
    }
    public int get_count(String table){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "get_sync_count: ");
        String s="SELECT * FROM "+table+" ;";
        Cursor c=db.rawQuery(s,null);
        int Count=0;
        if(c!=null){
            Count=c.getCount();
        }else{
            Count=0;
        }
        c.close();
        db.close();

        return Count;
    }
    public String getVillageCode(String lgdcode){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "get_sync_count: ");
        String lib_name="";
        String s="SELECT DISTINCT "+VILLAGE_CODE+" FROM "+TABLE_Villagename+ " WHERE "+LGDCODE+"='"+lgdcode+"' ;";
        Cursor c=db.rawQuery(s,null);
        if(c.getCount()!=0){
            c.moveToFirst();
            while(!c.isAfterLast()){
                lib_name = c.getString(c.getColumnIndex(VILLAGE_CODE));
                c.moveToNext();
            }
        }
        c.close();
        db.close();
        return lib_name;
    }
    public int get_count(String table,String Username){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "get_sync_count: ");
        String s="SELECT * FROM "+table+" WHERE "+USerID+"='"+Username+"' ;";
        Cursor c=db.rawQuery(s,null);
        int Count=0;
        if(c!=null){
            Count=c.getCount();
        }else{
            Count=0;
        }
        c.close();
        db.close();

        return Count;
    }
    public boolean match_user_password(String username,String password) {

        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "get_count_download: ");
        int count=0;
        boolean flag = false;
        String query="SELECT * FROM "+TABLE_LOGIN+" WHERE "+USerID+"='"+username+"' ;";
        Log.d("cursor_query",query);
        Cursor c= db.rawQuery(query, null);
        count=c.getCount();
        Log.d("cursor_query",String.valueOf(c.getCount()));

        if(c.getCount()!=0){
            c.moveToFirst();
            String user=c.getString(c.getColumnIndex(USerID));
            String pwd=c.getString(c.getColumnIndex(PASSWORD));
            if(user.equals(username)&&pwd.equals(password)){
                flag=true;
            }
        }

        c.close();
        db.close();

        return flag;
    }
    public int getfullcount(String userid){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "get_sync_count: ");
        String s="SELECT * FROM "+TABLE_Villagename+" WHERE "+USerID+"='"+userid +"';";
        Cursor c=db.rawQuery(s,null);
        int Count=0;
        if(c!=null){
            Count=c.getCount();
        }else{
            Count=0;
        }
        c.close();
        db.close();

        return Count;
    }
    public int getfullcount_village_detaile(String userid){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "get_sync_count: ");
        String s="SELECT DISTINCT "+VILLAGE_NAME+" FROM "+TABLE_FULLDETAILS+" WHERE "+USerID+"='"+userid +"';";
        Cursor c=db.rawQuery(s,null);
        int Count=0;
        if(c!=null){
            Count=c.getCount();
        }else{
            Count=0;
        }
        c.close();
        db.close();

        return Count;
    }
    public void  changeStatus_flag_Of_VillageList(String satus,String villname){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "change status: ");
        String ss="UPDATE "+TABLE_Villagename+" SET "+STATUS+"='"+satus+"' WHERE "+VILLAGE_NAME+"='"+villname+"' ;";
        Log.d("jmkjhljk",ss);
        db.execSQL(ss);

    }

    public void  delete_viewflag_Of_DetailVillageList(String userId,String villname){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "get_sync_count: ");
        String ss="DELETE FROM "+TABLE_FULLDETAILS+" WHERE "+VILLAGE_NAME+"='"+villname+"' AND "+USerID+"='"+userId+"' ;";
        db.execSQL(ss);
    }
    public void  delete_syncData(String userId,String vilagename){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "get_sync_count: ");
        String ss="DELETE FROM "+TABLE_MASTER_MAIN+" WHERE "+USerID+"='"+userId+"' AND "+VILLAGE_NAME+"='"+vilagename+"' ;";
        db.execSQL(ss);

    }
    public List<String> getsubcategory(String assetcategory){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "get_sync_count: ");
        List<String> lib_name=new ArrayList<>();
        String s="SELECT DISTINCT "+HISSA_NO+" FROM "+TABLE_MASTER_MAIN+
                " WHERE "+VILLAGE_CODE+"='"+assetcategory + "' AND "+STATUS+"='1' ;";
        Cursor c=db.rawQuery(s,null);
        if(c.getCount()!=0){
            c.moveToFirst();
            while(!c.isAfterLast()){
                lib_name.add(c.getString(c.getColumnIndex(HISSA_NO)));
                c.moveToNext();
            }
        }
        c.close();
        db.close();
        lib_name.add(0,"Select");
        return lib_name;
    }
    public List<String> getasset_Type(String assetcategory,String assetsubcat){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "get_sync_count: ");
        List<String> lib_name=new ArrayList<>();
        String s="SELECT DISTINCT "+HISSA_NO+" FROM "+TABLE_MASTER_MAIN+
                " WHERE "+VILLAGE_CODE+"='"+assetcategory + "' AND "+VILLAGE_NAME+"='"+assetsubcat + "' AND "+STATUS+"='1' ;";
        Cursor c=db.rawQuery(s,null);
        if(c.getCount()!=0){
            c.moveToFirst();
            while(!c.isAfterLast()){
                lib_name.add(c.getString(c.getColumnIndex(HISSA_NO)));
                c.moveToNext();
            }
        }
        c.close();
        db.close();
        lib_name.add(0,"Select");
        // getdistListspinner
        return lib_name;
    }
    public void  delete_view(String Beneficiaryid,String Villagename,String Surveyno){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "get_sync_count: ");

        String ss="DELETE FROM "+TABLE_MASTER_MAIN+" WHERE "+BENEFICIARY_ID+"='"+Beneficiaryid+"' AND "+
                VILLAGE_NAME+" ='" +Villagename+"' AND "+SERVEY_NO+" ='"+Surveyno +"';";
        db.execSQL(ss);
    }
    public void  delete_downloaded_data(String villageName){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "get_sync_count: ");
        String ss="DELETE FROM "+TABLE_FULLDETAILS+" WHERE "+VILLAGE_NAME+"='"+villageName+"' ;";
        db.execSQL(ss);
    }

    public void  update_record(String id,String assetname,String locality,String remarks){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "get_sync_count: ");
        String ss="UPDATE "+TABLE_MASTER_MAIN+" SET "+HISSA_NO+"='"+assetname+"',"+
                VILLAGE_NAME+"='"+locality+"',"+remars+"='"+remarks+"' WHERE "+KEY_ID+"='"+id+"' ;";
        db.execSQL(ss);

    }

    public int  get_Total_points(String Table){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "get_sync_count: ");
        int count = 0;
        String ss="SELECT * FROM "+Table+" WHERE "+STATUS+"='1' ;";
        Cursor c=db.rawQuery(ss,null);
        if(c.getCount()!=0){
            count=c.getCount();
        }
        c.close();
        db.close();
        return  count;
    }
    public void deletrecord_Seed(String id) {

        SQLiteDatabase db = this.getWritableDatabase();
        String ss="DELETE FROM "+TABLE_MASTER_MAIN+" WHERE "+KEY_ID+"='"+id+"' ;";
        db.execSQL(ss);

    }

    public int  get_sync_count(){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "get_sync_count: ");
        int count = 0;
        String ss="SELECT * FROM "+TABLE_MASTER_MAIN+" WHERE "+STATUS+"= '1' ;";
        Cursor c=db.rawQuery(ss,null);
        if(c.getCount()!=0){
            count=c.getCount();
        }
        c.close();
        db.close();
        return  count;
    }
    public void deletrecord_sync_asset(String sample_id) {

        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "deteleCropDetailByID: ");
        db.delete(TABLE_MASTER_MAIN, BENEFICIARY_ID + " = ?", new String[]{String.valueOf(sample_id)});
        db.close();

    }
    public List<String> getbeneficiarylst(){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "get_sync_count: ");
        List<String> lib_name=new ArrayList<>();
        String s="SELECT DISTINCT "+OBJ_ID+" FROM "+TABLE_FULLDETAILS+";";
        //  " WHERE "+USerID+"='"+UserId + "' AND "+VILLAGE_CODE+"='"+Vllagecode + "' AND "+STATUS+"='1' ;";
        Cursor c=db.rawQuery(s,null);
        if(c.getCount()!=0){
            c.moveToFirst();
            while(!c.isAfterLast()){
                lib_name.add(c.getString(c.getColumnIndex(OBJ_ID)));
                c.moveToNext();
            }
        }
        c.close();
        db.close();
        return lib_name;
    }
    public List<String> getvillagecode(){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "get_sync_count: ");
        List<String> lib_name=new ArrayList<>();
        String s="SELECT DISTINCT "+VILLAGE_CODE+" FROM "+TABLE_FULLDETAILS+";";
        //  " WHERE "+USerID+"='"+UserId + "' AND "+VILLAGE_CODE+"='"+Vllagecode + "' AND "+STATUS+"='1' ;";
        Cursor c=db.rawQuery(s,null);
        if(c.getCount()!=0){
            c.moveToFirst();
            while(!c.isAfterLast()){
                lib_name.add(c.getString(c.getColumnIndex(VILLAGE_CODE)));
                c.moveToNext();
            }
        }
        c.close();
        db.close();
        return lib_name;
    }
    public List<String> getstage_lst(String UserId,String Vllagecode){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "get_sync_count: ");
        List<String> lib_name=new ArrayList<>();
        String s="SELECT DISTINCT "+STAGE_PROGress+" FROM "+TABLE_FULLDETAILS+
                " WHERE "+USerID+"='"+UserId + "' AND "+VILLAGE_CODE+"='"+Vllagecode + "' AND "+STATUS+"='1' ;";
        Cursor c=db.rawQuery(s,null);
        if(c.getCount()!=0){
            c.moveToFirst();
            while(!c.isAfterLast()){
                if(c.getString(c.getColumnIndex(STAGE_PROGress)).equals("")) {
                    lib_name.add("0");
                }
                else if(c.getString(c.getColumnIndex(STAGE_PROGress)).equals("Stage1")) {
                    lib_name.add("1");
                }
                else if(c.getString(c.getColumnIndex(STAGE_PROGress)).equals("Stage2")) {
                    lib_name.add("2");
                }
                else if(c.getString(c.getColumnIndex(STAGE_PROGress)).equals("Stage3")) {
                    lib_name.add("3");
                }
                c.moveToNext();
            }
        }
        c.close();
        db.close();
        lib_name.add(0,"Select");
        return lib_name;
    }
    public void update_fullDetails(String benificiaryId,String stageprog,String ShapeFile) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "get_sync_count: ");

        String ss="UPDATE "+TABLE_FULLDETAILS+" SET "+STAGE_PROGress+"='"+stageprog+"',"+SHAPE_FILE_GEOMETRY+"='"+ShapeFile+"' "+
                " WHERE "+OBJ_ID+"='"+benificiaryId+"' ;";
        db.execSQL(ss);
    }
    public void update_SyncTable(String benificiaryId,String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "get_sync_count: ");
        String ss="UPDATE "+TABLE_MASTER_MAIN+" SET "+ STATUS+"='"+status+"' WHERE "+BENEFICIARY_ID+"='"+benificiaryId+"' ;";
        db.execSQL(ss);
    }
    public void update_collectedvillage(String villagecode,String status,String stage,String beneficiaryid) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "get_sync_count update_collectedvillage: ");
        String ss="UPDATE "+TABLE_FULLDETAILS+" SET "+ STATUS+"='"+status+"' WHERE "+VILLAGE_CODE+"='"+villagecode+
                "' AND "+OBJ_ID+" = '"+beneficiaryid+
                "' AND "+STAGE_PROGress+" = '"+stage+"' ;";
        Log.d(TAG, ss);
        db.execSQL(ss);
    }
    public List<String> get_listVillagecode_frombeneficiaryId(){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "get_sync_count: ");
        List<String> lib_name=new ArrayList<>();
        String s="SELECT DISTINCT "+VILLAGE_CODE+" FROM "+TABLE_MASTER_MAIN+ " WHERE "+STATUS+"='2' ;";
        Cursor c=db.rawQuery(s,null);
        if(c.getCount()!=0){
            c.moveToFirst();
            while(!c.isAfterLast()){
                lib_name.add(c.getString(c.getColumnIndex(VILLAGE_CODE)));
                c.moveToNext();
            }
        }
        c.close();
        db.close();
        return lib_name;
    }

    public List<String> get_listbeneficiaryId(String Villagecode){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "get_sync_count: ");
        List<String> lib_name=new ArrayList<>();
        String s="SELECT DISTINCT "+OBJ_ID+" FROM "+TABLE_FULLDETAILS+ " WHERE "+VILLAGE_CODE+"='"+Villagecode+"' ;";
        Cursor c=db.rawQuery(s,null);
        if(c.getCount()!=0){
            c.moveToFirst();
            while(!c.isAfterLast()){
                lib_name.add(c.getString(c.getColumnIndex(OBJ_ID)));
                c.moveToNext();
            }
        }
        c.close();
        db.close();
        return lib_name;
    }
}