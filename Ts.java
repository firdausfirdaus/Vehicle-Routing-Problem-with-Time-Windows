/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.com.firdaus.program;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author slackware
 */
public class Ts {

    private int Q = 300;
    private int kec = 20;
    private int[] tMax = {15, 00};
    private int d[] = {0, 100, 100, 300, 40, 55, 50, 100, 50, 80, 40, 50, 70, 120, 40};
    private int s[] = {0, 25, 25, 60, 15, 15, 15, 25, 15, 20, 15, 15, 20, 30, 15};
    private int tw[][] = {
        {0, 0},
        {13, 20},//1
        {11, 45},//2
        {13, 00},//3
        {11, 30},//4
        {11, 40},//5
        {11, 20},//6
        {12, 20},//7
        {12, 10},//8
        {11, 40},//9
        {11, 50},//10
        {10, 50},//11
        {12, 40},//12
        {12, 40},//13
        {10, 30}//14
    };
    private double jarak[][] = {
        {0, 8.01, 5, 10.06, 4.09, 1.29, 0.45, 5.29, 8.2, 2.47, 4.89, 2.41, 3.04, 4.75, 0.353},
        {0, 11.5, 3.04, 8.37, 6.94, 8.61, 2.27, 13.1, 5.54, 9.82, 7.99, 8.16, 11.3, 7.66},
        {0, 15.4, 8.96, 5.49, 5.32, 9.38, 9.79, 5.97, 9.77, 4.45, 5.33, 0.284, 5.35},
        {0, 6.14, 11.4, 11.1, 5.67, 11.2, 8.94, 7.87, 12.8, 13.5, 15, 10.9},
        {0, 5.04, 3.6, 6.74, 5.5, 6.46, 2.19, 6.16, 6.78, 8.49, 4.2},
        {0, 2.44, 4.82, 10.2, 1.4, 6.89, 2.42, 3.05, 4.76, 1.64},
        {0, 5.77, 7.84, 3.64, 4.53, 3.62, 4.25, 4.77, 1.66},
        {0, 12.9, 3.4, 9.6, 5.85, 6.03, 9.13, 4.93},
        {0, 10.8, 3.53, 10.5, 11.1, 9.68, 8.54},
        {0, 7.5, 2.45, 2.62, 5.73, 2.12},
        {0, 7.19, 7.82, 9.53, 5.24},
        {0, 3.73, 4.2, 0.994},
        {0, 4.48, 3.33},
        {0, 5.1},
        {0}
    };
    private ArrayList<Agen> agenListUpdate = new ArrayList<Agen>();
    private ArrayList<Agen> jalur = new ArrayList<Agen>();
    private ArrayList ruteSemua = new ArrayList();

    public Ts() {
    }

    private ArrayList<Agen> initAgenList() {
        ArrayList<Agen> agenList = new ArrayList<Agen>();
        for (int i = 0; i < d.length; i++) {
            Agen a = new Agen();
            a.setNama("Agen " + (i));
            a.setDemand(d[i]);
            a.setTimeWindow(tw[i]);
            a.setTimeService(s[i]);
            a.setJarakAgenLain(getJarakAgenLain(i));
            agenList.add(a);
        }
        return agenList;
    }

    //mengambil array jarak "from" terhadap setiap Agen
    private double[] getJarakAgenLain(int from) {
        ArrayList<Double> distant = new ArrayList<Double>();
        double j[] = new double[jarak.length];
        for (int i = 0; i < j.length; i++) {
            j[i] = getJarak(from, i);
        }
        return j;
    }

    //mengambil jarak setiap i terhadap j pada array jarak
    private double getJarak(int i, int j) {
        if (i > j) {
            int temp = i;
            i = j;
            j = temp;
        }
        //Ai Aj
        double jrk = 0;
        if (i == j) {
            jrk = 0;
        } else {
            jrk = jarak[i][j - i];
        }
        //System.out.println("jarak : "+jrk);
        return jrk;
    }

