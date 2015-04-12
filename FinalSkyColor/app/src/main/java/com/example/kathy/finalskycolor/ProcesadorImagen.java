package com.example.kathy.finalskycolor;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.TextView;

import java.io.File;

public class ProcesadorImagen extends Activity implements LocationListener {
    private static final int IMG_WIDTH = 100;
    private static final int IMG_HEIGHT = 50;
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Context context;
    TextView r1;
    TextView r2;
    String lat;
    String provider;
    protected String latitude, longitude;
    protected boolean gps_enabled, network_enabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);
        //txtLat = (TextView) findViewById(R.id.textview1);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

/*

        Log.d("dir",dir.toString()+"/pru.jpg");
        String fname=new File(getFilesDir(), "pru.png").getAbsolutePath();
*/


        BitmapFactory.Options options;


        File sdCard = Environment.getExternalStorageDirectory();
        File dir = new File(sdCard.getAbsolutePath());
        options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        Bundle bundle = getIntent().getExtras();
        String nom = bundle.getString("nombreFoto");
        Log.d("nom",nom);
        String variable = dir.getAbsolutePath() + "/camtest/" + nom;
        Bitmap originalImage = BitmapFactory.decodeFile(variable, options);
        Bitmap bmp = Bitmap.createBitmap(originalImage);
            /*OutputStream stream = new FileOutputStream(variable);
            bmp.compress(Bitmap.CompressFormat.JPEG,50,stream);*/
//        Bitmap resize = Bitmap.createBitmap(bmp, 0, 0, 800, 600);


        r1 = (TextView) findViewById(R.id.r1);
        r2 = (TextView) findViewById(R.id.r2);


        r1.setText(marchThroughImage(bmp));
        marchThroughImage(bmp);
        r2.setText("Aerosoles");

        //int type = originalImage.describeContents() == 0? Bitmap.CONTENTS_FILE_DESCRIPTOR : originalImage.CONTENTS_FILE_DESCRIPTOR;

        //Bitmap resizeImageJpg = resizeImage(originalImage);

        /*ImageIO.write(resizeImageJpg, "jpg", new File("C:\\Users\\SamYoo\\IdeaProjects\\reziseImage\\src\\mkyong_jpg.jpg"));
        String sdcardPath = Environment.getExternalStorageDirectory().toString();
*/



       /* Log.d("path",imgFile.getAbsolutePath());
        Log.d("bmp",bmp.toString());*/


    }

    @Override
    public void onLocationChanged(Location location) {
        //txtLat = (TextView) findViewById(R.id.textview1);
        //txtLat.setText("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude", "disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude", "enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude", "status");
    }

    public int[] printPixelARGB(int pixel) {
        int alpha = (pixel >> 24) & 0xff;
        int red = (pixel >> 16) & 0xff;
        int green = (pixel >> 8) & 0xff;
        int blue = (pixel) & 0xff;
        int[] argb = {alpha, red, green, blue};
        return argb;
    }


