/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.rm.veiw;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;

public class PieChartExample extends JFrame {

    public PieChartExample() {
        setTitle("Exemplo de Gráfico de Pizza");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Criar um conjunto de dados para o gráfico
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Lucro 1", 2000);
        dataset.setValue("Lucro 2", 3000);
        dataset.setValue("Lucro 3", 1500);
        dataset.setValue("Lucro 4", 2500);

        // Criar o gráfico de pizza usando o conjunto de dados
        JFreeChart chart = ChartFactory.createPieChart(
                "Relatório de Lucros",
                dataset,
                true, // exibir legenda
                true, // exibir dicas de ferramentas (tooltips)
                false // URLs de links (não usados aqui)
        );

        // Personalizar o gráfico, se necessário
        // Por exemplo, alterar as cores das fatias do gráfico:
        /*PiePlot plot = (PiePlot) chart.getPlot();
        plot.setSectionPaint("Lucro 1", Color.BLUE);
        plot.setSectionPaint("Lucro 2", Color.GREEN);
        plot.setSectionPaint("Lucro 3", Color.RED);
        plot.setSectionPaint("Lucro 4", Color.ORANGE);*/

        // Criar o painel do gráfico e adicioná-lo à janela
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(600, 400));
        add(chartPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PieChartExample example = new PieChartExample();
            example.setVisible(true);
        });
    }
}
