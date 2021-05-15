package hinhtron;
import java.util.*;

public class index {
	public static int input(String tr) {
		int i;
		Scanner as = new Scanner(System.in);
		System.out.print("Nhập "+tr+" : ");
		i = as.nextInt();
		return i;
	}
	public static void s(int r) {
		final double PI = 3.1416;
		double s;
		s = PI*(r*r);
		System.out.print("dien tich hinh tron la: "+s);
	}

	public static void main(String[] args) {
		int r;
		r = input("Bán kính");
		s(r);
	}

}
