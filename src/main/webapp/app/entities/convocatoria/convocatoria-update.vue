<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="sampleSentryApp.convocatoria.home.createOrEditLabel"
          data-cy="ConvocatoriaCreateUpdateHeading"
          v-text="$t('sampleSentryApp.convocatoria.home.createOrEditLabel')"
        >
          Create or edit a Convocatoria
        </h2>
        <div>
          <div class="form-group" v-if="convocatoria.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="convocatoria.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('sampleSentryApp.convocatoria.nombre')" for="convocatoria-nombre">Nombre</label>
            <input
              type="text"
              class="form-control"
              name="nombre"
              id="convocatoria-nombre"
              data-cy="nombre"
              :class="{ valid: !$v.convocatoria.nombre.$invalid, invalid: $v.convocatoria.nombre.$invalid }"
              v-model="$v.convocatoria.nombre.$model"
              required
            />
            <div v-if="$v.convocatoria.nombre.$anyDirty && $v.convocatoria.nombre.$invalid">
              <small class="form-text text-danger" v-if="!$v.convocatoria.nombre.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('sampleSentryApp.convocatoria.anio')" for="convocatoria-anio">Anio</label>
            <input
              type="number"
              class="form-control"
              name="anio"
              id="convocatoria-anio"
              data-cy="anio"
              :class="{ valid: !$v.convocatoria.anio.$invalid, invalid: $v.convocatoria.anio.$invalid }"
              v-model.number="$v.convocatoria.anio.$model"
            />
            <div v-if="$v.convocatoria.anio.$anyDirty && $v.convocatoria.anio.$invalid">
              <small class="form-text text-danger" v-if="!$v.convocatoria.anio.min" v-text="$t('entity.validation.min', { min: 1960 })">
                This field should be at least 1960.
              </small>
              <small class="form-text text-danger" v-if="!$v.convocatoria.anio.max" v-text="$t('entity.validation.max', { max: 2099 })">
                This field cannot be longer than 2099 characters.
              </small>
              <small class="form-text text-danger" v-if="!$v.convocatoria.anio.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('sampleSentryApp.convocatoria.fechaCreacion')" for="convocatoria-fechaCreacion"
              >Fecha Creacion</label
            >
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="convocatoria-fechaCreacion"
                  v-model="$v.convocatoria.fechaCreacion.$model"
                  name="fechaCreacion"
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
                id="convocatoria-fechaCreacion"
                data-cy="fechaCreacion"
                type="text"
                class="form-control"
                name="fechaCreacion"
                :class="{ valid: !$v.convocatoria.fechaCreacion.$invalid, invalid: $v.convocatoria.fechaCreacion.$invalid }"
                v-model="$v.convocatoria.fechaCreacion.$model"
              />
            </b-input-group>
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
            :disabled="$v.convocatoria.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./convocatoria-update.component.ts"></script>