    private void checkKelayakan(Agen a0, int tStart[]) {
        System.out.println("Check kelayakan antar ke masing-masing Agen ");
        System.out.print("Unsorted jarak : ");
        double unSorted[] = a0.getJarakAgenLain();
        for (int i = 0; i < unSorted.length; i++) {
            System.out.print("#" + unSorted[i] + " ");
        }
        System.out.println("");
        System.out.print("Sorted jarak : ");
        double sorted[] = getSortedDistant(unSorted);
        for (int i = 0; i < sorted.length; i++) {
            System.out.print("#" + sorted[i] + " ");
        }
        System.out.println("");

        Agen next = new Agen();
        for (int i = 0; i < sorted.length; i++) {//meloop masing2 jarak dari mulai yang terdekat
            if (sorted[i] != 0) {//jika jarak lebih tidak = 0
                int idx = checkIndex(sorted[i], unSorted);//mencari posisi index/urutan agen yang jaraknya adalah sorted[i]
                //System.out.println("jarak = " + sorted[i] + " index = " + idx);
                boolean isAgenNextExist = false;
                for (Agen ag : agenListUpdate) {//mengecheck ada tidaknya agen tersebut, kalau ada maka belum dikunjungi, kalau tidak berarti sudah dikunjung
                    if (ag.getNama().equals("Agen " + (idx))) {
                        next = ag;
                        isAgenNextExist = true;
                        break;
                    } else {
                        isAgenNextExist = false;
                    }
                }

                if (isAgenNextExist) {
                    int ti = (int) Math.round(((double) sorted[i] / (double) kec * (double) 60));
                    int tNew[] = addTime(tStart, (ti + next.getTimeService()));

                    if (Q < next.getDemand()) {
                        for (Agen x : agenListUpdate) {
                            if (x.getNama().equals("Agen " + (idx))) {
                                System.out.println(x.getNama() + " : demand lebih besar dari Quota");
                                break;
                            }
                        }
                    }
                    if (!checkLessThanTimeWindow(next.getTimeWindow(), tNew)) {
                        for (Agen x : agenListUpdate) {
                            if (x.getNama().equals("Agen " + (idx))) {
                                System.out.println(x.getNama() + " : kecepatan terlalu kecil sehingga tidak dapat mencapai sebelum time window");
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    private void run() {
        int tStart[] = {10, 00};
        agenListUpdate = initAgenList();
        Agen a0 = agenListUpdate.get(0);
        ArrayList<Agen> al = new ArrayList<Agen>();
        al.addAll(agenListUpdate);
        al.remove(0);
        agenListUpdate.clear();
        agenListUpdate.addAll(al);

        int sisaAgenBelumDikunjungi = 0;

        int jumlahKendaraan = 0;
        while (agenListUpdate.size() > 0 && sisaAgenBelumDikunjungi != agenListUpdate.size()) {
            Q = 300;
            tStart[0] = 10;
            tStart[1] = 00;
            System.out.println("==========Berangkat Kendaraan Ke " + (jumlahKendaraan + 1) + "===========");
            System.out.println("Q = " + Q);
            System.out.println("t0 = " + tStart[0] + ":" + tStart[1]);
            System.out.println("Nama : " + a0.getNama());
            System.out.println("Demand : " + a0.getDemand());
            System.out.println("Time Service : " + a0.getTimeService());
            System.out.println("Time Window : " + a0.getTimeWindow()[0] + " " + a0.getTimeWindow()[1]);

            sisaAgenBelumDikunjungi = agenListUpdate.size();
            runProcess(a0, agenListUpdate, tStart);
            System.out.println("Agen Belum Terkunjungi : ");
            for (Agen agen : agenListUpdate) {
                System.out.println("" + agen.getNama());
            }
            System.out.println("jalur : ");

            ArrayList<Agen> jalurCopy = new ArrayList<Agen>();
            jalurCopy.addAll(jalur);
            ruteSemua.add(jalurCopy);
            jalur.clear();

            System.out.println("===================Selesai===================");
            jumlahKendaraan++;
        }
        if (agenListUpdate.size() > 0) {//ada yang tidak dikunjungi
            checkKelayakan(a0, tStart);
            jumlahKendaraan -= 1;
            ruteSemua.remove(ruteSemua.size() - 1);
        }
        System.out.println("jumlah kendaraan : " + jumlahKendaraan);

        for (int i = 0; i < ruteSemua.size(); i++) {
            ArrayList<Agen> jalur = (ArrayList<Agen>) ruteSemua.get(i);
            System.out.print("Jalur ke " + (i + 1) + " : ");
            for (int j = 0; j < jalur.size(); j++) {
                //System.out.print(" {" + jalur.get(j).getNama() + ", Qsisa : " + jalur.get(j).getQ() + ", tNew" + jalur.get(j).gettNew()[0] + ":" + jalur.get(j).gettNew()[1]+"}");
                System.out.print(" " + jalur.get(j).getNama());
            }
            double unSorted[] = jalur.get(jalur.size() - 1).getJarakAgenLain();
            System.out.println("");
            for (int k = 0; k < unSorted.length; k++) {
                System.out.print("#" + unSorted[k] + " ");
            }
            int ti = (int) Math.round(((double) unSorted[0] / (double) kec * (double) 60));
            int tNew[] = addTime(jalur.get(jalur.size() - 1).gettNew(), ti);
            System.out.println("\nStart pulang : " + jalur.get(jalur.size() - 1).gettNew()[0] + ":" + jalur.get(jalur.size() - 1).gettNew()[1]);
            System.out.println("Waktu tempuh pulang : " + ti + " menit");
            System.out.println("Waktu tiba di depot/A0 : " + tNew[0] + ":" + tNew[1]);
            if (checkLessThanTimeWindow(tMax, tNew)) {
                System.out.println("tepat waktu");
            } else {
                System.out.println("telat");
            }

            System.out.println("");
        }
    }

    private Agen runProcess(Agen previous, ArrayList<Agen> agenList, int tStart[]) {
        System.out.println("Agen List Size : " + agenList.size());
        System.out.print("Unsorted jarak : ");
        double unSorted[] = previous.getJarakAgenLain();
        for (int i = 0; i < unSorted.length; i++) {
            System.out.print("#" + unSorted[i] + " ");
        }
        System.out.println("");
        System.out.print("Sorted jarak : ");
        double sorted[] = getSortedDistant(unSorted);
        for (int i = 0; i < sorted.length; i++) {
            System.out.print("#" + sorted[i] + " ");
        }
        System.out.println("");

        Agen next = new Agen();
        for (int i = 0; i < sorted.length; i++) {//meloop masing2 jarak dari mulai yang terdekat
            if (sorted[i] != 0) {//jika jarak lebih tidak = 0
                int idx = checkIndex(sorted[i], unSorted);//mencari posisi index/urutan agen yang jaraknya adalah sorted[i]
                System.out.println("jarak = " + sorted[i] + " index = " + idx);
                boolean isAgenNextExist = false;
                for (Agen ag : agenList) {//mengecheck ada tidaknya agen tersebut, kalau ada maka belum dikunjungi, kalau tidak berarti sudah dikunjung
                    if (ag.getNama().equals("Agen " + (idx))) {
                        next = ag;
                        System.out.println("Agen Selanjutnya : " + next.getNama());
                        isAgenNextExist = true;
                        break;
                    } else {
                        isAgenNextExist = false;
                    }
                }

                if (isAgenNextExist) {

                    int ti = (int) Math.round(((double) sorted[i] / (double) kec * (double) 60));
                    int tNew[] = addTime(tStart, (ti + next.getTimeService()));

                    if (Q >= next.getDemand()) {
                        if (checkLessThanTimeWindow(next.getTimeWindow(), tNew)) {

                            System.out.println("\n");
                            System.out.println("##############################################");
                            System.out.println("Nama : " + next.getNama());
                            System.out.println("Demand : " + next.getDemand());

                            System.out.println("Real waktu tempuh = " + ((double) sorted[i] / (double) kec * (double) 60));//dari jam diuah ke menit
                            System.out.println("Waktu tempuh = " + ti);
                            System.out.println("Time Service : " + next.getTimeService());
                            System.out.println("Total time = " + (ti + next.getTimeService()));
                            System.out.println("Time Start : " + tStart[0] + " " + tStart[1]);
                            System.out.println("tNew = " + tNew[0] + ":" + tNew[1]);
                            System.out.println("Time Window : " + next.getTimeWindow()[0] + " " + next.getTimeWindow()[1]);
                            System.out.println("Check demand : " + (Q >= next.getDemand()));
                            System.out.println("Check Less Than Time Window : " + checkLessThanTimeWindow(next.getTimeWindow(), tNew));

                            Q -= next.getDemand();
                            System.out.println("Q sisa = " + Q);

                            next.setQ(Q);
                            next.settNew(tNew);
                            jalur.add(next);

                            ArrayList<Agen> newAgenList = new ArrayList<Agen>();
                            newAgenList.addAll(agenList);
                            for (int j = 0; j < agenList.size(); j++) {
                                if (agenList.get(j).getNama().equals("Agen " + idx)) {
                                    newAgenList.remove(j);
                                    agenListUpdate.clear();
                                    agenListUpdate.addAll(newAgenList);
                                    if (newAgenList.size() > 0) {
                                        next.setNextAgen(runProcess(next, newAgenList, tNew));
                                    }
                                    break;
                                }
                            }
                            break;
                        } else {
                            //time window tidak memenuhi lanjut ke paling dekat berikutnya
                            System.out.println("Agen " + idx + " tidak memenuhi TW");
                        }
                    } else {
                        //demand tidak memenuhi lanjut ke paling dekat berikutnya
                        System.out.println("Agen " + idx + " tidak memenuhi demand");
                    }
                }
            }
        }
        if (next.getNama().equals("")) {
            next.setNextAgen(null);
        }
        return next;
    }

    private double[] getSortedDistant(double[] jarak) {
        double[] temp = new double[jarak.length];
        ArrayList<Double> x = new ArrayList<Double>();
        for (int i = 0; i < jarak.length; i++) {
            x.add(jarak[i]);
        }
        Collections.sort(x);
        Object z[] = x.toArray();
        for (int i = 0; i < z.length; i++) {
            temp[i] = Double.parseDouble(z[i].toString());
        }
        return temp;
    }

    private int[] addTime(int[] time, int menit) {
        int timebaru[] = new int[2];
        int menitBaru = time[1] + menit;
        if (menitBaru >= 60) {
            int jam = menitBaru / 60;
            int menitSisa = menitBaru % 60;
            timebaru[0] = time[0] + jam;
            timebaru[1] = menitSisa;
        } else {
            timebaru[0] = time[0];
            timebaru[1] = menitBaru;
        }

        if (timebaru[0] == 24) {
            timebaru[0] = 00;
        } else if (timebaru[0] > 24) {
            timebaru[0] = timebaru[0] - 24;
        }
        return timebaru;
    }

    private boolean checkLessThanTimeWindow(int[] tw, int[] tnew) {
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

    private int checkIndex(double jarak, double[] unsorted) {
        int x = 0;
        for (int i = 0; i < unsorted.length; i++) {
            if (jarak == unsorted[i]) {
                x = i;
                break;
            }
        }
        return x;
    }

    public static void main(String[] args) {
        Ts p = new Ts();
        p.run();
    }

}
