
public class Sqrt {

    private final static double ACCURACY = 0.0000000000001;

    public static double sqrt(double num) throws SqrtException {
        if (num < 0.0) {
            throw new SqrtException("Bug!  "+num+" is negative!");
        }
        double delta = num * ACCURACY;
        double min = 0;
        double max = Math.max(num, 1.0);
        while (max - min >= delta) {
            double mid = (min + max) / 2.0;
            if ((mid * mid) > num) {
                max = mid;
            } else {
                min = mid;
            }
        }
        return (min + max) / 2.0;
    }

    public static void main(String[] args) {

        try {
            System.out.println(sqrt(4.0));
            System.out.println(sqrt(2.0));

            System.out.println(sqrt(-2.0));
            System.out.println(sqrt(-16.0));
        } catch (SqrtException ex) {
            ex.printStackTrace();
            System.exit(1);
        }


    }
}
