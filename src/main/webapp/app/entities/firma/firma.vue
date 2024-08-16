<template>
  <div>
    <h2 id="page-heading" data-cy="FirmaHeading">
      <span v-text="$t('sampleSentryApp.firma.home.title')" id="firma-heading">Firmas</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('sampleSentryApp.firma.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'FirmaCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-firma"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('sampleSentryApp.firma.home.createLabel')"> Create a new Firma </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && firmas && firmas.length === 0">
      <span v-text="$t('sampleSentryApp.firma.home.notFound')">No firmas found</span>
    </div>
    <div class="table-responsive" v-if="firmas && firmas.length > 0">
      <table class="table table-striped" aria-describedby="firmas">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('sampleSentryApp.firma.rol')">Rol</span></th>
            <th scope="row"><span v-text="$t('sampleSentryApp.firma.documentoUri')">Documento Uri</span></th>
            <th scope="row"><span v-text="$t('sampleSentryApp.firma.certificado')">Certificado</span></th>
            <th scope="row"><span v-text="$t('sampleSentryApp.firma.rfc')">Rfc</span></th>
            <th scope="row"><span v-text="$t('sampleSentryApp.firma.curp')">Curp</span></th>
            <th scope="row"><span v-text="$t('sampleSentryApp.firma.signature')">Signature</span></th>
            <th scope="row"><span v-text="$t('sampleSentryApp.firma.active')">Active</span></th>
            <th scope="row"><span v-text="$t('sampleSentryApp.firma.documento')">Documento</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="firma in firmas" :key="firma.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'FirmaView', params: { firmaId: firma.id } }">{{ firma.id }}</router-link>
            </td>
            <td>{{ firma.rol }}</td>
            <td>{{ firma.documentoUri }}</td>
            <td>{{ firma.certificado }}</td>
            <td>{{ firma.rfc }}</td>
            <td>{{ firma.curp }}</td>
            <td>{{ firma.signature }}</td>
            <td>{{ firma.active }}</td>
            <td>
              <div v-if="firma.documento">
                <router-link :to="{ name: 'DocumentoView', params: { documentoId: firma.documento.id } }">{{
                  firma.documento.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'FirmaView', params: { firmaId: firma.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'FirmaEdit', params: { firmaId: firma.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(firma)"
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
        ><span id="sampleSentryApp.firma.delete.question" data-cy="firmaDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="core-delete-firma-heading" v-text="$t('sampleSentryApp.firma.delete.question', { id: removeId })">
          Are you sure you want to delete this Firma?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="core-confirm-delete-firma"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeFirma()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./firma.component.ts"></script>
