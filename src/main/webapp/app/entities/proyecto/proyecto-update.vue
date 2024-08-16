<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="sampleSentryApp.proyecto.home.createOrEditLabel"
          data-cy="ProyectoCreateUpdateHeading"
          v-text="$t('sampleSentryApp.proyecto.home.createOrEditLabel')"
        >
          Create or edit a Proyecto
        </h2>
        <div>
          <div class="form-group" v-if="proyecto.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="proyecto.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('sampleSentryApp.proyecto.detalle')" for="proyecto-detalle">Detalle</label>
            <input
              type="text"
              class="form-control"
              name="detalle"
              id="proyecto-detalle"
              data-cy="detalle"
              :class="{ valid: !$v.proyecto.detalle.$invalid, invalid: $v.proyecto.detalle.$invalid }"
              v-model="$v.proyecto.detalle.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('sampleSentryApp.proyecto.modalidad')" for="proyecto-modalidad">Modalidad</label>
            <input
              type="text"
              class="form-control"
              name="modalidad"
              id="proyecto-modalidad"
              data-cy="modalidad"
              :class="{ valid: !$v.proyecto.modalidad.$invalid, invalid: $v.proyecto.modalidad.$invalid }"
              v-model="$v.proyecto.modalidad.$model"
              required
            />
            <div v-if="$v.proyecto.modalidad.$anyDirty && $v.proyecto.modalidad.$invalid">
              <small class="form-text text-danger" v-if="!$v.proyecto.modalidad.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('sampleSentryApp.proyecto.titulo')" for="proyecto-titulo">Titulo</label>
            <input
              type="text"
              class="form-control"
              name="titulo"
              id="proyecto-titulo"
              data-cy="titulo"
              :class="{ valid: !$v.proyecto.titulo.$invalid, invalid: $v.proyecto.titulo.$invalid }"
              v-model="$v.proyecto.titulo.$model"
              required
            />
            <div v-if="$v.proyecto.titulo.$anyDirty && $v.proyecto.titulo.$invalid">
              <small class="form-text text-danger" v-if="!$v.proyecto.titulo.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('sampleSentryApp.proyecto.solicitudClave')" for="proyecto-solicitudClave"
              >Solicitud Clave</label
            >
            <input
              type="text"
              class="form-control"
              name="solicitudClave"
              id="proyecto-solicitudClave"
              data-cy="solicitudClave"
              :class="{ valid: !$v.proyecto.solicitudClave.$invalid, invalid: $v.proyecto.solicitudClave.$invalid }"
              v-model="$v.proyecto.solicitudClave.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('sampleSentryApp.proyecto.solicitudFecha')" for="proyecto-solicitudFecha"
              >Solicitud Fecha</label
            >
            <div class="d-flex">
              <input
                id="proyecto-solicitudFecha"
                data-cy="solicitudFecha"
                type="datetime-local"
                class="form-control"
                name="solicitudFecha"
                :class="{ valid: !$v.proyecto.solicitudFecha.$invalid, invalid: $v.proyecto.solicitudFecha.$invalid }"
                :value="convertDateTimeFromServer($v.proyecto.solicitudFecha.$model)"
                @change="updateInstantField('solicitudFecha', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('sampleSentryApp.proyecto.completo')" for="proyecto-completo">Completo</label>
            <input
              type="checkbox"
              class="form-check"
              name="completo"
              id="proyecto-completo"
              data-cy="completo"
              :class="{ valid: !$v.proyecto.completo.$invalid, invalid: $v.proyecto.completo.$invalid }"
              v-model="$v.proyecto.completo.$model"
              required
            />
            <div v-if="$v.proyecto.completo.$anyDirty && $v.proyecto.completo.$invalid">
              <small class="form-text text-danger" v-if="!$v.proyecto.completo.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('sampleSentryApp.proyecto.convocatoria')" for="proyecto-convocatoria"
              >Convocatoria</label
            >
            <select
              class="form-control"
              id="proyecto-convocatoria"
              data-cy="convocatoria"
              name="convocatoria"
              v-model="proyecto.convocatoria"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  proyecto.convocatoria && convocatoriaOption.id === proyecto.convocatoria.id ? proyecto.convocatoria : convocatoriaOption
                "
                v-for="convocatoriaOption in convocatorias"
                :key="convocatoriaOption.id"
              >
                {{ convocatoriaOption.id }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.proyecto.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./proyecto-update.component.ts"></script>
