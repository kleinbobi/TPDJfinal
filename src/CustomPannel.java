import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class CustomPannel extends JPanel {

    private List<Kreis> kreisList = new LinkedList<>();
    private Kreis loked;
    private List<Node> pathlist = new LinkedList<Node>();

    @Override
    public void paint(Graphics graphics) {
        this.setBackground(Color.BLACK);
        super.paintComponent(graphics);
        int r = Kreis.getR();
        graphics.setColor(Color.white);
        for (int i = 0; i < kreisList.size(); i++) {

            Kreis k = kreisList.get(i);

            LinkedList<Kreis> ziele = k.getZiele();





            for (int j = i; j < kreisList.size(); j++) {

                Kreis kk = kreisList.get(j);
                for (Kreis kkk: ziele
                     ) {
                    if(kkk.getName().equals(kk.getName())){
                        graphics.drawLine(k.getX(),k.getY(),kk.getX(),kk.getY());
                        if(k.getDistance()!=Integer.MAX_VALUE) {
                            int x = 0;
                            int y = 0;
                            if (k.getX() > kk.getX()) {
                                x = kk.getX() + (k.getX() - kk.getX()) / 2;
                            } else {
                                x = k.getX() + (kk.getX() - k.getX()) / 2;
                            }
                            if (k.getY() > kk.getY()) {
                                y = kk.getY() + (k.getY() - kk.getY()) / 2;
                            } else {
                                y = k.getY() + (kk.getY() - k.getY()) / 2;
                            }

                            graphics.drawString(kk.getDistance() + "", x, y);
                        }
                    }
                }
           }

            if (pathlist != null){
                for (Node kkk : pathlist
                ) {
                    if (kkk.getName().equals(k.getName())) {
                        graphics.setColor(Color.MAGENTA);
                        System.out.println(k.getName());
                    }
                }
            }

            graphics.drawString(k.getName(),k.getX()+r/2,k.getY()+r/2);
            graphics.fillOval(k.getX()-r/2,k.getY()-r/2,r,r);
            graphics.setColor(Color.white);

        }
    }

    public void addKreis(Kreis a){
        kreisList.add(a);
    }
    public void remouveKeis(Kreis a){
        kreisList.remove(a);
    }

    public List<Kreis> getKreisList() {
        return kreisList;
    }

    public void setLoked(Kreis loked) {
        this.loked = loked;
    }

    public void setPathlist(List<Node> pathlist) {
        this.pathlist = pathlist;
    }
}
