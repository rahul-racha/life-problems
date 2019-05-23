// Time Limit Exceeded
// Last executed input
// ["KthLargest","add","add","add","add","add","add","add","add"]
// [[3,[1,1]],[1],[1],[3],[3],[3],[4],[4],[4]]

class KthLargest {

    int k;
    ArrayList<Integer> nums;
    Node root;
    
    class Node {
        int value;
        Node left;
        Node right;
    }
    
    public void printRoot() {
        System.out.println("#####");
        Node temp = this.root;
        while(temp != null) {
            System.out.println(temp.value);
            temp = temp.right;
        }
        System.out.println("#####");
    }
    
    private void initProperties(int[] nums) {
        for (int item: nums) {
            this.nums.add(item);
        }
        heapify();

        int counter = this.nums.size() - 1;
        while (counter >= this.nums.size() - this.k && counter != 0) {
            Collections.swap(this.nums, counter, 1);
            sink(1, --counter);
        }
        
        this.root = new Node();
        this.root.value = this.nums.get(this.nums.size() - this.k);
        this.root.left = null;
        Node prevNode = this.root;
        for (int i = (this.nums.size() - this.k) + 1; i < this.nums.size(); i++) {
            Node node = new Node();
            node.value = this.nums.get(i);
            node.left = prevNode;
            prevNode.right = node;
            prevNode = node;
        }
        prevNode.right = null;
    }
    
    public KthLargest(int k, int[] nums) {
        this.k = k;
        this.nums = new ArrayList<Integer>();
        this.nums.add(null);
        if (nums.length > 0 && k <= nums.length) {
            initProperties(nums);
        } else {
            for (int item: nums) {
                this.nums.add(item);
            }
            this.root = null;
        }
    }
    
    public int add(int val) {
        if (this.root != null && val < this.root.value) {
            return this.root.value;
        }
        if (this.root == null) {
            initProperties(new int[]{val});
            return this.root.value;
        }
        Node node = this.root;
        Node prevNode = this.root;
        while (node != null) {
            if (val > node.value) {
                prevNode = node;
            } else {
                Node newNode = new Node();
                newNode.value = val;
                newNode.right = node;
                node.left = newNode;
                newNode.left = prevNode;
                prevNode.right = newNode;
                break;
            }
            node = node.right;
        }
        if (node == null) {
            Node newNode = new Node();
            newNode.value = val;
            newNode.right = null;
            newNode.left = prevNode;
            prevNode.right = newNode;
        }
        Node temp = this.root;
        this.root = temp.right;
        return this.root.value;
    }
    
    private void heapify() {
        for (int i = this.nums.size()/2; i > 0; i--) {
            sink(i, this.nums.size()-1);
        }
    }
    
    private void sink(int index, int lastIndex) {
        while (index >= 1 && 2*index <= lastIndex) {
            int j = 2*index;
            if (j+1 <= lastIndex && this.nums.get(j+1) > this.nums.get(j)) {
                j++;
            }
            if (this.nums.get(index) > this.nums.get(j)) {
                break;
            }
            Collections.swap(this.nums, index, j);
            index = j;
        }
    }
               
    private void swim(int index) {
        while (index/2 >= 1) {
            if (this.nums.get(index/2) < this.nums.get(index)) {
                Collections.swap(this.nums, index/2, index);
            }
            index = index/2;
        }
    } 
}

/**
 * Your KthLargest object will be instantiated and called as such:
 * KthLargest obj = new KthLargest(k, nums);
 * int param_1 = obj.add(val);
 */