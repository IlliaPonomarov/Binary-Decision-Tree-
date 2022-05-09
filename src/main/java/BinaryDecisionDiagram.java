
import java.nio.charset.StandardCharsets;
import java.util.*;


public class BinaryDecisionDiagram {

        private Node root;
        static final int COUNT = 10;
        private String order;
        private HashTable hashTable;
        private Node zero = new Node("0");
        private Node one = one = new Node("1");
        private String left_bfunction;
        private String right_bfunction;
        private int random_id = (int) Math.floor(Math.random() * 400);




        public Node getRoot() {
                return root;
        }


        public BinaryDecisionDiagram(){

                this.root = null;

        }
        public BinaryDecisionDiagram(Node root){

                this.root = root;

        }


        public int getMemory(){
                return HashTable.count;
        }

        public int BDD_use(BinaryDecisionDiagram binaryDecisionDiagram, String input_values){

                Node current = binaryDecisionDiagram.root;

                if (input_values.length() != this.order.length()) {
                        System.err.println("Order and Way should be identical.");
                        return -1;
                }

                for (Character number: input_values.toCharArray()) {

                        if (number.equals('0'))
                                current = current.left;

                        else if (number.equals('1'))
                                current = current.right;

                        if (current == zero) {
                                return 0;
                        }
                        else if (current == one) {
                                return 1;
                        }


                }


                return -1;
        }





        //In the function argument, we pass the parent of our vertex (for which we want to create a child),
        // the current hash table (to check for uniqueness), and Order the current order.
        public Node createLeftNode(Node root, HashTable hashTable, String bfunction, String order){


                //We generate a new DNF function. If we create a left vertex,
                // then we substitute “0” in the current bfunction (DNF function).
                if (order.length() >= 1)
                        this.left_bfunction = getDNF(bfunction, Character.toString(order.charAt(0)), 0);


               // We send a new vertex to the hash table to check for uniqueness, and pass a random id to the hash table.
                if (order.length() > 1)
                        root.left = hashTable.insert(random_id, new Node(this.left_bfunction, Character.toString(order.charAt(1))));

                //We send a new vertex to the hash table to check for uniqueness, and pass a random id to the hash table.
                //The same, only if our order has a length of 1
                else if (order.length() == 1)
                        root.left = hashTable.insert(random_id, new Node(this.left_bfunction, Character.toString(order.charAt(0))));



               // If after generating the DNF function we get “0”, then our left vertex will receive a link to the vertex “0”.
                if (root.left.bfunction.equals("0") ) {
                        root.left = zero;
                        return  root.left;
                }

               // If after generating the DNF function we get “1”, then our left vertex will receive a link to the vertex “1”.
                else if (root.left.bfunction.equals("1")) {
                        root.left = one;
                        return root.left;
                }

                return root.left;
        }

        //In the function argument, we pass the parent of our vertex (for which we want to create a child),
        // the current hash table (to check for uniqueness), and Order the current order.
        public Node createRightNode(Node root, HashTable hashTable, String bfunction, String order){

                //We generate a new DNF function. If we create a left vertex, then we substitute “1” in the current bfunction (DNF function).
                // If to the right vertex, then we substitute 1 into the current order.
                if (order.length() >= 1)
                        this.right_bfunction = getDNF(bfunction, Character.toString(order.charAt(0)), 1);


                // We send a new vertex to the hash table to check for uniqueness, and pass a random id to the hash table.
                if (order.length() > 1)
                        root.right = hashTable.insert(random_id, new Node(this.right_bfunction, Character.toString(order.charAt(1))));

                //We send a new vertex to the hash table to check for uniqueness, and pass a random id to the hash table.
                        // The same, only if our order has a length of 1
                else if (order.length() == 1)
                        root.right = hashTable.insert(random_id, new Node(this.right_bfunction, Character.toString(order.charAt(0))));


                //If after generating the DNF function we get “0”, then our left vertex will receive a link to the vertex “0”.
                if (root.right.bfunction.equals("0")) {
                        root.right = zero;
                        return root.right;
                }

                //If after generating the DNF function we get “1”, then our left vertex will receive a link to the vertex “1”.
                else if (root.right.bfunction.equals("1")  || (root.right.bfunction.equals(order) &&  root.right.bfunction.length() == 1)) {
                        root.right = one;
                        return root.right;
                }


                return root.right;
        }


