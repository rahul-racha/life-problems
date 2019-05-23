let que = {
    H: 0,
    T: 0,
    N: 0,
    capacity: 10,
    list: [],
    enqueue: function(item) {
        if (this.N < this.capacity) { 
            this.list[(this.T++)%this.capacity] = item;
            ++this.N;
            return true;
        }
        return false;
    },
    dequeue: function() {
        if (this.N > 0) {
            let temp = this.list[this.H];
            this.list[(this.H++) % this.capacity] = null;
            --this.N;
            return temp;
        }
        return null;
    }
    
};

for (let i = 1; i <= 10; i++) {
    que.enqueue(i);
}
console.log(que.list);
for (let i = 1; i <= 5; i++) {
    que.dequeue();
}
console.log(que.list);
for (let i = 11; i <= 15; i++) {
    que.enqueue(i);
}
console.log(que.list);
console.log(que.N);
console.log(que.enqueue(16));
console.log(que.list);
que.dequeue();
que.dequeue();
que.enqueue(16);
que.enqueue(17);
console.log(que.list);
que.dequeue();
que.enqueue(18);
console.log(que.list);