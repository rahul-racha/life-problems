// Used a sliding window model and solved it in JS.

// space complexity: O(M + N)
// Time complexity: O(M + N) + mLogM + nLogN

// N = Number of fwd options
// M = Number of backward options

// Algorithm:
//     -- -- -- -- -- -- -- -- -
//     -use a two pointer approach, one starting from 0 index of fwd list and another from last index of bwdlist. -
//     Keep track of best sum achieved so far, compare and increment pointers accordingly. -
//     store results when you find a better best / max.

//     -- -
//     Note: this works
// for multiple combinations, but would not work
// if either the fwd or the backward path distances are duplicated.

function findRoute(fwdList, bwdList, max) {

    let fList = fwdList.sort(function (x, y) {
        if (x[1] < y[1]) {
            return -1;
        }
        if (x[1] > y[1]) {
            return 1;
        }
        return 0;
    });
    let bList = bwdList.sort(function (x, y) {
        if (x[1] < y[1]) {
            return -1;
        }
        if (x[1] > y[1]) {
            return 1;
        }
        return 0;
    });

    let fHash = new Map();;
    let bHash = new Map();
    for (let tuple of fList) {
        fHash.has(tuple[1]) || fHash.set(tuple[1], tuple[0]);
    }
    for (let tuple of bList) {
        bHash.has(tuple[1]) || bHash.set(tuple[1], tuple[0]);
    }

    fList = fList.map(tuple => tuple[1]);
    bList = bList.map(tuple => tuple[1]);
    console.log(fList);
    console.log(bList);
    let result = [];
    let left = 0;
    let right = bList.length - 1;
    let best = 0;

    while (left < fList.length && right >= 0) {
        let sum = fList[left] + bList[right];
        console.log(`left is ${left} , right is ${right}`);
        console.log(`sum is ${sum}, max is ${max}`);
        if (sum <= max) {
            if (sum > best) {
                result = [];
                result.push([fHash.get(fList[left]), bHash.get(bList[right])]);
                best = sum;
            } else if (sum === best) {
                result.push([fHash.get(fList[left]), bHash.get(bList[right])]);
            }
            left++;
        } else if (sum > max) {
            right--;
        }
    }
    return result.length === 0 ? [] : (result.length > 1 ? result : result[0]);
}

let f = [
    [4, 10000],
    [5, 9000],
    [6, 7000],
    [2, 5000],
    [3, 4000],
    [1, 3000]
];
let b = [
    [3, 4000],
    [2, 3000],
    [1, 2000],
    [4, 1000]
];
// let f = [[2,5000],[3,4000],[4,10000],[5,9000],[6,7000], [1,3000]];
// let b = [[1,2000],[2,3000],[3,4000],[4,1000]];
console.log(findRoute(f, b, 5000));