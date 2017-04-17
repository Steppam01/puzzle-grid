/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzlegridrestruct;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import org.apache.commons.math3.util.ArithmeticUtils;

/**
 *
 * @author nevee_000
 */
public class GridController {
    private Grid model;
    private PuzzleGridRestruct view;
    
    public GridController(Grid model, PuzzleGridRestruct view){
        this.model = model;
        this.view = view;
    }
    
    public static long calculate(int leftNode, int upNode){
        long fact = ArithmeticUtils.factorial(leftNode + upNode)/(ArithmeticUtils.factorial(leftNode) * ArithmeticUtils.factorial(upNode));
        return fact;
    }
}
