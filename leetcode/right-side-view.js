// Runtime: 44 ms, faster than 100.00 % of JavaScript online submissions
// for Binary Tree Right Side View.
// Memory Usage: 34.1 MB, less than 79.95 % of JavaScript online submissions
// for Binary Tree Right Side View.

/**
 * Definition for a binary tree node.
 * function TreeNode(val) {
 *     this.val = val;
 *     this.left = this.right = null;
 * }
 */
/**
 * @param {TreeNode} root
 * @return {number[]}
 */
var rightSideView = function (root) {
    let numbers = [];
    if (root) {
        let frontier = [root];
        let rightViewNode = null;
        while (frontier.length > 0) {
            let next = [];
            for (let i = 0; i < frontier.length; i++) {
                let node = frontier[i];
                rightViewNode = node;
                if (node.left) {
                    next.push(node.left);
                }
                if (node.right) {
                    next.push(node.right);
                }
            }
            numbers.push(rightViewNode.val);
            frontier = next;
        }
    }
    return numbers;
};
