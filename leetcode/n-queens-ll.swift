// 9 / 9 test cases passed.
// Status: Accepted
// Runtime: 12 ms
// Memory Usage: 20.9 MB
// Your runtime beats 98.08 % of swift submissions

class Solution {
    
    func isPlaceSafe(row: Int, col: Int, nQueens: Int, sRows: [Bool], hDiags: [Bool], dDiags: [Bool]) -> Bool {
        if !(sRows[row] || hDiags[row+col] || dDiags[((row-col >= 0) ? (row-col) : (nQueens-row+col-1))]) {
            return true
        }
        return false
    }
    
    func backtrack(count: inout Int, col: Int, nQueens: Int, sRows: inout [Bool], hDiags: inout [Bool], dDiags: inout [Bool]) {
        for currentRow in 0..<nQueens {
            if isPlaceSafe(row: currentRow, col: col, nQueens: nQueens, sRows: sRows, hDiags: hDiags, dDiags: dDiags) {
                let dDiagIndex: Int = (currentRow-col >= 0) ? (currentRow-col) : (nQueens-currentRow+col-1)
                sRows[currentRow] = true
                hDiags[currentRow+col] = true
                dDiags[dDiagIndex] = true
                
                if (col+1 >= nQueens) {
                    count += 1
                } else {
                    backtrack(count: &count, col: col+1, nQueens: nQueens, sRows: &sRows, hDiags: &hDiags, dDiags: &dDiags)
                }
                
                sRows[currentRow] = false
                hDiags[currentRow+col] = false
                dDiags[dDiagIndex] = false
            }
        }
    }
    
    func totalNQueens(_ n: Int) -> Int {
        var selectedRows: [Bool] = Array(repeating: false, count: n)
        var hillDiagonals: [Bool] = Array(repeating: false, count: 2*n-1)
        var daleDiagonals: [Bool] = Array(repeating: false, count: 2*n-1)
        var count: Int = 0
        backtrack(count: &count, col: 0, nQueens: n, sRows: &selectedRows, hDiags: &hillDiagonals, dDiags: &daleDiagonals)
        return count
    }
}