        // As input we get a DNF function and its Order
        public BinaryDecisionDiagram BDD_create(String bfunction, String order) {
                this.order = order;
                int countOrder = 2;
                StringBuilder buffer_order = new StringBuilder(order);
                DynamicArray currentStates = new DynamicArray();
                int maxSize = (int) Math.pow(2, countOrder) + 100;


                if (root == null) {

                       // We initialize a hash table for the second level, in which we will store unique elements for each of our levels.
                        hashTable = new HashTable(maxSize);

                        StringBuilder buffer = new StringBuilder(order);

                        // Creating the very first vertex.
                        this.root = new Node(bfunction, Character.toString(order.charAt(0)));

                        // We create children for the first vertex.
                        this.root.left = createLeftNode(root, hashTable, bfunction, order);
                        this.root.right = createRightNode(root, hashTable, bfunction, order);

                        buffer.deleteCharAt(0);
                        order = new String(buffer);


                        // Adding references of children the first vertex to a dynamic array (to my own implementation of a dynamic array).
                        // So that we always remember at what level we stopped.
                        //(This is so we don't go through all the vertices looking for vertices that don't have children created)
                        currentStates.add(this.root.left);
                        currentStates.add(this.root.right);


                        // I reset the hash table
                        hashTable = null;
                }

                StringBuilder order_builder = new StringBuilder(order);
                DynamicArray child = new DynamicArray();
                hashTable = new HashTable(maxSize);



                //After setting the first three vertices, we create a loop in which we iterate over our elements from the dynamic array (children of the previous vertex) that we added.
                // Now they become parents and we create children for them.

                for (int i = 0; i < currentStates.size(); ) {


                        if (!(currentStates.get(i).bfunction.equals("0")) && !(currentStates.get(i).bfunction.equals("1")) && (currentStates.get(i).left == null && currentStates.get(i).right == null)) {

                                currentStates.get(i).left = createLeftNode(currentStates.get(i), hashTable, currentStates.get(i).bfunction, order);

                                currentStates.get(i).right = createRightNode(currentStates.get(i), hashTable, currentStates.get(i).bfunction, order);


                                //After creating children for these vertices, we add them to the dynamic array too, to repeat the same algorithm.
                                child.add(currentStates.get(i).left);
                                child.add(currentStates.get(i).right);
                        }

                        //When we have created all the children for the current vertices, we reset the hash table for the current level.
                          if (i == currentStates.size() - 1) {


                                  //Children become parents and the current level changes.
                                currentStates = child;
                                countOrder++;
                                maxSize = (int) Math.pow(2, countOrder) + 100;
                                hashTable = new HashTable(maxSize);
                                i = 0;

                                if (order_builder.length() >= 1)
                                        order_builder.deleteCharAt(0);

                                if (order_builder.length() == 0)
                                        break;

                                child = new DynamicArray();
                        }
                        else
                                i++;

                        if (order_builder.equals(""))
                                break;

                        order = new String(order_builder);
                }


                return new BinaryDecisionDiagram(root);

        }

        public String getOrder(){
                return this.order;
        }



        //This function shortens the current DNF. If we generate a function for the left vertex, then we substitute “0”, if for the right vertex, then “1”.
        //
        //Example for function “AB+C”, current level “A”.
        //For the left vertex, we substitute “0” instead of “A” and get the result “C”.
        //For the right vertex, we substitute “1” instead of “A! and we get the result "B + C".

