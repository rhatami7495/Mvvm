package com.example.weather.custome_view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Catzie on 24/09/2016.
 */
public class WindDirection extends View {

    private String TAG = "CompassNeedle";
    private Paint mPaint;
    private Paint mPaintInnerCircle;
    private Paint mPaintPointer;
    private int myWidth;
    private int myHeight;
    private int radius;
    private Bitmap bitmapCanvasInnerCircle;
    private Canvas canvasInnerCircle;


    public WindDirection(Context context) { // view created in code
        super(context);

        initialize();
    }

    public WindDirection(Context context, AttributeSet attrs) { // view created through resource
        super(context, attrs);
        initialize();

    }
    public WindDirection(Context context, AttributeSet attrs, int defaultStyle) { // view created through inflation
        super(context, attrs, defaultStyle);
        initialize();

    }
    private void initialize(){
        // Initialize
        mPaint = new Paint();
//        canvasInnerCircle = new Canvas (bitmapCanvasInnerCircle);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh){
        super.onSizeChanged(w, h, oldw, oldh);
    }

    /**
     * The base View class draws a borderless empty 100px x 100 px box
     * To change that, we override the onMeasure handler
     * which allows us to indicate the view size
     * -
     * onMeasure is called as your view's parent is laying out its children
     * -
     * wMeasureSpec & hMeasureSpec: how much space is available and
     * whether the view will be given that much space, or at most that much space
     */
    @Override
    protected void onMeasure(int wMeasureSpec, int hMeasureSpec){

        /**
         * Set height
         */

        int hSpecMode = MeasureSpec.getMode(hMeasureSpec);
        int hSpecSize = MeasureSpec.getSize(hMeasureSpec);
        myHeight = hSpecSize;

        if(hSpecMode == MeasureSpec.EXACTLY){

            myHeight = hSpecSize;

        }

        else if (hSpecMode == MeasureSpec.AT_MOST){
            // wrap content
        }

        /**
         * Set width
         */

        int wSpecMode = MeasureSpec.getMode(wMeasureSpec);
        int wSpecSize = MeasureSpec.getSize(wMeasureSpec);
        myWidth = wSpecSize;
        if(wSpecMode == MeasureSpec.EXACTLY) {
            myWidth = wSpecSize;
        }
        else if (wSpecMode == MeasureSpec.AT_MOST){
            // wrap content
        }

        /**
         * Call after determining size of "control"
         * Without calling this, app will crash as soon as view is laid out
         */

        setMeasuredDimension(myWidth, myHeight); //w and h of circle


    }


    @Override
    protected void onDraw(Canvas canvas){


        super.onDraw(canvas);

        // Border Circle

        radius = getWidth();

        int radHalf = radius / 2;

        // Inner Circle
        mPaint.setStyle(Paint.Style.FILL);

        mPaint.setColor(Color.GRAY);

        canvas.drawCircle(radHalf, radHalf, radHalf, mPaint);

        // Middle Circle
        mPaint.setStyle(Paint.Style.FILL);

        mPaint.setColor(Color.LTGRAY);
        canvas.drawCircle(radHalf, radHalf, (float) radius/15, mPaint);

        // Needle

        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(6);

        Point a = new Point(radHalf, 0);
        Point c = new Point(radHalf, radHalf);

        Path path = new Path();
        path.setFillType(Path.FillType.EVEN_ODD);
        path.moveTo(a.x, a.y);
        path.lineTo(c.x, c.y);
        path.close();

        canvas.drawPath(path, mPaint);


    }

}
