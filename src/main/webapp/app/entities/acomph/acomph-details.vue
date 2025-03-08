<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="acomph">
        <h2 class="jh-entity-heading" data-cy="acomphDetailsHeading">
          <span v-text="t$('wattsUpEnergyApp.acomph.detail.title')"></span> {{ acomph.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('wattsUpEnergyApp.acomph.date')"></span>
          </dt>
          <dd>
            <span>{{ acomph.date }}</span>
          </dd>
          <dt>
            <span v-text="t$('wattsUpEnergyApp.acomph.vazaoDefluenteLido')"></span>
          </dt>
          <dd>
            <span>{{ acomph.vazaoDefluenteLido }}</span>
          </dd>
          <dt>
            <span v-text="t$('wattsUpEnergyApp.acomph.vazaoDefluenteConsolidado')"></span>
          </dt>
          <dd>
            <span>{{ acomph.vazaoDefluenteConsolidado }}</span>
          </dd>
          <dt>
            <span v-text="t$('wattsUpEnergyApp.acomph.vazaoAfluenteLido')"></span>
          </dt>
          <dd>
            <span>{{ acomph.vazaoAfluenteLido }}</span>
          </dd>
          <dt>
            <span v-text="t$('wattsUpEnergyApp.acomph.vazaoAfluenteConsolidado')"></span>
          </dt>
          <dd>
            <span>{{ acomph.vazaoAfluenteConsolidado }}</span>
          </dd>
          <dt>
            <span v-text="t$('wattsUpEnergyApp.acomph.vazaoIncrementalConsolidado')"></span>
          </dt>
          <dd>
            <span>{{ acomph.vazaoIncrementalConsolidado }}</span>
          </dd>
          <dt>
            <span v-text="t$('wattsUpEnergyApp.acomph.vazaoNaturalConsolidado')"></span>
          </dt>
          <dd>
            <span>{{ acomph.vazaoNaturalConsolidado }}</span>
          </dd>
          <dt>
            <span v-text="t$('wattsUpEnergyApp.acomph.nivelReservatorioLido')"></span>
          </dt>
          <dd>
            <span>{{ acomph.nivelReservatorioLido }}</span>
          </dd>
          <dt>
            <span v-text="t$('wattsUpEnergyApp.acomph.nivelReservatorioConsolidado')"></span>
          </dt>
          <dd>
            <span>{{ acomph.nivelReservatorioConsolidado }}</span>
          </dd>
          <dt>
            <span v-text="t$('wattsUpEnergyApp.acomph.dataPublicacao')"></span>
          </dt>
          <dd>
            <span>{{ acomph.dataPublicacao }}</span>
          </dd>
          <dt>
            <span v-text="t$('wattsUpEnergyApp.acomph.numPostoMedicao')"></span>
          </dt>
          <dd>
            <div v-if="acomph.numPostoMedicao">
              <router-link :to="{ name: 'PostoMedicaoView', params: { postoMedicaoId: acomph.numPostoMedicao.id } }">{{
                acomph.numPostoMedicao.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" @click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="acomph.id" :to="{ name: 'AcomphEdit', params: { acomphId: acomph.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./acomph-details.component.ts"></script>
