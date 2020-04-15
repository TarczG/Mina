package mina.Controller;
import mina.Processor.*;
import mina.GUI.*;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Klasa kontrolera dla wzorca MVC
 * Calosc mozna znacznie ulepszyc o dodanie np. flag
 * mozna poprawic komunnikacje poprzez dodanie klasy pomocniczej w gui bedacej wiadomoscia i jedynym wyjsciem do klasy controlera
 * mozna poprawic aplikacje o dodanie wzrocow projektowych np. state czy strategy
 */
public class Controller {
    /** Referencja obiektu GUI*/
    private GUI gui;
    public final int szer_min=5;
    public final int szer_max=20;
    public final int wys_min=5;
    public final int wys_max=30;
    public final int min_min=1;
    public final int min_max=20;
    /**zmienna ilosc pol min w kolumnie*/
   public int szer=10;
    /**zmienna ilosc pol min w wierszu*/
   public int wys=11;
    /**zmienna ilosc min*/
   public int iloscMin=5;
   boolean wygrana = true;
   int wolnePola= szer*wys;
    public Timer timer;
    int seconds=0;
    /**zmienna przechowujaca tablice o danej wysokosci i szerokosci ze znakami dla interfejsu gui*/
    public int [][] tablica;

    /**konstruktor kontrolera przypisujacy zmiennej tablicy odppowienie wartosci z metody statycznej klasy procesora*/
    public Controller()
    {
        Processor.wypelnijLiczbami(tablica,iloscMin);
        Processor.drukuj(tablica);
    }



    /**Metoda ustawiajaca ilosc przyciskow w wierszach dla interfejsu GUI*/
    public int setWidth () {
        return szer;
    }

    /**Metoda ustawiajaca ilosc przyciskow w kolumnie dla interfejsu GUI*/
    public int setHeight () {
        return wys;
    }

    /**Metoda udostepniana obiektowi gui w celu obslugi zdarzen wcisniecia przycisku miny*/
    public  synchronized void  sendInfo (int x, int y){

        String znak = new String();
        Integer liczba = new Integer(tablica[x][y]);
        /**Obsluga zdarzenia jesli wpadnieto na mine */
        if(liczba==9){
            znak="*";
            for (Point punkt: Processor.saperTablica){
                wygrana=false;
                ((JButton)(getGUI().tablicaButtonow[punkt.x][punkt.y])).setText(znak);
                ((JButton)(getGUI().tablicaButtonow[punkt.x][punkt.y])).setEnabled(false);
                ((JButton)(getGUI().tablicaButtonow[punkt.x][punkt.y])).setBackground(setColor(liczba));
            }
            for ( int i =0; i<tablica.length;i++){
                for(int j=0; j<tablica[1].length;j++){
                    ((JButton)(getGUI().tablicaButtonow[i][j])).setEnabled(false);
                    ((JButton)(getGUI().tablicaButtonow[i][j])).setBackground(setColor(tablica[i][j]));
                    if (tablica[i][j]!=0 && tablica[i][j]!=10 && tablica[i][j]!=9)
                    ((JButton)(getGUI().tablicaButtonow[i][j])).setText((new Integer(tablica[i][j]).toString()));
                }
            }
            JOptionPane.showMessageDialog(null,"Niestety nie tym razem :-(","Probuj dalej ! ",JOptionPane.INFORMATION_MESSAGE);
            restart();
        }
        /**Obsluga zdarzenia jesli wpadnieto pole bez sasiadujacej miny*/
        else if (liczba ==0)
            fill(tablica,x,y);

        /**Obsluga zdarzenia jesli wpadnieto na pole z sasiadujaca mina i nie bedacej samej mina*/
        else {
            znak =liczba.toString();
            ((JButton)(getGUI().tablicaButtonow[x][y])).setText(znak);
            ((JButton)(getGUI().tablicaButtonow[x][y])).setEnabled(false);
            ((JButton)(getGUI().tablicaButtonow[x][y])).setBackground(setColor(liczba));
        }
        /**Sprawdzenie czy wygrano gre i obsluga jesli nastapila wygrana*/
    if(sprawdzCzyWygrana()){
     for (Point punkt: Processor.saperTablica){
             ((JButton)(getGUI().tablicaButtonow[punkt.x][punkt.y])).setEnabled(false);
             ((JButton)(getGUI().tablicaButtonow[punkt.x][punkt.y])).setText("*");
             ((JButton)(getGUI().tablicaButtonow[punkt.x][punkt.y])).setBackground(Color.GREEN);
         }
         timer.stop();
         JOptionPane.showMessageDialog(null,"Gratulacje wygrales !!Twoj czas wynosi " + seconds +" s","Wygrana",JOptionPane.INFORMATION_MESSAGE);
         restart();
     }
    }

