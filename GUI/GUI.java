package mina.GUI;

import mina.Controller.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Implementacja obiektu klasy GUI - interfejsu graficznego programu z uzyciem Swing.
 * Calosc mozna znacznie ulepszyc o dodanie np. flag
 * mozna poprawic komunnikacje poprzez dodanie klasy pomocniczej w gui bedacej wiadomoscia i jedynym wyjsciem do klasy controlera
 * mozna poprawic aplikacje o dodanie wzrocow projektowych np. state czy strategy
 */

public class GUI {
    /** Referencja obiektu controlera*/
    private Controller controller;
    /** Referencja obiektu miniPanelu ustawien*/
    public JUstawienia Justawienia;

   public JUstawieniaPowitalne JustawieniaPowitalne;
    /** Referencja obiektu wyswietlacza ilosci min*/
    private JLabel display ;
    /** Referencja obiektu czasu*/
    private JLabel time ;
    public int szer ;
    public  int wys ;
    public MyButton [][] tablicaButtonow;

    /** Konstruktor tworzacy obiekt graficzny oraz zapamietujacy referencje do kontrolera oraz pobierajacy z kontrolera parametrow jak ilosc pol w wierszach i kolumnach z obiektu controllera*/
    public GUI(Controller controller){
        this.controller=controller;
        szer = controller.setWidth();
        wys = controller.setHeight();


        try
        {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    createAndShowGUI();
                }
            });
        }
        catch(Exception e)
        {
            System.out.println("Blad podczas budowania GUI");
        }
    }

    public void setwysszer (int s, int w){
        this.szer=s;
        this.wys=w;
        tablicaButtonow = new MyButton[wys][szer];
    }
    /**
     * Metoda odpowiadajaca za stworzeniie interfejsu graficznego aplikacji
     */
    private void createAndShowGUI (){

        JustawieniaPowitalne = new JUstawieniaPowitalne();
    }

    /**
     * Metoda udostepniana controlerowi w celu umieszczenia na wyswietlaczu interfejsu gui ilosci min
     */
    public void setDisplay (int iloscMin){
        display.setText("Miny = " + new Integer(controller.iloscMin).toString());
    }

    /**
     * Metoda udostepniana controlerowi w celu umieszczenia na wyswietlaczu interfejsu gui czasu od momentu rozpoczecia danej gry
     */
    public void setTime (String time)
    {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GUI.this.time.setText(time);
            }
        });
    }

    /**
     * Klasa tworzaca panel z przyciskami do min
     */
    private class Buttons extends JPanel{

        private static final long serialVersionUID=1L;

        Buttons (ButtonListenerImplementation buttonlistener)
        {
            setLayout(new GridLayout(wys,szer));
            for(int i =0;i<wys;i++)
            {
                for(int j=0;j<szer;j++){
                    tablicaButtonow[i][j]=new MyButton(i,j,buttonlistener);
                    add(tablicaButtonow[i][j]);
                }
            }
        }
    }

    /**
     * Klasa bedaca szablonem przycisku miny
     */
    public class MyButton extends JButton
    {
        int indexX;
        int indexY;
        private static final long serialVersionUID =1L;
        /**
         * Konstruktor przycisku zapamietujacy zmienne i,j symbolizujace lokalizacje w tablicy z minami
         */
        MyButton(int i, int j, ButtonListenerImplementation buttonListener){

            super("   ");
            setBackground(Color.LIGHT_GRAY);

            indexX=i;
            indexY=j;
            addActionListener(buttonListener);
        }
    }

    /**
     * Klasa wewnetrzna obslugujaca akcje wcisniecia przycisku
     */
    private class ButtonListenerImplementation implements ActionListener
    {
        public void actionPerformed(ActionEvent event) {
controller.setTime();
            controller.sendInfo((((MyButton) (event.getSource())).indexX),((MyButton) (event.getSource())).indexY);
        }
    }

    /**
     * Klasa tworzaca glowny interffejs graficzny aplikacji wraz z obudowa o paski menu
     */
    public class JMenuSystemu extends JFrame{
        /**
         * Konstruktor klasy
         */
        public JMenuSystemu( int szer, int wys, int iloscmin)

        {
            /**
             * Konstruktor klasy nadrzednej
             */
            super ("mina version 1.0");

            setDefaultLookAndFeelDecorated(true);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocation(100,100);
            Buttons buttons = new Buttons (new ButtonListenerImplementation());
            /**            Umieszczenie przyciskow min w glownym kontenerze         */
            getContentPane().add(buttons,"North");
            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(1,2));
            display = new JLabel("Miny = " + new Integer(controller.iloscMin).toString(),JLabel.CENTER);
            display.setOpaque(true);
            display.setForeground(Color.LIGHT_GRAY);
            display.setBackground(Color.BLACK);
            /**            Umieszczenie wyswietlacza ilosci min glownym kontenerze         */
            panel.add(display);
            time = new JLabel("czas",JLabel.CENTER);
            time.setOpaque(true);
            time.setForeground(Color.LIGHT_GRAY);
            time.setBackground(Color.BLACK);
            /**            Umieszczenie wyswietlacza czasu w glownym kontenerze         */
            panel.add(time);
            getContentPane().add(panel,"South");
            /**            Umieszczenie funkcjonalnosci menu w glownym kontenerze         */
            createMenu();
            pack();
            setLocationRelativeTo(null);
            setResizable(false);
            setVisible(true);
        }

        /**           Funkcja tworzaca menu aplikacji          */
        private void createMenu(){

            JMenuBar  mbar = new JMenuBar();
            setJMenuBar(mbar);
            JMenu Opcje = new JMenu("Opcje");
            mbar.add(Opcje);
            JMenuItem Ustawienia = new JMenuItem("Ustawienia");
            /**          Obsluga zdarzen wcisniecia przycisku ustawien w menu          */
            Ustawienia.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(Justawienia==null)
                        Justawienia =new JUstawienia();
                    Justawienia.setVisible(true);
                    if (controller.timer!=null)
                        controller.timer.stop();
                }
            });
            Opcje.add(Ustawienia);
            Opcje.addSeparator();
            JMenuItem Info = new JMenuItem("Info");
            /**          Obsluga zdarzen wcisniecia przycisku info w menu          */
            Info.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    controller.WyswietlInfo();
                }
            });
            Opcje.add(Info);
        }
    }
    /**
     * Klasa tworzaca interfejs panelu ustawien z opcja wyboru ilosci min w grze
     */
    public class JUstawienia extends JFrame
    {
        public JUstawienia(){
            super ("Ustawienia");
            setDefaultLookAndFeelDecorated(true);
            setBackground(Color.GREEN);
            this.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    setVisible(false);
                    if (controller.timer!=null)
                        controller.timer.start();
                }
            });
            setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            JPanel Panel = new JPanel();
            Panel.setBackground(Color.LIGHT_GRAY);
            Panel.setLayout(new GridLayout(3,1));
            JLabel IloscMin = new JLabel("Ile % min chcesz mie\u0107 ?",SwingConstants.CENTER);
            Panel.add(IloscMin);
            JSlider iloscMin = new JSlider(JSlider.HORIZONTAL, 5,95,50);
            iloscMin.setBackground(Color.LIGHT_GRAY);
            iloscMin.setMajorTickSpacing(15);
            iloscMin.setMinorTickSpacing(1);
            iloscMin.setPaintTicks(true);
            iloscMin.setPaintLabels(true);
            Panel.add(iloscMin);
            JButton OK = new JButton("OK");
            OK.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Justawienia.setVisible(false);
                    controller.restartZUstawieniami(iloscMin.getValue() );

           }
            });
            OK.setBackground(Color.BLACK);
            OK.setForeground(Color.WHITE);
            Panel.add(OK);
            getContentPane().add(Panel,"Center");
            pack();
            setLocationRelativeTo(null);
            setResizable(false);
            setVisible(false);
        }
    }
    /**
     * Klasa tworzaca interfejs powitalnego panelu ustawien z opcja wyboru ilosci min w grze oraz rozmiaru planszy
     */
    public class JUstawieniaPowitalne extends JFrame {
        public JUstawieniaPowitalne() {
            super("Witaj!");
            setDefaultLookAndFeelDecorated(true);
            setBackground(Color.GREEN);

            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            JPanel Panel = new JPanel();
            Panel.setBackground(Color.LIGHT_GRAY);
            Panel.setLayout(new GridLayout(7, 1));
            JLabel Szerokosc = new JLabel("Szerokosc");
            Panel.add(Szerokosc);
            JSlider szer = new JSlider(JSlider.HORIZONTAL, controller.szer_min, controller.szer_max, 10);
            szer.setBackground(Color.LIGHT_GRAY);
            szer.setMajorTickSpacing(5);
            szer.setMinorTickSpacing(1);
            szer.setPaintTicks(true);
            szer.setPaintLabels(true);
            Panel.add(szer);
            JLabel Wysokosc = new JLabel("Wysokosc");
            Panel.add(Wysokosc);
            JSlider wys = new JSlider(JSlider.HORIZONTAL, controller.wys_min, controller.wys_max, 10);
            wys.setBackground(Color.LIGHT_GRAY);
            wys.setMajorTickSpacing(5);
            wys.setMinorTickSpacing(1);
            wys.setPaintTicks(true);
            wys.setPaintLabels(true);
            Panel.add(wys);
            JLabel IloscMin = new JLabel("IloscMin %");
            Panel.add(IloscMin);
            JSlider iloscMin = new JSlider(JSlider.HORIZONTAL, 5, 95, 20);
            iloscMin.setBackground(Color.LIGHT_GRAY);
            iloscMin.setMajorTickSpacing(45);
            iloscMin.setMinorTickSpacing(5);
            iloscMin.setPaintTicks(true);
            iloscMin.setPaintLabels(true);
            Panel.add(iloscMin);
            JButton OK = new JButton("! GRAMY !");
            OK.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    setwysszer(szer.getValue(), wys.getValue());
                    controller.szer=szer.getValue();
                    controller.wys=wys.getValue();
                    controller.iloscMin=iloscMin.getValue();
                    new JMenuSystemu(szer.getValue(), wys.getValue(), iloscMin.getValue());
                    controller.startGry(szer.getValue(), wys.getValue(), iloscMin.getValue() );
                    setVisible(false);
                }
            });
            OK.setBackground(Color.BLACK);
            OK.setForeground(Color.WHITE);
            Panel.add(OK);
            getContentPane().add(Panel, "Center");
            pack();
            setLocationRelativeTo(null);
            setResizable(false);
            setVisible(true);

        }


    }
    }
