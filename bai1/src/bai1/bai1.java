package bai1;
import java.util.*;

public class bai1 {
	public static void input(int n, int a[]) {
		Scanner asd = new Scanner(System.in);
		for(int i = 0; i<n;i++) {
			System.out.print("Nhap i thu "+i+" : ");
			a[i] = asd.nextInt();
		}
	}
	
	public static void output(int n, int a[]) {
		for(int i=0;i<n;i++) System.out.print(a[i]+"	");
		System.out.println();
		System.out.println();
	}
	
	public static void chiahet(int n, int a[]) {
		int chiahet=0;
		System.out.print("Day so chia het cho 5:	");
		for(int i=0;i<n;i++) {
			if(a[i]%5==0) {
				chiahet=chiahet+a[i];
				System.out.print(a[i]+"	");
			}
		}
		System.out.println();
		System.out.println();
		System.out.print("Tong so chia het cho 5:	"+chiahet);
		System.out.println();
		System.out.println();
	}
	
	public static void kochiahet(int n, int a[]) {
		int kochiahet=0;
		System.out.print("Day so khong chia het cho 5:	");
		for(int i=0;i<n;i++) {
			if(a[i]%5==0) {
				kochiahet=kochiahet+a[i];
				System.out.print(a[i]+"	");
			}
		}
		System.out.println();
		System.out.println();
		System.out.print("Tong so khong chia het cho 5:	"+kochiahet);
		System.out.println();
		System.out.println();
	}
	
	public static Boolean test(int a) {
		if(a<2) return false;
		for(int i=2;i<a;i++) 
			if(a%i==0) return false;
		return true;
	}
	
	public static void prime(int n, int a[]) {
		System.out.print("Day so nguyen to:	");
		for(int i=0;i<n;i++) if(test(a[i])) System.out.print(a[i]+"	");
		System.out.println();
		System.out.println();
	}

	public static void main(String[] args) {
		int n;
		int[] a=new int[100];
		Scanner as = new Scanner(System.in);
		System.out.print("Nhap n: ");
		n = as.nextInt();
		input(n,a);
		output(n,a);
		chiahet(n,a);
		kochiahet(n,a);
		prime(n,a);
	}

}
