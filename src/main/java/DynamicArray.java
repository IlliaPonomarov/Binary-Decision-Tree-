public class DynamicArray {
    private Node[] array = new Node[]{};

    public DynamicArray() {
    }

    public void add(Node node){
        Node[] newArray = new Node[array.length +1];

        for (int i = 0; i < array.length; i++)
            newArray[i] = array[i];

        newArray[newArray.length - 1] = node;

        this.array = newArray;
    }

    public int size(){
        return array.length;
    }

    public Node get(int i) {
        return array[i];
    }
}
