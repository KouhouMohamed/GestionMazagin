package threads;

import java.sql.Time;

class A extends Thread{
	public void manger() {
		
			for(int i=0;i<10;i++) {
				System.out.println("je mange "+i);

				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		
	}
	@Override
	public void run() {
		manger();
	}
}
class B extends Thread{
	public void marcher() {
		
			for(int i=0;i<10;i++) {
				System.out.println("je marche "+i);

				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		
	}
	@Override
	public void run() {
		marcher();
	}
}
public class Program {
public static void main(String[] args) {
	System.out.println("Je me reveille !!!");
	A a = new A();
	B b = new B();
//	a.setPriority(Thread.MIN_PRIORITY);
//	b.setPriority(Thread.MAX_PRIORITY);
	a.start();
	b.start();
	
	//Je ne dois passer à la suite qu' apres terminer a et b
	try {
		a.join();
		b.join();
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	System.out.println("Entrer à la salle");
	System.out.println("Suivre le cour");
	
}
}
