class Solution {
    func reverse(_ x: Int) -> Int {
        var reversedNumber: Int = 0
        var number = x 
        while (number !=  0) {
            let pop = number % 10
            if (
                number > 0 && (Int.max/10 > reversedNumber ||
                              (Int.max/10 == reversedNumber && pop <= 7))
               ) ||
               (
                number < 0 && (Int.min/10 < reversedNumber) || 
                              (Int.min/10 == reversedNumber && pop <= -8)
               ) {
              reversedNumber = (reversedNumber * 10) + pop
            } else {
                return 0
            }
            number = number / 10
        }
        return reversedNumber
    }
}