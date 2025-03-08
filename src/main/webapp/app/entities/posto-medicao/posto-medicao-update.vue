<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate @submit.prevent="save()">
        <h2
          id="wattsUpEnergyApp.postoMedicao.home.createOrEditLabel"
          data-cy="PostoMedicaoCreateUpdateHeading"
          v-text="t$('wattsUpEnergyApp.postoMedicao.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="postoMedicao.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="postoMedicao.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('wattsUpEnergyApp.postoMedicao.nome')" for="posto-medicao-nome"></label>
            <input
              type="text"
              class="form-control"
              name="nome"
              id="posto-medicao-nome"
              data-cy="nome"
              :class="{ valid: !v$.nome.$invalid, invalid: v$.nome.$invalid }"
              v-model="v$.nome.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('wattsUpEnergyApp.postoMedicao.numUsinaHidreletrica')"
              for="posto-medicao-numUsinaHidreletrica"
            ></label>
            <input
              type="number"
              class="form-control"
              name="numUsinaHidreletrica"
              id="posto-medicao-numUsinaHidreletrica"
              data-cy="numUsinaHidreletrica"
              :class="{ valid: !v$.numUsinaHidreletrica.$invalid, invalid: v$.numUsinaHidreletrica.$invalid }"
              v-model.number="v$.numUsinaHidreletrica.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('wattsUpEnergyApp.postoMedicao.bacia')" for="posto-medicao-bacia"></label>
            <input
              type="text"
              class="form-control"
              name="bacia"
              id="posto-medicao-bacia"
              data-cy="bacia"
              :class="{ valid: !v$.bacia.$invalid, invalid: v$.bacia.$invalid }"
              v-model="v$.bacia.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('wattsUpEnergyApp.postoMedicao.subbacia')" for="posto-medicao-subbacia"></label>
            <input
              type="text"
              class="form-control"
              name="subbacia"
              id="posto-medicao-subbacia"
              data-cy="subbacia"
              :class="{ valid: !v$.subbacia.$invalid, invalid: v$.subbacia.$invalid }"
              v-model="v$.subbacia.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('wattsUpEnergyApp.postoMedicao.submercado')"
              for="posto-medicao-submercado"
            ></label>
            <input
              type="text"
              class="form-control"
              name="submercado"
              id="posto-medicao-submercado"
              data-cy="submercado"
              :class="{ valid: !v$.submercado.$invalid, invalid: v$.submercado.$invalid }"
              v-model="v$.submercado.$model"
            />
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" @click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.cancel')"></span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="v$.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.save')"></span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./posto-medicao-update.component.ts"></script>
