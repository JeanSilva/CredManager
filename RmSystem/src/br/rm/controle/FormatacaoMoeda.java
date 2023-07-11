/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.rm.controle;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 *
 * @author DEV
 */
public class FormatacaoMoeda {
    
    
    public String formatarMoeda(Double valor){
        double valorBase = valor;

        // Defina a localização desejada (neste caso, o Brasil)
        Locale brasil = new Locale("pt", "BR");

        // Crie um objeto NumberFormat para formatação de moeda
        NumberFormat formatoMoeda = NumberFormat.getCurrencyInstance(brasil);

        // Formate o valor do salário base como moeda
         String salarioFormatado = formatoMoeda.format(valorBase);
         return salarioFormatado;
    }
    public String formatarPercentual(double valor) {
         // Crie um objeto DecimalFormat para formatação de porcentagem
        DecimalFormat formatoPorcentagem = new DecimalFormat("0.#");

        // Formate o valor como uma string percentual
        String percentualFormatado = formatoPorcentagem.format(valor) + "%";

        return percentualFormatado;
    }
}
