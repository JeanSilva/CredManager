/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.rm.controle;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.DecimalFormat;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 *
 * @author camaleaoti_2
 */
@SuppressWarnings("serial")
public class JDecimalTextField extends JTextField {

    public static final int PRECISAO_FLOAT = 0;
    
    public static final int PRECISAO_DOUBLE = 1;
    
    public static final int ESQUERDA = 1;
    
    public static final int CENTRO = 2;
    
    public static final int DIREITA = 3;
    
    private int precisao;
    
    private static DecimalFormat defaultFormat;
    

    static {
        JDecimalTextField.defaultFormat = new DecimalFormat();
        JDecimalTextField.defaultFormat.setGroupingUsed(false);
    }
    
    public JDecimalTextField(Integer precisao) {
        
//        JDecimalTextField.defaultFormat.setMaximumFractionDigits(casasDecimais);
//        JDecimalTextField.defaultFormat.setMinimumFractionDigits(casasDecimais);

        this.precisao = precisao;
//        setDocument(obterFiltroDeDocumento(casasDecimais, precisao));
        setHorizontalAlignment(JDecimalTextField.RIGHT);

        addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                Double value = JDecimalTextField.this.getDoubleValue();
                JDecimalTextField.this.setDoubleValue(value);
            }
        });
    }
    
    public JDecimalTextField(Integer casasDecimais, Integer precisao) {
        
        JDecimalTextField.defaultFormat.setMaximumFractionDigits(casasDecimais);
        JDecimalTextField.defaultFormat.setMinimumFractionDigits(casasDecimais);

        this.precisao = precisao;
        setDocument(obterFiltroDeDocumento(casasDecimais, precisao));
        setHorizontalAlignment(JDecimalTextField.RIGHT);

        addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                Double value = JDecimalTextField.this.getDoubleValue();
                JDecimalTextField.this.setDoubleValue(value);
            }
        });
    }
    
    public JDecimalTextField(Integer casasDecimais, Integer precisao, Integer alinhamento) {
        
        JDecimalTextField.defaultFormat.setMaximumFractionDigits(casasDecimais);
        JDecimalTextField.defaultFormat.setMinimumFractionDigits(casasDecimais);

        this.precisao = precisao;
        setDocument(obterFiltroDeDocumento(casasDecimais, precisao));
        alinharDecimalTableCellRenderer(alinhamento);

        addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                Double value = JDecimalTextField.this.getDoubleValue();
                JDecimalTextField.this.setDoubleValue(value);
            }
        });
    }

    public void alinharDecimalTableCellRenderer(Integer alinhamento){
        
        switch(alinhamento){
            case ESQUERDA:
                setHorizontalAlignment(JDecimalTextField.LEFT);
                break;
            case CENTRO:
                setHorizontalAlignment(JDecimalTextField.CENTER);
                break;
            default: 
                setHorizontalAlignment(JDecimalTextField.RIGHT);
                break;
        }
            
            
    }
    
     
    
    public Double getDoubleValue() {
        Double valor = null;
        try {
          if(getText() != null && !getText().equals("")){
              if(getText().matches("[0-9](.)*(\\,[0-9]{1," + defaultFormat.getMaximumFractionDigits() + "})$")){
                  valor = Double.parseDouble(getText().replace(".", "").replace(",", "."));
              }else{
                valor = Double.parseDouble(getText().replace(',', '.'));
              }
          }else{
              valor = 0d;
          }
        } catch (Exception e) {
            System.out.println(e);
        }
        return valor;
    }

    public void setDoubleValue(Double doubleValue) {
        if (doubleValue != null) {
          //  this.setText(NumeroUtil.formatar(doubleValue, 2, 2));
            this.setText(JDecimalTextField.defaultFormat.format(doubleValue));
        }
    }

    public Float getFloatValue() {
        Float valor = null;
        try {
          if(this.getText() != null && !this.getText().equals("")){
            valor = Float.parseFloat(this.getText().replace(',', '.'));
          }else{
              valor = 0F;
          }
        } catch (Exception e) {
            System.out.println(e);
        }
        return valor;
    }

    public void setFloatValue(Float floatValue) {
        if (floatValue != null) {
            this.setText(JDecimalTextField.defaultFormat.format(floatValue));
        }
    }

    public int getPrecisao() {
        return precisao;
    }

    public static PlainDocument obterFiltroDeDocumento(final int casasDecimais, final int precisao) {
        PlainDocument pd = new PlainDocument() {
            @Override
            public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                try {
                    StringBuffer nova = new StringBuffer(super.getText(0, super.getLength()));

                    nova.insert(offs, str);

                    if ((nova.toString().matches("[0-9]*(\\,[0-9]{1," + casasDecimais + "}){0,1}$")) || (nova.toString().matches("-[1-9][0-9]*(\\,[0-9]{1," + casasDecimais + "}){0,1}$"))
                            || (nova.toString().matches("[0-9]+\\,$")) || (nova.toString().matches("-[1-9][0-9]+\\,$"))
                            || (nova.toString().matches("\\,[0-9]{1," + casasDecimais + "}$"))
                            || (nova.toString().matches("[0-9](.)*(\\,[0-9]{1," + casasDecimais + "})$"))) {
                        super.insertString(offs, str, a);
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }

            @Override
            public void remove(int offs, int len) throws BadLocationException {
                try {
                    StringBuffer nova = new StringBuffer(super.getText(0, super.getLength()));
                    nova.delete(offs, offs + len);

                    if ((nova.toString().matches("[0-9]*(\\,[0-9]{1," + casasDecimais + "}){0,1}$")) || (nova.toString().matches("-[1-9][0-9]*(\\,[0-9]{1," + casasDecimais + "}){0,1}$"))
                            || (nova.toString().matches("[0-9]+\\,$")) || (nova.toString().matches("-[1-9][0-9]+\\,$"))
                            || (nova.toString().matches("\\,[0-9]{1," + casasDecimais + "}$"))
                            || (nova.toString().matches("[0-9](.)*(\\,[0-9]{1," + casasDecimais + "})$"))) {
                        super.remove(offs, len);
                    }
                } catch (Exception e) {
                    System.out.println("Except... " + e);

                    for (int i = 0; i < e.getStackTrace().length; i++) {
                        System.out.println(e.getStackTrace()[i]);

                    }
                }
            }
        };

        return pd;
    }
}