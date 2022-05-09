import java.util.Arrays;


public class HashTable {

    private int capacity;
    public final int p = 31, m = 1000000007;
    private static int size = 0;
    private double loadFactor;

    public static int count = 2;

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



    public  int hashFunction(String value){
        return hashCode(value) & capacity;
    }


    //To get the hash code, I used the algorithm of summing products over the entire text of the string.
    // For example, if an instance s of class java.lang.String would have a hash code h(s) defined
    //
    //h(s)=s[0]*31^(n-1) + s[1]*31^(n-2) + ... + s[n-1]
    //
    //where terms are summed using Java 32-bit integer addition,
    // s[i] denotes the i-th character of the string, and n is the length of s.
    public  int hashCode(String value){
        int hash_so_far = 0;
        final char[] c_string = value.toCharArray();

        long p_pow = 1;

        //s[0]*31^(n - 1) + s[1]*31^(n - 2) + ... + s[n - 1]
        for (int i = 0; i < value.length(); i++) {
            hash_so_far = (int) ((hash_so_far + (c_string[i] - 'a' + 1) * p_pow) % m);
            p_pow = (31 * p_pow) % m;
        }

        return hash_so_far;
    }




    public Node insert(int key, Node node){

        // We calculate the hash function for a regular string.
        int index = hashFunction(node.bfunction);

        if (index == hashtable.length)
            resize();

        HTObject  htObjectNormal = hashtable[index];


        //If there is no collision,
        // then we create a new node and add it to the hash table, and return a reference to it.
        if (htObjectNormal == null) {
            hashtable[index] = new HTObject(key, new Node(node.bfunction, node.order));
            Main.MEMORY++;
            if (index >= hashtable.length - 1 )
                resize();


            return hashtable[index].value;
        }

        if ( (htObjectNormal != null)) {

            if (!htObjectNormal.value.bfunction.equals(node.bfunction)) {

                //There is a collision:
                //2) If the elements are different, then we will go through the table until we find an empty cell.
                for (int i = index; i < hashtable.length; i++) {
                    if (hashtable[i] == null) {
                        hashtable[i] = new HTObject(key, new Node(node.bfunction, node.order));
                        Main.MEMORY++;
                        if (i >= hashtable.length - 1)
                            resize();;

                        return hashtable[i].getValue();
                    }
                }
            }

            //There is a collision:
            //1) If the elements are the same, then we return a link to the top in the hash table.

            return hashtable[index].value;
        }



        ///If loadFactory is greater than 0.75, then the resize() function is called,
        // which doubles the size of the table.
            size++;

            double loadfactory = (1.0 * size) / capacity;

            if (loadfactory > this.loadFactor)
                resize();



      return null;
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