/*    private Bitmap resizeImage(Bitmap originalImage){

        originalImage.setWidth(IMG_WIDTH);
        originalImage.setHeight(IMG_HEIGHT);

        return originalImage;
    }*/
    private String marchThroughImage(Bitmap image) {
        int w = image.getWidth();
        int h = image.getHeight();
        int deepBlue = 0;
        int blue = 0;
        int lightBlue = 0;
        int paleBlue = 0;
        int milky = 0;
        int unknown = 0;
        System.out.println("width, height: " + w + ", " + h);

        double[] hsvDeepBlue = {230 / 360, 0.5, 0.1};
        double[] hsvBlue = {229 / 360, 0.35, 0.1};
        double[] hsvLightBlue = {225 / 360, 0.25, 0.1};
        double[] hsvPaleBlue = {222 / 360, 0.14, 0.1};
        double[] hsvMilky = {196 / 360, 0.4, 0.1};

        for (int i = 0; i < h; i = i + 1) {
            for (int j = 0; j < w; j = j + 1) {

                int pixel = image.getPixel(j, i);
                int[] argb = printPixelARGB(pixel);
                float[] hsv = new float[4];
                Color.RGBToHSV(argb[1], argb[2], argb[3], hsv);//.RGBtoHSB(argb[1], argb[2], argb[3], null);

                float[] hsvDeepBlue1 = new float[4];
                Color.RGBToHSV(128, 149, 255, hsvDeepBlue1);
                float[] hsvBlue1 = new float[4];
                Color.RGBToHSV(165, 181, 255, hsvBlue1);
                float[] hsvLightBlue1 = new float[4];
                Color.RGBToHSV(192, 209, 255, hsvLightBlue1);
                float[] hsvPaleBlue1 = new float[4];
                Color.RGBToHSV(219, 209, 255, hsvPaleBlue1);
                float[] hsvMilky1 = new float[4];
                Color.RGBToHSV(244, 252, 255, hsvMilky1);
                // float[] hsvBlue1 = Color.RGBtoHSB(165,181,255,null);
                //float[] hsvLightBlue1 = Color.RGBtoHSB(192,209,255,null);
                //float[] hsvPaleBlue1 = Color.RGBtoHSB(219,231,255,null);
                //float[] hsvMilky1 = Color.RGBtoHSB(244,252,255,null);
                //float[] white = Color.RGBtoHSB(255,255,255,null);
                //      System.out.println("hsvDeepBlue1: "+hsvDeepBlue1[0]+","+hsvDeepBlue1[1]+","+hsvDeepBlue1[2]);
                //     System.out.println("hsvBlue1: "+hsvBlue1[0]+","+hsvBlue1[1]+","+hsvBlue1[2]);
                //    System.out.println("hsvLightBlue1: "+hsvLightBlue1[0]+","+hsvLightBlue1[1]+","+hsvLightBlue1[2]);
                //   System.out.println("hsvPaleBlue1: "+hsvPaleBlue1[0]+","+hsvPaleBlue1[1]+","+hsvPaleBlue1[2]);
                //  System.out.println("hsvMilky1: "+hsvMilky1[0]+","+hsvMilky1[1]+","+hsvMilky1[2]);
                //System.out.println(hsv[0]);

                if ((hsvDeepBlue1[0] <= hsv[0]) && (hsvDeepBlue1[1] <= hsv[1])) {
                    deepBlue++;

                } else if ((hsvBlue1[0] <= hsv[0]) && (hsvBlue1[1] <= hsv[1])) {
                    blue++;

                } else if ((hsvLightBlue1[0] <= hsv[0]) && (hsvLightBlue1[1] <= hsv[1])) {
                    lightBlue++;

                } else if ((hsvPaleBlue1[0] <= hsv[0]) && (hsvPaleBlue1[1] <= hsv[1])) {
                    paleBlue++;

                } else if ((hsvMilky1[0] <= hsv[0]) && (hsvMilky1[1] <= hsv[1])) {
                    milky++;

                } else {
                    unknown++;
                }

            }
        }

        //System.out.println("deepblue "+ deepBlue);
        //System.out.println("blue"+ blue);
        //System.out.println("lightblue "+ lightBlue);
        //System.out.println("paleblue "+paleBlue);
        //System.out.println("milky: " + milky);

        Log.d("deepblue", Integer.toString(deepBlue));
        Log.d("blue", Integer.toString(blue));
        Log.d("lightblue", Integer.toString(lightBlue));
        Log.d("paleblue", Integer.toString(paleBlue));
        Log.d("milky", Integer.toString(milky));

        //System.out.println("w "+w);
        //System.out.println("h "+h);
        double multi = h * w;
        //System.out.println("multi " + multi);
        double porcentajeDeepBlue = (deepBlue * 100 / multi);
        //System.out.println("porcentaje deep blue: "+porcentajeDeepBlue);
        double porcentajeBlue = (blue * 100 / multi);
        //System.out.println("porcentaje blue: "+porcentajeBlue);
        double porcentajeLightBlue = (lightBlue * 100 / multi);
        //System.out.println("porcentaje light blue: "+porcentajeLightBlue);
        double porcentajePaleBlue = (paleBlue * 100 / multi);
        //System.out.println("porcentaje pale blue: "+porcentajePaleBlue);
        double porcentajeMilky = (milky * 100 / multi);
        //System.out.println("porcentaje milky: "+porcentajeMilky);
        double porcentajeUnknown = (unknown * 100 / multi);
        //System.out.println("unknown: " + porcentajeUnknown);

        if (porcentajeDeepBlue >= 25){
            return "1%-20%";
        }
        else if (porcentajeBlue >= 25){
            return "20%-40%";
        }
        else if (porcentajeBlue >= 25){
            return "40%-60%";
        }
        else if (porcentajeBlue >= 25){
            return "60%-80%";
        }
        else {
            return "80%-100%";
        }
    }
}
