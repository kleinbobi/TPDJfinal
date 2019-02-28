import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Kreis extends Node {
    public static final int r = 40;
    LinkedList<Kreis> ziele = new LinkedList<>();

    private int x;
    private int y;
    private Color color;
    // private String name;


    public Kreis(int x, int y, Color color, String name) throws FieldExeption {
        super(name);
        setX(x);
        setY(y);
        this.color = color;

    }

    public Kreis(String name, int x, int y) throws FieldExeption {

        super(name);
        setX(x);
        setY(y);

    }

    public Kreis(String name) {
        super(name);
        Random random = new Random();
        x = random.nextInt(1750);
        y = random.nextInt(700);


    }

    public static int getR() {
        return r;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) throws FieldExeption {
        if (x >= 0 && x <= neuguimitconsol.w) {
            this.x = x;
        } else {
            throw new FieldExeption("X wert nicht im Bereich");
        }
    }

    public int getY() {
        return y;
    }

    public void setY(int y) throws FieldExeption {
        if (y >= 0 && y <= neuguimitconsol.w) {
            this.y = y;
        } else {
            throw new FieldExeption("Y wert nicht im Bereich");
        }
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
        System.out.println("color canced");
    }

    // public String getName() {return name;}

    // public void setName(String name) { this.name = name; }


    @Override
    protected Kreis clone() throws CloneNotSupportedException {
        try {
            Kreis k = new Kreis(this.x, this.y, this.color, this.getName());
            k.setZiele(this.getZiele());
            k.setAdjacentNodes(this.getAdjacentNodes());
            return k;
        } catch (FieldExeption fieldExeption) {
            fieldExeption.printStackTrace();
        }
        return null;
    }

    @Override
    public void addDestination(Node destination, int distance) {
        try {
            super.addDestination(destination, distance);
        } catch (NegativeDistanceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ziele.add((Kreis) destination);
    }

    public LinkedList<Kreis> getZiele() {
        return ziele;
    }

    public void setZiele(LinkedList<Kreis> ziele) {
        this.ziele = ziele;
    }
}
