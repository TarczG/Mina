package mina;

import mina.Controller.*;
import mina.GUI.*;

/**
 * Klasa glowna z metaoda main
 */


public class Startuj {
    /**
     * Metoda tworzaca instancje klas wzorca MVC - controlera, gui ( metody klasy procesora zaprojektowano statyczne - nie maja instancji klasy
     * @param args
     */
    public static void main(String[] args){
    Controller controller = new Controller ();
    GUI gui = new GUI(controller);
    controller.setGUI(gui);

}
}
