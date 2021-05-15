
import java.util.*;
import java.io.*;

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
	public static void ptb2(int a, int b, int c) {
		double x,x1,x2;
		int d;
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
	

	public static void main(String[] args) throws IOException {
		int n,a,b,c;
		char check;
		Scanner asds = new Scanner(System.in);
		do {
			System.out.println("----------------------------");
			System.out.println("     1. Giải PT bậc 1");
			System.out.println("     2. Giải PT bậc 2");
			System.out.println("     3. Thoát");
			System.out.println("----------------------------");
			System.out.print("n: ");
			n = asds.nextInt();
			
			switch(n) {
			case 1:{
				a = input("a");
				b = input("b");
				pt1(a,b);
			};break;
			case 2:{
				a = input("a");
				b = input("b");
				c = input("c");
				ptb2(a,b,c);
			};break;
			default: System.out.println("?");break;
			}
			
			System.out.print("Tiếp tục không ?(Y/n)");
			check = (char) System.in.read();
		}while(check == 'Y' || check == 'y');
	}

}
