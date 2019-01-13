class Quicksort {
    swap(arr, i, j) {
        var temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    sort(arr, low, high) {
        if (high <= low) { return; }
        var lt = low;
        var i = low;
        var gt = high;
        var index = Math.floor((Math.random() * (high - low + 1))) + low;
        this.swap(arr, low, index);
        var pivot = arr[low];
        while (i <= gt) {
            if (pivot > arr[i]) {
                this.swap(arr, lt++, i++);
            } else if (pivot < arr[i]) {
                this.swap(arr, i, gt--);
            } else {
                i++;
            }
        }
        this.sort(arr, low, lt - 1);
        this.sort(arr, gt + 1, high);
    }
}

var quicksort = new Quicksort();
var arr = [10, 10, 8, 9, 7, 2, 3, 3, 8, 6];
quicksort.sort(arr, 0, arr.length - 1);
console.log(arr);