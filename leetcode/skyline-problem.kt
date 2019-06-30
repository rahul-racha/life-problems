class Solution {
    fun getSkyline(buildings: Array<IntArray>): List<List<Int>> {
        val size = buildings.size
        var output: List<List<Int>> = emptyList()
        if (size == 0) return output
        if (size == 1) {
            val xl = buildings.get(0)[0]
            val height = buildings.get(0)[2]
            val xr = buildings.get(0)[1]
            output = listOf(listOf(xl, height), listOf(xr, 0))
            return output
        }
        val leftOutput = getSkyline(buildings.sliceArray(IntRange(0, size/2)))
        val rightOutput = getSkyline(buildings.sliceArray(IntRange(size/2 + 1, size)))
        return mergeSkylines(leftOutput, rightOutput)
    }
    
    fun updateOutput(output: MutableList<MutableList<Int>>, x: Int, height: Int): MutableList<MutableList<Int>> {
        val mutableOutput = output
        if (mutableOutput.isEmpty() || mutableOutput.get(output.size - 1).get(0) != x) {
            mutableOutput.add(mutableListOf(x, height))
        } else {
            mutableOutput.get(output.size - 1).set(1, height)
        }
        return mutableOutput
    }
    
    fun appendToOutput(output: MutableList<MutableList<Int>>, start: Int, end: Int, currentHParam: Int): MutableList<MutableList<Int>> {
        var currentH = currentHParam
        var mutableOutput = output
        while (start < end) {
            val x = output.get(start).get(0)
            val height = mutableOutput.get(start).get(1)
            
            if (currentH != height) {
                mutableOutput = updateOutput(mutableOutput, x, height)
                currentH = height
            }
        }
        return mutableOutput
    }
    
    fun mergeSkylines(leftOutput: List<List<Int>>, rightOutput: List<List<Int>>): List<List<Int>> {
        var output: MutableList<MutableList<Int>> = mutableListOf()
        var minX: Int = 0
        var currentL: Int = 0
        var currentR: Int = 0
        var currentH: Int = 0
        var maxHeight: Int = 0
        var leftH = 0
        var rightH = 0
        var leftSize = leftOutput.size
        var rightSize = rightOutput.size
        while (currentL < leftSize && currentR < rightSize) {
            val leftPoint = leftOutput.get(currentL)
            val rightPoint = rightOutput.get(currentR)
            if (leftPoint.get(0) < rightPoint.get(0)) {
                minX = leftPoint.get(0)
                leftH = leftPoint.get(1)
                currentL += 1
            } else {
                minX = rightPoint.get(0)
                rightH = rightPoint.get(1)
                currentR += 1
            }
            
            maxHeight = maxOf(leftH, rightH)
            if (currentH != maxHeight) {
                output = updateOutput(output, minX, maxHeight)
                currentH = maxHeight
            }
        }
        
        output = appendToOutput(output, currentL, leftSize, currentH)
        output = appendToOutput(output, currentR, rightSize, currentH)
        return output
    }
}