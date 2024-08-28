package edu.fvtc.fileiodemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    public static final String XMLFILENAME = "data.xml";
    public static final String FILENAME = "data.txt";

    ArrayList<Actor> actors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createActors();
    }

    private void createActors() {
        actors = new ArrayList<Actor>();
        actors.add(new Actor(1, "Matthew", "Perry"));
        actors.add(new Actor(2, "Jennifer", "Aniston"));
        actors.add(new Actor(3, "Matt", "Damon"));
        actors.add(new Actor(4, "Courtney", "Cox"));
        actors.add(new Actor(5, "David", "Schwimer"));
        actors.add(new Actor(6, "David", "Perry"));
        actors.add(new Actor(7, "Lisa", "Kudrow"));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        int id = item.getItemId();

        if (id == R.id.action_readtext) {
            Log.d(TAG, "onOptionsItemSelected: Read Text");
            readFromTextFile();
        }else if (id == R.id.action_writetext) {
            Log.d(TAG, "onOptionsItemSelected: Write Text");
            writeToTextFile();
        }else if (id == R.id.action_readXML) {
            Log.d(TAG, "onOptionsItemSelected: Read XML");
            readFromXML();
        }else {
            Log.d(TAG, "onOptionsItemSelected: Write XML");
            writeToXML();
        }
        return super.onOptionsItemSelected(item);
    }

    private void writeToXML() {
        try {
            Log.d(TAG, "writeToXML: Begin");
            FileIO.WriteXMLFile(XMLFILENAME, this, actors);
            Log.d(TAG, "writeToXML: End");
        }catch (Exception ex) {
            Log.d(TAG, "writeToXML: "+ ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void readFromXML() {
        try {
            actors = FileIO.ReadFromXMLFile(XMLFILENAME, this);

            Log.i(TAG, "*********** Actors XML *********************");

            for (Actor actor : actors) {
                Log.d(TAG, "readFromXML: "+actor.toString());
            }
            Log.i(TAG, "****************************************");

        }catch (Exception ex) {
            Log.d(TAG, "readFromXML: "+ ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void writeToTextFile() {
        try {

            Integer counter = 0;
            String [] data = new String[actors.size()];

            for (Actor actor : actors) {
                data[counter++] = actor.toString();
            }
            Log.d(TAG, "writeToTextFile: Begin");
            FileIO.writeFile(FILENAME, this, data);
            Log.d(TAG, "writeToTextFile: End");

        }catch (Exception ex) {
            Log.d(TAG, "writeToTextFile: "+ ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void readFromTextFile() {
        try {

            ArrayList<String> strData = FileIO.readFile(FILENAME, this);

            actors = new ArrayList<Actor>();

            for (String s : strData) {
                String[] data = s.split("\\|");
                actors.add(new Actor(Integer.parseInt(data[0]),data[1], data[2]));
            }
            Log.i(TAG, "*********** Actors TextFile *********************");

            for (Actor actor : actors) {
                Log.d(TAG, "readFromTextFile: "+actor.toString());
            }
            Log.i(TAG, "****************************************");

        }catch (Exception ex) {
            Log.d(TAG, "readFromTextFile: "+ ex.getMessage());
            ex.printStackTrace();
        }
    }
}