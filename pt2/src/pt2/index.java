package pt2;

import java.util.Scanner;

public class index {

	public static void main(String[] args) {
		int a,b,c,d;
		double x,x1,x2;
		Scanner asd = new Scanner(System.in);
		
		System.out.println("Nhap a: ");
		a = asd.nextInt();
		
		System.out.println("Nhap b: ");
		b = asd.nextInt();

		System.out.println("Nhap c: ");
		c = asd.nextInt();
		
		d=b*b-2*a*c;
		if(a==0) {
			if(b==0 && c==0) {
				System.out.println("PTVSN");
			}
			else if(b==0 && c!=0) {
				System.out.println("PTVN");
			}
			else {
				x=(double)-b/a;
				System.out.println("x= "+x);
			}
		}
		else {
			if (a+b+c==0) {
				//x1=1;
				x2=(double)c/a;
				System.out.println("PT co 2 Nghiem phan biet");
				System.out.println("x1 = 1, x2 = "+x2);
			}
			else if (a-b+c==0) {
				//x1=1;
				x2=(double)-c/a;
				System.out.println("PT co 2 Nghiem phan biet");
				System.out.println("x1= -1, x2 = "+x2);
			}
			else if(d<0) {
				System.out.println("PTVN");
			}
			else if(d==0) {
				x= (double)-b/(2*a);
				System.out.println("Co 2 nghiem kep x= "+x);
			}
			else {
				x1=(double)(-b+Math.sqrt(d))/(2*a);
				x2=(double)(-b-Math.sqrt(d))/(2*a);
				System.out.println("PT co 2 Nghiem phan biet");
				System.out.println("x1= "+x1);
				System.out.println("x2= "+x2);
			}
		}
	}

}
