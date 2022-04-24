import java.util.Arrays;


public class HashTable {

    private int capacity;
    public final int p = 31, m = 1000000007;
    private int size = 0;
    private double loadFactor;

    private HTObject[] hashtable;


    class HTObject{

        private int key;
        private Node value;

        public HTObject(int key, Node value) {
            this.key = key;
            this.value = value;
        }

        public int getKey() {
            return key;
        }

        public Node getValue() {
            return value;
        }
    }


    public HashTable(int capacity) {
        this.capacity = capacity;
        this.loadFactor = 0.75;

        hashtable = new HTObject[capacity];
        Arrays.stream(hashtable).forEach(index -> index = null);
    }

    public Node insert(int key, Node node){
        int index = hashFunction(node.bfunction);
        int indexForReverse = hashFunction(reverseString(node.bfunction));

        HTObject  htObjectNormal = hashtable[index];
        HTObject  htObjectReverse = hashtable[indexForReverse];


        if (htObjectNormal == null && htObjectReverse == null) {
            hashtable[index] = new HTObject(key, new Node(node.bfunction, node.order));
            return hashtable[index].value;
        }

        else if ( (htObjectNormal != null || htObjectReverse != null)) {

            if (!htObjectNormal.value.bfunction.equals(node.bfunction) || !htObjectReverse.value.bfunction.equals(node.bfunction)) {
                for (int i = 0; i < hashtable.length; i++) {
                    if (hashtable[i] == null) {
                        hashtable[i] = new HTObject(key, new Node(node.bfunction, node.order));
                        return hashtable[i].getValue();
                    }
                }
            }
            return htObjectNormal.value;
        }


        size++;

        double loadfactory = (1.0 * size) / capacity;

        if (loadfactory > this.loadFactor)
            resize();

        return null;
    }

    public  int hashFunction(String value){
        return hashCode(value) & capacity;
    }

    public  int hashCode(String value){
      int hash_so_far = 0;
      final char[] c_string = value.toCharArray();

      long p_pow = 1;

        for (int i = 0; i < value.length(); i++) {
            hash_so_far = (int) ((hash_so_far + (c_string[i] - 'a' + 1) * p_pow) % m);
            p_pow = (p * p_pow) % m;
        }

        return hash_so_far;
    }

    public String reverseString(String str){
        char ch;
        String reverse = new String();

        for (int i = 0; i < str.length(); i++) {
            ch = str.charAt(i);
            reverse = ch + reverse;
        }

        return reverse;
    }

    public void resize() {


        HTObject[] newtable = new HTObject[hashtable.length * 2];
        for (int i = 0; i < hashtable.length; i++) {

            HTObject list = hashtable[i];
            newtable[i] = list;
        }


        hashtable = newtable;

        if (hashtable.length > 10000000){
            this.capacity = 1000000;
            resize();
        }

    }


}
