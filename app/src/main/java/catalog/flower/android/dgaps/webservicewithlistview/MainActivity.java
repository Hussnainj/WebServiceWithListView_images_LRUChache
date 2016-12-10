package catalog.flower.android.dgaps.webservicewithlistview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView output;ProgressBar pb;
    List<MyTask> myTaskList;
    List<Flower> flowerList;
    public ListView listView;
    private static final String PHOTOS_BASE_URL= "http://services.hanselandpetal.com/photos/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



    //    output = (TextView) findViewById(R.id.textView1);
        myTaskList = new ArrayList<>();

       // output.setMovementMethod( new ScrollingMovementMethod());

        pb = (ProgressBar) findViewById(R.id.progressBar1);
        pb.setVisibility(View.INVISIBLE);


//        for (int i=0; i <=100;i++)
//        {
//            updateDisplay("Line: "+i+"\n");
//
//        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);





        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.doTask) {

//            updateDisplay("Task Done ..... ");

            //myTask.execute("Param 1","Param 2","Param 3");
            if (isOnline()) {
                //requestData("http://services.hanselandpetal.com/feeds/flowers.xml");
                // requestData("http://services.hanselandpetal.com/feeds/flowers.json");

                requestData("http://services.hanselandpetal.com/secure/flowers.json");
            }
            else
            {
                Toast.makeText(this, "Not Connected to Internet", Toast.LENGTH_SHORT).show();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    private void requestData(String uri) {
        MyTask myTask = new MyTask();

        // myTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"Param 1","Param 2","Param 3");
        myTask.execute(uri);
    }

    public  void updateDisplay()
    {
//
//        if (flowerList!=null)
//        {
//
//            for (Flower flower: flowerList) {
//
//                output.append(flower.getName()+ "\n");
//            }
//        }
        listView = (ListView) findViewById(R.id.listView);

        FlowerAdapter flowerAdapter = new FlowerAdapter(this, R.layout.item_flower,flowerList);
        listView.setAdapter(flowerAdapter);



    }


    //  FOr NetworkConnectivity and Internet
    protected  Boolean isOnline()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo !=null && networkInfo.isConnectedOrConnecting())
        {
            return  true;
        }
        else
        {
            return  false;
        }


    }

    /// ************** New Class Async  Start **********************


    private  class  MyTask extends AsyncTask<String,String,List<Flower>>
    {


        @Override
        protected void onPreExecute() {

            //   updateDisplay("Starting Task from onPre Execute Function\n");
            pb.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Flower> doInBackground(String... strings) {

//
//              for (int i =0; i<strings.length;i++)
//             {
//
//
//                publishProgress("Working With "+ strings[i]);
//
//
//                 try {
//                     Thread.sleep(1200);
//                 }
//                 catch (Exception e)
//                 {
//
//                 }
//             }

            String content= HttpManager.getData(strings[0],"feeduser","feedpassword");
            flowerList = FlowerJSONParser.parseFeed(content);

            for (Flower flower :flowerList
                 ) {

                try
                {
                     String imageURL= PHOTOS_BASE_URL+ flower.getPhoto();
                    InputStream in = (InputStream) new URL(imageURL).getContent();
                    Bitmap bitmap = BitmapFactory.decodeStream(in);
                    flower.setBitmap(bitmap);
                    in.close();
                }
                catch (Exception e)
                {

                }
                
            }

            // updateDisplay("I am inside doIBackground");

            //return "Task Completed";

            return flowerList;

        }

        @Override
        protected void onPostExecute(List<Flower> s)

        {

            if (s==null)
            {
                Toast.makeText(MainActivity.this, "Can not Connect to Internet Now...", Toast.LENGTH_SHORT).show();
                return;
            }
            flowerList = (s);
            updateDisplay();
        }


        @Override
        protected void onProgressUpdate(String... values) {


            // updateDisplay(values[0]);
            pb.setVisibility(View.INVISIBLE);
        }
    }



    //******************** New Class Async  Finish  *******
}
