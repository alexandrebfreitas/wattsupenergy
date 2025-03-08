package com.alexandreburghesi.app2.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A PostoMedicao.
 */
@Table("posto_medicao")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PostoMedicao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @Column("nome")
    private String nome;

    @Column("num_usina_hidreletrica")
    private Integer numUsinaHidreletrica;

    @Column("bacia")
    private String bacia;

    @Column("subbacia")
    private String subbacia;

    @Column("submercado")
    private String submercado;

    @org.springframework.data.annotation.Transient
    @JsonIgnoreProperties(value = { "numPostoMedicao" }, allowSetters = true)
    private Set<Acomph> numPostoMedicaos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PostoMedicao id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public PostoMedicao nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getNumUsinaHidreletrica() {
        return this.numUsinaHidreletrica;
    }

    public PostoMedicao numUsinaHidreletrica(Integer numUsinaHidreletrica) {
        this.setNumUsinaHidreletrica(numUsinaHidreletrica);
        return this;
    }

    public void setNumUsinaHidreletrica(Integer numUsinaHidreletrica) {
        this.numUsinaHidreletrica = numUsinaHidreletrica;
    }

    public String getBacia() {
        return this.bacia;
    }

    public PostoMedicao bacia(String bacia) {
        this.setBacia(bacia);
        return this;
    }

    public void setBacia(String bacia) {
        this.bacia = bacia;
    }

    public String getSubbacia() {
        return this.subbacia;
    }

    public PostoMedicao subbacia(String subbacia) {
        this.setSubbacia(subbacia);
        return this;
    }

    public void setSubbacia(String subbacia) {
        this.subbacia = subbacia;
    }

    public String getSubmercado() {
        return this.submercado;
    }

    public PostoMedicao submercado(String submercado) {
        this.setSubmercado(submercado);
        return this;
    }

    public void setSubmercado(String submercado) {
        this.submercado = submercado;
    }

    public Set<Acomph> getNumPostoMedicaos() {
        return this.numPostoMedicaos;
    }

    public void setNumPostoMedicaos(Set<Acomph> acomphs) {
        if (this.numPostoMedicaos != null) {
            this.numPostoMedicaos.forEach(i -> i.setNumPostoMedicao(null));
        }
        if (acomphs != null) {
            acomphs.forEach(i -> i.setNumPostoMedicao(this));
        }
        this.numPostoMedicaos = acomphs;
    }

    public PostoMedicao numPostoMedicaos(Set<Acomph> acomphs) {
        this.setNumPostoMedicaos(acomphs);
        return this;
    }

    public PostoMedicao addNumPostoMedicao(Acomph acomph) {
        this.numPostoMedicaos.add(acomph);
        acomph.setNumPostoMedicao(this);
        return this;
    }

    public PostoMedicao removeNumPostoMedicao(Acomph acomph) {
        this.numPostoMedicaos.remove(acomph);
        acomph.setNumPostoMedicao(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PostoMedicao)) {
            return false;
        }
        return getId() != null && getId().equals(((PostoMedicao) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PostoMedicao{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", numUsinaHidreletrica=" + getNumUsinaHidreletrica() +
            ", bacia='" + getBacia() + "'" +
            ", subbacia='" + getSubbacia() + "'" +
            ", submercado='" + getSubmercado() + "'" +
            "}";
    }
}
