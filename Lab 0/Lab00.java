public class Lab00 {
    public static void main(String[] args) {

        int x = 5;
        String y = "hello";
        float z = 9.8f;

        // printing the variables
        System.out.println("x: " + x + " y: " + y + " z: " + z);

        // a list (Make an array in java)
        int arr[] = {3, 6, -1, 2};
        for (int nums : arr) {
            System.out.println(nums);
        }

        // call a function
        int numFound = char_counts(y, 'l');
        System.out.println("Found: " + numFound);


        // a counting for loop
        for (int n = 1; n <= 10; n++) {
            System.out.print(n + " ");
        }

        System.out.println();
    }

    public static int char_counts(String s, char c) {
        int count = 0;
        for (int  i = 0 ; i  < s.length()-1; i++) {
            if (c == s.charAt(i)) {
                count++;
            }
        }
        return count;
    }
}
