/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.com.firdaus.program;

/**
 *
 * @author slackware
 */
public class Agen {

    private String nama;
    private int demand;
    private int[] timeWindow;
    private int timeService;
    private int[] tNew;
    private double[] jarakAgenLain;
    private Agen previousAgen;
    private Agen nextAgen;
    private int Q;

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getQ() {
        return Q;
    }

    public void setQ(int Q) {
        this.Q = Q;
    }

    public int getDemand() {
        return demand;
    }

    public void setDemand(int demand) {
        this.demand = demand;
    }

    public int[] getTimeWindow() {
        return timeWindow;
    }

    public void setTimeWindow(int[] timeWindow) {
        this.timeWindow = timeWindow;
    }

    public int getTimeService() {
        return timeService;
    }

    public void setTimeService(int timeService) {
        this.timeService = timeService;
    }

    public int[] gettNew() {
        return tNew;
    }

    public void settNew(int[] tNew) {
        this.tNew = tNew;
    }

    public double[] getJarakAgenLain() {
        return jarakAgenLain;
    }

    public void setJarakAgenLain(double[] jarakAgenLain) {
        this.jarakAgenLain = jarakAgenLain;
    }

    public Agen getPreviousAgen() {
        return previousAgen;
    }

    public void setPreviousAgen(Agen previousAgen) {
        this.previousAgen = previousAgen;
    }

    public Agen getNextAgen() {
        return nextAgen;
    }

    public void setNextAgen(Agen nextAgen) {
        this.nextAgen = nextAgen;
    }

}
