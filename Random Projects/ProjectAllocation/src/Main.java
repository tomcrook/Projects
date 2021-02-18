public class Main {
	
	public static void main(String[] args) {
		int[][] projects = new int[5][10];
		
		for (int i = 0; i < projects.length; i++) {
			int[] nums = new int[10];
			for (int j = 0; j < projects[i].length; j++) {
				if (j == 0) {
					nums[0] = 0;
				} else {
					nums[j] = (int) (Math.random() * 5 + nums[j - 1]);
				}
			}
			projects[i] = nums;
		}
		
		for (int i = 0; i < projects.length; i++) {
			for (int j = 0; j < projects[i].length; j++) {
				System.out.print(projects[i][j]+ " ");
				if (projects[i][j] < 10) {
					System.out.print(" ");
				}
			}
			System.out.println();
		}
		Allocate a = new Allocate(projects);
		int[] ans = a.allocate(5);
		for (int i = 0; i < ans.length; i++) {
			System.out.println(ans[i]);
		}
	}
}

class Allocate {
	int[][] projects;
	
	Allocate(int[][] p) {
		projects = p;
	}
	
	int[] allocate(int i) {
		int[] p = new int[projects.length];
		for (int x = 0; x < i; x++) {
			p[x] = 0;
		}
		for (int x = 0; x < i; x++) {
			int opt = -1;
			int val = 0;
			for (int y = 0; y < projects.length; y++) {
				if (projects[y][p[y]+1] - projects[y][p[y]] > val) {
					opt = y;
					val = projects[y][p[y]+1] - projects[y][p[y]];
				}
			}
			p[opt] += 1;
		}
		return p;
	}
	
	
	
}