/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.rm.controle;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

/**
 *
 * @author wagner.rodrigues
 */
public class JDecimalLabel extends JLabel{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DecimalFormat df;

    public JDecimalLabel(int casasDecimais){
        this.df = new DecimalFormat();
        
        this.df.setMaximumFractionDigits(casasDecimais);
        this.df.setMinimumFractionDigits(casasDecimais);
        
        this.setHorizontalAlignment(JLabel.LEFT);
    }
    
    public void setDoubleValue(Double valor){
        if (valor != null) {
            super.setText(this.df.format(valor));
        } else {
            super.setText("");
        }
    }
    
    public Double getDoubleValue(){
        Double valor = null;
           
        try{
            if (getText() != null && !getText().equals("")) {
                valor =  Double.parseDouble(this.getText().replaceAll("\\.", "").replaceAll(",", "."));
            } else{
              valor = 0d;
          }
        }catch (NumberFormatException ex){
            Logger.getLogger(JDecimalLabel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return valor;
    }
    
    @Override
    public void setText(String valor){
        try{
            if ( (this.df != null) && (valor != null) ){
                if (!valor.isEmpty())
                    this.setDoubleValue(this.df.parse(valor).doubleValue());
            }
        }catch (ParseException ex){
            Logger.getLogger(JDecimalLabel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}