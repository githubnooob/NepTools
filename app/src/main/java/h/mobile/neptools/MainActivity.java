package h.mobile.neptools;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenu;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import h.mobile.neptools.Utils.*;

public class MainActivity extends AppCompatActivity {

    private CardView torchCardView;
    private CardView keyboardCardView;
    private CardView audioRecordCardView;
    private CardView mobileHelpCardView;
    private CardView dateconverterCardView;
    private CardView qrCodeScannerCardView;

    private NavigationView navigationView;
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private Toolbar toolbar;

    private ImageView torchImageView;

    private Boolean is_flash_on;
    private Boolean hasFlash;
    private Camera.Parameters p;
    private static final int CAMERA_REQUEST = 50;


    private Camera mCamera;
    private CameraManager camManager;
    private Boolean oldCameraUsedForNewMobile = false;



    // for date Converter
    private Spinner yearNepSpinner;
    private Spinner monthNepSpinner;
    private Spinner dayNepSpinner;

    private Spinner yearEngSpinner;
    private Spinner monthEngSpinner;
    private Spinner dayEngSpinner;

    private Button convertToEnglish;
    private Button  convertToNepali;

    private TextView dateConvertedTextView;

    //NEP DATE INITIAL
    int startingNepYear = 2000;
    int startingNepMonth = 1;
    int startingNepDay = 1;
    int dayOfWeek = Calendar.WEDNESDAY;

    //EQUIVALENT ENG DATE
    int startingEngYear = 1943;
    int startingEngMonth = 4;
    int startingEngDay = 14;



    //starting eng date
    int startingEngYearForSelection = 1944;
    int startingEngMonthForSelection = 1;
    int startingEngDayForSelection = 1;

    //equivalent Nepali date for it
    int startingNepYearForSelection = 2000;
    int startingNepMonthForSelection = 9;
    int startingNepDayForSelection = 17;


    private String [] nepMonths = new String[12];
    private String [] nepDays = new String[32];
    private Map<String, String> shortMonthToLongMonthMatcher;


    int yearSelectedOnClick = 0;
    int noOfDaysOnClick = 30;
    int monthPositionOnClick = 1;

    private ArrayAdapter<String> yearAdapter;
    private ArrayAdapter<String> monthAdapter;
    private ArrayAdapter<String> dayAdapter;

    private ArrayAdapter<String> yearEngToNepAdapter;
    private ArrayAdapter<String> monthEngToNepAdapter;
    private ArrayAdapter<String> dayEngToNepAdapter;

    private ProgressBar progressbarForConverting;

    private  Calendar currentEngDate;
    private Calendar baseEngDate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();


        hasFlash= getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        is_flash_on = false;

    }


    @Override public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }


        private void initialize() {

        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, 50);
        }

        navigationView =  findViewById(R.id.navigationMenu);
        torchCardView = findViewById(R.id.torchCardView);
        keyboardCardView = findViewById(R.id.keyboardCardView);
        audioRecordCardView = findViewById(R.id.audioRecordCardView);
        mobileHelpCardView = findViewById(R.id.mobileHelpCardView);
        dateconverterCardView = findViewById(R.id.dateconverterCardView);
        qrCodeScannerCardView = findViewById(R.id.qrCodeScannerCardView);
        torchImageView = findViewById(R.id.torchImageView);


        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dl = (DrawerLayout)findViewById(R.id.drawerLayout);
        t = new ActionBarDrawerToggle(this, dl,R.string.Open, R.string.Close);

        dl.addDrawerListener(t);
        t.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.privacy_policy:
                        buildYetAnotherDialog();
                        return true;
                    case R.id.developer_team:
                        buildAnotherDialog();
                        return true;


                }
                return false;
            }
        });

        torchCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(hasFlash)
                {
                    if (is_flash_on) {
                    turnFlashlightOff();
                    } else {
                        turnFlashlightOn();
                    }
                }

                else
                {
                    Toast.makeText(getApplicationContext(), "Flash not supported", Toast.LENGTH_SHORT).show();
                }


            }
        });

        mobileHelpCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this,MobileHelperActivity.class);
