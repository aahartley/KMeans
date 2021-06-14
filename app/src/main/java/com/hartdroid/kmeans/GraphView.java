package com.hartdroid.kmeans;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.method.Touch;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class GraphView extends View {
    Paint paint = new Paint();
    Paint paint2 = new Paint();
    boolean erase = false;
    boolean drawSlope = false;
    List<Player> dataset = new ArrayList<>();
    double smallestX = 0;
    double largestX = 0;
    double smallestY = 0;
    double largestY = 0;
    double x = 0;
    double y = 0;
    double touchX = 0;
    double touchY = 0;
    boolean touch = false;
    List<Touch> touches = new ArrayList<>();
    List<Player> cluster1 = new ArrayList<>();
    List<Player> cluster2 = new ArrayList<>();
    List<Player> cluster3 = new ArrayList<>();


    public GraphView(Context context) {
        super(context);
    }

    public GraphView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GraphView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public GraphView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (erase) {
            canvas.drawColor(Color.WHITE);
            dataset.clear();
            cluster1.clear();
            cluster2.clear();
            cluster3.clear();
            touches.clear();
            drawSlope = false;
            smallestX = 0;
            smallestY = 0;
            erase(false);
        } else {
            //canvas.drawColor(Color.WHITE);
            paint.setColor(Color.BLUE);
            paint.setStrokeWidth(5.0f);
            paint2.setColor(Color.RED);
            paint2.setStrokeWidth(4.0f);
           /* for (Player s : dataset) {
                System.out.println("drawing dots" + s.toString());
                System.out.println((getWidth() / 2.0f + s.getAge() ));
                canvas.drawCircle((float) (getWidth() / 2.0f + s.getTimePlayed()*.33 ), (float) (getHeight() / 2.0f - s.getHeight()*.33 ), 5.0f, paint2);
            }*/
            for(Player p : cluster1){
                System.out.println("drawing red dots" + p.toString());
                System.out.println((getWidth() / 2.0f + p.getAge() ));
                canvas.drawCircle((float) (getWidth() / 2.0f + p.getTimePlayed()*2), (float) (getHeight() / 2.0f - p.getHeight() ), 5.0f, paint2);
            }
            for(Player p: cluster2){
                paint2.setColor(Color.BLUE);
                System.out.println("drawing blue dots" + p.toString());
                System.out.println((getWidth() / 2.0f + p.getAge() ));
                canvas.drawCircle((float) (getWidth() / 2.0f + p.getTimePlayed()*2 ), (float) (getHeight() / 2.0f - p.getHeight()), 5.0f, paint2);
            }
            if(!cluster3.isEmpty()) {
                for (Player p : cluster3) {
                    paint2.setColor(Color.GREEN);
                    System.out.println("drawing green dots" + p.toString());
                    System.out.println((getWidth() / 2.0f + p.getAge()));
                    canvas.drawCircle((float) (getWidth() / 2.0f + p.getTimePlayed()*2 ), (float) (getHeight() / 2.0f - p.getHeight() ), 5.0f, paint2);
                }
            }


            canvas.drawLine(0, getHeight() / 2.0f, getWidth(), getHeight() / 2.0f, paint);
            canvas.drawLine(getWidth() / 2.0f, 0, getWidth() / 2.0f, getHeight(), paint);
            // System.out.println(getWidth() + " " + getHeight());
        }
    }

    protected void setList(List<Player> dataset) {
        this.dataset = dataset;
        invalidate();

    }
    protected void setCluster1List(List<Player> cluster1) {
        this.cluster1 = cluster1;
        invalidate();

    }  protected void setCluster2List(List<Player> cluster2) {
        this.cluster2 = cluster2;
        invalidate();

    }  protected void setCluster3List(List<Player> cluster3) {
        this.cluster3 = cluster3;
        invalidate();

    }



    protected void erase(boolean erase) {
        this.erase = erase;
        invalidate();
    }

}
