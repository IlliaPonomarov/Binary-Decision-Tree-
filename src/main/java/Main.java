import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static int MEMORY = 0;
    public static double MEMORY_AVERAGE = 0;
    public static double TIME_AVERAGE_CREATE = 0;
    public static double TIME_AVERAGE_SEARCH = 0;
    private static int countOfBDD = 0;
    private static String ORDER = "ABCDEFGHIJKLM";
    private static int errors = 0;
    private static int maxNodes = 0;


    public static String convertDecimalToBinary(int number){
        if (number == 0)
            return String.valueOf(number);

        StringBuilder binaryNumber = new StringBuilder();
        Integer quotient = number;

        while (quotient > 0){
            int reminded = quotient % 2;
            binaryNumber.append(reminded);
            quotient /= 2;
        }


        binaryNumber = binaryNumber.reverse();
        return binaryNumber.toString();
    }

    public static void main(String[] args) throws IOException {

      BinaryDecisionDiagram binaryDecisionDiagram = new BinaryDecisionDiagram();

        String order = "";
        int size = 0;

        Scanner scanner = new Scanner(System.in);

        System.out.println("Input your order for generation of random dnf function (Valid values from \"A\" to \"Z\", Example:\"ZBDEKLMN\"): ");
        order = scanner.next();

        maxNodes = (int) Math.pow(2, order.length());


        System.out.println("Count of trees: ");
        size = scanner.nextInt();
        countOfBDD = size;

        ORDER = order.toUpperCase();
        testRandomDNFandSearchAllWays();

//        BinaryDecisionDiagram binaryDecisionDiagram1 = new BinaryDecisionDiagram();
//        binaryDecisionDiagram1.BDD_create("AB+C", "ABC");
//        //binaryDecisionDiagram1.inorder();


    }

    public static String randomDNF(String arguments, String order, Random random){
        for (int j = 0; j < order.length(); j++) {
            if (random.nextBoolean())
                if (random.nextBoolean())
                    arguments+= "!" +order.charAt(j);
                else
                    arguments+= order.charAt(j);

        }
        return arguments;
    }

    public static String generateDNF(String order, Integer statesCount) {
        String result = "";
        String dnf = "";
        Random rand = new Random();

        if (statesCount <= 0)
            statesCount = rand.nextInt(order.length() + 1);


        for (int i = 0; i < statesCount; i++) {
            int randomStateLength = rand.nextInt(order.length());

            if (randomStateLength > 0) {
                dnf = "";
                for (int j = 0; j < order.length(); j++) {
                    if (rand.nextBoolean())
                        if (rand.nextBoolean())
                            dnf += "!" + order.charAt(j);
                        else
                            dnf+= "" + order.charAt(j);

                }
                if (dnf.length() == 0)
                    continue;
                else
                    result += "+" + dnf;
            }
        }

        if (result.length() <= 1){
            if (rand.nextBoolean())
                return "!" + order.charAt(rand.nextInt(order.length()));
            else
                return "" + order.charAt(rand.nextInt(order.length()));
        }
        else
            return result.substring(1);

    }

    public static String replaceZeroOne(String calculateDnfRes, String state, String order, int i){
        if (state.charAt(i) == '0') {
            calculateDnfRes = calculateDnfRes.replaceAll("!" + String.valueOf(order.charAt(i)), "1");
            calculateDnfRes = calculateDnfRes.replaceAll(String.valueOf(order.charAt(i)), "0");

        } else {
            calculateDnfRes = calculateDnfRes.replaceAll("!" + String.valueOf(order.charAt(i)), "0");
            calculateDnfRes = calculateDnfRes.replaceAll(String.valueOf(order.charAt(i)), "1");
        }

        return  calculateDnfRes;
    }

    public static int calculateDnfResult(String state, String bfunction, String order) {
        String calculateDnfRes = bfunction;
        int cur = 0;
        String[] states = null;

        if (state.length() != order.length()) {
            System.err.println("State length should be equals a order length!");
            return -1;
        }

        for (int i = 0; i < order.length(); i++)
            calculateDnfRes = replaceZeroOne(calculateDnfRes, state, order, i);


        //0+0+1011
         states = calculateDnfRes.split("\\+");



        for (String stat : states) {
            if (stat.contains("0"))
                continue;
            else {
                cur = 1;
                return cur;
            }
        }

        return cur;
    }

    public static  void testRandomDNFandSearchAllWays() throws IOException {

        int sumFailer = 0;

        System.out.println("Count of checked tree : ");

        //
        for (int i = 0; i < countOfBDD; i++) {

            errors = 0;

            //generate random dnf function , with 100 length
            String bFunction = generateDNF(ORDER, 100);
            BinaryDecisionDiagram tree = new BinaryDecisionDiagram();

            // 
            double startTime = System.currentTimeMillis();
                 tree.BDD_create(bFunction, ORDER);
            double endTime = System.currentTimeMillis();

            TIME_AVERAGE_CREATE += ((endTime - startTime) / 1000.00) / countOfBDD;

            testOfConcreteTree(tree);

            sumFailer += errors;
            System.lineSeparator().repeat(100);
            System.out.printf("Checking the tree %s,\nNumber of checked tree %d tree, number of mistakes: %d\n", bFunction, i, errors);
            System.out.println("--------");

        }
        MEMORY_AVERAGE = MEMORY / countOfBDD;

        System.out.println(sumFailer);
        System.out.println();

        System.out.printf("\nCREATE:\nAverage Time complexity create of one tree: %f s.\nAverage Memory: %.0f \n", TIME_AVERAGE_CREATE, MEMORY_AVERAGE);
        System.out.printf("\nSEARCH:\nAverage Time complexity search of one nodes in tree: %f s.\n", TIME_AVERAGE_SEARCH);
        System.out.printf("\nNumber of vertices with reduction: %d\n", (int)MEMORY_AVERAGE);
        System.out.printf("\nNumber of vertices without reduction: %d\n", maxNodes);
        System.out.printf("%d vertices out of %d vertices were not created\n", (int)(maxNodes - MEMORY_AVERAGE), maxNodes);
        System.out.printf("\n\nReduction: %.5f %%\n", ((maxNodes - MEMORY_AVERAGE) / maxNodes) * 100);


    }

    public static String correctTruthTableForOrder(String states, int n, BinaryDecisionDiagram tree){

        if (n < tree.getOrder().length())
            for (int j = 0; j < tree.getOrder().length() - n; j++)
                states = "0" + states;


            return states;
    }


    public static void testOfConcreteTree(BinaryDecisionDiagram tree) {
        int search_res = -1;
        int alter = -1, n = 0;
        double timeSearchAllElementsTree = 0;
        int maxSizeTruthTable = (int) Math.pow(2, tree.getOrder().length());

        String states = new String();

        for (int i = 0; i < maxSizeTruthTable; i++) {
            states = convertDecimalToBinary(i);
            n = states.length();

            states = correctTruthTableForOrder(states, n, tree);
            alter = calculateDnfResult(states, tree.getRoot().bfunction, tree.getOrder());


            double startTime = System.currentTimeMillis();
                search_res = tree.BDD_use(tree, states);
            double endTime = System.currentTimeMillis();

            timeSearchAllElementsTree += (endTime - startTime) / 1000.00;


            // If we have error during a search.
            if (search_res != alter) {
                System.out.println(states + "-\t\t" + tree.getRoot().bfunction + ":\tActually: " + search_res + "\tExcept: " + alter + "");
                errors++;
            }
        }
        TIME_AVERAGE_SEARCH += timeSearchAllElementsTree / maxSizeTruthTable;

    }
}

//CURRENT LEVEL: A
// A + BC = 1
// 1 + 00, 1 + 10, 1 + 01, 1 + 11 == 1