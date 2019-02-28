import javax.swing.*;
import java.io.*;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class neuguimitconsol extends JFrame {

    public static int w = 1750;
    public static int h = 700;

    public static void main(String[] args) {

        startimage();

        boolean console = true;
        CustomPannel panel = new CustomPannel();
        Scanner scanner = new Scanner(System.in);
        neuguimitconsol gui = new neuguimitconsol();
        System.out.println("Hallo (help -> öffnet Help)");
        String s = "";
        BufferedReader br = null;
        do {
            if (console) {
                System.out.print(":");
                s = scanner.nextLine() + " ";
            } else {

                try {
                    s = br.readLine();
                    if (s == null) {
                        br.close();
                        console = true;
                    }
                    s += " ";
                    System.out.println(s);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            LinkedList<String> strings = stringgrinder(s);

            try {


                if ("help".equals(strings.get(0))) {
                    showhelp();
                } else if ("new".equals(strings.get(0))) {
                    createNew(strings, panel);
                } else if ("show".equals(strings.get(0))) {
                    new PanlesShowforConsol(panel);
                } else if ("link".equals(strings.get(0))) {
                    makelink(strings, panel);
                } else if ("start".equals(strings.get(0))) {
                    start(strings, panel);
                } else if ("list".equals(strings.get(0))) {
                    listetheshi(panel);
                } else if ("exit".equals(strings.get(0))) {
                    System.exit(0);
                } else if ("file".equals(strings.get(0))) {
                    console = false;
                    try {
                    	
                        File homedir = new File(System.getProperty("user.home"));
                        File fileToRead = new File(homedir, String.valueOf(strings.get(1)));
                        br = new BufferedReader(new FileReader(fileToRead));
                    } catch (FileNotFoundException e) {
                        System.out.println("Path incorekt");
                        console = true;
                    }
                } else if ("field".equals(strings.get(0))) {
                    System.out.println("X: " + w + "  Y: " + h);
                } else  if("weg".equals(strings.get(0))){
                    weg(strings, panel);
                } else {
                    System.out.println("Unbekanter Befehl ");
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            panel.repaint();


        } while (true);


    }

    public static void listetheshi(CustomPannel panel) {
        for (Kreis k : panel.getKreisList()
        ) {
            System.out.print(k.getName() + "  x:" + k.getX() + "  y:" + k.getY() + "    Ziele: ");
            for (Kreis kk : k.getZiele()
            ) {
                System.out.print(kk.getName() + ",");
            }
            System.out.println();
        }
    }

    public static void start(LinkedList<String> strings, CustomPannel panel) throws FieldExeption, CloneNotSupportedException {

        if (strings.size() != 2) {
            throw new FieldExeption("Parameter anzahl nicht korekt");
        }

        List<Kreis> kreisliste = panel.getKreisList();
        Graph graph = new Graph();
        Kreis nodeB = null;
        for (Kreis k : kreisliste
        ) {
            if (k.getName().equals(strings.get(1))) {
                nodeB = k;
            }
            k = k.clone();
            graph.addNode(k);
        }


        panel.setPathlist(null);
        if (nodeB != null) {
            graph = Dijkstra.calculateShortestPathFromSource(graph, nodeB);
            graph.getNodes().stream().sorted(Comparator.comparing(Node::getName)).forEach(x -> System.out.println("Node" + x.getName() + " hat den folgenden Wert: " + x.getDistance()));
        }
    }

    public static void makelink(LinkedList<String> strings, CustomPannel panel) throws FieldExeption {

        if (strings.size() != 4) {
            throw new FieldExeption("Parameter Anzahl inkorekt");
        }

        List<Kreis> kreisliste = panel.getKreisList();

        Kreis a = null;
        Kreis b = null;

        for (Kreis k : kreisliste
        ) {
            if (k.getName().equals(strings.get(1))) {
                a = k;
            }
            if (k.getName().equals(strings.get(2))) {
                b = k;
            }

        }


        String s = strings.get(3);
        if ((a != null) && (b != null) && Integer.parseInt(s) >= 0) {
            a.addDestination(b, Integer.parseInt(s));
        } else {
            throw new FieldExeption("Note nicht Forhanden");
        }

    }

    public static void showhelp() {
        System.out.println("----Help----");
        System.out.println("new <Name> [optional/empfohlen] <x:> <y:> Erstelt neuen Knoten");
        System.out.println("show startet gui Macht eine GUI sichtbahr");
        System.out.println("link <from> <to> <wert> erstelt neuen link zwischen knoten");
        System.out.println("start <Start Note> startet die berechnung");
        System.out.println("list zeigt die einzelnen Knoten");
        System.out.println("file <sorce>(von home aus) arbeitet die Angegebene datei ab");
        System.out.println("field gibt die Größe der GUI max:X und max:Y");
    }

    public static LinkedList stringgrinder(String s) {
        LinkedList<String> ret = new LinkedList<>();

        int j = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ' ') {
                ret.add(s.substring(j, i));
                j = i + 1;
            }
        }

        return ret;
    }

    public static void createNew(LinkedList<String> strings, CustomPannel panel) throws FieldExeption {

        for (Kreis k : panel.getKreisList()
        ) {
            if (k.getName().equals(strings.get(1))) {
                throw new FieldExeption("Note mit disem Namen existiart bereitz");
            }
        }


        if (strings.size() == 2) {
            panel.addKreis(new Kreis(strings.get(1)));
            System.out.println(strings.get(1) + " :Created");
        } else {
            if (strings.size() == 4) {

                panel.addKreis(new Kreis(strings.get(1), Integer.parseInt(strings.get(2)), Integer.parseInt(strings.get(3))));
                System.out.println(strings.get(1) + " :Created");
            } else {
                System.out.println("Parameter nicht Passend");
            }
        }


    }


    private static void startimage(){
        System.out.println("    @----@           @----@");
        System.out.println("    |    |           |    |     |-\\      |");
        System.out.println("    @----@           @----@     |  \\ ");
        System.out.println("          \\\\     //             |   \\    |");
        System.out.println("           \\\\   //              |   /    |");
        System.out.println("            @----@              |  /     |");
        System.out.println("            |    |              |-/   \\  /");
        System.out.println("            @----@                     \\/");



    }

    private static void weg(LinkedList<String> strings, CustomPannel panel) throws CloneNotSupportedException {
        System.out.println("starte");
        List<Kreis> kreisliste = panel.getKreisList();
        Graph graph = new Graph();
        Kreis nodeB = null;
        Kreis nodeA = null;
        for (Kreis k : kreisliste
        ) {
            if (k.getName().equals(strings.get(1))) {
                nodeB = k;
            }
            if (k.getName().equals(strings.get(2))) {
                nodeA = k;
            }
            k = k.clone();
            graph.addNode(k);
        }


     //  nodeA.setShortestPath(new LinkedList<>());
        panel.setPathlist(new LinkedList<>());
        if (nodeB != null) {
            Dijkstra.calculateShortestPathFromSource(graph, nodeB);

            List<Node> a = nodeA.getShortestPath();
            System.out.println(nodeA.getShortestPath());

            a.add(nodeA);

            panel.setPathlist(a);
        }
    }
}
