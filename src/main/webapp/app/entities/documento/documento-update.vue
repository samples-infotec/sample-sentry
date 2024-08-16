<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="sampleSentryApp.documento.home.createOrEditLabel"
          data-cy="DocumentoCreateUpdateHeading"
          v-text="$t('sampleSentryApp.documento.home.createOrEditLabel')"
        >
          Create or edit a Documento
        </h2>
        <div>
          <div class="form-group" v-if="documento.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="documento.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('sampleSentryApp.documento.uri')" for="documento-uri">Uri</label>
            <input
              type="text"
              class="form-control"
              name="uri"
              id="documento-uri"
              data-cy="uri"
              :class="{ valid: !$v.documento.uri.$invalid, invalid: $v.documento.uri.$invalid }"
              v-model="$v.documento.uri.$model"
              required
            />
            <div v-if="$v.documento.uri.$anyDirty && $v.documento.uri.$invalid">
              <small class="form-text text-danger" v-if="!$v.documento.uri.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('sampleSentryApp.documento.data')" for="documento-data">Data</label>
            <input
              type="text"
              class="form-control"
              name="data"
              id="documento-data"
              data-cy="data"
              :class="{ valid: !$v.documento.data.$invalid, invalid: $v.documento.data.$invalid }"
              v-model="$v.documento.data.$model"
              required
            />
            <div v-if="$v.documento.data.$anyDirty && $v.documento.data.$invalid">
              <small class="form-text text-danger" v-if="!$v.documento.data.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('sampleSentryApp.documento.proyecto')" for="documento-proyecto">Proyecto</label>
            <select class="form-control" id="documento-proyecto" data-cy="proyecto" name="proyecto" v-model="documento.proyecto">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="documento.proyecto && proyectoOption.id === documento.proyecto.id ? documento.proyecto : proyectoOption"
                v-for="proyectoOption in proyectos"
                :key="proyectoOption.id"
              >
                {{ proyectoOption.id }}
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
            :disabled="$v.documento.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./documento-update.component.ts"></script>
