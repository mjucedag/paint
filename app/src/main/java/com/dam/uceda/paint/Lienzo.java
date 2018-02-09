package com.dam.uceda.paint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class Lienzo extends View {

    float touchX;
    float touchY;

    private static final float TOUCH_TOLERANCE = 4;

    //Path que utilizaré para ir isDrawing las lineas
    private Path drawPath;
    //Paint de dibujar y Paint de Canvas
    private static Paint drawPaint;
    private Paint canvasPaint;
    //Color Inicial
    public static int paintColor = 0xFFFF0000;
    //canvas
    private Canvas drawCanvas;
    //canvas para save

    private Bitmap canvasBitmap;

    static float sizepoint;
    private static boolean delete = false;

    public String pushedButton = "";


    public Lienzo(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupDrawing();
    }


    private void setupDrawing() {
//Configuración del area sobre la que pintar

        drawPath = new Path();
        drawPaint = new Paint();
        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);

        //setsizepoint(20);

        drawPaint.setStrokeWidth(20);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        canvasPaint = new Paint(Paint.DITHER_FLAG);
    }

    //Tamaño asignado a la vista
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
    }


    //Pinta la vista. Será llamado desde el OnTouchEvent
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        if (isDrawing) {
            if (pushedButton.equals("square")) {
                onDrawRectangle(canvas);
            }else if (pushedButton.equals("circle")) {
                onDrawCircle(canvas);
            }else if (pushedButton.equals("line")) {
                onDrawLine(canvas);
            }else{
                canvas.drawPath(drawPath, drawPaint);
            }
        }
    }

    private float mStartX, mStartY;

    private boolean isDrawing = false;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        touchX = event.getX();
        touchY = event.getY();

        if (pushedButton.equals("square")) {
            onTouchEventRectangle(event);
            return true;
        }
        if (pushedButton.equals("circle")) {
            onTouchEventCircle(event);
            return true;
        }
        if (pushedButton.equals("line")) {
            onTouchEventLine(event);
            return true;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isDrawing = true;

                invalidate();
                drawPath.moveTo(touchX, touchY);

                break;
            case MotionEvent.ACTION_MOVE:

                drawPath.lineTo(touchX, touchY);

                break;
            case MotionEvent.ACTION_UP:

                drawPath.lineTo(touchX, touchY);
                drawCanvas.drawPath(drawPath, drawPaint);
                drawPath.reset();

                break;
            default:
                super.onTouchEvent(event);
                break;
        }
        //repintar
        invalidate();
        return true;

    }



    //Actualiza color
    public void setColor(String newColor) {
        invalidate();
        paintColor = Color.parseColor(newColor);
        drawPaint.setColor(paintColor);
    }

    //Poner tamaño del punto
    public static void setsizepoint(float nuevoTamanyo) {


        //float pixel = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
        //        nuevoTamaño, getResources().getDisplayMetrics());

        //sizepoint=pixel;
        drawPaint.setStrokeWidth(nuevoTamanyo);
    }


    //set borrado true or false
    public static void setDelete(boolean isdeleted) {
        delete = isdeleted;
        if (delete) {

            drawPaint.setColor(Color.WHITE);
            //drawPaint.setmEndXermode(new PorterDuffmEndXermode(PorterDuff.Mode.CLEAR));

        } else {
            drawPaint.setColor(paintColor);
            //drawPaint.setmEndXermode(null);
        }
    }

    public void NewDraw() {
        drawCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        invalidate();

    }

    public void setCanvasPaint() {
        NewDraw();
        drawCanvas.drawColor(paintColor, PorterDuff.Mode.ADD);
    }


    //------------------------------------------------------------------
    // Circle
    //------------------------------------------------------------------

    private void onDrawCircle(Canvas canvas) {
        canvas.drawCircle(mStartX, mStartY, calculateRadius(mStartX, mStartY, touchX, touchY), canvasPaint);
    }

    private void onTouchEventCircle(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isDrawing = true;
                mStartX = touchX;
                mStartY = touchY;
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                isDrawing = false;
                drawCanvas.drawCircle(mStartX, mStartY,
                        calculateRadius(mStartX, mStartY, touchX, touchY), drawPaint);
                invalidate();
                break;
        }
    }

    /**
     * @return
     */
    protected float calculateRadius(float x1, float y1, float x2, float y2) {

        return (float) Math.sqrt(
                Math.pow(x1 - x2, 2) +
                        Math.pow(y1 - y2, 2)
        );
    }

    //------------------------------------------------------------------
    // Line
    //------------------------------------------------------------------

    private void onDrawLine(Canvas canvas) {

        float dx = Math.abs(touchX - mStartX);
        float dy = Math.abs(touchY - mStartY);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            canvas.drawLine(mStartX, mStartY, touchX, touchY, canvasPaint);
        }
    }

    private void onTouchEventLine(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isDrawing = true;
                mStartX = touchX;
                mStartY = touchY;
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                isDrawing = false;
                drawCanvas.drawLine(mStartX, mStartY, touchX, touchY, drawPaint);
                invalidate();
                break;
        }
    }


    //------------------------------------------------------------------
    // Rectangle
    //------------------------------------------------------------------

    private void onTouchEventRectangle(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isDrawing = true;
                mStartX = touchX;
                mStartY = touchY;
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                isDrawing = false;
                drawRectangle(drawCanvas, drawPaint);
                invalidate();
                break;
        }
    }

    private void onDrawRectangle(Canvas canvas) {
        drawRectangle(canvas, canvasPaint);
    }

    private void drawRectangle(Canvas canvas, Paint paint) {
        float right = mStartX > touchX ? mStartX : touchX;
        float left = mStartX > touchX ? touchX : mStartX;
        float bottom = mStartY > touchY ? mStartY : touchY;
        float top = mStartY > touchY ? touchY : mStartY;
        canvas.drawRect(left, top, right, bottom, paint);
    }
}