//                startActivity(intent);
            }
        });

        audioRecordCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this,AudioRecorderActivity.class);
//                startActivity(intent);
            }
        });

        dateconverterCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                buildAlertDialog();

//                Intent intent = new Intent(MainActivity.this,DateConverterActivity.class);
//                startActivity(intent);
            }
        });

        keyboardCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this,KeyboardActivity.class);
//                startActivity(intent);
            }
        });

        qrCodeScannerCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this,QRCodeActivity.class);
//                startActivity(intent);
            }
        });

    }



    private void buildYetAnotherDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.setMessage( getResources().getString(R.string.privacy_policy));
        alertDialog.setCancelable(true);
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.cancel();
            }
        });

        alertDialog.show();
    }

    private void buildAnotherDialog()
    {
        AlertDialog.Builder anotherDialogBuilder = new AlertDialog.Builder(this);
        View dateConverterDialog = getLayoutInflater().inflate(R.layout.current_team, null);
        anotherDialogBuilder.setView(dateConverterDialog);

        AlertDialog alertDialogAnother = anotherDialogBuilder.create();

        alertDialogAnother.show();
    }

    private void buildAlertDialog() {


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dateConverterDialog = inflater.inflate(R.layout.date_converter_dialog, null);
        dialogBuilder.setView(dateConverterDialog);

        AlertDialog alertDialog = dialogBuilder.create();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(alertDialog.getWindow().getAttributes());
        lp.windowAnimations = R.style.DialogAnimation;
        alertDialog.getWindow().setAttributes(lp);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        initializeDateConverterObject(dateConverterDialog);


        alertDialog.show();



    }

    private void initializeDateConverterObject(View alertDialog) {

        Set<Integer> nepYearSet = com.utilnepal.Utils.EachMonthNumberOfDates.getNepaliMap().keySet();
        Integer[] nepYearArray =  nepYearSet.toArray(new Integer[nepYearSet.size()]);
        List<Integer> nepYearList = new ArrayList<>();

        String [] nepyearArrayString = new String[nepYearArray.length];

        for (int i =0; i<nepYearArray.length; i++)
        {
            nepYearList.add(nepYearArray[i]);
        }

        Collections.sort(nepYearList);
        for (int i =0; i<nepYearList.size(); i++)
        {
            nepyearArrayString[i] = nepYearList.get(i).toString();
            nepyearArrayString[i] = nepyearArrayString[i].replace("1","१");
            nepyearArrayString[i] = nepyearArrayString[i].replace("2","२");
            nepyearArrayString[i] = nepyearArrayString[i].replace("3","३");
            nepyearArrayString[i] = nepyearArrayString[i].replace("4","४");
            nepyearArrayString[i] = nepyearArrayString[i].replace("5","५");
            nepyearArrayString[i] = nepyearArrayString[i].replace("6","६");
            nepyearArrayString[i] = nepyearArrayString[i].replace("7","७");
            nepyearArrayString[i] = nepyearArrayString[i].replace("8","८");
            nepyearArrayString[i] = nepyearArrayString[i].replace("9","९");
            nepyearArrayString[i] = nepyearArrayString[i].replace("0","०");
        }

        convertToEnglish = alertDialog.findViewById(R.id.buttonConvertToEnglish);
        convertToNepali = alertDialog.findViewById(R.id.buttonConvertToNepali);

        dateConvertedTextView = alertDialog.findViewById(R.id.dateConvertedTextView);


        //progresbar
        progressbarForConverting = alertDialog.findViewById(R.id.progressbarForConverting);
        progressbarForConverting.setVisibility(View.GONE);

        //spinner

        yearNepSpinner = alertDialog.findViewById(R.id.yearNepSpinner);
        monthNepSpinner = alertDialog.findViewById(R.id.monthNepSpinner);
        dayNepSpinner = alertDialog.findViewById(R.id.dayNepSpinner);



        yearEngSpinner = alertDialog.findViewById(R.id.yearEngSpinner);
        monthEngSpinner = alertDialog.findViewById(R.id.monthEngSpinner);
        dayEngSpinner = alertDialog.findViewById(R.id.dayEngSpinner);

        yearAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.each_spinner_item, nepyearArrayString);
        monthAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.each_spinner_item, getMonthArray());
        dayAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.each_spinner_item, getDayArray(30));


        yearEngToNepAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.each_spinner_item, getYearsForEnglish());
        monthEngToNepAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.each_spinner_item, getMonthForEnglish());

        Calendar mycal = new GregorianCalendar(startingEngYearForSelection,startingEngMonthForSelection, 1);
        int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);


        dayEngToNepAdapter = new ArrayAdapter<String>(this,R.layout.each_spinner_item, getDaysForEnglish(daysInMonth));

        yearAdapter.setDropDownViewResource(R.layout.each_spinner_item);
        monthAdapter.setDropDownViewResource(R.layout.each_spinner_item);
        dayAdapter.setDropDownViewResource(R.layout.each_spinner_item);


        yearEngToNepAdapter.setDropDownViewResource(R.layout.each_spinner_item);
        monthEngToNepAdapter.setDropDownViewResource(R.layout.each_spinner_item);
        dayEngToNepAdapter.setDropDownViewResource(R.layout.each_spinner_item);


        yearEngSpinner.setAdapter(yearEngToNepAdapter);
        monthEngSpinner.setAdapter(monthEngToNepAdapter);
        dayEngSpinner.setAdapter(dayEngToNepAdapter);

        yearNepSpinner.setAdapter(yearAdapter);
        monthNepSpinner.setAdapter(monthAdapter);
        dayNepSpinner.setAdapter(dayAdapter);


        yearNepSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(monthNepSpinner.getSelectedItem()!=null) {
                    monthPositionOnClick = Arrays.asList(nepMonths).indexOf(monthNepSpinner.getSelectedItem().toString()) + 1;
                }

                String yearSelectedOnClickString =  yearNepSpinner.getSelectedItem().toString();
                yearSelectedOnClick = Integer.parseInt(yearSelectedOnClickString);
                noOfDaysOnClick= com.utilnepal.Utils.EachMonthNumberOfDates.getNepaliMap().get(yearSelectedOnClick)[monthPositionOnClick];
                dayAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.each_spinner_item, getDayArray(noOfDaysOnClick));
                dayAdapter.setDropDownViewResource(R.layout.each_spinner_item);
                dayNepSpinner.setAdapter(dayAdapter);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        monthNepSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(monthNepSpinner.getSelectedItem()!=null) {
                    monthPositionOnClick = Arrays.asList(nepMonths).indexOf(monthNepSpinner.getSelectedItem().toString()) + 1;
                }

                noOfDaysOnClick= com.utilnepal.Utils.EachMonthNumberOfDates.getNepaliMap().get(yearSelectedOnClick)[monthPositionOnClick];
                dayAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.each_spinner_item, getDayArray(noOfDaysOnClick));
                dayAdapter.setDropDownViewResource(R.layout.each_spinner_item);
                dayNepSpinner.setAdapter(dayAdapter);




            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        dayNepSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        yearEngSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int yearSelectedOnClickStringForEnglishInteger = 1944;
                int monthSelectedOnClickStringEnglishInteger = 1;
                if(yearEngSpinner.getSelectedItem()!=null) {
                    String yearSelectedOnClickStringEnglish = yearEngSpinner.getSelectedItem().toString();
                    yearSelectedOnClickStringForEnglishInteger = Integer.parseInt(yearSelectedOnClickStringEnglish);
                }

                if(monthEngSpinner.getSelectedItem()!=null)
                {
                    Date date = null;//put your month name here
                    try {
                        date = new SimpleDateFormat("MMM").parse( shortMonthToLongMonthMatcher.get(monthEngSpinner.getSelectedItem().toString()));
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(date);
                        monthSelectedOnClickStringEnglishInteger=cal.get(Calendar.MONTH)+1;

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }

                Calendar mycal = new GregorianCalendar(yearSelectedOnClickStringForEnglishInteger, monthSelectedOnClickStringEnglishInteger, 1);
                int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH); // 28

                dayEngToNepAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.each_spinner_item, getDaysForEnglish(daysInMonth));
                dayAdapter.setDropDownViewResource(R.layout.each_spinner_item);
                dayEngSpinner.setAdapter(dayEngToNepAdapter);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        monthEngSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int yearSelectedOnClickStringForEnglish = 1944;
                int monthSelectedOnClickStringEnglish = 1;
                if(yearEngSpinner.getSelectedItem()!=null) {
                    String yearSelectedOnClickStringEnglish = yearEngSpinner.getSelectedItem().toString();
                    yearSelectedOnClickStringForEnglish = Integer.parseInt(yearSelectedOnClickStringEnglish);
                }

                if(monthEngSpinner.getSelectedItem()!=null)
                {
                    Date date = null;
                    try {
                        date = new SimpleDateFormat("MMM").parse( shortMonthToLongMonthMatcher.get(monthEngSpinner.getSelectedItem().toString()));
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(date);
                        monthSelectedOnClickStringEnglish=cal.get(Calendar.MONTH);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }

                Calendar cal = new GregorianCalendar(yearSelectedOnClickStringForEnglish, monthSelectedOnClickStringEnglish, 1);
                int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

                dayEngToNepAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.each_spinner_item, getDaysForEnglish(days));
                dayAdapter.setDropDownViewResource(R.layout.each_spinner_item);
                dayEngSpinner.setAdapter(dayEngToNepAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        dayEngSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        convertToEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(progressbarForConverting.getVisibility()== View.GONE)
                {
                    progressbarForConverting.setVisibility(View.VISIBLE);
                    convertToEnglish.setEnabled(false);
                    convertToNepali.setEnabled(false);
                }



                long daysCount = getTotalDaysCount();
                String date = convertToEnglish(daysCount);
                dateConvertedTextView.setText(date);

                if(progressbarForConverting.getVisibility()== View.VISIBLE)
                {
                    progressbarForConverting.setVisibility(View.GONE);
                    convertToEnglish.setEnabled(true);
                    convertToNepali.setEnabled(true);
                }

            }
        });

        convertToNepali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(progressbarForConverting.getVisibility()== View.GONE)
                {
                    progressbarForConverting.setVisibility(View.VISIBLE);
                    convertToEnglish.setEnabled(false);
                    convertToNepali.setEnabled(false);
                    dateConvertedTextView.setText(" CONVERTING ");
                }
                long daysCount = getTotalDaysCountEnglish();
                String date = convertToNepaliFunction(daysCount);
                dateConvertedTextView.setText(date);

                if(progressbarForConverting.getVisibility()== View.VISIBLE)
                {
                    progressbarForConverting.setVisibility(View.GONE);
                    convertToEnglish.setEnabled(true);
                    convertToNepali.setEnabled(true);
                }
            }

        });
    }

    private long getTotalDaysCountEnglish() {
        currentEngDate = new GregorianCalendar();
        int engYear = startingEngYearForSelection;
        int engMonth = startingEngMonthForSelection;
        int engDay = startingEngDayForSelection;

        if(yearEngSpinner.getSelectedItem()!=null && monthEngSpinner.getSelectedItem()!=null && dayEngSpinner.getSelectedItem()!=null )
        {
            engYear = Integer.parseInt(yearEngSpinner.getSelectedItem().toString());
            engDay = Integer.parseInt(dayEngSpinner.getSelectedItem().toString());
            String engMonthString = shortMonthToLongMonthMatcher.get(monthEngSpinner.getSelectedItem().toString());
            if(engMonthString!=null)
            {
                engMonth = getDateInEnglish(engMonthString)-1;
            }
            else
            {
                engMonth = 1;
            }
        }

        currentEngDate.set(engYear, engMonth, engDay);

        baseEngDate = new GregorianCalendar();

        baseEngDate.set(startingEngYearForSelection, startingEngMonthForSelection-1, startingEngDayForSelection);



        long diff =currentEngDate.getTimeInMillis()-baseEngDate.getTimeInMillis();

        diff = Math.round(diff * 1f / TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));


        return diff;

    }


    private int getDateInEnglish(String engMonthString) {
        switch (engMonthString)
        {
            case "January":
                return 1;
            case "February":
                return 2;
            case "March":
                return 3;
            case "April":
                return 4;
            case "May":
                return 5;
            case "June":
                return 6;
            case "July":
                return 7;
            case "August":
                return 8;
            case "September":
                return 9;
            case "Octobor":
                return 10;
            case "November":
                return 11;
            case "December":
                return 12;
            default:
                return 0;

        }
    }

    private String convertToNepaliFunction(@NotNull long totalEngDaysCount) {
        int nepYear = startingNepYearForSelection;
        int nepMonth = startingNepMonthForSelection;
        int nepDay = startingNepDayForSelection;



        while (totalEngDaysCount != 0) {

            int daysInIthMonth = com.utilnepal.Utils.EachMonthNumberOfDates.getNepaliMap().get(nepYear)[nepMonth];

            nepDay++;

            if (nepDay > daysInIthMonth) {
                nepMonth++;
                nepDay = 1;
            }
            if (nepMonth > 12) {
                nepYear++;
                nepMonth = 1;
            }

            totalEngDaysCount--;
        }

        String nepaliYear = convertEngYearToNepWithTextReplacement(String.valueOf(nepYear));
        String [] months = getMonthArray();
        String nepaliMonth = months[nepMonth-1];
        String nepaliDay = convertEngYearToNepWithTextReplacement(String.valueOf(nepDay));


        return nepaliYear +"  "+nepaliMonth+" "+ nepaliDay ;

    }


    private String [] getMonthForEnglish() {
        shortMonthToLongMonthMatcher = new HashMap<>();
        String[] months = new DateFormatSymbols().getMonths();

        String [] shortMonth = new String[months.length];
        int index = 0;
        for (String month : months)
        {
            shortMonth[index] = month.substring(0,3);
            shortMonthToLongMonthMatcher.put(month.substring(0,3),month);
            index++;
        }

        return shortMonth;
    }

    private String [] getYearsForEnglish() {
        int length = 0;
        String [] years = new String[89];
        for (int i = 1944; i<2033;i++)
        {
            years[length] = String.valueOf(i);
            length++;
        }

        return years;
    }

    private String [] getDaysForEnglish(int noOfdays)
    {
        int length = 0;
        String [] days = new String[90];
        for (int i = 1; i<32;i++)
        {
            days[length] = String.valueOf(i);
            length++;
        }

        return Arrays.copyOfRange(days,0,noOfdays);
    }

    private String convertToEnglish(long dayscount) {
        int engYear = startingEngYear;
        int engMonth = startingEngMonth;
        int engDay = startingEngDay;

        // for leap year
        int[] daysInMonth = new int[] { 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
        int[] daysInMonthOfLeapYear = new int[] { 0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
        int endDayOfMonth = 0;


        while (dayscount != 0) {
            if (isLeapYear(engYear)) {
                endDayOfMonth = daysInMonthOfLeapYear[engMonth];
            } else {
                endDayOfMonth = daysInMonth[engMonth];
            }
            engDay++;
            dayOfWeek++;
            if (engDay > endDayOfMonth) {
                engMonth++;
                engDay = 1;
                if (engMonth > 12) {
                    engYear++;
                    engMonth = 1;
                }
            }
            if (dayOfWeek > 7) {
                dayOfWeek = 1;
            }
            dayscount--;
        }

        String actualName = new DateFormatSymbols().getMonths()[engMonth-1];

        return String.valueOf(engYear) +"  "+actualName+"  "+ String.valueOf(engDay) ;
    }

    public static boolean isLeapYear(int year) {
        if (year % 100 == 0) {
            return year % 400 == 0;
        } else {
            return year % 4 == 0;
        }
    }



    private long getTotalDaysCount() {
        long totalNepDaysCount = 0;
        String newDate=null;
        int selectedDate =0;
        if(yearNepSpinner.getSelectedItem()!=null && monthNepSpinner.getSelectedItem()!=null && dayNepSpinner.getSelectedItem()!=null)
        {
            newDate= convertNepYearToEnglishWithTextReplacement(yearNepSpinner.getSelectedItem().toString());
            selectedDate = Integer.parseInt(newDate);

            for (int i = startingNepYear; i < selectedDate; i++) {
                for (int j = 1; j <= 12; j++) {
                    totalNepDaysCount += com.utilnepal.Utils.EachMonthNumberOfDates.getNepaliMap().get(i)[j];
                }
            }

            int selectedMonth=  Arrays.asList(nepMonths).indexOf(monthNepSpinner.getSelectedItem().toString())+1;

            for (int j = startingNepMonth; j < selectedMonth ; j++) {
                totalNepDaysCount += com.utilnepal.Utils.EachMonthNumberOfDates.getNepaliMap().get(selectedDate)[j];
            }

            int selectedDay=  Arrays.asList(nepDays).indexOf(dayNepSpinner.getSelectedItem().toString())+1;
            totalNepDaysCount += selectedDay - startingNepDay;

        }

        else
        {
            Toast.makeText(this, "Some Error for this date, please choose another!", Toast.LENGTH_SHORT).show();
        }

        return totalNepDaysCount;
    }

    private String convertNepYearToEnglishWithTextReplacement(String spinnerEngYear) {

        spinnerEngYear = spinnerEngYear.replace("०","0");
        spinnerEngYear = spinnerEngYear.replace("१","1");
        spinnerEngYear = spinnerEngYear.replace("२","2");
        spinnerEngYear = spinnerEngYear.replace("३","3");
        spinnerEngYear = spinnerEngYear.replace("४","4");
        spinnerEngYear = spinnerEngYear.replace("५","5");
        spinnerEngYear = spinnerEngYear.replace("६","6");
        spinnerEngYear = spinnerEngYear.replace("७","7");
        spinnerEngYear = spinnerEngYear.replace("८","8");
        spinnerEngYear = spinnerEngYear.replace("९","9");
        return spinnerEngYear;
    }


    private String convertEngYearToNepWithTextReplacement(String spinnerEngYear) {

        spinnerEngYear = spinnerEngYear.replace("0","०");
        spinnerEngYear = spinnerEngYear.replace("1","१");
        spinnerEngYear = spinnerEngYear.replace("2","२");
        spinnerEngYear = spinnerEngYear.replace("3","३");
        spinnerEngYear = spinnerEngYear.replace("4","४");
        spinnerEngYear = spinnerEngYear.replace("5","५");
        spinnerEngYear = spinnerEngYear.replace("6","६");
        spinnerEngYear = spinnerEngYear.replace("7","७");
        spinnerEngYear = spinnerEngYear.replace("8","८");
        spinnerEngYear = spinnerEngYear.replace("9","९");
        return spinnerEngYear;
    }

    private String[] getDayArray(int totalDays) {
        nepDays[0]=  "१";
        nepDays[1]=  "२";
        nepDays[2]=  "३";
        nepDays[3]=  "४";
        nepDays[4]=  "५";
        nepDays[5]=  "६";
        nepDays[6]=  "७";
        nepDays[7]=  "८";
        nepDays[8]=  "९";
        nepDays[9]=  "१०";
        nepDays[10]= "११";
        nepDays[11]= "१२";
        nepDays[12]= "१३";
        nepDays[13]= "१४";
        nepDays[14]= "१५";
        nepDays[15]= "१६";
        nepDays[16]= "१७";
        nepDays[17]= "१८";
        nepDays[18]= "१९";
        nepDays[19]= "२०";
        nepDays[20]= "२१";
        nepDays[21]= "२२";
        nepDays[22]= "२३";
        nepDays[23]= "२४";
        nepDays[24]= "२५";
        nepDays[25]= "२६";
        nepDays[26]= "२७";
        nepDays[27]= "२८";
        nepDays[28]= "२९";
        nepDays[29]= "३०";
        nepDays[30]= "३१";
        nepDays[31]= "३२";
        return Arrays.copyOfRange(nepDays,0,totalDays);
    }

    private String[] getMonthArray() {

        nepMonths[0]= "बैशाख";
        nepMonths[1]= "जेष्ठ";
        nepMonths[2]= "आषाढ";
        nepMonths[3]= "श्रावण";
        nepMonths[4]= "भाद्र";
        nepMonths[5]= "आश्विन";
        nepMonths[6]= "कार्तिक";
        nepMonths[7]= "मंसिर";
        nepMonths[8]= "पौष";
        nepMonths[9]= "माघ";
        nepMonths[10]= "फाल्गुन ";
        nepMonths[11]= "चैत्र";
        return nepMonths ;
    }


    private void getCamera() {
        if (mCamera == null) {
            try {
                mCamera = Camera.open();
                p = mCamera.getParameters();
            } catch (RuntimeException e) {
                Log.e("Camera Null", "Camera is null " + e.getMessage());
                Toast.makeText(getApplicationContext(), e.getMessage() , Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void turnFlashlightOff() {

        if (is_flash_on) {
            if (mCamera == null || p == null) {
                return;
            }



            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                if(oldCameraUsedForNewMobile)
                {
                    p = mCamera.getParameters();
                    p.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                    mCamera.setParameters(p);
                    mCamera.stopPreview();
                    is_flash_on = false;
                    torchImageView.setImageResource(R.drawable.ic_lightbulb_outline_black_24dp);
                    return;
                }


                try {
                    String cameraId=null;
                    camManager = (CameraManager) this.getSystemService(Context.CAMERA_SERVICE);
                    if (camManager != null) {
                        cameraId = camManager.getCameraIdList()[0];
                        camManager.setTorchMode(cameraId, false);
                    }
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.getMessage() , Toast.LENGTH_SHORT).show();

                }
            } else {
                p = mCamera.getParameters();
                p.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                mCamera.setParameters(p);
                mCamera.stopPreview();
                is_flash_on = false;
                torchImageView.setImageResource(R.drawable.ic_lightbulb_outline_black_24dp);
            }

        }

    }


    private void turnFlashlightOn() {

        if (!is_flash_on) {
            if (mCamera == null || p == null) {
                return;
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                try {
                    camManager = (CameraManager) this.getSystemService(Context.CAMERA_SERVICE);
                    String cameraId = null;
                    if (camManager != null) {

                        cameraId = camManager.getCameraIdList()[0];
                        camManager.setTorchMode(cameraId, true);
                    }
                } catch (CameraAccessException e) {
                    try {
                        p = mCamera.getParameters();
                        p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                        mCamera.setParameters(p);
                        mCamera.startPreview();
                        is_flash_on = true;
                        oldCameraUsedForNewMobile = true;
                        torchImageView.setImageResource(R.drawable.ic_lightbulb_outline_yellow_64dp);
                    }
                    catch (Exception exception)
                    {
                        Toast.makeText(getApplicationContext()," Sorry, something went wrong. Flash doesn't work", Toast.LENGTH_SHORT).show();
                        oldCameraUsedForNewMobile = false;
                    }
                }
            } else {
                p = mCamera.getParameters();
                p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                mCamera.setParameters(p);
                mCamera.startPreview();
                is_flash_on = true;
                torchImageView.setImageResource(R.drawable.ic_lightbulb_outline_yellow_64dp);
            }

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(t.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();

        // on pause turn off the flash
        turnFlashlightOff();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getCamera();
    }

    @Override
    protected void onStop() {
        super.onStop();
        turnFlashlightOff();

        // on stop release the camera
        if (mCamera != null) {
            mCamera.release();
            mCamera=null;
        }
    }
}



