//import Foundation
struct Stack<T> {
    
    fileprivate var container = [T]()
    
    var isEmpty: Bool {
        return self.container.isEmpty
    } 

    var count: Int {
        return self.container.count
    }

    var top: T? {
        return self.container.last
    }

    func getElements() -> [T] {
        return self.container
    }

    mutating func push(_ value: T)  {
        self.container.append(value)
    }

    mutating func pop() -> T? {
        var temp: T?
        temp = self.container.popLast()
        if (temp == nil) {
            print("Stack overflow")
        }
        return temp
    }
}

var stack = Stack<Int>()
stack.push(5); stack.push(6); stack.push(7); stack.push(8);
print(stack.getElements())
_ = stack.pop(); 
print(stack.getElements())
_ = stack.pop(); _ = stack.pop();
print(stack.top as Any)
 _ = stack.pop(); _ = stack.pop();
 print(stack.top as Any)