package baitap1;
import java.io.*;
import java.util.*;

abstract class shapse{
	float x;
	abstract void input();
	abstract void output();
	abstract float calarea();
}
class square extends shapse{
	
	void input() {
		Scanner ab=new Scanner(System.in);
		System.out.print("Canh a: ");
		x=ab.nextFloat();
	}

	float calarea() {
		return x*x;
	}
	void output() {
		System.out.println("| Dien tich: "+calarea()+" |");
	}

	
}

class rectangle extends shapse{
	float y;
	void input() {
		Scanner ab=new Scanner(System.in);
		System.out.print("Canh a: ");
		x=ab.nextFloat();
		System.out.print("Canh b: ");
		y=ab.nextFloat();
	}
	float calarea() {
		return x*y;
	}
	void output() {
		System.out.println("| Dien tich: "+calarea()+" |");
	}
	
}
class data{
	shapse nhap[]=new shapse[100];
	int n=0;
	char ab,c;
	void input() throws IOException {
		do {
			System.out.print("Hinh Vuong hay Hinh Chu Nhat (v/c): ");
			ab=(char) System.in.read();
			if(ab=='v'||ab=='V')
				nhap[n]=new square();
			else
				nhap[n]=new rectangle();
			System.in.skip(ab);
			nhap[n++].input();
			System.out.print("Tiep tuc ?(y/n): ");
			c=(char)System.in.read();
			System.in.skip(c);
			if(n==100||c=='n'||c=='N')
				break;
		}while(true);
	}
	void output() {
		for (int i=0;i<n;i++)
			nhap[i].output();
	}
	void max(){
		float max=nhap[0].calarea();
		for(int i=0;i<n;i++) 
			if(max<nhap[i].calarea())
				max=nhap[i].calarea();
		for(int i=0;i<n;i++) 
			if(max==nhap[i].calarea())
				System.out.println("Dien tich lon nhat la: "+nhap[i].calarea());
	}
	void min() {
		float min=nhap[0].calarea();
		for(int i=0;i<n;i++) 
			if(min>nhap[i].calarea())
				min=nhap[i].calarea();
		for(int i=0;i<n;i++) 
			if(min==nhap[i].calarea())
				System.out.println("Dien tich lon nhat la: "+nhap[i].calarea());
	}
	void tang() {
		for(int i=0;i<n-1;i++)
			for(int j=i+1;j<n;j++)
				if(nhap[i].calarea()>nhap[j].calarea()) {
					shapse a=nhap[i];
					nhap[i]=nhap[j];
					nhap[j]=a;
				}
	}
	void giam() {
		for(int i=0;i<n-1;i++)
			for(int j=i+1;j<n;j++)
				if(nhap[i].calarea()<nhap[j].calarea()) {
					shapse a=nhap[i];
					nhap[i]=nhap[j];
					nhap[j]=a;
				}
	}
}

public class baitap1 {
	public static void main(String[] args) throws IOException {
		data ab = new data();
		System.out.println("Nhap: ");
		ab.input();
		System.out.println("Xuat: ");
		ab.output();
		System.out.println("Gia tri max: ");
		ab.max();
		System.out.println("Gia tri min: ");
		ab.min();
		System.out.println("Gia tri tang: ");
		ab.tang();
		System.out.println("Gia tri giam: ");
		ab.giam();
	}

}//Nguyen Ho Huu Hoang
