package com.example.kathy.finalskycolor;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.app.Activity;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import android.view.ViewGroup.LayoutParams;



public class MainActivity extends ActionBarActivity {

    private static final String TAG = "CamTestActivity";
    Camera camera;
    Preview preview;
    Activity act;
    Context ctx;
    String fileName;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        act = this;
        ctx = this;
        preview = new Preview(this, (SurfaceView) findViewById(R.id.surfaceView));
        preview.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        ((FrameLayout) findViewById(R.id.camera_preview)).addView(preview);
        preview.setKeepScreenOn(true);

        final Camera.ShutterCallback shutterCallback = new Camera.ShutterCallback() {
            public void onShutter() {
                Log.d(TAG, "onShutter'd");
            }
        };

        final PictureCallback rawCallback = new PictureCallback() {
            public void onPictureTaken(byte[] data, Camera camera) {
                Log.d(TAG, "onPictureTaken - raw");
            }
        };
        final Button boton = (Button) findViewById(R.id.button_capture);
        final PictureCallback jpegCallback = new PictureCallback() {
            public void onPictureTaken(byte[] data, Camera camera) {
                new SaveImageTask().execute(data);
                //resetCam();

                boton.setEnabled(false);
                Log.d(TAG, "onPictureTaken - jpeg");
            }
        };

        final Button boton1 = (Button) findViewById(R.id.botonX);
        boton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                finish();

            }
        });

        final Button boton2 = (Button) findViewById(R.id.botonY);
        boton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //resetCam();
                //boton.setEnabled(true);
                boton2.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, ProcesadorImagen.class);
                        intent.putExtra("nombreFoto",fileName);
                        startActivity(intent);

                    }
                });

            }
        });

        //======== codigo nuevo ========


        //accion para el boton
        boton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                camera.takePicture(shutterCallback, rawCallback, jpegCallback);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        int numCams = Camera.getNumberOfCameras();
        if (numCams > 0) {
            try {
                camera = Camera.open(0);
                camera.startPreview();
                preview.setCamera(camera);
            } catch (RuntimeException ex) {
                Toast.makeText(ctx, getString(R.string.camera_no), Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onPause() {
        if (camera != null) {
            camera.stopPreview();
            preview.setCamera(null);
            camera.release();
            camera = null;
        }
        super.onPause();
    }

    private class SaveImageTask extends AsyncTask<byte[], Void, Void> {

        @Override
        protected Void doInBackground(byte[]... data) {
            FileOutputStream outStream;

            // Write to SD Card
            try {
                File sdCard = Environment.getExternalStorageDirectory();
                File dir = new File(sdCard.getAbsolutePath() + "/camtest");
                dir.mkdirs();

                fileName = String.format("%d.jpg", System.currentTimeMillis());
                File outFile = new File(dir, fileName);

                outStream = new FileOutputStream(outFile);
                outStream.write(data[0]);
                outStream.flush();
                outStream.close();

                Log.d(TAG, "onPictureTaken - wrote bytes: " + data.length + " to " + outFile.getAbsolutePath());

                refreshGallery(outFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

    }

    private void refreshGallery(File file) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(Uri.fromFile(file));
        sendBroadcast(mediaScanIntent);
    }

    private void resetCam() {
        camera.startPreview();
        preview.setCamera(camera);
    }

}


