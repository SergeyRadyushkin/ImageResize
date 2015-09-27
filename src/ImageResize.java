/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imageresize;

import java.text.ParseException;
import javax.swing.JDialog;

/**
 *
 * @author Sergey Radyushkin
 */
public class ImageResize {
     public static void main(String[] args) throws ParseException {
        GUI createUI = new GUI();
        createUI.setVisible(true);
        createUI.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }
}
