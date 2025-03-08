<template>
  <div>
    <h2 id="page-heading" data-cy="AcomphHeading">
      <span v-text="t$('wattsUpEnergyApp.acomph.home.title')" id="acomph-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" @click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('wattsUpEnergyApp.acomph.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'AcomphCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-acomph"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('wattsUpEnergyApp.acomph.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && acomphs && acomphs.length === 0">
      <span v-text="t$('wattsUpEnergyApp.acomph.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="acomphs && acomphs.length > 0">
      <table class="table table-striped" aria-describedby="acomphs">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('wattsUpEnergyApp.acomph.date')"></span></th>
            <th scope="row"><span v-text="t$('wattsUpEnergyApp.acomph.vazaoDefluenteLido')"></span></th>
            <th scope="row"><span v-text="t$('wattsUpEnergyApp.acomph.vazaoDefluenteConsolidado')"></span></th>
            <th scope="row"><span v-text="t$('wattsUpEnergyApp.acomph.vazaoAfluenteLido')"></span></th>
            <th scope="row"><span v-text="t$('wattsUpEnergyApp.acomph.vazaoAfluenteConsolidado')"></span></th>
            <th scope="row"><span v-text="t$('wattsUpEnergyApp.acomph.vazaoIncrementalConsolidado')"></span></th>
            <th scope="row"><span v-text="t$('wattsUpEnergyApp.acomph.vazaoNaturalConsolidado')"></span></th>
            <th scope="row"><span v-text="t$('wattsUpEnergyApp.acomph.nivelReservatorioLido')"></span></th>
            <th scope="row"><span v-text="t$('wattsUpEnergyApp.acomph.nivelReservatorioConsolidado')"></span></th>
            <th scope="row"><span v-text="t$('wattsUpEnergyApp.acomph.dataPublicacao')"></span></th>
            <th scope="row"><span v-text="t$('wattsUpEnergyApp.acomph.numPostoMedicao')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="acomph in acomphs" :key="acomph.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'AcomphView', params: { acomphId: acomph.id } }">{{ acomph.id }}</router-link>
            </td>
            <td>{{ acomph.date }}</td>
            <td>{{ acomph.vazaoDefluenteLido }}</td>
            <td>{{ acomph.vazaoDefluenteConsolidado }}</td>
            <td>{{ acomph.vazaoAfluenteLido }}</td>
            <td>{{ acomph.vazaoAfluenteConsolidado }}</td>
            <td>{{ acomph.vazaoIncrementalConsolidado }}</td>
            <td>{{ acomph.vazaoNaturalConsolidado }}</td>
            <td>{{ acomph.nivelReservatorioLido }}</td>
            <td>{{ acomph.nivelReservatorioConsolidado }}</td>
            <td>{{ acomph.dataPublicacao }}</td>
            <td>
              <div v-if="acomph.numPostoMedicao">
                <router-link :to="{ name: 'PostoMedicaoView', params: { postoMedicaoId: acomph.numPostoMedicao.id } }">{{
                  acomph.numPostoMedicao.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'AcomphView', params: { acomphId: acomph.id } }"
                  class="btn btn-info btn-sm details"
                  data-cy="entityDetailsButton"
                >
                  <font-awesome-icon icon="eye"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                </router-link>
                <router-link
                  :to="{ name: 'AcomphEdit', params: { acomphId: acomph.id } }"
                  class="btn btn-primary btn-sm edit"
                  data-cy="entityEditButton"
                >
                  <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                </router-link>
                <b-button
                  @click="prepareRemove(acomph)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.delete')"></span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <template #modal-title>
        <span id="wattsUpEnergyApp.acomph.delete.question" data-cy="acomphDeleteDialogHeading" v-text="t$('entity.delete.title')"></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-acomph-heading" v-text="t$('wattsUpEnergyApp.acomph.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" @click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-acomph"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            @click="removeAcomph()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./acomph.component.ts"></script>
