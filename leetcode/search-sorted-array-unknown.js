let search = function (reader, target) {
    let high = 1;
    while (reader.get(high) < target) {
        high = high * 2;
    }
    let low = Math.floor(high / 2);

    while (low <= high) {
        let mid = low + Math.round((high - low) / 2);

        if (reader.get(mid) === target) {
            return mid;
        } else if (reader.get(mid) > target) {
            high = mid - 1;
        } else {
            low = mid + 1;
        }
    }
    return -1;
};