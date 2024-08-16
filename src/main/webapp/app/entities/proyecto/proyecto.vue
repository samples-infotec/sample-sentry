<template>
  <div>
    <h2 id="page-heading" data-cy="ProyectoHeading">
      <span v-text="$t('sampleSentryApp.proyecto.home.title')" id="proyecto-heading">Proyectos</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('sampleSentryApp.proyecto.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'ProyectoCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-proyecto"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('sampleSentryApp.proyecto.home.createLabel')"> Create a new Proyecto </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && proyectos && proyectos.length === 0">
      <span v-text="$t('sampleSentryApp.proyecto.home.notFound')">No proyectos found</span>
    </div>
    <div class="table-responsive" v-if="proyectos && proyectos.length > 0">
      <table class="table table-striped" aria-describedby="proyectos">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('sampleSentryApp.proyecto.detalle')">Detalle</span></th>
            <th scope="row"><span v-text="$t('sampleSentryApp.proyecto.modalidad')">Modalidad</span></th>
            <th scope="row"><span v-text="$t('sampleSentryApp.proyecto.titulo')">Titulo</span></th>
            <th scope="row"><span v-text="$t('sampleSentryApp.proyecto.solicitudClave')">Solicitud Clave</span></th>
            <th scope="row"><span v-text="$t('sampleSentryApp.proyecto.solicitudFecha')">Solicitud Fecha</span></th>
            <th scope="row"><span v-text="$t('sampleSentryApp.proyecto.completo')">Completo</span></th>
            <th scope="row"><span v-text="$t('sampleSentryApp.proyecto.convocatoria')">Convocatoria</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="proyecto in proyectos" :key="proyecto.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ProyectoView', params: { proyectoId: proyecto.id } }">{{ proyecto.id }}</router-link>
            </td>
            <td>{{ proyecto.detalle }}</td>
            <td>{{ proyecto.modalidad }}</td>
            <td>{{ proyecto.titulo }}</td>
            <td>{{ proyecto.solicitudClave }}</td>
            <td>{{ proyecto.solicitudFecha ? $d(Date.parse(proyecto.solicitudFecha), 'short') : '' }}</td>
            <td>{{ proyecto.completo }}</td>
            <td>
              <div v-if="proyecto.convocatoria">
                <router-link :to="{ name: 'ConvocatoriaView', params: { convocatoriaId: proyecto.convocatoria.id } }">{{
                  proyecto.convocatoria.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'ProyectoView', params: { proyectoId: proyecto.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'ProyectoEdit', params: { proyectoId: proyecto.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(proyecto)"
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
        ><span id="sampleSentryApp.proyecto.delete.question" data-cy="proyectoDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="core-delete-proyecto-heading" v-text="$t('sampleSentryApp.proyecto.delete.question', { id: removeId })">
          Are you sure you want to delete this Proyecto?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="core-confirm-delete-proyecto"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeProyecto()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./proyecto.component.ts"></script>
