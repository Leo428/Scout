//package com.example.huzheyuan.scout.activities;
//
//import android.app.Service;
////import android.content.ContentValues;
//import android.content.Context;
//import android.content.Intent;
////import android.database.sqlite.SQLiteDatabase;
//import android.os.Bundle;
//import android.os.Vibrator;
//import android.util.Log;
//import android.view.MotionEvent;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.Button;
//import android.app.Activity;
//import android.widget.CheckBox;
//import android.widget.CompoundButton;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.Spinner;
//import android.widget.Switch;
//import android.widget.TextView;
//import android.os.CountDownTimer;
//import android.widget.Toast;
//import java.io.File;
//import android.app.AlertDialog;
//import android.content.DialogInterface;
//
//import com.example.huzheyuan.scout.R;
//import com.example.huzheyuan.scout.realmService.VexStarRealm;
//import com.example.huzheyuan.scout.utilities.IconUtil;
////import com.example.huzheyuan.scout.sqliteService.DataBaseContext;
////import com.example.huzheyuan.scout.sqliteService.DataBaseHelper;
//
//import io.realm.Realm;
//import io.realm.RealmConfiguration;
//import static android.content.ContentValues.TAG;
//
//public class Vex2016Activity extends Activity {
//    RelativeLayout frame, mainLayout;
//    TextView cX,cY,cT,tDriverStarNear,tDriverStarFar,tDriverCubeNear,tDriverCubeFar;
//    TextView tAutoStarNear,tAutoStarFar, tAutoCubeNear,tAutoCubeFar ;
//    ImageView starPic,cubePic;
//    Button nearRightScore,farRightScore,nearLeftScore,farLeftScore,bAuto,bDriver,bClear,bTStart;
//    Switch sSide,sPoint;
//    Spinner teamNumSpin;
//    CheckBox lifted;
//    String strCX,strCY,strCAN,strCAF,strCDN,strCDF,strSAN,strSAF,strSDN,strSDF,teamNumber, mode,
//            lift = "not lifted";
//    long time;
//    String[] teamNumberArray;
//    int cubeAutoNear = 0,cubeAutoFar = 0,starAutoNear = 0,starAutoFar = 0;
//    int cubeDriverNear = 0,cubeDriverFar = 0,starDriverNear = 0,starDriverFar = 0,arrayPosition = 0;
//    boolean leftSide = true,up = true,liftedIcon = false,autoMode = false;
//    boolean visibilityStar = false,visibilityCube = false;//Set all the variables
//    CountDownTimer cuteDriver = null,cuteAuto = null;
////    DataBaseContext dBContext;
////    DataBaseHelper dataBaseHelper;
////    SQLiteDatabase dataBase;
//    Realm realm;
//    Vibrator vibrator;
//    // "Vibrator" is a default/traditional name of
//    // the api class written by brilliant Google programmers
//    AlertDialog alert = null;
//    AlertDialog.Builder builder = null;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_vex2016);
////        startDataBase();
//        Realm.init(this);// init
//        RealmConfiguration realmConfiguration = new
//                RealmConfiguration.Builder()
//                .directory(
//                        getApplicationContext()
//                                .getExternalFilesDir(null)
//                                .getAbsoluteFile()) //LMAO, finally I found this! Not working yet
//                .name("vexData.realm") //config the file name
//                .build(); // build
//        realm = Realm.getInstance(realmConfiguration);
//        openOrCreateRealm(this,realmConfiguration);
//    }
//    @Override
//    protected void onStart(){
//        super.onStart();
//        findViews();
//        clear();
//        lift();
//        selectTeam();
//        // finalize the icon iconView, which you will understand soon... Try to delete this code!!
//        final IconUtil iconUtil = new IconUtil(Vex2016Activity.this, "VexStar");
//        sSide.setChecked(false);
//        sPoint.setChecked(false);
//        bTStart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(Vex2016Activity.this, QRcodeActivity.class));
//            }
//        });
//        /**
//         * Don't even try to make these switchers into method! And don't ask me why, think, plus
//         * I've done the experiment already! (Well, it is doable, it is just me ~~)
//         */
//        sSide.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) leftSide = false;
//                else leftSide = true;
//                resetIcon(iconUtil);
//            }
//        });
//
//        sPoint.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked) up = false;
//                else up = true;
//                resetIcon(iconUtil);
//            }
//        });
//        bAuto.setOnClickListener(new View.OnClickListener() { //Auto start button
//            @Override
//            public void onClick(View v) {
//                autoMode = true;
//                mode = "auto";
//                Toast.makeText(Vex2016Activity.this, "Auto Mode Starts", Toast.LENGTH_SHORT).show();
//                timerBug();
//                cuteAuto = new CountDownTimer(15000, 1000) {
//                    // Auto time limits 15s
//                    public void onTick(long millisUntilFinished) {
//                        scoreStarTally();
//                        scoreCubeTally();
//                        time = millisUntilFinished / 1000;
//                        cT.setText("End: " + time);
//                        //the tallies are for counting the scoring actions ...
//                        //Then, this is the code for drawing a icon on the screen!!!
//                        iconUtil.setOnTouchListener(new View.OnTouchListener() {
//                            @Override
//                            public boolean onTouch(View view, MotionEvent event) {
//                                //设置妹子显示的位置, set the icon's position
//                                iconUtil.setBitmapX(event.getX() - 36);
//                                iconUtil.setBitmapY(event.getY() - 44);
//                                // the only reason why I minus number from the position is ...
//                                // you can try to remove it and you will know why!!!
//                                drawIcon(iconUtil);
//                                return true; // you have to return true here >_<
//                            }
//                        });
//                    }
//                    public void onFinish(){ // on finish is a important method to know, learn it!
//                        resetIcon(iconUtil);
//                        iconUtil.setOnTouchListener(null);
//                        cT.setText("End!");
//                    }
//                }.start(); // end of the timer
//                preventMoron(); // not being offensive
//            }
//        });
//        //Driver Mode Start, same structure and usage as auto mode
//        bDriver.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                autoMode = false;
//                mode = "driver";
//                Toast.makeText(Vex2016Activity.this, "Driver Mode Starts", Toast.LENGTH_SHORT).show();
//                timerBug();
//                cuteDriver = new CountDownTimer(105000, 1000) { // Driver time limits 105s
//                    public void onTick(long millisUntilFinished) {
//                        time = millisUntilFinished / 1000;
//                        cT.setText("End: " + time);
//                        scoreStarTally();
//                        scoreCubeTally();
//                        iconUtil.setOnTouchListener(new View.OnTouchListener() {
//                            @Override
//                            public boolean onTouch(View view, MotionEvent event) {
//                                //设置妹子显示的位置
//                                iconUtil.setBitmapX(event.getX() - 36);
//                                iconUtil.setBitmapY(event.getY() - 44);
//                                drawIcon(iconUtil);
//                                return true;
//                            }
//                        });
//                    }
//                    public void onFinish(){
//                        resetIcon(iconUtil);
//                        iconUtil.setOnTouchListener(null);
//                        cT.setText("End!");
//                    }
//                }.start();
//                preventMoron();
//            }
//        });
//    frame.addView(iconUtil); // add a little cute icon on the screen, important!!!
//    }
//
//    @Override
//    public void onDestroy(){
//        super.onDestroy();
////        dataBase.close(); // plz shut down everything before you leave!
//        realm.close();
//    }
//////////////////////////////////////////////////////////////////////////////////////////////////////
//    // Here is all the methods I am using!!!
//    // Try to make it explicit ...
//    public void findViews() {
//        cX = (TextView) findViewById(R.id.coordinateX);
//        cY = (TextView) findViewById(R.id.coordinateY);
//        cT = (TextView) findViewById(R.id.countDownVex);
//        tDriverStarNear = (TextView) findViewById(R.id.textDriverStarNear);
//        tDriverStarFar = (TextView) findViewById(R.id.textDriverStarFar);
//        tDriverCubeNear = (TextView) findViewById(R.id.textDriverCubeNear);
//        tDriverCubeFar = (TextView) findViewById(R.id.textDriverCubeFar);
//        tAutoStarNear = (TextView) findViewById(R.id.textAutoStarNear);
//        tAutoStarFar = (TextView) findViewById(R.id.textAutoStarFar);
//        tAutoCubeNear = (TextView) findViewById(R.id.textAutoCubeNear);
//        tAutoCubeFar = (TextView) findViewById(R.id.textAutoCubeFar);
//        starPic = (ImageView) findViewById(R.id.popStarView);
//        cubePic = (ImageView) findViewById(R.id.popCubeView);
//        bAuto = (Button) findViewById(R.id.btnAuto);
//        bDriver = (Button) findViewById(R.id.btnDrive);
//        bClear = (Button) findViewById(R.id.btnClear);
//        nearRightScore = (Button) findViewById(R.id.rightNear);
//        farRightScore = (Button) findViewById(R.id.rightFar);
//        nearLeftScore = (Button) findViewById(R.id.leftNear);
//        farLeftScore = (Button) findViewById(R.id.leftFar);
//        bTStart = (Button) findViewById(R.id.btnBt);
//        sSide = (Switch) findViewById(R.id.stchSide);
//        sPoint = (Switch) findViewById(R.id.stchPoint);
//        teamNumSpin = (Spinner) findViewById(R.id.spinVexTeamNum);
//        lifted = (CheckBox) findViewById(R.id.Checklifted);
//        frame = (RelativeLayout) findViewById(R.id.fieldLayout);
//        mainLayout = (RelativeLayout) findViewById(R.id.vexMainRLayout);
//        vibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
//    }
//    public void clear() // Highly emergency issues right here!!! The clear method is broken and not function too properly.
//    //It need to be rewritten and clarified!!!
//    {
//        bClear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                cubeAutoNear = 0;
//                cubeAutoFar = 0;
//                starAutoNear = 0;
//                starAutoFar = 0;
//                cubeDriverNear = 0;
//                cubeDriverFar = 0;
//                starDriverNear = 0;
//                starDriverFar = 0;
//                liftedIcon = false;
//                visibilityStar = false;
//                visibilityCube = false;
//                teamNumSpin.setSelection(0);
//                lifted.setChecked(false);
//                strSDN = Integer.toString(starDriverNear);
//                tDriverStarNear.setText("Near Star: " +strSDN);
//                strSDF = Integer.toString(starDriverFar);
//                tDriverStarFar.setText("Far Star: " + strSDF);
//                strCDN = Integer.toString(cubeDriverNear);
//                tDriverCubeNear.setText("Near Cube: " +strCDN);
//                strCDF = Integer.toString(cubeDriverFar);
//                tDriverCubeFar.setText("Far Cube: " + strCDF);
//                strSAN = Integer.toString(starAutoNear);
//                tAutoStarNear.setText("Near Star: " +strSAN);
//                strSAF = Integer.toString(starAutoFar);
//                tAutoStarFar.setText("Far Star: " + strSAF);
//                strCAN = Integer.toString(cubeAutoNear);
//                tAutoCubeNear.setText("Near Cube: " +strCAN);
//                strCAF = Integer.toString(cubeAutoFar);
//                tAutoCubeFar.setText("Far Cube: " + strCAF);
//                lift = "not lifted";
//                mode = "";
//                popStar();
//                popCube();
//                //end all the running timer!
//                timerBug(); // need to clear all the textView to 0 or empty!
//           }
//        });
//    }
//    public void selectTeam(){
//        teamNumSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                teamNumberArray = getResources().getStringArray(R.array.data);
//                teamNumber = teamNumberArray[position];
//                arrayPosition = position;
//                Toast.makeText(Vex2016Activity.this, "Team: "+teamNumberArray[position], Toast.LENGTH_SHORT).show();
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
//    }
//    public void preventMoron(){
//        if(arrayPosition == 0){
//            timerBug();
//            alert = null;
//            builder = new AlertDialog.Builder(Vex2016Activity.this);
//            alert = builder.setTitle("Alarm：")
//                    .setMessage("Please choose team number!")
//                    .setPositiveButton("Sorry, Please forgive me", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            Toast.makeText(Vex2016Activity.this, "You are forgiven!", Toast.LENGTH_SHORT).show();
//                        }
//                    }).create();             //创建AlertDialog对象
//            alert.show();                    //显示对话框
//        }
//    }
//    public void drawIcon(IconUtil icon) {
//        mapBoundary(icon);
//        invalidateIcon(icon);
//    }
//    public void mapBoundary(IconUtil iconUtil){
//        // For the next person trying to "optimize" this code, please increment the following counter for the
//        // the next fool who doesn't read this:
//        // HoursWasted = 42 hours;    ------ by Eddie, a little freshman who tried this!
//        if(leftSide) {
//            if (iconUtil.getBitmapX() < 0) iconUtil.setBitmapX(0);
//            else if (iconUtil.getBitmapX() > 240) iconUtil.setBitmapX(240);
//            if (iconUtil.getBitmapY() < 0) iconUtil.setBitmapY(0);
//            else if (iconUtil.getBitmapY() > 520) iconUtil.setBitmapY(520);
//        }
//        else if(!leftSide) {
//            if (iconUtil.getBitmapX() < 295) iconUtil.setBitmapX(295);
//            else if (iconUtil.getBitmapX() > 530) iconUtil.setBitmapX(530);
//            if (iconUtil.getBitmapY() < 0) iconUtil.setBitmapY(0);
//            else if (iconUtil.getBitmapY() > 520) iconUtil.setBitmapY(520);
//        }
//    }
//    public void invalidateIcon(IconUtil iconUtil){
//        //调用重绘方法
//        iconUtil.invalidate();
//        strCX = Float.toString(iconUtil.getBitmapX());
//        strCY = Float.toString(iconUtil.getBitmapY());
//        cX.setText("x-axis: " + strCX);
//        cY.setText("y-axis: " + strCY);
//    }
//    public void resetIcon(IconUtil iconUtil) {
//        if(leftSide && up) {
//            iconUtil.setBitmapX(15);
//            iconUtil.setBitmapY(110);
//            invalidateIcon(iconUtil);
//        }
//        else if(leftSide && !up){
//            iconUtil.setBitmapX(15);
//            iconUtil.setBitmapY(410);
//            invalidateIcon(iconUtil);
//        }
//        else if(!leftSide && up){
//            iconUtil.setBitmapX(510);
//            iconUtil.setBitmapY(110);
//            invalidateIcon(iconUtil);
//        }
//        else if(!leftSide && !up){
//            iconUtil.setBitmapX(510);
//            iconUtil.setBitmapY(410);
//            invalidateIcon(iconUtil);
//        }
//    }
//    // Driver mode score counters
//    public void driverNearStar() {
//        ++starDriverNear;
//        strSDN = Integer.toString(starDriverNear);
//        tDriverStarNear.setText("Near Star: " +strSDN);
//        insertRealm();
//    }
//    public void driverFarStar() {
//        starDriverFar = starDriverFar + 2;
//        strSDF = Integer.toString(starDriverFar);
//        tDriverStarFar.setText("Far Star: " + strSDF);
//        insertRealm();
//    }
//    public void driverNearCube() {
//        cubeDriverNear = cubeDriverNear + 2;
//        strCDN = Integer.toString(cubeDriverNear);
//        tDriverCubeNear.setText("Near Cube: " +strCDN);
//        insertRealm();
//    }
//    public void driverFarCube() {
//        cubeDriverFar = cubeDriverFar + 4;
//        strCDF = Integer.toString(cubeDriverFar);
//        tDriverCubeFar.setText("Far Cube: " + strCDF);
//        insertRealm();
//    }
//    // Auto mode score counters
//    public void autoNearStar() {
//        ++starAutoNear;
//        strSAN = Integer.toString(starAutoNear);
//        tAutoStarNear.setText("Near Star: " +strSAN);
//        insertRealm();
//    }
//    public void autoFarStar() {
//        starAutoFar = starAutoFar + 2;
//        strSAF = Integer.toString(starAutoFar);
//        tAutoStarFar.setText("Far Star: " + strSAF);
//        insertRealm();
//    }
//    public void autoNearCube() {
//        cubeAutoNear = cubeAutoNear + 2;
//        strCAN = Integer.toString(cubeAutoNear);
//        tAutoCubeNear.setText("Near Cube: " +strCAN);
//        insertRealm();
//    }
//    public void autoFarCube() {
//        cubeAutoFar = cubeAutoFar + 4;
//        strCAF = Integer.toString(cubeAutoFar);
//        tAutoCubeFar.setText("Far Cube: " + strCAF);
//        insertRealm();
//    }
//    public void scoreStarTally() {
//        if(leftSide) {
//            farRightScore.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(autoMode) autoFarStar();
//                    else driverFarStar();
//                    visibilityStar = true;
//                    visibilityCube = false;
//                    popCube();
//                    popStar();
//                }
//            });
//            nearRightScore.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(autoMode) autoNearStar();
//                    else driverNearStar();
//                    visibilityStar = true;
//                    visibilityCube = false;
//                    popCube();
//                    popStar();
//                }
//            });
//        }
//        else if (!leftSide) {
//            farLeftScore.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(autoMode)autoFarStar();
//                    else driverFarStar();
//                    visibilityStar = true;
//                    visibilityCube = false;
//                    popCube();
//                    popStar();
//                }
//            });
//            nearLeftScore.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(autoMode) autoNearStar();
//                    else driverNearStar();
//                    visibilityStar = true;
//                    visibilityCube = false;
//                    popCube();
//                    popStar();
//                }
//            });
//        }
//    }
//    public void scoreCubeTally() {
//        if(leftSide) {
//            farRightScore.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    if(autoMode)autoFarCube();
//                    else driverFarCube();
//                    visibilityCube = true;
//                    visibilityStar = false;
//                    popStar();
//                    popCube();
//                    vibration();
//                    return true;
//                }
//            });
//
//            nearRightScore.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    if(autoMode) autoNearCube();
//                    else driverNearCube();
//                    visibilityCube = true;
//                    visibilityStar = false;
//                    popStar();
//                    popCube();
//                    vibration();
//                    return true;
//                }
//            });
//        }
//        else if (!leftSide) {
//            farLeftScore.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    if(autoMode)autoFarCube();
//                    else driverFarCube();
//                    visibilityCube = true;
//                    visibilityStar = false;
//                    popStar();
//                    popCube();
//                    vibration();
//                    return true;
//                }
//            });
//            nearLeftScore.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    if(autoMode) autoNearCube();
//                    else driverNearCube();
//                    visibilityStar = false;
//                    visibilityCube = true;
//                    popStar();
//                    popCube();
//                    vibration();
//                    return true;
//                }
//            });
//        }
//    }
//    public void vibration(){ // this is a method for vibrator, makes the device to vibrate >_<
//        vibrator.cancel();
//        vibrator.vibrate(1000);
//    }
//    public void lift() {
//        lifted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(lifted.isChecked()) {
//                    liftedIcon = true;
//                    lift = "lifted";
//                }
//                else {
//                    liftedIcon = false;
//                    lift = "not lifted";
//                }
//                System.out.println(liftedIcon);
//            }
//        });
//    }
//    public void popStar() {
//        if(visibilityStar) starPic.setVisibility(View.VISIBLE);
//        else starPic.setVisibility(View.GONE);
//    }
//    public void popCube() {
//        if (visibilityCube) cubePic.setVisibility(View.VISIBLE);
//        else cubePic.setVisibility(View.GONE);
//    }
//    public void timerBug(){// the if statements here are for avoiding multiple countdown timer bug
//        if(cuteDriver != null){
//            cuteDriver.cancel();
//            cuteDriver.onFinish();
//        }
//        if (cuteAuto != null){
//            cuteAuto.cancel();
//            cuteAuto.onFinish();
//        }
//    }
////    public void startDataBase() {
////        dataBaseHelper = new DataBaseHelper(Vex2016Activity.this);
////        //dataBaseHelper.getReadableDatabase();
////        //dataBaseHelper.getWritableDatabase();
////        File f = new File(getExternalFilesDir(null).getAbsolutePath() + File.separator + dataBaseHelper.DATABASE_NAME);// 创建文件
////        if(f.exists()) {
////            Toast.makeText(Vex2016Activity.this, "Database has been created", Toast.LENGTH_SHORT).show();
////            System.out.println(f);
////        }
////    }
////    public void insertData(){
////        dataBase = dataBaseHelper.getWritableDatabase();
////        ContentValues values = new ContentValues();
////        values.put("teamNumber", teamNumber);
////        values.put("Mode", mode);
////        values.put("time", time);
////        values.put("SAN", strSAN);
////        values.put("SAF", strSAF);
////        values.put("SDN", strSDN);
////        values.put("SDF", strSDF);
////        values.put("CAN", strCAN);
////        values.put("CAF", strCAF);
////        values.put("CDN", strCDN);
////        values.put("CDF", strCDF);
////        values.put("lifted", lift);
////        values.put("positionX", strCX);
////        values.put("positionY", strCY);
////        dataBase.insert("teamData",null,values);
////        dataBase.close();
////    }
//
//    private void openOrCreateRealm(Context context,RealmConfiguration realmConfiguration){
//        File realmFile = new File(realmConfiguration.getPath());
//        if(realmFile.exists()){
//            Toast.makeText(this,realmFile.getPath(),Toast.LENGTH_LONG).show();
//            Log.d(TAG, "openOrCreateRealm: created");
//            Log.i("R: ", realmConfiguration.getPath());
//        }
//    }
//
//    private void insertRealm(){
//        realm.executeTransaction(new Realm.Transaction(){
//            @Override
//            public void execute(Realm realm){
//                VexStarRealm vexStarRealm = realm.createObject(VexStarRealm.class);
//                vexStarRealm.setTeamName(teamNumber);
//                vexStarRealm.setGameMode(mode);
//                vexStarRealm.setTime(time);
//                vexStarRealm.setSAN(strSAN);
//                vexStarRealm.setSAF(strSAF);
//                vexStarRealm.setSDN(strSDN);
//                vexStarRealm.setSDF(strSDF);
//                vexStarRealm.setCAN(strCAN);
//                vexStarRealm.setCAF(strCAF);
//                vexStarRealm.setCDN(strCDN);
//                vexStarRealm.setCDF(strCDF);
//                vexStarRealm.setLifted(lift);
//                vexStarRealm.setPositionX(strCX);
//                vexStarRealm.setPositionY(strCY);
//            }
//        });
//    }
//}