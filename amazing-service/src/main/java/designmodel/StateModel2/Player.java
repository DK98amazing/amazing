package designmodel.StateModel2;

public class Player {
    private Level Primary, Secondary, Professional, Final, current;

    public Player() {
        this.Primary = new PrimaryLevel();
        this.Secondary = new SecondaryLevel();
        this.Professional = new ProfessionalLevel();
        this.Final = new FinalLevel();
        this.current = Primary;
    }

    private void setLevel(Level level) {
        this.current = level;
    }

    public void upgrade() {
        if (this.current == Primary) {
            setLevel(Secondary);
        } else if (this.current == Secondary) {
            setLevel(Professional);
        } else if (this.current == Professional) {
            setLevel(Final);
        } else if (this.current == Final) {
            System.out.println("祝贺你，你已满级。");
        }
    }

    public void play() {
        current.play();
    }

    public void doubleScore() {
        current.doubleScore();
    }

    public void changeCards() {
        current.changeCards();
    }

    public void peekCards() {
        current.peekCards();
    }
}
