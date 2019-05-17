// Time Complexity = O(nc)
// where n = the number of items
//       c = the remaining capacities available

let weight = [undefined, 1, 2, 3, 4, 5];
let value = [undefined, 5, 3, 5, 3, 2];
const noOfitems = weight.length - 1;
const noOfcapacity = 10
let storage = [];

let initStorage = function(rows, cols) {
    storage.push(undefined);
    for (let i = 1; i < rows; i++) {
        let temp = [];
        for (let j = 1; j < cols; j++) {
            temp.push(null);
        }
        storage.push(temp);
    }
}

let ks = function(n, c) {
    let result = 0;
    if (0 == n || 0 == c) {
        result = 0;
    } else if (storage[n][c]) {
        console.log('n:' + n + ', c:' + c);
        return storage[n][c];
    } else if (weight[n] > c) {
        result = ks(n-1, c);
    } else {
        let temp1 = ks(n-1, c);
        let temp2 = value[n] + ks(n-1, c-weight[n]);
        result = Math.max(temp1, temp2);
    }
    if (n > 0 && c > 0) {
        storage[n][c] = result;
    }
    return result;
}

initStorage(noOfitems + 1, noOfcapacity + 1);
console.log(ks(noOfitems, noOfcapacity));
console.log(storage);