<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="postoMedicao">
        <h2 class="jh-entity-heading" data-cy="postoMedicaoDetailsHeading">
          <span v-text="t$('wattsUpEnergyApp.postoMedicao.detail.title')"></span> {{ postoMedicao.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('wattsUpEnergyApp.postoMedicao.nome')"></span>
          </dt>
          <dd>
            <span>{{ postoMedicao.nome }}</span>
          </dd>
          <dt>
            <span v-text="t$('wattsUpEnergyApp.postoMedicao.numUsinaHidreletrica')"></span>
          </dt>
          <dd>
            <span>{{ postoMedicao.numUsinaHidreletrica }}</span>
          </dd>
          <dt>
            <span v-text="t$('wattsUpEnergyApp.postoMedicao.bacia')"></span>
          </dt>
          <dd>
            <span>{{ postoMedicao.bacia }}</span>
          </dd>
          <dt>
            <span v-text="t$('wattsUpEnergyApp.postoMedicao.subbacia')"></span>
          </dt>
          <dd>
            <span>{{ postoMedicao.subbacia }}</span>
          </dd>
          <dt>
            <span v-text="t$('wattsUpEnergyApp.postoMedicao.submercado')"></span>
          </dt>
          <dd>
            <span>{{ postoMedicao.submercado }}</span>
          </dd>
        </dl>
        <button type="submit" @click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="postoMedicao.id"
          :to="{ name: 'PostoMedicaoEdit', params: { postoMedicaoId: postoMedicao.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./posto-medicao-details.component.ts"></script>
