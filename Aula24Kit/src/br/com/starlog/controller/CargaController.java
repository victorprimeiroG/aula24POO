package br.com.starlog.controller;

import br.com.starlog.model.Carga;
import br.com.starlog.view.TelaCadastroCarga;
import java.util.ArrayList;
import java.util.List;

public class CargaController {

    private final List<Carga> listaCargas = new ArrayList<>();

    // Construtor padrão — o MainApp chama new CargaController()
    public CargaController() {
    }

    public void cadastrarCarga(String codigo, String categoria, String pesoStr, String valorStr, TelaCadastroCarga tela) {
        try {
            double peso = Double.parseDouble(pesoStr.trim());
            double valor = Double.parseDouble(valorStr.trim());

            Carga carga = new Carga(codigo, categoria, peso, valor);
            listaCargas.add(carga);

            tela.atualizarRelatorio(gerarTextoRelatorio());
            tela.exibirMensagemSucesso("Carga cadastrada com sucesso!");
            tela.limparCampos();

        } catch (NumberFormatException ex) {
            tela.exibirMensagemErro("Erro de formato numérico: digite apenas números válidos para Peso e Valor!");
        } catch (IllegalArgumentException ex) {
            tela.exibirMensagemErro("Regra de negócio violada: " + ex.getMessage());
        }
    }

    /**
     * Retorna a lista de cargas como texto formatado para a JTextArea da View.
     */
    public String gerarTextoRelatorio() {
        if (listaCargas.isEmpty()) {
            return "Nenhuma carga registrada.";
        }

        StringBuilder sb = new StringBuilder();
        double pesoTotal = 0.0;
        double seguroTotal = 0.0;

        for (Carga c : listaCargas) {
            sb.append(String.format("%s | %s | %.1f kg | R$ %.2f | Seguro: R$ %.2f%n",
                    c.getCodigoRastreio(),
                    c.getCategoria(),
                    c.getPeso(),
                    c.getValorDeclarado(),
                    c.calcularSeguro()));

            pesoTotal += c.getPeso();
            seguroTotal += c.calcularSeguro();
        }

        sb.append(String.format("%nTOTAL: Peso %.1f kg | Seguro R$ %.2f", pesoTotal, seguroTotal));
        return sb.toString();
    }

    public void limparDados() {
        listaCargas.clear();
    }

    public int getTotalCargas() {
        return listaCargas.size();
    }
    public List<Carga> getRepositorio() {
        return listaCargas;
    }
}