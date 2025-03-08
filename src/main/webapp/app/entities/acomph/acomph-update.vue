<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate @submit.prevent="save()">
        <h2
          id="wattsUpEnergyApp.acomph.home.createOrEditLabel"
          data-cy="AcomphCreateUpdateHeading"
          v-text="t$('wattsUpEnergyApp.acomph.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="acomph.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="acomph.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('wattsUpEnergyApp.acomph.date')" for="acomph-date"></label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="acomph-date"
                  v-model="v$.date.$model"
                  name="date"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="acomph-date"
                data-cy="date"
                type="text"
                class="form-control"
                name="date"
                :class="{ valid: !v$.date.$invalid, invalid: v$.date.$invalid }"
                v-model="v$.date.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('wattsUpEnergyApp.acomph.vazaoDefluenteLido')"
              for="acomph-vazaoDefluenteLido"
            ></label>
            <input
              type="number"
              class="form-control"
              name="vazaoDefluenteLido"
              id="acomph-vazaoDefluenteLido"
              data-cy="vazaoDefluenteLido"
              :class="{ valid: !v$.vazaoDefluenteLido.$invalid, invalid: v$.vazaoDefluenteLido.$invalid }"
              v-model.number="v$.vazaoDefluenteLido.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('wattsUpEnergyApp.acomph.vazaoDefluenteConsolidado')"
              for="acomph-vazaoDefluenteConsolidado"
            ></label>
            <input
              type="number"
              class="form-control"
              name="vazaoDefluenteConsolidado"
              id="acomph-vazaoDefluenteConsolidado"
              data-cy="vazaoDefluenteConsolidado"
              :class="{ valid: !v$.vazaoDefluenteConsolidado.$invalid, invalid: v$.vazaoDefluenteConsolidado.$invalid }"
              v-model.number="v$.vazaoDefluenteConsolidado.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('wattsUpEnergyApp.acomph.vazaoAfluenteLido')"
              for="acomph-vazaoAfluenteLido"
            ></label>
            <input
              type="number"
              class="form-control"
              name="vazaoAfluenteLido"
              id="acomph-vazaoAfluenteLido"
              data-cy="vazaoAfluenteLido"
              :class="{ valid: !v$.vazaoAfluenteLido.$invalid, invalid: v$.vazaoAfluenteLido.$invalid }"
              v-model.number="v$.vazaoAfluenteLido.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('wattsUpEnergyApp.acomph.vazaoAfluenteConsolidado')"
              for="acomph-vazaoAfluenteConsolidado"
            ></label>
            <input
              type="number"
              class="form-control"
              name="vazaoAfluenteConsolidado"
              id="acomph-vazaoAfluenteConsolidado"
              data-cy="vazaoAfluenteConsolidado"
              :class="{ valid: !v$.vazaoAfluenteConsolidado.$invalid, invalid: v$.vazaoAfluenteConsolidado.$invalid }"
              v-model.number="v$.vazaoAfluenteConsolidado.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('wattsUpEnergyApp.acomph.vazaoIncrementalConsolidado')"
              for="acomph-vazaoIncrementalConsolidado"
            ></label>
            <input
              type="number"
              class="form-control"
              name="vazaoIncrementalConsolidado"
              id="acomph-vazaoIncrementalConsolidado"
              data-cy="vazaoIncrementalConsolidado"
              :class="{ valid: !v$.vazaoIncrementalConsolidado.$invalid, invalid: v$.vazaoIncrementalConsolidado.$invalid }"
              v-model.number="v$.vazaoIncrementalConsolidado.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('wattsUpEnergyApp.acomph.vazaoNaturalConsolidado')"
              for="acomph-vazaoNaturalConsolidado"
            ></label>
            <input
              type="number"
              class="form-control"
              name="vazaoNaturalConsolidado"
              id="acomph-vazaoNaturalConsolidado"
              data-cy="vazaoNaturalConsolidado"
              :class="{ valid: !v$.vazaoNaturalConsolidado.$invalid, invalid: v$.vazaoNaturalConsolidado.$invalid }"
              v-model.number="v$.vazaoNaturalConsolidado.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('wattsUpEnergyApp.acomph.nivelReservatorioLido')"
              for="acomph-nivelReservatorioLido"
            ></label>
            <input
              type="number"
              class="form-control"
              name="nivelReservatorioLido"
              id="acomph-nivelReservatorioLido"
              data-cy="nivelReservatorioLido"
              :class="{ valid: !v$.nivelReservatorioLido.$invalid, invalid: v$.nivelReservatorioLido.$invalid }"
              v-model.number="v$.nivelReservatorioLido.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('wattsUpEnergyApp.acomph.nivelReservatorioConsolidado')"
              for="acomph-nivelReservatorioConsolidado"
            ></label>
            <input
              type="number"
              class="form-control"
              name="nivelReservatorioConsolidado"
              id="acomph-nivelReservatorioConsolidado"
              data-cy="nivelReservatorioConsolidado"
              :class="{ valid: !v$.nivelReservatorioConsolidado.$invalid, invalid: v$.nivelReservatorioConsolidado.$invalid }"
              v-model.number="v$.nivelReservatorioConsolidado.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('wattsUpEnergyApp.acomph.dataPublicacao')" for="acomph-dataPublicacao"></label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="acomph-dataPublicacao"
                  v-model="v$.dataPublicacao.$model"
                  name="dataPublicacao"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="acomph-dataPublicacao"
                data-cy="dataPublicacao"
                type="text"
                class="form-control"
                name="dataPublicacao"
                :class="{ valid: !v$.dataPublicacao.$invalid, invalid: v$.dataPublicacao.$invalid }"
                v-model="v$.dataPublicacao.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('wattsUpEnergyApp.acomph.numPostoMedicao')" for="acomph-numPostoMedicao"></label>
            <select
              class="form-control"
              id="acomph-numPostoMedicao"
              data-cy="numPostoMedicao"
              name="numPostoMedicao"
              v-model="acomph.numPostoMedicao"
            >
              <option :value="null"></option>
              <option
                :value="
                  acomph.numPostoMedicao && postoMedicaoOption.id === acomph.numPostoMedicao.id
                    ? acomph.numPostoMedicao
                    : postoMedicaoOption
                "
                v-for="postoMedicaoOption in postoMedicaos"
                :key="postoMedicaoOption.id"
              >
                {{ postoMedicaoOption.id }}
              </option>
            </select>
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
<script lang="ts" src="./acomph-update.component.ts"></script>
