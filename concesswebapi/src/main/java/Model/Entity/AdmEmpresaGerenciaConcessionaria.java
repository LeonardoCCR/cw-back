package Model.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class AdmEmpresaGerenciaConcessionaria {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @EqualsAndHashCode.Include
    private Long id;


    @ManyToOne
    private AdmEmpresa admEmpresa;

    @ManyToOne
    private Concessionaria concessionaria;
}
