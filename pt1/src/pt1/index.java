package pt1;
import java.util.*;

public class index {

	public static void main(String[] args) {
		int a,b;
		double x;
		Scanner asd = new Scanner(System.in);
		
		System.out.println("Nhap a: ");
		a = asd.nextInt();
		
		System.out.println("Nhap b: ");
		b = asd.nextInt();
		
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

}
