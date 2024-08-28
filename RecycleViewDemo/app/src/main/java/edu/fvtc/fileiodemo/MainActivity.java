package edu.fvtc.fileiodemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
       // ReadFromXml();

        if (actors.size() == 0){
            createActors();
            WriteToXML();
            ReadFromXml();
        }


        ArrayList<String> names = new ArrayList<String>();

        for(Actor actor : actors)
            names.add(actor.getFirstName() + " " + actor.getLastName());

        RecyclerView rvActors = findViewById(R.id.rvActors);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvActors.setLayoutManager(layoutManager);
        ActorAdapter actorAdapter = new ActorAdapter(actors, this);
        rvActors.setAdapter(actorAdapter);;
        Log.d(TAG, "onCreate: end ");

    }

    private void createActors() {
        actors = new ArrayList<Actor>();
        actors.add(new Actor(1, "Matthew", "Perry", 53));
        actors.add(new Actor(2, "Jennifer", "Aniston", 54));
        actors.add(new Actor(3, "Matt", "Damon", 54));
        actors.add(new Actor(4, "Courtney", "Cox", 55));
        actors.add(new Actor(5, "David", "Schwimer", 53));
        actors.add(new Actor(6, "David", "Perry", 57));
        actors.add(new Actor(7, "Lisa", "Kudrow", 57));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        int id = item.getItemId();

        if(id == R.id.action_readtext)
        {
            Log.d(TAG, "onOptionsItemSelected: ReadText");
            ReadfromTextFile();
        }
        else if(id == R.id.action_writetext)
        {
            Log.d(TAG, "onOptionsItemSelected: WriteText");
            WriteToTextFile();
        }
        else if(id == R.id.action_readXML)
        {
            Log.d(TAG, "onOptionsItemSelected: read xml");
            ReadFromXml();
        }
        else {
            Log.d(TAG, "onOptionsItemSelected: write xml");
            WriteToXML();
        }

        return super.onOptionsItemSelected(item);
    }

    private void WriteToXML() {
        try{
            FileIO fileIO = new FileIO();
            fileIO.WriteXMLFile(XMLFILENAME, this, actors);
            Log.d(TAG, "WriteToXML: finished");
        }
        catch(Exception e) {
            Log.d(TAG, "WriteToXML: " + e.getMessage());
        }
    }

    private void ReadFromXml() {
        try{
            FileIO fileIO = new FileIO();
            actors = fileIO.ReadFromXMLFile(XMLFILENAME, this);
            Log.d(TAG, "ReadFromXml: " + actors.size());
        }
        catch(Exception e) {
            Log.d(TAG, "ReadFromXml: " + e.getMessage());
        }
    }

    private void WriteToTextFile() {
        try{
            FileIO fileIO = new FileIO();
            Integer counter  = 0;
            String[] data = new String[actors.size()];
            for(Actor actor : actors)
            {
                data[counter++] = actor.toString();
            }
            fileIO.writeFile(FILENAME, this, data);

        }
        catch(Exception e) {
            Log.d(TAG, "WriteToTextFile: " + e.getMessage());
        }
    }

    private void ReadfromTextFile() {
        try{
            FileIO fileIO = new FileIO();
            ArrayList<String> strData = fileIO.readFile(FILENAME, this);
            actors = new ArrayList<Actor>();

            for(String s : strData)
            {
                String[] data = s.split("\\|");
                actors.add(new Actor(Integer.parseInt(data[0]),
                        data[1],
                        data[2],
                        Integer.parseInt(data[3])));

                Log.d(TAG, "ReadfromTextFile: " + actors.get(actors.size()-1).getFirstName());
            }
            Log.d(TAG, "ReadfromTextFile: " + actors.size());
        }
        catch(Exception e) {
            Log.d(TAG, "ReadfromTextFile: " + e.getMessage());
        }
    }
}