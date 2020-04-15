package mina.Processor;

import java.awt.*;
import java.util.Random;
import java.util.Set;
import java.util.HashSet;

/**
 * Klasa processora dla wzorca MVC
 * Calosc mozna znacznie ulepszyc o dodanie np. flag
 * mozna poprawic komunnikacje poprzez dodanie klasy pomocniczej w gui bedacej wiadomoscia i jedynym wyjsciem do klasy controlera
 * mozna poprawic aplikacje o dodanie wzrocow projektowych np. state czy strategy
 */

public class Processor  {
  public static Set<Point> saperTablica=new HashSet<Point>();
    /**           Funkcja wypelniajaca zadana tablice podana iloscia min, oraz informacjami o sasiedztwie wokol danego pola dla min         */
public static void wypelnijLiczbami( int [][] tablica, int iloscmin){
    if( tablica==null)
        return;
        else{


    for ( int i =0; i<tablica.length;i++){
        for(int j=0;j<tablica[i].length;j++)
            tablica[i][j]=0;
    }

    Random generator = new Random();


    Point mina;
    while(saperTablica.size()<iloscmin){
        mina = new Point();
        mina.x=generator.nextInt(tablica.length);
        mina.y=generator.nextInt(tablica[1].length);
        saperTablica.add(mina);
    }
    for(Point punkt: saperTablica){
        tablica[punkt.x][punkt.y]=9;
    }
    int liczba =0;
    for (int i =0; i<tablica.length;i++){
        for ( int j=0; j<tablica[i].length;j++){
            if(tablica[i][j]!=9){
                liczba =0;
                try {
                    if (tablica[i - 1][j - 1] == 9)
                        liczba++;
                }
                catch (Exception e){}
                try {
                    if (tablica[i - 1][j] == 9)
                        liczba++;
                }
                catch (Exception e){}
                try {
                    if (tablica[i - 1][j + 1] == 9)
                        liczba++;
                }
                catch (Exception e){}
                try {
                    if (tablica[i ][j - 1] == 9)
                        liczba++;
                }
                catch (Exception e){}
                try {
                    if (tablica[i][j + 1] == 9)
                        liczba++;
                }
                catch (Exception e){}
                try {
                    if (tablica[i + 1][j - 1] == 9)
                        liczba++;
                }
                catch (Exception e){}
                try {
                    if (tablica[i + 1][j ] == 9)
                        liczba++;
                }
                catch (Exception e){}
                try {
                    if (tablica[i + 1][j + 1] == 9)
                        liczba++;
                }
                catch (Exception e){}

                tablica[i][j]=liczba;
            }
        }
    }
    }
}

    /**           Funkcja drukujaca wypelniona tablice         */
    public  static void drukuj(int[][] a) {
        if (a==null)
            return;
        else{


        for (int i =0; i<a.length;i++){
            for ( int j=0; j<a[i].length;j++){
                System.out.print(a[i][j]);
            }
            System.out.println();
        }
    }
    }


}
