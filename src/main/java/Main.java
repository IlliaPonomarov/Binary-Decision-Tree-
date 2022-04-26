import java.util.Random;

public class Main {
    public static void main(String[] args) {
      BinaryDecisionDiagram binaryDecisionDiagram = new BinaryDecisionDiagram();

          String order = "ABCD";
      String str = "";

      binaryDecisionDiagram.BDD_create("AB+C", "ABC");

      if (binaryDecisionDiagram.BDD_use("000") != 0)
          System.out.println("Error, for A=0, B=0, C=0 result should be 0.\n");

      if (binaryDecisionDiagram.BDD_use("001") != 1)
          System.out.println("Error, for A=0, B=0, C=1 result should be 1.\n");

        if (binaryDecisionDiagram.BDD_use("010") != 0)
            System.out.println("Error, for A=0, B=1, C=0 result should be 0.\n");

        if (binaryDecisionDiagram.BDD_use("011") != 1)
            System.out.println("Error, for A=0, B=0, C=1 result should be 1.\n");

        if (binaryDecisionDiagram.BDD_use("100") != 0)
            System.out.println("Error, for A=1, B=0, C=0 result should be 0.\n");

    }


    public static String generate_DNF(String Alphabet, int conjunction_count, int conjunction_max_length) {
        String result = "";
        Random rand = new Random();

        for (int i = 0; i < conjunction_count; i++) {
            int conjunction_lenght = rand.nextInt(conjunction_max_length);
            if (conjunction_lenght > 0)
                result += "+";
            for (int j = 0; j < conjunction_lenght; j++) {
                result += ((rand.nextBoolean()) ? "!" : "") + Alphabet.charAt(rand.nextInt(Alphabet.length()));
            }
        }
        result = result.substring(1);
        return result;
    }
}
