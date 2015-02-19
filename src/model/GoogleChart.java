package model;

import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class GoogleChart {
    public static String drawPic(int a, int b, String la, String lb) {
        int bit = 0;
        if (a < b) {
            bit = String.valueOf(b).length();
            bit -= 2;
            double da = 0.0;
            if (String.valueOf(b).length() - String.valueOf(a).length() < 3)
                da = (double) Integer.valueOf(String.valueOf(a).substring(
                        0,
                        3 - (String.valueOf(b).length() - String.valueOf(a)
                                .length())));
            da /= 10.0;
            double db = (double) Integer.valueOf(String.valueOf(b).substring(0,
                    3));
            db /= 10.0;
            String surl = "http://chart.apis.google.com/chart?chs=100x300&chd=t:"
                    + da + "," + db
                    + "&cht=bvs&chco=ff0000&chf=c,s,76A4FB&chxt=x,y&chxl=0:|" + la
                    + "|" + lb + "|1:|0|"+(100*Math.pow(10, bit))/2+"|"+(100*Math.pow(10, bit));
            return surl;

        }
        else {
            bit = String.valueOf(a).length();
            bit -= 2;
            double db = 0.0;
            if (String.valueOf(a).length() - String.valueOf(b).length() < 3)
                db = (double) Integer.valueOf(String.valueOf(b).substring(
                        0,
                        3 - (String.valueOf(a).length() - String.valueOf(b)
                                .length())));
            db /= 10.0;
            double da = (double) Integer.valueOf(String.valueOf(a).substring(0,
                    3));
            da /= 10.0;
            String surl = "http://chart.apis.google.com/chart?chs=100x300&chd=t:"
                    + da + "," + db
                    + "&cht=bvs&chco=ff0000&chf=c,s,76A4FB&chxt=x,y&chxl=0:|" + la
                    + "|" + lb + "|1:|0|"+(100*Math.pow(10, bit))/2+"|"+(100*Math.pow(10, bit));
            return surl;

        }

    }
}
