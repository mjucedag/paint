package com.dam.uceda.paint;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton black, yellow, red, green, blue;
    private static Lienzo lienzo;
    float smallpoint, mediumpoint, bigpoint, defaultpoint;
    ImageButton draw, add, delete, save, background, square, circle, line;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        black = (ImageButton) findViewById(R.id.black);
        yellow = (ImageButton) findViewById(R.id.yellow);
        red = (ImageButton) findViewById(R.id.red);
        green = (ImageButton) findViewById(R.id.green);
        blue = (ImageButton) findViewById(R.id.blue);
        draw = (ImageButton) findViewById(R.id.draw);
        add = (ImageButton) findViewById(R.id.add);
        delete = (ImageButton) findViewById(R.id.delete);
        save = (ImageButton) findViewById(R.id.save);
        background = (ImageButton) findViewById(R.id.background);
        square = (ImageButton) findViewById(R.id.square);
        circle = (ImageButton) findViewById(R.id.circle);
        line = (ImageButton) findViewById(R.id.line);

        black.setOnClickListener(this);
        yellow.setOnClickListener(this);
        red.setOnClickListener(this);
        green.setOnClickListener(this);
        blue.setOnClickListener(this);
        draw.setOnClickListener(this);
        add.setOnClickListener(this);
        delete.setOnClickListener(this);
        save.setOnClickListener(this);
        background.setOnClickListener(this);
        square.setOnClickListener(this);
        circle.setOnClickListener(this);
        line.setOnClickListener(this);

        lienzo = (Lienzo) findViewById(R.id.lienzo);

        smallpoint = 10;
        mediumpoint = 20;
        bigpoint = 30;

        defaultpoint = mediumpoint;
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        String color = null;

        switch (v.getId()) {
            case R.id.black:
                color = v.getTag().toString();
                lienzo.setColor(color);
                break;
            case R.id.yellow:
                color = v.getTag().toString();
                lienzo.setColor(color);
                break;
            case R.id.blue:
                color = v.getTag().toString();
                lienzo.setColor(color);
                break;
            case R.id.green:
                color = v.getTag().toString();
                lienzo.setColor(color);
                break;
            case R.id.red:
                color = v.getTag().toString();
                lienzo.setColor(color);
                break;
            case R.id.draw:
                lienzo.pushedButton = "draw";
                final Dialog sizepoint = new Dialog(this);
                sizepoint.setTitle("Size point:");
                sizepoint.setContentView(R.layout.sizepoint);
                //listen for clicks on tamaños de los botones
                TextView smallBtn = (TextView) sizepoint.findViewById(R.id.smallsize);
                smallBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Lienzo.setDelete(false);
                        Lienzo.setsizepoint(smallpoint);

                        sizepoint.dismiss();
                    }
                });
                TextView mediumBtn = (TextView) sizepoint.findViewById(R.id.mediumsize);
                mediumBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Lienzo.setDelete(false);
                        Lienzo.setsizepoint(mediumpoint);

                        sizepoint.dismiss();
                    }
                });
                TextView largeBtn = (TextView) sizepoint.findViewById(R.id.bigsize);
                largeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Lienzo.setDelete(false);
                        Lienzo.setsizepoint(bigpoint);

                        sizepoint.dismiss();
                    }
                });
                //show and wait for user interaction
                sizepoint.show();

                break;

            case R.id.add:
                lienzo.pushedButton = "";
                AlertDialog.Builder newDialog = new AlertDialog.Builder(this);
                newDialog.setTitle("New Draw");
                newDialog.setMessage("Begining new draw (you will lose the previous draw)?");
                newDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        lienzo.NewDraw();
                        dialog.dismiss();
                    }
                });
                newDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                newDialog.show();


                break;
            case R.id.delete:
                lienzo.pushedButton = "";
                final Dialog deletepoint = new Dialog(this);
                deletepoint.setTitle("Delete size:");
                deletepoint.setContentView(R.layout.sizepoint);
                //listen for clicks on tamaños de los botones
                TextView smallBtndelete = (TextView) deletepoint.findViewById(R.id.smallsize);
                smallBtndelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Lienzo.setDelete(true);
                        Lienzo.setsizepoint(smallpoint);

                        deletepoint.dismiss();
                    }
                });
                TextView mediumBtndelete = (TextView) deletepoint.findViewById(R.id.mediumsize);
                mediumBtndelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Lienzo.setDelete(true);
                        Lienzo.setsizepoint(mediumpoint);

                        deletepoint.dismiss();
                    }
                });
                TextView largeBtndelete = (TextView) deletepoint.findViewById(R.id.bigsize);
                largeBtndelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Lienzo.setDelete(true);
                        Lienzo.setsizepoint(bigpoint);

                        deletepoint.dismiss();
                    }
                });
                //show and wait for user interaction
                deletepoint.show();
                break;

            case R.id.save:
                lienzo.pushedButton = "";
                AlertDialog.Builder saveDraw = new AlertDialog.Builder(this);
                saveDraw.setTitle("Save draw");
                saveDraw.setMessage("Save draw to the gallery?");
                saveDraw.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        //Salvar dibujo
                        lienzo.setDrawingCacheEnabled(true);
                        //attempt to save in SD CARD
                        /*String imgSaved = MediaStore.Images.Media.insertImage(
                                getContentResolver(), lienzo.getDrawingCache(),
                                UUID.randomUUID().toString()+".png", "drawing");*/

                        FileOutputStream out = null;
                        String path = Environment.getExternalStorageDirectory().toString();
                        File file = new File(path, UUID.randomUUID().toString()+".png"); // the File to save , append increasing numeric counter to prevent files from getting overwritten.
                        try {
                            out = new FileOutputStream(file);
                            lienzo.getDrawingCache().compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
                            // PNG is a lossless format, the compression factor (100) is ignored
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast unsavedToast = Toast.makeText(getApplicationContext(),
                                    "Error! The image couldn't be saved.", Toast.LENGTH_SHORT);
                            unsavedToast.show();
                            return;
                        } finally {
                            try {
                                if (out != null) {
                                    out.close();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        //Mensaje de correcto
                        Toast savedToast = Toast.makeText(getApplicationContext(),
                                " Saved draw at the gallery!", Toast.LENGTH_SHORT);
                        savedToast.show();


                        lienzo.destroyDrawingCache();
                    }
                });
                saveDraw.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                saveDraw.show();

                break;
            case R.id.background:
                lienzo.pushedButton = "";
                lienzo.setCanvasPaint();
                break;
            case R.id.square:
                lienzo.pushedButton = "square";
                break;
            case R.id.circle:
                lienzo.pushedButton = "circle";
                break;
            case R.id.line:
                lienzo.pushedButton = "line";
                break;
            default:
                break;
        }
    }

}
