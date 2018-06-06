package designmodel.StateModel2;

public class ProfessionalLevel extends Level {
    @Override
    public void play() {
        System.out.println("player正在play()");
    }

    @Override
    public void doubleScore() {
        System.out.println("player正在doubleScore()");
    }

    @Override
    public void changeCards() {
        System.out.println("player正在changeCards()");
    }

    @Override
    public void peekCards() {
        System.out.println("player未解锁此功能");
    }
}
