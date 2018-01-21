package br.com.ortiz.controller;


import br.com.ortiz.domain.CotacaoDiaria;
import br.com.ortiz.repository.CotacaoDiariaRepository;
import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.annotation.RequestAction;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.ocpsoft.rewrite.faces.annotation.Deferred;
import org.ocpsoft.rewrite.faces.annotation.IgnorePostback;
import org.primefaces.model.chart.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;


@Scope(value = "session")
@Component(value = "cotacaoController")
@ELBeanName(value = "cotacaoController")
@Join(path = "/", to = "/cotacoes.jsf")
public class CotacaoController {

    @Autowired
    private CotacaoDiariaRepository cotacaoDiariaRepository;

    private List<CotacaoDiaria> cotacoes;

    @Deferred
    @RequestAction
    @IgnorePostback
    public void loadData() {
        cotacoes = cotacaoDiariaRepository.findAll(sortByDataAsc());
    }

    public LineChartModel getGraficoCotacoes() {
        LineChartModel lineChartModel = new LineChartModel();

        LineChartSeries petr4 = new LineChartSeries();
        petr4.setLabel("petr4");
        cotacoes.forEach((CotacaoDiaria cotacao) -> {
            String data = new SimpleDateFormat("dd/MM/yyyy").format(cotacao.getData());
            BigDecimal valor = cotacao.getValor();
            petr4.set(data, valor);
        });

        lineChartModel.addSeries(petr4);

        lineChartModel.setTitle("Cotações");
        lineChartModel.setLegendPosition("e");
        lineChartModel.setShowPointLabels(true);

        Axis xAxis = new CategoryAxis("Data");
        lineChartModel.getAxes().put(AxisType.X, xAxis);
        Axis yAxis = lineChartModel.getAxis(AxisType.Y);
        yAxis.setLabel("Preço");
        yAxis.setMin(95);
        yAxis.setMax(105);

        return lineChartModel;
    }

    private Sort sortByDataAsc() {
        return new Sort(Sort.Direction.ASC, "data");
    }
}