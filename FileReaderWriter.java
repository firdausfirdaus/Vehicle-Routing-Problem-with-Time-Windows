package src.com.firdaus.program;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;

/**
 *
 * @author slackware
 */
public class FileReaderWriter {

    private static PrintStream ps = null;
    private static FileOutputStream fos = null;
    private static DataInputStream dis = null;
    private static FileInputStream fis = null;

    public static void writeJarak(int jmlAgen, ArrayList<Double> z) {
        try {
            fos = new FileOutputStream("jarakAgen.txt");
            ps = new PrintStream(fos);
            int x = 0;
            for (int i = 0; i < jmlAgen; i++) {
                for (int j = i + 1; j <= jmlAgen; j++) {
                    ps.print(z.get(x).toString() + " ,");
                    x++;
                }
                ps.print("#");
                ps.print("\n");
            }
            JOptionPane.showMessageDialog(null, "Simpan Jarak Agen Berhasil");
        } catch (FileNotFoundException x) {
            System.out.println("File tidak dapat dibuka");
        }
    }

    public static ArrayList<String> readJarak() {
        ArrayList<String> baris = new ArrayList<String>();
        try {
            fis = new FileInputStream("jarakAgen.txt");
            dis = new DataInputStream(fis);
            String x = "";
            try {
                while ((x = dis.readLine()) != null) {
                    StringTokenizer st = new StringTokenizer(x, "#");
                    while (st.hasMoreTokens()) {
                        baris.add((String) st.nextElement());
                    }
                }
            } catch (IOException r) {
                System.out.println("Error : " + r.getMessage());
            }
        } catch (FileNotFoundException d) {
            System.out.println("file tidak ditemukan");
        }
        return baris;
    }

    public static void writeJumlahAgen(String[] jkw) {
        try {
            fos = new FileOutputStream("jumlahAgen.txt");
            ps = new PrintStream(fos);
            ps.print(jkw[0] + ", " + jkw[1] + ", " + jkw[2]+ ", " + jkw[3]+ ", " + jkw[4]);
            JOptionPane.showMessageDialog(null, "Simpan Jumlah Agen Berhasil");
        } catch (FileNotFoundException x) {
            System.out.println("File tidak dapat dibuka");
        }

    }

    public static String[] readJumlahAgen() {
        String[] jkw = new String[5];
        try {
            fis = new FileInputStream("jumlahAgen.txt");
            dis = new DataInputStream(fis);
            int jml = 0;
            String x = "";
            try {
                while ((x = dis.readLine()) != null) {
                    //jml = Integer.parseInt(x.trim());
                    StringTokenizer st = new StringTokenizer(x, ",");
                    String j = (String) st.nextElement().toString().trim();
                    String k = (String) st.nextElement().toString().trim();
                    String w = (String) st.nextElement().toString().trim();
                    String m = (String) st.nextElement().toString().trim();
                    String kc = (String) st.nextElement().toString().trim();
                    jkw[0] = j;
                    jkw[1] = k;
                    jkw[2] = w;
                    jkw[3] = m;
                    jkw[4] = kc;
                }
            } catch (IOException r) {
                System.out.println("Error : " + r.getMessage());
            }
        } catch (FileNotFoundException d) {
            System.out.println("file tidak ditemukan");
        }
        return jkw;
    }

    public static void writeAgen(ArrayList<Agen> al) {
        try {
            fos = new FileOutputStream("agen.txt");
            ps = new PrintStream(fos);
            for (Agen a : al) {
                ps.print(a.getNama() + ", " + a.getDemand() + ", " + a.getTimeService() + ", " + a.getTimeWindow()[0] + ", " + a.getTimeWindow()[1] + ", ");
                ps.print("#");
                ps.print("\n");
            }
            JOptionPane.showMessageDialog(null, "Simpan Data Agen Berhasil");
        } catch (FileNotFoundException x) {
            System.out.println("File tidak dapat dibuka");
        }
    }

    public static ArrayList<Agen> readAgen() {
        ArrayList<Agen> al = new ArrayList<Agen>();
        try {
            fis = new FileInputStream("agen.txt");
            dis = new DataInputStream(fis);
            String x = "";
            try {
                while ((x = dis.readLine()) != null) {
                    StringTokenizer st = new StringTokenizer(x, "#");
                    while (st.hasMoreTokens()) {
                        String ag = (String) st.nextElement();
                        //System.out.println(ag);
                        StringTokenizer st2 = new StringTokenizer(ag, ",");
                        String nama = (String) st2.nextElement().toString().trim();
                        String demand = (String) st2.nextElement().toString().trim();
                        String ts = (String) st2.nextElement().toString().trim();
                        String tw0 = (String) st2.nextElement().toString().trim();
                        String tw1 = (String) st2.nextElement().toString().trim();
                        Agen a = new Agen();
                        a.setNama(nama);
                        a.setDemand(Integer.parseInt(demand));
                        a.setTimeService(Integer.parseInt(ts));
                        int tw[] = {Integer.parseInt(tw0), Integer.parseInt(tw1)};
                        a.setTimeWindow(tw);
                        al.add(a);
                    }
                }
            } catch (IOException r) {
                System.out.println("Error : " + r.getMessage());
            }
        } catch (FileNotFoundException d) {
            System.out.println("file tidak ditemukan");
        }
        return al;
    }
}
