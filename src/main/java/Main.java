import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static int MEMORY = 0;
    public static double MEMORY_AVERAGE = 0;
    public static double TIME_AVERAGE_CREATE = 0;
    public static double TIME_AVERAGE_SEARCH = 0;
    private final static int countOfBDD = 1000;
    private static String ORDER = "ABCDEFGHIJKLM";
    private static int failure = 0;


    public static void main(String[] args) throws IOException {

      BinaryDecisionDiagram binaryDecisionDiagram = new BinaryDecisionDiagram();

        String order = "";

        Scanner scanner = new Scanner(System.in);

        System.out.println("Input your order for generation of random dnf function (Valid values from \"A\" to \"Z\", Example:\"ZBDEKLMN\"): ");

        order = scanner.next();
        ORDER = order.toUpperCase();
        BDDuse_TEST();

    }


    public static String generateDNF(String Alphabet, Integer ConjunctionCount) {
        String result = "";
        String conjunction = "";
        Random rand = new Random();
        if (ConjunctionCount == null) {
            ConjunctionCount = rand.nextInt(Alphabet.length() + 1);
        }

        for (int i = 0; i < ConjunctionCount; i++) {
            int conjunction_lenght = rand.nextInt(Alphabet.length());
            if (conjunction_lenght > 0) {
                conjunction = "";
                for (int j = 0; j < Alphabet.length(); j++) {
                    if (rand.nextBoolean())
                        conjunction += ((rand.nextBoolean()) ? "!" : "") + Alphabet.charAt(j);
                }
                if (conjunction.length() != 0)
                    result += "+" + conjunction;
            }
        }

        if (result.length() <= 1)
            return ((rand.nextBoolean()) ? "!" : "") + Alphabet.charAt(rand.nextInt(Alphabet.length()));
        else {
            result = result.substring(1);
            return result;
        }
    }

    public static char SubstituteAllVariables(String State, String Bfunction, String Order) {
        String result = Bfunction;
        String letter;

        if (State.length() != Order.length()) {
            System.out.println("ERR");
            return '9';
        }

        for (int i = 0; i < Order.length(); i++) {
            letter = String.valueOf(Order.charAt(i));

            if (State.charAt(i) == '1') {
                result = result.replaceAll("!" + letter, "0");
                result = result.replaceAll(letter, "1");
            } else {
                result = result.replaceAll("!" + letter, "1");
                result = result.replaceAll(letter, "0");
            }
        }

        String[] conjunction = result.split("\\+");

        for (int i = 0; i < conjunction.length; i++) {
            if (conjunction[i].contains("0")) {

            } else {
                return '1';
            }

        }

        return '0';
    }


    /**
     * Rigorous Test.
     */

    public static  void BDDuse_TEST() throws IOException {

        int sumFailer = 0;
        int treeCheck = 0;

        System.out.println("Count of checked tree : ");
        for (int i = 0; i < countOfBDD; i++) {
            failure = 0;
            String bFunction = generateDNF(ORDER, 100);
            BinaryDecisionDiagram tree = new BinaryDecisionDiagram();


           double startTime = System.currentTimeMillis();
                 tree.BDD_create(bFunction, ORDER);
            double endTime = System.currentTimeMillis();
            TIME_AVERAGE_CREATE += ((endTime - startTime) / 1000.00) / countOfBDD;


            testOfConcreteTree(tree);

            sumFailer += failure;
            System.lineSeparator().repeat(100);
            System.out.printf("Checking the tree %s,\nNumber of checked tree %d tree, number of mistakes: %d\n", bFunction, i, failure);
            System.out.println("--------");

        }
        MEMORY_AVERAGE = MEMORY / countOfBDD;

        System.out.println(sumFailer);
        System.out.println();

        System.out.printf("\nCREATE:\nAverage Time complexity create of one tree: %f s.\nAverage Memory: %.0f megabyte\n", TIME_AVERAGE_CREATE, MEMORY_AVERAGE);
        System.out.printf("\nSEARCH:\nAverage Time complexity search of one nodes in tree: %f s.\n", TIME_AVERAGE_SEARCH);


    }

    public static void testOfConcreteTree(BinaryDecisionDiagram tree) {
        int BDD_USE_result = -5;
        char alternative_result = ' ';
        int alter = -5;
        double timeSearchAllElementsTree = 0;

        int stateVariations = (int) Math.pow(2, tree.getOrder().length());
        String arguments;

        for (int i = 0; i < stateVariations; i++) {
            arguments = Integer.toBinaryString(i);
            int arg_len = arguments.length();
            if (arg_len < tree.getOrder().length()) {
                for (int j = 0; j < tree.getOrder().length() - arg_len; j++)
                    arguments = "0" + arguments;
            }
            alternative_result = SubstituteAllVariables(arguments, tree.getRoot().bfunction, tree.getOrder());
            alter = Character.getNumericValue(alternative_result);


            double startTime = System.currentTimeMillis();
             BDD_USE_result = tree.BDD_use(tree, arguments);
            double endTime = System.currentTimeMillis();

            timeSearchAllElementsTree += (endTime - startTime) / 1000.00;



            // If we have error during a search.
            if (BDD_USE_result != alter) {
                System.out.println(arguments + "|\t:" + tree.getRoot().bfunction + ":\tActually: [" +
                        BDD_USE_result + "]\tExcept: [" + alter + "]");
                failure++;
              }
            }
            TIME_AVERAGE_SEARCH += timeSearchAllElementsTree / stateVariations;

        }

    }