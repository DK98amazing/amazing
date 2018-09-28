public class Tests {
    private String string;
    private int anInt;
    public String param = "121212";

    public Tests(String string, int anInt) {
        this.string = string;
        this.anInt = anInt;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public int getAnInt() {
        return anInt;
    }

    public void setAnInt(int anInt) {
        this.anInt = anInt;
    }

    public static void main(String[] args) {
        System.out.println(3 * Math.pow(2, 12));
        System.out.print(3 << 12);
    }
}
