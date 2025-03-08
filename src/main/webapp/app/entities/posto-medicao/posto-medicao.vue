<template>
  <div>
    <h2 id="page-heading" data-cy="PostoMedicaoHeading">
      <span v-text="t$('wattsUpEnergyApp.postoMedicao.home.title')" id="posto-medicao-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" @click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('wattsUpEnergyApp.postoMedicao.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'PostoMedicaoCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-posto-medicao"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('wattsUpEnergyApp.postoMedicao.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && postoMedicaos && postoMedicaos.length === 0">
      <span v-text="t$('wattsUpEnergyApp.postoMedicao.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="postoMedicaos && postoMedicaos.length > 0">
      <table class="table table-striped" aria-describedby="postoMedicaos">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('wattsUpEnergyApp.postoMedicao.nome')"></span></th>
            <th scope="row"><span v-text="t$('wattsUpEnergyApp.postoMedicao.numUsinaHidreletrica')"></span></th>
            <th scope="row"><span v-text="t$('wattsUpEnergyApp.postoMedicao.bacia')"></span></th>
            <th scope="row"><span v-text="t$('wattsUpEnergyApp.postoMedicao.subbacia')"></span></th>
            <th scope="row"><span v-text="t$('wattsUpEnergyApp.postoMedicao.submercado')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="postoMedicao in postoMedicaos" :key="postoMedicao.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'PostoMedicaoView', params: { postoMedicaoId: postoMedicao.id } }">{{
                postoMedicao.id
              }}</router-link>
            </td>
            <td>{{ postoMedicao.nome }}</td>
            <td>{{ postoMedicao.numUsinaHidreletrica }}</td>
            <td>{{ postoMedicao.bacia }}</td>
            <td>{{ postoMedicao.subbacia }}</td>
            <td>{{ postoMedicao.submercado }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'PostoMedicaoView', params: { postoMedicaoId: postoMedicao.id } }"
                  class="btn btn-info btn-sm details"
                  data-cy="entityDetailsButton"
                >
                  <font-awesome-icon icon="eye"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                </router-link>
                <router-link
                  :to="{ name: 'PostoMedicaoEdit', params: { postoMedicaoId: postoMedicao.id } }"
                  class="btn btn-primary btn-sm edit"
                  data-cy="entityEditButton"
                >
                  <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                </router-link>
                <b-button
                  @click="prepareRemove(postoMedicao)"
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
        <span
          id="wattsUpEnergyApp.postoMedicao.delete.question"
          data-cy="postoMedicaoDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-postoMedicao-heading" v-text="t$('wattsUpEnergyApp.postoMedicao.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" @click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-postoMedicao"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            @click="removePostoMedicao()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./posto-medicao.component.ts"></script>
