public class A {
    private static A istance;

    private A() {
    }

    public static A create() {
        if (instance == null) {
            instance = new A();
        }
        return instance;
    }
}


