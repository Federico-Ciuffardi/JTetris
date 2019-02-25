public class Main{
	public static void main(String[] args){
		System.out.println("Starting...");
		Engine engine = new Engine();
		new Thread(engine).start();
	}
}
