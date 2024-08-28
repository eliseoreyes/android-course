package edu.fvtc.grocerylistdb.utils;

import android.content.Context;
import android.util.Log;
import android.util.Xml;

import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import edu.fvtc.grocerylistdb.models.Product;
import edu.fvtc.grocerylistdb.models.WriteMode;

public class FileIO {
    public static final String TAG = "FileIO";
/*
    public static ArrayList<Product> ReadFromXMLFile(String filename,
                                                     AppCompatActivity activity)
    {
        ArrayList<Product>  products = new ArrayList<Product>();
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
                    if(xmlPullParser.getName().equals("products"))
                    {
                        String productDescription  = xmlPullParser.getAttributeValue(null, "productDescription");
                        int isOnShoppingList = Integer.parseInt(xmlPullParser.getAttributeValue(null, "isOnShoppingList"));
                        int isInCart = Integer.parseInt(xmlPullParser.getAttributeValue(null, "isInCart"));

                        Product product = new Product(productDescription, isOnShoppingList, isInCart);
                        products.add(product);
                        Log.d(TAG, "ReadFromXMLFile: " + product.toString());
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
        return products;
    }

    public static void WriteXMLFile(String filename,
                                    AppCompatActivity activity,
                                    ArrayList<Product> products)
    {
        Log.d(TAG, "WriteXMLFile: Start: " + filename);
        XmlSerializer serializer = Xml.newSerializer();
        File file = new File(filename);
        Log.d(TAG, "WriteXMLFile: 2");
        OutputStreamWriter writer = null;
        try{

            //file.createNewFile();
            Log.d(TAG, "WriteXMLFile: 3");
            writer = new OutputStreamWriter(activity.getApplicationContext().openFileOutput(filename, Context.MODE_PRIVATE));
            Log.d(TAG, "WriteXMLFile: 4");
            serializer.setOutput(writer);
            serializer.startDocument("UTF-8", true);
            serializer.startTag("", "products");
            serializer.attribute("", "number", String.valueOf(products.size()));

            for(Product product: products)
            {
                serializer.startTag("", "product");
                serializer.attribute("", "ProductDescription", String.valueOf(product.getProductDescription()));
                serializer.attribute("", "isOnShoppingList", String.valueOf(product.getIsOnShoppingList()));
                serializer.attribute("", "isInCart", String.valueOf(product.getIsInCart()));
                serializer.endTag("", "product");
                Log.d(TAG, "WriteXMLFile: " + product.toString());
            }
            serializer.endTag("", "products");
            serializer.endDocument();
            serializer.flush();
            writer.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

    }

    public static void writeFile(String filename,
                                 WriteMode writeMode,
                                 AppCompatActivity activity,
                                 String[] items) {

        try {

            OutputStreamWriter writer;

            if (WriteMode.MODE_APPEND.equals(writeMode)) {
                writer = new OutputStreamWriter(activity.openFileOutput(filename, Context.MODE_APPEND));
            }else {
                writer = new OutputStreamWriter(activity.openFileOutput(filename, Context.MODE_PRIVATE));
            }

            String line = "";

            for(Integer counter = 0; counter < items.length; counter++)
            {
                line = items[counter];

                File file = new File(activity.getApplicationContext().getFilesDir(),filename);

                if (WriteMode.MODE_APPEND.equals(writeMode)){
                    if(file.exists()){
                        if(counter < items.length-1)
                            line += "\r\n";
                        writer.append(line+"\n");
                    }
                    else{
                        if(counter < items.length-1)
                            line += "\r\n";
                        writer.append(line);
                    }
                }else {
                    if(counter < items.length) {
                        line += "\n";
                        writer.write(line);
                    }
                }

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
            e.printStackTrace();
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


 */
}