/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.rm.controle;

import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 *
 * @author camaleaoti_2
 */

public class JIntegerTextField extends JFormattedTextField{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final int PRECISAO_LONG = 0;
    public static final int PRECISAO_INTEGER = 1;
    public static final int PRECISAO_SHORT = 2;

    private int precisao;
    private Integer limiteDeCaracteres;

    public JIntegerTextField(int precisao){
        this.setDocument(obterFiltroDeDocumento(precisao, 0));
        this.setHorizontalAlignment(JDecimalTextField.RIGHT);

        this.precisao = precisao;
    }

    public JIntegerTextField(int precisao, Integer limiteDeCaracteres){
        this.setDocument(obterFiltroDeDocumento(precisao, limiteDeCaracteres));
        this.setHorizontalAlignment(JDecimalTextField.RIGHT);

        this.precisao = precisao;
        this.limiteDeCaracteres = limiteDeCaracteres;
    }

    public Double getDoubleValue() {
        Double valor = null;
        try {
          if(this.getText() != null && !this.getText().equals("")){
            valor = Double.parseDouble(this.getText().replace(',', '.'));
          }else{
              valor = 0d;
          }
        } catch (Exception e) {
            System.out.println(e);
        }
        return valor;
    }
    
    public Integer getIntValue(){
        Integer valor = null;
        try {
            if(getText() != null && !getText().equals("")){
            valor = Integer.parseInt(getText());
        }else{
                valor = 0;
            }
        }
        catch (NumberFormatException e)
        {
            System.out.println(e);
        }
      return valor;
    }

    public void setIntValue(Integer intValue){
       try{
           if (intValue != null) {
               setValue(String.valueOf(""+intValue));
           }
       }catch(NumberFormatException e){
           System.out.println("Erro: [Int] " + e);
       }
    }

    public Long getLongValue(){
      Long valor = null;
      try {
            if(getText() != null && !getText().equals("")){
            valor = Long.parseLong(getText());
        }else{
                valor = 0L;
            }
        }catch (NumberFormatException e){
            System.out.println(e);
        }
      return valor;
    }

    public void setLongValue(Long longValue){
        try{
            if (longValue != null)
                this.setValue(longValue.toString());
        }catch(Exception e){
            System.out.println("Erro: [Long] " + e);
        }
    }

    public Short getShortValue(){
     Short ret = null;
      String valor = getText();
      if(!valor.isEmpty()){
        try{
            ret = Short.parseShort(valor);
        }catch (Exception e){
            System.out.println(e);
        }
      }
      return ret;
    }

    public void setShortValue(Short shortValue){
        try{
        	if (shortValue != null){
        		this.setValue(shortValue.toString());
        	}
        }catch(Exception e){
            System.out.println("Erro: [Short] " + e);
        }
    }

    public int getPrecisao(){
        return precisao;
    }

    public void setPrecisao(int precisao){
        this.precisao = precisao;
    }

    public static PlainDocument obterFiltroDeDocumento(final int precisao, final int limiteDeCaracteres){
        PlainDocument pd = new PlainDocument(){
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public void insertString(int offs, String str, AttributeSet a) throws BadLocationException{
                try{
                    StringBuffer nova = new StringBuffer(super.getText(0, super.getLength()));

                    nova.insert(offs, str);

                    if (limiteDeCaracteres > 0){
                        if (nova.toString().matches("[0-9]{0,"+limiteDeCaracteres+"}")){
                            super.insertString(offs, str, a);
                        }
                    }else{
                        if (nova.toString().matches("[0-9]*")){
                            super.insertString(offs, str, a);
                        }
                    }
                }catch(Exception e){
                    System.out.println(e);
                }
            }

            @Override
            public void remove(int offs, int len) throws BadLocationException{
                try{
                    StringBuffer nova = new StringBuffer(super.getText(0, super.getLength()));
                    nova.delete(offs, offs+len);

                    if (limiteDeCaracteres > 0){
                        if (nova.toString().matches("[0-9]{0,"+limiteDeCaracteres+"}")){
                            super.remove(offs, len);
                        }
                    }else{
                        if (nova.toString().matches("[0-9]*")){
                            super.remove(offs, len);
                        }
                    }
                }catch(Exception e){
                    System.out.println("Except... " + e);

                    for (int i = 0; i < e.getStackTrace().length; i++){
                        System.out.println(e.getStackTrace()[i]);

                    }
                }
            }
        };

        return pd;
    }
    
    public class FiltroDeTexto extends PlainDocument{
    	
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		private Integer limiteDeCaracteres;

        public FiltroDeTexto(Integer limiteDeCaracteres){
            this.limiteDeCaracteres = limiteDeCaracteres;
        }
        
        @Override
        public void insertString(int offs, String str, AttributeSet a) throws BadLocationException{
            try{
                StringBuilder nova = new StringBuilder(super.getText(0, super.getLength()));

                nova.insert(offs, str);

                if (this.limiteDeCaracteres > 0){
                    if (nova.toString().matches("[0-9]{0,"+this.limiteDeCaracteres+"}"))
                        super.insertString(offs, str, a);
                }else{
                    if (nova.toString().matches("[0-9]*")){
                        super.insertString(offs, str, a);
                    }
                }
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

	public Integer getLimiteDeCaracteres() {
		return limiteDeCaracteres;
	}

	public void setLimiteDeCaracteres(Integer limiteDeCaracteres) {
		this.limiteDeCaracteres = limiteDeCaracteres;
	}
}