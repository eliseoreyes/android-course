package edu.fvtc.timediff;

import androidx.appcompat.app.AppCompatActivity;

import android.icu.text.LocaleDisplayNames;
import android.icu.text.NumberFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.fvtc.timediff.Exceptions.InputFormatException;
import edu.fvtc.timediff.Exceptions.TimeDiffException;


public class MainActivity extends AppCompatActivity {
    public static final String REGEX = "^(2[0-3]|[01]?[0-9]):([0-5]?[0-9]):([0-5]?[0-9])$";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final EditText edtStartTime = (EditText) findViewById(R.id.edtStartTime);
        final EditText edtEndTime = (EditText) findViewById(R.id.edtEndTime);

        Button btnCalcDiff = (Button) findViewById(R.id.btnCalcDiff);
        btnCalcDiff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final TextView textView = (TextView) findViewById(R.id.txtVEndTime);

                String startTime = "";
                String endTime = "";

                try {
                    if (validateFields(edtStartTime))
                        startTime = edtStartTime.getText().toString();

                    if (validateFields(edtEndTime))
                        endTime = edtEndTime.getText().toString();

                   String timeDiff =  calculateTimeDiff(startTime, endTime);

                    //Show values in TextView
                    textView.setText(timeDiff);

                }catch (InputFormatException ex) {
                    Log.e("Invalid", "Invalid input format..");
                    Toast.makeText(getApplicationContext(), ex.getMessage() , Toast.LENGTH_SHORT).show();
                    ex.printStackTrace();
                } catch (TimeDiffException ex) {
                    Log.e("Invalid", "Invalid Time Diff..");
                    Toast.makeText(getApplicationContext(), ex.getMessage() , Toast.LENGTH_SHORT).show();
                    ex.printStackTrace();
                }
            }
        });

    }

    private boolean validateFields(EditText editText) throws InputFormatException {

        // Validating format inputs...
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(editText.getText().toString());

            if (editText.getText().toString().equals(""))
                throw new InputFormatException(String.format("%s","The field can not be left blank. You must enter a time."));

            if (matcher.find())
                return true;
            else
                throw new InputFormatException(String.format("%s %s ","Invalid Time [##:##:##].->", editText.getText().toString()));
    }

    private String calculateTimeDiff(String startTime, String endTime) throws TimeDiffException {

        String[] sTime = startTime.split(":");
        String[] eTime = endTime.split(":");

        //Parsing Values
        int sHour = Integer.parseInt(sTime[0]) * 60 * 60;
        int sMin = Integer.parseInt(sTime[1]) * 60;
        int sSec = Integer.parseInt(sTime[2]);

        int eHour = Integer.parseInt(eTime[0]) * 60 * 60;
        int eMin = Integer.parseInt(eTime[1]) * 60;
        int eSec = Integer.parseInt(eTime[2]);

        int stotalTime = (sHour + sMin + sSec);
        int etotalTime = (eHour + eMin + eSec);

        if (stotalTime > etotalTime)
            throw new TimeDiffException("Start time can not be greater than end time.");

        int totalTime = etotalTime - stotalTime;

        return String.format("%s = %s:%s:%s", String.valueOf(NumberFormat.getNumberInstance(Locale.US).format(totalTime)),
                String.format("%02d", (totalTime / 60) / 60),
                String.format("%02d",((totalTime / 60) ) % 60),
                String.format("%02d",((totalTime % 60) % 60)) );
    }
}