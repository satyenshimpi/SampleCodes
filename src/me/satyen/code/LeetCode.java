package me.satyen.code;

import java.util.ArrayList;
import java.util.Stack;

public class LeetCode {
	public int largestRectangleArea(ArrayList<Integer> A) {
		Stack<Integer> stack = new Stack<>();
		int n = A.size();
		int res = 0;
		int prevMin[], nextMin[];
		int cur;
		int idx;
		int prev, next;

		prevMin = new int[n];
		nextMin = new int[n];

		for (int i = 0; i < n; i++) {
			cur = A.get(i);
			while (!stack.isEmpty() && cur <= A.get(stack.peek())) {
				stack.pop();
			}

			if (stack.isEmpty()) {
				prevMin[i] = -1;
			} else {
				prevMin[i] = stack.peek();
			}

			stack.push(i);
		}
		stack.clear();

		for (int i = n - 1; i >= 0; i--) {
			cur = A.get(i);
			while (!stack.isEmpty() && cur <= A.get(stack.peek())) {
				stack.pop();
			}
			if (stack.isEmpty())
				nextMin[i] = n;
			else
				nextMin[i] = stack.peek();
			stack.push(i);
		}

		for (int i = 0; i < n; i++) {
			prev = prevMin[i];
			next = nextMin[i];
			int area = (next - prev - 1) * A.get(i);
			res = Math.max(res, area);
		}

		return res;
	}
	public static void main(String[] args) {
		LeetCode o = new LeetCode();
		ArrayList<Integer> lst = new ArrayList<>();
		lst.add(2);
		lst.add(1);
		lst.add(5);
		lst.add(6);
		lst.add(2);
		lst.add(3);
		System.out.println(o.largestRectangleArea(lst));
	}


//		public int uniquePathsWithObstacles(ArrayList A) { 
//			int m, n; 
//			if (A == null) return 0; 
//			m = A.size(); 
//			if (m == 0) return 0; 
//			n = A.get(0).size(); 
//			if (n == 0) return 0; 
//			mem = new int[m][n]; 
//			for (int i = 0; i < m; i++) Arrays.fill(mem[i], -1); 
//			this.A = A; 
//			if (A.get(0).get(0) == 0) mem[0][0] = 1; rec(m - 1, n - 1); return mem[m - 1][n - 1]; 
//			}
//
//		public int rec(int i, int j) {
//			if (i < 0 || j < 0)
//				return 0;
//			if (mem[i][j] != -1)
//				return mem[i][j];
//			if (A.get(i).get(j) == 1)
//				return mem[i][j] = 0;
//			mem[i][j] = rec(i - 1, j) + rec(i, j - 1);
//			return mem[i][j];
//		}
//	}
}
