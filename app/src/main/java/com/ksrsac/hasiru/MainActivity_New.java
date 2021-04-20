package com.ksrsac.hasiru;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.esri.arcgisruntime.arcgisservices.LabelDefinition;
import com.esri.arcgisruntime.concurrent.ListenableFuture;
import com.esri.arcgisruntime.data.Feature;
import com.esri.arcgisruntime.data.FeatureQueryResult;
import com.esri.arcgisruntime.data.QueryParameters;
import com.esri.arcgisruntime.data.ShapefileFeatureTable;
import com.esri.arcgisruntime.geometry.Envelope;
import com.esri.arcgisruntime.geometry.Geometry;
import com.esri.arcgisruntime.geometry.GeometryEngine;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.PointCollection;
import com.esri.arcgisruntime.geometry.SpatialReference;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.layers.ArcGISMapImageLayer;
import com.esri.arcgisruntime.layers.FeatureLayer;
import com.esri.arcgisruntime.loadable.LoadStatus;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.Viewpoint;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.LocationDisplay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.SimpleLineSymbol;
import com.esri.arcgisruntime.symbology.TextSymbol;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.ksrsac.hasiru.constants.Constants;
import com.ksrsac.hasiru.database.SqliteHelper;
import com.ksrsac.hasiru.model.AddDataModel;
import com.ksrsac.hasiru.model.ItemData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity_New extends AppCompatActivity implements  GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener{
    ImageView okbutton;
    int responsecode1;
    private String currentversion;
    Double curver, latver;
    String latestversion;
    Dialog dialog;
    ImageView BasemapSelection;
    StringBuilder responseOutput1;
    String responsestring1,result2,sss2;
    ImageView showmap;
    LinearLayout linearlayout_offline;
    Intent launchIntent;
    private com.esri.arcgisruntime.mapping.view.Graphic Graphic = null;
    Basemap basemap1;
    Point clickPoint=null;
    Spinner basemapsp;
    Geometry distancefrompolygon=null;
    String Serveynostr="";
    int countresume=0;
    String villagenameStr="";
    String Mapchangeflag="0";
    StringBuilder responseOutput;
    int responsecode;
    TextView latitude,longitude,Metertxt,km_txt,Foot_txt,Miles_txt,Sq_meterstxt,Sq_Kmtxt,Sq_Foottxt,Sq_Milestxt;
    private GraphicsOverlay myGraphicalLayer=null,gsurvey=null;
    ProgressDialog progress;
    String str_dist="",str_villagemis="",str_taluk="",str_msg="";
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    File wallpaperDirectory;
    String responsestring,result1,sss1;
    MapView mView;
    ArcGISMap map;
    private ShapefileFeatureTable shapefileFeatureTable;
    FeatureLayer featureLayer;
    NetworkInfo wifi;
    NetworkInfo data;
    private String Drawflag="";
    ConnectivityManager Manager;
    private LocationDisplay mLocationDisplay;
    String str_image1_lat = "0.0", str_image1_lng = "0.0";
    private com.esri.arcgisruntime.mapping.view.Graphic polylineGraphic1 = null, LineGraphic = null;
    private static PointCollection auto_points_polly;
    Envelope envelope;
    AddDataModel lmodel = new AddDataModel();
    QueryParameters query;
    LocationListener mlocListener;
    LocationManager mlocManager;
    static Location loc1 = null;
    LinearLayout linearlayout_basemap;
    //String serverFilePath="http://stg1.ksrsac.in/ksda_shape/";
    String serverFilePath="http://stg1.ksrsac.in/ksda_shape/";
    String currentDateandTime = " ";
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 96;
    private TextView latlng1;
    private int requestCode = 2;
    String[] reqPermissions = new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION};
    LinearLayout lin_drawtools;
    ImageView refresh, current_loc;
    String StrVillage = "", StrVillageCode = "";
    TextView villagenametxt;
    ImageView point,line,polygone;
    LocationManager locationManager;
    private Bundle b=null;
    String Lgdcodes="",dept_code="",app_code="",type="";
    Spinner offlinesp;
    Button PolygonDistance,PointDistance;
    LinearLayout detailslatlong,distanceLinear,areaLinear,lin_distanetool;
    ImageButton Measurement,Loc_Report,Distacetool;
    List<String> map_list=new ArrayList<String>();
    String Packagename="";
    String Classname="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_testing);
        onViewCreated();
        mView = (MapView)findViewById(R.id.mapView1);
        offlinesp = findViewById(R.id.offlinesp);
        okbutton = findViewById(R.id.okbutton);
        showmap = findViewById(R.id.map);
        basemapsp = findViewById(R.id.basemapsp);
        BasemapSelection = findViewById(R.id.BasemapSelection);
        linearlayout_basemap = findViewById(R.id.linearlayout_basemap);
        // oknbuttonlinear = findViewById(R.id.oknbuttonlinear);
        villagenametxt = findViewById(R.id.villagename);
        lin_distanetool = findViewById(R.id.lin_distanetool);
        refresh = findViewById(R.id.refresh);
        PolygonDistance = findViewById(R.id.dis_poly);
        PointDistance = findViewById(R.id.dis_point);
        current_loc = findViewById(R.id.current_loc);
        lin_drawtools = findViewById(R.id.lin_action);
        linearlayout_offline = findViewById(R.id.linearlayout_village);
        latitude = findViewById(R.id.lat);
        longitude = findViewById(R.id.lng);
        Metertxt = findViewById(R.id.meters);
        km_txt = findViewById(R.id.km);
        Foot_txt = findViewById(R.id.foot);
        Miles_txt = findViewById(R.id.miles);
        Sq_meterstxt = findViewById(R.id.sqmeters);
        Sq_Kmtxt = findViewById(R.id.sq_km);
        Sq_Foottxt = findViewById(R.id.sq_foot);
        Sq_Milestxt = findViewById(R.id.sq_miles);
        point = findViewById(R.id.point);
        line = findViewById(R.id.line);
        polygone = findViewById(R.id.polygon);
        latlng1 = findViewById(R.id.tvpoint);
        detailslatlong = findViewById(R.id.detailslatlong);
        distanceLinear = findViewById(R.id.distance);
        areaLinear = findViewById(R.id.area);
        Measurement = findViewById(R.id.measurement);
        Loc_Report = findViewById(R.id.locreport);
        Distacetool = findViewById(R.id.distancetool);

        /*        detailslatlong.setVisibility(View.INVISIBLE);
        distanceLinear.setVisibility(View.INVISIBLE);
        areaLinear.setVisibility(View.INVISIBLE);
        lin_distanetool.setVisibility(View.INVISIBLE);*/
        // lin_drawtools.setVisibility(View.GONE);
        //  okbutton.setVisibility(View.GONE);
        // oknbuttonlinear.setVisibility(View.GONE);
        Manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        wifi = Manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        data = Manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        myGraphicalLayer = new GraphicsOverlay();
        gsurvey = new GraphicsOverlay();

        auto_points_polly = new PointCollection(SpatialReferences.getWebMercator());
        //linearlayout_offline.setVisibility(View.INVISIBLE);
        if ((wifi.isConnected() || data.isConnected())) {
            map = new ArcGISMap(Basemap.createTopographic());
            mView.setMap(map);
        } else {
            map = new ArcGISMap();
            mView.setMap(map);
        }
        mView.getGraphicsOverlays().add(myGraphicalLayer);
        mView.getGraphicsOverlays().add(gsurvey);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            Constants.MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            Constants.MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                }
            }
        }

        linearlayout_basemap.setVisibility(View.GONE);

        map_list.add(getString(R.string.select_basemap));
        map_list.add(getString(R.string.kgistopo));
        map_list.add(getString(R.string.kgissatellite));
        map_list.add(getString(R.string.topo));
        map_list.add(getString(R.string.satellite));
        ArrayAdapter<String> mapadapter = new ArrayAdapter<String>(MainActivity_New.this,
                R.layout.spinner_item, map_list){
            @Override
            public boolean isEnabled(int position){
                return position != 0;
            }
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    tv.setTextColor(Color.YELLOW);
                    tv.setBackgroundResource(R.color.colorPrimary);
                }
                else {
                    tv.setTextColor(Color.WHITE);
                    tv.setBackgroundResource(R.color.colorPrimaryDark);

                }
                return view;
            }
        };
        mapadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        basemapsp.setAdapter(mapadapter);

        basemapsp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Log.d("SelectedItems111111",basemapsp.getSelectedItem().toString());
                if (position != 0) {
                    Log.d("SelectedItems",basemapsp.getSelectedItem().toString());
                    mLocationDisplay = mView.getLocationDisplay();
                    mLocationDisplay.addDataSourceStatusChangedListener(new LocationDisplay.DataSourceStatusChangedListener() {
                        @Override
                        public void onStatusChanged(LocationDisplay.DataSourceStatusChangedEvent dataSourceStatusChangedEvent) {
                            if (dataSourceStatusChangedEvent.isStarted()) {
                                return;
                            }
                            if (dataSourceStatusChangedEvent.getError() == null)
                                return;
                            boolean permissionCheck1 = ContextCompat.checkSelfPermission(MainActivity_New.this, reqPermissions[0]) ==
                                    PackageManager.PERMISSION_GRANTED;
                            boolean permissionCheck2 = ContextCompat.checkSelfPermission(MainActivity_New.this, reqPermissions[1]) ==
                                    PackageManager.PERMISSION_GRANTED;

                            if (!(permissionCheck1 && permissionCheck2)) {
                                ActivityCompat.requestPermissions(MainActivity_New.this, reqPermissions, requestCode);
                            } else {
                                String message = String.format("Error in DataSourceStatusChangedListener: %s", dataSourceStatusChangedEvent
                                        .getSource().getLocationDataSource().getError().getMessage());
                                Toast.makeText(MainActivity_New.this, message, Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    mLocationDisplay.setAutoPanMode(LocationDisplay.AutoPanMode.RECENTER);
                    if (!mLocationDisplay.isStarted()) {
                        //     loading.dismiss();
                        mLocationDisplay.startAsync();
                        Log.d("Lastlocation111", String.valueOf(mLocationDisplay.getLocation().getTimeStamp()));
                    }
                    ArcGISMapImageLayer kgistopo = new ArcGISMapImageLayer("https://kgis.ksrsac.in/kgismaps1/rest/services/Base_Layer/Portal_BaseMapV4/MapServer");
                    ArcGISMapImageLayer kgissatellite = new ArcGISMapImageLayer("https://kgis.ksrsac.in/kgismaps1/rest/services/Satellite/MSS50CM/MapServer");

                    //  if (adapterView.getId() == R.id.basemapsp) {
                    if (basemapsp.getSelectedItem().toString().equalsIgnoreCase(getString(R.string.topo))) {
                        linearlayout_offline.setVisibility(View.GONE);
                        Mapchangeflag="1";
                        map.setBasemap(Basemap.createTopographic());
                        mView.setMap(map);
                        if (featureLayer != null) {
                            mView.getMap().getOperationalLayers().remove(featureLayer);
                            File myFile = new File(Environment.getExternalStorageDirectory() + "/HASIRU/" + StrVillageCode + ".shp");
                            Log.d("shape_File.....", "shape file" + Environment.getExternalStorageDirectory() + "/HASIRU/"  + StrVillageCode + ".shp");
                            if (myFile.exists()) {
                                shapefileFeatureTable = new ShapefileFeatureTable(
                                        Environment.getExternalStorageDirectory() + "/HASIRU/" + StrVillageCode + ".shp");
                                Log.d("shape_File1223333", String.valueOf(Environment.getExternalStorageDirectory() + "/HASIRU/" + StrVillageCode + ".shp"));
                                shapefileFeatureTable.loadAsync();
                                shapefileFeatureTable.addDoneLoadingListener(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (shapefileFeatureTable.getLoadStatus() == LoadStatus.LOADED) {
                                            featureLayer = new FeatureLayer(shapefileFeatureTable);
                                            featureLayer.setOpacity(0.7f);
                                            TextSymbol textSymbol = new TextSymbol();
                                            textSymbol.setSize(20);
                                            textSymbol.setColor(0xFF0000FF);
                                            textSymbol.setHaloColor(0xFFFFFF00);
                                            textSymbol.setHaloWidth(2);
                                            JsonObject json = new JsonObject();
                                            JsonObject expressionInfo = new JsonObject();
                                            expressionInfo.add("expression", new JsonPrimitive("$feature.SurveyNumb"));
                                            json.add("labelExpressionInfo", expressionInfo);

                                            json.add("symbol", new JsonParser().parse(textSymbol.toJson()));
                                            LabelDefinition labelDefinition = LabelDefinition.fromJson(json.toString());
                                            featureLayer.getLabelDefinitions().add(labelDefinition);
                                            featureLayer.setLabelsEnabled(true);
                                            mView.getMap().getOperationalLayers().add(featureLayer);
                                            mView.setViewpointAsync(new Viewpoint(featureLayer.getFullExtent()));
                                        } else {
                                            showPopUpMsg("Shapefile feature table failed to load:.","Alert");
                                            // Toast.makeText(MainActivity_New.this, "Shapefile feature table failed to load: ", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }

                        }
                    } else if (basemapsp.getSelectedItem().toString().equalsIgnoreCase(getString(R.string.satellite))) {
                        linearlayout_offline.setVisibility(View.GONE);
                        map.setBasemap(Basemap.createImagery());
                        Mapchangeflag="2";
                        mView.setMap(map);
                        if (featureLayer != null) {
                            mView.getMap().getOperationalLayers().remove(featureLayer);
                            File myFile = new File(Environment.getExternalStorageDirectory() + "/HASIRU/" + StrVillageCode + ".shp");
                            Log.d("shape_File.....", "shape file" + Environment.getExternalStorageDirectory() + "/HASIRU/" + StrVillageCode + ".shp");
                            if (myFile.exists()) {
                                shapefileFeatureTable = new ShapefileFeatureTable(
                                        Environment.getExternalStorageDirectory() + "/HASIRU/" + StrVillageCode + ".shp");
                                Log.d("shape_File1223333", String.valueOf(Environment.getExternalStorageDirectory() + "/HASIRU/" + StrVillageCode + ".shp"));
                                shapefileFeatureTable.loadAsync();
                                shapefileFeatureTable.addDoneLoadingListener(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (shapefileFeatureTable.getLoadStatus() == LoadStatus.LOADED) {
                                            featureLayer = new FeatureLayer(shapefileFeatureTable);
                                            featureLayer.setOpacity(0.7f);
                                            TextSymbol textSymbol = new TextSymbol();
                                            textSymbol.setSize(20);
                                            textSymbol.setColor(0xFF0000FF);
                                            textSymbol.setHaloColor(0xFFFFFF00);
                                            textSymbol.setHaloWidth(2);
                                            JsonObject json = new JsonObject();
                                            JsonObject expressionInfo = new JsonObject();
                                            expressionInfo.add("expression", new JsonPrimitive("$feature.SurveyNumb"));
                                            json.add("labelExpressionInfo", expressionInfo);

                                            json.add("symbol", new JsonParser().parse(textSymbol.toJson()));
                                            LabelDefinition labelDefinition = LabelDefinition.fromJson(json.toString());
                                            featureLayer.getLabelDefinitions().add(labelDefinition);
                                            featureLayer.setLabelsEnabled(true);
                                            mView.getMap().getOperationalLayers().add(featureLayer);
                                            mView.setViewpointAsync(new Viewpoint(featureLayer.getFullExtent()));
                                        } else {
                                            showPopUpMsg("Shapefile feature table failed to load:.","Alert");
                                            //  Toast.makeText(MainActivity_New.this, "Shapefile feature table failed to load: ", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }

                        }
                    } else if (basemapsp.getSelectedItem().toString().equalsIgnoreCase(getString(R.string.kgistopo))) {
                        linearlayout_offline.setVisibility(View.GONE);
                        basemap1 = new Basemap(kgistopo);
                        map = new ArcGISMap(basemap1);
                        Mapchangeflag="3";
                        mView.setMap(map);
                        if (featureLayer != null) {
                            mView.getMap().getOperationalLayers().remove(featureLayer);
                            File myFile = new File(Environment.getExternalStorageDirectory() + "/HASIRU/"  + StrVillageCode + ".shp");
                            Log.d("shape_File.....", "shape file" + Environment.getExternalStorageDirectory() + "/HASIRU/" + StrVillageCode + ".shp");
                            if (myFile.exists()) {
                                shapefileFeatureTable = new ShapefileFeatureTable(
                                        Environment.getExternalStorageDirectory() + "/HASIRU/" + StrVillageCode + ".shp");
                                Log.d("shape_File1223333", String.valueOf(Environment.getExternalStorageDirectory() + "/HASIRU/" + StrVillageCode + ".shp"));
                                shapefileFeatureTable.loadAsync();
                                shapefileFeatureTable.addDoneLoadingListener(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (shapefileFeatureTable.getLoadStatus() == LoadStatus.LOADED) {
                                            featureLayer = new FeatureLayer(shapefileFeatureTable);
                                            featureLayer.setOpacity(0.7f);
                                            TextSymbol textSymbol = new TextSymbol();
                                            textSymbol.setSize(20);
                                            textSymbol.setColor(0xFF0000FF);
                                            textSymbol.setHaloColor(0xFFFFFF00);
                                            textSymbol.setHaloWidth(2);
                                            JsonObject json = new JsonObject();
                                            JsonObject expressionInfo = new JsonObject();
                                            expressionInfo.add("expression", new JsonPrimitive("$feature.SurveyNumb"));
                                            json.add("labelExpressionInfo", expressionInfo);
                                            json.add("symbol", new JsonParser().parse(textSymbol.toJson()));
                                            LabelDefinition labelDefinition = LabelDefinition.fromJson(json.toString());
                                            featureLayer.getLabelDefinitions().add(labelDefinition);
                                            featureLayer.setLabelsEnabled(true);
                                            mView.getMap().getOperationalLayers().add(featureLayer);
                                            mView.setViewpointAsync(new Viewpoint(featureLayer.getFullExtent()));
                                        } else {
                                            showPopUpMsg("Shapefile feature table failed to load:.","Alert");
                                            //  Toast.makeText(MainActivity_New.this, "Shapefile feature table failed to load: ", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }
                        }
                    } else if (basemapsp.getSelectedItem().toString().equalsIgnoreCase(getString(R.string.kgissatellite))) {
                        linearlayout_offline.setVisibility(View.GONE);
                        basemap1 = new Basemap(kgissatellite);
                        map = new ArcGISMap(basemap1);
                        Mapchangeflag="4";
                        mView.setMap(map);
                        if (featureLayer != null) {
                            mView.getMap().getOperationalLayers().remove(featureLayer);
                            File myFile = new File(Environment.getExternalStorageDirectory() + "/HASIRU/"  + StrVillageCode + ".shp");
                            Log.d("shape_File.....", "shape file" + Environment.getExternalStorageDirectory() + "/HASIRU/" + StrVillageCode + ".shp");
                            if (myFile.exists()) {
                                shapefileFeatureTable = new ShapefileFeatureTable(
                                        Environment.getExternalStorageDirectory() + "/HASIRU/" + StrVillageCode + ".shp");
                                Log.d("shape_File1223333", String.valueOf(Environment.getExternalStorageDirectory() + "/HASIRU/" + StrVillageCode + ".shp"));
                                shapefileFeatureTable.loadAsync();
                                shapefileFeatureTable.addDoneLoadingListener(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (shapefileFeatureTable.getLoadStatus() == LoadStatus.LOADED) {
                                            featureLayer = new FeatureLayer(shapefileFeatureTable);
                                            featureLayer.setOpacity(0.7f);
                                            TextSymbol textSymbol = new TextSymbol();
                                            textSymbol.setSize(20);
                                            textSymbol.setColor(0xFF0000FF);
                                            textSymbol.setHaloColor(0xFFFFFF00);
                                            textSymbol.setHaloWidth(2);
                                            JsonObject json = new JsonObject();
                                            JsonObject expressionInfo = new JsonObject();
                                            expressionInfo.add("expression", new JsonPrimitive("$feature.SurveyNumb"));
                                            json.add("labelExpressionInfo", expressionInfo);

                                            json.add("symbol", new JsonParser().parse(textSymbol.toJson()));
                                            LabelDefinition labelDefinition = LabelDefinition.fromJson(json.toString());
                                            featureLayer.getLabelDefinitions().add(labelDefinition);
                                            featureLayer.setLabelsEnabled(true);
                                            mView.getMap().getOperationalLayers().add(featureLayer);
                                            mView.setViewpointAsync(new Viewpoint(featureLayer.getFullExtent()));
                                        } else {
                                            showPopUpMsg("Shapefile feature table failed to load:.","Alert");
                                            //  Toast.makeText(MainActivity_New.this, "Shapefile feature table failed to load: ", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }

                        }
                    }
                    else{
                        Mapchangeflag="0";
                    }
                    //  }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
      /*  loading = new ProgressDialog(MainActivity_New.this);
        loading.setCancelable(false);
        loading.setTitle("Loading Map");
        loading.show();*/

           mLocationDisplay = mView.getLocationDisplay();
        mLocationDisplay.addDataSourceStatusChangedListener(new LocationDisplay.DataSourceStatusChangedListener() {
            @Override
            public void onStatusChanged(LocationDisplay.DataSourceStatusChangedEvent dataSourceStatusChangedEvent) {
                if (dataSourceStatusChangedEvent.isStarted()) {
                    return;
                }
                if (dataSourceStatusChangedEvent.getError() == null)
                    return;
                boolean permissionCheck1 = ContextCompat.checkSelfPermission(MainActivity_New.this, reqPermissions[0]) ==
                        PackageManager.PERMISSION_GRANTED;
                boolean permissionCheck2 = ContextCompat.checkSelfPermission(MainActivity_New.this, reqPermissions[1]) ==
                        PackageManager.PERMISSION_GRANTED;

                if (!(permissionCheck1 && permissionCheck2)) {
                    ActivityCompat.requestPermissions(MainActivity_New.this, reqPermissions, requestCode);
                } else {
                    String message = String.format("Error in DataSourceStatusChangedListener: %s", dataSourceStatusChangedEvent
                            .getSource().getLocationDataSource().getError().getMessage());
                    Toast.makeText(MainActivity_New.this, message, Toast.LENGTH_LONG).show();
                }
            }
        });
        mLocationDisplay.setAutoPanMode(LocationDisplay.AutoPanMode.RECENTER);
        if (!mLocationDisplay.isStarted()) {
            //  loading.dismiss();
            mLocationDisplay.startAsync();
//            Log.d("Lastlocation", String.valueOf(mLocationDisplay.getLocation().getTimeStamp()));
        }

        SpinnerAdapter1 villadapter = new SpinnerAdapter1(MainActivity_New.this, R.layout.spinner_delete, get_tpk_files());
        offlinesp.setAdapter(villadapter);
        b = getIntent().getExtras();
        Log.d("Bundle", String.valueOf(b));


         current_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLocationDisplay = mView.getLocationDisplay();
                mLocationDisplay.addDataSourceStatusChangedListener(new LocationDisplay.DataSourceStatusChangedListener() {
                    @Override
                    public void onStatusChanged(LocationDisplay.DataSourceStatusChangedEvent dataSourceStatusChangedEvent) {
                        if (dataSourceStatusChangedEvent.isStarted()) {
                            return;
                        }
                        if (dataSourceStatusChangedEvent.getError() == null)
                            return;
                        boolean permissionCheck1 = ContextCompat.checkSelfPermission(MainActivity_New.this, reqPermissions[0]) ==
                                PackageManager.PERMISSION_GRANTED;
                        boolean permissionCheck2 = ContextCompat.checkSelfPermission(MainActivity_New.this, reqPermissions[1]) ==
                                PackageManager.PERMISSION_GRANTED;

                        if (!(permissionCheck1 && permissionCheck2)) {
                            ActivityCompat.requestPermissions(MainActivity_New.this, reqPermissions, requestCode);
                        } else {
                            String message = String.format("Error in DataSourceStatusChangedListener: %s", dataSourceStatusChangedEvent
                                    .getSource().getLocationDataSource().getError().getMessage());
                            Toast.makeText(MainActivity_New.this, message, Toast.LENGTH_LONG).show();
                        }
                    }
                });
                mLocationDisplay.setAutoPanMode(LocationDisplay.AutoPanMode.RECENTER);
                if (!mLocationDisplay.isStarted())
                    mLocationDisplay.startAsync();
            }
        });
        point.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("point","point");
                // polygone.setBackgroundColor(getResources().getColor(R.color.gray));
                //  line.setBackgroundColor(getResources().getColor(R.color.gray));
                polygone.setImageResource(R.drawable.polygon);
                line.setImageResource(R.drawable.line);
                //  point.setBackgroundColor(getResources().getColor(R.color.blue));
                point.setImageResource(R.drawable.point1);
                Drawflag="point"   ;
                if (myGraphicalLayer != null) {
                    myGraphicalLayer.getGraphics().clear();
                }
                if(auto_points_polly!=null){
                    auto_points_polly.clear();
                }
                detailslatlong.setVisibility(View.INVISIBLE);
                distanceLinear.setVisibility(View.INVISIBLE);
                areaLinear.setVisibility(View.INVISIBLE);
            }
        });
        line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /*    polygone.setBackgroundColor(getResources().getColor(R.color.gray));
                line.setBackgroundColor(getResources().getColor(R.color.blue));
                point.setBackgroundColor(getResources().getColor(R.color.gray));*/

                polygone.setImageResource(R.drawable.polygon);
                line.setImageResource(R.drawable.line2);
                point.setImageResource(R.drawable.point);
                Log.d("line","line");
                Drawflag="line";
                if (myGraphicalLayer != null) {
                    myGraphicalLayer.getGraphics().clear();
                }
                if(auto_points_polly!=null){
                    auto_points_polly.clear();
                }
                detailslatlong.setVisibility(View.INVISIBLE);
                distanceLinear.setVisibility(View.INVISIBLE);
                areaLinear.setVisibility(View.INVISIBLE);
            }
        });
        polygone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /*    polygone.setBackgroundColor(getResources().getColor(R.color.blue));
                line.setBackgroundColor(getResources().getColor(R.color.gray));
                point.setBackgroundColor(getResources().getColor(R.color.gray));*/
                polygone.setImageResource(R.drawable.polygon1);
                line.setImageResource(R.drawable.line);
                point.setImageResource(R.drawable.point);
                Log.d("polygone","polygone");
                Drawflag="polygon";
                // myGraphicalLayer.removeAll();
                if (myGraphicalLayer != null) {
                    myGraphicalLayer.getGraphics().clear();
                }
                if(auto_points_polly!=null){
                    auto_points_polly.clear();
                }

                detailslatlong.setVisibility(View.INVISIBLE);
                distanceLinear.setVisibility(View.INVISIBLE);
                areaLinear.setVisibility(View.INVISIBLE);
            }
        });
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drawflag="";
                detailslatlong.setVisibility(View.INVISIBLE);
                distanceLinear.setVisibility(View.INVISIBLE);
                areaLinear.setVisibility(View.INVISIBLE);
                if (myGraphicalLayer != null) {
                    myGraphicalLayer.getGraphics().clear();
                }
                if(auto_points_polly!=null){
                    auto_points_polly.clear();
                }
            }
        });
        Measurement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                basemapsp.setSelection(0);
                linearlayout_basemap.setVisibility(View.GONE);
                linearlayout_offline.setVisibility(View.INVISIBLE);
                okbutton.setBackgroundColor(getResources().getColor(R.color.gray));
                Measurement.setBackgroundColor(getResources().getColor(R.color.blue));
                Loc_Report.setBackgroundColor(getResources().getColor(R.color.gray));
                Distacetool.setBackgroundColor(getResources().getColor(R.color.gray));
                BasemapSelection.setBackgroundColor(getResources().getColor(R.color.gray));
                /*polygone.setBackgroundColor(getResources().getColor(R.color.gray));
                line.setBackgroundColor(getResources().getColor(R.color.gray));
                point.setBackgroundColor(getResources().getColor(R.color.gray));*/
                polygone.setImageResource(R.drawable.polygon);
                line.setImageResource(R.drawable.line);
                point.setImageResource(R.drawable.point);
                lin_drawtools.setVisibility(View.VISIBLE);
                lin_distanetool.setVisibility(View.INVISIBLE);
                Drawflag="";
                detailslatlong.setVisibility(View.INVISIBLE);
                distanceLinear.setVisibility(View.INVISIBLE);
                areaLinear.setVisibility(View.INVISIBLE);
                if (myGraphicalLayer != null) {
                    myGraphicalLayer.getGraphics().clear();
                }
                if(auto_points_polly!=null){
                    auto_points_polly.clear();
                }
            }
        });
        PolygonDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(loc1!=null) {
                    if (distancefrompolygon != null) {
                        show_distance(distancefrompolygon);
                    }
                    else{
                        showPopUpMsg("Please click on the map to get the distance from polygon.","Alert");
                        //   Toast.makeText(MainActivity_New.this,"Please click on the map to get the distance from polygon.",Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    showPopUpMsg("location service not available.","Alert");
                    //  Toast.makeText(MainActivity_New.this,"location service not available",Toast.LENGTH_LONG).show();
                }
            }
        });
        PointDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(loc1!=null) {
                    if(clickPoint!=null){
                        show_distancelatlong(clickPoint.getX(),clickPoint.getY());
                    }
                    else{
                        showPopUpMsg("Please click on the map to get the distance.","Alert");
                        // Toast.makeText(MainActivity_New.this,"Please click on the map to get the distance.",Toast.LENGTH_LONG).show();
                    }
                    //  show_distancelatlong();
                }
                else{
                    showPopUpMsg("location service not available.","Alert");
                    // Toast.makeText(MainActivity_New.this,"location service not available",Toast.LENGTH_LONG).show();
                }
            }
        });
        showmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearlayout_basemap.setVisibility(View.GONE);
                basemapsp.setSelection(0);
                if (featureLayer != null) {
                    mView.getMap().getOperationalLayers().remove(featureLayer);
                    File myFile = new File(Environment.getExternalStorageDirectory() + "/HASIRU/" + StrVillageCode + ".shp");
                    Log.d("shape_File.....", "shape file" + Environment.getExternalStorageDirectory() + "/HASIRU/"  + StrVillageCode + ".shp");
                    if (myFile.exists()) {
                        shapefileFeatureTable = new ShapefileFeatureTable(
                                Environment.getExternalStorageDirectory() + "/HASIRU/" + StrVillageCode + ".shp");
                        Log.d("shape_File1223333", String.valueOf(Environment.getExternalStorageDirectory() + "/HASIRU/" + StrVillageCode + ".shp"));
                        shapefileFeatureTable.loadAsync();
                        shapefileFeatureTable.addDoneLoadingListener(new Runnable() {
                            @Override
                            public void run() {
                                if (shapefileFeatureTable.getLoadStatus() == LoadStatus.LOADED) {
                                    featureLayer = new FeatureLayer(shapefileFeatureTable);
                                    featureLayer.setOpacity(0.7f);
                                    TextSymbol textSymbol = new TextSymbol();
                                    textSymbol.setSize(20);
                                    textSymbol.setColor(0xFF0000FF);
                                    textSymbol.setHaloColor(0xFFFFFF00);
                                    textSymbol.setHaloWidth(2);
                                    JsonObject json = new JsonObject();
                                    JsonObject expressionInfo = new JsonObject();
                                    expressionInfo.add("expression", new JsonPrimitive("$feature.SurveyNumb"));
                                    json.add("labelExpressionInfo", expressionInfo);

                                    json.add("symbol", new JsonParser().parse(textSymbol.toJson()));
                                    LabelDefinition labelDefinition = LabelDefinition.fromJson(json.toString());
                                    featureLayer.getLabelDefinitions().add(labelDefinition);
                                    featureLayer.setLabelsEnabled(true);
                                    mView.getMap().getOperationalLayers().add(featureLayer);
                                    mView.setViewpointAsync(new Viewpoint(featureLayer.getFullExtent()));
                                } else {
                                    showPopUpMsg("Shapefile feature table failed to load:","Alert");
                                    // Toast.makeText(MainActivity_New.this, "Shapefile feature table failed to load: ", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }

                }
            }
        });
        Loc_Report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                basemapsp.setSelection(0);
                linearlayout_basemap.setVisibility(View.GONE);
                linearlayout_offline.setVisibility(View.INVISIBLE);
                okbutton.setBackgroundColor(getResources().getColor(R.color.gray));
                Measurement.setBackgroundColor(getResources().getColor(R.color.gray));
                Loc_Report.setBackgroundColor(getResources().getColor(R.color.blue));
                Distacetool.setBackgroundColor(getResources().getColor(R.color.gray));
                BasemapSelection.setBackgroundColor(getResources().getColor(R.color.gray));
              /*  polygone.setBackgroundColor(getResources().getColor(R.color.gray));
                line.setBackgroundColor(getResources().getColor(R.color.gray));
                point.setBackgroundColor(getResources().getColor(R.color.gray));*/
                polygone.setImageResource(R.drawable.polygon);
                line.setImageResource(R.drawable.line);
                point.setImageResource(R.drawable.point);
                lin_drawtools.setVisibility(View.INVISIBLE);
                lin_distanetool.setVisibility(View.INVISIBLE);
                Drawflag="";
                detailslatlong.setVisibility(View.INVISIBLE);
                distanceLinear.setVisibility(View.INVISIBLE);
                areaLinear.setVisibility(View.INVISIBLE);
                if (myGraphicalLayer != null) {
                    myGraphicalLayer.getGraphics().clear();
                }
                if(auto_points_polly!=null){
                    auto_points_polly.clear();
                }

                if (loc1 != null) {
                    get_loc_report(loc1.getLatitude(), loc1.getLongitude());
                } else {
                    showPopUpMsg("Please wait your gps is not available....","Alert");
                    //  Toast.makeText(MainActivity_New.this, "Please wait your gps is not available.....", Toast.LENGTH_SHORT).show();
                    get_location_data();
                }


            }
        });
        Distacetool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                basemapsp.setSelection(0);
                linearlayout_basemap.setVisibility(View.GONE);
                linearlayout_offline.setVisibility(View.INVISIBLE);
                okbutton.setBackgroundColor(getResources().getColor(R.color.gray));
                Measurement.setBackgroundColor(getResources().getColor(R.color.gray));
                Loc_Report.setBackgroundColor(getResources().getColor(R.color.gray));
                Distacetool.setBackgroundColor(getResources().getColor(R.color.blue));
                BasemapSelection.setBackgroundColor(getResources().getColor(R.color.gray));
              /*  polygone.setBackgroundColor(getResources().getColor(R.color.gray));
                line.setBackgroundColor(getResources().getColor(R.color.gray));
                point.setBackgroundColor(getResources().getColor(R.color.gray));*/
                polygone.setImageResource(R.drawable.polygon);
                line.setImageResource(R.drawable.line);
                point.setImageResource(R.drawable.point);
                lin_drawtools.setVisibility(View.INVISIBLE);
                lin_distanetool.setVisibility(View.VISIBLE);
                Drawflag="";
                detailslatlong.setVisibility(View.INVISIBLE);
                distanceLinear.setVisibility(View.INVISIBLE);
                areaLinear.setVisibility(View.INVISIBLE);
                if (myGraphicalLayer != null) {
                    myGraphicalLayer.getGraphics().clear();
                }
                if(auto_points_polly!=null){
                    auto_points_polly.clear();
                }
            }
        });
        okbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("StrVillageCode11111",String.valueOf(str_villagemis));
                Log.d("StrVillageCode",String.valueOf(StrVillageCode));
             //   if (str_villagemis.equals("")) {
                    basemapsp.setSelection(0);
                    linearlayout_basemap.setVisibility(View.GONE);
                    BasemapSelection.setBackgroundColor(getResources().getColor(R.color.gray));
                    if(loc1!=null) {
                        if(distancefrompolygon!=null){
                            Measurement.setBackgroundColor(getResources().getColor(R.color.gray));
                            Loc_Report.setBackgroundColor(getResources().getColor(R.color.gray));
                            Distacetool.setBackgroundColor(getResources().getColor(R.color.gray));
                            okbutton.setBackgroundColor(getResources().getColor(R.color.blue));
                            Deg2UTM(loc1.getLatitude(),loc1.getLongitude());
                            Point wgspoint =new Point(Double.parseDouble(lmodel.getEasting()), Double.parseDouble(lmodel.getNorthing()), SpatialReference.create(32643));
                            Geometry point1 = GeometryEngine.project(wgspoint, SpatialReference.create(32643));
                            Log.d("point1",String.valueOf(wgspoint));
                            Log.d("point12222222",String.valueOf(distancefrompolygon));
                            Geometry part1 = GeometryEngine.project(distancefrompolygon, SpatialReference.create((32643)));
                            Log.d("point12223",String.valueOf(point1));
                            Log.d("point122222224",String.valueOf(part1));

                            boolean pointinside_outside = GeometryEngine.intersects(point1,part1);

                            if(dept_code.equals("02_1")) {
                                if (pointinside_outside) {
                                   launchIntent = new Intent(Intent.ACTION_MAIN);
                                    PackageManager pm = MainActivity_New.this.getPackageManager();
                                    launchIntent = new Intent(Packagename+"."+Classname);
                                 //   launchIntent = new Intent(MainActivity_New.this,Packagename+"."+Classname+".class");
                                   // launchIntent.setClassName(this.getClass().getName(), Packagename+"."+Classname);
                                    if (isPackageInstalled(Packagename, pm)) {
                                        if (launchIntent != null) {
                                            //  launchIntent.putExtra("villagename", villagenameStr);
                                            // launchIntent.putExtra("villagecode", Lgdcodes);
                                            launchIntent.putExtra("servey_no", Serveynostr);
                                            Log.d("servey_no11", Serveynostr);
                                            startActivity(launchIntent);
                                          //  finish();
                                        }
                                    } else {
                                        launchIntent = new Intent(Intent.ACTION_VIEW);
                                        launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        launchIntent.setData(Uri.parse("market://details?id=" + Packagename));
                                        //  launchIntent.putExtra("villagename", villagenameStr);
                                        //  launchIntent.putExtra("villagecode", Lgdcodes);
                                        launchIntent.putExtra("servey_no", Serveynostr);
                                        Log.d("servey_no1144", Serveynostr);
                                        startActivity(launchIntent);
                                        finish();
                                    }
                                } else {
                                    show_distance(distancefrompolygon);
                                }
                            }

                        }
                        else{
                            showPopUpMsg("Please select the Survey no.","Alert");
                        }
                    }
                    else{
                        showPopUpMsg("Please wait for gps.","Alert");
                    }
            //    }
            /*  else if (str_villagemis.equals(StrVillageCode)) {
                    basemapsp.setSelection(0);
                    linearlayout_basemap.setVisibility(View.GONE);
                    BasemapSelection.setBackgroundColor(getResources().getColor(R.color.gray));
                    if(loc1!=null) {
                        if(distancefrompolygon!=null){
                            Measurement.setBackgroundColor(getResources().getColor(R.color.gray));
                            Loc_Report.setBackgroundColor(getResources().getColor(R.color.gray));
                            Distacetool.setBackgroundColor(getResources().getColor(R.color.gray));
                            okbutton.setBackgroundColor(getResources().getColor(R.color.blue));
                            Deg2UTM(loc1.getLatitude(),loc1.getLongitude());
                            Point wgspoint =new Point(Double.parseDouble(lmodel.getEasting()), Double.parseDouble(lmodel.getNorthing()), SpatialReference.create(32643));
                            Geometry point1 = GeometryEngine.project(wgspoint, SpatialReference.create(32643));
                            Log.d("point1",String.valueOf(wgspoint));
                            Log.d("point12222222",String.valueOf(distancefrompolygon));
                            Geometry part1 = GeometryEngine.project(distancefrompolygon, SpatialReference.create((32643)));
                            Log.d("point12223",String.valueOf(point1));
                            Log.d("point122222224",String.valueOf(part1));

                            boolean pointinside_outside = GeometryEngine.intersects(point1,part1);

                            if(dept_code.equals("02_1")) {
                                if (pointinside_outside) {
                                    launchIntent = new Intent(Intent.ACTION_MAIN);
                                    PackageManager pm = MainActivity_New.this.getPackageManager();

                                    launchIntent = new Intent(Packagename+"."+Classname);
                                    if (isPackageInstalled(Packagename, pm)) {
                                        if (launchIntent != null) {
                                          //  launchIntent.putExtra("villagename", villagenameStr);
                                           // launchIntent.putExtra("villagecode", Lgdcodes);
                                            launchIntent.putExtra("servey_no", Serveynostr);
                                            Log.d("servey_no11", Serveynostr);
                                            startActivity(launchIntent);
                                            finish();
                                        }
                                    } else {
                                        launchIntent = new Intent(Intent.ACTION_VIEW);
                                        launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        launchIntent.setData(Uri.parse("market://details?id=" + Packagename));
                                      //  launchIntent.putExtra("villagename", villagenameStr);
                                      //  launchIntent.putExtra("villagecode", Lgdcodes);
                                        launchIntent.putExtra("servey_no", Serveynostr);
                                        Log.d("servey_no11", Serveynostr);
                                        startActivity(launchIntent);
                                        finish();
                                    }
                                } else {
                                    show_distance(distancefrompolygon);
                                }
                            }

                        }
                        else{
                            showPopUpMsg("Please select the Survey no.","Alert");
                        }
                    }
                    else{
                        showPopUpMsg("Please wait for gps.","Alert");
                    }
                }else {
                    showPopUpMsg("Selected village is not same as survayedVillage.","Alert");
                   // launchIntent(true);
                }*/
            }
        });

        BasemapSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                okbutton.setBackgroundColor(getResources().getColor(R.color.gray));
                Measurement.setBackgroundColor(getResources().getColor(R.color.gray));
                Loc_Report.setBackgroundColor(getResources().getColor(R.color.gray));
                Distacetool.setBackgroundColor(getResources().getColor(R.color.gray));
                BasemapSelection.setBackgroundColor(getResources().getColor(R.color.blue));
                lin_drawtools.setVisibility(View.INVISIBLE);
                lin_distanetool.setVisibility(View.INVISIBLE);
                Drawflag="";
                detailslatlong.setVisibility(View.INVISIBLE);
                distanceLinear.setVisibility(View.INVISIBLE);
                areaLinear.setVisibility(View.INVISIBLE);
                linearlayout_basemap.setVisibility(View.VISIBLE);

            }
        });

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API).addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(MainActivity_New.this).build();
            mGoogleApiClient.connect();
            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(30 * 1000);
            locationRequest.setFastestInterval(5 * 1000);
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest);

            // **************************
            builder.setAlwaysShow(true); // this is the key ingredient
            // **************************

            PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi
                    .checkLocationSettings(mGoogleApiClient, builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    final LocationSettingsStates state = result
                            .getLocationSettingsStates();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.SUCCESS:
                            // All location settings are satisfied. The client can
                            // initialize location
                            // requests here.
                            break;
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            // Location settings are not satisfied. But could be
                            // fixed by showing the user
                            // a dialog.
                            try {
                                // Show the dialog by calling
                                // startResolutionForResult(),
                                // and check the result in onActivityResult().
                                status.startResolutionForResult(MainActivity_New.this, 1000);
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            // Location settings are not satisfied. However, we have
                            // no way to fix the
                            // settings so we won't show the dialog.
                            break;
                    }
                }
            });
        }
   //     onmapload();
    }

    private void get_loc_report(double latitude, double longitude) {
        if (featureLayer != null) {
            new query_kgisVillage_id().execute(latitude, longitude);
        }
        else{
            showPopUpMsg("You are outside the Selected village boundary.","Alert");
            //  Toast.makeText(MainActivity_New.this,"You are outside the Selected village boundary.",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ex, menu);
        menu.getItem(0).setTitle("V-" + getVersion());
        return true;
    }
    private String getVersion(){
        PackageManager pm = this.getPackageManager();
        PackageInfo pInfo = null;

        try {
            pInfo =  pm.getPackageInfo(this.getPackageName(),0);

        } catch (PackageManager.NameNotFoundException e1) {
            e1.printStackTrace();
        }

        currentversion = pInfo.versionName;
        curver= Double.parseDouble(currentversion);
     //   new GetLatestVersion().execute();
        return currentversion;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.offline) {
            linearlayout_basemap.setVisibility(View.GONE);
            Measurement.setBackgroundColor(getResources().getColor(R.color.gray));
            Loc_Report.setBackgroundColor(getResources().getColor(R.color.gray));
            Distacetool.setBackgroundColor(getResources().getColor(R.color.gray));
            okbutton.setBackgroundColor(getResources().getColor(R.color.gray));
            lin_distanetool.setVisibility(View.INVISIBLE);
            detailslatlong.setVisibility(View.INVISIBLE);
            distanceLinear.setVisibility(View.INVISIBLE);
            areaLinear.setVisibility(View.INVISIBLE);
            if (myGraphicalLayer != null) {
                myGraphicalLayer.getGraphics().clear();
            }
            if(auto_points_polly!=null){
                auto_points_polly.clear();
            }
            linearlayout_offline.setVisibility(View.VISIBLE);
            return true;
        }
       /* if (id == R.id.measurement) {
            lin_drawtools.setVisibility(View.VISIBLE);
            return true;
        }*/
        if (id == R.id.about) {
            Intent in1 = new Intent(MainActivity_New.this, About.class);
            startActivity(in1);
            return true;
        }
        if (id == R.id.disclaimer) {
            Intent in2 = new Intent(MainActivity_New.this, DisclaimerActivity.class);
            startActivity(in2);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onResume() {
        super.onResume();

        Log.d("MapResume","RRRRRRRRRRRRRRRRRRRRRRRRRRRRrRRrRusume");
        get_location_data();
        //registerBDCast();
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);
        if (location!=null) {
            Deg2UTM(location.getLatitude(), location.getLongitude());
        }
        b = getIntent().getExtras();
        mView.resume();
        onmapload();



    }

 /*   private class GetLatestVersion extends AsyncTask<String, Void, Integer>
    {
        @Override
        protected void onPreExecute(){

        }
        @Override
        protected Integer doInBackground(String... params) {
            URL url = null;
            HttpURLConnection connection = null;
            try {
                url = new URL("http://164.100.133.168:8087/MobileApp_Version/webapi/myresource/getVersionData/hasiru");
                connection = (HttpURLConnection) url.openConnection();
                Log.d("url", String.valueOf(url));
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(20000);
                connection.setReadTimeout(20000);
                connection.connect();
                InputStreamReader dIStream = new InputStreamReader(connection.getInputStream());
                BufferedReader br = new BufferedReader(dIStream);
                StringBuilder stringBuilder = new StringBuilder();
                Log.d("BufferedReader", String.valueOf(br));
                String line = "";
                responsestring1 = connection.getResponseMessage();
                responsecode1 = connection.getResponseCode();
                responseOutput1 = new StringBuilder();

                while ((line = br.readLine()) != null) {
                    responseOutput1.append(line);
                }
                br.close();
                dIStream.close();
                result2 = stringBuilder.toString();
                sss2 = responseOutput1.toString();
            }
            catch (ProtocolException e) {
                Log.d("ProtocolException", String.valueOf(e));
                e.printStackTrace();
            } catch (MalformedURLException e) {
                Log.d("MalformedURLException", String.valueOf(e));
                e.printStackTrace();
            }
            catch (SocketTimeoutException bug) {
                Log.d("Socket Timeout", String.valueOf(bug));
            } catch (IOException e) {
                Log.d("IOException", String.valueOf(e));
                result2 = null;
                e.printStackTrace();
            }
            return responsecode1;
        }

        @Override
        protected void onPostExecute(Integer responsecode) {
            super.onPostExecute(responsecode);
            Log.d("rssss", String.valueOf(responsecode));
            if(responsecode==200){
                if(sss2!=null){
                    Log.d("ss2", sss2);
                    try {
                        JSONArray jsonArray = new JSONArray(sss2);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            latestversion=jsonObject.getString("versionno");
                        }
                        String regex = "[0-9.]*";
                        if(latestversion.matches(regex)){
                            latver=Double.parseDouble(latestversion);
                            Log.d("new version", String.valueOf(latver));
                        }else{
                            latver=null;
                        }
                        if(latver!=null) {
                            if (curver<latver){
                                if(!isFinishing()){ //This would help to prevent Error : BinderProxy@45d459c0 is not valid; is your activity running? error
                                    showUpdateDialog();
                                }
                            }
                        }
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("message", "Latest Version of App on Play store not available");
                    }
                }
            }
            else{
                if(sss2==null) {
                    Log.d("message", "Latest Version of App on Play store not available");
                }
            }
        }
    }*/

    private class getVillage extends AsyncTask<String, Void, Integer> {
        @Override
        protected void onPreExecute() {
            progress = new ProgressDialog(MainActivity_New.this);
            progress.setMessage("Fetching village");
            progress.setCancelable(false);
            progress.show();
        }

        @Override
        protected Integer doInBackground(String... params) {
            URL url = null;
            String lgdcode=params[0];
            String dept=params[1];
            String appcode=params[2];
            String type=params[3];

            try {
                //  url = new URL("http://117.254.85.204:8086/generic/webapi/myresource/kgiscodeforlgd/"+lgdcode);
              /*  url = new URL("http://117.254.85.204:8086/genericwebservices/ws/kgisadminhierarchy?deptcode="+dept+"&applncode="+
                        appcode+"&code="+lgdcode+"&type="+type);*/
                /*url = new URL("http://164.100.133.168:8087/genericwebservices/ws/kgisadminhierarchy?deptcode="+dept+"&applncode="+
                        appcode+"&code="+lgdcode+"&type="+type);*/
                url = new URL("https://kgis.ksrsac.in:9000/genericwebservices/ws/kgisadminhierarchy?deptcode="+dept+"&applncode="+
                        appcode+"&code="+lgdcode+"&type="+type);
                Log.d("url", String.valueOf(url));
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                Log.d("compose json", "json....................................entered2");
                connection.setRequestMethod("GET");
                connection.setSSLSocketFactory(setSSlCert().getSocketFactory());
                connection.setConnectTimeout(20000);
                connection.setReadTimeout(20000);
                connection.connect();
                InputStreamReader dIStream = new InputStreamReader(connection.getInputStream());
                BufferedReader br = new BufferedReader(dIStream);
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";
                responsestring = connection.getResponseMessage();
                responsecode = connection.getResponseCode();
                responseOutput = new StringBuilder();

                while ((line = br.readLine()) != null) {
                    Log.d("lineno", line);
                    responseOutput.append(line);
                }
                br.close();
                dIStream.close();
                result1 = stringBuilder.toString();
                sss1 = responseOutput.toString();
                Log.d("Search", result1);
            } catch (ProtocolException e) {
                Log.d("ProtocolException", String.valueOf(e));
                e.printStackTrace();
                progress.dismiss();
                launchIntent(true);
            } catch (MalformedURLException e) {
                Log.d("MalformedURLException", String.valueOf(e));
                e.printStackTrace();
                progress.dismiss();
                launchIntent(true);
            } catch (SocketTimeoutException bug) {
                Log.d("Socket Timeout", String.valueOf(bug));
                progress.dismiss();
                launchIntent(true);
            } catch (IOException e) {
                Log.d("IOException", String.valueOf(e));
                result1 = null;
                e.printStackTrace();
                progress.dismiss();
                launchIntent(true);
            }
            return responsecode;
        }

        @Override
        protected void onPostExecute(Integer responsecode) {
            super.onPostExecute(responsecode);
            Log.d("rssss", String.valueOf(responsecode));
            if (responsecode == 200) {
                if (sss1 != null) {

                    Log.d("result1ghh", sss1);
                    try {
                        JSONArray jsonArray = new JSONArray(sss1);
                        int length = jsonArray.length();
                        ContentValues value = new ContentValues();
                        progress.dismiss();
                      //  for (int i = 0; i < length; i++) {
                            JSONObject jo = (JSONObject) jsonArray.get(0);

               /*         [{"districtName":"Bagalkot","districtCode":"02","talukName":"Badami","talukCode":"0204","hobliName":
                                "BADAMI","hobliCode":"020401","villageName":"Adagal (Balageri)","villageCode":"0204010026"}]
                            */
                            str_msg = jo.getString("message");
                            str_dist = jo.getString("districtName").trim().replace(" ","_")
                                    .replace(")","").replace("(","");
                            StrVillageCode = jo.getString("villageCode").trim();
                            StrVillage =  jo.getString("villageName").trim();
                            str_taluk = jo.getString("talukName").trim();
                            value.put(SqliteHelper.LGDCODE, Lgdcodes);
                            value.put(SqliteHelper.DISTRICT, str_dist);
                            value.put(SqliteHelper.DISTCODE, jo.getString("districtCode").trim());
                            value.put(SqliteHelper.VILLAGE_CODE, jo.getString("villageCode").trim());
                            value.put(SqliteHelper.VILLAGE_NAME, jo.getString("villageName").trim());
                            value.put(SqliteHelper.TALUK, jo.getString("talukName").trim());
                            value.put(SqliteHelper.TALUKCODE, jo.getString("talukCode").trim());
                            value.put(SqliteHelper.STATUS, "1");
                            App.mDBHandler.insert_values(SqliteHelper.TABLE_Villagename, value);
                      //  }
                        villagenametxt.setText("Dist: "+str_dist+", Taluk: "+str_taluk+",  Village: "+StrVillage);

                        Log.d("StrDistrict", str_dist + "nodistrict");
                        if(str_msg.equals("Data not Available")){
                            //  Toast.makeText(MainActivity.this,str_msg,Toast.LENGTH_LONG).show();
                            showPopUpMsg(str_msg,"Alert");
                        }
                        else {
                            wallpaperDirectory = new File(Environment.getExternalStorageDirectory() + "/HASIRU/");
                            Log.d("pat0", wallpaperDirectory.getAbsolutePath());
                            File direct = new File(Environment.getExternalStorageDirectory() + "/HASIRU/");
                            if (!direct.exists()) {
                                Log.d("pat0", wallpaperDirectory.getAbsolutePath());
                                wallpaperDirectory.mkdirs();
                            }
                            downloadZipFile(serverFilePath + str_dist + "/" + StrVillageCode + ".zip", wallpaperDirectory.getAbsolutePath() + "/" +
                                    StrVillageCode + ".zip");

                        }
                        // progress.dismiss();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        progress.dismiss();
                        showPopUpMsg("Something went wrong please try after some time.","Alert");
                        launchIntent(true);
                        //Toast.makeText(getApplicationContext(), "Something went wrong please try after some time.", Toast.LENGTH_LONG).show();
                    }
                }
            } else {
                if (sss1 == null) {
                    progress.dismiss();
                    showPopUpMsg("Village not present for this lgd code.","Alert");
                    launchIntent(true);
                    //Toast.makeText(getApplicationContext(), "Village code is not present for this lgd code.", Toast.LENGTH_LONG).show();
                }
            }
        }

    }

    private void querySurveyNumber() {
        QueryParameters query1 = new QueryParameters();
        query1.setWhereClause("surveynumb"+ "=" + Serveynostr);
        query1.setSpatialRelationship(QueryParameters.SpatialRelationship.INTERSECTS);
        query1.setReturnGeometry(true);
        final ListenableFuture<FeatureQueryResult> future1 = featureLayer.selectFeaturesAsync(query1, FeatureLayer.SelectionMode.SUBTRACT);
        future1.addDoneListener(new Runnable() {
            @Override
            public void run() {
                try {
                    FeatureQueryResult result = future1.get();
                    if(result!=null) {
                        Feature tmpFeat = null;
                        for (Object featAsObj : result) {
                            tmpFeat = (Feature) featAsObj;
                            Serveynostr = String.valueOf(tmpFeat.getAttributes().get("surveynumb"));
                            villagenameStr = StrVillage;
                        }
                        if (gsurvey != null) {
                            gsurvey.getGraphics().clear();
                        }
                        if(tmpFeat!=null){
                            SimpleLineSymbol polygonOutline = new SimpleLineSymbol( SimpleLineSymbol.Style.SOLID,Color.RED, 5);
                            Graphic = new Graphic(tmpFeat.getGeometry(), polygonOutline);
                            distancefrompolygon = tmpFeat.getGeometry();
                            gsurvey.getGraphics().add(Graphic);
                        }
                        else{
                            launchIntent(false);
                        }

                    }
                } catch (Exception e) {
                    // launchIntent(false);
                    Log.e(getResources().getString(R.string.app_name), "Select feature failed: " + e.getMessage());
                }
            }
        });
    }


    public void downloadZipFile(String urlStr, String destinationFilePath) {

        InputStream input = null;
        OutputStream output = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(urlStr);

            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                Log.d("downloadZipFile111", "Server ResponseCode=" + connection.getResponseCode() +
                        " ResponseMessage=" + connection.getResponseMessage());
                if(connection.getResponseCode()==404){
                    Log.d("ResponseMessage",connection.getResponseMessage());
                  /*  downloadZipFile_village(VillageFilePath + StrDistrict + "/" + StrVillageCode + ".zip", wallpaperDirectory.getAbsolutePath() + "/" +
                            StrVillageCode + ".zip");*/
                }
            }
            else {
                // download the file
                input = connection.getInputStream();
                Log.d("downloadZipFile", "destinationFilePath=" + destinationFilePath);
                new File(destinationFilePath).createNewFile();
                output = new FileOutputStream(destinationFilePath);

                byte data[] = new byte[4096];
                int count;
                while ((count = input.read(data)) != -1) {
                    output.write(data, 0, count);
                }
            }
            //    progress.dismiss();
        } catch (Exception e) {
            //   progress.dismiss();
            e.printStackTrace();
            return;
        } finally {
            try {
                if (output != null) output.close();
                if (input != null) input.close();
                //   progress.dismiss();
            } catch (IOException e) {
                e.printStackTrace();
                //   progress.dismiss();
            }

            if (connection != null) connection.disconnect();
        }

        File f = new File(destinationFilePath);
        Log.d("downloadZipFile111",  f.getName());
        Log.d("downloadZipFileddd", destinationFilePath);
        unpackZip(destinationFilePath);
    }
    public boolean unpackZip(String filePath) {
        InputStream is;
        ZipInputStream zis;
        try {

            File zipfile = new File(filePath);
            String parentFolder = zipfile.getParentFile().getPath();
            String filename;
            String subFolder ="";
            is = new FileInputStream(filePath);
            zis = new ZipInputStream(new BufferedInputStream(is));
            ZipEntry ze;
            byte[] buffer = new byte[1024];
            int count;

            while ((ze = zis.getNextEntry()) != null) {
                filename = ze.getName();
                Log.d("filename",filename);
                Log.d("parentFolder",parentFolder);
                if (filename.contains("/")) {
                    subFolder = filename.split("\\/")[0];
                    File vlgCode = new File(parentFolder+"/" + subFolder);
                    if (!vlgCode.exists()) {
                        Log.d("mat0",  vlgCode.getAbsolutePath());
                        vlgCode.mkdirs();
                    }
                }
                FileOutputStream fout = new FileOutputStream(parentFolder + "/" + filename);
                while ((count = zis.read(buffer)) != -1) {
                    fout.write(buffer, 0, count);
                }

                fout.close();
                zis.closeEntry();
            }

            zis.close();

            if(featureLayer!=null) {
                mView.getMap().getOperationalLayers().remove(featureLayer);
            }
            File myFile = new File(Environment.getExternalStorageDirectory() + "/HASIRU/" + StrVillageCode + ".shp");

            Log.d("shape_File.....","shape file"+Environment.getExternalStorageDirectory() + "/HASIRU/"+ StrVillageCode + ".shp");
            if (myFile.exists()) {
                shapefileFeatureTable = new ShapefileFeatureTable(
                        Environment.getExternalStorageDirectory() + "/HASIRU/" + StrVillageCode + ".shp");
                Log.d("shape_File1223333", String.valueOf(Environment.getExternalStorageDirectory() + "/HASIRU/" + StrVillageCode + ".shp"));
            } else {
                showPopUpMsg("File not Present.","Alert");
                //   Toast.makeText(getApplicationContext(), "File not Present", Toast.LENGTH_SHORT).show();
            }
            // Toast.makeText(getApplicationContext(), "Data download Successfully", Toast.LENGTH_SHORT).show();
            shapefileFeatureTable.loadAsync();
            shapefileFeatureTable.addDoneLoadingListener(new Runnable() {
                @Override
                public void run() {
                    if (shapefileFeatureTable.getLoadStatus() == LoadStatus.LOADED) {
                        featureLayer = new FeatureLayer(shapefileFeatureTable);
                        featureLayer.setOpacity(0.7f);
                        TextSymbol textSymbol = new TextSymbol();
                        textSymbol.setSize(20);
                        textSymbol.setColor(0xFF0000FF);
                        textSymbol.setHaloColor(0xFFFFFF00);
                        textSymbol.setHaloWidth(2);
                        JsonObject json = new JsonObject();
                        JsonObject expressionInfo = new JsonObject();
                        expressionInfo.add("expression", new JsonPrimitive("$feature.SurveyNumb"));
                        json.add("labelExpressionInfo", expressionInfo);

                        json.add("symbol", new JsonParser().parse(textSymbol.toJson()));
                        LabelDefinition labelDefinition = LabelDefinition.fromJson(json.toString());
                        featureLayer.getLabelDefinitions().add(labelDefinition);
                        featureLayer.setLabelsEnabled(true);
                        mView.getMap().getOperationalLayers().add(featureLayer);
                        mView.setViewpointAsync(new Viewpoint(featureLayer.getFullExtent()));

//                        new queryForSurveyNumber().execute(survey_number);
                        querySurveyNumber();
                    } else {
                        Toast.makeText(MainActivity_New.this, "Shapefile feature table failed to load: ", Toast.LENGTH_LONG).show();
                    }
                }
            });
            SpinnerAdapter1 villadapter = new SpinnerAdapter1(MainActivity_New.this, R.layout.spinner_delete, get_tpk_files());
            offlinesp.setAdapter(villadapter);


        } catch(IOException e) {
            e.printStackTrace();
            launchIntent(true);
            //  Toast.makeText(getApplicationContext(), "Data download failed", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void launchIntent(Boolean isVillage) {
        if(dept_code.equals("02_1")) {
            launchIntent = new Intent(Intent.ACTION_MAIN);
            PackageManager pm = MainActivity_New.this.getPackageManager();
            launchIntent = new Intent(Packagename+"."+Classname);
            if (isPackageInstalled(Packagename, pm)) {
                if (launchIntent != null) {
                    if (isVillage) {
                        //  launchIntent.putExtra("villagename", getString(R.string.village_not));
                        //  launchIntent.putExtra("villagecode", getString(R.string.village_not));
                        launchIntent.putExtra("servey_no", getString(R.string.village_not));
                        Log.d("servey_no", getString(R.string.village_not));

                    }else {
                        //   launchIntent.putExtra("villagename", villagenameStr);
                        //   launchIntent.putExtra("villagecode", Lgdcodes);
                        //   launchIntent.putExtra("servey_no", Serveynostr);
                        launchIntent.putExtra("servey_no", getString(R.string.surveynumber_not));
                        Log.d("servey_no1", getString(R.string.surveynumber_not));
                    }
                    startActivity(launchIntent);
                    finish();
                }
            } else {
                launchIntent = new Intent(Intent.ACTION_VIEW);
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (isVillage) {
                    //   launchIntent.putExtra("villagename", getString(R.string.village_not));
                    //  launchIntent.putExtra("villagecode", getString(R.string.village_not));
                    Log.d("servey_no131", getString(R.string.village_not));
                    launchIntent.putExtra("servey_no", Serveynostr);
                }else {
                    //   launchIntent.putExtra("villagecode", Lgdcodes);
                    //   launchIntent.putExtra("villagename", villagenameStr);
                    Log.d("servey_no13", getString(R.string.surveynumber_not));
                    launchIntent.putExtra("servey_no", getString(R.string.surveynumber_not));
                }
                launchIntent.setData(Uri.parse("market://details?id=" + Packagename));
                //   launchIntent.putExtra("villagecode", Lgdcodes);
                //   launchIntent.putExtra("villagename", villagenameStr);
                //  Log.d("servey_no13", getString(R.string.surveynumber_not));
                //  launchIntent.putExtra("servey_no", getString(R.string.surveynumber_not));
                startActivity(launchIntent);
                finish();
            }
        }
    }

    private class query_kgisVillage_id extends AsyncTask<Double, Void, FeatureQueryResult> {
        double lat;
        double lng;
        @Override
        protected void onPreExecute() {
            progress = new ProgressDialog(MainActivity_New.this);
            progress.setMessage("Fetching data");
            progress.setCancelable(false);
            progress.show();
        }

        @Override
        protected FeatureQueryResult doInBackground(Double... params) {
            lat=params[0];
            lng=params[1];
            Deg2UTM(lat, lng);
            Point pt = new Point(Double.parseDouble(lmodel.getEasting()), Double.parseDouble(lmodel.getNorthing()), SpatialReference.create(32643));
            Geometry wgspoint = GeometryEngine.project(pt, SpatialReference.create(32643));
            Log.d("wgspoint", wgspoint.toString());
            QueryParameters query=new QueryParameters();
            query.setGeometry(wgspoint);
            Log.d("geom", wgspoint.toString());
            query.setSpatialRelationship(QueryParameters.SpatialRelationship.INTERSECTS);
            query.setReturnGeometry(true);
            ListenableFuture<FeatureQueryResult> future = featureLayer.selectFeaturesAsync(query, FeatureLayer.SelectionMode.NEW);
            FeatureQueryResult result;
            try {
                result=future.get();
                Log.d("value",String.valueOf(result));
                return result;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onCancelled(){
            showPopUpMsg("Data not available.","Alert");
            //  Toast.makeText(MainActivity_New.this, "Data not available.", Toast.LENGTH_SHORT).show();

            progress.dismiss();
        }
        @Override
        protected void onPostExecute(FeatureQueryResult results1) {
            if(results1!=null) {
                Log.d("value11",String.valueOf(results1));
                Feature tmpFeat=null;
                String kgisvillagecode="",SurveyNo="",Village_Name="";
                for (Object featAsObj : results1) {
                    tmpFeat = (Feature) featAsObj;
                    Log.d("tmpFeat1111", String.valueOf(tmpFeat.getAttributes()));
                    SurveyNo=String.valueOf(tmpFeat.getAttributes().get("surveynumb"));
                    Village_Name=String.valueOf(tmpFeat.getAttributes().get("villagenam"));
                    kgisvillagecode=String.valueOf(tmpFeat.getAttributes().get("KGISVill_1"));
                    Log.d("SurveyNo",SurveyNo);
                    Log.d("Village_Name",Village_Name);
                    Log.d("kgisvillagecode",kgisvillagecode);
                }
                if(SurveyNo.equals("")){
                    showPopUpMsg("You are outside the Selected village boundary.","Alert");
                    // Toast.makeText(MainActivity_New.this,"You are outside the Selected village boundary",Toast.LENGTH_LONG).show();
                }
                else{
                    show_popup(SurveyNo,Village_Name,str_taluk,str_dist);
                }
                progress.dismiss();
            }else {
                showPopUpMsg("Data not available.","Alert");
                // Toast.makeText(MainActivity_New.this, "Data not available.", Toast.LENGTH_SHORT).show();
                progress.dismiss();
            }

        }
    }

    private void show_popup(String sur, String vill, String tal, final String dist){
        final Dialog dialog = new Dialog(MainActivity_New.this);
        dialog.setContentView(R.layout.add_popup_layout);
        dialog.setCancelable(false);

        ImageView popup_addCropTitle = dialog.findViewById(R.id.popup_image_cancel);
        TextView survey=dialog.findViewById(R.id.tvsno);
        TextView village=dialog.findViewById(R.id.tvvillage);
        TextView taluk=dialog.findViewById(R.id.tvtaluk);
        TextView district=dialog.findViewById(R.id.tvdistrict);
        survey.setText(":"+sur);
        village.setText(":"+vill);
        taluk.setText(":"+tal);
        district.setText(":"+dist);

        popup_addCropTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private boolean isPackageInstalled(String packagename, PackageManager packageManager) {
        try {
            packageManager.getPackageInfo(packagename, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public void onViewCreated()
    {
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }

    public void get_location_data() {
        mLocationDisplay = mView.getLocationDisplay();
        mLocationDisplay.addDataSourceStatusChangedListener(new LocationDisplay.DataSourceStatusChangedListener() {
            @Override
            public void onStatusChanged(LocationDisplay.DataSourceStatusChangedEvent dataSourceStatusChangedEvent) {
                if (dataSourceStatusChangedEvent.isStarted()) {
                    return;
                }
                if (dataSourceStatusChangedEvent.getError() == null)
                    return;
                boolean permissionCheck1 = ContextCompat.checkSelfPermission(MainActivity_New.this, reqPermissions[0]) ==
                        PackageManager.PERMISSION_GRANTED;
                boolean permissionCheck2 = ContextCompat.checkSelfPermission(MainActivity_New.this, reqPermissions[1]) ==
                        PackageManager.PERMISSION_GRANTED;

                if (!(permissionCheck1 && permissionCheck2)) {
                    ActivityCompat.requestPermissions(MainActivity_New.this, reqPermissions, requestCode);
                } else {
                    String message = String.format("Error in DataSourceStatusChangedListener: %s", dataSourceStatusChangedEvent
                            .getSource().getLocationDataSource().getError().getMessage());
                    Toast.makeText(MainActivity_New.this, message, Toast.LENGTH_LONG).show();
                }
            }
        });
        mLocationDisplay.setAutoPanMode(LocationDisplay.AutoPanMode.RECENTER);
        if (!mLocationDisplay.isStarted()) {
            //     loading.dismiss();
            mLocationDisplay.startAsync();
//            Log.d("Lastlocation111", String.valueOf(mLocationDisplay.getLocation().getTimeStamp()));
        }
        mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mlocListener = new MyLocationListener();
        if (!mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && !mlocManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) && !mlocManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER)) {
            showGPSDisabledAlertToUser();
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            checkLocationPermission();
        } else {
            if (mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                loc1 = mlocManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            } else if (mlocManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                loc1 = mlocManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            } else if (mlocManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER)) {
                loc1 = mlocManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            }
            if (mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, mlocListener);
            } else if (mlocManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, mlocListener);
            } else if (mlocManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER)) {
                mlocManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 1000, 0, mlocListener);
            }
        }
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API).addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(MainActivity_New.this).build();
            mGoogleApiClient.connect();
            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(30 * 1000);
            locationRequest.setFastestInterval(5 * 1000);
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest);

            // **************************
            builder.setAlwaysShow(true); // this is the key ingredient
            // **************************

            PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi
                    .checkLocationSettings(mGoogleApiClient, builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    final LocationSettingsStates state = result
                            .getLocationSettingsStates();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.SUCCESS:
                            // All location settings are satisfied. The client can
                            // initialize location
                            // requests here.
                            break;
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            // Location settings are not satisfied. But could be
                            // fixed by showing the user
                            // a dialog.
                            try {
                                // Show the dialog by calling
                                // startResolutionForResult(),
                                // and check the result in onActivityResult().
                                status.startResolutionForResult(MainActivity_New.this, 1000);
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            // Location settings are not satisfied. However, we have
                            // no way to fix the
                            // settings so we won't show the dialog.
                            break;
                    }
                }
            });

        }
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);
        if(provider!=null){
            Location location = locationManager.getLastKnownLocation(provider);
            loc1=location;
            if(loc1!=null){
                long time = location.getTime();
                Date date = new Date(time);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                latlng1.setText((getPrecisionvalue(location.getLatitude())) + " N," + (getPrecisionvalue(location.getLongitude())) + " E   " + sdf.format(date));
                Deg2UTM(location.getLatitude(),location.getLongitude());
            }
        }
    }

    private void show_distance(Geometry survey_geom){

        double x= survey_geom.getExtent().getCenter().getX();

        double y= survey_geom.getExtent().getCenter().getY();
        Log.d("xy",String.valueOf(x)+"---"+String.valueOf(y));

        Point pt = new Point(x,y, SpatialReference.create(32643));

       /* Geometry point = GeometryEngine.project(pt, SpatialReference.create(32643));
        Point pt1 = (Point) GeometryEngine.project(point, SpatialReference.create(32643));
        Log.d("point1111",String.valueOf(point));*/
        //  Point p=GeometryEngine.project(x,y, SpatialReferences.getWebMercator());
        Log.d("pt",String.valueOf(pt));
        Log.d("latlng",String.valueOf(pt.getX())+"---"+String.valueOf(pt.getY()));
        Deg2UTM(loc1.getLatitude(),loc1.getLongitude());
        Point wgspoint =new Point(Double.parseDouble(lmodel.getEasting()), Double.parseDouble(lmodel.getNorthing()), SpatialReference.create(32643));
        Geometry point1 = GeometryEngine.project(wgspoint, SpatialReference.create(32643));
        Log.d("point1",String.valueOf(point1));

        double distance=GeometryEngine.distanceBetween(pt,point1);
        Log.d("distance", String.valueOf(distance));
        double dis_value=distance/1000;

        NumberFormat formatter = new DecimalFormat("#0.0000");

        Log.d("distance", String.valueOf(dis_value));
        String message=getString(R.string.distance_message_part1)+" "+formatter.format(dis_value)+" "+getString(R.string.distance_message_part2)+" "+Serveynostr+" of "+" Village "+villagenameStr;

        Double latitude = Double.parseDouble(UTM2Deg(String.valueOf(pt.getY()),String.valueOf(pt.getX())).split("-")[0]);
        Log.d("latitude", String.valueOf(latitude));
        Double longitude = Double.parseDouble(UTM2Deg(String.valueOf(pt.getY()),String.valueOf(pt.getX())).split("-")[1]);
        Log.d("longitude", String.valueOf(longitude));

        show_distance_popup(message,latitude,longitude);
        //alert_message_out(getString(R.string.distance_message_part1)+" "+formatter.format(dis_value)+getString(R.string.distance_message_part2)+" "+str_survey+" of\n"+str_villagemis+" Village, \n"+str_hobli+" Hobli, \n"+str_tal+" Taluk, \n"+str_dist+" District");
    }
    private String UTM2Deg(String lat, String lng)
    {
        double latitude;
        double longitude;
        int Zone=43;
        char Letter='N';
        double Easting=Double.parseDouble(lng);
        double Northing=Double.parseDouble(lat);
        double Hem;
        if (Letter>'M')
            Hem='N';
        else
            Hem='S';
        double north;
        if (Hem == 'S')
            north = Northing - 10000000;
        else
            north = Northing;
        latitude = (north/6366197.724/0.9996+(1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2)-0.006739496742*Math.sin(north/6366197.724/0.9996)*Math.cos(north/6366197.724/0.9996)*(Math.atan(Math.cos(Math.atan(( Math.exp((Easting - 500000) / (0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2))))*(1-0.006739496742*Math.pow((Easting - 500000) / (0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2)))),2)/2*Math.pow(Math.cos(north/6366197.724/0.9996),2)/3))-Math.exp(-(Easting-500000)/(0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2))))*( 1 -  0.006739496742*Math.pow((Easting - 500000) / (0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2)))),2)/2*Math.pow(Math.cos(north/6366197.724/0.9996),2)/3)))/2/Math.cos((north-0.9996*6399593.625*(north/6366197.724/0.9996-0.006739496742*3/4*(north/6366197.724/0.9996+Math.sin(2*north/6366197.724/0.9996)/2)+Math.pow(0.006739496742*3/4,2)*5/3*(3*(north/6366197.724/0.9996+Math.sin(2*north/6366197.724/0.9996 )/2)+Math.sin(2*north/6366197.724/0.9996)*Math.pow(Math.cos(north/6366197.724/0.9996),2))/4-Math.pow(0.006739496742*3/4,3)*35/27*(5*(3*(north/6366197.724/0.9996+Math.sin(2*north/6366197.724/0.9996)/2)+Math.sin(2*north/6366197.724/0.9996)*Math.pow(Math.cos(north/6366197.724/0.9996),2))/4+Math.sin(2*north/6366197.724/0.9996)*Math.pow(Math.cos(north/6366197.724/0.9996),2)*Math.pow(Math.cos(north/6366197.724/0.9996),2))/3))/(0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2))))*(1-0.006739496742*Math.pow((Easting-500000)/(0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2)))),2)/2*Math.pow(Math.cos(north/6366197.724/0.9996),2))+north/6366197.724/0.9996)))*Math.tan((north-0.9996*6399593.625*(north/6366197.724/0.9996 - 0.006739496742*3/4*(north/6366197.724/0.9996+Math.sin(2*north/6366197.724/0.9996)/2)+Math.pow(0.006739496742*3/4,2)*5/3*(3*(north/6366197.724/0.9996+Math.sin(2*north/6366197.724/0.9996)/2)+Math.sin(2*north/6366197.724/0.9996 )*Math.pow(Math.cos(north/6366197.724/0.9996),2))/4-Math.pow(0.006739496742*3/4,3)*35/27*(5*(3*(north/6366197.724/0.9996+Math.sin(2*north/6366197.724/0.9996)/2)+Math.sin(2*north/6366197.724/0.9996)*Math.pow(Math.cos(north/6366197.724/0.9996),2))/4+Math.sin(2*north/6366197.724/0.9996)*Math.pow(Math.cos(north/6366197.724/0.9996),2)*Math.pow(Math.cos(north/6366197.724/0.9996),2))/3))/(0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2))))*(1-0.006739496742*Math.pow((Easting-500000)/(0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2)))),2)/2*Math.pow(Math.cos(north/6366197.724/0.9996),2))+north/6366197.724/0.9996))-north/6366197.724/0.9996)*3/2)*(Math.atan(Math.cos(Math.atan((Math.exp((Easting-500000)/(0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2))))*(1-0.006739496742*Math.pow((Easting-500000)/(0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2)))),2)/2*Math.pow(Math.cos(north/6366197.724/0.9996),2)/3))-Math.exp(-(Easting-500000)/(0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2))))*(1-0.006739496742*Math.pow((Easting-500000)/(0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2)))),2)/2*Math.pow(Math.cos(north/6366197.724/0.9996),2)/3)))/2/Math.cos((north-0.9996*6399593.625*(north/6366197.724/0.9996-0.006739496742*3/4*(north/6366197.724/0.9996+Math.sin(2*north/6366197.724/0.9996)/2)+Math.pow(0.006739496742*3/4,2)*5/3*(3*(north/6366197.724/0.9996+Math.sin(2*north/6366197.724/0.9996)/2)+Math.sin(2*north/6366197.724/0.9996)*Math.pow(Math.cos(north/6366197.724/0.9996),2))/4-Math.pow(0.006739496742*3/4,3)*35/27*(5*(3*(north/6366197.724/0.9996+Math.sin(2*north/6366197.724/0.9996)/2)+Math.sin(2*north/6366197.724/0.9996)*Math.pow(Math.cos(north/6366197.724/0.9996),2))/4+Math.sin(2*north/6366197.724/0.9996)*Math.pow(Math.cos(north/6366197.724/0.9996),2)*Math.pow(Math.cos(north/6366197.724/0.9996),2))/3))/(0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2))))*(1-0.006739496742*Math.pow((Easting-500000)/(0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2)))),2)/2*Math.pow(Math.cos(north/6366197.724/0.9996),2))+north/6366197.724/0.9996)))*Math.tan((north-0.9996*6399593.625*(north/6366197.724/0.9996-0.006739496742*3/4*(north/6366197.724/0.9996+Math.sin(2*north/6366197.724/0.9996)/2)+Math.pow(0.006739496742*3/4,2)*5/3*(3*(north/6366197.724/0.9996+Math.sin(2*north/6366197.724/0.9996)/2)+Math.sin(2*north/6366197.724/0.9996)*Math.pow(Math.cos(north/6366197.724/0.9996),2))/4-Math.pow(0.006739496742*3/4,3)*35/27*(5*(3*(north/6366197.724/0.9996+Math.sin(2*north/6366197.724/0.9996)/2)+Math.sin(2*north/6366197.724/0.9996)*Math.pow(Math.cos(north/6366197.724/0.9996),2))/4+Math.sin(2*north/6366197.724/0.9996)*Math.pow(Math.cos(north/6366197.724/0.9996),2)*Math.pow(Math.cos(north/6366197.724/0.9996),2))/3))/(0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2))))*(1-0.006739496742*Math.pow((Easting-500000)/(0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2)))),2)/2*Math.pow(Math.cos(north/6366197.724/0.9996),2))+north/6366197.724/0.9996))-north/6366197.724/0.9996))*180/Math.PI;
        latitude=Math.round(latitude*10000000);
        latitude=latitude/10000000;
        longitude =Math.atan((Math.exp((Easting-500000)/(0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2))))*(1-0.006739496742*Math.pow((Easting-500000)/(0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2)))),2)/2*Math.pow(Math.cos(north/6366197.724/0.9996),2)/3))-Math.exp(-(Easting-500000)/(0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2))))*(1-0.006739496742*Math.pow((Easting-500000)/(0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2)))),2)/2*Math.pow(Math.cos(north/6366197.724/0.9996),2)/3)))/2/Math.cos((north-0.9996*6399593.625*( north/6366197.724/0.9996-0.006739496742*3/4*(north/6366197.724/0.9996+Math.sin(2*north/6366197.724/0.9996)/2)+Math.pow(0.006739496742*3/4,2)*5/3*(3*(north/6366197.724/0.9996+Math.sin(2*north/6366197.724/0.9996)/2)+Math.sin(2* north/6366197.724/0.9996)*Math.pow(Math.cos(north/6366197.724/0.9996),2))/4-Math.pow(0.006739496742*3/4,3)*35/27*(5*(3*(north/6366197.724/0.9996+Math.sin(2*north/6366197.724/0.9996)/2)+Math.sin(2*north/6366197.724/0.9996)*Math.pow(Math.cos(north/6366197.724/0.9996),2))/4+Math.sin(2*north/6366197.724/0.9996)*Math.pow(Math.cos(north/6366197.724/0.9996),2)*Math.pow(Math.cos(north/6366197.724/0.9996),2))/3)) / (0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2))))*(1-0.006739496742*Math.pow((Easting-500000)/(0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2)))),2)/2*Math.pow(Math.cos(north/6366197.724/0.9996),2))+north/6366197.724/0.9996))*180/Math.PI+Zone*6-183;
        longitude=Math.round(longitude*10000000);
        longitude=longitude/10000000;

        return latitude+"-"+longitude;
    }

    private void show_distancelatlong(double x,double y){
        Log.d("xy",String.valueOf(x)+"---"+String.valueOf(y));
        Point pt = new Point(x,y, SpatialReferences.getWebMercator());
        Geometry point = GeometryEngine.project(pt, SpatialReference.create(32643));
        Point pt1 = (Point) GeometryEngine.project(point, SpatialReference.create(32643));
        Log.d("point1111",String.valueOf(point));
        Log.d("point888",String.valueOf(pt1));
        //  Point p=GeometryEngine.project(x,y, SpatialReferences.getWebMercator());
        Log.d("latlng",String.valueOf(pt.getX())+"---"+String.valueOf(pt.getY()));
        Deg2UTM(loc1.getLatitude(),loc1.getLongitude());
        Point wgspoint =new Point(Double.parseDouble(lmodel.getEasting()), Double.parseDouble(lmodel.getNorthing()), SpatialReference.create(32643));
        Geometry point1 = GeometryEngine.project(wgspoint, SpatialReference.create(32643));
        Log.d("point1",String.valueOf(point1));

        double distance=GeometryEngine.distanceBetween(point,point1);
        Log.d("distance", String.valueOf(distance));
        double dis_value=distance/1000;

        NumberFormat formatter = new DecimalFormat("#0.0000");

        Log.d("distance", String.valueOf(dis_value));
        String message=getString(R.string.distance_message_part1)+" "+formatter.format(dis_value)+getString(R.string.distance_message_part3)+
                " Selected survey no is "+Serveynostr+
                " of Village "+villagenameStr;

        Double latitude = Double.parseDouble(UTM2Deg(String.valueOf(pt1.getY()),String.valueOf(pt1.getX())).split("-")[0]);
        Log.d("latitude", String.valueOf(latitude));
        Double longitude = Double.parseDouble(UTM2Deg(String.valueOf(pt1.getY()),String.valueOf(pt1.getX())).split("-")[1]);
        Log.d("longitude", String.valueOf(longitude));

        show_distance_popup(message,latitude,longitude);
        //alert_message_out(getString(R.string.distance_message_part1)+" "+formatter.format(dis_value)+getString(R.string.distance_message_part2)+" "+str_survey+" of\n"+str_villagemis+" Village, \n"+str_hobli+" Hobli, \n"+str_tal+" Taluk, \n"+str_dist+" District");
    }
    private void show_distance_popup(String message, final double lat, final double lng){
        final Dialog dialog = new Dialog(MainActivity_New.this);
        dialog.setContentView(R.layout.add_distance_popup);
        dialog.setCancelable(false);

        ImageView popup_addCropTitle = (ImageView) dialog.findViewById(R.id.popup_image_cancel);
        TextView survey=dialog.findViewById(R.id.tvsno);
        ImageButton navigation =dialog.findViewById(R.id.navigation);
      /*  ImageButton report =dialog.findViewById(R.id.report);
        ImageButton navigation =dialog.findViewById(R.id.navigation);*/


        survey.setText(message);

        popup_addCropTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String uri = "http://maps.google.com/maps?saddr=" + loc1.getLatitude()+ ","
                        +loc1.getLongitude() + "&daddr=" + lat + "," +lng;
                Intent in=new Intent(Intent.ACTION_VIEW,Uri.parse(uri));
                startActivity(in);
            }
        });
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d("ready","location available.....");
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);

        if(provider!=null){
            Location location = locationManager.getLastKnownLocation(provider);
            loc1=location;
            if(loc1!=null) {
                long time = location.getTime();
                Date date = new Date(time);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                latlng1.setText((getPrecisionvalue(location.getLatitude())) + " N," + (getPrecisionvalue(location.getLongitude())) + " E   " + sdf.format(date));
                Deg2UTM(location.getLatitude(), location.getLongitude());
            }
        }
    }
    private String getPrecisionvalue( Double value) {
        return String.format("%.4f", value);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        loc1 = location;
        str_image1_lat = String.valueOf(loc1.getLatitude());
        str_image1_lng = String.valueOf(loc1.getLongitude());
        Deg2UTM(loc1.getLatitude(), loc1.getLongitude());
        long time = loc1.getTime();
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        currentDateandTime = sdf.format(date);
        latlng1.setText((getPrecisionvalue(loc1.getLatitude())) + " N," + (getPrecisionvalue(loc1.getLongitude())) + " E   " + sdf.format(date));
        // Log.d("locationchange1111", String.valueOf(loc1.getAccuracy()));
    }


    private class SpinnerAdapter1 extends ArrayAdapter<ItemData> {
        Context context;
        ArrayList<ItemData> iName=new ArrayList<>();

        TextView spnItemName;
        ImageButton spnItemDel;

        public SpinnerAdapter1(Context context, int textViewResourceId, ArrayList<ItemData> iName){
            super(context,textViewResourceId,iName);
            this.context = context;
            this.iName = iName;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent){
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            View rowView =  inflater.inflate(R.layout.spinner_delete, parent, false);

            spnItemName = (TextView) rowView.findViewById(R.id.text1);
            spnItemDel =  rowView.findViewById(R.id.img1);
            spnItemDel.setImageResource(iName.get(position).getImageId());

            spnItemDel.setTag(position);
            spnItemName.setTag(position);
            spnItemName.setText(iName.get(position).getText());

            spnItemDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int pos = (int)v.getTag();
                    if(pos!=0){
                        //   showPopUpMsg(iName.get(pos).getText()+" Village deleted"+".","Alert");
                        Toast.makeText(context, iName.get(pos).getText()+" Village deleted"+".", Toast.LENGTH_LONG).show();
                        File ff = new File(Environment.getExternalStorageDirectory(), "HASIRU/");
                        try
                        {
                            // File file = new File(ff,  iName.get(position).getText());
                            File file = new File(ff,App.mDBHandler.getVillagecode(iName.get(pos).getText()));
                            Log.d("file",String.valueOf(file));
                            if(ff.exists())
                            {
                                Log.d("fileexists",String.valueOf(ff));
                                DeleteRecursive(ff,App.mDBHandler.getVillagecode(iName.get(pos).getText()));
                                // boolean result = file.delete();
                                // App.mDBHandler.delete_record_mis(iName.get(position).getText());
                                App.mDBHandler.delete_record_mis(App.mDBHandler.getVillagecode(iName.get(pos).getText()));
                                // Log.d("filedelete", String.valueOf(result));
                            }
                        }
                        catch (Exception e)
                        {
                            Log.e("filedelete", "Exception while deleting file " + e.getMessage());
                        }
                        iName.remove(pos);
                        SpinnerAdapter1.this.notifyDataSetChanged();
                    }
                }
            });
            spnItemName.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    int pos = (int)v.getTag();
                    if (pos!=0) {
                        if (wifi.isConnected() || data.isConnected()) {
                            ArcGISMapImageLayer kgistopo = new ArcGISMapImageLayer("http://kgis.ksrsac.in/kgismaps1/rest/services/Base_Layer/Portal_BaseMapV4/MapServer");
                            ArcGISMapImageLayer kgissatellite = new ArcGISMapImageLayer("https://kgis.ksrsac.in/kgismaps1/rest/services/Satellite/MSS50CM/MapServer");
                            if(Mapchangeflag.equals("1")) {
                                map.setBasemap(Basemap.createTopographic());
                                mView.setMap(map);
                            }
                            else if(Mapchangeflag.equals("2")) {
                                map.setBasemap(Basemap.createImagery());
                                mView.setMap(map);
                            }
                            else if(Mapchangeflag.equals("3")) {
                                basemap1 = new Basemap(kgistopo);
                                map.setBasemap(basemap1);
                                mView.setMap(map);
                            }
                            else if(Mapchangeflag.equals("4")) {
                                basemap1 = new Basemap(kgissatellite);
                                map.setBasemap(basemap1);
                                mView.setMap(map);
                            }
                            else{
                                map.setBasemap(Basemap.createTopographic());
                                mView.setMap(map);
                            }
                        }
                        else{
                            map = new ArcGISMap();
                            mView.setMap(map);
                        }
                        if(featureLayer!=null) {
                            mView.getMap().getOperationalLayers().remove(featureLayer);
                        }
                        Manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                        wifi = Manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                        data = Manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                        str_villagemis = App.mDBHandler.getVillagecode(iName.get(pos).getText());
                       // StrVillageCode = str_villagemis;
                        str_dist = App.mDBHandler.getdistrict1(str_villagemis);
                        str_taluk = App.mDBHandler.getTaluk1(str_villagemis);

                        villagenametxt.setText("Dist: "+str_dist+", Taluk: "+str_taluk+",  Village: "+iName.get(pos).getText());
                        File myFile = new File(Environment.getExternalStorageDirectory() + "/HASIRU/"+ str_villagemis + ".shp");
                        Log.d("shape_File.....","shape file"+Environment.getExternalStorageDirectory() + "/HASIRU/"+ str_villagemis + ".shp");
                        if (myFile.exists()) {
                            shapefileFeatureTable = new ShapefileFeatureTable(
                                    Environment.getExternalStorageDirectory() + "/HASIRU/"+ str_villagemis + ".shp");
                            Log.d("shape_File1223333", String.valueOf(Environment.getExternalStorageDirectory() + "/HASIRU/"+ str_villagemis + ".shp"));
                        } else {
                            Toast.makeText(getApplicationContext(), "File not Present", Toast.LENGTH_SHORT).show();
                        }
                        // Toast.makeText(getApplicationContext(), "Data download Successfully", Toast.LENGTH_SHORT).show();
                        shapefileFeatureTable.loadAsync();
                        shapefileFeatureTable.addDoneLoadingListener(new Runnable() {
                            @Override
                            public void run() {
                                if (shapefileFeatureTable.getLoadStatus() == LoadStatus.LOADED) {
                                    featureLayer = new FeatureLayer(shapefileFeatureTable);
                                    featureLayer.setOpacity(0.7f);
                                    TextSymbol textSymbol = new TextSymbol();
                                    textSymbol.setSize(20);
                                    textSymbol.setColor(0xFF0000FF);
                                    textSymbol.setHaloColor(0xFFFFFF00);
                                    textSymbol.setHaloWidth(2);
                                    JsonObject json = new JsonObject();
                                    JsonObject expressionInfo = new JsonObject();
                                    expressionInfo.add("expression", new JsonPrimitive("$feature.SurveyNumb"));
                                    json.add("labelExpressionInfo", expressionInfo);

                                    json.add("symbol", new JsonParser().parse(textSymbol.toJson()));
                                    LabelDefinition labelDefinition = LabelDefinition.fromJson(json.toString());
                                    featureLayer.getLabelDefinitions().add(labelDefinition);
                                    featureLayer.setLabelsEnabled(true);
                                    mView.getMap().getOperationalLayers().add(featureLayer);
                                    mView.setViewpointAsync(new Viewpoint(featureLayer.getFullExtent()));
                                } else {
                                    Toast.makeText(MainActivity_New.this, "Shapefile feature table failed to load: ", Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                    }
                }
            });
            return rowView;
        }
    }
    private ArrayList<ItemData> get_tpk_files(){
        File ff= new File(Environment.getExternalStorageDirectory(), "HASIRU/");
        File[] fff = ff.listFiles();
        ArrayList<ItemData> filelist = new ArrayList<ItemData>();
        if(fff!=null) {
            Log.d("filelist_length", String.valueOf(fff.length));
            for (File aFff : fff) {
                if(aFff.getName().contains(".zip")){
                    Log.d("Villagename888",aFff.getName().trim());
                    // for(int i=0;i<aFff.length();i++){
                    Log.d("Villagename81",App.mDBHandler.getVillageName(aFff.getName().trim().replace(".zip",""))+"empty");
                    ItemData data=new ItemData(App.mDBHandler.getVillageName(aFff.getName().trim().replace(".zip","")),R.drawable.delete);
                    if(!App.mDBHandler.getVillageName(aFff.getName().trim().replace(".zip","")).equals("")){
                        filelist.add(data);
                    }

                    //  }

                }
                // ItemData data=new ItemData(aFff.getName(),R.drawable.delete);
            }
            ItemData data1=new ItemData("Select Offline Village",0);
            filelist.add(0,data1);
            Log.d("Villagenamedata",String.valueOf(filelist.get(0)));
        }else{
            ItemData data1=new ItemData("Select Offline Village",0);
            filelist.add(0,data1);
            Log.d("Villagenamedata111",String.valueOf(filelist.get(0)));
        }
        return  filelist;
    }

    private void DeleteRecursive(File fileOrDirectory,String village) {
        Log.d("fileOrDirectory",String.valueOf(fileOrDirectory));
        Log.d("fileOrDirectory11",String.valueOf(fileOrDirectory.getAbsolutePath()));
        if (fileOrDirectory.isDirectory())
            for (File child : fileOrDirectory.listFiles())
            {
                Log.d("child",String.valueOf(child.getAbsolutePath()));
                String name = child.getName();
                if (name.startsWith(village)) {
                    child.delete();
                    // DeleteRecursive(child, village);
                    Log.d("village", String.valueOf(village));
                }
            }
        fileOrDirectory.delete();
    }

    public class MyLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location loc) {
            loc1 = loc;
            str_image1_lat = String.valueOf(loc1.getLatitude());
            str_image1_lng = String.valueOf(loc1.getLongitude());
            Deg2UTM(loc1.getLatitude(), loc1.getLongitude());
            long time = loc1.getTime();
            Date date = new Date(time);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            currentDateandTime = sdf.format(date);
            latlng1.setText((getPrecisionvalue(loc1.getLatitude())) + " N," + (getPrecisionvalue(loc1.getLongitude())) + " E   " + sdf.format(date));
            //  Log.d("locationchange1111", String.valueOf(loc1.getAccuracy()));
//            latlng.setText(String.valueOf(loc1.getLatitude()) + " N," + String.valueOf(loc1.getLongitude()) + " E   " + sdf.format(date));
        }

        @Override
        public void onProviderDisabled(String provider) {
            Toast.makeText(getApplicationContext(), "GPS disabled", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderEnabled(String provider) {
            // Toast.makeText(getApplicationContext(), "GPS enabled", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    }
    private void showGPSDisabledAlertToUser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(callGPSSettingIntent);

                    }
                });

        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
    public void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            // return false;
        } else {
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                showGPSDisabledAlertToUser();
            }
            //  return true;
        }

    }

    private AddDataModel Deg2UTM(double Lat, double Lon) {
        double Easting;
        double Northing;
        int Zone;
        char Letter;
        Zone = 43;
        if (Lat < -72)
            Letter = 'C';
        else if (Lat < -64)
            Letter = 'D';
        else if (Lat < -56)
            Letter = 'E';
        else if (Lat < -48)
            Letter = 'F';
        else if (Lat < -40)
            Letter = 'G';
        else if (Lat < -32)
            Letter = 'H';
        else if (Lat < -24)
            Letter = 'J';
        else if (Lat < -16)
            Letter = 'K';
        else if (Lat < -8)
            Letter = 'L';
        else if (Lat < 0)
            Letter = 'M';
        else if (Lat < 8)
            Letter = 'N';
        else if (Lat < 16)
            Letter = 'P';
        else if (Lat < 24)
            Letter = 'Q';
        else if (Lat < 32)
            Letter = 'R';
        else if (Lat < 40)
            Letter = 'S';
        else if (Lat < 48)
            Letter = 'T';
        else if (Lat < 56)
            Letter = 'U';
        else if (Lat < 64)
            Letter = 'V';
        else if (Lat < 72)
            Letter = 'W';
        else
            Letter = 'X';
        Easting = 0.5 * Math.log((1 + Math.cos(Lat * Math.PI / 180) * Math.sin(Lon * Math.PI / 180 - (6 * Zone - 183) * Math.PI / 180)) / (1 - Math.cos(Lat * Math.PI / 180) * Math.sin(Lon * Math.PI / 180 - (6 * Zone - 183) * Math.PI / 180))) * 0.9996 * 6399593.62 / Math.pow((1 + Math.pow(0.0820944379, 2) * Math.pow(Math.cos(Lat * Math.PI / 180), 2)), 0.5) * (1 + Math.pow(0.0820944379, 2) / 2 * Math.pow((0.5 * Math.log((1 + Math.cos(Lat * Math.PI / 180) * Math.sin(Lon * Math.PI / 180 - (6 * Zone - 183) * Math.PI / 180)) / (1 - Math.cos(Lat * Math.PI / 180) * Math.sin(Lon * Math.PI / 180 - (6 * Zone - 183) * Math.PI / 180)))), 2) * Math.pow(Math.cos(Lat * Math.PI / 180), 2) / 3) + 500000;
        Easting = Math.round(Easting * 100) * 0.01;
        Northing = (Math.atan(Math.tan(Lat * Math.PI / 180) / Math.cos((Lon * Math.PI / 180 - (6 * Zone - 183) * Math.PI / 180))) - Lat * Math.PI / 180) * 0.9996 * 6399593.625 / Math.sqrt(1 + 0.006739496742 * Math.pow(Math.cos(Lat * Math.PI / 180), 2)) * (1 + 0.006739496742 / 2 * Math.pow(0.5 * Math.log((1 + Math.cos(Lat * Math.PI / 180) * Math.sin((Lon * Math.PI / 180 - (6 * Zone - 183) * Math.PI / 180))) / (1 - Math.cos(Lat * Math.PI / 180) * Math.sin((Lon * Math.PI / 180 - (6 * Zone - 183) * Math.PI / 180)))), 2) * Math.pow(Math.cos(Lat * Math.PI / 180), 2)) + 0.9996 * 6399593.625 * (Lat * Math.PI / 180 - 0.005054622556 * (Lat * Math.PI / 180 + Math.sin(2 * Lat * Math.PI / 180) / 2) + 4.258201531e-05 * (3 * (Lat * Math.PI / 180 + Math.sin(2 * Lat * Math.PI / 180) / 2) + Math.sin(2 * Lat * Math.PI / 180) * Math.pow(Math.cos(Lat * Math.PI / 180), 2)) / 4 - 1.674057895e-07 * (5 * (3 * (Lat * Math.PI / 180 + Math.sin(2 * Lat * Math.PI / 180) / 2) + Math.sin(2 * Lat * Math.PI / 180) * Math.pow(Math.cos(Lat * Math.PI / 180), 2)) / 4 + Math.sin(2 * Lat * Math.PI / 180) * Math.pow(Math.cos(Lat * Math.PI / 180), 2) * Math.pow(Math.cos(Lat * Math.PI / 180), 2)) / 3);
        if (Letter < 'M')
            Northing = Northing + 10000000;
        Northing = Math.round(Northing * 100) * 0.01;
        lmodel = new AddDataModel();
        lmodel.setEasting(String.valueOf(Easting));
        lmodel.setNorthing(String.valueOf(Northing));
        // Log.d("modelclass",String.valueOf(Easting));
        return lmodel;
    }

    private void showPopUpMsg(String msg, String title) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity_New.this);
        dialog.setCancelable(false);
        dialog.setTitle(title);
        dialog.setMessage(msg);
        dialog.setNegativeButton("Ok ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        final AlertDialog alert = dialog.create();
        alert.show();


    }

    public void showDialog(String output){

        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(MainActivity_New.this);
        alertDialogBuilder.setMessage(output);
        alertDialogBuilder.setPositiveButton("ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        arg0.dismiss();
                    }
                });
        android.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void showUpdateDialog() {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("A New Update is Available");
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //  startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://kgis.ksrsac.in/kgisdocuments/Apk/KSDA_V1.01.apk")));
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse
                        ("https://play.google.com/store/apps/details?id=com.ksrsac.hasiru")));
                dialog.dismiss();
            }
        });
        builder.setCancelable(false);
        dialog = builder.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    private SSLContext setSSlCert() {
        SSLContext sslContext = null;
        try {
            TrustManager[] tm = {
                    new X509TrustManager() {
                        public void checkClientTrusted(X509Certificate[]
                                                               chain, String authType) throws CertificateException {}
                        public void checkServerTrusted(X509Certificate[]
                                                               chain, String authType) throws CertificateException {}
                        @Override
                        public X509Certificate[] getAcceptedIssuers() { return null; }
                    }
            };
            sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, tm, null);

        } catch(Exception e) {
            e.printStackTrace();
        }
        return sslContext;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mView.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mView.dispose();
    }
    public void onmapload(){

        b = getIntent().getExtras();
        Log.d("Bundle", String.valueOf(b));
       if (b != null) {
    /*   Lgdcodes = "610792";
        // Lgdcodes = "610723";
        //  Lgdcodes = "610792nulll";
        dept_code= "02_1";
        app_code= "02_2";
        type ="lgd";
        Serveynostr= "2";
        Packagename ="com.example.horticulture";
        Classname = "PreInspectionBeneficiaryDataCapture";*/
            Lgdcodes = b.getString("LG_Village");
            dept_code= "02_1";
            app_code="02_2";
            type = "lgd";
            Serveynostr = b.getString("surveyno");
            Packagename = b.getString("package_name");
            Classname = b.getString("class_name");
        Log.d("Lgdcodesid.......", Lgdcodes + "nulll");

        if (App.mDBHandler.getlgdcode().contains(Lgdcodes)) {
            if(featureLayer!=null) {
                mView.getMap().getOperationalLayers().remove(featureLayer);
            }
            Log.d("Lgdcodes111no",Lgdcodes+"nulll");
            StrVillageCode = App.mDBHandler.getVillageCode(Lgdcodes);
            StrVillage = App.mDBHandler.getVillageName1(Lgdcodes);
            str_dist = App.mDBHandler.getDistname(Lgdcodes);
            str_taluk = App.mDBHandler.getTaluk(Lgdcodes);
            Log.d("StrVillageCode111",StrVillageCode+"null");
            villagenametxt.setText("Dist: "+str_dist+", Taluk:"+str_taluk+",  Village: "+StrVillage);
            File myFile = new File(Environment.getExternalStorageDirectory() + "/HASIRU/"+ StrVillageCode + ".shp");
            Log.d("shape_File.....","shape file"+Environment.getExternalStorageDirectory() + "/HASIRU/" + StrVillageCode + ".shp");
            if (myFile.exists()) {
                shapefileFeatureTable = new ShapefileFeatureTable(
                        Environment.getExternalStorageDirectory() + "/HASIRU/"  + StrVillageCode + ".shp");
                Log.d("shape_File1223333", String.valueOf(Environment.getExternalStorageDirectory() + "/HASIRU/" + StrVillageCode + ".shp"));
                shapefileFeatureTable.loadAsync();
                shapefileFeatureTable.addDoneLoadingListener(new Runnable() {
                    @Override
                    public void run() {
                        if (shapefileFeatureTable.getLoadStatus() == LoadStatus.LOADED) {
                            featureLayer = new FeatureLayer(shapefileFeatureTable);
                            featureLayer.setOpacity(0.7f);
                            TextSymbol textSymbol = new TextSymbol();
                            textSymbol.setSize(20);
                            textSymbol.setColor(0xFF0000FF);
                            textSymbol.setHaloColor(0xFFFFFF00);
                            textSymbol.setHaloWidth(2);
                            JsonObject json = new JsonObject();
                            JsonObject expressionInfo = new JsonObject();
                            expressionInfo.add("expression", new JsonPrimitive("$feature.SurveyNumb"));
                            json.add("labelExpressionInfo", expressionInfo);

                            json.add("symbol", new JsonParser().parse(textSymbol.toJson()));
                            LabelDefinition labelDefinition = LabelDefinition.fromJson(json.toString());
                            featureLayer.getLabelDefinitions().add(labelDefinition);
                            featureLayer.setLabelsEnabled(true);
                            mView.getMap().getOperationalLayers().add(featureLayer);
                            mView.setViewpointAsync(new Viewpoint(featureLayer.getFullExtent()));
//                                new queryForSurveyNumber().execute(survey_number);
                            querySurveyNumber();
                        } else {
                            showPopUpMsg("Shapefile feature table failed to load:","Alert");
                            //Toast.makeText(MainActivity.this, "Shapefile feature table failed to load: ", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            } else {
                if ((wifi.isConnected()) || (data.isConnected())) {
                    File ff= new File(Environment.getExternalStorageDirectory(), "HASIRU/");
                    Log.d("filelist_length111", String.valueOf(ff.length()));
                    File[] list = ff.listFiles();
                    int count = 0;
                    // DeleteRecursive
                    if(list!=null){
                        for (File f: list) {
                            String name = f.getName();
                            if (name.endsWith(".zip")) {
                                count++;
                                Log.d("count", String.valueOf(count));
                            }
                        }
                    }
                    if(count<10){
                        Log.d("count", String.valueOf(count));
                        if(countresume==0) {
                            countresume=1;
                            new getVillage().execute(Lgdcodes,dept_code,app_code,type);
                        }

                    }
                    else{
                        showPopUpMsg("You can not download more than ten village.","Alert");
                    }
                }
                else {
                    showDialog("Please see that internet connection is available or not ...?");
                }

            }

        } else {
            Log.d("Lgdcodesresume", Lgdcodes + "nulllqqq");
            ContentValues value = new ContentValues();
            value.put(SqliteHelper.LGDCODE, Lgdcodes);
            value.put(SqliteHelper.STATUS, "1");
            App.mDBHandler.insert_values(SqliteHelper.TABLE_LGDCODE, value);
            if ((wifi.isConnected()) || (data.isConnected())) {
                File ff= new File(Environment.getExternalStorageDirectory(), "HASIRU/");
                Log.d("filelist_length111", String.valueOf(ff.length()));
                File[] list = ff.listFiles();
                int count = 0;
                if(list!=null){
                    for (File f: list) {
                        String name = f.getName();
                        if (name.endsWith(".zip")) {
                            count++;
                            Log.d("count", String.valueOf(count));


                        }
                    }
                }
                if(count<10){
                    Log.d("count", String.valueOf(count));
                    if(countresume==0) {
                        countresume=1;
                        new getVillage().execute(Lgdcodes,dept_code,app_code,type);
                    }
                  //  new getVillage().execute(Lgdcodes,dept_code,app_code,type);
                }
                else{
                    showPopUpMsg("You can not download more than ten village.","Alert");
                }
            }
            else {
                showDialog("Please see that internet connection is available or not ...?");
            }
        }
         }
    }
}
