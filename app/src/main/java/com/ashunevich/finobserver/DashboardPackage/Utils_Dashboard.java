package com.ashunevich.finobserver.DashboardPackage;


import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ashunevich.finobserver.UtilsPackage.PostPOJO;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.ashunevich.finobserver.UtilsPackage.Utils.getTextFrom;


abstract class Utils_Dashboard {

    public static String PREFERENCE_NAME ="APP_PREF";
    protected static String BALANCE="BALANCE";
    protected static String INCOME="INCOME";
    protected static String EXPENDITURES="EXPENDITURES";
    //protected static String TOTAL_BALANCE = "BALANCE";


   protected static int getImageInt(String type){
       if(type.matches("Income")){
           return 0;
       }
       else if (type.matches("Expenditures")){
           return 1;
       }
       else{
           return 2;
       }
   }

    protected static String returnString(TextView textView){
        return textView.getText().toString();
    }

    protected static String getDate(){
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("d MMM, yyyy ", Locale.UK);
        return df.format(c);
    }

    protected static Double textToDouble(TextView view){
        return Double.parseDouble(view.getText().toString());
    }

    protected static String returnStringFromObj(PostPOJO postPOJO) {
        return postPOJO.getZero();
    }


    protected static void enableSubmitIfReady(Button button,EditText text1, EditText text2) {
        button.setEnabled(getTextFrom(text1).length() > 0 && getTextFrom(text2).length() > 0);
    }

}