/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.unimontes.ccet.dcc.pg1.view.panels;

import javax.swing.JPanel;

import br.unimontes.ccet.dcc.pg1.view.interfaces.PanelInterface;
import java.awt.Component;
import java.util.HashMap;

/**
 *
 * @author Avell
 */
public class PanelPG1 extends JPanel implements PanelInterface {

    @Override
    public HashMap<String, Component> getMapComponents() {
        HashMap<String, java.awt.Component> map = new HashMap<>();
        Component[] vetor = this.getComponents();
        
        // a propriedade NAME do componente deve estar preenchida!!!
        for(int i=0; i<vetor.length; i++){
            map.put(vetor[i].getName(), vetor[i]);
        }
        
        /*
        for(Component c: vetor){
            map.put(c.getName(), c);
        }
        */
        
        return map;
    }
    
}
