/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.com.firdaus.program;

import java.util.StringTokenizer;

/**
 *
 * @author slackware
 */
public class Test {

    public static void main(String[] args) {
//        double a=(double)1/(double)20*(double)60;
//        System.out.println(a);
//        double x=Math.round(a*100)/(double)100;
//        System.out.println(x);
//        double detik=(x-Math.floor(x))*(double)60;
//        System.out.println(detik);
//        System.out.println((double)20/(double)60*(double)4.5);
//        System.out.println((double)0.353%(double)20);
//        System.out.println(((double)0.353%(double)20));
//        //150 kec 20 150/20=7 
//        System.out.println((double)1/(double)20*(double)60);
//        System.out.println((double)20/(double)60*(double)3);
//        System.out.println(((double)150%(double)20)/(double)150*(double)60);
//        int x[] = {23, 00};
//        int n[] = new Test().addTime(x, 120);
//        System.out.println(n[0] + ":" + n[1]);
//        int a[] = {21, 30};
//        int b[] = {20, 31};
//        boolean o = new Test().checkMeetTimeWindow(a, b);
//        System.out.println(o);
//        for (int i = 0; i <= 14; i++) {
//            for (int j = i+1; j <= 14; j++) {
//                System.out.print(i + " : " + j+"   ");
//            }
//            System.out.println("");
//        }
//        Integer k=new Integer(12);
//        int x=12;
//        System.out.println(k==x);
        StringTokenizer st = new StringTokenizer("13:20", ":");
        String x[] = new String[2];
        x[0] = st.nextElement().toString().trim();
        x[1] = st.nextElement().toString().trim();
        System.out.println(x[0] + " : " + x[1]);
    }
    // 1/20*60
    //1 jam 60 menit
    //1 menit 60  detik

    private boolean checkMeetTimeWindow(int[] tw, int[] tnew) {
        //tw 00-03 tNew 21-23, 
        //batasan masalah : jarak antara agen satu dg yg lain tidak lebih dari 3 jam, 
        //bila jam antar antara jam 3 pagi - 21 malam
        if (tw[0] >= 0 && tw[0] <= 3 && tnew[0] >= 21 && tnew[0] <= 23) {
            return true;
        } else {
            if (tw[0] - tnew[0] > 0) {
                return true;
            } else if (tw[0] - tnew[0] == 0) {
                if (tw[1] - tnew[1] >= 0) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
    }

    private int[] addTime(int[] time, int menit) {
        int menitBaru = time[1] + menit;
        if (menitBaru >= 60) {
            int jam = menitBaru / 60;
            int menitSisa = menitBaru % 60;
            time[0] = time[0] + jam;
            time[1] = menitSisa;
        } else {
            time[1] = menitBaru;
        }
        if (time[0] == 24) {
            time[0] = 00;
        } else if (time[0] > 24) {
            time[0] = time[0] - 24;
        }
        return time;
    }
}
