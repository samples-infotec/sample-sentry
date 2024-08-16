<template>
  <div>
    <h2 id="page-heading" data-cy="ConvocatoriaHeading">
      <span v-text="$t('sampleSentryApp.convocatoria.home.title')" id="convocatoria-heading">Convocatorias</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('sampleSentryApp.convocatoria.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'ConvocatoriaCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-convocatoria"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('sampleSentryApp.convocatoria.home.createLabel')"> Create a new Convocatoria </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && convocatorias && convocatorias.length === 0">
      <span v-text="$t('sampleSentryApp.convocatoria.home.notFound')">No convocatorias found</span>
    </div>
    <div class="table-responsive" v-if="convocatorias && convocatorias.length > 0">
      <table class="table table-striped" aria-describedby="convocatorias">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('sampleSentryApp.convocatoria.nombre')">Nombre</span></th>
            <th scope="row"><span v-text="$t('sampleSentryApp.convocatoria.anio')">Anio</span></th>
            <th scope="row"><span v-text="$t('sampleSentryApp.convocatoria.fechaCreacion')">Fecha Creacion</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="convocatoria in convocatorias" :key="convocatoria.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ConvocatoriaView', params: { convocatoriaId: convocatoria.id } }">{{
                convocatoria.id
              }}</router-link>
            </td>
            <td>{{ convocatoria.nombre }}</td>
            <td>{{ convocatoria.anio }}</td>
            <td>{{ convocatoria.fechaCreacion }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'ConvocatoriaView', params: { convocatoriaId: convocatoria.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'ConvocatoriaEdit', params: { convocatoriaId: convocatoria.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(convocatoria)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span
          id="sampleSentryApp.convocatoria.delete.question"
          data-cy="convocatoriaDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="core-delete-convocatoria-heading" v-text="$t('sampleSentryApp.convocatoria.delete.question', { id: removeId })">
          Are you sure you want to delete this Convocatoria?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="core-confirm-delete-convocatoria"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeConvocatoria()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./convocatoria.component.ts"></script>
