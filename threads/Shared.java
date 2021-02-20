package threads;

class C extends Thread{
	public static int content=0;
	
	static public void incrementer() {
		content ++;
		
	}
	@Override
	public void run() {
		
		super.run();
		for (int i = 0; i < 10000; i++) {
			incrementer();
			System.out.println(getName() + " " + i);
		}
	}
}


public class Shared {
public static void main(String[] args) {

	C a1 = new C();
	C a2 = new C();

	a1.setName("Ali");
	a2.setName("Baba");
	a1.start();
	a2.start();
	
	try {
		a1.join();
		a2.join();
	} catch (Exception e) {
		// TODO: handle exception
	}
	
	System.out.println("************"+C.content);

}
}
