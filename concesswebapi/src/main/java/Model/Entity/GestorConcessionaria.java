package Model.Entity;


import javax.persistence.ManyToOne;

public class GestorConcessionaria {

    @ManyToOne
    private Gestor gestor;//aponta para gestor

    @ManyToOne
    private Concessionaria concessionaria; //aponta para gestor
}
