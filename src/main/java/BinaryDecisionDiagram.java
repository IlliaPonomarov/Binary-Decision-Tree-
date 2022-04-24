
import java.util.*;


public class BinaryDecisionDiagram {

        private Node root;
        static final int COUNT = 10;
        private HashTable hashTable;
        private Node zero;
        private Node one;
        private String left_bfunction;
        private String right_bfunction;
        private int random_id = (int) Math.floor(Math.random() * 400);


        public BinaryDecisionDiagram(){

                zero = new Node("0", "");
                one = new Node("1", "");
                this.root = null;

        }

        public void BDD_use(String way){
            String[] wayArray = way.split("");

            while (root != null){

            }
        }

        public Node createLeftNode(Node root, HashTable hashTable, String bfunction, String order){

                this.left_bfunction = getDNF(bfunction, Character.toString(order.charAt(0)), 0);

                if (order.length() > 1)
                        root.left = hashTable.insert(random_id, new Node(this.left_bfunction, Character.toString(order.charAt(1))));
                else
                        root.left = hashTable.insert(random_id, new Node(this.left_bfunction, Character.toString(order.charAt(0))));


                if (root.left.bfunction.equals("0")) {
                        root.left = zero;
                        return  root.left;
                }
                else if (root.left.bfunction.equals("1")) {
                        root.left = one;
                        return root.left;
                }

                return root.left;
        }
        public Node createRightNode(Node root, HashTable hashTable, String bfunction, String order){
                this.right_bfunction = getDNF(bfunction, Character.toString(order.charAt(0)), 1);

                if (order.length() > 1)
                        root.right = hashTable.insert(random_id, new Node(this.right_bfunction, Character.toString(order.charAt(1))));
                else
                        root.right = hashTable.insert(random_id, new Node(this.right_bfunction, Character.toString(order.charAt(0))));



                if (root.right.bfunction.equals("0")) {
                        root.right = zero;
                        return root.right;
                }
                else if (root.right.bfunction.equals("1")) {
                        root.right = one;
                        return root.right;
                }

                return root.right;
        }

        public void BDD_create(String bfunction, String order) {

                StringBuilder buffer_order = new StringBuilder(order);
                ArrayList<Node> currentStates = new ArrayList<>();
                int maxSize = 100;


                if (root == null) {

                        hashTable = new HashTable(maxSize);

                        StringBuilder buffer = new StringBuilder(order);

                        // init bfunction for left and right node
                        this.root = new Node(bfunction, Character.toString(order.charAt(0)));
                        this.root.left = createLeftNode(root, hashTable, bfunction, order);
                        this.root.right = createRightNode(root, hashTable, bfunction, order);

                        buffer.deleteCharAt(0);
                        order = new String(buffer);

                        currentStates.add(this.root.left);
                        currentStates.add(this.root.right);

                        hashTable = null;
                }

                StringBuilder order_builder = new StringBuilder(order);
                ArrayList<Node> buffer = new ArrayList<>();
                hashTable = new HashTable(maxSize);

                for (int i = 0; i < currentStates.size(); i++) {

                        currentStates.get(i).left = createLeftNode(currentStates.get(i), hashTable, currentStates.get(i).bfunction, order);
                        currentStates.get(i).right = createRightNode(currentStates.get(i), hashTable, currentStates.get(i).bfunction, order);

                        if (currentStates.get(i).equals("0") || currentStates.get(i).equals("1")) {
                                buffer.add(currentStates.get(i).left);
                                buffer.add(currentStates.get(i).right);
                        }

                        if (i == currentStates.size() - 1) {
                                currentStates = buffer;
                                hashTable = new HashTable(maxSize);
                                i = 0;
                                order_builder.deleteCharAt(0);
                        }
                        order = new String(order_builder);
                }


                return;

        }

        public void print2DUtil(Node root, int space)
        {
                // Base case
                if (root == null)
                        return;

                // Increase distance between levels
                space += COUNT;

                // Process right child first
                print2DUtil(root.right, space);

                // Print current node after space
                // count
                System.out.print("\n");
                for (int i = COUNT; i < space; i++)
                        System.out.print(" ");
                System.out.print(root.bfunction + "\n");

                // Process left child
                print2DUtil(root.left, space);
        }

        // Wrapper over print2DUtil()
        public void print2D(Node root)
        {
                // Pass initial space count as 0
                print2DUtil(root, 0);
        }



