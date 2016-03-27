package com.thenewboston.mediaplayerapp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
//import com.thenewboston.hanumanchalisa.A;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;

public class MainActivity extends ActionBarActivity  {


    MediaPlayer objPlayer;
    String line;
    InputStream in;
    BufferedReader reader;
    TextView Text1;
    A[] arr=new A[38];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        objPlayer=MediaPlayer.create(this,R.raw.manma);
        //display of lyrics
        Text1 = (TextView) findViewById(R.id.Text1);

        //for enabling hindi fonts
        // Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Ananda Lipi Cn Bt.ttf");
        //Text1.setTypeface(tf);
        //in = this.getAssets().open(String.valueOf(R.raw.hanumanchalisa));
        in = getResources().openRawResource(R.raw.manmasubs);


        reader = new BufferedReader(new InputStreamReader(in));
        //
        for(int i=0;i<38;i++) {
            try {
                line = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.i("MyActivity","I want to print the text" +line);
            arr[i].setIndex(Integer.parseInt(line));
            Log.i("MyActivity","I want to print the index" +arr[i].getIndex());
            //System.out.println(arr[i].getIndex());
            // Log.d(arr[i].getIndex());
            try {
                line = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            arr[i].setStarttime(line);
            Log.i("MyActivity","I want to print the starttime" +arr[i].getStarttime());
            try {
                line = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            arr[i].setEndtime(line);
            Log.i("MyActivity","I want to print the endtime" +arr[i].getEndtime());
            while(true){
                try {
                    if((line=reader.readLine()).equals(""))
                    {
                        break;
                    }
                    else{
                        arr[i].setLine(line+"\n");
                        //arr[i].setLine("\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Log.i("MyActivity","I want to print the line" +arr[i].getLine());
        }


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);


        return true;
    }


    static int flag=0;
    public void Play(View view) throws IOException {
        Button playbutton = (Button) findViewById(R.id.playbutton);
        //int x=objPlayer.getCurrentPosition();
        if (objPlayer.isPlaying()) {//normal pause
            objPlayer.pause();
            playbutton.setText("Play");
        } else {
            if (flag == 1) {//play again from begining after stopping
                objPlayer.prepare();
                //objPlayer.seekTo(0);
                objPlayer.start();
                playbutton.setText("Pause");
                //display lyrics from begining
                int i = 0;
                while (i != 38) {
                    int x = objPlayer.getCurrentPosition();
                    if ((Integer.parseInt(arr[i].getStarttime())) == x || (Integer.parseInt(arr[i].getEndtime())) == x || ((Integer.parseInt(arr[i].getStarttime())) < x && (Integer.parseInt(arr[i].getEndtime())) > x)) {
                        Text1.setText(arr[i].getLine());
                    } else {
                        i++;
                    }

                }
            }


            // objPlayer.seekTo(x);
            else { //normal play
                int j = 0;
                objPlayer.start();
                playbutton.setText("Pause");
                //displays lyrics when played
                //get position from where to play again
                int y = objPlayer.getCurrentPosition();
                for (int i = 0; i < 38; i++) {
                    if ((Integer.parseInt(arr[i].getStarttime())) == y || (Integer.parseInt(arr[i].getEndtime())) == y || ((Integer.parseInt(arr[i].getStarttime())) < y && (Integer.parseInt(arr[i].getEndtime())) > y))  {
                       /*if (arr[i].getLine()!= null){
                           Text1.setText(arr[i].getLine());
                       }*/
                      /* else{
                           reader.close();
                       }*/

                        j = i;
                        break;
                    }
                }
                while (j != 38) {
                    int x = objPlayer.getCurrentPosition();
                    if ((Integer.parseInt(arr[j].getStarttime())) == x || (Integer.parseInt(arr[j].getEndtime())) == x || ((Integer.parseInt(arr[j].getStarttime())) < x && (Integer.parseInt(arr[j].getEndtime())) > x))  {
                        Text1.setText(arr[j].getLine());
                    } else {
                        j++;
                    }

                }


                //Log.d("str", line);

            }
        }

    }
    public void Stop(View view) throws IOException {
        Button playbutton=(Button)findViewById(R.id.playbutton);
        //Button stopbutton=(Button)findViewById(R.id.stopbutton);
        objPlayer.stop();
        playbutton.setText("Play");
        flag=1;
        //  objPlayer.prepare();

    }



    public void forwardSong(View view) {
        //getcurrentposition of song and position where every 2 lines end. difference=seekforwardtime
        //get current line number . Display from next even line number.
        int i=1;
        int x=objPlayer.getCurrentPosition();
        while(24000*i<x)
        {
            i++;
        }
        int pos=24000*i;
        int seekForwardTime=pos-x;
        if (objPlayer != null) {
            int currentPosition = objPlayer.getCurrentPosition();
            if (currentPosition + seekForwardTime <= objPlayer.getDuration()) {
                objPlayer.seekTo(currentPosition + seekForwardTime);
            } else {
                objPlayer.seekTo(objPlayer.getDuration());
            }
        }
    }


    public void rewindSong(View view) {
        int i=1;
        int x=objPlayer.getCurrentPosition();
        while(24000*i>x)
        {
            i--;
        }
        int pos=24000*i;
        int seekBackwardTime=x-pos;
        if (objPlayer != null) {
            int currentPosition = objPlayer.getCurrentPosition();
            if (currentPosition - seekBackwardTime >= 0) {
                objPlayer.seekTo(currentPosition - seekBackwardTime);
            } else {
                objPlayer.seekTo(0);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
