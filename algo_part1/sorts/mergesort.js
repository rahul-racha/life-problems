class Mergesort {
    constructor() { this.aux = new Array(); }

    merge(arr, low, mid, high) {
        var aux = arr.slice(0, high+1);
        var first = low;
        var second = mid+1;
        for (var i = low; i <= high; i++) {
            if (first > mid) { arr[i] = aux[second++]; }
            else if (second > high) {
                arr[i] = aux[first++];
            }
            else if (aux[first] <= aux[second]) { arr[i] = aux[first++]; }
            else { arr[i] = aux[second++]; }
        }
    }

    sort(arr) {
        for (var i = 1; i < arr.length; i += i) {
            for (var j = 0; j < arr.length - i; j += 2*i) {
                this.merge(arr, j, (j+i)-1, Math.min(j+(2*i)-1, arr.length-1));        
            }
        }     
    }
}

var mergesort = new Mergesort();
var arr = [9,8,7,6];
mergesort.sort(arr);
console.log(arr);