        public static String getDNF(String bfunction, String order, int side){
                int index = 0;
                String[] zeroList = new String[]{};
                String[] oneList = new String[]{};


                if (order.length() > 1 )
                        return "Error";
                else
                {
                        if (side == 0) {
                                StringBuilder buffer = new StringBuilder();
                                zeroList = bfunction.split("\\+");
                                zeroList = removeDuplicate(zeroList);
                                String result = new String();
                                String buffer_string = new String();


                                for (String str: zeroList) {
                                        if (str.contains("!"+order)  && str.length() == 2) {
                                                result = "1";
                                        }

                                        if (str.contains("!"+order)) {
                                                buffer = new StringBuilder(str);
                                                int indexOf = buffer.indexOf("!");
                                                buffer.deleteCharAt(indexOf);
                                                indexOf = buffer.indexOf(order);
                                                buffer.deleteCharAt(indexOf);
                                                result+=buffer + "+";
                                        }

                                        if (str.contains(order)){
                                                result+="0";
                                        }

                                        if (!str.contains(order)) {
                                                buffer_string = new String(str);
                                                result += removeDuplicate(buffer_string.toCharArray()) + "+";
                                        }
                                }


                                if (result.length() > 1){

                                        result = removeDuplicate(result.toCharArray());
                                        buffer = new StringBuilder(result);


                                        for (int i = 0; i < buffer.length(); i++) {
                                                if (buffer.charAt(i) == '0') {
                                                        buffer.deleteCharAt(i);
                                                        i = 0;
                                                }
                                        }
                                        result=new String(buffer);
                                }

                                StringBuilder n = new StringBuilder(result);
                                n.deleteCharAt(n.length()-1);
                                result = new String(n);

                                if ((result.length() == 0) || (result.length() == 1 && result.contains(order)))
                                        result = "0";

                                return result;
                        }
                        if (side == 1){
                                        StringBuilder buffer = new StringBuilder();
                                        oneList = bfunction.split("\\+");
                                        String result = new String();
                                        oneList = removeDuplicate(oneList);
                                        String buffer_string = new String();

                                        for (int i = 0; i < oneList.length; i++) {
                                                buffer = new StringBuilder(oneList[i]);

                                                int indexOf = -1;

                                                if (oneList[i].contains("!"+order))
                                                        result = "0";

                                                // string without order
                                                if (!oneList[i].contains("!") && !oneList[i].contains(order)) {

                                                        buffer_string = new String(buffer);

                                                        result += removeDuplicate(buffer_string.toCharArray()) + "+";
                                                }

                                                if (!oneList[i].contains("!") && oneList[i].contains(order)){
                                                        indexOf = buffer.indexOf(order);
                                                        buffer.deleteCharAt(indexOf);
                                                        buffer_string = new String(buffer);

                                                        result += removeDuplicate(buffer_string.toCharArray()) + "+";

                                                }

                                                if (oneList[i].contains("!") && !oneList[i].contains("!" + order)){
                                                        indexOf = buffer.indexOf(order);
                                                        buffer.deleteCharAt(indexOf);

                                                        buffer_string = new String(buffer);

                                                        result += removeDuplicate(buffer_string.toCharArray()) + "+";
                                                }

                                                if (oneList[i].contains(order) && oneList[i].length() == 1)
                                                        return "1";


                                        }

                                        StringBuilder n = new StringBuilder(result);

                                        if (n.length() > 0) {
                                                n.deleteCharAt(n.length() - 1);
                                                result = new String(n);
                                        }
                                        return result;


                        }
                }

                return null;
        }

        public void inorder(){
                inorderRec(this.root);
        }
        public void inorderRec(Node root){

                if (root == null)
                        return;

                inorderRec(root.left);
                System.out.println(root.bfunction + " ");
                inorderRec(root.right);
        }

        public static String removeDuplicate(char str[]){

                int index = 0;

                for (int i = 0; i < str.length; i++) {
                        int j;

                        for ( j = 0; j < i; j++) {
                                if (str[i] == str[j])
                                        break;
                        }

                        if (j == i)
                                str[index++] = str[i];
                }

                return String.valueOf(Arrays.copyOf(str, index));
        }

        public static String[] removeDuplicate(String str[]){

                ArrayList<String> aListColors = new ArrayList<String>();

                for(int i=0; i < str.length; i++){

                        if( !aListColors.contains(str[i]) ){
                                aListColors.add(str[i]);
                        }
                }
                return str = aListColors.toArray( new String[aListColors.size()] );
        }

}
