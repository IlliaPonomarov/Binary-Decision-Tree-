
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


        public BinaryDecisionDiagram(){

                this.root = null;

        }

        public int BDD_use(String way){

            Node current = root;

            if (way.length() != this.order.length()) {
                    System.err.println("Order and Way should be identical.");
                    return -1;
            }

                for (Character number: way.toCharArray()) {


                        if (number.equals('0'))
                                current = current.left;

                        else if (number.equals('1'))
                                current = current.right;

                        if (current == zero) {
                                System.out.println("Result:" + current.bfunction);
                                return 0;
                        }
                        else if (current == one) {
                                System.out.println("Result:" + current.bfunction);
                                return 1;
                        }


                }


            return -1;
        }

        public Node createLeftNode(Node root, HashTable hashTable, String bfunction, String order){

                if (order.length() > 1)
                        this.left_bfunction = getDNF(bfunction, Character.toString(order.charAt(0)), 0);

                if (order.length() > 1)
                        root.left = hashTable.insert(random_id, new Node(this.left_bfunction, Character.toString(order.charAt(1))));
                else if (order.length() == 1)
                        root.left = hashTable.insert(random_id, new Node(this.left_bfunction, Character.toString(order.charAt(0))));



                if (root.left.bfunction.equals("0") ) {
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

                if (order.length() >= 1)
                        this.right_bfunction = getDNF(bfunction, Character.toString(order.charAt(0)), 1);

                if (order.length() > 1)
                        root.right = hashTable.insert(random_id, new Node(this.right_bfunction, Character.toString(order.charAt(1))));
                else if (order.length() == 1)
                        root.right = hashTable.insert(random_id, new Node(this.right_bfunction, Character.toString(order.charAt(0))));

                if (root.right.bfunction.equals("0")) {
                        root.right = zero;
                        return root.right;
                }
                else if (root.right.bfunction.equals("1")  || (root.right.bfunction.equals(order) &&  root.right.bfunction.length() == 1)) {
                        root.right = one;
                        return root.right;
                }

                return root.right;
        }

        public void BDD_create(String bfunction, String order) {
                this.order = order;
                StringBuilder buffer_order = new StringBuilder(order);
                DynamicArray currentStates = new DynamicArray();
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
                DynamicArray buffer = new DynamicArray();
                hashTable = new HashTable(maxSize);




                for (int i = 0; i < currentStates.size(); i++) {


                        if ((currentStates.get(i).bfunction.length() == 1  && currentStates.get(i).bfunction.equals(order))){
                                currentStates.get(i).left = zero;
                                currentStates.get(i).right = one;
                        }


                        if (!(currentStates.get(i).bfunction.equals("0")) && !(currentStates.get(i).bfunction.equals("1")) && (currentStates.get(i).left == null && currentStates.get(i).right == null)) {
                                currentStates.get(i).left = createLeftNode(currentStates.get(i), hashTable, currentStates.get(i).bfunction, order);
                                currentStates.get(i).right = createRightNode(currentStates.get(i), hashTable, currentStates.get(i).bfunction, order);
                                buffer.add(currentStates.get(i).left);
                                buffer.add(currentStates.get(i).right);
                        }

//                        if (   (currentStates.get(i).left.bfunction.equals("0") ||  currentStates.get(i).left.bfunction.equals("1"))
//                                && (!(currentStates.get(i).right.bfunction.equals("0") ||  currentStates.get(i).right.bfunction.equals("1"))))
//                                buffer.add(currentStates.get(i).right);
//
//                        if (   (currentStates.get(i).right.bfunction.equals("0") ||  currentStates.get(i).right.bfunction.equals("1"))
//                                && !(currentStates.get(i).left.bfunction.equals("0") ||  currentStates.get(i).left.bfunction.equals("1")))
//                                buffer.add(currentStates.get(i).left);
//
//                        if ( !(currentStates.get(i).right.bfunction.equals("0") ||  currentStates.get(i).right.bfunction.equals("1"))
//                                && !(currentStates.get(i).left.bfunction.equals("0") ||  currentStates.get(i).left.bfunction.equals("1")))
//                        {
//                                buffer.add(currentStates.get(i).left);
//                                buffer.add(currentStates.get(i).right);
//                        }


                        if (i == currentStates.size() - 1) {
                                currentStates = buffer;
                                hashTable = new HashTable(maxSize);
                                i = 0;

                                if (order_builder.length() >= 1)
                                        order_builder.deleteCharAt(0);

                                if (order_builder.length() == 0)
                                        break;
                        }

                        if (order_builder.equals(""))
                                break;
                        order = new String(order_builder);
                }


                return;

        }



//        public static String getDNF(String bfunction, String order, int side){
//                int index = 0;
//                String[] zeroList = new String[]{};
//                String[] oneList = new String[]{};
//
//
//                if (order.length() > 1 )
//                        return "Error";
//                else
//                {
//                        if (side == 0) {
//                                StringBuilder buffer = new StringBuilder();
//                                zeroList = bfunction.split("\\+");
//                                zeroList = removeDuplicate(zeroList);
//                                String result = new String();
//                                String buffer_string = new String();
//
//
//                                for (String str: zeroList) {
//                                        if (str.contains("!"+order)  && str.length() == 2) {
//                                                result = "1";
//                                        }
//
//                                        if (str.contains("!"+order)) {
//                                                buffer = new StringBuilder(str);
//                                                int indexOf = buffer.indexOf("!");
//                                                buffer.deleteCharAt(indexOf);
//                                                indexOf = buffer.indexOf(order);
//                                                buffer.deleteCharAt(indexOf);
//                                                result+=buffer + "+";
//                                        }
//
//                                        if (str.contains(order)){
//                                                result+="0";
//                                        }
//
//                                        if (!str.contains(order)) {
//                                                buffer_string = new String(str);
//                                                result += removeDuplicate(buffer_string.toCharArray()) + "+";
//                                        }
//                                }
//
//
//                                if (result.length() > 1){
//
//                                        result = removeDuplicate(result.toCharArray());
//                                        buffer = new StringBuilder(result);
//
//
//                                        for (int i = 0; i < buffer.length(); i++) {
//                                                if (buffer.charAt(i) == '0') {
//                                                        buffer.deleteCharAt(i);
//                                                        i = 0;
//                                                }
//                                        }
//                                        result=new String(buffer);
//                                }
//
//                                StringBuilder n = new StringBuilder(result);
//                                n.deleteCharAt(n.length()-1);
//                                result = new String(n);
//
//                                if ((result.length() == 0) || (result.length() == 1 && result.contains(order)))
//                                        result = "0";
//
//                                return result;
//                        }
//                        if (side == 1){
//                                        StringBuilder buffer = new StringBuilder();
//                                        oneList = bfunction.split("\\+");
//                                        String result = new String();
//                                        oneList = removeDuplicate(oneList);
//                                        String buffer_string = new String();
//
//                                        for (int i = 0; i < oneList.length; i++) {
//                                                buffer = new StringBuilder(oneList[i]);
//
//                                                int indexOf = -1;
//
//                                                if (oneList[i].contains("!"+order))
//                                                        result = "0";
//
//                                                // string without order
//                                                if (!oneList[i].contains("!") && !oneList[i].contains(order)) {
//
//                                                        buffer_string = new String(buffer);
//
//                                                        result += removeDuplicate(buffer_string.toCharArray()) + "+";
//                                                }
//
//                                                if (!oneList[i].contains("!") && oneList[i].contains(order)){
//                                                        indexOf = buffer.indexOf(order);
//                                                        buffer.deleteCharAt(indexOf);
//                                                        buffer_string = new String(buffer);
//
//                                                        result += removeDuplicate(buffer_string.toCharArray()) + "+";
//
//                                                }
//
//                                                if (oneList[i].contains("!") && !oneList[i].contains("!" + order)){
//                                                        indexOf = buffer.indexOf(order);
//                                                        buffer.deleteCharAt(indexOf);
//
//                                                        buffer_string = new String(buffer);
//
//                                                        result += removeDuplicate(buffer_string.toCharArray()) + "+";
//                                                }
//
//                                                if (oneList[i].contains(order) && oneList[i].length() == 1)
//                                                        return "1";
//
//
//                                        }
//
//                                        StringBuilder n = new StringBuilder(result);
//
//                                        if (n.length() > 0) {
//                                                n.deleteCharAt(n.length() - 1);
//                                                result = new String(n);
//                                        }
//                                        return result;
//
//
//                        }
//                }
//
//                return null;
//        }

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
                                zeroList = bfunction.split("\\+");
                                zeroList = removeDuplicate(zeroList);
                                String result = new String();
                                String buffer_string = new String();

                                for (String str: zeroList) {

                                        if (str.contains("!"+order) && str.length() == 2)
                                                return "1";

                                        if (str.contains("!" + order)) {
                                                str = str.replaceAll("!" + order, "") + "+";
                                                if (str.contains(order))
                                                        continue;
                                                else
                                                        result+= str + "+";
                                        }
                                        else if (str.contains(order) )
                                                continue;
                                        else
                                                result += str + "+";

                                }

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
                        else if (side == 1)
                        {
                                StringBuilder buffer = new StringBuilder();
                                oneList = bfunction.split("\\+");
                                oneList = removeDuplicate(oneList);
                                String result = new String();
                                String buffer_string = new String();

                                for (String str: oneList) {

                                        if (str.contains(order) && str.length() == 1)
                                                return "1";

                                        if (str.contains("!" + order))
                                                continue;

                                        else if (str.contains(order))
                                                result +=  str.replaceAll(order, "") + "+";
                                        else
                                                result += str + "+";
                                }

                                if (result.equals(""))
                                        return "1";

                                if (result.length() > 1){
                                        result.replace("++", "+");
                                        buffer = new StringBuilder(result);
                                        buffer.deleteCharAt(buffer.length() - 1);
                                        result = new String(buffer);
                                }

                                return result;

                        }
//                        if (side == 0) {
//
//                                if (bfunction.length() == 1 && bfunction.contains(order))
//                                        return "0";
//
//                                StringBuilder buffer = new StringBuilder();
//                                zeroList = bfunction.split("\\+");
//                                zeroList = removeDuplicate(zeroList);
//                                String result = new String();
//                                String buffer_string = new String();
//
//
//                                for (String str: zeroList) {
//                                        if (str.contains("!"+order)) {
//                                                result.replace("!"+order, "");
//                                        }
//
//                                        if (str.contains(order)){
//
//                                                result+="0";
//                                        }
//
//                                        if (!str.contains(order)) {
//                                                buffer_string = new String(str);
//                                                result += removeDuplicate(buffer_string.toCharArray()) + "+";
//                                        }
//                                }
//
//                                if (result.length() == 1 && result.contains("0"))
//                                        return "0";
//
//                                if (result.length() > 1){
//
//                                        if (bfunction.length() == 1 && bfunction.contains(order))
//                                                return "1";
//
//                                        buffer = new StringBuilder(result);
//                                        if (buffer.indexOf("0") != -1 && buffer.length() > 1) {
//                                                for (int i = 0; i < buffer.length(); ) {
//                                                        if (buffer.charAt(i) == '0') {
//                                                                buffer.deleteCharAt(i);
//                                                                i--;
//                                                        }
//                                                        i++;
//                                                        if (buffer.length() == 1 && buffer.indexOf("0") != -1)
//                                                                return "0";
//
//                                                }
//                                        }
//
//                                        result=new String(buffer);
//                                }
//
//                                if ((result.length() == 0) || (result.length() == 1 && result.contains(order)))
//                                        return  "0";
//
//
//                                StringBuilder n = new StringBuilder(result);
//                                n.deleteCharAt(n.length()-1);
//                                result = new String(n);
//
//                                return result;
//                        }
//                        if (side == 1){
//                                StringBuilder buffer = new StringBuilder();
//                                oneList = bfunction.split("\\+");
//                                String result = new String();
//                                oneList = removeDuplicate(oneList);
//                                String buffer_string = new String();
//
//                                for (int i = 0; i < oneList.length; i++)
//                                        if (!oneList[i].contains("!") && oneList[i].contains(order) && oneList[i].length() == 1)
//                                                return "1";
//
//                                for (int i = 0; i < oneList.length; i++) {
//                                        buffer = new StringBuilder(oneList[i]);
//
//                                        int indexOf = -1;
//
//                                        if (oneList[i].contains("!"+order))
//                                                result = "0";
//
//                                        // string without order
//                                        if (!oneList[i].contains("!"+order) && !oneList[i].contains(order)) {
//
//                                                buffer_string = new String(buffer);
//
//                                                result += removeDuplicate(buffer_string.toCharArray()) + "+";
//                                        }
//
//                                        // String with order, without !order
//                                        if (!oneList[i].contains("!"+order) && oneList[i].contains(order) && oneList[i].length() > 1){
//                                                indexOf = buffer.indexOf(order);
//                                                buffer = new StringBuilder(removeDuplicate(new String(buffer).toCharArray()));
//                                                buffer.deleteCharAt(indexOf);
//                                                buffer_string = new String(buffer);
//
//                                                if (buffer_string.equals(""))
//                                                        result += removeDuplicate(buffer_string.toCharArray());
//                                                else
//                                                        result += removeDuplicate(buffer_string.toCharArray()) + "+";
//
//                                        }
//
//
//                                        // single order
//                                        if (!oneList[i].contains("!") && oneList[i].contains(order) && oneList[i].length() == 1)
//                                                return "1";
//
//                                        //
//                                        if (oneList[i].contains(order) && !oneList[i].contains("!" + order)){
//                                                indexOf = buffer.indexOf(order);
//                                                buffer.deleteCharAt(indexOf);
//
//                                                buffer_string = new String(buffer);
//
//                                                result += removeDuplicate(buffer_string.toCharArray()) + "+";
//                                        }
//
//                                        if (oneList[i].contains(order) && oneList[i].length() == 1)
//                                                result = "1";
//
//
//                                }
//
//                                StringBuilder n = new StringBuilder(result);
//
//                                if (n.length() > 0) {
//                                        n.deleteCharAt(n.length() - 1);
//                                        result = new String(n);
//                                }
//                                return result;
//
//
//                        }



//
//                        if (side == 1){
//
//                                if (bfunction.length() == 1 && bfunction.contains(order))
//                                        return "1";
//
//                                StringBuilder buffer = new StringBuilder();
//                                oneList = bfunction.split("\\+");
//                                oneList = removeDuplicate(zeroList);
//                                String result = new String();
//                                String buffer_string = new String();
//
//
//                                for (String str: oneList) {
//                                        if (str.contains("!"+order)) {
//                                                result += "0";
//                                        }
//
//                                       else if (str.contains(order)){
//
//                                                buffer = new StringBuilder(new String(str));
//                                                buffer.deleteCharAt(buffer.indexOf(order));
//                                                result+="";
//
//                                        }
//
//                                        if (!str.contains(order)) {
//                                                buffer_string = new String(str);
//                                                result += removeDuplicate(buffer_string.toCharArray()) + "+";
//                                        }
//                                }
//
//                                if (result.length() == 1 && result.contains("1"))
//                                        return "1";
//
//                                if (result.length() > 1){
//
//                                        if (bfunction.length() == 1 && bfunction.contains(order))
//                                                return "1";
//
//                                        buffer = new StringBuilder(result);
//                                        if (buffer.indexOf("0") != -1 && buffer.length() > 1) {
//                                                for (int i = 0; i < buffer.length(); ) {
//                                                        if (buffer.charAt(i) == '1' || buffer.charAt(i) == '0') {
//                                                                buffer.deleteCharAt(i);
//                                                                i--;
//                                                        }
//                                                        i++;
//                                                        if (buffer.length() == 1 && buffer.indexOf("1") != -1)
//                                                                return "1";
//
//                                                }
//                                        }
//
//                                        result=new String(buffer);
//                                }
//
//                                if ((result.length() == 0) || (result.length() == 1 && result.contains(order)))
//                                        return  "1";
//
//
//                                StringBuilder n = new StringBuilder(result);
//
//                                if (n.length() > 1) {
//                                        n.deleteCharAt(n.length() - 1);
//                                        result = new String(n);
//                                }
//
//                                return result;
//
//                        }
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

                String string = String.valueOf(Arrays.copyOf(str, index));


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
