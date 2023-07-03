package br.rm.veiw;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Parcela {
     public static List<Date> calcularParcelas(Date dataInicial, int intervaloDias, int numParcelas) {
        List<Date> parcelas = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dataInicial);
        for (int i = 0; i < numParcelas; i++) {
            // Adiciona intervalo em dias à data atual
            calendar.add(Calendar.DAY_OF_MONTH, intervaloDias);

            // Verifica se a data é domingo e ajusta para o próximo dia útil
            int diaSemana = calendar.get(Calendar.DAY_OF_WEEK);
            if (diaSemana == Calendar.SUNDAY) {
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            }

            // Adiciona juros de 10% diário para parcelas em atraso
            Date dataParcela = calendar.getTime();
            if (dataParcela.before(new Date())) {
                double valorJuros = 0.1 * (dataParcela.getTime() - new Date().getTime()) / (24 * 60 * 60 * 1000);
                parcelas.add(new Date(dataParcela.getTime() + Math.round(valorJuros * parcelas.get(i - 1).getTime())));
            } else {
                parcelas.add(dataParcela);
            }
        }
        return parcelas;
    }
     public static void main(String[] args) {
        Date dataAtual = new Date();
        double valorTotal = 1000.0;
        int numParcelas = 5;
        int intervaloDias = 1;

        List<Date> vencimentos = calcularParcelas(dataAtual, intervaloDias, numParcelas );

        System.out.println("Vencimentos:");
        for (Date vencimento : vencimentos) {
            System.out.println(vencimento);
        }
    }
     
}
