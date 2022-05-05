public class DynamicArray {
    private Node[] array = new Node[]{};
    private long[] arrayLong = new long[]{};

    public DynamicArray() {
    }

    public void add(Node node){
        Node[] newArray = new Node[array.length +1];

        for (int i = 0; i < array.length; i++)
            newArray[i] = array[i];

        newArray[newArray.length - 1] = node;

        this.array = newArray;
    }
    public void add(Long node){
        long[] newArray = new long[array.length +1];

        for (int i = 0; i < arrayLong.length; i++)
            newArray[i] = arrayLong[i];

        newArray[newArray.length - 1] = node;

        this.arrayLong = newArray;
    }

    public int size(){
        return array.length;
    }

    public long[] getArrayLong(){
        return arrayLong;
    }


    public Node get(int i) {
        return array[i];
    }
}
