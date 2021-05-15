package pt1ham;
import java.util.*;

public class index {
	public static int input(String tr) {
		int a;
		Scanner asd = new Scanner(System.in);
		System.out.print("Nhập "+tr+" : ");
		a = asd.nextInt();
		return a;
	}
	public static void pt1(int a, int b) {
		double x;
		if(a==0) {
			if (b==0) {
				System.out.println("PTVSN");
			}
			else {
				System.out.println("PTVN");
			}
		}
		else {
			x=(double)-b/a;
			System.out.println("x= "+x);
		}
	}
	public static void main(String[] args) {
		int a,b;
		a = input("a");
		b = input("b");
		pt1(a,b);
	}

}
