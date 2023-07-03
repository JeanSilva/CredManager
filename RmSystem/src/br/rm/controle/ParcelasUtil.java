/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.rm.controle;

/**
 *
 * @author DEV
 */
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ParcelasUtil {
    public  List<Date> calcularParcelas(Date dataInicial, int intervalo, int numParcelas, String tipoVencimento) {
        List<Date> parcelas = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dataInicial);

        for (int i = 0; i < numParcelas; i++) {
            // Adiciona o intervalo ao calendário
            calendar.add(Calendar.DAY_OF_MONTH, intervalo);

            // Verifica o tipo de vencimento
            if (tipoVencimento.equals("segunda/sexta")) {
                while (isFimDeSemana(calendar)) {
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                }
            } else if (tipoVencimento.equals("segunda/sabado")) {
                if (isDomingo(calendar)) {
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                }
            }
            
            // Adiciona a data da parcela à lista
            parcelas.add(calendar.getTime());
        }

        return parcelas;
    }

    private static boolean isFimDeSemana(Calendar calendar) {
        int diaSemana = calendar.get(Calendar.DAY_OF_WEEK);
        return diaSemana == Calendar.SATURDAY || diaSemana == Calendar.SUNDAY;
    }

    private static boolean isDomingo(Calendar calendar) {
        int diaSemana = calendar.get(Calendar.DAY_OF_WEEK);
        return diaSemana == Calendar.SUNDAY;
    }
}