        public static String getDNF(String bfunction, String order, int side){
                int index = 0;
                String[] zeroList = new String[]{};
                String[] oneList = new String[]{};


                if (order.length() > 1 )
                        return "Error";
                else
                {

                        if (side == 0){

                                StringBuilder buffer = new StringBuilder();

                                // 1) We split our function by pluses and get an array.
                                //For example: AB+C => [AB, C].

                                zeroList = bfunction.split("\\+");
                                zeroList = removeDuplicate(zeroList);
                                String result = "";
                                String buffer_string = "";

                                // 2) We begin to consider each element for the presence of symbols of the current level.
                                for (String str: zeroList) {

//                                        if (str.contains("!"+order) && str.length() == 2)
//                                                return "1";

                                        if (str.contains("!"+order+"+"+order) || str.contains(order+"+"+ "!" + order))
                                                return "1";



                                        // 3) If the current element contains !A , and there are no characters in the string other than “!A” ,
                                        // then we return “1” because our function will definitely get “1” as output.
                                        //For Example:
                                        //
                                        //  !A + ABCDEFGH + !ABCD  =  1 + 11100000+ 1000   or
                                        //   1 + 0000000 + 1111  or
                                        //   1 + 101010101 + 1010  =   1.
                                        //
                                        //Then no matter what we substitute, we will always get “1” at the output.
                                        // Then it does not make sense to create further vertices, then we will simply indicate the link to the vertex “1”.
                                        if (str.contains("!" + order)) {


                                                // 4) If the current element contains !A , but there are characters of other levels in the string,
                                                // then we simply remove the “!A”, since !0 => 1.
                                                if (str.equals("!"+order))
                                                        return "1";

                                                str = str.replaceAll("!" + order, "");


                                                // 5) If the string contains the symbol of the current “A” level and the symbol of this level with a negation of !A -
                                                // example: !AAB => AB => 0 , because 0 * 1 or 0 * 0 = 0.
                                                if (str.contains(order))
                                                        continue;

                                                // 6) If the string does not contain characters of the current level, then we do not change anything in it C => C.
                                                else
                                                        result+= str + "+";
                                        }

                                        // 7)  If the negated element of the current level does not contain "!A",
                                        // but contains just a "А", then AB => 0.
                                        else if (str.contains(order) )
                                                continue;
                                        // 8) If the string does not contain characters of the current level,
                                                // then we do not change anything in it C => C.
                                        else
                                                result += str + "+";

                                }




                                buffer = new StringBuilder(result);

                                if(buffer.lastIndexOf("+") != -1)
                                        buffer.deleteCharAt(buffer.length()-1);

                                // If, after the reduction, there are no elements left in our function, then we return 0.
                                //For example: AB + AC + AD => 0;
                                if (result.length() == 0)
                                        return "0";

                                // DNF correction
                                if (result.length() > 1){

                                        result.replace("++", "+");

                                        buffer = new StringBuilder(result);
                                        buffer.deleteCharAt(buffer.length() - 1);
                                        result = new String(buffer);
                                }

                                return result;

                        }
                        else if (side == 1)
                        {
                                StringBuilder buffer = new StringBuilder();

                                //We split our function by pluses and get an array.
                                //For example: AB+C => [AB, C].

                                oneList = bfunction.split("\\+");
                                oneList = removeDuplicate(oneList);
                                String result = new String();
                                String buffer_string = new String();


                                //We begin to consider each element for the presence of symbols of the current level.

                                for (String str: oneList) {

                                        //If the current element contains A , and there are no characters in the string other than “A” ,
                                        // then we return “1” because our function will definitely get “1” as output.
                                        if (str.contains(order) && str.length() == 1)
                                                return "1";

                                        //If the current line has a negation in a character, then we delete that line.
                                        //For example: !ABC+C => C
                                        if (str.contains("!" + order))
                                                continue;


                                        //If the current element contains A , and there are no characters in the string other than “A” , then we return “1” because our function will definitely get “1” as output.
                                                //
                                                //For Example:
                                                //
                                                //A + ABCDEFGH + !ABCD  =  1 + 11100000+ 1000   or  1 + 0000000 + 1111  or
                                                //
                                                //1 + 101010101 + 1010  =   1.
                                                //
                                                //Then no matter what we substitute, we will always get “1” at the output.
                                                // Then it does not make sense to create further vertices, then we will simply indicate the link to the vertex “1”.

                                        else if (str.contains(order)) {
                                                if (str.equals(order))
                                                        return "1";

                                                result += str.replaceAll(order, "") + "+";
                                        }
                                        //If the string does not contain characters of the current level, then we do not change anything in it C => C.
                                        else
                                                result += str + "+";
                                }

                                // If, after the reduction, there are no elements left in our function, then we return 0.
                                //For example: AB + AC + AD => 0
                                if (result.equals(""))
                                        return "0";

                                if (result.length() > 1){
                                        result.replace("++", "+");
                                        buffer = new StringBuilder(result);
                                        buffer.deleteCharAt(buffer.length() - 1);
                                        result = new String(buffer);
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


                Stack<Node> stack = new Stack<Node>();
                Node currentState = root;


                while (currentState != null || stack.size() > 0)
                {
                        while (currentState !=  null)
                        {
                                stack.push(currentState);
                                currentState = currentState.left;
                        }

                        currentState = stack.pop();

                        System.out.println(currentState.bfunction + " ");

                        currentState = currentState.right;
                }
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
