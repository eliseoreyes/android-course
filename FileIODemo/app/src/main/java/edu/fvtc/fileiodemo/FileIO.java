package edu.fvtc.fileiodemo;

import android.content.Context;
import android.util.Log;
import android.util.Xml;

import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.content.ContextCompat;
//import androidx.navigation.fragment.DialogFragmentNavigatorDestinationBuilder;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.Buffer;
import java.util.ArrayList;

public class FileIO {
    public static final String TAG = "FileIO";

    public static ArrayList<Actor> ReadFromXMLFile(String filename,
                                            AppCompatActivity activity)
    {
        ArrayList<Actor>  actors = new ArrayList<Actor>();
        Log.d(TAG, "ReadFromXMLFile: Start");
        try{

            InputStream is = activity.openFileInput(filename);
            XmlPullParser xmlPullParser = Xml.newPullParser();
            InputStreamReader isr = new InputStreamReader(is);
            xmlPullParser.setInput(isr);

            while(xmlPullParser.getEventType() != XmlPullParser.END_DOCUMENT)
            {
                if(xmlPullParser.getEventType() == XmlPullParser.START_TAG)
                {
                    if(xmlPullParser.getName().equals("actor"))
                    {
                        int id = Integer.parseInt(xmlPullParser.getAttributeValue(null, "id"));
                        String firstName = xmlPullParser.getAttributeValue(null, "firstname");
                        String lastName = xmlPullParser.getAttributeValue(null, "lastname");
                        Actor actor = new Actor(id, firstName, lastName);
                        actors.add(actor);
                        Log.d(TAG, "ReadFromXMLFile: " + actor.toString());
                    }
                }
                xmlPullParser.next();
            }
        }
        catch(Exception e)
        {
            Log.d(TAG, "ReadFromXMLFile: Error: " + e.getMessage());
        }
        Log.d(TAG, "ReadFromXMLFile: End");
        return actors;
    }

    public static void WriteXMLFile(String filename,
                             AppCompatActivity activity,
                             ArrayList<Actor> actors)
    {
        Log.d(TAG, "WriteXMLFile: Start: " + filename);
        XmlSerializer serializer = Xml.newSerializer();
        File file = new File(filename);
        Log.d(TAG, "WriteXMLFile: Done");
        OutputStreamWriter writer = null;
        try{

            //file.createNewFile();
            Log.d(TAG, "WriteXMLFile: 3");
            writer = new OutputStreamWriter(activity.getApplicationContext().openFileOutput(filename, Context.MODE_PRIVATE));
            Log.d(TAG, "WriteXMLFile: 4");
            serializer.setOutput(writer);
            serializer.startDocument("UTF-8", true);
            serializer.startTag("", "actors");
            serializer.attribute("", "number", String.valueOf(actors.size()));

            for(Actor actor: actors)
            {
                serializer.startTag("", "actor");
                serializer.attribute("", "id", String.valueOf(actor.getId()));
                serializer.attribute("", "firstname", String.valueOf(actor.getFirstName()));
                serializer.attribute("", "lastname", String.valueOf(actor.getLastName()));
                serializer.endTag("", "actor");
                Log.d(TAG, "WriteXMLFile: " + actor.toString());
            }
            serializer.endTag("", "actors");
            serializer.endDocument();
            serializer.flush();
            writer.close();
            Log.d(TAG, "WriteXMLFile: " + actors.size() + " actors written.");

            Log.d(TAG, "WriteXMLFile: End");
        }
        catch(Exception e)
        {
            Log.d(TAG, "WriteXMLFile: " + e.getMessage());
        }

        Log.d(TAG, "WriteXMLFile: End");
    }

    public static void writeFile(String filename,
                          AppCompatActivity activity,
                          String[] items)
    {
        try {
            OutputStreamWriter writer = new OutputStreamWriter(activity.openFileOutput(filename, Context.MODE_PRIVATE));
            String line = "";

            for(Integer counter = 0; counter < items.length; counter++)
            {
                line = items[counter];
                if(counter < items.length-1)
                    line += "\r\n";
                writer.write(line);
                Log.d(TAG, "writeFile: " + line);
            }
            writer.close();
        }
        catch(FileNotFoundException e)
        {
            Log.d(TAG, "writeFile: FileNotFoundException:" + e.getMessage());
        }
        catch(IOException e)
        {
            Log.d(TAG, "writeFile: IOException:" + e.getMessage());
        }
        catch(Exception e) {
            Log.d(TAG, "writeFile: " + e.getMessage());
        }
    }

    public static ArrayList<String> readFile(String filename, AppCompatActivity activity)
    {
        ArrayList<String> items = new ArrayList<String>();

        try{
            InputStream is = activity.openFileInput(filename);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader bufferedReader = new BufferedReader(isr);

            String line;
            while ((line = bufferedReader.readLine()) != null)
            {
                items.add(line);
            }
            is.close();
        }
        catch(Exception e)
        {
            Log.d(TAG, "readFile: " + e.getMessage());
        }

        return items;
    }
}