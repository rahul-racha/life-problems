// Testcase
// [1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1]
// [5,6,4]

// Fatal error: Double value cannot be converted to Int because the result would be greater than Int.max

class Solution {
    func addTwoNumbers(_ l1: ListNode?, _ l2: ListNode?) -> ListNode? {
        var a: Int = 0
        var b: Int = 0
        var result: Int = 0
        
        var list1: ListNode? = l1
        var list2: ListNode? = l2
        var number: Int = 0
        while list1 != nil {
            a += Int(pow(10.0, Double(number)))*list1!.val
            number += 1
            list1 = list1?.next
        }
        number = 0
        while list2 != nil {
            b += Int(pow(10.0, Double(number)))*list2!.val
            number += 1
            list2 = list2?.next
        }
        result = a + b
        var root: ListNode? = nil
        var prevNode: ListNode = ListNode(-1)
        while result != 0 {
            var currentDigit = result%10
            result = result/10
            var node: ListNode = ListNode(currentDigit)
            if (root == nil) {
                root = node
                prevNode = node
            } else {
               prevNode.next = node
               prevNode = node
            }
        }
        return root
    }
}