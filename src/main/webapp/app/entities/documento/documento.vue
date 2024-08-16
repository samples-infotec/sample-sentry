<template>
  <div>
    <h2 id="page-heading" data-cy="DocumentoHeading">
      <span v-text="$t('sampleSentryApp.documento.home.title')" id="documento-heading">Documentos</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('sampleSentryApp.documento.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'DocumentoCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-documento"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('sampleSentryApp.documento.home.createLabel')"> Create a new Documento </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && documentos && documentos.length === 0">
      <span v-text="$t('sampleSentryApp.documento.home.notFound')">No documentos found</span>
    </div>
    <div class="table-responsive" v-if="documentos && documentos.length > 0">
      <table class="table table-striped" aria-describedby="documentos">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('sampleSentryApp.documento.uri')">Uri</span></th>
            <th scope="row"><span v-text="$t('sampleSentryApp.documento.data')">Data</span></th>
            <th scope="row"><span v-text="$t('sampleSentryApp.documento.proyecto')">Proyecto</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="documento in documentos" :key="documento.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'DocumentoView', params: { documentoId: documento.id } }">{{ documento.id }}</router-link>
            </td>
            <td>{{ documento.uri }}</td>
            <td>{{ documento.data }}</td>
            <td>
              <div v-if="documento.proyecto">
                <router-link :to="{ name: 'ProyectoView', params: { proyectoId: documento.proyecto.id } }">{{
                  documento.proyecto.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'DocumentoView', params: { documentoId: documento.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'DocumentoEdit', params: { documentoId: documento.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(documento)"
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
        ><span id="sampleSentryApp.documento.delete.question" data-cy="documentoDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="core-delete-documento-heading" v-text="$t('sampleSentryApp.documento.delete.question', { id: removeId })">
          Are you sure you want to delete this Documento?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="core-confirm-delete-documento"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeDocumento()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./documento.component.ts"></script>
