// Runtime: 52 ms, faster than 86.51 % of JavaScript online submissions
// for Binary Search Tree to Greater Sum Tree.
// Memory Usage: 33.9 MB, less than 100.00 % of JavaScript online submissions
// for Binary Search Tree to Greater Sum Tree.

/**
 * Definition for a binary tree node.
 * function TreeNode(val) {
 *     this.val = val;
 *     this.left = this.right = null;
 * }
 */
/**
 * @param {TreeNode} root
 * @return {TreeNode}
 */

let visitRightOnLeft = function (node, value) {
    if (!node.right && !node.left) {
        node.val = node.val + value;
    }

    if (node.right) {
        let sum = visitRightOnLeft(node.right, value);
        node.val = node.val + sum;
    }

    if (!node.right && node.left) {
        node.val = node.val + value;
    }

    if (node.left) {
        return visitRightOnLeft(node.left, node.val);
    }

    return node.val;
};

let visitRight = function (node) {
    let result = node.val;
    if (node.right) {
        let sum = visitRight(node.right);
        node.val = node.val + sum;
        result = node.val;
    }
    if (node.left) {
        result = visitRightOnLeft(node.left, node.val);
    }
    return result;
};

var bstToGst = function (root) {
    let node = root;
    visitRight(node);
    return node;
};
