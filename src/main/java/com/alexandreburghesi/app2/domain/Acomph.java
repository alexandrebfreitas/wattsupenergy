package com.alexandreburghesi.app2.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A Acomph.
 */
@Table("acomph")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Acomph implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @Column("date")
    private LocalDate date;

    @Column("vazao_defluente_lido")
    private Double vazaoDefluenteLido;

    @Column("vazao_defluente_consolidado")
    private Double vazaoDefluenteConsolidado;

    @Column("vazao_afluente_lido")
    private Double vazaoAfluenteLido;

    @Column("vazao_afluente_consolidado")
    private Double vazaoAfluenteConsolidado;

    @Column("vazao_incremental_consolidado")
    private Double vazaoIncrementalConsolidado;

    @Column("vazao_natural_consolidado")
    private Double vazaoNaturalConsolidado;

    @Column("nivel_reservatorio_lido")
    private Double nivelReservatorioLido;

    @Column("nivel_reservatorio_consolidado")
    private Double nivelReservatorioConsolidado;

    @Column("data_publicacao")
    private LocalDate dataPublicacao;

    @org.springframework.data.annotation.Transient
    @JsonIgnoreProperties(value = { "numPostoMedicaos" }, allowSetters = true)
    private PostoMedicao numPostoMedicao;

    @Column("num_posto_medicao_id")
    private Long numPostoMedicaoId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Acomph id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public Acomph date(LocalDate date) {
        this.setDate(date);
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getVazaoDefluenteLido() {
        return this.vazaoDefluenteLido;
    }

    public Acomph vazaoDefluenteLido(Double vazaoDefluenteLido) {
        this.setVazaoDefluenteLido(vazaoDefluenteLido);
        return this;
    }

    public void setVazaoDefluenteLido(Double vazaoDefluenteLido) {
        this.vazaoDefluenteLido = vazaoDefluenteLido;
    }

    public Double getVazaoDefluenteConsolidado() {
        return this.vazaoDefluenteConsolidado;
    }

    public Acomph vazaoDefluenteConsolidado(Double vazaoDefluenteConsolidado) {
        this.setVazaoDefluenteConsolidado(vazaoDefluenteConsolidado);
        return this;
    }

    public void setVazaoDefluenteConsolidado(Double vazaoDefluenteConsolidado) {
        this.vazaoDefluenteConsolidado = vazaoDefluenteConsolidado;
    }

    public Double getVazaoAfluenteLido() {
        return this.vazaoAfluenteLido;
    }

    public Acomph vazaoAfluenteLido(Double vazaoAfluenteLido) {
        this.setVazaoAfluenteLido(vazaoAfluenteLido);
        return this;
    }

    public void setVazaoAfluenteLido(Double vazaoAfluenteLido) {
        this.vazaoAfluenteLido = vazaoAfluenteLido;
    }

    public Double getVazaoAfluenteConsolidado() {
        return this.vazaoAfluenteConsolidado;
    }

    public Acomph vazaoAfluenteConsolidado(Double vazaoAfluenteConsolidado) {
        this.setVazaoAfluenteConsolidado(vazaoAfluenteConsolidado);
        return this;
    }

    public void setVazaoAfluenteConsolidado(Double vazaoAfluenteConsolidado) {
        this.vazaoAfluenteConsolidado = vazaoAfluenteConsolidado;
    }

    public Double getVazaoIncrementalConsolidado() {
        return this.vazaoIncrementalConsolidado;
    }

    public Acomph vazaoIncrementalConsolidado(Double vazaoIncrementalConsolidado) {
        this.setVazaoIncrementalConsolidado(vazaoIncrementalConsolidado);
        return this;
    }

    public void setVazaoIncrementalConsolidado(Double vazaoIncrementalConsolidado) {
        this.vazaoIncrementalConsolidado = vazaoIncrementalConsolidado;
    }

    public Double getVazaoNaturalConsolidado() {
        return this.vazaoNaturalConsolidado;
    }

    public Acomph vazaoNaturalConsolidado(Double vazaoNaturalConsolidado) {
        this.setVazaoNaturalConsolidado(vazaoNaturalConsolidado);
        return this;
    }

    public void setVazaoNaturalConsolidado(Double vazaoNaturalConsolidado) {
        this.vazaoNaturalConsolidado = vazaoNaturalConsolidado;
    }

    public Double getNivelReservatorioLido() {
        return this.nivelReservatorioLido;
    }

    public Acomph nivelReservatorioLido(Double nivelReservatorioLido) {
        this.setNivelReservatorioLido(nivelReservatorioLido);
        return this;
    }

    public void setNivelReservatorioLido(Double nivelReservatorioLido) {
        this.nivelReservatorioLido = nivelReservatorioLido;
    }

    public Double getNivelReservatorioConsolidado() {
        return this.nivelReservatorioConsolidado;
    }

    public Acomph nivelReservatorioConsolidado(Double nivelReservatorioConsolidado) {
        this.setNivelReservatorioConsolidado(nivelReservatorioConsolidado);
        return this;
    }

    public void setNivelReservatorioConsolidado(Double nivelReservatorioConsolidado) {
        this.nivelReservatorioConsolidado = nivelReservatorioConsolidado;
    }

    public LocalDate getDataPublicacao() {
        return this.dataPublicacao;
    }

    public Acomph dataPublicacao(LocalDate dataPublicacao) {
        this.setDataPublicacao(dataPublicacao);
        return this;
    }

    public void setDataPublicacao(LocalDate dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public PostoMedicao getNumPostoMedicao() {
        return this.numPostoMedicao;
    }

    public void setNumPostoMedicao(PostoMedicao postoMedicao) {
        this.numPostoMedicao = postoMedicao;
        this.numPostoMedicaoId = postoMedicao != null ? postoMedicao.getId() : null;
    }

    public Acomph numPostoMedicao(PostoMedicao postoMedicao) {
        this.setNumPostoMedicao(postoMedicao);
        return this;
    }

    public Long getNumPostoMedicaoId() {
        return this.numPostoMedicaoId;
    }

    public void setNumPostoMedicaoId(Long postoMedicao) {
        this.numPostoMedicaoId = postoMedicao;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Acomph)) {
            return false;
        }
        return getId() != null && getId().equals(((Acomph) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Acomph{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", vazaoDefluenteLido=" + getVazaoDefluenteLido() +
            ", vazaoDefluenteConsolidado=" + getVazaoDefluenteConsolidado() +
            ", vazaoAfluenteLido=" + getVazaoAfluenteLido() +
            ", vazaoAfluenteConsolidado=" + getVazaoAfluenteConsolidado() +
            ", vazaoIncrementalConsolidado=" + getVazaoIncrementalConsolidado() +
            ", vazaoNaturalConsolidado=" + getVazaoNaturalConsolidado() +
            ", nivelReservatorioLido=" + getNivelReservatorioLido() +
            ", nivelReservatorioConsolidado=" + getNivelReservatorioConsolidado() +
            ", dataPublicacao='" + getDataPublicacao() + "'" +
            "}";
    }
}
