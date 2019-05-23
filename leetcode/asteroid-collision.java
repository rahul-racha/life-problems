// Runtime: 14 ms, faster than 56.42% of Java online submissions for Asteroid Collision.
// Memory Usage: 37.8 MB, less than 97.96% of Java online submissions for Asteroid Collision.

class Solution {
    Stack < Integer > stack;
    public int[] asteroidCollision(int[] asteroids) {
        if (asteroids.length < 1) {
            return new int[0];
        }
        stack = new Stack();
        stack.push(asteroids[0]);
        for (int i = 1; i < asteroids.length; i++) {
            if (handleCollision(asteroids[i])) {
                stack.push(asteroids[i]);
            }
        }
        int array[] = new int[stack.size()];
        Object[] arr = stack.toArray();
        for (int i = 0; i < arr.length; i++) {
            array[i] = (int) arr[i];
        }
        return array;
    }

    private boolean handleCollision(int asteroid) {
        while (!stack.empty() && !isStable(asteroid, stack.peek())) {
            if (asteroid + stack.peek() == 0) {
                stack.pop();
                return false;
            }
            if (Math.abs(asteroid) < Math.abs(stack.peek())) {
                return false;
            }
            if (Math.abs(asteroid) > Math.abs(stack.peek())) {
                stack.pop();
            }
        }
        return true;
    }

    private boolean isStable(int asteroid, int top) {
        if ((top <= 0 && asteroid >= 0) ||
            (top > 0 && asteroid > 0) ||
            (top < 0 && asteroid < 0)
        ) {
            return true;
        }
        return false;
    }
}