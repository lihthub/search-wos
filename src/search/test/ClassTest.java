package search.test;

public class ClassTest {

	public static void main(String[] args) {
		C c = new C();
		System.out.println(c.a);
	}

}

class A {
	public String a;
	
	public A() {
		System.out.println("A");
	}
	
}

class B extends A {
	
	public B() {
		System.out.println("B");
		System.out.println(this.a);
	}
	
}

class C extends B {
	
	public C() {
		System.out.println("C");
		this.a = "c";
	}
	
}
