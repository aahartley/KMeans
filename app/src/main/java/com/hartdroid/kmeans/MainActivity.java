package com.hartdroid.kmeans;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView title = null;
    Button bt = null;
    Button bt2 = null;
    Button reset = null;
    TextView tV =null;
    @SuppressLint("StaticFieldLeak")
    static TextView tV4 =null;
    EditText et = null;
    List<Touch> dataset = new ArrayList<>();
    int k =3;
    FirstGraphView view = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = super.findViewById(R.id.title);
        bt = super.findViewById(R.id.bt);
        bt2 = super.findViewById(R.id.bt2);
        reset = super.findViewById(R.id.reset);
        tV = super.findViewById(R.id.tV);
        tV4 = super.findViewById(R.id.tV4);
        et = super.findViewById(R.id.et);




            view = super.findViewById(R.id.FirstGraphView);

        bt.setOnClickListener(v -> {
            System.out.println("clicked");
            try {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            } catch (Exception e) {
                    System.out.println(e.getMessage());
            }
            if(view.getTouches().isEmpty()){
                return;
            }
            String input = et.getText().toString().toLowerCase().trim();
            if (input.isEmpty() || !input.equals("1") & !input.equals("2") & !input.equals("3") & !input.equals("4") & !input.equals("5")) {
                et.setError("must match above");

            }

            k = Integer.parseInt(input);
            if (k == 2) {

                view.erase(false);
                Classify2();
            }
            else if (k == 3) {

                view.erase(false);
                Classify3();
            }


        });
        bt2.setOnClickListener(v -> startNew());
        reset.setOnClickListener(v -> {
            view.erase(true);
        });
    }

      protected double euclideanDistance(Touch one, Touch two){
          //System.out.println(x.toString()+" "+y.toString());
          return (Math.sqrt((Math.pow((one.getX())-(two.getX()),2))+(Math.pow((one.getY())-(two.getY()),2))));
      }


    protected void startNew(){
        Intent intent = new Intent(this,SecondActivity.class);
        startActivity(intent);
    }
    protected void Classify2() {
        dataset = view.getTouches();
        view.setDrawCluster();
        System.out.println("in classify2");
        List<Touch> distances = new ArrayList<>();
        List<Touch> cl1 = new ArrayList<>();
        List<Touch> cl2 = new ArrayList<>();

        Touch clMean;
        double meanX = 0;
        double meanY = 0;

        double mean2X = 0;
        double mean2Y = 0;

        //            distances.sort(Comparator.comparingDouble(People::getDistance));
        Touch cl2Mean;
        Touch cluster1 = view.getCluster1();
        Touch cluster2 = view.getCluster2();
        System.out.println(dataset.size());
        for (int i = 0; i < dataset.size(); i++) {
            double distance1 = euclideanDistance(dataset.get(i), cluster1);
            double distance2 = euclideanDistance(dataset.get(i), cluster2);
            if (distance1 < distance2) {
                //dataset.get(i).setLabel(1);
                dataset.get(i).setDistance(distance1);
                cl1.add(dataset.get(i));
                distances.add(dataset.get(i));
                meanX += dataset.get(i).getX();
                meanY += dataset.get(i).getY();


            } else {
                //dataset.get(i).setLabel(2);
                dataset.get(i).setDistance(distance2);
                distances.add(dataset.get(i));
                cl2.add(dataset.get(i));
                mean2X += dataset.get(i).getX();
                mean2Y += dataset.get(i).getY();

            }
        }
        System.out.println(meanX + " " + mean2X);
        int counter = 0;
        while (counter < 10) {
            counter++;
            System.out.println("COUNTER " + counter);
            clMean = new Touch(meanX/ cl1.size(), meanY / cl1.size());
            cl2Mean = new Touch(mean2X / cl2.size(), mean2Y / cl2.size());
            distances.clear();
            meanX = 0;
            meanY = 0;

            mean2X=0;
            mean2Y = 0;

            cl1.clear();
            cl2.clear();
            for (Touch t : dataset) {
                double distance1 = euclideanDistance(t, clMean);
                double distance2 = euclideanDistance(t, cl2Mean);
                if (distance1 < distance2) {
                    // p.setLabel(1);
                    t.setDistance(distance1);
                    cl1.add(t);
                    distances.add(t);
                    meanX += t.getX();
                    meanY += t.getY();


                } else {
                    // p.setLabel(2);
                    t.setDistance(distance2);
                    cl2.add(t);
                    distances.add(t);
                    mean2X += t.getX();
                    mean2Y += t.getY();


                }
            }
            System.out.println(meanX+ " " + mean2X);


            System.out.println("GROUP ONE");
            for (Touch t : cl1) {
                //System.out.println(p.toString());
            }
            System.out.println(cl1.size());
            System.out.println("\nGROUP TWO");
            for (Touch t : cl2) {
                // System.out.println(p.toString());
            }
            System.out.println(cl2.size());
        }

        view.setCluster1List(cl1);
        view.setCluster2List(cl2);

    }
    protected void Classify3(){
        dataset = view.getTouches();
        List<Touch> distances = new ArrayList<>();
        List<Touch> cl1 = new ArrayList<>();
        List<Touch> cl2 = new ArrayList<>();
        List<Touch> cl3= new ArrayList<>();
        view.setDrawCluster();


        Touch clMean = null;
        double meanX = 0;
        double meanY = 0;

        double mean2X = 0;
        double mean2Y = 0;

        double mean3X = 0;
        double mean3Y = 0;


        //            distances.sort(Comparator.comparingDouble(People::getDistance));
        Touch cl2Mean = null;
        Touch cl3Mean = null;
        Touch cluster1 = view.getCluster1();
        Touch cluster2 = view.getCluster2();
        Touch cluster3 = view.getCluster3();
        for (int i = 0; i < dataset.size(); i++) {
            double distance1 = euclideanDistance(dataset.get(i), cluster1);
            double distance2 = euclideanDistance(dataset.get(i), cluster2);
            double distance3 = euclideanDistance(dataset.get(i), cluster3);


            if (distance1 < distance2 && distance1 < distance3) {

                // dataset.get(i).setLabel(1);
                dataset.get(i).setDistance(distance1);
                cl1.add(dataset.get(i));
                distances.add(dataset.get(i));
                meanX += dataset.get(i).getX();
                meanY += dataset.get(i).getY();



            }
            else if(distance2 < distance1 && distance2<distance3) {

                // dataset.get(i).setLabel(2);
                dataset.get(i).setDistance(distance2);
                distances.add(dataset.get(i));
                cl2.add(dataset.get(i));
                mean2X += dataset.get(i).getX();
                mean2Y += dataset.get(i).getY();


            }
            else{

                //dataset.get(i).setLabel(3);
                dataset.get(i).setDistance(distance3);
                distances.add(dataset.get(i));
                cl3.add(dataset.get(i));
                mean3X += dataset.get(i).getX();
                mean3Y += dataset.get(i).getY();


            }
        }
        System.out.println(cl1.size()+" "+cl2.size()+" "+cl3.size());
        System.out.println(meanX + " " + mean2X+" "+mean3X);
        int counter = 0;
        while (counter < 10) {
            counter++;
            System.out.println("COUNTER " + counter);
            clMean = new Touch(meanX / cl1.size(), meanY/ cl1.size());
            cl2Mean = new Touch(mean2X / cl2.size(), mean2Y / cl2.size());
            cl3Mean = new Touch(mean3X / cl3.size(), mean3Y / cl3.size());

            distances.clear();
             meanX = 0;
             meanY = 0;

             mean2X = 0;
             mean2Y = 0;

             mean3X = 0;
             mean3Y = 0;
            cl1.clear();
            cl2.clear();
            cl3.clear();
            for (Touch t : dataset) {
                double distance1 = euclideanDistance(t, clMean);
                double distance2 = euclideanDistance(t, cl2Mean);
                double distance3 = euclideanDistance(t, cl3Mean);
                if (distance1 < distance2 && distance1 < distance3) {
                    // p.setLabel(1);
                    t.setDistance(distance1);
                    cl1.add(t);
                    distances.add(t);
                    meanX += t.getX();
                    meanY += t.getY();


                }
                else if(distance2 < distance1 && distance2<distance3) {

                    // p.setLabel(2);
                    t.setDistance(distance2);
                    distances.add(t);
                    cl2.add(t);
                    mean2X +=t.getX();
                    mean2Y += t.getY();

                }
                else{

                    //p.setLabel(3);
                    t.setDistance(distance3);
                    distances.add(t);
                    cl3.add(t);
                    mean3X +=t.getX();
                    mean3Y += t.getY();


                }


            }
            System.out.println(meanX + " " + mean2X);
/*
            for (Player p : distances) {
                if (p.getLabel() == 1) {
                    cl1.add(p);

                }
                if (p.getLabel() == 2) {

                    cl2.add(p);
                }
                if (p.getLabel() == 3) {

                    cl3.add(p);
                }
            }*/
            // System.out.println(dataset.size() + " " + distances.size());
            System.out.println("GROUP ONE");
            for (Touch t : cl1) {
                //System.out.println(p.toString());
            }
            System.out.println(cl1.size());
            System.out.println("\nGROUP TWO");
            for (Touch t : cl2) {
                // System.out.println(p.toString());
            }
            System.out.println(cl2.size());
            System.out.println("\nGROUP THREE");
            for (Touch t : cl3) {
                // System.out.println(p.toString());
            }
            System.out.println(cl3.size());
        }
        view.setCluster1List(cl1);
        view.setCluster2List(cl2);
        view.setCluster3List(cl3);
    }
}



