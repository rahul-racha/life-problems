class KLargest {
    klargest(arr1, start1, end1, arr2, start2, end2, k) {
        var length1 = end1 - start1 + 1;
        var length2 = end2 - start2 + 1;
        if (0 >= length1) {
            return arr2[k];
        }
        if (0 >= length2) {
            return arr1[k];
        }
        var mid1 = length1/2;
        var mid2 = length2/2;

        if (mid1 + mid2 < k) {
            if (arr1[mid1] > arr2[mid2]) {
                return this.klargest(arr1, start1, end1, arr2, mid2 + 1, end2, k - (mid2 + 1));
            } else {
                return this.klargest(arr1, mid1 + 1, end1, arr2, start2, end2, k - (mid1 + 1));
            }
        } else {
            if (arr1[mid1] > arr2[mid2]) {
                return this.klargest(arr1, start1, mid1, arr2, start2, end2, k);
            } else {
                return this.klargest(arr1, start1, end1, arr2, start2, mid2, k);
            }
        }
    }
}

var array1 = [2,4,6,8,10];
var array2 = [1,3,5,7,9];
// find Kth element
var klarge = new KLargest();
console.log(klarge.klargest(array1, 0, array1.length-1, array2, 0, array2.length-1, 1));