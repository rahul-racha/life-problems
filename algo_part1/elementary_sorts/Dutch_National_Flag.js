
class DNFsort {
    swap(arr, i, j) {
        var temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    sort(arr) {
        var start = 0;
        var middle = 0;
        var end = arr.length - 1;

        while (middle <= end) {
            if (arr[middle] === 0) {
                this.swap(arr, middle, start++);
            } else if (arr[middle] === 2) {
                this.swap(arr, middle, end--);
            } else if (arr[middle] === 1) {
                ++middle;
            } else {
                return;
            }
        }
    }
}

var dnf = new DNFsort();
var arr = [2, 0, 1, 2, 1, 1, 2, 0, 0, 1];
dnf.sort(arr);
console.log(arr);


