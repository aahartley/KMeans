package com.hartdroid.kmeans;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.Dataset;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SecondActivity extends AppCompatActivity {
    TextView title = null;
    Button bt = null;
    Button bt2 = null;
    Button bt3 = null;
    Button button = null;
    TextView tV = null;
    TextView tV2 = null;
    TextView tV3 = null;
    TextView tV4 = null;
    EditText et = null;
    EditText et2 = null;
    EditText et3 = null;
    List<String> lines = new ArrayList<>();
    List<Player> dataset = new ArrayList<>();
    static List<String> allLines= new ArrayList<>();

    int k = 3;
    GraphView view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        title = super.findViewById(R.id.title);
        bt = super.findViewById(R.id.bt);
        bt2 = super.findViewById(R.id.bt2);
        bt3= super.findViewById(R.id.bt3);
        button = super.findViewById(R.id.button);
        tV = super.findViewById(R.id.tV);
        tV4 = super.findViewById(R.id.tV4);
        et = super.findViewById(R.id.et);

        try {
            readFile();
             view = findViewById(R.id.graphView);

        } catch (IOException e) {
            e.printStackTrace();
        }
        button.setOnClickListener(v -> {
            startData();
        });
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("clicked");
                try {
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {

                }
                String input = et.getText().toString().toLowerCase().trim();
                if (input.isEmpty() || !input.equals("2") & !input.equals("3")) {
                    et.setError("must match above");


                }

                k = Integer.parseInt(input);
                if (k == 2) {
                    try {
                        readFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    view.erase(false);
                    Classify2();
                }
                else if (k == 3) {
                    try {
                        readFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    view.erase(false);
                    Classify3();
                }
                //else
                  //  et.setError("must match above");




            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNew();

            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.erase(true);

            }
        });
    }
    protected void startData(){

        Intent intent = new Intent(this, Data.class);
        startActivity(intent);
    }
    protected double euclideanDistance(Player x, Player y) {
        //System.out.println(x.toString()+" "+y.toString());
        return (Math.sqrt((Math.pow((x.getApm()) - (y.getApm()), 2)) + (Math.pow((x.getHeight()) - (y.getHeight()), 2)) +
                (Math.pow((x.getTimePlayed()) - (y.getTimePlayed()), 2)) + (Math.pow((x.getAge()) - (y.getAge()), 2)) +
                (Math.pow((x.getPpm()) - (y.getPpm()), 2))));
    }


    protected void readFile() throws IOException {
        InputStream is = getResources().openRawResource(R.raw.basketball);
        BufferedReader buf = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = buf.readLine()) != null) {
            if (line.contains("@")) {
                // System.out.println(line +"avoid");
                allLines.add(line);

            } else {
                //System.out.println(line);
                String[] tokens = line.split(",");
                Double[] attr = new Double[5];
                for (int i = 0; i < tokens.length; i++) {
                    attr[i] = Double.parseDouble(tokens[i]);
                }
                //1490 alive
                //711 dead

                dataset.add(new Player(attr[0], attr[1], attr[2], attr[3], attr[4]));
                //lines.add(line);
                allLines.add(line);

            }
        }

    }

    protected void startNew() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    protected void Classify2() {
        System.out.println("in classify2");
        List<Player> distances = new ArrayList<>();
        List<Player> cl1 = new ArrayList<>();
        List<Player> cl2 = new ArrayList<>();
        view.setList(dataset);

        Player clMean = null;
        double meanApm = 0;
        double meanH = 0;
        double meanTp = 0;
        double meanAge = 0;
        double meanPpm = 0;
        double mean2Apm = 0;
        double mean2H = 0;
        double mean2Tp = 0;
        double mean2Age = 0;
        double mean2Ppm = 0;
        //            distances.sort(Comparator.comparingDouble(People::getDistance));
        Player cl2Mean = null;
        Player cluster1 = new Player(0.3437, 203, 40.71, 37, 0.8281);//first,adult,female,sur
        Player cluster2 = new Player(0.0494, 160, 10.08, 22, 0.1593);//first,child,male,sur
        System.out.println(dataset.size());
        for (int i = 0; i < dataset.size(); i++) {
            double distance1 = euclideanDistance(dataset.get(i), cluster1);
            double distance2 = euclideanDistance(dataset.get(i), cluster2);
            if (distance1 < distance2) {
                //dataset.get(i).setLabel(1);
                dataset.get(i).setDistance(distance1);
                cl1.add(dataset.get(i));
                distances.add(dataset.get(i));
                meanApm += dataset.get(i).getApm();
                meanH += dataset.get(i).getHeight();
                meanTp += dataset.get(i).getTimePlayed();
                meanAge += dataset.get(i).getAge();
                meanPpm += dataset.get(i).getPpm();

            } else {
                //dataset.get(i).setLabel(2);
                dataset.get(i).setDistance(distance2);
                distances.add(dataset.get(i));
                cl2.add(dataset.get(i));
                mean2Apm += dataset.get(i).getApm();
                mean2H += dataset.get(i).getHeight();
                mean2Tp += dataset.get(i).getTimePlayed();
                mean2Age += dataset.get(i).getAge();
                mean2Ppm += dataset.get(i).getPpm();
            }
        }
        System.out.println(meanH + " " + mean2H);
        int counter = 0;
        while (counter < 10) {
            counter++;
            System.out.println("COUNTER " + counter);
            clMean = new Player(meanApm / cl1.size(), meanH / cl1.size(), meanTp / cl1.size(), meanAge / cl1.size(), meanPpm / cl1.size());
            cl2Mean = new Player(mean2Apm / cl2.size(), mean2H / cl2.size(), mean2Tp / cl2.size(), mean2Age / cl2.size(), mean2Ppm / cl2.size());
            distances.clear();
            meanApm = 0;
            meanH = 0;
            meanTp = 0;
            meanAge = 0;
            meanPpm = 0;
            mean2Apm = 0;
            mean2H = 0;
            mean2Tp = 0;
            mean2Age = 0;
            mean2Ppm = 0;
            cl1.clear();
            cl2.clear();
            for (Player p : dataset) {
                double distance1 = euclideanDistance(p, clMean);
                double distance2 = euclideanDistance(p, cl2Mean);
                if (distance1 < distance2) {
                   // p.setLabel(1);
                    p.setDistance(distance1);
                    cl1.add(p);
                    distances.add(p);
                    meanApm += p.getApm();
                    meanH += p.getHeight();
                    meanTp += p.getTimePlayed();
                    meanAge += p.getAge();
                    meanPpm += p.getPpm();


                } else {
                   // p.setLabel(2);
                    p.setDistance(distance2);
                    cl2.add(p);
                    distances.add(p);
                    mean2Apm += p.getApm();
                    mean2H += p.getHeight();
                    mean2Tp += p.getTimePlayed();
                    mean2Age += p.getAge();
                    mean2Ppm += p.getPpm();

                }
            }
            System.out.println(meanH + " " + mean2H);

         /*   for (Player p : distances) {
                if (p.getLabel() == 1) {
                    cl1.add(p);

                }
                if (p.getLabel() == 2) {

                    cl2.add(p);
                }
            }*/
            // System.out.println(dataset.size() + " " + distances.size());
            System.out.println("GROUP ONE");
            for (Player p : cl1) {
                //System.out.println(p.toString());
            }
            System.out.println(cl1.size());
            System.out.println("\nGROUP TWO");
            for (Player p : cl2) {
                // System.out.println(p.toString());
            }
            System.out.println(cl2.size());
        }
        view.setCluster1List(cl1);
        view.setCluster2List(cl2);

    }

    protected void Classify3() {
        List<Player> distances = new ArrayList<>();
        List<Player> cl1 = new ArrayList<>();
        List<Player> cl2 = new ArrayList<>();
        List<Player> cl3= new ArrayList<>();
        view.setList(dataset);


        Player clMean = null;
        double meanApm = 0;
        double meanH = 0;
        double meanTp = 0;
        double meanAge = 0;
        double meanPpm = 0;
        double mean2Apm = 0;
        double mean2H = 0;
        double mean2Tp = 0;
        double mean2Age = 0;
        double mean2Ppm = 0;
        double mean3Apm = 0;
        double mean3H = 0;
        double mean3Tp = 0;
        double mean3Age = 0;
        double mean3Ppm = 0;

        //            distances.sort(Comparator.comparingDouble(People::getDistance));
        Player cl2Mean = null;
        Player cl3Mean = null;
        Player cluster1 = new Player(0.3437, 203, 40.71, 37, 0.8281);
        Player cluster2 = new Player(0.0494, 160, 10.08, 22, 0.1593);
        Player cluster3 = new Player(0.1501,180,25.00,26,0.4123);
        for (int i = 0; i < dataset.size(); i++) {
            double distance1 = euclideanDistance(dataset.get(i), cluster1);
            double distance2 = euclideanDistance(dataset.get(i), cluster2);
            double distance3 = euclideanDistance(dataset.get(i), cluster3);


            if (distance1 < distance2 && distance1 < distance3) {

               // dataset.get(i).setLabel(1);
                dataset.get(i).setDistance(distance1);
                cl1.add(dataset.get(i));
                distances.add(dataset.get(i));
                meanApm += dataset.get(i).getApm();
                meanH += dataset.get(i).getHeight();
                meanTp += dataset.get(i).getTimePlayed();
                meanAge += dataset.get(i).getAge();
                meanPpm += dataset.get(i).getPpm();


            }
            else if(distance2 < distance1 && distance2<distance3) {

               // dataset.get(i).setLabel(2);
                dataset.get(i).setDistance(distance2);
                distances.add(dataset.get(i));
                cl2.add(dataset.get(i));
                mean2Apm += dataset.get(i).getApm();
                mean2H += dataset.get(i).getHeight();
                mean2Tp += dataset.get(i).getTimePlayed();
                mean2Age += dataset.get(i).getAge();
                mean2Ppm += dataset.get(i).getPpm();

            }
            else{

                //dataset.get(i).setLabel(3);
                dataset.get(i).setDistance(distance3);
                distances.add(dataset.get(i));
                cl3.add(dataset.get(i));
                mean3Apm += dataset.get(i).getApm();
                mean3H += dataset.get(i).getHeight();
                mean3Tp += dataset.get(i).getTimePlayed();
                mean3Age += dataset.get(i).getAge();
                mean3Ppm += dataset.get(i).getPpm();

            }
        }
        System.out.println(cl1.size()+" "+cl2.size()+" "+cl3.size());
        System.out.println(meanH + " " + mean2H+" "+mean3H);
        int counter = 0;
        while (counter < 10) {
            counter++;
            System.out.println("COUNTER " + counter);
            clMean = new Player(meanApm / cl1.size(), meanH / cl1.size(), meanTp / cl1.size(), meanAge / cl1.size(), meanPpm / cl1.size());
            cl2Mean = new Player(mean2Apm / cl2.size(), mean2H / cl2.size(), mean2Tp / cl2.size(), mean2Age / cl2.size(), mean2Ppm / cl2.size());
            cl3Mean = new Player(mean3Apm / cl3.size(), mean3H / cl3.size(), mean3Tp / cl3.size(), mean3Age / cl3.size(), mean3Ppm / cl3.size());

            distances.clear();
            meanApm = 0;
            meanH = 0;
            meanTp = 0;
            meanAge = 0;
            meanPpm = 0;
            mean2Apm = 0;
            mean2H = 0;
            mean2Tp = 0;
            mean2Age = 0;
            mean2Ppm = 0;
            mean3Apm = 0;
            mean3H = 0;
            mean3Tp = 0;
            mean3Age = 0;
            mean3Ppm = 0;
            cl1.clear();
            cl2.clear();
            cl3.clear();
            for (Player p : dataset) {
                double distance1 = euclideanDistance(p, clMean);
                double distance2 = euclideanDistance(p, cl2Mean);
                double distance3 = euclideanDistance(p, cl3Mean);
                if (distance1 < distance2 && distance1 < distance3) {
                   // p.setLabel(1);
                    p.setDistance(distance1);
                    cl1.add(p);
                    distances.add(p);
                    meanApm += p.getApm();
                    meanH += p.getHeight();
                    meanTp += p.getTimePlayed();
                    meanAge += p.getAge();
                    meanPpm += p.getPpm();

                }
                else if(distance2 < distance1 && distance2<distance3) {

                   // p.setLabel(2);
                    p.setDistance(distance2);
                    distances.add(p);
                    cl2.add(p);
                    mean2Apm +=p.getApm();
                    mean2H += p.getHeight();
                    mean2Tp += p.getTimePlayed();
                    mean2Age += p.getAge();
                    mean2Ppm += p.getPpm();

                }
                else{

                    //p.setLabel(3);
                    p.setDistance(distance3);
                    distances.add(p);
                    cl3.add(p);
                    mean3Apm +=p.getApm();
                    mean3H += p.getHeight();
                    mean3Tp += p.getTimePlayed();
                    mean3Age += p.getAge();
                    mean3Ppm += p.getPpm();

                }


            }
            System.out.println(meanH + " " + mean2H);
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
            for (Player p : cl1) {
                //System.out.println(p.toString());
            }
            System.out.println(cl1.size());
            System.out.println("\nGROUP TWO");
            for (Player p : cl2) {
                // System.out.println(p.toString());
            }
            System.out.println(cl2.size());
            System.out.println("\nGROUP THREE");
            for (Player p : cl3) {
                // System.out.println(p.toString());
            }
            System.out.println(cl3.size());
        }
        view.setCluster1List(cl1);
        view.setCluster2List(cl2);
        view.setCluster3List(cl3);
    }
}

