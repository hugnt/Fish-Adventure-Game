package root;

public class Intersection {
    public static boolean check(double[][] rec1, double[][] rec2) { //2*2 matrix
        double c1 = Math.max(rec1[0][0], rec2[0][0]);
        double d1 = Math.max(rec1[0][1], rec2[0][1]);
        double c2 = Math.min(rec1[1][0], rec2[1][0]);
        double d2 = Math.min(rec1[1][1], rec2[1][1]);
        return (c2 > c1 && d2 > d1);
    }
}