public class Main {
    public static void main(String[] args) {
      BinaryDecisionDiagram binaryDecisionDiagram = new BinaryDecisionDiagram();

      binaryDecisionDiagram.BDD_create("ABCD+DC+AB+BC+A", "DCAB");
      //binaryDecisionDiagram.BDD_create("AB+AC+BC", "ABC");
      binaryDecisionDiagram.inorder();

    }
}
