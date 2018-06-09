class Node 
{
    constructor(value) 
    {
        this.value = value;
        this.next = null;
    }
}

class LinkedList 
{
    constructor()
    {
        this.head = null;
        this.length = 0;
    }

    insert(val)
    {
        if (val == null)
        {
            return;
        } else
        {
            var node = new Node(val);
            if (this.head)
            {
                node.next = this.head;
            }
            this.head = node;
            this.length++;
        }
    }

    delete(val)
    {
        var curr = this.head;
        if (curr.value == val)
        {
            this.head = this.head.next;
            curr.next = null;
            return curr;
        } else
        {
            while (curr != null && curr.next)
            {
                var temp = curr.next;
                if (temp.value == val)
                {
                    curr.next = curr.next.next;
                    this.length--;
                    temp.next = null;
                    return temp;
                } else {
                    curr = temp;
                }
            }
        }
    }

    displayList()
    {
        var curr = this.head;
        console.log(curr.value);
        while (curr != null && curr.next) {
            var nxt = curr.next;
            console.log(nxt.value);
            curr = nxt;
        }
        console.log("\n");
    }
}

ll = new LinkedList();
n = parseInt(prompt("How many elements?"));
while (n > 0)
{
    var x = parseInt(prompt("Enter the value:"));
    ll.insert(x);
    n--;
}

ll.displayList();

d = parseInt(prompt("Enter the element to delete:"));
ll.delete(d);
ll.displayList();
d = parseInt(prompt("Enter the element to delete:"));
ll.delete(d);
ll.displayList();