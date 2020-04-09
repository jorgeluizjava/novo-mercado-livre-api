package br.com.deveficiente.nossomercadolivreapi.categoria;

public class OrdenacaoCategoria {

    private boolean ordenaPorSuperior;

    public OrdenacaoCategoria(boolean ordenaPorSuperior) {
        this.ordenaPorSuperior = ordenaPorSuperior;
    }

    public boolean isOrdenaPorSuperior() {
        return ordenaPorSuperior;
    }
}