    /**Metoda wypelniajaca sasiadujace pola ktore nie possiadaja min. metoda rekurencyjna z algorytmem flood fill*/
    public synchronized void fill( int tablica[][],int x, int y){

        if(x<0 || y<0 ||x>tablica.length-1||y>tablica[1].length-1)
        return;

        if (tablica[x][y]==0){

            ((JButton)(getGUI().tablicaButtonow[x][y])).setText("");
            ((JButton)(getGUI().tablicaButtonow[x][y])).setEnabled(false);
            tablica[x][y]=10;
            try {
                fill(tablica,x,y-1);
            }
            catch (Exception e) {}
            try {
                fill(tablica,x,y+1);
            }
            catch (Exception e) {}
            try {
                fill(tablica,x+1,y);
            }
            catch (Exception e) {}
            try {
                fill(tablica,x-1,y);
            }
            catch (Exception e) {}
            try {
                fill(tablica,x-1,y-1);
            }
            catch (Exception e) {}
            try {
                fill(tablica,x-1,y+1);
            }
            catch (Exception e) {}
            try {
                fill(tablica,x+1,y-1);
            }
            catch (Exception e) {}
            try {
                fill(tablica,x+1,y+1);
            }
            catch (Exception e) {}

        }
        if (tablica[x][y]!=0&&tablica[x][y]!=10){
            ((JButton)(getGUI().tablicaButtonow[x][y])).setText((new Integer(tablica[x][y]).toString()));
            ((JButton)(getGUI().tablicaButtonow[x][y])).setEnabled(false);
            ((JButton)(getGUI().tablicaButtonow[x][y])).setBackground(setColor(tablica[x][y]));
        }
        else
            ((JButton)(getGUI().tablicaButtonow[x][y])).setBackground(setColor(0));
    }

    /**metoda ustawiajaca referencje do obiektu gui*/
    public void setGUI(GUI gui){
        this.gui=gui;
    }

    /**metoda pobierajaca referencje do obiektu gui*/
    public GUI getGUI(){
        return gui;
}

    /**metoda zwracajaca kolor w zaleznosci od liczby z gry miny*/
    public Color setColor (int liczba){
    Color color = new Color(0,0,0);
    switch (liczba){
        case 1: {
            color=new Color(0,0,0);
            break;
        }
        case 2: {
            color=new Color(40,40,40);
            break;
        }
        case 3: {
            color=new Color(60,60,60);
            break;
        }
        case 4:{
            color=new Color(65,65,65);
            break;
        }
        case 5: {
            color=new Color(70,70,70);
            break;
        }
        case 6: {
            color=new Color(75,75,75);
            break;
        }
        case 7: {
            color=new Color(80,80,80);
            break;
        }
        case 8: {
            color=new Color(85,85,85);
            break;
        }
        case 9: {
            color=new Color(133,27,32);
            break;
        }
        default: color=new Color(20,20,20);
    }
    return  color;
    }

    /**metoda sprawdzajaca czy niekliknieta ilosc pol == ilosci min - jesli tak to wygrana*/
    public boolean  sprawdzCzyWygrana(){
        for ( int i =0; i<tablica.length;i++){
          for(int j=0; j<tablica[1].length;j++){
           if(   ( ((JButton)(getGUI().tablicaButtonow[i][j])).isEnabled())==false){
              wolnePola--;
           }
          }
    }
    if (wolnePola==iloscMin){
        return true;
    }
    wolnePola=szer*wys;
    return false;
    }

    /**metoda restartujaca gre*/
    public void restart (){
        Processor.saperTablica.clear();
        Processor.wypelnijLiczbami(tablica,iloscMin);
        seconds=0;
        getGUI().setTime("czas");
        if (timer==null)
            return;
        else {
            timer.restart();
        }
        Processor.drukuj(tablica);
        for ( int i =0; i<tablica.length;i++){
            for(int j=0; j<tablica[1].length;j++){
                ((JButton)(getGUI().tablicaButtonow[i][j])).setText(" ");
                ((JButton)(getGUI().tablicaButtonow[i][j])).setEnabled(true);
                ((JButton)(getGUI().tablicaButtonow[i][j])).setBackground(Color.LIGHT_GRAY);
            }
        }
    }

    /**metoda obsugujaca biegnacy czas w grze*/
    public void setTime()
    {
    if (timer==null){
          timer=new Timer(1000, new ActionListener() {
               @Override
              public void actionPerformed(ActionEvent e) {
                    getGUI().setTime(String.valueOf(seconds)+"s");
                  seconds++;
                }
            });
          timer.start();
     }
      else
           return;
    }

    /**metoda obsugujaca zdarzenie klikniecia przycisku info z interfejsu graficznego gui*/
    public void WyswietlInfo(){
        JOptionPane.showMessageDialog(null,"Program mina v.1.0 \n Aby wygra\u0107 musisz zgadna\u0107 wszystkie pola \n w kt\u00f3rych nie ma min, nie nadepnij na nie !!  ", " Wersja programu",JOptionPane.INFORMATION_MESSAGE);
    }

    /**metoda obsugujaca zdarzenie klikniecia przycisku ok z panelu mini ustawien gry - restartuje gre z nowa podana iloscia min*/
    public void restartZUstawieniami( int procentMin ){
        iloscMin=szer*wys*procentMin/100;
         restart();
         getGUI().setDisplay(iloscMin);
    }
    /**metoda obsugujaca zdarzenie klikniecia przycisku ok z panelu powitalnego paneluustawien ustawien gry - restartuje gre z nowa podana iloscia min i szerokoscia i wysokoscia*/
    public void startGry ( int s, int w , int iM){
        szer=s;
        wys=w;
        iloscMin=szer*wys*iM/100;
        tablica=  new int[wys][szer];
        restart();
        getGUI().setDisplay(iloscMin);

    }



}
