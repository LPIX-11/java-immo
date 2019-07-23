package main.java.com.m1gl.utils;

import main.java.com.m1gl.config.HibernateConfiguration;
import main.java.com.m1gl.models.Photo;
import org.glassfish.jersey.media.multipart.ContentDisposition;

import java.io.*;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utilitaire {

    private static String uploadedDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMYYYYhhmmss");
        return dateFormat.format(new Date());
    }

    // save uploaded file to new location
    public Photo writeToFile(InputStream uploadedInputStream, ContentDisposition contentDisposition) throws IOException {
//        String uploadLocation = System.getProperty("user.home") + "/img/";
        String uploadLocation = this.getClass().getResource("images/").getPath();
        File chemin = new File(uploadLocation);
        if (!chemin.exists())
            chemin.mkdir();
        String path = uploadLocation + uploadedDate() + contentDisposition.getFileName();
        File result = new File(path);
        OutputStream out = new FileOutputStream(result);
        try {
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            Photo photo = new Photo();
            photo.setPath(path);
            out.flush();
            out.close();
            return photo;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            out.close();
        }
    }

    public static String maDate() {
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String samaDate = df.format(date);
        String[] array1 = samaDate.split(" ");
        String[] array = array1[0].split("/");

        String y = array[2];
        String[] array2 = array1[1].split(":");
        String hh = array2[0];
        String mm = array2[1];
        String ss = array2[2];
        return y + hh + mm + ss;
    }

    public static String generateNumBien() {
        try {
            int a = (int) HibernateConfiguration.getSession().createQuery("SELECT MAX(b.id) FROM Bien b").getSingleResult();
            NumberFormat nf = new DecimalFormat("0000");
            int b = a + 1;
            String matricule = nf.format(b);
            return "B" +maDate() + matricule;

        } catch (Exception e) {
            return "B" + maDate() + "0001";
        }
    }

    public static String generateMatriculeClient() {
        try {
            int a = (int) HibernateConfiguration.getSession().createQuery("SELECT MAX(c.id) FROM Client c").getSingleResult();
            NumberFormat nf = new DecimalFormat("0000");
            int b = a + 1;
            String matricule = nf.format(b);
            return "C" +maDate() + matricule;

        } catch (Exception e) {
            return "C" + maDate() + "0001";
        }
    }

    public static String generateMatriculeUser() {
        try {
            int a = (int) HibernateConfiguration.getSession().createQuery("SELECT MAX(u.id) FROM User u").getSingleResult();
            NumberFormat nf = new DecimalFormat("0000");
            int b = a + 1;
            String matricule = nf.format(b);
            return "U" +maDate() + matricule;

        } catch (Exception e) {
            return "U" + maDate() + "0001";
        }
    }

    public static String generateNumpaiement() {
        try {
            int a = (int) HibernateConfiguration.getSession().createQuery("SELECT MAX(p.id) FROM Paiement p").getSingleResult();
            NumberFormat nf = new DecimalFormat("0000");
            int b = a + 1;
            String matricule = nf.format(b);
            return "P" +maDate() + matricule;

        } catch (Exception e) {
            return "P" + maDate() + "0001";
        }
    }
}
