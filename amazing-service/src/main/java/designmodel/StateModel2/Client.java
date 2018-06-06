package designmodel.StateModel2;

public class Client {
    public static void main(String args[]) {
        Player player = new Player();
        player.play();
        player.changeCards();
        player.peekCards();
        player.upgrade();
        player.upgrade();
        player.changeCards();
        player.peekCards();
        player.upgrade();
        player.peekCards();
        player.upgrade();
    }
}
